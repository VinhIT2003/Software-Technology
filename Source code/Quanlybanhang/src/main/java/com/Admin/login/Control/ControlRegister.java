
package com.Admin.login.Control;

import java.sql.*;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import com.formdev.flatlaf.FlatLightLaf;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;

public class ControlRegister {
    private CustomDialog cs;
    private Connection conn;
    private DatabaseConnection db;
    // Constructor nhận kết nối từ bên ngoài
  
     // 🟢 Hàm đăng ký khách hàng
    public void registerCustomer(String idCard, String fullName, String gender,
                                 String contact, String email, String password) {

        // ✅ Áp dụng giao diện FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn= db.connect();

        // 🛑 Kiểm tra các trường bắt buộc
        if (idCard.isEmpty() || fullName.isEmpty() || gender.isEmpty() ||
            email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            cs.showError("Please fill in all required fields!");
            return;
        }


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
            String checkSql = "SELECT COUNT(*) FROM Admin WHERE Admin_ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, idCard);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    cs.showError("ID Card already exists! Please enter a different ID.");
                    return;
                }
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12)); 
            // 📝 Câu lệnh SQL để thêm Admin
            String sql = "INSERT INTO Admin (Admin_ID, Admin_Name, Gender, Email, Contact, Password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, idCard);
                pstmt.setString(2, fullName);
                pstmt.setString(3, gender);
                pstmt.setString(4, email);
                pstmt.setString(5, contact);
                pstmt.setString(6, hashedPassword);

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

   
