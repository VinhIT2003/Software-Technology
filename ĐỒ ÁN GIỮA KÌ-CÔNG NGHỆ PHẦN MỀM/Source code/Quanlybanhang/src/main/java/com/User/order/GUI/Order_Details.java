
package com.User.order.GUI;
import com.ComponentandDatabase.Components.MyPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.User.order.BUS.BUS_OrderDetails;
import com.User.home.BUS.productBUS;
import com.User.order.DTO.DTO_OrderDetails;
import com.User.home.DTO.productDTO;
import com.User.dashboard_user.GUI.Dashboard_user;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
public class Order_Details extends javax.swing.JFrame {
    public JLabel lblTitle;
    public MyPanel panelTitle;
     private String customerID;
    private String orderNo;
    private JPanel productsPanel;
    private JScrollPane scrollPane;
    private BUS_OrderDetails orderDetailsBUS;
    private productBUS productBUS;
     
    public Order_Details(String customerID, String orderNo) {
        
        this.customerID = customerID;
        this.orderNo = orderNo;
        this.orderDetailsBUS = new BUS_OrderDetails();
        this.productBUS = new productBUS();
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        initUI();
        loadOrderProducts();
    }

   private void initUI() {
        bg.setLayout(new MigLayout("fillx, insets 0", "[grow]", "[][grow]"));
        
        // Panel tiêu đề
        panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
        panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

        lblTitle = new JLabel("Order " + orderNo, JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle, "grow, push, align center");
        bg.add(panelTitle, "growx, h 40!, wrap");

        // Panel chứa sản phẩm
        productsPanel = new JPanel(new GridLayout(0, 4, 10, 15));
        productsPanel.setBackground(Color.WHITE);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        bg.add(scrollPane, "grow, push");
    }
   
   
   
    private void loadOrderProducts() {
        productsPanel.removeAll();
        
        // Lấy danh sách sản phẩm trong đơn hàng từ BUS
        ArrayList<DTO_OrderDetails> orderDetails = orderDetailsBUS.getOrderDetails(customerID, orderNo);
        
        if (orderDetails == null || orderDetails.isEmpty()) {
            showNoProductsMessage();
        } else {
            for (DTO_OrderDetails detail : orderDetails) {
                // Lấy thông tin đầy đủ sản phẩm
                productDTO product = productBUS.getProductById(detail.getProductID());
                if (product != null) {
                    productsPanel.add(createProductCard(product, detail));
                }
            }
        }
        
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    private void showNoProductsMessage() {
       productsPanel.setLayout(new BorderLayout());
        
        JLabel noProducts = new JLabel("No products in this order", SwingConstants.CENTER);
        noProducts.setFont(new Font("Arial", Font.BOLD, 18));
        noProducts.setForeground(Color.GRAY);
        
        productsPanel.add(noProducts, BorderLayout.CENTER);
    }

    private JPanel createProductCard(productDTO product, DTO_OrderDetails orderDetail) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(280, 240));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Ảnh sản phẩm
        ImageIcon icon = new ImageIcon(product.getImage());
        Image img = icon.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(imageLabel, BorderLayout.NORTH);

        // Chi tiết sản phẩm
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
        detailsPanel.setBackground(Color.WHITE);
        
        addDetail(detailsPanel, "ID: " + product.getProductID(), Font.PLAIN, 13);
        addDetail(detailsPanel, product.getProductName(), Font.BOLD, 14);
        addDetail(detailsPanel, "Price: " + orderDetail.getPrice() + " VNĐ", Font.PLAIN, 13);
        addDetail(detailsPanel, "Quantity: " + orderDetail.getQuantity(), Font.PLAIN, 13);
        addDetail(detailsPanel, "Status: " + orderDetail.getStatus(), Font.PLAIN, 13);
        
        card.add(detailsPanel, BorderLayout.CENTER);
        
        return card;
    }

    private void addDetail(JPanel panel, String text, int fontStyle, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times new roman", fontStyle, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panel.add(label);
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
            .addGap(0, 1369, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 583, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(Order_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Order_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Order_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Order_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String customerID= Dashboard_user.customerID;
                String orderNo= Order_Form.orderNo;
                new Order_Details(customerID, orderNo).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
