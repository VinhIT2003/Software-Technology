package com.Admin.category.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.category.BUS.BusCategory;
import com.Admin.category.DTO.DTOCategory;

import javax.swing.*;

import java.awt.*;

public class Form_Category extends JPanel {
    private JPanel panel, panelSearch;
    private JLabel lblCateID, lblCateName, lblBrandID, lblimage1, lblimage2, lblimage3; 
    private MyTextField txtSearch, txtCateID, txtCateName;
    private MyButton bntSearch, bntSave, bntUpdate, bntDelete, bntClear, bntRefresh,bntImportFile, bntExportFile;
    private MyCombobox<String> cmbSearchcate, cmbBrandID;
    private MyTable tableCate;
    private BusCategory busCategory;
    public Form_Category() {
        initComponents();
        init();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1530, 860)); // Giữ kích thước nhưng không ép buộc vị trí
        setBackground(Color.WHITE); // Kiểm tra hiển thị
    }

    private void init() {
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1530, 860); // Giữ nguyên layout của các thành phần
        panel.setBackground(Color.WHITE); // Màu xanh dương
        add(panel);

        // Tiêu đề
//        lblTitle = new JLabel("Form Category");
//        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
//        lblTitle.setForeground(Color.WHITE);
//        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
//        lblTitle.setBounds(280, 130, 250, 50); // Định vị đúng bên trong panel
//        lblTitle.setForeground(Color.BLACK);
//        panel.add(lblTitle);
        
                     // Tạo panelSearch với màu nền trắng
            panelSearch = new MyPanel(Color.WHITE);
            panelSearch.setLayout(null);
            panelSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            panelSearch.setBounds(600,20, 600, 60);
            
  
            // Tạo txtSearch
            txtSearch = new MyTextField();
            txtSearch.setHint("Enter the search key word...");
            txtSearch.setBounds(210, 10, 250, 35); // Đặt vị trí và kích thước
            txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
            txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
            panelSearch.add(txtSearch); // Thêm vào giao diện
            
            // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
            String[] items = {"Category.ID", "Category Name", "Brand.ID"};
            cmbSearchcate = new MyCombobox<>(items);
            cmbSearchcate.setBounds(30, 10, 165,35);
            cmbSearchcate.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbSearchcate.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
            //cmbSearch.setMaximumRowCount(5); // Giới hạn dòng dropdown nếu dài
            cmbSearchcate.repaint();
            cmbSearchcate.revalidate();

                    // 👉 Thêm đoạn invokeLater để đảm bảo cmbSearch được refresh UI
           SwingUtilities.invokeLater(() -> {
              cmbSearchcate.repaint();
              cmbSearchcate.revalidate();
              //cmbSearch.updateUI(); // 👈 Bắt buộc để refresh lại giao diện
           });

            panelSearch.add(cmbSearchcate);
            
            bntSearch= new MyButton("Search", 20);
            bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
            bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
            bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
            bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
            bntSearch.setForeground(Color.WHITE);
            bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
            bntSearch.setBounds(txtSearch.getX() + txtSearch.getWidth() + 10, 10, 120, 35);
            bntSearch.addActionListener((ActionEvent e) -> {
                // Clear TextFields
               String keyword= txtSearch.getText().trim();
               String selectedItem = cmbSearchcate.getSelectedItem().toString();
               List<DTOCategory> results = busCategory.searchCategory(keyword, selectedItem);
                            // Clear bảng trước khi đổ dữ liệu mới
                DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

                // Đổ kết quả vào JTable
                for (DTOCategory cate : results) {
                    model.addRow(new Object[]{
                        cate.getCategoryID(),
                        cate.getCategoryName(),
                        cate.getSupID(),
                        cate.getSupName(),
                        cate.getAddress(),
                        cate.getContact()
                    });
                }
         });
            panelSearch.add(bntSearch);
            panel.add(panelSearch);
            
            lblCateID= new JLabel("Category.ID");
            lblCateID.setFont(new Font("sansserif", Font.PLAIN, 18));
            lblCateID.setForeground(Color.WHITE);
            lblCateID.setBounds(20, 130, 250, 50); // Định vị đúng bên trong panel
            lblCateID.setForeground(Color.BLACK);
            panel.add(lblCateID);
            
            txtCateID = new MyTextField();
            txtCateID.setBounds(150, 135, 200, 32); // Đặt vị trí và kích thước
            txtCateID.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtCateID.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtCateID.setBackgroundColor(Color.decode("#F0FFFF"));
            panel.add(txtCateID);
            
            
            lblCateName= new JLabel("Category Name");
            lblCateName.setFont(new Font("sansserif", Font.PLAIN, 18));
            lblCateName.setForeground(Color.WHITE);
            lblCateName.setBounds(20, 200, 250, 50); // Định vị đúng bên trong panel
            lblCateName.setForeground(Color.BLACK);
            panel.add(lblCateName);
            
            txtCateName= new MyTextField();
            txtCateName.setBounds(180, 210, 250, 32); // Đặt vị trí và kích thước
            txtCateName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtCateName.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtCateName.setBackgroundColor(Color.decode("#F0FFFF"));
            panel.add(txtCateName);
            
            lblBrandID= new JLabel("Brand.ID");
            lblBrandID.setFont(new Font("sansserif", Font.PLAIN, 18));
            lblBrandID.setForeground(Color.WHITE);
            lblBrandID.setBounds(20, 275, 250, 50); // Định vị đúng bên trong panel
            lblBrandID.setForeground(Color.BLACK);
            panel.add(lblBrandID);
            
            
           // Tạo danh sách item cho JComboBox (ban đầu rỗng)
            cmbBrandID = new MyCombobox<String>();
            cmbBrandID.setBounds(120, 280, 130, 35);
            cmbBrandID.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbBrandID.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);

            // 👉 Gọi SwingUtilities để đảm bảo UI cập nhật đúng
            SwingUtilities.invokeLater(() -> {
                cmbBrandID.removeAllItems(); // Xóa item cũ nếu có

                // Lấy danh sách Sup_ID từ cơ sở dữ liệu
                List<String> supIDs = busCategory.getAllSupplierIDs();

                // Thêm từng Sup_ID vào combobox
                for (String id : supIDs) {
                    cmbBrandID.addItem(id);
                }

                // Làm mới giao diện của combobox
                cmbBrandID.repaint();
                cmbBrandID.revalidate();
            });


           panel.add(cmbBrandID);
           busCategory= new BusCategory();
           bntSave = new MyButton("Save", 20);
           bntSave.setBackgroundColor(Color.decode("#2196F3")); // Xanh dương chính
           bntSave.setHoverColor(Color.decode("#42A5F5"));       // Hover sáng hơn
           bntSave.setPressedColor(Color.decode("#1976D2"));     // Nhấn đậm hơn
           bntSave.setFont(new Font("Times New Roman", Font.BOLD, 16));
           bntSave.setForeground(Color.WHITE);
           bntSave.setBounds(20, 380, 100, 40);
           bntSave.addActionListener(e -> {
            String categoryID = txtCateID.getText().trim();
            String categoryName = txtCateName.getText().trim();
            String brandID = cmbBrandID.getSelectedItem() != null ? cmbBrandID.getSelectedItem().toString() : null;

            if (categoryID.isEmpty() || categoryName.isEmpty() || brandID == null) {
                CustomDialog.showError("Please fill in all required fields!");
                return;
            }

            // Kiểm tra trùng ID trước
            if (busCategory.isDuplicateID(categoryID)) {
                CustomDialog.showError("This Category.ID already exists!");
                return;
            }

            DTOCategory category = new DTOCategory(categoryID, categoryName, brandID);

            boolean success = busCategory.addCategory(category);
            if (success) {
                CustomDialog.showSuccess("This category was added successfully!");
                DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
                busCategory.loadCategoryToTable(model); // Tải lại dữ liệu từ database
            } else {
                CustomDialog.showError("Failed to add category. Please try again later.");
            }
        });

           panel.add(bntSave);
