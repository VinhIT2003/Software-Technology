package com.User.login_user.DAO;

import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.User.login_user.DTO.DTOAccount_cus;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;



public class ControlLogin_cus {
    private Connection conn;
    private DTOAccount_cus currentAccount;
    private String customerName;
    private static String recentOTP = null;
    private static long otpTimestamp = 0;
    
    public ControlLogin_cus(Connection conn) {
        this.conn = conn;
    }

    // ✅ Hàm này trả về customerName sau khi login thành công
    public String getCustomerName() {
        return customerName;
    }

   public DTOAccount_cus login(String email, String password) {
        String query = "SELECT * FROM Customer WHERE Email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("Status");
                String hashedPassword = rs.getString("Password");  // 🛑 lấy Password đã mã hóa trong DB

                if ("Inactive".equalsIgnoreCase(status)) {
                    CustomDialog.showError("You are not authorized to log in.");
                    return null;
                } else if ("Active".equalsIgnoreCase(status)) {
                    // ✅ Dùng BCrypt để kiểm tra password nhập vào với password trong DB
                    if (org.mindrot.jbcrypt.BCrypt.checkpw(password, hashedPassword)) {
                        currentAccount = new DTOAccount_cus(
                            rs.getString("Customer_ID"),
                            rs.getString("Full_Name"),
                            rs.getString("Gender"),
                            rs.getDate("Date_Of_Birth"),
                            rs.getString("Email"),
                            rs.getString("Contact"),
                            rs.getString("Address"),
                            rs.getString("Password"),
                            rs.getString("Status")
                        );

                        customerName = rs.getString("Full_Name");  // Lưu tên cho getName()
                        return currentAccount;
                    } else {
                        CustomDialog.showError("Email or password is invalid. Please try again !");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   
   
    public String sendOTPToEmail(String email) {
      conn = DatabaseConnection.connect();

      if (conn == null) {
          CustomDialog.showError("Cannot connect to the database!");
          return null;
      }

      try {
          String sql = "SELECT Email FROM Customer WHERE Email = ?";
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, email);
          ResultSet rs = pstmt.executeQuery();

          if (rs.next()) {
              // Nếu email tồn tại trong DB
              String otp = String.valueOf(100000 + new Random().nextInt(900000));

              if (sendEmail(email, otp)) {
                  recentOTP = otp;
                  otpTimestamp = System.currentTimeMillis();
                  return otp;
              } else {
                  CustomDialog.showError("Unable to send OTP. Please check the email address!");
              }
          } else {
              // Email không tồn tại
              CustomDialog.showError("The email address does not exist in the system!");
          }
      } catch (Exception e) {
          CustomDialog.showError("Error while sending OTP: " + e.getMessage());
          e.printStackTrace();
      }

      return null;
  }


   
    private boolean sendEmail(String toEmail, String otp) {
            final String fromEmail = "";
            final String password = "";

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
                CustomDialog.showError("Error while sending email: " + e.getMessage());
                return false;
            }
        }
    
     public boolean confirmOTP(String inputOTP) {
        if (recentOTP == null) {
            CustomDialog.showError("No OTP has been sent.");
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpTimestamp;

        if (elapsedTime > 60_000) { // 60,000 ms = 1 phút
            CustomDialog.showError("OTP has expired. Please request a new one.");
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

  
    public boolean updateNewPassword(DTOAccount_cus dto, String confirmPass) {
       String newPass = dto.getPassword();
       String email = dto.getEmail();

      // Kiểm tra trống
      if (newPass == null || newPass.isEmpty() || confirmPass == null || confirmPass.isEmpty()) {
          CustomDialog.showError("Password fields cannot be empty.");
          return false;
      }

      // Kiểm tra khớp mật khẩu
      if (!newPass.equals(confirmPass)) {
          CustomDialog.showError("Passwords do not match.");
          return false;
      }

      // Hash password bằng BCrypt
      String hashedPassword = BCrypt.hashpw(newPass, BCrypt.gensalt());

      conn = DatabaseConnection.connect();
      if (conn == null) {
          CustomDialog.showError("Database connection failed.");
          return false;
      }

      try {
          String sql = "UPDATE Customer SET Password = ? WHERE Email = ?";
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, hashedPassword);  // Cập nhật password đã mã hóa
          pstmt.setString(2, email);

          int rows = pstmt.executeUpdate();
          if (rows > 0) {
              //cs.showSuccess("Password updated successfully.");
              return true;
          } else {
              CustomDialog.showError("Update failed. Admin ID not found.");
          }
      } catch (Exception e) {
          CustomDialog.showError("Error updating password: " + e.getMessage());
          e.printStackTrace();
      }

      return false;
  }
     
     

}
