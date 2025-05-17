
package com.User.Cart.GUI;

import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.User.Cart.BUS.BUSCart;
import com.User.Cart.DTO.DTOCart;
import com.User.dashboard_user.BUS.BUSProfile_cus;
import com.User.dashboard_user.GUI.MyProfile_cus;
import com.User.home.BUS.productBUS;
import com.User.home.DTO.productDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import net.coobird.thumbnailator.Thumbnails;
import net.miginfocom.swing.MigLayout;

public class CartDetails extends javax.swing.JFrame {
     public JLabel lblTitle, lblID, lblProductName, lblCPU, lblRam, lblGraphicCard, lblOperating, lblWarranty, lblCateID
             , lblBrand, lblQuantity;
     public MyPanel panelTitle;
     public MyCombobox cmbGender;
     public static MyTextField txtID, txtProductName, txtCPU, txtRam, txtGraphicCard, txtOperating, txtWarranty, txtCateID, txtBrand, txtQuantity;
     private JDateChooser dateOfBirth;
     private JTextArea txtAddress;
     public JPanel panelUpload;
     public MyButton bntUpload, bntAddcart;
//     public JSpinner spinnerQuantity;
     private BUSProfile_cus busProfile;
     private productBUS busProduct;
     private BUSCart busCart;
     private MyProfile_cus profile;
    
    public CartDetails() {
        initComponents();
        setSize(630, 800); 
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 

        // Tính toán vị trí để căn giữa và trên cùng
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        setLocation(x, 0); // y = 0 để nằm trên cùng

        setAlwaysOnTop(true); // Luôn hiển thị trên cùng
        init();
    }
    public void init() {
     // Thiết lập layout chính
     bg.setLayout(new MigLayout("fillx, insets 0", "[grow]", "[][][grow]"));

     // 1. Panel tiêu đề
     panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
     panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

     lblTitle = new JLabel("Product Details", JLabel.CENTER);
     lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
     lblTitle.setForeground(Color.WHITE);

     panelTitle.add(lblTitle, "grow, push, align center");
     bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng

        // Panel upload ảnh
    panelUpload = new JPanel();
    panelUpload.setLayout(new MigLayout("fill, insets 5")); // Layout fill với padding 5px
    panelUpload.setBackground(Color.WHITE);
    panelUpload.setPreferredSize(new Dimension(200, 200));
    panelUpload.setBorder(null);

    // Thêm vào bg
    bg.add(panelUpload, "w 200!, h 200!, gap 0, align center, wrap");

     lblID= new JLabel("Product.ID");
     lblID.setFont(new Font("sansserif", Font.BOLD, 18));
     lblID.setForeground(Color.BLACK);
     bg.add(lblID, "pos 30 220, w 150!, h 50!");
  
    lblProductName= new JLabel("Product name");
    lblProductName.setFont(new Font("sansserif", Font.BOLD, 18));
    lblProductName.setForeground(Color.BLACK);
    bg.add(lblProductName, "pos 30 280, w 150!, h 50!");
     
     lblCPU= new JLabel("CPU");
     lblCPU.setFont(new Font("sansserif", Font.BOLD, 18));
     lblCPU.setForeground(Color.BLACK);
     bg.add(lblCPU, "pos 30 340, w 400!, h 50!");
     
       
     lblRam= new JLabel("Ram");
     lblRam.setFont(new Font("sansserif", Font.BOLD, 18));
     lblRam.setForeground(Color.BLACK);
     bg.add(lblRam, "pos 30 400, w 150!, h 50!");
     
       
     lblGraphicCard= new JLabel("Graphics Card");
     lblGraphicCard.setFont(new Font("sansserif", Font.BOLD, 18));
     lblGraphicCard.setForeground(Color.BLACK);
     bg.add(lblGraphicCard, "pos 30 460, w 150!, h 50!");
     
     lblOperating= new JLabel("Operating System");
     lblOperating.setFont(new Font("sansserif", Font.BOLD, 18));
     lblOperating.setForeground(Color.BLACK);
     bg.add(lblOperating, "pos 30 520, w 160!, h 50!");
     
     lblWarranty= new JLabel("Warranty Period");
     lblWarranty.setFont(new Font("sansserif", Font.BOLD, 18));
     lblWarranty.setForeground(Color.BLACK);
     bg.add(lblWarranty, "pos 30 580, w 150!, h 50!");
     
          
     lblCateID= new JLabel("Category.ID");
     lblCateID.setFont(new Font("sansserif", Font.BOLD, 18));
     lblCateID.setForeground(Color.BLACK);
     bg.add(lblCateID, "pos 30 640, w 150!, h 50!");
     
     
     lblBrand= new JLabel("Brand");
     lblBrand.setFont(new Font("sansserif", Font.BOLD, 18));
     lblBrand.setForeground(Color.BLACK);
     bg.add(lblBrand, "pos 380 580, w 150!, h 50!");
     
     lblQuantity= new JLabel("Quantity");
     lblQuantity.setFont(new Font("sansserif", Font.BOLD, 18));
     lblQuantity.setForeground(Color.BLACK);
     bg.add(lblQuantity, "pos 380 640, w 150!, h 50!");
     
     
     txtID = new MyTextField();
     txtID.setBorder(null);
     txtID.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtID.setLocked(true); // Gọi sau cũng không sao
     txtID.setTextFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
     txtID.setBackgroundColor(Color.WHITE);

     bg.add(txtID, "pos 130 230, w 230!, h 32!");
     
     txtProductName = new MyTextField();
     txtProductName.setBorder(null);
     txtProductName.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtProductName.setLocked(true);
     txtProductName.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtProductName.setBackgroundColor(Color.WHITE);

     bg.add(txtProductName, "pos 150 290, w 480!, h 32!");
     
     
     txtCPU = new MyTextField();
     txtCPU.setBorder(null);
     txtCPU.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtCPU.setLocked(true);
     txtCPU.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtCPU.setBackgroundColor(Color.WHITE);

     bg.add(txtCPU, "pos 100 350, w 480!, h 32!");
     
     txtRam = new MyTextField();
     txtRam.setBorder(null);
     txtRam.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtRam.setLocked(true);
     txtRam.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtRam.setBackgroundColor(Color.WHITE);

     bg.add(txtRam, "pos 100 410, w 130!, h 32!");
     
     
     txtGraphicCard = new MyTextField();
     txtGraphicCard.setBorder(null);
     txtGraphicCard.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtGraphicCard.setLocked(true);
     txtGraphicCard.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtGraphicCard.setBackgroundColor(Color.WHITE);

     bg.add(txtGraphicCard, "pos 180 470, w 450!, h 32!");
     
     txtOperating = new MyTextField();
     txtOperating.setBorder(null);
     txtOperating.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtOperating.setLocked(true);
     txtOperating.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtOperating.setBackgroundColor(Color.WHITE);

     bg.add(txtOperating, "pos 190 530, w 180!, h 32!");
     
     txtWarranty = new MyTextField();
     txtWarranty.setBorder(null);
     txtWarranty.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtWarranty.setLocked(true);
     txtWarranty.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtWarranty.setBackgroundColor(Color.WHITE);

     bg.add(txtWarranty, "pos 170 590, w 200!, h 32!");
     
     txtCateID = new MyTextField();
     txtCateID.setBorder(null);
     txtCateID.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtCateID.setLocked(true);
     txtCateID.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtCateID.setBackgroundColor(Color.WHITE);

     bg.add(txtCateID, "pos 150 650, w 150!, h 32!");
     
     txtBrand = new MyTextField();
     txtBrand.setBorder(null);
     txtBrand.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtBrand.setLocked(true);
     txtBrand.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtBrand.setBackgroundColor(Color.WHITE);

     bg.add(txtBrand, "pos 430 590, w 150!, h 32!");
     
     txtQuantity = new MyTextField();
     txtQuantity.setBorder(null);
     txtQuantity.setTextColor(Color.BLACK); // Đặt màu mong muốn
     txtQuantity.setLocked(true);
     txtQuantity.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtQuantity.setBackgroundColor(Color.WHITE);
    
     bg.add(txtQuantity, "pos 460 650, w 60!, h 30!");
     
    }
    
