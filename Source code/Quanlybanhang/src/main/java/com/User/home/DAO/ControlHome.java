package com.User.home.DAO;

import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.User.home.DTO.productDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ControlHome {
    private DatabaseConnection db = new DatabaseConnection(); // đảm bảo khởi tạo

    public ArrayList<productDTO> showProduct(String condition) {
        ArrayList<productDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                p.Product_ID, 
                p.Product_Name, 
                p.CPU, 
                p.Ram, 
                p.Graphics_Card, 
                p.Operating_System, 
                p.Price, 
                p.Quantity, 
                p.Warranty_Period, 
                CASE 
                    WHEN p.Quantity = 0 THEN 'Unavailable' 
                    ELSE p.Status 
                END AS Status, 
                c.Category_ID, 
                p.Image
            FROM 
                Product p
            JOIN Category c ON p.Category_ID = c.Category_ID
            """ + (condition != null && !condition.trim().isEmpty() ? " WHERE " + condition : "");

        try (
            Connection conn = db.connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
          

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productDTO product = new productDTO(
                    rs.getString("Product_ID"),
                    rs.getString("Product_Name"),
                    rs.getString("CPU"),
                    rs.getString("Ram"),
                    rs.getString("Graphics_Card"),
                    rs.getString("Operating_System"),
                    rs.getBigDecimal("Price"),
                    rs.getInt("Quantity"),
                    rs.getString("Warranty_Period"),
                    rs.getString("Status"),
                    rs.getString("Category_ID"),
                    rs.getString("Image")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public productDTO getProductById(String productId) {
        String sql = """
            SELECT p.Product_ID, p.Product_Name, p.CPU, p.Ram, p.Graphics_Card, 
                   p.Operating_System, p.Price, p.Quantity, p.Warranty_Period, 
                   p.Status, p.Category_ID, p.Image, s.Sup_ID
            FROM Product p
            JOIN Category c ON p.Category_ID = c.Category_ID
            JOIN Supplier s ON c.Sup_ID = s.Sup_ID
            WHERE p.Product_ID = ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productDTO product = new productDTO();
                    product.setProductID(rs.getString("Product_ID"));
                    product.setProductName(rs.getString("Product_Name"));
                    product.setCpu(rs.getString("CPU"));
                    product.setRam(rs.getString("Ram"));
                    product.setGraphicsCard(rs.getString("Graphics_Card"));
                    product.setOperatingSystem(rs.getString("Operating_System"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getString("Warranty_Period"));
                    product.setStatus(rs.getString("Status"));
                    product.setCategoryID(rs.getString("Category_ID"));
                    product.setImage(rs.getString("Image"));

                    // Lấy Brand từ Supplier và gán vào một biến tạm
                    String brand = rs.getString("Sup_ID");

                   
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Lỗi khi lấy thông tin sản phẩm theo ID!");
        }

        return null;
    }
    
    public String getBrandByProductId(String productId) {
        String sql = """
            SELECT s.Sup_ID
            FROM Product p
            JOIN Category c ON p.Category_ID = c.Category_ID
            JOIN Supplier s ON c.Sup_ID = s.Sup_ID
            WHERE p.Product_ID = ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Sup_ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
    
}