//           
           bntUpdate = new MyButton("Update", 20);
           bntUpdate.setBackgroundColor(Color.decode("#4caf50")); // Xanh dương chính
           bntUpdate.setHoverColor(Color.decode("#66CC00"));       // Hover sáng hơn
           bntUpdate.setPressedColor(Color.decode("#388E3C"));     // Nhấn đậm hơn
           bntUpdate.setFont(new Font("Times New Roman", Font.BOLD, 16));
           bntUpdate.setForeground(Color.WHITE);
           bntUpdate.setBounds(130, 380, 100, 40);
           bntUpdate.addActionListener((ActionEvent e) -> {
            String categoryID = txtCateID.getText().trim();
            String categoryName = txtCateName.getText().trim();
            String supplierID = (String) cmbBrandID.getSelectedItem();

            if (categoryID.isEmpty() || categoryName.isEmpty() || supplierID == null) {
                CustomDialog.showError("Please select a category to update !");
                return;
            }

            DTOCategory category = new DTOCategory(categoryID, categoryName, supplierID);

            boolean success = busCategory.updateCategory(category);

            if (success) {
               CustomDialog.showSuccess("Category updated successfully !");
                // Refresh lại bảng sau khi cập nhật
                DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ
                busCategory.loadCategoryToTable(model);
            } else {
               CustomDialog.showError("Update the list of failures !");
            }
        });

           panel.add(bntUpdate);