    public void displayProductDetails(productDTO product) {
        busProduct = new productBUS();
        panelUpload.removeAll();
        panelUpload.setLayout(new MigLayout("fill, insets 0"));

        if (product.getImage() != null && !product.getImage().isEmpty()) {
            try {
                BufferedImage originalImage = Thumbnails.of(new File(product.getImage()))
                    .scale(1)
                    .asBufferedImage();

                int targetWidth = panelUpload.getWidth() - 20;
                int targetHeight = panelUpload.getHeight() - 20;

                double widthRatio = (double)targetWidth / originalImage.getWidth();
                double heightRatio = (double)targetHeight / originalImage.getHeight();
                double ratio = Math.min(widthRatio, heightRatio);

                int newWidth = (int)(originalImage.getWidth() * ratio);
                int newHeight = (int)(originalImage.getHeight() * ratio);

                BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(newWidth, newHeight)
                    .keepAspectRatio(true)
                    .outputQuality(1.0)
                    .asBufferedImage();

                JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);

                panelUpload.add(imageLabel, "w 100%, h 100%, gap 0, align center");

            } catch (IOException e) {
                JLabel errorLabel = new JLabel("Không thể tải ảnh", SwingConstants.CENTER);
                panelUpload.add(errorLabel, "w 100%, h 100%, align center");
            }
        } else {
            JLabel noImageLabel = new JLabel("No image", SwingConstants.CENTER);
            panelUpload.add(noImageLabel, "w 100%, h 100%, align center");
        }

        // Hiển thị thông tin sản phẩm
        txtID.setText(product.getProductID());
        txtProductName.setText(product.getProductName());
        txtCPU.setText(product.getCpu());
        txtRam.setText(product.getRam());
        txtGraphicCard.setText(product.getGraphicsCard());
        txtOperating.setText(product.getOperatingSystem());
        txtWarranty.setText(product.getWarrantyPeriod());
        txtCateID.setText(product.getCategoryID());
        txtQuantity.setText(String.valueOf(product.getQuantity()));
        String brand = busProduct.getBrandByProductId(product.getProductID());
        txtBrand.setText(brand != null ? brand : "N/A");
       

             // Cập nhật giao diện
        panelUpload.revalidate();
        panelUpload.repaint();
  }

   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 407, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger( CartDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger( CartDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger( CartDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger( CartDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CartDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    // End of variables declaration                   
}

