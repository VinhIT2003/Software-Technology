package com.Admin.insurance.DAO;

import com.Admin.insurance.DTO.DTO_CustomerInfo;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCustomerInfo {

    // Lấy thông tin khách hàng theo IMEI
    public DTO_CustomerInfo getCustomerInfoByIMEI(String imei) throws SQLException {
        String sql = "SELECT c.Customer_ID, c.Full_Name, c.Address, c.Contact "
                   + "FROM Bill_Exported_Details bed "
                   + "JOIN Customer c ON bed.Customer_ID = c.Customer_ID "
                   + "WHERE bed.IMEI_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, imei);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new DTO_CustomerInfo(
                    rs.getString("Customer_ID"),
                    rs.getString("Full_Name"),
                    rs.getString("Address"),
                    rs.getString("Contact")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer info: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }

        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    // Lấy Customer_ID theo IMEI
    public String getCustomerIDByIMEI(String imei) throws SQLException {
        String sql = "SELECT Customer_ID FROM Bill_Exported_Details WHERE IMEI_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, imei);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Customer_ID"); // Trả về Customer_ID nếu tìm thấy
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Customer_ID: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }

        return null; // Trả về null nếu không tìm thấy Customer_ID
    }
}