//         
//           
           bntDelete = new MyButton("Delete", 20);
           bntDelete.setBackgroundColor(Color.decode("#f44336")); 
           bntDelete.setHoverColor(Color.decode("#FF6633"));      
           bntDelete.setPressedColor(Color.decode("#d32f2f"));     
           bntDelete.setFont(new Font("Times New Roman", Font.BOLD, 16));
           bntDelete.setForeground(Color.WHITE);
           bntDelete.setBounds(240, 380, 100, 40);
           bntDelete.addActionListener((ActionEvent e) -> {
            int selectedRow = tableCate.getSelectedRow();

            if (selectedRow != -1) {
                String cateID = tableCate.getValueAt(selectedRow, 0).toString();
                String cateName = tableCate.getValueAt(selectedRow, 1).toString(); // Column 1 is category name

                boolean confirm = CustomDialog.showOptionPane(
                        "Confirm Deletion",
                        "Are you sure you want to delete the category: " + cateName + "?",
                        UIManager.getIcon("OptionPane.questionIcon"),
                        Color.decode("#FF6666")
                );

                if (confirm) {
                    boolean success = busCategory.deleteCategory(cateID);

                    if (success) {
                        CustomDialog.showSuccess("Successfully deleted the category: " + cateName + "!");
                        // Refresh table
                        DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                        model.setRowCount(0);
                        busCategory.loadCategoryToTable(model);
                    } else {
                        CustomDialog.showError("Failed to delete the category!");
                    }
                }

            } else {
                CustomDialog.showError("Please select a category to delete!");
            }
        });

           panel.add(bntDelete);
           
           
          bntClear = new MyButton("Clear", 20);
          bntClear.setBackgroundColor(Color.decode("#607D8B"));  // Base background: blue-gray
          bntClear.setHoverColor(Color.decode("#78909C"));       // Lighter blue-gray for hover state
          bntClear.setPressedColor(Color.decode("#455A64"));     // Darker blue-gray for pressed state
          bntClear.setFont(new Font("Times New Roman", Font.BOLD, 16));
          bntClear.setForeground(Color.WHITE);
          bntClear.setBounds(350, 380, 100, 40);
            bntClear.addActionListener((ActionEvent e) -> {
                // Clear TextFields
                txtCateID.setText("");
                txtCateName.setText("");

                // Reset combobox về item đầu tiên (nếu có)
                if (cmbBrandID.getItemCount() > 0) {
                    cmbBrandID.setSelectedIndex(0);
                }
            });
          panel.add(bntClear);
          
          bntRefresh = new MyButton("Refresh", 20);
          bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
          bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntRefresh.setBounds(10, 40, 140, 35); // Tăng chiều rộng để icon không bị che mất
          bntRefresh.setFont(new Font("sansserif", Font.BOLD, 18));
          bntRefresh.setForeground(Color.BLACK);
          bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
            // Gắn sự kiện cho nút Refresh
            bntRefresh.addActionListener((ActionEvent e) -> {
                DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
                busCategory.refreshTable(model); // Tải lại dữ liệu từ database
            });
          panel.add(bntRefresh);
          
          bntImportFile = new MyButton("Import File", 0);
          bntImportFile.setBackgroundColor(Color.WHITE); // Màu nền
          bntImportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntImportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntImportFile.setBounds(160, 35, 180, 40); // Tăng chiều rộng để icon không bị che mất
          bntImportFile.setFont(new Font("sansserif", Font.BOLD, 18));
          bntImportFile.setForeground(Color.BLACK);
          bntImportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntImportFile.addActionListener((ActionEvent e) -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a file excel to import");

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    busCategory.importFile(selectedFile);
                      DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                      model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
                      busCategory.loadCategoryToTable(model);
                      tableCate.adjustColumnWidths();
                }
            });

          panel.add(bntImportFile);
          
          
           bntExportFile = new MyButton("Export File", 0);
           bntExportFile.setBackgroundColor(Color.WHITE); // Màu nền
           bntExportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntExportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntExportFile.setBounds(350, 35, 180, 40); // Tăng chiều rộng để icon không bị che mất
           bntExportFile.setFont(new Font("sansserif", Font.BOLD, 18));
           bntExportFile.setForeground(Color.BLACK);
           bntExportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
            bntExportFile.addActionListener((ActionEvent e) -> {
                busCategory.exportFile(tableCate);
            });
           panel.add(bntExportFile);

                        // 1️⃣ Tên cột
             String[] columnNames = {
                 "Category.ID", "Category Name", "Brand.ID", "Brand Name", 
                 "Address", "Contact"
             };

             // 2️⃣ Tạo model
             DefaultTableModel model = new DefaultTableModel(columnNames, 0);

             // 3️⃣ Font hiển thị
             Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
             Font headerFont = new Font("SansSerif", Font.BOLD, 16);

             // 4️⃣ Tạo bảng sử dụng MyTable
             tableCate = new MyTable(
                 model,
                 new Color(255, 255, 255),  // Nền bảng
                 new Color(0, 0, 0),        // Chữ bảng
                 new Color(250, 219, 216),  // Nền dòng được chọn
                 new Color(0, 0, 0),        // Chữ dòng được chọn
                 Color.decode("#FF6666"),   // Nền tiêu đề
                 new Color(255, 255, 255),  // Chữ tiêu đề
                 contentFont,
                 headerFont
             );

             // 5️⃣ ScrollPane chứa bảng
             JScrollPane scrollPane = MyTable.createScrollPane(tableCate, 480, 110, 780, 430);

             // 6️⃣ Tùy chỉnh thanh cuộn
             scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
             scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

             // 7️⃣ Thêm scrollPane vào panel
             panel.add(scrollPane);


             // 🔟 Load dữ liệu và căn chỉnh cột (chạy trong UI thread)
             SwingUtilities.invokeLater(() -> {
                 busCategory.loadCategoryToTable(model);   // Load dữ liệu
                 tableCate.adjustColumnWidths();           // Căn chỉnh cột
             });

             // 🔁 Sự kiện click để hiển thị dữ liệu lên form
             tableCate.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     int selectedRow = tableCate.getSelectedRow();
                     if (selectedRow != -1) {
                         String categoryID = tableCate.getValueAt(selectedRow, 0).toString();

                         // Lấy dữ liệu từ BUS
                         DTOCategory cate = busCategory.getCategoryID(categoryID);

                         if (cate != null) {
                             txtCateID.setText(cate.getCategoryID());
                             txtCateName.setText(cate.getCategoryName());
                             cmbBrandID.setSelectedItem(cate.getSupID());
                         }
                     }

                     // Làm mới bảng nếu cần
                     SwingUtilities.invokeLater(() -> {
                         DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
                         model.fireTableDataChanged();
                     });
                 }
             });

            
                // 1️⃣ Load ảnh gốc từ file
         ImageIcon icon1 = new ImageIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Asus Gaming.jpg");

         // 2️⃣ Scale ảnh bằng hàm chất lượng cao mà bạn đã viết
         Image scaledImage = getHighQualityScaledImage(icon1.getImage(), 300, 300);
         icon1 = new ImageIcon(scaledImage);

         // 3️⃣ Gắn ảnh vào JLabel
         lblimage1 = new JLabel(icon1);
         lblimage1.setBounds(100, 550, 300, 300); // vị trí và kích thước label

         // 4️⃣ Thêm vào panel để hiển thị
         panel.add(lblimage1);

         
                 // 1️⃣ Load ảnh gốc từ file
         ImageIcon icon2 = new ImageIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Asus Gaming Rog.png");

         // 2️⃣ Scale ảnh bằng hàm chất lượng cao mà bạn đã viết
         Image scaledImage2 = getHighQualityScaledImage(icon2.getImage(), 300, 300);
         icon2 = new ImageIcon(scaledImage2);

         // 3️⃣ Gắn ảnh vào JLabel
         lblimage2 = new JLabel(icon2);
         lblimage2.setBounds(500, 530, 400, 300); // vị trí và kích thước label

         // 4️⃣ Thêm vào panel để hiển thị
         panel.add(lblimage2);
         
         
                  // 1️⃣ Load ảnh gốc từ file
         ImageIcon icon3 = new ImageIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Asus Gaming Rog2.png");

         // 2️⃣ Scale ảnh bằng hàm chất lượng cao mà bạn đã viết
         Image scaledImage3 = getHighQualityScaledImage(icon3.getImage(), 300, 300);
         icon3 = new ImageIcon(scaledImage3);

         // 3️⃣ Gắn ảnh vào JLabel
         lblimage3 = new JLabel(icon3);
         lblimage3.setBounds(950, 550, 400, 300); // vị trí và kích thước label

         // 4️⃣ Thêm vào panel để hiển thị
         panel.add(lblimage3);
        
