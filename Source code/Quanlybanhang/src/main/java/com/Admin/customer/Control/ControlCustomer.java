package com.Admin.customer.Control;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
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

    // Phương thức hiển thị dữ liệu khách hàng từ SQL Server lên bảng
    public void showCustomer() {
        if (model == null) {
            System.out.println("Error: Model chưa được gán!");
            return;
        }
        
        // Tạo định dạng ngày theo kiểu "ddMMyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        String query = "SELECT Customer_ID, Full_Name, Gender, Date_Of_Birth, Email, Contact, Address, Status FROM Customer";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Xóa dữ liệu cũ trước khi tải mới
            model.setRowCount(0);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("Customer_ID"));
                row.add(rs.getString("Full_Name"));
                row.add(rs.getString("Gender"));
                
                // Lấy ngày sinh và định dạng theo ddMMyy
                Date dob = rs.getDate("Date_Of_Birth");
                if (dob != null) {
                    row.add(sdf.format(dob));
                } else {
                    row.add("");
                }
                
                row.add(rs.getString("Email"));
                row.add(rs.getString("Contact"));
                row.add(rs.getString("Address"));
                row.add(rs.getString("Status"));         
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // Hàm này được gọi khi nhấn nút Refresh
    public void refreshCustomerData() {
        showCustomer(); // Tái sử dụng luôn hàm hiện có
        //System.out.println("Dữ liệu khách hàng đã được làm mới!");
    }

        // Method to delete a customer by ID
    public void deleteCustomer(String customerID) {
        if (customerID == null || customerID.trim().isEmpty()) {
            System.out.println("Please select a customer to delete.");
            return;
        }

        String query = "DELETE FROM Customer WHERE Customer_ID = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully!");
                refreshCustomerData(); // Reload the data
            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                refreshCustomerData(); // Refresh lại bảng sau khi cập nhật
            } else {
                CustomDialog.showError("No customer was updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Database error: " + e.getMessage());
        }
    }
    
    public void searchCustomer(MyTextField txtSearch, JComboBox<String> cmbSearch, JComboBox<String> cmbStatus) {
       if (model == null) {
           System.out.println("Error: Model chưa được gán!");
           return;
       }

       String keyword = txtSearch.getText().trim();
       String selectedColumn = cmbSearch.getSelectedItem().toString();
       String statusFilter = cmbStatus.getSelectedItem().toString();

       // Mapping selectedColumn -> dbColumn
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
               dbColumn = ""; // Không tìm theo column nếu chọn mặc định
       }

       StringBuilder sql = new StringBuilder("SELECT Customer_ID, Full_Name, Gender, Date_Of_Birth, Email, Contact, Address, Status FROM Customer WHERE 1=1");
       List<Object> params = new ArrayList<>();

       // Nếu người dùng nhập từ khóa để tìm theo cột
       if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
           sql.append(" AND ").append(dbColumn).append(" LIKE ?");
           params.add("%" + keyword + "%");
       }

       // Nếu có chọn trạng thái cụ thể (Tránh trường hợp "Tất cả", nếu bạn có mục đó)
       if (!statusFilter.equals("Tất cả")) {
           sql.append(" AND Status = ?");
           params.add(statusFilter);
       }

       try (Connection conn = db.connect();
            PreparedStatement pst = conn.prepareStatement(sql.toString())) {

           // Gán tham số
           for (int i = 0; i < params.size(); i++) {
               pst.setObject(i + 1, params.get(i));
           }

           ResultSet rs = pst.executeQuery();

           model.setRowCount(0); // Clear bảng
           SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

           while (rs.next()) {
               Vector<Object> row = new Vector<>();
               row.add(rs.getString("Customer_ID"));
               row.add(rs.getString("Full_Name"));
               row.add(rs.getString("Gender"));

               Date dob = rs.getDate("Date_Of_Birth");
               row.add(dob != null ? sdf.format(dob) : "");

               row.add(rs.getString("Email"));
               row.add(rs.getString("Contact"));
               row.add(rs.getString("Address"));
               row.add(rs.getString("Status"));

               model.addRow(row);
           }

       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage());
    }
       
}

}
