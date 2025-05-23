package com.Admin.stock.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection; // <-- Import đúng chỗ connect CSDL của bạn
import com.Admin.stock.DTO.DTOProduct_Stock;
import com.ComponentandDatabase.Components.CustomDialog;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class DAOProduct_Stock {

    // Hàm lấy tất cả dữ liệu Product + Quantity_Stock lên bảng
    public void getAllProductStock(JTable table) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.connect(); // kết nối CSDL
            
         String sql = "SELECT " +
             "p.Product_ID, " +
             "p.Product_Name, " +
             "p.Price, " +
             "ps.Quantity_Stock, " +
             "c.Category_ID, " +
             "c.Category_Name, " +
             "s.Sup_ID, " +
             "s.Sup_Name, " +
             "s.Contact " +
             "FROM Product p " +
             "INNER JOIN Product_Stock ps ON p.Product_ID = ps.Product_ID " +
             "INNER JOIN Category c ON p.Category_ID = c.Category_ID " +
             "INNER JOIN Supplier s ON c.Sup_ID = s.Sup_ID";

                         
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // clear dữ liệu cũ
            
            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getString("Product_ID"),
                    rs.getString("Product_Name"),
                    rs.getBigDecimal("Price"),
                    rs.getInt("Quantity_Stock"), // Quantity ở đây
                    rs.getString("Category_ID"),
                    rs.getString("Category_Name"),
                    rs.getString("Sup_ID"),
                    rs.getString("Sup_Name"),
                    rs.getString("Contact")
                };
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    public DTOProduct_Stock getProductDetailByID(String productID) {
        DTOProduct_Stock dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT * FROM Product p " +
                         "INNER JOIN Product_Stock ps ON p.Product_ID = ps.Product_ID " +
                         "WHERE p.Product_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, productID);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new DTOProduct_Stock();
                dto.setProductID(rs.getString("Product_ID"));
                dto.setProductName(rs.getString("Product_Name"));
                dto.setCpu(rs.getString("CPU"));
                dto.setRam(rs.getString("Ram"));
                dto.setGraphicsCard(rs.getString("Graphics_Card"));
                dto.setPrice(rs.getBigDecimal("Price"));
                dto.setWarrantyPeriod(rs.getString("Warranty_Period"));
                dto.setQuantity(rs.getInt("Quantity_Stock"));
                dto.setOperatingSystem(rs.getString("Operating_System"));
                dto.setCategoryID(rs.getString("Category_ID"));
                dto.setImage(rs.getString("Image")); // nếu có cột này
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }

        return dto;
    }

    
      public void cleanAllProduct_Stock() {
        String sql = "DELETE FROM Product_Stock";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.executeUpdate();
            CustomDialog.showSuccess("All data cleaned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void importExcel(File excelFile) {
     if (excelFile == null || !excelFile.exists()) {
         CustomDialog.showError("The selected file does not exist!");
         return;
     }

     try (FileInputStream fis = new FileInputStream(excelFile)) {
         Workbook workbook;
         if (excelFile.getName().toLowerCase().endsWith(".xlsx")) {
             workbook = new XSSFWorkbook(fis);
         } else if (excelFile.getName().toLowerCase().endsWith(".xls")) {
             workbook = new HSSFWorkbook(fis);
         } else {
             CustomDialog.showError("Unsupported file format. Please use .xls or .xlsx files.");
             return;
         }

         Sheet sheet = workbook.getSheetAt(0);
         Iterator<Row> rowIterator = sheet.iterator();

         // Skip header row
         if (rowIterator.hasNext()) {
             rowIterator.next();
         }

         Connection conn = DatabaseConnection.connect();
         String sql = "INSERT INTO Product_Stock (Product_ID, Quantity_Stock) VALUES (?, ?)";
         PreparedStatement pstmt = conn.prepareStatement(sql);

         while (rowIterator.hasNext()) {
             Row row = rowIterator.next();
             Cell cellID = row.getCell(0);
             Cell cellQty = row.getCell(1);

             if (cellID == null || cellQty == null) continue;

             cellID.setCellType(CellType.STRING);
             cellQty.setCellType(CellType.NUMERIC);

             String productID = cellID.getStringCellValue();
             int quantity = (int) cellQty.getNumericCellValue();

             pstmt.setString(1, productID);
             pstmt.setInt(2, quantity);
             pstmt.addBatch();
         }

         pstmt.executeBatch();
         pstmt.close();
         conn.close();
         workbook.close();

         CustomDialog.showSuccess("File imported successfully!");

     } catch (Exception ex) {
         ex.printStackTrace();
         CustomDialog.showError("An error occurred while importing the file!");
     }
    }
    
     public void exportFile(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");

        // Lọc chỉ cho phép lưu file .xlsx
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Đảm bảo file có đuôi .xlsx
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                selectedFile = new File(filePath + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("C Data");

                TableModel model = table.getModel();
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col));
                }

                for (int row = 0; row < model.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object value = model.getValueAt(row, col);
                        Cell cell = excelRow.createCell(col);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }

                for (int col = 0; col < model.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                try (FileOutputStream fileOut = new FileOutputStream(selectedFile)) {
                    workbook.write(fileOut);
                    CustomDialog.showSuccess("Export successfuly!");
                }

            } catch (IOException e) {
                e.printStackTrace();
                CustomDialog.showError("Error exporting to Excel: " + e.getMessage());
            }
        }
    }
    
     public void searchProduct(String keyword, String selected, DefaultTableModel model) {
        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

        String sql = "SELECT " +
            "p.Product_ID, " +
            "p.Product_Name, " +
            "p.Price, " +
            "ps.Quantity_Stock, " +
            "c.Category_ID, " +
            "c.Category_Name, " +
            "s.Sup_ID, " +
            "s.Sup_Name, " +
            "s.Contact " +
            "FROM Product p " +
            "INNER JOIN Product_Stock ps ON p.Product_ID = ps.Product_ID " +
            "INNER JOIN Category c ON p.Category_ID = c.Category_ID " +
            "INNER JOIN Supplier s ON c.Sup_ID = s.Sup_ID ";

        boolean needParameter = false;

        switch (selected) {
            case "Product.ID" -> {
                sql += "WHERE p.Product_ID = ?";
                needParameter = true;
            }
            case "Product Name" -> {
                sql += "WHERE p.Product_Name LIKE ?";
                needParameter = true;
            }
            case "Brand.ID" -> {
                sql += "WHERE s.Sup_ID LIKE ?";
                needParameter = true;
            }
        }

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (needParameter) {
                if (selected.equals("Product.ID")) {
                    stmt.setString(1, keyword);
                } else {
                    stmt.setString(1, "%" + keyword + "%");
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Quantity_Stock"),
                        rs.getString("Category_ID"),
                        rs.getString("Category_Name"),
                        rs.getString("Sup_ID"),
                        rs.getString("Sup_Name"),
                        rs.getString("Contact")
                    };
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Lỗi khi tìm kiếm sản phẩm!");
        }
    }


}
