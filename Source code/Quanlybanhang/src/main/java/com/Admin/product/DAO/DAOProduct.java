package com.Admin.product.DAO;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.ComponentandDatabase.Components.CustomDialog;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.awt.Font;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.Admin.category.DTO.DTOCategory;
import com.Admin.product.DTO.DTOProduct;
import java.sql.Connection;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class DAOProduct {

    private BufferedImage uploadedImage;
    private String imagePath;
     private Connection getConnection() throws SQLException {
        return DatabaseConnection.connect();
    }
    
   public String handleUploadButton(MyButton bntupload, JPanel panelUpload) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choose an image");
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();

        try {
            BufferedImage originalImage = ImageIO.read(selectedFile);
            if (originalImage != null) {
                // Resize ảnh
                Image scaledImage = originalImage.getScaledInstance(230, 230, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                String imagePath = selectedFile.getAbsolutePath(); // <-- Khai báo ở đây

                // Lưu ảnh vào thư mục nội bộ
                File destFolder = new File("D:" + File.separator + "Image_Data");

                if (!destFolder.exists()) destFolder.mkdirs(); // Tạo thư mục nếu chưa có

                // Kiểm tra nếu ảnh đã tồn tại trong thư mục (so sánh tên đầy đủ bao gồm phần mở rộng)
                File destFile = new File(destFolder, selectedFile.getName());
                if (destFile.exists()) {
                    // Nếu file đã tồn tại, sử dụng tên gốc của file
                    imagePath = destFile.getAbsolutePath(); // Dùng đường dẫn của file đã tồn tại
                    // Hiển thị ảnh lên GUI
                    JLabel imageLabel = new JLabel(icon);
                    panelUpload.removeAll();
                    panelUpload.add(imageLabel, "center");
                    panelUpload.revalidate();
                    panelUpload.repaint();
                    panelUpload.setVisible(true);
                } else {
                    // Nếu file chưa tồn tại, tạo bản sao mới với tên duy nhất
                    String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                    destFile = new File(destFolder, newFileName);

                    // Kiểm tra nếu file với tên mới đã tồn tại, sẽ tiếp tục tạo tên mới
                    while (destFile.exists()) {
                        newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                        destFile = new File(destFolder, newFileName);
                    }

                    // Sao chép ảnh vào thư mục
                    Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    imagePath = destFile.getAbsolutePath(); // Cập nhật đường dẫn file mới

                    // Hiển thị ảnh lên GUI
                    JLabel imageLabel = new JLabel(icon);
                    panelUpload.removeAll();
                    panelUpload.add(imageLabel, "center");
                    panelUpload.revalidate();
                    panelUpload.repaint();
                    panelUpload.setVisible(true);
                }

                return imagePath; // ✅ Trả về đường dẫn ảnh đã lưu

            } else {
                new CustomDialog().showError("Invalid image!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            new CustomDialog().showError("Cannot upload this image!");
        }
    }
    return null; // Trường hợp không chọn ảnh hoặc xảy ra lỗi
}

    
    public List<DTOCategory> getAllCategoriesWithSupplier() {
        List<DTOCategory> list = new ArrayList<>();
        String sql = """
            SELECT c.Category_ID, c.Category_Name, c.Sup_ID,
                   s.Sup_Name, s.Address, s.Contact
            FROM Category c
            JOIN Supplier s ON c.Sup_ID = s.Sup_ID
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DTOCategory dto = new DTOCategory();
                dto.setCategoryID(rs.getString("Category_ID"));
                dto.setCategoryName(rs.getString("Category_Name"));
                dto.setSupID(rs.getString("Sup_ID"));
                dto.setSupName(rs.getString("Sup_Name"));
                dto.setAddress(rs.getString("Address"));
                dto.setContact(rs.getString("Contact"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog dialog = new CustomDialog();
            dialog.showError("Lỗi khi truy vấn dữ liệu danh mục!");
        }

        return list;
    }
     
     public boolean isProductIDExists(String productID) {
        String sql = "SELECT 1 FROM Product WHERE Product_ID = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next(); // Nếu có dòng trả về, tức là đã tồn tại Product_ID
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
     public void saveProduct(DTOProduct product) {
        // Kiểm tra xem Product_ID đã tồn tại chưa
        if (isProductIDExists(product.getProductID())) {
            CustomDialog.showError("Product ID already exists! Please use another one.");
            return;
        }

        // Nếu Product_ID không tồn tại, thực hiện lưu sản phẩm
        String sql = "INSERT INTO Product(Product_ID, Product_Name, CPU, Ram, Graphics_Card, Operating_System, Price, Quantity, Warranty_Period, Status, Spoiled_Quantity, Category_ID, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductID());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getCpu());
            stmt.setString(4, product.getRam());
            stmt.setString(5, product.getGraphicsCard());
            stmt.setString(6, product.getOperatingSystem());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setInt(8, product.getQuantity());
            stmt.setString(9, product.getWarrantyPeriod());
            stmt.setString(10, product.getStatus());
            stmt.setInt(11, product.getSpoiledQuantity());
            stmt.setString(12, product.getCategoryID());
            stmt.setString(13, product.getImage());

            stmt.executeUpdate();
            CustomDialog.showSuccess("Product saved successfully !");
        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Product save failed");
        }
    }
     
     
    public void uploadProductToTable(DefaultTableModel model) {
      // Xóa dữ liệu cũ nếu có
      model.setRowCount(0);

      String sql = """
          SELECT 
              p.Product_ID, 
              p.Product_Name, 
              p.Price, 
              p.Quantity, 
              CASE 
                  WHEN p.Quantity = 0 THEN 'Unavailable' 
                  ELSE p.Status 
              END AS Status, 
              p.Spoiled_Quantity AS Broken_Quantity, 
              c.Category_ID, 
              c.Category_Name, 
              s.Sup_ID AS Brand_ID, 
              s.Sup_Name AS Brand_Name, 
              s.Contact
          FROM 
              Product p
          JOIN Category c ON p.Category_ID = c.Category_ID
          JOIN Supplier s ON c.Sup_ID = s.Sup_ID
      """;

      try (Connection conn = getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql);
           ResultSet rs = stmt.executeQuery()) {

          while (rs.next()) {
              Object[] row = new Object[]{
                  rs.getString("Product_ID"),
                  rs.getString("Product_Name"),
                  rs.getBigDecimal("Price"),
                  rs.getInt("Quantity"),
                  rs.getString("Status"),  // Lúc này đã là "Unavailable" nếu Quantity = 0
                  rs.getInt("Broken_Quantity"),
                  rs.getString("Category_ID"),
                  rs.getString("Category_Name"),
                  rs.getString("Brand_ID"),
                  rs.getString("Brand_Name"),
                  rs.getString("Contact")
              };
              model.addRow(row);
          }

      } catch (SQLException e) {
          e.printStackTrace();
          CustomDialog.showError("Lỗi khi tải sản phẩm vào bảng!");
      }
  }
    public DTOProduct getProductById(String productId) {
        String sql = """
            SELECT Product_ID, Product_Name, CPU, Ram, Graphics_Card, Operating_System,
                   Price, Quantity, Warranty_Period, Status, Spoiled_Quantity, Category_ID, Image
            FROM Product
            WHERE Product_ID = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DTOProduct product = new DTOProduct();
                    product.setProductID(rs.getString("Product_ID"));
                    product.setProductName(rs.getString("Product_Name"));
                    product.setCpu(rs.getString("CPU"));
                    product.setRam(rs.getString("Ram"));
                    product.setGraphicsCard(rs.getString("Graphics_Card"));
                    product.setOperatingSystem(rs.getString("Operating_System"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getString("Warranty_Period"));
                    product.setStatus(rs.getString("Status"));
                    product.setSpoiledQuantity(rs.getInt("Spoiled_Quantity"));
                    product.setCategoryID(rs.getString("Category_ID"));
                    product.setImage(rs.getString("Image")); // ✅ Trả về đường dẫn ảnh

                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Lỗi khi lấy thông tin sản phẩm theo ID!");
        }

        return null;
    }

    public boolean updateProduct(DTOProduct product) {
       // Cập nhật status dựa trên Quantity
       String status = (product.getQuantity() == 0) ? "Unavailable" : "Available";

       String sql = "UPDATE Product SET " +
                    "Product_Name = ?, CPU = ?, Ram = ?, Graphics_Card = ?, " +
                    "Operating_System = ?, Price = ?, Quantity = ?, Warranty_Period = ?, " +
                    "Status = ?, Spoiled_Quantity = ?, Category_ID = ?, Image = ? " +
                    "WHERE Product_ID = ?";

       try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, product.getProductName());
           stmt.setString(2, product.getCpu());
           stmt.setString(3, product.getRam());
           stmt.setString(4, product.getGraphicsCard());
           stmt.setString(5, product.getOperatingSystem());
           stmt.setBigDecimal(6, product.getPrice());
           stmt.setInt(7, product.getQuantity());
           stmt.setString(8, product.getWarrantyPeriod());
           stmt.setString(9, status);  // Set status dựa trên Quantity
           stmt.setInt(10, product.getSpoiledQuantity());
           stmt.setString(11, product.getCategoryID());
           stmt.setString(12, product.getImage());
           stmt.setString(13, product.getProductID());

           int rowsAffected = stmt.executeUpdate();
           if (rowsAffected > 0) {
               return true;
           } else {
               return false;
           }

       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }

   public boolean deleteProduct(String productId) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa trực tiếp product, các bảng liên quan sẽ tự động xóa nhờ ON DELETE CASCADE
            String sql = "DELETE FROM Product WHERE Product_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, productId);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    conn.commit(); // Commit nếu thành công
                    return true;
                }
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset lại autocommit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean hasRelatedRecords(String productId) {
        String sql = "SELECT 1 FROM Order_Details WHERE Product_ID = ? LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            return stmt.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Trả về true nếu có lỗi để ngăn xóa nhầm
        }
    }
    
    public void searchProduct(String keyword, String selected, DefaultTableModel model) {
        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

        String sql = """
            SELECT 
                p.Product_ID, 
                p.Product_Name, 
                p.Price, 
                p.Quantity, 
                CASE 
                    WHEN p.Quantity = 0 THEN 'Unavailable' 
                    ELSE p.Status 
                END AS Status, 
                p.Spoiled_Quantity AS Broken_Quantity, 
                c.Category_ID, 
                c.Category_Name, 
                s.Sup_ID AS Brand_ID, 
                s.Sup_Name AS Brand_Name, 
                s.Contact
            FROM 
                Product p
            JOIN Category c ON p.Category_ID = c.Category_ID
            JOIN Supplier s ON c.Sup_ID = s.Sup_ID
            WHERE 
        """;

        boolean needParameter = true;

        switch (selected) {
            case "Product.ID" -> sql += "p.Product_ID = ?";
            case "Product Name" -> sql += "p.Product_Name LIKE ?";
            case "Brand.ID" -> sql += "s.Sup_ID LIKE ?";
            case "Available" -> {
                sql += "p.Status = 'Available'";
                needParameter = false;
            }
            case "Unavailable" -> {
                sql += "p.Quantity = 0";
                needParameter = false;
            }
            default -> {
                return;  // Không làm gì nếu không khớp
            }
        }

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (needParameter) {
                stmt.setString(1, selected.equals("Product.ID") ? keyword : "%" + keyword + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getInt("Broken_Quantity"),
                        rs.getString("Category_ID"),
                        rs.getString("Category_Name"),
                        rs.getString("Brand_ID"),
                        rs.getString("Brand_Name"),
                        rs.getString("Contact")
                    };
                    model.addRow(row);  // Đổ vào bảng
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Lỗi khi tìm kiếm sản phẩm!");
        }
    }

    public void exportProductToExcel(String filePath) {
        String sql = "SELECT * FROM Product";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Products");

            // Header
            String[] headers = {
                "Product ID", "Product Name", "CPU", "RAM", "Graphics Card", "Operating System",
                "Price", "Quantity", "Warranty Period", "Status", "Spoiled Quantity", "Category ID", "Image"
            };

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Data
            int rowIndex = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(rs.getString("Product_ID"));
                row.createCell(1).setCellValue(rs.getString("Product_Name"));
                row.createCell(2).setCellValue(rs.getString("CPU"));
                row.createCell(3).setCellValue(rs.getString("RAM"));
                row.createCell(4).setCellValue(rs.getString("Graphics_Card"));
                row.createCell(5).setCellValue(rs.getString("Operating_System"));
                row.createCell(6).setCellValue(rs.getBigDecimal("Price").toString());
                row.createCell(7).setCellValue(rs.getInt("Quantity"));
                row.createCell(8).setCellValue(rs.getString("Warranty_Period"));
                row.createCell(9).setCellValue(rs.getString("Status"));
                row.createCell(10).setCellValue(rs.getInt("Spoiled_Quantity"));
                row.createCell(11).setCellValue(rs.getString("Category_ID"));
                row.createCell(12).setCellValue(rs.getString("Image"));
            }

            // Autosize columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }

            workbook.close();
            CustomDialog.showSuccess("File exported successfully !");

        } catch (Exception e) {
            e.printStackTrace();
            CustomDialog.showError("Lỗi khi xuất dữ liệu sản phẩm ra Excel!");
        }
    }


}
