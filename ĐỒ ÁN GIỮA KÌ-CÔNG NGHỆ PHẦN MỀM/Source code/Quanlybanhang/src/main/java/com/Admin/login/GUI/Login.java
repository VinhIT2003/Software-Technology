package com.Admin.login.GUI;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.SplineInterpolator;

public class Login extends javax.swing.JFrame {

    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginandRegister LoginandRegister;
    private boolean isLogin;
    private final double addSize= 30;
    private final double coverSize = 55;
    private final double LoginSize = 45;
    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private Animator animator;

    public Login() {
        initComponents();
        init();
    }

    private void init() {
        layout = new MigLayout("fill");
        cover = new PanelCover();
        LoginandRegister = new PanelLoginandRegister();

        // Thiết lập layout ban đầu
        bg.setLayout(layout);
        bg.add(LoginandRegister, "width " + LoginSize + "%, pos 0 0 n 100%");
        bg.add(cover, "width " + coverSize + "%, pos " + LoginSize + "% 0 n 100%");

        // Luôn hiển thị cả hai panel để tránh chớp
        LoginandRegister.setVisible(true);
        cover.setVisible(true);

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                // Hiển thị ngay lập tức để tránh chớp
                LoginandRegister.showRegister(isLogin);
                bg.revalidate();
                bg.repaint();
            }

            @Override
            public void timingEvent(float fraction) {
                fraction = Math.max(0, Math.min(1, fraction));

                double fractionCover;
                double fractionLogin;

                // 👉 **Tăng tốc độ mở rộng của panelCover**
                double speedFactor = 1.5;  // Giúp cover mở rộng nhanh hơn
                double size = coverSize + (fraction <= 0.5f ? fraction * addSize * speedFactor 
                                                            : (1 - fraction) * addSize * speedFactor);

                if (isLogin) {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                } else {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                }

                fractionCover = Double.parseDouble(df.format(fractionCover));
                fractionLogin = Double.parseDouble(df.format(fractionLogin));

                layout.setComponentConstraints(LoginandRegister, "width " + LoginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");

                bg.revalidate();
                bg.repaint();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };

        // 🔥 **Tăng tốc animation để panelCover di chuyển nhanh hơn**
        animator = new Animator(950, target); // Giảm xuống 500ms để nhanh hơn
        animator.setInterpolator(new SplineInterpolator(0.6f, 0f, 0.3f, 1f)); // Chuyển động nhanh dần
        animator.setResolution(0); 

        // Xử lý sự kiện click
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
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
            .addGap(0, 1161, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
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
       
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
//       /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
