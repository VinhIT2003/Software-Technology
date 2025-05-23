
package com.Admin.product.GUI;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.product.BUS.BusProduct;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import com.Admin.category.DTO.DTOCategory;
import com.Admin.product.BUS.BusProduct;
import com.Admin.product.DTO.DTOProduct;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class NewProduct extends javax.swing.JFrame {
     private int mouseX, mouseY;
     private JLabel lblTitle, lblProductID, lblProductName, lblCPU, lblRam, 
             lblCard, lblOprerate, lblPrice, lblQuantity, lblwaranty, lblSpoiled, lblCate ;
     private MyPanel panelTitle;
     private MyTextField txtProductID, txtProductName, txtCPU, txtRam, txtCard, txtPrice, txtwaranty;
     private MyCombobox cmbOperate;
     private MyButton bntupload, bntSave, bntReset;
     private JPanel panelUpload;
     private JSpinner spinnerQuantity, spinderBrokenQuantity;
     private JMenu menu;
     private String image;
      private BusProduct busProduct;

    public NewProduct() {
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

          lblTitle = new JLabel("New Products", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
          bg.add(panelTitle, "growx, h 40!, wrap");
          

            // 2. Panel Upload (ẩn ban đầu)
            panelUpload = new JPanel();
            panelUpload.setLayout(new MigLayout("fill, insets 0"));
            panelUpload.setBackground(Color.LIGHT_GRAY); // Bạn có thể thay đổi màu nền hoặc đặt ảnh vào đây
            panelUpload.setVisible(false); // Ẩn panelUpload lúc đầu

            JLabel lblUploadImage = new JLabel("Upload Image Here");
            lblUploadImage.setFont(new Font("Arial", Font.PLAIN, 16));
            panelUpload.add(lblUploadImage, "center");
            bg.add(panelUpload);
    
          // 2. Nút Upload (vị trí như ban đầu)
          bntupload = new MyButton("Upload", 0);
          bntupload.setBackgroundColor(Color.WHITE);
          bntupload.setPressedColor(Color.decode("#D3D3D3"));
          bntupload.setHoverColor(Color.decode("#EEEEEE"));
          bntupload.setFont(new Font("sansserif", Font.BOLD, 18));
          bntupload.setForeground(Color.BLACK);
          bntupload.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\upload_image.png", 
                                50, 50, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          
                    // Thêm sự kiện click vào nút Upload
           busProduct= new BusProduct();
          // Thêm sự kiện click vào nút Upload
            bntupload.addActionListener(e -> {
                // Gọi phương thức từ BusProduct để xử lý việc tải ảnh lên
                // Truyền panel hoặc nơi cần thiết để hiển thị ảnh, nếu cần
                busProduct.handleUpload(bntupload, panelUpload); // panel là nơi bạn muốn hiển thị ảnh
                image = busProduct.getImagePath(); // <-- Đây là imagePath mới
            });


          bg.add(bntupload, "pos 10 220, w 180!, h 50!");
          
          
          
          // 2. Nút Upload (vị trí như ban đầu)
          bntReset = new MyButton("Reset", 0);
          bntReset.setBackgroundColor(Color.WHITE);
          bntReset.setPressedColor(Color.decode("#D3D3D3"));
          bntReset.setHoverColor(Color.decode("#EEEEEE"));
          bntReset.setFont(new Font("sansserif", Font.PLAIN, 16));
          bntReset.setForeground(Color.BLACK);
          bntReset.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\reset.png", 
                                25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          
          bntReset.addActionListener((e) -> {
              resetForm();
          });
          bg.add(bntReset, "pos 10 50, w 110!, h 30!");
          
          

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

          SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 1000, 1);
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
          bntSave = new MyButton("Save", 20);
          bntSave.setBackgroundColor(Color.decode("#E55454")); // Màu nền
          bntSave.setPressedColor(Color.decode("#C04444")); // Màu khi nhấn
          bntSave.setHoverColor(Color.decode("#FF7F7F")); // Màu khi rê chuột vào
          bntSave.setFont(new Font("Times New Roman", Font.BOLD, 16));
          bntSave.setForeground(Color.WHITE);
          bntSave.addActionListener(e -> {
                saveProductFromGUI();  // Lấy dữ liệu từ GUI và lưu vào cơ sở dữ liệu
                // Thông báo cho tất cả Form_Product biết để refresh
                ProductUpdateNotifier.getInstance().notifyProductUpdated();
            });

          // Thêm nút Save vào layout - nằm dưới cùng và chính giữa
          bg.add(bntSave, "w 100!, h 40!, span, align center, dock south, gapbottom 20");

                    // Gán sự kiện ActionListener cho nút Save
          

           // Xử lý thay đổi kích thước cho font tiêu đề
           panelTitle.addComponentListener(new ComponentAdapter() {
               @Override
               public void componentResized(ComponentEvent e) {
                   float fontSize = Math.min(20f, Math.max(12f, panelTitle.getWidth() / 20f));
                   lblTitle.setFont(lblTitle.getFont().deriveFont(fontSize));
               }
           });
      }
        
     // Phương thức lấy dữ liệu từ giao diện và gọi phương thức saveProduct
    public void saveProductFromGUI() {
      String productID = txtProductID.getText().trim();
      String productName = txtProductName.getText().trim();
      String cpu = txtCPU.getText().trim();
      String ram = txtRam.getText().trim();
      String graphicsCard = txtCard.getText().trim();
      String operatingSystem = (cmbOperate.getSelectedItem() != null) ? cmbOperate.getSelectedItem().toString() : "";
      String priceStr = txtPrice.getText().trim();
      String warrantyPeriod = txtwaranty.getText().trim();
      String categoryID = menu.getText().trim();
      String imagePath = busProduct.getImagePath();

      int quantity = (int) spinnerQuantity.getValue();
      int spoiledQuantity = (int) spinderBrokenQuantity.getValue();

      CustomDialog dialog= new CustomDialog();
      // ✅ Kiểm tra các trường bắt buộc
      if (productID.isEmpty() || productName.isEmpty() || cpu.isEmpty() || ram.isEmpty() || graphicsCard.isEmpty()
          || operatingSystem.isEmpty() || priceStr.isEmpty() || warrantyPeriod.isEmpty() || categoryID.isEmpty()) {
          dialog.showError("Please fill in all the required fields!");
          return;
      }

      // ✅ Kiểm tra định dạng giá
      BigDecimal price;
      try {
          price = new BigDecimal(priceStr);
          if (price.compareTo(BigDecimal.ZERO) < 0) {
              dialog.showError("Price must be greater than or equal to 0!");
              return;
          }
      } catch (NumberFormatException e) {
          dialog.showError("Invalid price format!");
          return;
      }


      // ✅ Kiểm tra ảnh có được upload chưa
      if (imagePath == null || imagePath.isEmpty()) {
          dialog.showError("Please upload an image for the product!");
          return;
      }

        // ✅ Kiểm tra danh mục chưa được chọn hoặc còn giữ mặc định
        if (categoryID.equals("Choose") || categoryID.equals("Select category") || categoryID.isEmpty()) {
            dialog.showError("Please select a product category!");
            return;
        }

      // Tạo đối tượng DTOProduct sau khi kiểm tra
      DTOProduct product = new DTOProduct(
          productID, productName, cpu, ram, graphicsCard, operatingSystem, 
          price, quantity, warrantyPeriod, "Available", spoiledQuantity, categoryID, imagePath
      );

      // Lưu sản phẩm
      busProduct = new BusProduct();
      busProduct.saveProduct(product);
  }

     private void resetForm() {
        // 1. Clear text fields
        txtProductID.setText("");
        txtProductName.setText("");
        txtCPU.setText("");
        txtRam.setText("");
        txtCard.setText("");
        txtwaranty.setText("");
        txtPrice.setText("");

        // 2. Reset combobox về mục đầu tiên
        cmbOperate.setSelectedIndex(0);

        // 3. Reset spinner về giá trị mặc định
        spinnerQuantity.setValue(1);
        spinderBrokenQuantity.setValue(0);

        // 4. Ẩn lại panel upload
        panelUpload.setVisible(false);

        // 5. Xóa đường dẫn ảnh
        image = null;
    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewProduct().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
