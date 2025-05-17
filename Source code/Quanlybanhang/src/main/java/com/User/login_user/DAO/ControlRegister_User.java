
package com.User.login_user.DAO;

import java.sql.*;
import java.util.Date;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import com.formdev.flatlaf.FlatLightLaf;
import com.User.login_user.DTO.DTOAccount_cus;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;

public class ControlRegister_User {
    private CustomDialog cs;
    private Connection conn;
    private DatabaseConnection db;
    
    public void registerCustomer(DTOAccount_cus customerDTO) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn = db.connect();

        // 🛑 Kiểm tra các trường bắt buộc
        if (customerDTO.getCustomerID().isEmpty() || customerDTO.getFullName().isEmpty() || 
            customerDTO.getGender().isEmpty() || customerDTO.getEmail().isEmpty() || 
            customerDTO.getContact().isEmpty() || customerDTO.getAddress().isEmpty() || 
            customerDTO.getPassword().isEmpty()) {
            cs.showError("Please fill in all required fields!");
            return;
        }

        if (customerDTO.getDateOfBirth() == null) {
            cs.showError("Please select a date of birth!");
            return;
        }

        java.sql.Date dob = new java.sql.Date(customerDTO.getDateOfBirth().getTime());

        if (!customerDTO.getEmail().matches("^[\\w.-]+@[\\w-]+\\.[a-z]{2,4}$")) {
            cs.showError("Invalid email format!");
            return;
        }

        if (!customerDTO.getContact().matches("^0\\d{9}$")) {
            cs.showError("Phone number must be 10 digits and start with 0!");
            return;
        }

        if (conn == null) {
            cs.showError("Database connection failed!");
            return;
        }

        try {
            // 🔍 Kiểm tra trùng ID
            String checkSql = "SELECT COUNT(*) FROM Customer WHERE Customer_ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, customerDTO.getCustomerID());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    cs.showError("ID Card already exists! Please enter a different ID.");
                    return;
                }
            }

            // ✅ Mã hóa mật khẩu
            String hashedPassword = BCrypt.hashpw(customerDTO.getPassword(), BCrypt.gensalt(12));

            // 📝 Thêm khách hàng
            String sql = "INSERT INTO Customer (Customer_ID, Full_Name, Gender, Date_Of_Birth, Email, Contact, Address, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, customerDTO.getCustomerID());
                pstmt.setString(2, customerDTO.getFullName());
                pstmt.setString(3, customerDTO.getGender());
                pstmt.setDate(4, dob);
                pstmt.setString(5, customerDTO.getEmail());
                pstmt.setString(6, customerDTO.getContact());
                pstmt.setString(7, customerDTO.getAddress());
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

   
