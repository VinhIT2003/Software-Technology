package com.Admin.customer.DAO;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.Admin.customer.DTO.DTOCustomer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyTextField;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ControlCustomer {
    private DatabaseConnection db;
    private DefaultTableModel model;
    

    // Hàm này để gán model từ bên ngoài
    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

   public List<DTOCustomer> getAllCustomers() {
        List<DTOCustomer> customerList = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DTOCustomer customer = new DTOCustomer(
                    rs.getString("Customer_ID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getDate("Date_Of_Birth"),
                    rs.getString("Email"),
                    rs.getString("Contact"),
                    rs.getString("Address"),
                    rs.getString("Password"),
                    rs.getString("Status")
                );
                customerList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    
  
        // Method to get Full_Name from Customer_ID
   public String getCustomerNameByID(String customerID) {
        String name = "this customer"; // fallback
        String query = "SELECT Full_Name FROM Customer WHERE Customer_ID = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("Full_Name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
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
                Sheet sheet = workbook.createSheet("Customer Data");

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
    public void update(JTable table, JComboBox<String> cmbStatus) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            CustomDialog.showError("Please select a customer to update!");
            return;
        }

        // Lấy Customer_ID từ hàng được chọn (giả sử cột 0 là Customer_ID)
        String customerID = table.getValueAt(selectedRow, 0).toString();

        // Lấy trạng thái mới từ ComboBox
        String newStatus = (String) cmbStatus.getSelectedItem();

        String sql = "UPDATE Customer SET Status = ? WHERE Customer_ID = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setString(2, customerID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                CustomDialog.showSuccess("Customer status updated successfully!");
                //refreshCustomerData(); // Refresh lại bảng sau khi cập nhật
            } else {
                CustomDialog.showError("No customer was updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Database error: " + e.getMessage());
        }
    }
    
    public List<DTOCustomer> searchCustomer(String selectedColumn, String keyword, String statusFilter) {
        List<DTOCustomer> result = new ArrayList<>();

        // Chuyển tên cột hiển thị sang cột trong DB
        String dbColumn = "";
        switch (selectedColumn) {
            case "Customer.ID":
                dbColumn = "Customer_ID";
                break;
            case "Customer Name":
                dbColumn = "Full_Name";
                break;
            case "Email":
                dbColumn = "Email";
                break;
            case "Contact":
                dbColumn = "Contact";
                break;
            default:
                dbColumn = ""; // Trường hợp không tìm theo cột
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM Customer WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Nếu có từ khóa và cột hợp lệ thì thêm điều kiện
        if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
            sql.append(" AND ").append(dbColumn).append(" LIKE ?");
            params.add("%" + keyword + "%");
        }

        try (Connection conn = db.connect();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DTOCustomer customer = new DTOCustomer(
                    rs.getString("Customer_ID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getDate("Date_Of_Birth"),
                    rs.getString("Email"),
                    rs.getString("Contact"),
                    rs.getString("Address"),
                    rs.getString("Password"),
                    rs.getString("Status")
                );
                result.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
  
    public boolean deleteCustomer(String customerID) {
       Connection conn = null;
       PreparedStatement pstmtCheck = null;
       PreparedStatement pstmtUpdateBill = null;
       PreparedStatement pstmtUpdateDetails = null;
       PreparedStatement pstmtUpdateInsurance = null;
       PreparedStatement pstmtDeleteCustomer = null;

       try {
           conn = db.connect();
           conn.setAutoCommit(false); // Bắt đầu giao dịch để đảm bảo an toàn dữ liệu

           // Bước 1: Kiểm tra xem khách hàng có dữ liệu liên quan không
           String sqlCheck = "SELECT 1 FROM Bill_Exported WHERE Customer_ID = ? UNION ALL " +
                             "SELECT 1 FROM Bill_Exported_Details WHERE Customer_ID = ? UNION ALL " +
                             "SELECT 1 FROM Insurance WHERE Customer_ID = ?";
           pstmtCheck = conn.prepareStatement(sqlCheck);
           pstmtCheck.setString(1, customerID);
           pstmtCheck.setString(2, customerID);
           pstmtCheck.setString(3, customerID);

           ResultSet rs = pstmtCheck.executeQuery();
           boolean hasRelatedData = rs.next(); // Nếu có dữ liệu liên quan, `hasRelatedData = true`

           // Bước 2: Nếu có dữ liệu liên quan, cập nhật `NULL` trước khi xóa
           if (hasRelatedData) {
               // Cập nhật `Customer_ID = NULL` trong `Bill_Exported`
               String sqlUpdateBill = "UPDATE Bill_Exported SET Customer_ID = NULL, Description = CONCAT('Deleted_', ?) WHERE Customer_ID = ?";
               pstmtUpdateBill = conn.prepareStatement(sqlUpdateBill);
               pstmtUpdateBill.setString(1, customerID);
               pstmtUpdateBill.setString(2, customerID);
               pstmtUpdateBill.executeUpdate();

               // Cập nhật `Customer_ID = NULL` trong `Bill_Exported_Details`
               String sqlUpdateDetails = "UPDATE Bill_Exported_Details SET Customer_ID = NULL WHERE Customer_ID = ?";
               pstmtUpdateDetails = conn.prepareStatement(sqlUpdateDetails);
               pstmtUpdateDetails.setString(1, customerID);
               pstmtUpdateDetails.executeUpdate();

               // Cập nhật `Customer_ID = NULL` trong `Insurance`
               String sqlUpdateInsurance = "UPDATE Insurance SET Customer_ID = NULL, Describle_Customer = CONCAT('Deleted_', ?) WHERE Customer_ID = ?";
               pstmtUpdateInsurance = conn.prepareStatement(sqlUpdateInsurance);
               pstmtUpdateInsurance.setString(1, customerID);
               pstmtUpdateInsurance.setString(2, customerID);
               pstmtUpdateInsurance.executeUpdate();
           }

           // Bước 3: Xóa khách hàng khỏi bảng `Customer`
           String sqlDeleteCustomer = "DELETE FROM Customer WHERE Customer_ID = ?";
           pstmtDeleteCustomer = conn.prepareStatement(sqlDeleteCustomer);
           pstmtDeleteCustomer.setString(1, customerID);
           int affectedRows = pstmtDeleteCustomer.executeUpdate();

           conn.commit(); // Xác nhận giao dịch

           return affectedRows > 0;

       } catch (SQLException ex) {
           try {
               if (conn != null) conn.rollback(); // Quay lại trạng thái trước khi lỗi xảy ra
           } catch (SQLException rollbackEx) {
               rollbackEx.printStackTrace();
           }
           ex.printStackTrace();
           return false;

       } finally {
           try {
               if (pstmtCheck != null) pstmtCheck.close();
               if (pstmtUpdateBill != null) pstmtUpdateBill.close();
               if (pstmtUpdateDetails != null) pstmtUpdateDetails.close();
               if (pstmtUpdateInsurance != null) pstmtUpdateInsurance.close();
               if (pstmtDeleteCustomer != null) pstmtDeleteCustomer.close();
               if (conn != null) conn.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
   }



}
