
package com.Admin.login.GUI;

import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.Admin.login.BUS.BusAccount_ad;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Font;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class OTPFrame extends javax.swing.JFrame {

      private JLabel lblTitle;
      private MyPanel panelTitle;
      private MyTextField txtOTP;
      private MyButton btnConfirm;
      private BusAccount_ad busAccount;
    public OTPFrame() {
       setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        initComponents();
        init();
    }

    public void init(){
               // Thiết lập MigLayout cho panel chính với khả năng co giãn
          bg.setLayout(new MigLayout("insets 0, fill", "[grow]", "[40!][grow]"));

          // 1. Panel tiêu đề (tự động co giãn theo chiều ngang)
          panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
          panelTitle.setGradientColors(new Color(30,166,97), new Color(22,116,66), MyPanel.VERTICAL_GRADIENT);

          lblTitle = new JLabel("Verify OTP", JLabel.CENTER);
          lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
          lblTitle.setForeground(Color.WHITE);

          panelTitle.add(lblTitle, "grow, push, align center");
          bg.add(panelTitle, "growx, h 40!, wrap");
          
          txtOTP = new MyTextField();
          txtOTP.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
          txtOTP.setHint("Enter an OTP code");
          txtOTP.setBorder(null);
          txtOTP.setBackgroundColor(Color.decode("#E0F2E9")); // ✅ Gọi hàm mới để đổi màu
          bg.add(txtOTP, "pos 60 80, w 220!, h 35!");
          
         btnConfirm = new MyButton("Confirm", 18);
         btnConfirm.setBackgroundColor(new Color(0, 150, 136)); // Màu nền
         btnConfirm.setPressedColor(new Color(0, 100, 90)); // Màu khi nhấn
         btnConfirm.setHoverColor(new Color(0, 180, 150)); // Màu khi rê chuột 
         btnConfirm.setFont(new Font("Arial", Font.BOLD, 16));
         btnConfirm.setForeground(Color.WHITE);
         btnConfirm.addActionListener((e) -> {
            busAccount = new BusAccount_ad();
            String inputOTP = txtOTP.getText().strip();

            if (inputOTP.isEmpty()) {
                CustomDialog.showError("Please enter the OTP code!");
                return;
            }

            if (busAccount.ConfirmOTP(inputOTP)) {
                this.dispose();
                 CustomDialog.showSuccess("OTP verified successfully!");
                 ResetPassword reset = new ResetPassword();
                 reset.setVisible(true);
                
             
            } else {
                CustomDialog.showError("Invalid or expired OTP!");
            }
        });

         bg.add(btnConfirm,"pos 110 140, w 120!, h 30!");
         
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
            .addGap(0, 336, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(OTPFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OTPFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OTPFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OTPFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OTPFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
