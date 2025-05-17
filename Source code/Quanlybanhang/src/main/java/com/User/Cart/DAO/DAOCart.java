package com.User.Cart.DAO;

import com.User.Cart.DTO.DTOCart;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCart {
    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(DTOCart cartItem) {
        String sql = "INSERT INTO Cart (Customer_ID, Product_ID, Quantity) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cartItem.getCustomerID());
            stmt.setString(2, cartItem.getProductID());
            stmt.setInt(3, cartItem.getQuantity());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
    public boolean isProductInCart(String customerID, String productID) {
        String sql = "SELECT COUNT(*) FROM Cart WHERE Customer_ID = ? AND Product_ID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerID);
            stmt.setString(2, productID);
            
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCartItem(DTOCart cartItem) {
        String sql = "UPDATE Cart SET Quantity = ? WHERE Customer_ID = ? AND Product_ID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getQuantity());         // Gán trực tiếp số lượng mới
            stmt.setString(2, cartItem.getCustomerID());
            stmt.setString(3, cartItem.getProductID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     public ArrayList<DTOCart> getCartItemsByCustomer(String customerID) {
        ArrayList<DTOCart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM Cart WHERE Customer_ID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DTOCart item = new DTOCart(
                    rs.getString("Customer_ID"),
                    rs.getString("Product_ID"),
                    rs.getInt("Quantity")
                );
                cartItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
    
    public boolean deleteCartItem(String customerId, String productId) {
        String sql = "DELETE FROM Cart WHERE Customer_ID = ? AND Product_ID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            stmt.setString(2, productId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
   public boolean clearCart(String customerID) {
        String sql = "DELETE FROM Cart WHERE Customer_ID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
}