//        // 9️⃣ Repaint panel sau khi thêm
//        panel.revalidate();
//        panel.repaint();
        
        this.repaint();
        this.revalidate();  
               
   }
    public static BufferedImage getHighQualityScaledImage(Image srcImg, int targetWidth, int targetHeight) {
       BufferedImage img = toBufferedImage(srcImg);
       int currentWidth = img.getWidth();
       int currentHeight = img.getHeight();
       int type = BufferedImage.TYPE_INT_ARGB;

       BufferedImage scaledImg = img;

       // Multi-step scaling nếu ảnh lớn hơn mục tiêu
       do {
           int nextWidth = currentWidth > targetWidth ? Math.max(targetWidth, currentWidth / 2) : currentWidth;
           int nextHeight = currentHeight > targetHeight ? Math.max(targetHeight, currentHeight / 2) : currentHeight;

           if (currentWidth == nextWidth && currentHeight == nextHeight) {
               break; // Đã tới đúng kích thước
           }

           BufferedImage temp = new BufferedImage(nextWidth, nextHeight, type);
           Graphics2D g2 = temp.createGraphics();

           g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
           g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
           g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

           g2.drawImage(scaledImg, 0, 0, nextWidth, nextHeight, null);
           g2.dispose();

           scaledImg = temp;
           currentWidth = nextWidth;
           currentHeight = nextHeight;

       } while (currentWidth != targetWidth || currentHeight != targetHeight);

       return scaledImg;
   }

    public static BufferedImage toBufferedImage(Image img) {
       if (img instanceof BufferedImage) return (BufferedImage) img;

       int width = img.getWidth(null);
       int height = img.getHeight(null);
       if (width < 0 || height < 0) {
           throw new IllegalArgumentException("Ảnh không hợp lệ hoặc chưa được load.");
       }

       BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D bGr = bimage.createGraphics();
       bGr.drawImage(img, 0, 0, null);
       bGr.dispose();
       return bimage;
   }


}
