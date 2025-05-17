package com.Admin.importation.DAO;

import com.Admin.importation.DTO.DTO_productStock;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.Admin.importation.DTO.DTOBill_Imported;
import com.Admin.importation.DTO.DTOBill_ImportedDetails;
import com.Admin.importation.DTO.DTOBill_ImportedFullDetails;
import com.ComponentandDatabase.Components.CustomDialog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DAOImport {

    public ArrayList<DTO_productStock> showDataStock() {
        ArrayList<DTO_productStock> productList = new ArrayList<>();

        String query = "SELECT p.Product_ID, p.Product_Name, p.Price, " +
                       "c.Category_ID, s.Sup_ID, ps.Quantity_Stock " +
                       "FROM Product_Stock ps " +
                       "JOIN Product p ON ps.Product_ID = p.Product_ID " +
                       "JOIN Category c ON p.Category_ID = c.Category_ID " +
                       "JOIN Supplier s ON c.Sup_ID = s.Sup_ID";

        try (
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ) {
            while (rs.next()) {
                String productID = rs.getString("Product_ID");
                String productName = rs.getString("Product_Name");
                BigDecimal price = rs.getBigDecimal("Price");
                String categoryID = rs.getString("Category_ID");
                String brandID = rs.getString("Sup_ID"); // Sup_ID dùng làm brandID ở DTO
                int quantityInStock = rs.getInt("Quantity_Stock");

                DTO_productStock product = new DTO_productStock(productID, productName, price, categoryID, brandID, quantityInStock);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }
    
    public boolean insertBillImported(DTOBill_Imported bill) {
        String query = "INSERT INTO Bill_Imported (Invoice_No, Admin_ID, Total_Product, Total_Price) " +
                       "VALUES (?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, bill.getInvoiceNo());
            pstmt.setString(2, bill.getAdminId());
            pstmt.setInt(3, bill.getTotalProduct());
            pstmt.setBigDecimal(4, bill.getTotalPrice());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertBillImportedDetails(DTOBill_ImportedDetails detail) {
        String query = "INSERT INTO Bill_Imported_Details " +
                       "(Invoice_No, Admin_ID, Product_ID, Quantity, Unit_Price, Total_Price, Date_Imported, Time_Imported) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, detail.getInvoiceNo());
            pstmt.setString(2, detail.getAdminId());
            pstmt.setString(3, detail.getProductId());
            pstmt.setInt(4, detail.getQuantity());
            pstmt.setBigDecimal(5, detail.getUnitPrice());
            pstmt.setBigDecimal(6, detail.getTotalPrice());
            pstmt.setDate(7, java.sql.Date.valueOf(detail.getDateImported()));
            pstmt.setTime(8, java.sql.Time.valueOf(detail.getTimeImported()));

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    
    
   public List<DTOBill_ImportedFullDetails> getAllBillFullDetails() throws SQLException {
     List<DTOBill_ImportedFullDetails> result = new ArrayList<>();
    
     String sql = "SELECT "
               + "bid.Invoice_No, bid.Admin_ID, a.Admin_Name, "
               + "bid.Product_ID, p.Product_Name, p.Category_ID, c.Sup_ID, "
               + "bid.Quantity, bid.Unit_Price, bid.Total_Price, "
               + "bid.Date_Imported, bid.Time_Imported "
               + "FROM Bill_Imported_Details bid "
               + "JOIN Product p ON bid.Product_ID = p.Product_ID "
               + "JOIN Category c ON p.Category_ID = c.Category_ID "
               + "JOIN Admin a ON bid.Admin_ID = a.Admin_ID";
    
    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {
        
        while (rs.next()) {
            DTOBill_ImportedFullDetails detail = new DTOBill_ImportedFullDetails(
                rs.getString("Invoice_No"),
                rs.getString("Admin_ID"),
                rs.getString("Admin_Name"),
                rs.getString("Product_ID"),
                rs.getString("Product_Name"),
                rs.getString("Category_ID"),
                rs.getString("Sup_ID"),
                rs.getInt("Quantity"),
                rs.getBigDecimal("Unit_Price"),
                rs.getBigDecimal("Total_Price"),
                rs.getDate("Date_Imported").toLocalDate(),
                rs.getTime("Time_Imported").toLocalTime()
            );
            result.add(detail);
        }
    }
    return result;
}
   
    public List<DTOBill_ImportedFullDetails> searchBillDetails(String searchType, String keyword) throws SQLException {
        List<DTOBill_ImportedFullDetails> result = new ArrayList<>();

        String baseSql = "SELECT bid.Invoice_No, bid.Admin_ID, a.Admin_Name, "
                       + "bid.Product_ID, p.Product_Name, p.Category_ID, c.Sup_ID, "
                       + "bid.Quantity, bid.Unit_Price, bid.Total_Price, "
                       + "bid.Date_Imported, bid.Time_Imported "
                       + "FROM Bill_Imported_Details bid "
                       + "JOIN Product p ON bid.Product_ID = p.Product_ID "
                       + "JOIN Category c ON p.Category_ID = c.Category_ID "
                       + "JOIN Admin a ON bid.Admin_ID = a.Admin_ID ";

        String whereClause = "";
        String orderClause = " ORDER BY bid.Date_Imported DESC, bid.Time_Imported DESC";

        // Nếu tìm theo ngày thì convert keyword về định dạng yyyy-MM-dd
        String formattedKeyword = keyword;
        if (searchType.equals("Date")) {
            try {
                java.text.SimpleDateFormat inputFormat1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
                java.text.SimpleDateFormat inputFormat2 = new java.text.SimpleDateFormat("dd-MM-yyyy");
                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");

                Date date = null;
                try {
                    date = inputFormat1.parse(keyword);
                } catch (Exception e1) {
                    date = inputFormat2.parse(keyword);
                }

                if (date != null) {
                    formattedKeyword = outputFormat.format(date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Build where clause
        switch (searchType) {
            case "Invoice.No":
                whereClause = "WHERE bid.Invoice_No LIKE ? ";
                break;
            case "Admin.ID":
                whereClause = "WHERE bid.Admin_ID LIKE ? ";
                break;
            case "Product.ID":
                whereClause = "WHERE bid.Product_ID LIKE ? ";
                break;
            case "Date":
                whereClause = "WHERE CONVERT(VARCHAR, bid.Date_Imported, 23) LIKE ? "; // 'yyyy-MM-dd'
                break;
            default:
                whereClause = "WHERE bid.Invoice_No LIKE ? OR bid.Admin_ID LIKE ? OR bid.Product_ID LIKE ? OR CONVERT(VARCHAR, bid.Date_Imported, 23) LIKE ? ";
        }

        String sql = baseSql + whereClause + orderClause;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            if (searchType.equals("Invoice.No") || searchType.equals("Admin.ID") || 
                searchType.equals("Product.ID") || searchType.equals("Date")) {
                pst.setString(1, "%" + formattedKeyword + "%");
            } else {
                pst.setString(1, "%" + keyword + "%");
                pst.setString(2, "%" + keyword + "%");
                pst.setString(3, "%" + keyword + "%");
                pst.setString(4, "%" + formattedKeyword + "%");
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DTOBill_ImportedFullDetails detail = new DTOBill_ImportedFullDetails(
                    rs.getString("Invoice_No"),
                    rs.getString("Admin_ID"),
                    rs.getString("Admin_Name"),
                    rs.getString("Product_ID"),
                    rs.getString("Product_Name"),
                    rs.getString("Category_ID"),
                    rs.getString("Sup_ID"),
                    rs.getInt("Quantity"),
                    rs.getBigDecimal("Unit_Price"),
                    rs.getBigDecimal("Total_Price"),
                    rs.getDate("Date_Imported").toLocalDate(),
                    rs.getTime("Time_Imported").toLocalTime()
                );
                result.add(detail);
            }
        }
        return result;
    }

   public List<DTOBill_Imported> getAllBillImported() throws SQLException {
        List<DTOBill_Imported> result = new ArrayList<>();
        String sql = "SELECT * FROM Bill_Imported";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DTOBill_Imported bill = new DTOBill_Imported(
                    rs.getString("Invoice_No"),
                    rs.getString("Admin_ID"),
                    rs.getInt("Total_Product"),
                    rs.getBigDecimal("Total_Price")
                );
                result.add(bill);
            }
        }
        return result;
    }
   

    public void createBillImportedSheet(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Bill_Imported");

        // Tạo header
        String[] headers = {"Invoice No", "Admin ID", "Total Product", "Total Price"};
        createHeaderRow(workbook, sheet, headers);

        // Lấy dữ liệu từ database
        List<DTOBill_Imported> bills = getAllBillImported();

        // Định dạng dữ liệu
        CellStyle dataStyle = createDataStyle(workbook);
        CellStyle dateStyle = createDateStyle(workbook, dataStyle);
        CellStyle currencyStyle = createCurrencyStyle(workbook, dataStyle);

        // Thêm dữ liệu
        int rowNum = 1;
        for (DTOBill_Imported bill : bills) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(bill.getInvoiceNo());
            row.createCell(1).setCellValue(bill.getAdminId());
            row.createCell(2).setCellValue(bill.getTotalProduct());

            Cell totalPriceCell = row.createCell(3);
            totalPriceCell.setCellValue(bill.getTotalPrice().doubleValue());
            totalPriceCell.setCellStyle(currencyStyle);


            // Áp dụng style cho các ô
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

       public void createBillImportedDetailsSheet(Workbook workbook, List<DTOBill_ImportedFullDetails> billDetails) {
            if (billDetails == null || billDetails.isEmpty()) {
                System.err.println("Error: billDetails is null or empty!");
                return;
            }

            Sheet sheet = workbook.createSheet("Bill_Imported_Details");

            // Tạo header
            String[] headers = {
                "Invoice No", "Admin ID", "Admin Name", "Product ID", 
                "Product Name", "Category ID", "Brand ID", "Quantity",
                "Unit Price", "Total Price", "Date Imported", "Time Imported"
            };
            createHeaderRow(workbook, sheet, headers);

            // Tạo các style
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook, dataStyle);
            CellStyle dateStyle = createDateStyle(workbook, dataStyle);

            // Style cho thời gian (dùng dataStyle thông thường vì Excel không có định dạng time AM/PM mặc định)
            CellStyle timeStyle = createDataStyle(workbook); 

            // Định dạng ngày giờ
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

            // Thêm dữ liệu
            int rowNum = 1;
            for (DTOBill_ImportedFullDetails detail : billDetails) {
                Row row = sheet.createRow(rowNum++);

                // Invoice No
                createCell(row, 0, detail.getInvoiceNo(), dataStyle);

                // Admin ID
                createCell(row, 1, detail.getAdminId(), dataStyle);

                // Admin Name
                createCell(row, 2, detail.getAdminName(), dataStyle);

                // Product ID
                createCell(row, 3, detail.getProductId(), dataStyle);

                // Product Name
                createCell(row, 4, detail.getProductName(), dataStyle);

                // Category ID
                createCell(row, 5, detail.getCategoryId(), dataStyle);

                // Brand ID
                createCell(row, 6, detail.getBrandId(), dataStyle);

                // Quantity
                createCell(row, 7, detail.getQuantity(), dataStyle);

                // Unit Price (định dạng tiền tệ)
                createCell(row, 8, detail.getUnitPrice(), currencyStyle);

                // Total Price (định dạng tiền tệ)
                createCell(row, 9, detail.getTotalPrice(), currencyStyle);

                // Date Imported (định dạng ngày)
                if (detail.getDateImported() != null) {
                    createCell(row, 10, detail.getDateImported().format(dateFormatter), dateStyle);
                } else {
                    createCell(row, 10, "N/A", dataStyle);
                }

                // Time Imported (định dạng giờ)
                if (detail.getTimeImported() != null) {
                    createCell(row, 11, detail.getTimeImported().format(timeFormatter), timeStyle);
                } else {
                    createCell(row, 11, "N/A", dataStyle);
                }
            }

            // Tự động điều chỉnh độ rộng cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // Phương thức hỗ trợ tạo cell
        private void createCell(Row row, int column, Object value, CellStyle style) {
            Cell cell = row.createCell(column);

            if (value == null) {
                cell.setCellValue("N/A");
            } else if (value instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) value).doubleValue());
            } else if (value instanceof Number) {
                cell.setCellValue(((Number) value).doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }

            cell.setCellStyle(style);
        }

    // Các phương thức hỗ trợ tạo style
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createDateStyle(Workbook workbook, CellStyle baseStyle) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(baseStyle);
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
        return style;
    }


    private CellStyle createCurrencyStyle(Workbook workbook, CellStyle baseStyle) {
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(baseStyle);
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        return style;
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
    
   
          
}
