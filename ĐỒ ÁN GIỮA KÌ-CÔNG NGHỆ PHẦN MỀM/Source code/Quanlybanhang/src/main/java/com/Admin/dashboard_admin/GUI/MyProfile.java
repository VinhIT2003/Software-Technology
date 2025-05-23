
package com.Admin.dashboard_admin.GUI;

import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.Admin.dashboard_admin.BUS.BusProfile_ad;
import com.Admin.dashboard_admin.DTO.DTOProfile_ad;
import com.Admin.login.GUI.PanelLoginandRegister;
import com.ComponentandDatabase.Components.CustomDialog;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class MyProfile extends javax.swing.JFrame {
     public JLabel lblTitle, lblID, lblFullName, lblGender, lblEmail, lblContact;
     public MyPanel panelTitle;
     public MyCombobox cmbGender;
     private MyTextField txtID;
     public MyTextField txtFullName, txtEmail, txtContact;
     public JPanel panelUpload;
     public MyButton bntUpload, bntUpdate;
     private BusProfile_ad busProfile;
     public static String selectedImagePath = null; // Lưu đường dẫn ảnh đã xử lý
     private DTOProfile_ad DTOProfile;
    
    public MyProfile() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        init();
        loadProfile();
    }
    public void init(){
      // Thiết lập layout chính với các cột và dòng linh hoạt
          bg.setLayout(new MigLayout("fill, insets 0", "[grow]", "[][grow]"));

          // 1. Panel tiêu đề (tự động co giãn theo chiều ngang)
          panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
          panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

          lblTitle = new JLabel("My Profile", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
        bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng mới
          
          lblID= new JLabel("ID");
          lblID.setFont(new Font("sansserif", Font.BOLD, 18));
          lblID.setForeground(Color.BLACK);
          bg.add(lblID, "pos 30 120, w 150!, h 50!");
          
          lblFullName= new JLabel("Full name");
          lblFullName.setFont(new Font("sansserif", Font.BOLD, 18));
          lblFullName.setForeground(Color.BLACK);
          bg.add(lblFullName, "pos 30 180, w 150!, h 50!");
          
          lblGender= new JLabel("Gender");
          lblGender.setFont(new Font("sansserif", Font.BOLD, 18));
          lblGender.setForeground(Color.BLACK);
          bg.add(lblGender, "pos 30 240, w 150!, h 50!");
          
          lblEmail= new JLabel("Email");
          lblEmail.setFont(new Font("sansserif", Font.BOLD, 18));
          lblEmail.setForeground(Color.BLACK);
          bg.add(lblEmail, "pos 30 300, w 150!, h 50!");
          
          lblContact= new JLabel("Contact");
          lblContact.setFont(new Font("sansserif", Font.BOLD, 18));
          lblContact.setForeground(Color.BLACK);
          bg.add(lblContact, "pos 30 360, w 230!, h 50!");
          
          txtID = new MyTextField();
          txtID.setBorder(null);
          txtID.setTextColor(Color.BLUE); // Đặt màu mong muốn
          txtID.setLocked(true); // Gọi sau cũng không sao
          txtID.setTextFont(new Font("Times New Roman", Font.BOLD, 16));
          txtID.setBackgroundColor(Color.WHITE);

          bg.add(txtID, "pos 50 130, w 230!, h 32!");
            
          txtFullName = new MyTextField();
          txtFullName.setBorder(null);
          txtFullName.setTextFont(new Font("Times new roman", Font.BOLD, 16));
          txtFullName.setTextColor(Color.red);
          txtFullName.setBackgroundColor(Color.WHITE);
          bg.add(txtFullName, "pos 120 190, w 230!, h 32!");
          
          String[] items = {"Male", "Female"};
          cmbGender = new MyCombobox<>(items);
          cmbGender.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
          cmbGender.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
          cmbGender.repaint();
          cmbGender.revalidate();
          bg.add(cmbGender, "pos 130 250, w 125!, h 35!");
          
          txtEmail = new MyTextField();
          txtEmail.setBorder(null);
          txtEmail.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtEmail.setBackgroundColor(Color.WHITE);
          bg.add(txtEmail, "pos 80 310, w 230!, h 32!");
          
          txtContact = new MyTextField();
          txtContact.setBorder(null);
          txtContact.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtContact.setBackgroundColor(Color.WHITE);
          bg.add(txtContact, "pos 100 370, w 230!, h 32!");
          
           panelUpload = new JPanel(new MigLayout("insets 0, gap 0, fill"));
           panelUpload.setBackground(Color.WHITE);
           panelUpload.setVisible(false);
           panelUpload.setPreferredSize(new Dimension(150,200));

           JLabel lblUploadImage = new JLabel("Upload Image");
           lblUploadImage.setFont(new Font("Arial", Font.PLAIN, 10));
           panelUpload.add(lblUploadImage, "pos 0.5al 0.5al");

           bg.add(panelUpload, "pos 150 40, w 100!, h 90!");  // Điều chỉnh đúng kích thước panel
           
                       // ===== NÚT UPLOAD ẢNH =====
           bntUpload = new MyButton("", 0);
           bntUpload.setBackgroundColor(Color.WHITE);
           bntUpload.setPressedColor(Color.decode("#D3D3D3"));
           bntUpload.setHoverColor(Color.decode("#EEEEEE"));
           bntUpload.setFont(new Font("sansserif", Font.BOLD, 18));
           bntUpload.setForeground(Color.BLACK);
           bntUpload.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\upload_profile.png", 
                                     50, 50, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           
           bntUpload.addActionListener((e) -> {
               uploadImage();
           });
         // Thêm nút upload vào vị trí mong muốn
           bg.add(bntUpload, "pos 150 50, w 100!, h 90!");
           
                    // 3. Nút Save - sử dụng MigLayout
          bntUpdate = new MyButton("Update", 20);
          bntUpdate.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
          bntUpdate.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
          bntUpdate.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
          bntUpdate.setFont(new Font("Times New Roman", Font.BOLD, 16));
          bntUpdate.setForeground(Color.WHITE); 
          bntUpdate.addActionListener((e) -> {
              String id = txtID.getText().strip();
              String name = txtFullName.getText().strip();
              String gender = cmbGender.getSelectedItem().toString();
              String email = txtEmail.getText().strip();
              String contact = txtContact.getText().strip();
              DTOProfile= new DTOProfile_ad(id, name, gender, email, contact, selectedImagePath);
              busProfile= new BusProfile_ad();
              boolean success= busProfile.updateProfile(DTOProfile);
               if (success) {
                   CustomDialog.showSuccess("Profile updated successfully !");
                } else {
                    CustomDialog.showError("Profile updated failure !");
                }
          });
          
          bg.add(bntUpdate, "w 100!, h 30!, span, align center, dock south, gapbottom 10");
          
    }
    
    public void loadProfile() {
        // Khởi tạo BUS
        busProfile = new BusProfile_ad();

        // Lấy admin ID từ phần đăng nhập
       String admin_id = Dashboard_ad.adminID;
    
    
        // Gọi BUS để lấy dữ liệu profile
        DTOProfile = busProfile.showProfile(admin_id, panelUpload);

        // Nếu dữ liệu tồn tại
        if (DTOProfile != null) {
            txtID.setText(DTOProfile.getAdminID());
            txtFullName.setText(DTOProfile.getAdminName());
            cmbGender.setSelectedItem(DTOProfile.getGender());
            txtEmail.setText(DTOProfile.getEmail());
            txtContact.setText(DTOProfile.getContact());
            panelUpload.setVisible(true);
           
        } else {
            CustomDialog.showError("This profile was not found !");
        }
    }

    private void uploadImage() {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select Profile Picture");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
      fileChooser.setFileFilter(filter);

      int result = fileChooser.showOpenDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          String fileName = selectedFile.getName();

          // Tạo thư mục đích nếu chưa tồn tại
          File destFolder = new File("D:\\Profile_Image");
          if (!destFolder.exists()) {
              destFolder.mkdirs();
          }

          // Đường dẫn file đích
          File destFile = new File(destFolder, fileName);

          // Copy file nếu chưa tồn tại
          if (!destFile.exists()) {
              try {
                  Files.copy(selectedFile.toPath(), destFile.toPath());
              } catch (IOException e) {
                  e.printStackTrace();
                  CustomDialog.showError("Error copying the image!");
                  return;
              }
          }

          selectedImagePath = destFile.getAbsolutePath();

          try {
              // Đọc ảnh và tính toán kích thước mới
              BufferedImage originalImage = ImageIO.read(destFile);
              int panelWidth = panelUpload.getWidth();
              int panelHeight = panelUpload.getHeight();

              // Scale ảnh bằng Thumbnailator (giữ tỷ lệ + chất lượng cao)
              BufferedImage scaledImage = Thumbnails.of(originalImage)
                  .size(panelWidth, panelHeight)
                  .keepAspectRatio(true)  // Giữ nguyên tỷ lệ
                  .outputQuality(1.0)     // Chất lượng 100%
                  .asBufferedImage();

              // Hiển thị ảnh
              JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
              imageLabel.setHorizontalAlignment(JLabel.CENTER);
              imageLabel.setVerticalAlignment(JLabel.CENTER);

              panelUpload.removeAll();
              panelUpload.add(imageLabel, "pos 0.5al 0.5al");
              panelUpload.revalidate();
              panelUpload.repaint();

              CustomDialog.showSuccess("Image uploaded successfully!");
          } catch (Exception ex) {
              ex.printStackTrace();
              CustomDialog.showError("Failed to display image!");
          }
      }
  }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
