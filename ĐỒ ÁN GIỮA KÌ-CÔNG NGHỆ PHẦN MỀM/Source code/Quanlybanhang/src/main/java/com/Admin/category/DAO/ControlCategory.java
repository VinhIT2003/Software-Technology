package com.Admin.category.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.Admin.category.DTO.DTOCategory;
import com.ComponentandDatabase.Components.CustomDialog;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ControlCategory {

    // Không dùng biến conn toàn cục nữa

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.connect();
    }

    public boolean insertCategory(DTOCategory cate) {
        String sql = "INSERT INTO Category (Category_ID, Category_Name, Sup_ID) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryID());
            ps.setString(2, cate.getCategoryName());
            ps.setString(3, cate.getSupID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Insert category failed: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCategory(DTOCategory cate) {
        String sql = "UPDATE Category SET Category_Name = ?, Sup_ID = ? WHERE Category_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cate.getCategoryName());
            ps.setString(2, cate.getSupID());
            ps.setString(3, cate.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update category failed: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCategory(String cateID) {
        String sql = "DELETE FROM Category WHERE Category_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cateID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete category failed: " + e.getMessage());
        }
        return false;
    }

    public List<DTOCategory> getAllCategories() {
        List<DTOCategory> list = new ArrayList<>();
        String sql = "SELECT c.Category_ID, c.Category_Name, s.Sup_ID, s.Sup_Name, s.Address, s.Contact " +
                     "FROM Category c JOIN Supplier s ON c.Sup_ID = s.Sup_ID";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DTOCategory cate = new DTOCategory();
                cate.setCategoryID(rs.getString("Category_ID"));
                cate.setCategoryName(rs.getString("Category_Name"));
                cate.setSupID(rs.getString("Sup_ID"));
                cate.setSupName(rs.getString("Sup_Name"));
                cate.setAddress(rs.getString("Address"));
                cate.setContact(rs.getString("Contact"));
                list.add(cate);
            }
        } catch (SQLException e) {
            System.err.println("Get all categories failed: " + e.getMessage());
        }
        return list;
    }

    public List<DTOCategory> searchCategories(String keyword, String selectedItem) {
       List<DTOCategory> list = new ArrayList<>();

     // Ánh xạ selectedItem từ JComboBox sang tên cột thực tế trong SQL
     String column = mapSearchColumn(selectedItem);

     if (column.isEmpty()) {
         System.err.println("Invalid column selection: " + selectedItem);
         return list;
     }

     String sql = "SELECT c.Category_ID, c.Category_Name, s.Sup_ID, s.Sup_Name, s.Address, s.Contact " +
                  "FROM Category c JOIN Supplier s ON c.Sup_ID = s.Sup_ID " +
                  "WHERE " + column + " LIKE ?";

     try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, "%" + keyword + "%");
         try (ResultSet rs = ps.executeQuery()) {
             while (rs.next()) {
                 DTOCategory cate = new DTOCategory();
                 cate.setCategoryID(rs.getString("Category_ID"));
                 cate.setCategoryName(rs.getString("Category_Name"));
                 cate.setSupID(rs.getString("Sup_ID"));
                 cate.setSupName(rs.getString("Sup_Name"));
                 cate.setAddress(rs.getString("Address"));
                 cate.setContact(rs.getString("Contact"));
                 list.add(cate);
             }
         }
     } catch (SQLException e) {
         System.err.println("Search category failed: " + e.getMessage());
     }

     return list;
 }
        private String mapSearchColumn(String selectedItem) {
            return switch (selectedItem) {
                case "Category.ID" -> "c.Category_ID";
                case "Category Name" -> "c.Category_Name";
                case "Brand.ID" -> "s.Sup_ID";
                default -> ""; // Nếu chọn sai
            };
        }

    public boolean isDuplicateID(String cateID) {
        String sql = "SELECT Category_ID FROM Category WHERE Category_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cateID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Check duplicate ID failed: " + e.getMessage());
        }
        return false;
    }
    
    public List<String> getAllSupplierIDs() {
      List<String> supIDList = new ArrayList<>();
        String sql = "SELECT Sup_ID FROM Supplier";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                supIDList.add(rs.getString("Sup_ID"));
            }
        } catch (SQLException e) {
            System.err.println("Get all supplier IDs failed: " + e.getMessage());
        }
        return supIDList;
    }

    
    public void loadCategoryToTable(DefaultTableModel model) {
        ControlCategory control = new ControlCategory();
        List<DTOCategory> categoryList = control.getAllCategories();

        // Xóa dữ liệu cũ trong model
        model.setRowCount(0);

        // Thêm dữ liệu từ danh sách vào bảng
        for (DTOCategory cate : categoryList) {
            Object[] rowData = {
                cate.getCategoryID(),
                cate.getCategoryName(),
                cate.getSupID(),
                cate.getSupName(),
                cate.getAddress(),
                cate.getContact()
            };
            model.addRow(rowData);
        }
    }

    public DTOCategory getCategoryByID(String categoryID) {
        DTOCategory cate = null;
        String sql = "SELECT c.Category_ID, c.Category_Name, s.Sup_ID, s.Sup_Name, s.Address, s.Contact " +
                     "FROM Category c JOIN Supplier s ON c.Sup_ID = s.Sup_ID " +
                     "WHERE c.Category_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cate = new DTOCategory();
                    cate.setCategoryID(rs.getString("Category_ID"));
                    cate.setCategoryName(rs.getString("Category_Name"));
                    cate.setSupID(rs.getString("Sup_ID"));
                    cate.setSupName(rs.getString("Sup_Name"));
                    cate.setAddress(rs.getString("Address"));
                    cate.setContact(rs.getString("Contact"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Get category by ID failed: " + e.getMessage());
        }
        return cate;
}
     public void importFile(File file) {
        String insertSQL = "INSERT INTO Category (Category_ID, Category_Name, Sup_ID) VALUES (?, ?, ?)";
        String checkSupplierSQL = "SELECT COUNT(*) FROM Supplier WHERE Sup_ID = ?";

        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis);
             Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL);
             PreparedStatement checkStmt = conn.prepareStatement(checkSupplierSQL)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua dòng tiêu đề
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getPhysicalNumberOfCells() >= 3) {
                    String categoryID = getCellValue(row.getCell(0));
                    String categoryName = getCellValue(row.getCell(1));
                    String supID = getCellValue(row.getCell(2));

                    // Kiểm tra Sup_ID có tồn tại trong bảng Supplier không
                    checkStmt.setString(1, supID);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            // Sup_ID tồn tại, tiến hành insert
                            pstmt.setString(1, categoryID);
                            pstmt.setString(2, categoryName);
                            pstmt.setString(3, supID);
                            pstmt.executeUpdate();
                        } else {
                            System.err.println("Bỏ qua: Sup_ID không tồn tại trong bảng Supplier - " + supID);
                        }
                    }
                }
            }
            CustomDialog.showSuccess("File imported successfully !");
            System.out.println("Import file thành công!");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.err.println("Import file thất bại: " + e.getMessage());
        }
    }

      // Hàm phụ để xử lý giá trị của Cell thành String
      private String getCellValue(Cell cell) {
          if (cell == null) return "";
          switch (cell.getCellType()) {
              case STRING:
                  return cell.getStringCellValue().trim();
              case NUMERIC:
                  if (DateUtil.isCellDateFormatted(cell)) {
                      return cell.getDateCellValue().toString();
                  } else {
                      return String.valueOf((long) cell.getNumericCellValue()); // Ép về long nếu cần
                  }
              case BOOLEAN:
                  return String.valueOf(cell.getBooleanCellValue());
              case FORMULA:
                  return cell.getCellFormula();
              default:
                  return "";
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

}
