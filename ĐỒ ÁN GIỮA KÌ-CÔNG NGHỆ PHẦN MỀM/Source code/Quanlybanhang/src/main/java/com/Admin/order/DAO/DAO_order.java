package com.Admin.order.DAO;

import com.Admin.order.DTO.DTO_order;
import com.Admin.order.DTO.DTO_orderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;

public class DAO_order {
    
  
    public List<DTO_order> getAllOrdersSorted() {
        List<DTO_order> orders = new ArrayList<>();
        String sql = "SELECT o.Order_No, o.Customer_ID, o.Total_Quantity_Product, " +
                     "o.Total_Price, o.Payment, o.Date_Order, o.Time_Order, " +
                     "od.Status, c.Full_Name, c.Address, c.Contact " +
                     "FROM Orders o " +
                     "JOIN Orders_Details od ON o.Order_No = od.Order_No " +
                     "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                     "GROUP BY o.Order_No, o.Customer_ID, o.Total_Quantity_Product, " +
                     "o.Total_Price, o.Payment, o.Date_Order, o.Time_Order, " +
                     "c.Full_Name, c.Address, c.Contact, od.Status";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                DTO_order order = new DTO_order();
                order.setOrderNo(rs.getString("Order_No"));
                order.setCustomerID(rs.getString("Customer_ID"));
                order.setTotalQuantityProduct(rs.getInt("Total_Quantity_Product"));
                order.setTotalPrice(rs.getBigDecimal("Total_Price"));
                order.setPayment(rs.getString("Payment"));
                order.setDateOrder(rs.getDate("Date_Order").toLocalDate());
                order.setTimeOrder(rs.getTime("Time_Order").toLocalTime());
                
                // Thêm thông tin khách hàng và trạng thái
                order.setCustomerName(rs.getString("Full_Name"));
                order.setAddress(rs.getString("Address"));
                order.setContact(rs.getString("Contact"));
                order.setStatus(rs.getString("Status"));
                
                orders.add(order);
            }
            
            // Sắp xếp theo yêu cầu: Status='Waiting' -> Date Order -> Time Order
            orders = orders.stream()
                .sorted(Comparator.comparing(DTO_order::getStatus, (s1, s2) -> {
                    if (s1.equals("Waiting") && !s2.equals("Waiting")) return -1;
                    if (!s1.equals("Waiting") && s2.equals("Waiting")) return 1;
                    return 0;
                })
                .thenComparing(DTO_order::getDateOrder)
                .thenComparing(DTO_order::getTimeOrder))
                .collect(Collectors.toList());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }    
        return orders;
    }
    
    public boolean updateOrderStatus(String orderNo, String newStatus) {
        String sql = "UPDATE Orders_Details SET Status = ? WHERE Order_No = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, orderNo);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DTO_orderDetails> getAllOrderDetails() throws SQLException {
        List<DTO_orderDetails> orderDetails = new ArrayList<>();
        String sql = "SELECT Order_No, Customer_ID, Product_ID, Price, Quantity, "
                   + "Date_Order, Time_Order, Status FROM Orders_Details";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DTO_orderDetails detail = new DTO_orderDetails();
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
        return orderDetails;
    }
    
    public boolean exportOrderDetailsToExcel(String filePath) {
        try {
            List<DTO_orderDetails> orderDetails = getAllOrderDetails();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Order Details");

                // Tạo header
                String[] headers = {
                    "Order.No", "Customer.ID", "Product.ID", 
                    "Price", "Quantity", "Subtotal",
                    "Date Order", "Time Order", "Status"
                };

                // Style cho header
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // Tạo header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // Đổ dữ liệu
                int rowNum = 1;
                for (DTO_orderDetails detail : orderDetails) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(detail.getOrderNo());
                    row.createCell(1).setCellValue(detail.getCustomerID());
                    row.createCell(2).setCellValue(detail.getProductID());
                    row.createCell(3).setCellValue(detail.getPrice().doubleValue());
                    row.createCell(4).setCellValue(detail.getQuantity());

                    // Tính subtotal
                    BigDecimal subtotal = detail.getPrice().multiply(new BigDecimal(detail.getQuantity()));
                    row.createCell(5).setCellValue(subtotal.doubleValue());

                    row.createCell(6).setCellValue(detail.getDateOrder().toString());
                    row.createCell(7).setCellValue(detail.getTimeOrder().toString());
                    row.createCell(8).setCellValue(detail.getStatus());
                }

                // Tự động điều chỉnh cột
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // Ghi file
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                    return true;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
}
    
    public List<DTO_order> searchOrders(String searchType, String keyword, String status) throws SQLException {
       List<DTO_order> orders = new ArrayList<>();

       StringBuilder sql = new StringBuilder(
           "SELECT DISTINCT o.Order_No, o.Customer_ID, o.Total_Quantity_Product, " +
           "o.Total_Price, o.Payment, o.Date_Order, o.Time_Order, " +
           "od.Status, c.Full_Name, c.Address, c.Contact " +
           "FROM Orders o " +
           "JOIN Orders_Details od ON o.Order_No = od.Order_No " +
           "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
           "WHERE od.Status = ?"
       );

       // Add search condition if keyword is not empty
       if (!keyword.isEmpty()) {
           sql.append(" AND ");
           switch (searchType) {
               case "Order.No":
                   sql.append("o.Order_No LIKE ?");
                   break;
               case "Customer.ID":
                   sql.append("o.Customer_ID LIKE ?");
                   break;
               case "Date Order":
                   if (keyword.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                       sql.append("CONVERT(varchar, o.Date_Order, 103) = ?");
                   } else if (keyword.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                       sql.append("o.Date_Order = ?");
                   } else {
                       sql.append("CONVERT(varchar, o.Date_Order, 103) LIKE ?");
                   }
                   break;
               default:
                   sql.append("o.Order_No LIKE ?");
           }
       }

       // Sắp xếp theo thời gian đặt hàng
       sql.append(" ORDER BY o.Date_Order DESC, o.Time_Order DESC");

       try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

           stmt.setString(1, status);

           if (!keyword.isEmpty()) {
               if (searchType.equals("Date Order")) {
                   if (keyword.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                       stmt.setString(2, keyword);
                   } else if (keyword.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                       stmt.setDate(2, java.sql.Date.valueOf(keyword));
                   } else {
                       stmt.setString(2, "%" + keyword + "%");
                   }
               } else {
                   stmt.setString(2, "%" + keyword + "%");
               }
           }

           try (ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   DTO_order order = new DTO_order();
                   order.setOrderNo(rs.getString("Order_No"));
                   order.setCustomerID(rs.getString("Customer_ID"));
                   order.setTotalQuantityProduct(rs.getInt("Total_Quantity_Product"));
                   order.setTotalPrice(rs.getBigDecimal("Total_Price"));
                   order.setPayment(rs.getString("Payment"));
                   order.setDateOrder(rs.getDate("Date_Order").toLocalDate());
                   order.setTimeOrder(rs.getTime("Time_Order").toLocalTime());
                   order.setCustomerName(rs.getString("Full_Name"));
                   order.setAddress(rs.getString("Address"));
                   order.setContact(rs.getString("Contact"));
                   order.setStatus(rs.getString("Status"));

                   orders.add(order);
               }
           }
       }
       return orders;
   }

}