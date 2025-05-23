
package com.User.dashboard_user.GUI;


import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.User.dashboard_user.BUS.BUSProfile_cus;
import com.User.login_user.GUI.PanelLoginandRegister_User;
import com.ComponentandDatabase.Components.CustomDialog;
import com.toedter.calendar.JDateChooser;
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
import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class MyProfile_cus extends javax.swing.JFrame {
     public JLabel lblTitle, lblID, lblFullName, lblGender, lblDateofBirth, lblEmail, lblContact, lblAddress;
     public MyPanel panelTitle;
     public MyCombobox cmbGender;
     public static MyTextField txtID, txtFullName, txtEmail, txtContact;
     private JDateChooser dateOfBirth;
     private JTextArea txtAddress;
     public JPanel panelUpload;
     public MyButton bntUpload, bntUpdate;
     private BUSProfile_cus busProfile;
    
    public MyProfile_cus() {
        initComponents();
        setSize(430, 650); 
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        init();
        showProfile();

    }
    public void init(){
      // Thiết lập layout chính với các cột và dòng linh hoạt
          bg.setLayout(new MigLayout("fill, insets 0", "[grow]", "[][grow]"));

          // 1. Panel tiêu đề (tự động co giãn theo chiều ngang)
          panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
          panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

          lblTitle = new JLabel("Your Information", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
          bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng mới
          
          lblID= new JLabel("ID Card");
          lblID.setFont(new Font("sansserif", Font.BOLD, 18));
          lblID.setForeground(Color.BLACK);
          bg.add(lblID, "pos 30 50, w 150!, h 50!");
          
          lblFullName= new JLabel("Full name");
          lblFullName.setFont(new Font("sansserif", Font.BOLD, 18));
          lblFullName.setForeground(Color.BLACK);
          bg.add(lblFullName, "pos 30 110, w 150!, h 50!");
          
          lblGender= new JLabel("Gender");
          lblGender.setFont(new Font("sansserif", Font.BOLD, 18));
          lblGender.setForeground(Color.BLACK);
          bg.add(lblGender, "pos 30 170, w 150!, h 50!");
          
          lblDateofBirth= new JLabel("Date of Birth");
          lblDateofBirth.setFont(new Font("sansserif", Font.BOLD, 18));
          lblDateofBirth.setForeground(Color.BLACK);
          bg.add(lblDateofBirth, "pos 30 240, w 150!, h 50!");
          
          
          lblEmail= new JLabel("Email");
          lblEmail.setFont(new Font("sansserif", Font.BOLD, 18));
          lblEmail.setForeground(Color.BLACK);
          bg.add(lblEmail, "pos 30 300, w 150!, h 50!");
          
          lblContact= new JLabel("Contact");
          lblContact.setFont(new Font("sansserif", Font.BOLD, 18));
          lblContact.setForeground(Color.BLACK);
          bg.add(lblContact, "pos 30 360, w 230!, h 50!");
          
          lblAddress= new JLabel("Address");
          lblAddress.setFont(new Font("sansserif", Font.BOLD, 18));
          lblAddress.setForeground(Color.BLACK);
          bg.add(lblAddress, "pos 30 420, w 230!, h 50!");
          
          txtID = new MyTextField();
          txtID.setBorder(null);
          txtID.setTextColor(Color.BLUE); // Đặt màu mong muốn
          txtID.setLocked(true); // Gọi sau cũng không sao
          txtID.setTextFont(new Font("Times New Roman", Font.BOLD, 16));
          txtID.setBackgroundColor(Color.WHITE);
          bg.add(txtID, "pos 100 60, w 230!, h 32!");
            
          txtFullName = new MyTextField();
          txtFullName.setBorder(null);
          txtFullName.setTextFont(new Font("Times new roman", Font.BOLD, 16));
          txtFullName.setTextColor(Color.red);
          txtFullName.setBackgroundColor(Color.WHITE);
          bg.add(txtFullName, "pos 120 120, w 230!, h 32!");
          
          String[] items = {"Male", "Female"};
          cmbGender = new MyCombobox<>(items);
          cmbGender.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
          cmbGender.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
          cmbGender.repaint();
          cmbGender.revalidate();
          bg.add(cmbGender, "pos 130 183, w 120!, h 35!");
          
          
                // Tạo JDateChooser
         dateOfBirth = new JDateChooser();
         dateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 18));
         dateOfBirth.setDateFormatString("dd/MM/yyyy");  // Định dạng ngày theo kiểu dd/MM/yyyy
         dateOfBirth.setBackground(Color.WHITE);
         bg.add(dateOfBirth, "pos 160 250, w 160!, h 35!");
          
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
          
            // Tạo JTextArea giống CTkTextbox (hỗ trợ nhiều dòng)
        txtAddress = new JTextArea();
        txtAddress.setFont(new Font("Times new roman", Font.PLAIN, 16));
        txtAddress.setBorder(null);
        txtAddress.setLineWrap(true); // Xuống dòng tự động khi hết chiều rộng
        txtAddress.setWrapStyleWord(true); // Xuống dòng theo từ (không cắt từ)

        // Đặt JScrollPane để có thể cuộn nếu nội dung dài
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        scrollAddress.setBorder(null);
         bg.add(scrollAddress,  "pos 120 435, w 290!, h 100!");
                    // 3. Nút Save - sử dụng MigLayout
          bntUpdate = new MyButton("Update", 20);
          bntUpdate.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
          bntUpdate.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
          bntUpdate.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
          bntUpdate.setFont(new Font("Times New Roman", Font.BOLD, 16));
          bntUpdate.setForeground(Color.WHITE); 
         
          bntUpdate.addActionListener((e) -> {
              busProfile= new BUSProfile_cus();
              busProfile.updateProfile(txtID, txtFullName, cmbGender, dateOfBirth, txtEmail, txtContact, txtAddress);
              
          });
          bg.add(bntUpdate, "w 100!, h 30!, span, align center, dock south, gapbottom 10");
          
    }
    
  public void showProfile(){
      busProfile= new BUSProfile_cus();
      String email= Dashboard_user.email;
      busProfile.showProfile(email, txtID, txtFullName, cmbGender, dateOfBirth, txtEmail, txtContact, txtAddress);
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
            java.util.logging.Logger.getLogger(MyProfile_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyProfile_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyProfile_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyProfile_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyProfile_cus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    // End of variables declaration                   
}
