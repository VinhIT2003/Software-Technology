package com.Admin.login.DAO;

import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

// Jakarta Mail imports
import jakarta.mail.*;
import jakarta.mail.internet.*;
import com.Admin.login.DTO.DTOAccount_ad;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.formdev.flatlaf.FlatLightLaf;

public class ControlLogin {
    private Connection conn;
    private DatabaseConnection db = new DatabaseConnection();
    private CustomDialog cs = new CustomDialog();
    private String adminName;
    private DTOAccount_ad account;
    private static String recentOTP = null;
    private static long otpTimestamp = 0;
    public String getAdminName() {
        return adminName;
    }
    
    public DTOAccount_ad getAccount() {
        return account;
    }

    public boolean loginCustomer(String adminID, String password) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn = db.connect();

        if (conn == null) {
            cs.showError("Database connection failed!");
            return false;
        }

        if (adminID.isEmpty() || password.isEmpty()) {
            cs.showError("Please enter both ID and Password!");
            return false;
        }

        try {
            String sql = "SELECT * FROM Admin WHERE Admin_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, adminID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String hashedPassword = rs.getString("Password");

                    if (BCrypt.checkpw(password, hashedPassword)) {
                        account = new DTOAccount_ad(
                            rs.getString("Admin_ID"),
                            rs.getString("Admin_Name"),
                            rs.getString("Gender"),
                            rs.getString("Email"),
                            rs.getString("Contact"),
                            rs.getString("Password")
                        );
                        adminName = rs.getString("Admin_Name");
                        return true;
                    } else {
                        cs.showError("Incorrect password!");
                        return false;
                    }
                } else {
                    cs.showError("Admin ID not found!");
                    return false;
                }
            }
        } catch (Exception e) {
            cs.showError("Login failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String sendOTPToEmail(String adminID) {
          conn = db.connect();

          if (conn == null) {
              cs.showError("Cannot connect to the database!");
              return null;
          }

          try {
              String sql = "SELECT Email FROM Admin WHERE Admin_ID = ?";
              PreparedStatement pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, adminID);
              ResultSet rs = pstmt.executeQuery();

              if (rs.next()) {
                  String email = rs.getString("Email");
                  String otp = String.valueOf(100000 + new Random().nextInt(900000));

                  if (sendEmail(email, otp)) {
                      // ✅ Lưu OTP và thời gian gửi
                      recentOTP = otp;
                      otpTimestamp = System.currentTimeMillis(); // thời gian tính bằng milliseconds
                      return otp;
                  } else {
                      cs.showError("Unable to send OTP. Please check the email address!");
                  }
              } else {
                  cs.showError("Failed to send OTP because Admin ID was not found!");
              }
          } catch (Exception e) {
              cs.showError("Error while sending OTP: " + e.getMessage());
              e.printStackTrace();
          }

          return null;
     }


    private boolean sendEmail(String toEmail, String otp) {
        final String fromEmail = "zquangvinh359@gmail.com";
        final String password = "trwa fmjj zlad vtdg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.debug", "true");

        try {
            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("OTP Code for Password Recovery");

           message.setContent(
            "Your OTP code is: <strong>" + otp + "</strong><br>" +
            "Please do not share this code with anyone.<br>" +
            "This OTP will expire in 1 minute.",
            "text/html; charset=UTF-8"
       );

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            cs.showError("Error while sending email: " + e.getMessage());
            return false;
        }
    }
    
    public boolean confirmOTP(String inputOTP) {
        if (recentOTP == null) {
            cs.showError("No OTP has been sent.");
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpTimestamp;

        if (elapsedTime > 60_000) { // 60,000 ms = 1 phút
            cs.showError("OTP has expired. Please request a new one.");
            return false;
        }

        if (inputOTP.equals(recentOTP)) {
            CustomDialog.showSuccess("OTP verified successfully!");
            return true;
        } else {
            //cs.showError("Invalid OTP code. Please try again.");
            return false;
        }
    }
    
    public boolean updateNewPassword(DTOAccount_ad dto, String confirmPass) {
      String newPass = dto.getPassword();
      String adminID = dto.getAdminID();

      // Kiểm tra trống
      if (newPass == null || newPass.isEmpty() || confirmPass == null || confirmPass.isEmpty()) {
          cs.showError("Password fields cannot be empty.");
          return false;
      }

      // Kiểm tra khớp mật khẩu
      if (!newPass.equals(confirmPass)) {
          cs.showError("Passwords do not match.");
          return false;
      }

      // Hash password bằng BCrypt
      String hashedPassword = BCrypt.hashpw(newPass, BCrypt.gensalt());

      conn = db.connect();
      if (conn == null) {
          cs.showError("Database connection failed.");
          return false;
      }

      try {
          String sql = "UPDATE Admin SET Password = ? WHERE Admin_ID = ?";
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, hashedPassword);  // Cập nhật password đã mã hóa
          pstmt.setString(2, adminID);

          int rows = pstmt.executeUpdate();
          if (rows > 0) {
              //cs.showSuccess("Password updated successfully.");
              return true;
          } else {
              cs.showError("Update failed. Admin ID not found.");
          }
      } catch (Exception e) {
          cs.showError("Error updating password: " + e.getMessage());
          e.printStackTrace();
      }

      return false;
  }


    
}