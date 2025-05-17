package com.Admin.insurance.DAO;

import com.Admin.insurance.DTO.DTOProductInfo;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOProductInfo {
    
    public DTOProductInfo getProductInfoByIMEI(String imei) throws SQLException {
        String sql = "SELECT "
                   + "p.Product_ID, p.Product_Name, p.Category_ID, "
                   + "c.Sup_ID AS Brand, p.Warranty_Period "
                   + "FROM Bill_Exported_Details bed "
                   + "JOIN Product p ON bed.Product_ID = p.Product_ID "
                   + "JOIN Category c ON p.Category_ID = c.Category_ID "
                   + "WHERE bed.IMEI_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, imei);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new DTOProductInfo(
                    rs.getString("Product_ID"),
                    rs.getString("Product_Name"),
                    rs.getString("Category_ID"),
                    rs.getString("Brand"),
                    rs.getString("Warranty_Period")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving product info: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }

        return null; // Nếu không tìm thấy dữ liệu
    }
    
    public String getProductIDByIMEI(String imei) throws SQLException {
        String sql = "SELECT Product_ID FROM Bill_Exported_Details WHERE IMEI_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, imei);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Product_ID"); // Trả về Product_ID nếu tìm thấy
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Product_ID: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }

        return null; // Trả về null nếu không tìm thấy Product_ID
    }
}  
    

