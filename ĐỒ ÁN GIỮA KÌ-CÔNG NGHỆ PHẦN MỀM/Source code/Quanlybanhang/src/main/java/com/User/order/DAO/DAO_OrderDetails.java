
package com.User.order.DAO;

import com.User.order.DTO.DTO_OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;

public class DAO_OrderDetails {

    public boolean insertOrderDetail(DTO_OrderDetails detail) {
        String sql = "INSERT INTO Orders_Details (Order_No, Customer_ID, Product_ID, Price, Quantity, Date_Order, Time_Order, Status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, detail.getOrderNo());
            stmt.setString(2, detail.getCustomerID());
            stmt.setString(3, detail.getProductID());
            stmt.setBigDecimal(4, detail.getPrice());
            stmt.setInt(5, detail.getQuantity());
            stmt.setDate(6, java.sql.Date.valueOf(detail.getDateOrder()));
            stmt.setTime(7, java.sql.Time.valueOf(detail.getTimeOrder()));
            stmt.setString(8, detail.getStatus());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public ArrayList<DTO_OrderDetails> getOrderDetails(String customerID, String orderNo) {
         ArrayList<DTO_OrderDetails> detailsList = new ArrayList<>();
        String sql = "SELECT * FROM Orders_Details WHERE Customer_ID = ? AND Order_No = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerID);
            stmt.setString(2, orderNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTO_OrderDetails detail = new DTO_OrderDetails();
                    detail.setOrderNo(rs.getString("Order_No"));
                    detail.setCustomerID(rs.getString("Customer_ID"));
                    detail.setProductID(rs.getString("Product_ID"));
                    detail.setPrice(rs.getBigDecimal("Price"));
                    detail.setQuantity(rs.getInt("Quantity"));
                    detail.setDateOrder(rs.getDate("Date_Order").toLocalDate());
                    detail.setTimeOrder(rs.getTime("Time_Order").toLocalTime());
                    detail.setStatus(rs.getString("Status"));
                    
                    detailsList.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return detailsList;
    }
    
    
}
