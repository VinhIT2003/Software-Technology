

package com.Admin.product.GUI;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import com.Admin.product.BUS.BusProduct;
import com.Admin.product.DTO.DTOProduct;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import com.Admin.category.DTO.DTOCategory;
import com.Admin.product.BUS.BusProduct;
import com.Admin.product.DTO.DTOProduct;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.imageio.IIOImage;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import net.miginfocom.swing.MigLayout;  // Layout manager chính
import net.miginfocom.layout.*;         // (Tùy chọn) Nếu cần các constraint nâng cao
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import java.awt.Cursor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class EditProduct extends javax.swing.JFrame {
     private int mouseX, mouseY;
     public JLabel lblTitle, lblProductID, lblProductName, lblCPU, lblRam, 
             lblCard, lblOprerate, lblPrice, lblQuantity, lblwaranty, lblSpoiled, lblCate ;
     public MyPanel panelTitle;
     public MyTextField txtProductID, txtProductName, txtCPU, txtRam, txtCard, txtPrice, txtwaranty;
     public MyCombobox cmbOperate;
     public MyButton bntupload, bntUpdate;
     public Form_Product product;
     private DTOProduct updatedProduct;
     public JPanel panelUpload;
     public JSpinner spinnerQuantity, spinderBrokenQuantity;
     public JMenu menu;
     public String image;
     private String currentImagePath = null; 
      private BusProduct busProduct;

    public EditProduct() {
        initComponents();
      
       setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
            // Thêm đoạn này vào đây:
      bg.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mousePressed(java.awt.event.MouseEvent evt) {
              mouseX = evt.getX();
              mouseY = evt.getY();
          }
      });

    bg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseDragged(java.awt.event.MouseEvent evt) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
        }
    });
        init();
    }
        public void init() {
                // Thiết lập MigLayout cho panel chính với khả năng co giãn
          bg.setLayout(new MigLayout("insets 0, fill", "[grow]", "[40!][grow]"));

          // 1. Panel tiêu đề (tự động co giãn theo chiều ngang)
          panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
          panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

          lblTitle = new JLabel("Edit Products", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
          bg.add(panelTitle, "growx, h 40!, wrap");
          
                    // ===== KHỞI TẠO PANEL UPLOAD =====
           panelUpload = new JPanel(new MigLayout("insets 0, gap 0, fill"));
           panelUpload.setBackground(Color.WHITE);
           panelUpload.setVisible(false);
           panelUpload.setPreferredSize(new Dimension(230, 230));

           JLabel lblUploadImage = new JLabel("Upload Image Here");
           lblUploadImage.setFont(new Font("Arial", Font.PLAIN, 16));
           panelUpload.add(lblUploadImage, "pos 0.5al 0.5al");

           bg.add(panelUpload, "pos 10 110, w 230!, h 230!");  // Điều chỉnh đúng kích thước panel

                        // ===== NÚT UPLOAD ẢNH =====
              bntupload = new MyButton("Upload", 0);
              bntupload.setBackgroundColor(Color.WHITE);
              bntupload.setPressedColor(Color.decode("#D3D3D3"));
              bntupload.setHoverColor(Color.decode("#EEEEEE"));
              bntupload.setFont(new Font("sansserif", Font.BOLD, 18));
              bntupload.setForeground(Color.BLACK);
              bntupload.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\upload_image.png", 
                                     50, 50, 10, SwingConstants.RIGHT, SwingConstants.CENTER);

              
           bntupload.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose an image");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        File storageDir = new File("D:/Image_Data");
                        if (!storageDir.exists()) {
                            storageDir.mkdirs(); // Tạo thư mục nếu chưa có
                        }

                        // Tạo tên file mới dựa trên tên file gốc
                        String originalFileName = selectedFile.getName();
                        File destFile = new File(storageDir, originalFileName);

                        // Kiểm tra xem file đã tồn tại hay chưa
                        if (destFile.exists()) {
                            // Nếu file đã tồn tại, không thay đổi tên và sử dụng file hiện có
                            image = destFile.getAbsolutePath();  // Sử dụng đường dẫn của file đã tồn tại
                            displayProductImage(destFile.getAbsolutePath());  // Hiển thị ảnh lên GUI
                        } else {
                            // Nếu file chưa tồn tại, sao chép ảnh vào thư mục
                            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            image = destFile.getAbsolutePath();  // Cập nhật đường dẫn file mới
                            displayProductImage(destFile.getAbsolutePath());  // Hiển thị ảnh lên GUI
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        new CustomDialog().showError("Cannot upload this image!");
                    }
                }
            });


        bg.add(bntupload, "pos 10 110, w 230!, h 230!"); // Đặt nút ở dưới panel ảnh



          // 3. Các thành phần khác - giữ nguyên vị trí như code gốc

          // Product.ID
          lblProductID = new JLabel("Product.ID");
          lblProductID.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblProductID.setForeground(Color.BLACK);
          bg.add(lblProductID, "pos 255 60, w 150!, h 50!");

          txtProductID = new MyTextField();
          txtProductID.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtProductID.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtProductID.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtProductID, "pos 360 68, w 130!, h 32!");

          // Product Name
          lblProductName = new JLabel("Product Name");
          lblProductName.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblProductName.setForeground(Color.BLACK);
          bg.add(lblProductName, "pos 255 160, w 150!, h 50!");

          txtProductName = new MyTextField();
          txtProductName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtProductName.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtProductName.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtProductName, "pos 390 168, w 200!, h 32!");

          // CPU
          lblCPU = new JLabel("CPU");
          lblCPU.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblCPU.setForeground(Color.BLACK);
          bg.add(lblCPU, "pos 255 255, w 130!, h 50!");

          txtCPU = new MyTextField();
          txtCPU.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtCPU.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtCPU.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtCPU, "pos 390 263, w 180!, h 32!");

          // RAM
          lblRam = new JLabel("Ram");
          lblRam.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblRam.setForeground(Color.BLACK);
          bg.add(lblRam, "pos 255 360, w 180!, h 50!");

          txtRam = new MyTextField();
          txtRam.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtRam.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtRam.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtRam, "pos 350 365, w 150!, h 32!");

          // Graphic card
          lblCard = new JLabel("Graphic card");
          lblCard.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblCard.setForeground(Color.BLACK);
          bg.add(lblCard, "pos 700 60, w 180!, h 50!");

          txtCard = new MyTextField();
          txtCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtCard.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtCard.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtCard, "pos 830 68, w 150!, h 32!");

          // Operate System
          lblOprerate = new JLabel("Operate System");
          lblOprerate.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblOprerate.setForeground(Color.BLACK);
          bg.add(lblOprerate, "pos 700 160, w 180!, h 50!");

          String[] items = {"Windows", "Mac OS"};
          cmbOperate = new MyCombobox<>(items);
          cmbOperate.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
          cmbOperate.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
          bg.add(cmbOperate, "pos 850 170, w 130!, h 35!");

          // Price
          lblPrice = new JLabel("Price");
          lblPrice.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblPrice.setForeground(Color.BLACK);
          bg.add(lblPrice, "pos 700 263, w 130!, h 50!");

          txtPrice = new MyTextField();
          txtPrice.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtPrice.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtPrice.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtPrice, "pos 800 270, w 180!, h 32!");

          // Quantity
          lblQuantity = new JLabel("Quantity");
          lblQuantity.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblQuantity.setForeground(Color.BLACK);
          bg.add(lblQuantity, "pos 700 365, w 130!, h 50!");

          SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 0, 1000, 1);
          spinnerQuantity = new JSpinner(quantityModel);
          JComponent editor = spinnerQuantity.getEditor();
          JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
          textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
          textField.setBackground(Color.WHITE);
          bg.add(spinnerQuantity, "pos 810 375, w 60!, h 30!");

          // Warranty Period
          lblwaranty = new JLabel("Warranty Period");
          lblwaranty.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblwaranty.setForeground(Color.BLACK);
          bg.add(lblwaranty, "pos 1080 60, w 140!, h 50!");

          txtwaranty = new MyTextField();
          txtwaranty.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtwaranty.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtwaranty.setBackgroundColor(Color.decode("#F0FFFF"));
          bg.add(txtwaranty, "pos 1250 68, w 150!, h 32!");

          // Broken Quantity
          lblSpoiled = new JLabel("Broken Quantity");
          lblSpoiled.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblSpoiled.setForeground(Color.BLACK);
          bg.add(lblSpoiled, "pos 1080 160, w 140!, h 50!");

          SpinnerNumberModel brokenquantityModel = new SpinnerNumberModel(0, 0, 1000, 1);
          spinderBrokenQuantity = new JSpinner(brokenquantityModel);
          JComponent editorbroken = spinderBrokenQuantity.getEditor();
          JFormattedTextField brokentextField = ((JSpinner.DefaultEditor) editorbroken).getTextField();
          brokentextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
          brokentextField.setBackground(Color.WHITE);
          bg.add(spinderBrokenQuantity, "pos 1250 170, w 60!, h 30!");

          // Category.ID
          lblCate = new JLabel("Category.ID");
          lblCate.setFont(new Font("sansserif", Font.PLAIN, 18));
          lblCate.setForeground(Color.BLACK);
          bg.add(lblCate, "pos 1080 263, w 130!, h 50!");

                    // Tạo Menu Bar và Menu chính
            JMenuBar menuBar = new JMenuBar();
            menu = new JMenu("Choose");
            menu.setFont(new Font("Times New Roman", Font.PLAIN, 16));

            // Lấy dữ liệu từ BUS
            busProduct = new BusProduct();
            List<DTOCategory> listCategory = busProduct.getAllCategoriesWithSupplier();

            // Map để nhóm Category theo Supplier
            Map<String, JMenu> supplierMenuMap = new LinkedHashMap<>();

            for (DTOCategory dto : listCategory) {
                String supplierID = dto.getSupID();
                String supplierName = dto.getSupName();
                String categoryID = dto.getCategoryID();

                // Nếu Supplier chưa tồn tại trong menu, tạo mới
                if (!supplierMenuMap.containsKey(supplierID)) {
                    JMenu supplierMenu = new JMenu(supplierID);
                    supplierMenu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    supplierMenuMap.put(supplierID, supplierMenu);
                    menu.add(supplierMenu);
                }

                // Thêm Category như là Detail vào Supplier Menu
                JMenuItem categoryItem = new JMenuItem(categoryID);
                supplierMenuMap.get(supplierID).add(categoryItem);

                // ✅ Gắn sự kiện: Khi chọn category -> đổi tên menu thành tên category
                categoryItem.addActionListener(e -> {
                    menu.setText(categoryID);
                });
            }

            // Thêm menu chính vào menu bar
            menuBar.add(menu);

            // Thêm menu bar vào giao diện
            bg.add(menuBar, "pos 1220 270, w 120!, h 30!");

                    // 3. Nút Save - sử dụng MigLayout
          bntUpdate = new MyButton("Update", 20);
          bntUpdate.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
          bntUpdate.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
          bntUpdate.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
          bntUpdate.setFont(new Font("Times New Roman", Font.BOLD, 16));
          bntUpdate.setForeground(Color.WHITE); 
          
       bntUpdate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            updatedProduct = null;
            try {
                // Lấy thông tin sản phẩm hiện tại từ database
                DTOProduct currentProduct = busProduct.getProductById(txtProductID.getText());
                if (currentProduct == null) {
                    new CustomDialog().showError("Không tìm thấy sản phẩm!");
                    return;
                }

                // Tạo đối tượng cập nhật
                updatedProduct = new DTOProduct();

                // Copy tất cả thông tin từ sản phẩm hiện tại
                updatedProduct.setProductID(currentProduct.getProductID());
                updatedProduct.setProductName(currentProduct.getProductName());
                updatedProduct.setPrice(currentProduct.getPrice());
                updatedProduct.setQuantity(currentProduct.getQuantity());
                updatedProduct.setCategoryID(currentProduct.getCategoryID());
                updatedProduct.setCpu(currentProduct.getCpu());
                updatedProduct.setRam(currentProduct.getRam());
                updatedProduct.setGraphicsCard(currentProduct.getGraphicsCard());
                updatedProduct.setOperatingSystem(currentProduct.getOperatingSystem());
                updatedProduct.setWarrantyPeriod(currentProduct.getWarrantyPeriod());
                updatedProduct.setImage(currentProduct.getImage()); // Giữ nguyên ảnh cũ mặc định

                // Cập nhật các trường thay đổi (nếu có)
                // Trường hợp 1: Chỉ cập nhật các thành phần khác, giữ nguyên ảnh
                if (!txtProductName.getText().equals(currentProduct.getProductName())) {
                    updatedProduct.setProductName(txtProductName.getText());
                }
                if (!txtPrice.getText().equals(currentProduct.getPrice().toString())) {
                    updatedProduct.setPrice(new BigDecimal(txtPrice.getText()));
                }
                if ((int)spinnerQuantity.getValue() != currentProduct.getQuantity()) {
                    updatedProduct.setQuantity((int) spinnerQuantity.getValue());
                }
                if (!menu.getText().equals(currentProduct.getCategoryID())) {
                    updatedProduct.setCategoryID(menu.getText());
                }
                if (!txtCPU.getText().equals(currentProduct.getCpu())) {
                    updatedProduct.setCpu(txtCPU.getText());
                }
                if (!txtRam.getText().equals(currentProduct.getRam())) {
                    updatedProduct.setRam(txtRam.getText());
                }
                if (!txtCard.getText().equals(currentProduct.getGraphicsCard())) {
                    updatedProduct.setGraphicsCard(txtCard.getText());
                }
                if (!cmbOperate.getSelectedItem().toString().equals(currentProduct.getOperatingSystem())) {
                    updatedProduct.setOperatingSystem(cmbOperate.getSelectedItem().toString());
                }
                if (!txtwaranty.getText().equals(currentProduct.getWarrantyPeriod())) {
                    updatedProduct.setWarrantyPeriod(txtwaranty.getText());
                }

                // Trường hợp 2 & 3: Cập nhật ảnh mới nếu có
                if (image != null && !image.isEmpty() && !image.equals(currentProduct.getImage())) {
                    updatedProduct.setImage(image);
                }

              try {
                boolean success = busProduct.updateProduct(updatedProduct);
                
                // Thông báo cho tất cả Form_Product biết để refresh
                ProductUpdateNotifier.getInstance().notifyProductUpdated();
                
                // Đóng form edit
                dispose();
                
                if (!success) {
                    throw new Exception("Cập nhật thất bại từ tầng BUS");
                }

                // Hiển thị kết quả
                if (image != null && !image.isEmpty()) {
                    displayProductImage(image);
                }
                
                refreshProductTable();
                
            } catch (Exception busEx) {
                throw new Exception("Lỗi khi gọi BUS: " + busEx.getMessage());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
             CustomDialog.showError("An error occurred while updating the product.");
        }
    }
});

          bg.add(bntUpdate, "w 100!, h 40!, span, align center, dock south, gapbottom 20");


           // Xử lý thay đổi kích thước cho font tiêu đề
           panelTitle.addComponentListener(new ComponentAdapter() {
               @Override
               public void componentResized(ComponentEvent e) {
                   float fontSize = Math.min(20f, Math.max(12f, panelTitle.getWidth() / 20f));
                   lblTitle.setFont(lblTitle.getFont().deriveFont(fontSize));
               }
           });
      }
   
        
        private void refreshProductTable() {
           SwingUtilities.invokeLater(() -> {
               Form_Product productForm = getProductFormInstance();
               if (productForm != null && productForm.tableProduct != null) {
                   DefaultTableModel model = (DefaultTableModel) productForm.tableProduct.getModel();
                   model.setRowCount(0); // Xóa dữ liệu cũ

                   // Load lại toàn bộ dữ liệu
                   busProduct.uploadProduct(model);

                   // Căn chỉnh cột và làm mới hiển thị
                   productForm.tableProduct.adjustColumnWidths();
                   productForm.tableProduct.revalidate();
                   productForm.tableProduct.repaint();

                   // Tìm và chọn lại dòng vừa cập nhật
                   selectUpdatedRow(productForm, updatedProduct.getProductID());
               }
           });
       }

       private void selectUpdatedRow(Form_Product form, String productId) {
           for (int i = 0; i < form.tableProduct.getRowCount(); i++) {
               if (form.tableProduct.getValueAt(i, 0).equals(productId)) {
                   form.tableProduct.setRowSelectionInterval(i, i);
                   form.tableProduct.scrollRectToVisible(form.tableProduct.getCellRect(i, 0, true));
                   break;
               }
           }
       }

       // Hàm lấy instance Form_Product
       private Form_Product getProductFormInstance() {
           // Thay thế bằng cách lấy instance thực tế của bạn
           return this.product != null ? this.product : new Form_Product();
       }
       
 
    public void displayProductImage(String imagePath) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Kiểm tra đường dẫn ảnh rỗng
                if (imagePath == null || imagePath.trim().isEmpty()) {
                    showDefaultPlaceholder();
                    return;
                }

                // Kiểm tra file ảnh tồn tại
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    System.out.println("Ảnh không tồn tại tại: " + imagePath);
                    showDefaultPlaceholder();
                    return;
                }

                // Đọc ảnh với nhiều định dạng
                BufferedImage originalImage = null;
                try {
                    originalImage = ImageIO.read(imageFile);
                } catch (IOException e) {
                    System.err.println("Không thể đọc file ảnh: " + e.getMessage());
                    showDefaultPlaceholder();
                    return;
                }

                if (originalImage == null) {
                    showDefaultPlaceholder();
                    return;
                }

                // Scale ảnh
                int maxWidth = 230;
                int maxHeight = 230;
                int originalWidth = originalImage.getWidth();
                int originalHeight = originalImage.getHeight();

                double scaleFactor = Math.min(
                    (double) maxWidth / originalWidth,
                    (double) maxHeight / originalHeight
                );

                int newWidth = (int) (originalWidth * scaleFactor);
                int newHeight = (int) (originalHeight * scaleFactor);

                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);

                // Hiển thị ảnh
                updateImagePanel(icon);

            } catch (Exception e) {
                e.printStackTrace();
                showDefaultPlaceholder();
            }
        });
    }

       private void showDefaultPlaceholder() {
            // Tạo ảnh placeholder đơn giản
            BufferedImage placeholder = new BufferedImage(230, 230, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = placeholder.createGraphics();
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 230, 230);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("No Image", 80, 120);
            g2d.dispose();

            updateImagePanel(new ImageIcon(placeholder));
        }

        private void updateImagePanel(ImageIcon icon) {
            panelUpload.removeAll();
            panelUpload.setLayout(new MigLayout("insets 0, gap 0, fill"));

            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);

            panelUpload.add(imageLabel, "pos 0.5al 0.5al");
            panelUpload.setVisible(true);
            panelUpload.revalidate();
            panelUpload.repaint();
        }
            @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        bg = new javax.swing.JLayeredPane();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1414, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditProduct().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration                   
}

