package com.Admin.export.DAO;

import com.Admin.export.DTO.DTO_Oderdetails;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.Admin.product.DTO.DTOIMEI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DAO_OrderDetail {
    
    public List<DTO_Oderdetails> getConfirmedOrderDetailsOldestFirst() throws SQLException {
        List<DTO_Oderdetails> orderDetails = new ArrayList<>();
        String sql = "SELECT Order_No, Customer_ID, Product_ID, Price, Quantity, "
                   + "Date_Order, Time_Order, Status FROM Orders_Details "
                   + "WHERE Status = 'Confirmed' "  // Thêm điều kiện Status
                   + "ORDER BY Date_Order ASC, Time_Order ASC"; // Cũ nhất lên đầu

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                DTO_Oderdetails detail = mapResultSetToDTO(rs);
                orderDetails.add(detail);
            }
        }
        return orderDetails;
    }

    public List<DTO_Oderdetails> getConfirmedOrderDetailsByOrderNo(String orderNo) throws SQLException {
        List<DTO_Oderdetails> orderDetails = new ArrayList<>();
        String sql = "SELECT Order_No, Customer_ID, Product_ID, Price, Quantity, "
                   + "Date_Order, Time_Order, Status FROM Orders_Details "
                   + "WHERE Order_No = ? AND Status = 'Confirmed' "  // Thêm điều kiện Status
                   + "ORDER BY Date_Order ASC, Time_Order ASC"; // Cũ nhất lên đầu

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, orderNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTO_Oderdetails detail = mapResultSetToDTO(rs);
                    orderDetails.add(detail);
                }
            }
        }
        return orderDetails;
    }

    // Phương thức hỗ trợ ánh xạ ResultSet sang DTO (giữ nguyên)
    private DTO_Oderdetails mapResultSetToDTO(ResultSet rs) throws SQLException {
        DTO_Oderdetails detail = new DTO_Oderdetails();
        detail.setOrderNo(rs.getString("Order_No"));
        detail.setCustomerID(rs.getString("Customer_ID"));
        detail.setProductID(rs.getString("Product_ID"));
        detail.setPrice(rs.getBigDecimal("Price"));
        detail.setQuantity(rs.getInt("Quantity"));
        detail.setDateOrder(rs.getDate("Date_Order").toLocalDate());
        detail.setTimeOrder(rs.getTime("Time_Order").toLocalTime());
        detail.setStatus(rs.getString("Status"));
        return detail;
    }
    
   public List<DTO_Oderdetails> searchOrderDetails(String searchType, String keyword) throws SQLException {
      List<DTO_Oderdetails> orderDetails = new ArrayList<>();
      String sql = "SELECT Order_No, Customer_ID, Product_ID, Price, Quantity, " +
                 "Date_Order, Time_Order, Status FROM Orders_Details " +
                 "WHERE Status = 'Confirmed' AND ";

      switch (searchType) {
          case "Order.No":
              sql += "Order_No LIKE ?";
              break;
          case "Customer.ID":
              sql += "Customer_ID LIKE ?";
              break;
          case "Date Order":
              sql += "CONVERT(varchar, Date_Order, 103) LIKE ?"; // Định dạng dd/mm/yyyy
              break;
          default:
              sql += "Order_No LIKE ?";
      }

      sql += " ORDER BY Date_Order ASC, Time_Order ASC";

      try (Connection conn = DatabaseConnection.connect();
           PreparedStatement stmt = conn.prepareStatement(sql)) {

          stmt.setString(1, "%" + keyword + "%");

          try (ResultSet rs = stmt.executeQuery()) {
              while (rs.next()) {
                  DTO_Oderdetails detail = new DTO_Oderdetails();
                  detail.setOrderNo(rs.getString("Order_No"));
                  detail.setCustomerID(rs.getString("Customer_ID"));
                  detail.setProductID(rs.getString("Product_ID"));
                  detail.setPrice(rs.getBigDecimal("Price"));
                  detail.setQuantity(rs.getInt("Quantity"));
                  detail.setDateOrder(rs.getDate("Date_Order").toLocalDate());
                  detail.setTimeOrder(rs.getTime("Time_Order").toLocalTime());
                  detail.setStatus(rs.getString("Status"));

                  orderDetails.add(detail);
              }
          }
      }
      return orderDetails;
  }

   public List<DTOIMEI> getIMEIByProductID(String productID) throws SQLException {
        List<DTOIMEI> imeiList = new ArrayList<>();
        String sql = "SELECT IMei_No FROM IMei_Product WHERE Product_ID = ? AND State = 'Useable'";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTOIMEI imei = new DTOIMEI();
                    imei.setImeiNo(rs.getString("IMei_No"));
                    imeiList.add(imei); // Thêm dòng này để thêm IMEI vào danh sách
                }
            }
        }
        return imeiList;
    }
   
    public boolean deleteIMEI(String imeiNo) {
        String sql = "DELETE FROM IMei_Product WHERE IMei_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imeiNo);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0; // true nếu có ít nhất một dòng bị xóa

        } catch (SQLException e) {
            System.err.println("Error deleting IMEI: " + e.getMessage());
            return false;
        }
    }

   
    
  public String getProductName(String productID) throws SQLException {
     String productName = null;
     String sql = "SELECT Product_Name FROM Product WHERE Product_ID = ?";

     try (Connection conn = DatabaseConnection.connect();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

         stmt.setString(1, productID);

         try (ResultSet rs = stmt.executeQuery()) {
             if (rs.next()) {
                 productName = rs.getString("Product_Name");
             }
         }
     }
     return productName;
 }
    public boolean deleteOrderByOrderNo(String orderNo) {
        String sql = "DELETE FROM Orders WHERE Order_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderNo);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0; // Trả về true nếu có ít nhất một dòng bị xóa

        } catch (SQLException e) {
            System.err.println("Error orders deleted ! " + e.getMessage());
            return false;
        }
    }
    
    
   public String getPayment(String orderNo) throws SQLException {
        String payment = "Unknown"; // Giá trị mặc định nếu không tìm thấy

        String sql = "SELECT Payment FROM Orders WHERE Order_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderNo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    payment = rs.getString("Payment");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting payment method: " + e.getMessage());
            throw e; // Re-throw exception để xử lý ở tầng cao hơn
        }

        return payment;
    }
        
}

   