
package com.User.login_user.GUI;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.User.login_user.BUS.BusAccount_cus;
import com.User.login_user.DTO.DTOAccount_cus;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class ResetPassword_cus extends javax.swing.JFrame {
      private JLabel lblTitle;
      private MyPanel panelTitle;
      private MyTextField txtNewPass, txtConfirmNewPass;
      private MyButton btnUpdate;
      private DTOAccount_cus DTOAccount;
      private BusAccount_cus busAccount;
    public ResetPassword_cus() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        init();
    }
    
    public void init(){
                // Thiết lập MigLayout cho panel chính với khả năng co giãn
          bg.setLayout(new MigLayout("insets 0, fill", "[grow]", "[40!][grow]"));

          // 1. Panel tiêu đề (tự động co giãn theo chiều ngang)
          panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
          panelTitle.setGradientColors(new Color(30,166,97), new Color(22,116,66), MyPanel.VERTICAL_GRADIENT);

          lblTitle = new JLabel("Reset Password", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
          bg.add(panelTitle, "growx, h 40!, wrap");
          
          // Tạo đối tượng MyTextField password
          txtNewPass = new MyTextField();
          txtNewPass.setBorder(null);
            Color backgroundColor = Color.decode("#E0F2E9"); // Màu xanh nhạt
          txtNewPass.setBackgroundColor(backgroundColor);
          txtNewPass.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
          txtNewPass.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\pass.png");

            // Gọi phương thức tạo password field có nút ẩn/hiện
            JPanel passwordPanel = txtNewPass.createPasswordFieldWithEyeButton(
                "Enter a new password", 
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\hidepass.png",
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\showpass.png",
                backgroundColor,   // Màu viền đồng bộ
                0 // Không cần viền
            );
            // Đồng bộ màu nền cho toàn bộ passwordPanel
            passwordPanel.setOpaque(true);
            passwordPanel.setBackground(backgroundColor);

          bg.add(passwordPanel, "pos 70 70, w 300!, h 35!");
          
          // Tạo đối tượng MyTextField password
          txtConfirmNewPass = new MyTextField();
          txtConfirmNewPass.setBorder(null);
           Color backgroundColorconfirm = Color.decode("#E0F2E9"); // Màu xanh nhạt
          txtConfirmNewPass.setBackgroundColor(backgroundColorconfirm);
          txtConfirmNewPass.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
          txtConfirmNewPass.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\pass.png");

            // Gọi phương thức tạo password field có nút ẩn/hiện
            JPanel passwordPanelconfirm = txtConfirmNewPass.createPasswordFieldWithEyeButton(
                "Confirm new password", 
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\hidepass.png",
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\showpass.png",
                backgroundColor,   // Màu viền đồng bộ
                0 // Không cần viền
            );
            // Đồng bộ màu nền cho toàn bộ passwordPanel
            passwordPanelconfirm.setOpaque(true);
            passwordPanelconfirm.setBackground(backgroundColor);
          bg.add(passwordPanelconfirm, "pos 70 140, w 300!, h 35!");
          
         btnUpdate = new MyButton("Update", 18);
         btnUpdate.setBackgroundColor(new Color(0, 150, 136)); // Màu nền
         btnUpdate.setPressedColor(new Color(0, 100, 90)); // Màu khi nhấn
         btnUpdate.setHoverColor(new Color(0, 180, 150)); // Màu khi rê chuột 
         btnUpdate.setFont(new Font("Arial", Font.BOLD, 16));
         btnUpdate.setForeground(Color.WHITE);
         btnUpdate.addActionListener((e) -> {
            String newPass = txtNewPass.getPasswordText().strip();
            String confirmPass = txtConfirmNewPass.getPasswordText().strip();
            String email= PanelLoginandRegister_User.txtEmailLogin.getText().strip();
            DTOAccount= new DTOAccount_cus();
            DTOAccount.setEmail(email);
            DTOAccount.setPassword(newPass);
            busAccount= new BusAccount_cus();
            boolean success = busAccount.updateNewPassword(DTOAccount, confirmPass);

            if (success) {
                CustomDialog.showSuccess("Password changed successfully");
                this.dispose(); 
                
            }
        });
         
         bg.add(btnUpdate,"pos 150 210, w 120!, h 30!");
          
                          
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
            .addGap(0, 433, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ResetPassword_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResetPassword_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResetPassword_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResetPassword_cus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResetPassword_cus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    // End of variables declaration                   
}
