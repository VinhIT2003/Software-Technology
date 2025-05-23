package com.Admin.product.DAO;

import com.Admin.product.DTO.DTOIMEI;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.ComponentandDatabase.Components.CustomDialog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

public class DAOIMEI {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.connect();
    }
    
    /**
     * Import data from Excel to database
     * @param excelFile Excel file to import
     */
    public void importIMEIFromExcel(File excelFile) {
        try {
            // Read data from Excel
            List<DTOIMEI> imeiList = readExcelFile(excelFile);
            if (imeiList.isEmpty()) {
                CustomDialog.showError("Excel file contains no valid data!");
                return;
            }
            
            // Save to database
            try (Connection conn = getConnection()) {
                int successCount = 0;
                int duplicateCount = 0;
                
                String insertSQL = "INSERT INTO IMei_Product (IMei_No, Product_ID, State) VALUES (?, ?, ?)";
                String checkSQL = "SELECT 1 FROM IMei_Product WHERE IMei_No = ?";
                
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
                     PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
                    
                    conn.setAutoCommit(false);
                    
                    for (DTOIMEI imei : imeiList) {
                        // Check if IMEI already exists
                        checkStmt.setString(1, imei.getImeiNo());
                        try (ResultSet rs = checkStmt.executeQuery()) {
                            if (rs.next()) {
                                duplicateCount++;
                                continue;
                            }
                        }
                        
                        // Add new IMEI
                        insertStmt.setString(1, imei.getImeiNo());
                        insertStmt.setString(2, imei.getProductID());
                        insertStmt.setString(3, imei.getState());
                        insertStmt.executeUpdate();
                        successCount++;
                    }
                    
                    conn.commit();
                    
                    // Show results
                    if (successCount > 0) {
                        CustomDialog.showSuccess("Successfully imported " + successCount + " IMEIs");
                    }
                    if (duplicateCount > 0) {
                        CustomDialog.showError(duplicateCount + " duplicate IMEIs were skipped");
                    }
                }
            } catch (SQLException e) {
                CustomDialog.showError("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            CustomDialog.showError("File reading error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Read data from Excel file
     */
    private List<DTOIMEI> readExcelFile(File excelFile) throws Exception {
        List<DTOIMEI> imeiList = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                // Skip header row and empty rows
                if (row.getRowNum() == 0 || row.getCell(0) == null) continue;
                
                String imeiNo = getCellValueAsString(row.getCell(0));
                String productID = getCellValueAsString(row.getCell(1));
                String state = getCellValueAsString(row.getCell(2));
                
                if (!imeiNo.isEmpty()) {
                    imeiList.add(new DTOIMEI(imeiNo, productID, state));
                }
            }
        }
        
        return imeiList;
    }
    
    /**
     * Convert Excel cell value to String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC:
                double num = cell.getNumericCellValue();
                return num == (long) num ? String.valueOf((long) num) : String.valueOf(num);
            default: return "";
        }
    }
    
   public void uploadIMEIDataToTable(JTable tableIMEI) {
        DefaultTableModel model = (DefaultTableModel) tableIMEI.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        String query = "SELECT i.IMei_No, i.Product_ID, p.Product_Name, c.Category_ID, c.Category_Name, c.Sup_ID, i.State " +
               "FROM IMei_Product i " +
               "JOIN Product p ON i.Product_ID = p.Product_ID " +
               "JOIN Category c ON p.Category_ID = c.Category_ID";


        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String imeiNo = rs.getString("IMei_No");
                String productId = rs.getString("Product_ID");
                String productName = rs.getString("Product_Name");
                String categoryId = rs.getString("Category_ID");
                String brandId = rs.getString("Sup_ID");
                String state = rs.getString("State");

                model.addRow(new Object[]{imeiNo, productId, productName, categoryId, brandId, state});
            }

        } catch (SQLException e) {
            CustomDialog.showError("Lỗi khi tải dữ liệu IMEI: " + e.getMessage());
            e.printStackTrace();
        }
}
    public void updateIMEIStateInDatabase(String imeiNo, String newState) {
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "UPDATE IMei_Product SET State = ? WHERE IMei_No = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newState);
            pst.setString(2, imeiNo);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanAllIMEIData() {
        String sql = "DELETE FROM IMei_Product";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.executeUpdate();
            CustomDialog.showSuccess("All data cleaned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
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
   
   public void searchIMEI(JTable table, String selectedColumn, String keyword, String stateFilter) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        StringBuilder query = new StringBuilder(
            "SELECT i.IMei_No, i.Product_ID, p.Product_Name, c.Category_ID, c.Category_Name, c.Sup_ID, i.State " +
            "FROM IMei_Product i " +
            "JOIN Product p ON i.Product_ID = p.Product_ID " +
            "JOIN Category c ON p.Category_ID = c.Category_ID "
        );

        // Trường hợp 1: tìm theo keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append("WHERE ");

            if ("IMEI.No".equals(selectedColumn)) {
                query.append("i.IMei_No LIKE ?");
            } else if ("Product.ID".equals(selectedColumn)) {
                query.append("i.Product_ID = ?");
            }
        }
        // Trường hợp 2: không nhập gì mà chọn State
        else if (stateFilter != null && !stateFilter.isEmpty()) {
            query.append("WHERE i.State = ?");
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            // Gán giá trị vào ? tương ứng
            if (keyword != null && !keyword.trim().isEmpty()) {
                stmt.setString(1, "%" + keyword.trim() + "%");
            } else if (stateFilter != null && !stateFilter.isEmpty()) {
                stmt.setString(1, stateFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String imeiNo = rs.getString("IMei_No");
                    String productId = rs.getString("Product_ID");
                    String productName = rs.getString("Product_Name");
                    String categoryId = rs.getString("Category_ID");
                    String brandId = rs.getString("Sup_ID");
                    String state = rs.getString("State");

                    model.addRow(new Object[]{imeiNo, productId, productName, categoryId, brandId, state});
                }
            }
        } catch (SQLException e) {
            CustomDialog.showError("Lỗi tìm kiếm IMEI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}