package com.Admin.login.Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.formdev.flatlaf.FlatLightLaf;

public class ControlLogin {
    private Connection conn;
    private DatabaseConnection db = new DatabaseConnection(); // Khởi tạo kết nối
    private CustomDialog cs = new CustomDialog();

    private String adminName; // ✅ lưu tên admin sau khi đăng nhập

    public String getAdminName() {
        return adminName;
    }

    // 🟢 Hàm đăng nhập khách hàng
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
            String sql = "SELECT Password, Admin_Name FROM Admin WHERE Admin_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, adminID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String hashedPassword = rs.getString("Password");

                    if (BCrypt.checkpw(password, hashedPassword)) {
                        adminName = rs.getString("Admin_Name"); // ✅ lấy tên admin
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
}

