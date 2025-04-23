
package com.User.login_user.Control_User;

import java.sql.*;
import java.util.Date;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import com.formdev.flatlaf.FlatLightLaf;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;

public class ControlRegister_User {
    private CustomDialog cs;
    private Connection conn;
    private DatabaseConnection db;
    // Constructor nhận kết nối từ bên ngoài
  
     // 🟢 Hàm đăng ký khách hàng
    public void registerCustomer(String idCard, String fullName, String gender, Date utilDate,
                                 String contact, String address, String email, String password) {

        // ✅ Áp dụng giao diện FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn= db.connect();

        // 🛑 Kiểm tra các trường bắt buộc
        if (idCard.isEmpty() || fullName.isEmpty() || gender.isEmpty() ||
            email.isEmpty() || contact.isEmpty() || address.isEmpty() || password.isEmpty()) {
            cs.showError("Please fill in all required fields!");
            return;
        }

        // 🛑 Kiểm tra ngày sinh
        if (utilDate == null) {
           cs.showError("Please select a date of birth!");
            return;
        }

        // Chuyển đổi từ `java.util.Date` sang `java.sql.Date`
        java.sql.Date dob = new java.sql.Date(utilDate.getTime());

        // 🛑 Kiểm tra định dạng email hợp lệ
        if (!email.matches("^[\\w.-]+@[\\w-]+\\.[a-z]{2,4}$")) {
            cs.showError("Invalid email format!");
            return;
        }

        // 🛑 Kiểm tra số điện thoại hợp lệ
        if (!contact.matches("^0\\d{9}$")) {
            cs.showError("Phone number must be 10 digits and start with 0!");
            return;
        }

        // 🛑 Kiểm tra kết nối cơ sở dữ liệu
        if (conn == null) {
            cs.showError("Database connection failed!");
            return;
        }

        try {
            // 🔍 Kiểm tra ID_Card đã tồn tại chưa
            String checkSql = "SELECT COUNT(*) FROM Customer WHERE Customer_ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, idCard);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    cs.showError("ID Card already exists! Please enter a different ID.");
                    return;
                }
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12)); 
            // 📝 Câu lệnh SQL để thêm khách hàng
            String sql = "INSERT INTO Customer (Customer_ID, Full_Name, Gender, Date_Of_Birth, Email, Contact, Address, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, idCard);
                pstmt.setString(2, fullName);
                pstmt.setString(3, gender);
                pstmt.setDate(4, dob);
                pstmt.setString(5, email);
                pstmt.setString(6, contact);
                pstmt.setString(7, address);
                pstmt.setString(8, hashedPassword);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    cs.showSuccess("Registration successful!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đăng ký: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

   
