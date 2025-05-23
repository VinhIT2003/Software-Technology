package com.ComponentandDatabase.Database_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String SERVER_NAME = "LAPTOP-NGQUJ9MT\\QUANGVINH"; // Thay bằng tên server của bạn
    private static final String DATABASE_NAME = "QuanLyBanHang";
    private static final String URL = "jdbc:sqlserver://" + SERVER_NAME + ":1433;databaseName=" + DATABASE_NAME + ";encrypt=false;loginTimeout=30";
    private static final String USER = "sa";
    private static final String PASSWORD = "182003";

    private static Connection conn = null;

    // Kết nối CSDL với xử lý lỗi nâng cao
    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("❌ Database Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    // Phương thức đóng kết nối có xử lý lỗi tốt hơn
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("⚠ Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Kiểm tra trạng thái kết nối
    public static boolean isConnectionValid() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("⚠ Connection validation failed: " + e.getMessage());
            return false;
        }
    }
}
