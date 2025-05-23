package com.Admin.export.DAO;

import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.Admin.export.DTO.DTO_BillExported;
import com.Admin.export.DTO.DTO_BillExportedDetail;
import com.Admin.export.DTO.DTO_BillExport;
import com.Admin.product.DTO.DTOProduct;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_ExportBill {
    public DTOProfile_cus getCustomerInfoByID(String customerID) throws SQLException {
        DTOProfile_cus customer = null;
        String sql = "SELECT Customer_ID, Full_Name, Address, Email, Contact "
                   + "FROM Customer WHERE Customer_ID = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = new DTOProfile_cus();
                    customer.setCustomerID(rs.getString("Customer_ID"));
                    customer.setFullName(rs.getString("Full_Name"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setContact(rs.getString("Contact"));
                    customer.setEmail(rs.getString("Email"));
                    // Các trường khác để null hoặc giá trị mặc định
                    customer.setGender(null);
                    customer.setDateOfBirth(null);
                    
                }
            }
        }
        return customer;
    }
    
     public String getWarranty(String productID) throws SQLException {
        String sql = "SELECT Warranty_Period FROM Product WHERE Product_ID = ?"; // Use Product table instead of Customer

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Warranty_Period"); // Return the warranty period
                }
            }
        }
        return "No warranty found"; // Default response if no data is found
    }

    
    
    
    
    // Phương thức thêm hóa đơn xuất vào database
    public boolean insertBillExported(DTO_BillExported bill) {
        String sql = "INSERT INTO Bill_Exported (Invoice_No, Admin_ID, Customer_ID, Total_Product) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConnection.connect();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getInvoiceNo());
            pstmt.setString(2, bill.getAdminId());
            pstmt.setString(3, bill.getCustomerId());
            pstmt.setInt(4, bill.getTotalProduct());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting bill exported: " + e.getMessage());
            return false;
        }
    }
    
   public boolean insertBillExportedDetail(DTO_BillExportedDetail detail, List<String> imeiList) {
        // Kiểm tra dữ liệu đầu vào
        if (detail == null) {
            System.err.println("Detail object cannot be null");
            return false;
        }

        if (imeiList == null || imeiList.isEmpty()) {
            System.err.println("IMEI list cannot be empty");
            return false;
        }

        if (detail.getQuantity() <= 0) {
            System.err.println("Quantity must be greater than 0");
            return false;
        }

        // Kiểm tra số lượng IMEI phải bằng Quantity
        if (imeiList.size() != detail.getQuantity()) {
            System.err.println(String.format("Error: Number of IMEIs (%d) does not match Quantity (%d)", 
                imeiList.size(), detail.getQuantity()));
            return false;
        }

        // Kiểm tra các IMEI phải là duy nhất
        Set<String> uniqueImeis = new HashSet<>(imeiList);
        if (uniqueImeis.size() != imeiList.size()) {
            System.err.println("Error: Duplicate IMEI numbers found");
            return false;
        }

        String sql = "INSERT INTO Bill_Exported_Details (Invoice_No, Admin_ID, Customer_ID, Product_ID, IMEI_No, " +
                     "Unit_Price, Quantity, Discount_Values, Total_Price_Before, Total_Price_After, " +
                     "Date_Exported, Time_Exported) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 1, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Bắt đầu transaction

            // Tính toán giá trị cho mỗi dòng (chia đều discount và total)
            BigDecimal unitPrice = detail.getUnitPrice();
            BigDecimal discountPerItem = detail.getDiscountValues().divide(
                new BigDecimal(detail.getQuantity()), 2, RoundingMode.HALF_UP);
            BigDecimal totalBeforePerItem = detail.getTotalPriceBefore().divide(
                new BigDecimal(detail.getQuantity()), 2, RoundingMode.HALF_UP);
            BigDecimal totalAfterPerItem = detail.getTotalPriceAfter().divide(
                new BigDecimal(detail.getQuantity()), 2, RoundingMode.HALF_UP);

            for (String imei : imeiList) {
                pstmt.setString(1, detail.getInvoiceNo());
                pstmt.setString(2, detail.getAdminId());
                pstmt.setString(3, detail.getCustomerId());
                pstmt.setString(4, detail.getProductId());
                pstmt.setString(5, imei.trim());
                pstmt.setBigDecimal(6, unitPrice);
                pstmt.setBigDecimal(7, discountPerItem);
                pstmt.setBigDecimal(8, totalBeforePerItem);
                pstmt.setBigDecimal(9, totalAfterPerItem);
                pstmt.setDate(10, detail.getDateExported());
                pstmt.setTime(11, detail.getTimeExported());

                pstmt.addBatch(); // Thêm vào batch
            }

            int[] results = pstmt.executeBatch(); // Thực thi batch insert
            conn.commit(); // Commit transaction

            // Kiểm tra tất cả các dòng đều được insert thành công
            for (int result : results) {
                if (result <= 0) {
                    conn.rollback();
                    return false;
                }
            }
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error inserting bill exported details: " + e.getMessage());
            return false;
        } catch (ArithmeticException e) {
            System.err.println("Calculation error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }

     public boolean updateProductQuantity(DTO_BillExportedDetail detail) throws SQLException {
        String sql = "UPDATE Product SET Quantity = Quantity - ? WHERE Product_ID = ? AND Quantity >= ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detail.getQuantity());
            pstmt.setString(2, detail.getProductId());
            pstmt.setInt(3, detail.getQuantity()); // Điều kiện đảm bảo đủ số lượng

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    public List<DTO_BillExportedDetail> getAllBillDetails() throws SQLException {
        List<DTO_BillExportedDetail> billDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Bill_Exported_Details");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                billDetails.add(new DTO_BillExportedDetail(
                    rs.getString("Invoice_No"),
                    rs.getString("Admin_ID"),
                    rs.getString("Customer_ID"),
                    rs.getString("Product_ID"),
                    rs.getString("IMEI_No"),
                    rs.getBigDecimal("Unit_Price"),
                    rs.getInt("Quantity"),
                    rs.getBigDecimal("Discount_Values"),
                    rs.getBigDecimal("Total_Price_Before"),
                    rs.getBigDecimal("Total_Price_After"),
                    rs.getDate("Date_Exported"),
                    rs.getTime("Time_Exported")
                ));
            }
        }
        return billDetails;
    }
    
      public List<DTO_BillExport> getAllBillExported() throws SQLException {
        List<DTO_BillExport> billList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("Select * FROM Bill_Exported");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                billList.add(new DTO_BillExport(
                    rs.getString("Invoice_No"),
                    rs.getString("Admin_ID"),
                    rs.getString("Customer_ID"),
                    rs.getInt("Total_Product"),
                    rs.getString("Description") 
                ));
            }
        }
        return billList;
    }

    
    // Xuất dữ liệu ra file Excel
    public boolean exportToExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo sheet cho Bill_Exported
            createBillExportedSheet(workbook);
            
            // Tạo sheet cho Bill_Exported_Details
            createBillExportedDetailsSheet(workbook);
            
            // Ghi file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                return true;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   private void createBillExportedSheet(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Bill_Exported");

        // Tạo header
        String[] headers = {"Invoice No", "Admin ID", "Customer ID", "Total Product", "Description"};
        createHeaderRow(workbook, sheet, headers);

        // Lấy dữ liệu từ database
        List<DTO_BillExport> bills = getAllBillExported();

        // Định dạng dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Thêm dữ liệu
        int rowNum = 1;
        for (DTO_BillExport bill : bills) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(bill.getInvoiceNo());
            row.createCell(1).setCellValue(bill.getAdminId());
            row.createCell(2).setCellValue(bill.getCustomerId() != null ? bill.getCustomerId() : "N/A"); // Tránh lỗi null
            row.createCell(3).setCellValue(bill.getTotalProduct());
            row.createCell(4).setCellValue(bill.getDescription() != null ? bill.getDescription() : "No Description"); // Thêm mô tả

            // Áp dụng style cho các ô
            for (int i = 0; i < headers.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createBillExportedDetailsSheet(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Bill_Exported_Details");
        
        // Tạo header
        String[] headers = {
            "Invoice No", "Admin ID", "Customer ID", "Product ID", 
            "IMEI No", "Unit Price", "Quantity", "Discount Values", 
            "Total Price Before", "Total Price After", "Date Exported", "Time Exported"
        };
        createHeaderRow(workbook, sheet, headers);
        
        // Lấy dữ liệu từ database
        List<DTO_BillExportedDetail> details = getAllBillDetails();
        
        // Định dạng dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        
        // Định dạng ngày tháng
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(dataStyle);
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
        
        // Định dạng thời gian
        CellStyle timeStyle = workbook.createCellStyle();
        timeStyle.cloneStyleFrom(dataStyle);
        timeStyle.setDataFormat(workbook.createDataFormat().getFormat("HH:mm:ss"));
        
        // Định dạng tiền tệ
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(dataStyle);
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        
        // Thêm dữ liệu
        int rowNum = 1;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        for (DTO_BillExportedDetail detail : details) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(detail.getInvoiceNo());
            row.createCell(1).setCellValue(detail.getAdminId());
            row.createCell(2).setCellValue(detail.getCustomerId());
            row.createCell(3).setCellValue(detail.getProductId());
            row.createCell(4).setCellValue(detail.getImeiNo());
            
            // Định dạng số tiền
            Cell unitPriceCell = row.createCell(5);
            unitPriceCell.setCellValue(detail.getUnitPrice().doubleValue());
            unitPriceCell.setCellStyle(currencyStyle);
            
            row.createCell(6).setCellValue(detail.getQuantity());
            
            Cell discountCell = row.createCell(7);
            discountCell.setCellValue(detail.getDiscountValues().doubleValue());
            discountCell.setCellStyle(currencyStyle);
            
            Cell totalBeforeCell = row.createCell(8);
            totalBeforeCell.setCellValue(detail.getTotalPriceBefore().doubleValue());
            totalBeforeCell.setCellStyle(currencyStyle);
            
            Cell totalAfterCell = row.createCell(9);
            totalAfterCell.setCellValue(detail.getTotalPriceAfter().doubleValue());
            totalAfterCell.setCellStyle(currencyStyle);
            
            // Định dạng ngày tháng
            if (detail.getDateExported() != null) {
                Cell dateCell = row.createCell(10);
                dateCell.setCellValue(detail.getDateExported());
                dateCell.setCellStyle(dateStyle);
            }
            
            // Định dạng thời gian
            if (detail.getTimeExported() != null) {
                Cell timeCell = row.createCell(11);
                timeCell.setCellValue(timeFormat.format(detail.getTimeExported()));
                timeCell.setCellStyle(timeStyle);
            }
            
            // Áp dụng style cho các ô còn lại
            for (int i = 0; i < headers.length; i++) {
                if (row.getCell(i) != null && row.getCell(i).getCellStyle() == null) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
        }
        
        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
      
    
    
    
    public List<DTO_BillExportedDetail> searchBillDetails(String searchType, String searchKeyword) {
            List<DTO_BillExportedDetail> results = new ArrayList<>();
            String sql = "SELECT * FROM Bill_Exported_Details WHERE ";

            // Xác định cột tìm kiếm dựa trên combobox
            switch(searchType) {
                case "Invoice.No":
                    sql += "Invoice_No LIKE ?";
                    break;
                case "Admin.ID":
                    sql += "Admin_ID LIKE ?";
                    break;
                case "Customer.ID":
                    sql += "Customer_ID LIKE ?";
                    break;
                case "IMEI.No":
                    sql += "IMEI_No LIKE ?";
                    break;
                case "Date":
                    sql += "Date_Exported LIKE ?";
                    break;
                default:
                    sql += "Invoice_No LIKE ?";
            }

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, "%" + searchKeyword + "%");

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        DTO_BillExportedDetail detail = new DTO_BillExportedDetail(
                            rs.getString("Invoice_No"),
                            rs.getString("Admin_ID"),
                            rs.getString("Customer_ID"),
                            rs.getString("Product_ID"),
                            rs.getString("IMEI_No"),
                            rs.getBigDecimal("Unit_Price"),
                            rs.getInt("Quantity"),
                            rs.getBigDecimal("Discount_Values"),
                            rs.getBigDecimal("Total_Price_Before"),
                            rs.getBigDecimal("Total_Price_After"),
                            rs.getDate("Date_Exported"),
                            rs.getTime("Time_Exported")
                        );
                        results.add(detail);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error searching bill details: " + e.getMessage());
            }

            return results;
        }


}