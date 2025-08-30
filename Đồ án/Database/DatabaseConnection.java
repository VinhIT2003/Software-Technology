package com.ComponentandDatabase.Database_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String SERVER_NAME = "LAPTOP-NGQUJ9MT\\QUANGVINH"; // Thay bằng tên server của bạn
    private static final String DATABASE_NAME = "QuanLyBanHang";
    private static final String URL = "jdbc:sqlserver://" + SERVER_NAME + ":1433;databaseName=" + DATABASE_NAME + ";encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "182003";
    
    private static Connection conn = null;

    // Phương thức kết nối CSDL
    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                //System.out.println("✅ Kết nối thành công với SQL Server: " + SERVER_NAME);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối CSDL:");
            e.printStackTrace();
        }
        return conn;
    }

    // Phương thức đóng kết nối
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Đã đóng kết nối CSDL.");
            }
        } catch (SQLException e) {
            System.out.println("⚠ Lỗi khi đóng kết nối:");
            e.printStackTrace();
        }
    }
}

