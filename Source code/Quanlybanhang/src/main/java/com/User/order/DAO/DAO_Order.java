
package com.User.order.DAO;

import com.User.order.DTO.DTO_Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.User.order.DTO.DTO_Order;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
public class DAO_Order {

    public boolean insertOrder(DTO_Order order) {
        String sql = "INSERT INTO [Orders] (Order_No, Customer_ID, Total_Quantity_Product, Total_Price, Payment, Date_Order, Time_Order) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getOrderNo());
            stmt.setString(2, order.getCustomerID());
            stmt.setInt(3, order.getTotalQuantityProduct());
            stmt.setBigDecimal(4, order.getTotalPrice());
            stmt.setString(5, order.getPayment());
            stmt.setDate(6, java.sql.Date.valueOf(order.getDateOrder()));
            stmt.setTime(7, java.sql.Time.valueOf(order.getTimeOrder()));

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<DTO_Order> getOrdersByCustomer(String customerID) {
        ArrayList<DTO_Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Customer_ID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String orderNo = rs.getString("Order_No");
                String customerId = rs.getString("Customer_ID");
                int totalQuantity = rs.getInt("Total_Quantity_Product");
                BigDecimal totalPrice = rs.getBigDecimal("Total_Price");
                String payment = rs.getString("Payment");
                Date dateOrder = rs.getDate("Date_Order");
                Time timeOrder = rs.getTime("Time_Order");

                DTO_Order order = new DTO_Order(orderNo, customerId, totalQuantity, totalPrice, payment, 
                                                dateOrder.toLocalDate(), timeOrder.toLocalTime());
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
    
    public ArrayList<DTO_Order> getSortedOrdersByCustomer(String customerID) {
        ArrayList<DTO_Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Customer_ID = ? "
                   + "ORDER BY Date_Order DESC, Time_Order DESC"; // Sắp xếp ngày và giờ giảm dần

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String orderNo = rs.getString("Order_No");
                String customerId = rs.getString("Customer_ID");
                int totalQuantity = rs.getInt("Total_Quantity_Product");
                BigDecimal totalPrice = rs.getBigDecimal("Total_Price");
                String payment = rs.getString("Payment");
                Date dateOrder = rs.getDate("Date_Order");
                Time timeOrder = rs.getTime("Time_Order");

                DTO_Order order = new DTO_Order(orderNo, customerId, totalQuantity, totalPrice, payment, 
                                              dateOrder.toLocalDate(), timeOrder.toLocalTime());
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
    
    
    
    
}