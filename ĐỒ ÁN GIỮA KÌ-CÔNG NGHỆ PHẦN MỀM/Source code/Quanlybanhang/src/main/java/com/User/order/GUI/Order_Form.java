package com.User.order.GUI;

import com.User.order.BUS.BUS_Order;
import com.User.order.DTO.DTO_Order;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import com.User.dashboard_user.GUI.Dashboard_user;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Order_Form extends JPanel implements OrderUpdateListener {
    private String customerID;
    private JPanel panel;
    private JPanel panelShow;
    private JScrollPane scrollShow;
    private BUS_Order busOrder;
    private JLabel lblTitle;
    public static String orderNo;
    public Order_Form(String customerID) {
        this.customerID = customerID;
        this.busOrder = new BUS_Order();
        initComponents();
        initOrderDisplayArea();
        updateOrderList();
        OrderUpdateNotifier.addListener(this);
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1530, 860));
        setBackground(Color.WHITE);
    }

    private void initOrderDisplayArea() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1530, 860);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Sử dụng GridLayout thay vì FlowLayout
        panelShow = new JPanel(new GridLayout(0, 4, 5, 8));
        panelShow.setBackground(Color.WHITE);
        panelShow.setBorder(null);

        scrollShow = new JScrollPane(panelShow);
        scrollShow.setBounds(0, 50, 1250, 500);
        scrollShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollShow.setBorder(null);
        panel.add(scrollShow); 
    }

    public void updateOrderList() {
        panelShow.removeAll();
        
        // Lấy danh sách đơn hàng từ BUS (cần implement hàm này trong BUS_Order)
        ArrayList<DTO_Order> orders = busOrder.getSortedOrdersByCustomer(customerID);
        
        if (orders == null || orders.isEmpty()) {
            showEmptyOrderMessage();
        } else {
            for (DTO_Order order : orders) {
                JPanel orderPanel = createOrderPanel(order);
                panelShow.add(orderPanel);
            }
        }
        
        panelShow.revalidate();
        panelShow.repaint();
    }

    private void showEmptyOrderMessage() {
        // Tạm thời đổi layout sang BorderLayout để căn giữa
        panelShow.setLayout(new BorderLayout());
        
        JLabel noOrders = new JLabel("You don't have any orders yet", SwingConstants.CENTER);
        noOrders.setFont(new Font("Arial", Font.BOLD, 20));
        noOrders.setForeground(Color.GRAY);
        
        panelShow.add(noOrders, BorderLayout.CENTER);
    }

   private JPanel createOrderPanel(DTO_Order order) {
        JPanel panelcreate = new JPanel(new BorderLayout(3, 3));
        panelcreate.setPreferredSize(new Dimension(260, 200)); // Giảm chiều cao
        panelcreate.setBackground(Color.WHITE);
        panelcreate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel orderNoLabel = new JLabel("Order:" + order.getOrderNo());
        orderNoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel dateLabel = new JLabel(
        order.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
        order.getTimeOrder().format(DateTimeFormatter.ofPattern("HH:mm"))
    );
    dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(orderNoLabel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBackground(Color.WHITE);

        addCompactDetail(detailsPanel, "Customer ID: " + order.getCustomerID(), Font.PLAIN, 12);
        addCompactDetail(detailsPanel, "Total Items: " + order.getTotalQuantityProduct(), Font.PLAIN, 12);
        addCompactDetail(detailsPanel, "Total Price: " + order.getTotalPrice() + " VNĐ", Font.BOLD, 12);
        addCompactDetail(detailsPanel, "Payment Method: " + order.getPayment(), Font.PLAIN, 12);
        addCompactDetail(detailsPanel, "Date Order: " + order.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), Font.PLAIN, 12);
        addCompactDetail(detailsPanel, "Time Order: " + order.getTimeOrder(), Font.PLAIN, 12);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center nút
        buttonPanel.setBackground(Color.WHITE);

        MyButton detailBtn = new MyButton("View Details", 10);
        detailBtn.setPreferredSize(new Dimension(120, 30));
        detailBtn.addActionListener((e) -> {
     
            String customerID= Dashboard_user.customerID;
            orderNo= order.getOrderNo();
            Order_Details orderDetails= new Order_Details(customerID, orderNo);
            orderDetails.setVisible(true);
        });
        buttonPanel.add(detailBtn);

        panelcreate.add(headerPanel, BorderLayout.NORTH);
        panelcreate.add(detailsPanel, BorderLayout.CENTER);
        panelcreate.add(buttonPanel, BorderLayout.SOUTH);

        return panelcreate;
    }


    private void addCompactDetail(JPanel panel, String text, int fontStyle, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panel.add(label);
    }

    
    @Override
    public void onOrderPlaced(String customerID, String orderNo) {
         if (this.customerID.equals(customerID)) {
         SwingUtilities.invokeLater(() -> {
            panelShow.removeAll();
            panelShow.setLayout(new GridLayout(0, 4, 5, 8));
            
            // Sử dụng hàm mới đã sắp xếp từ DAO
            ArrayList<DTO_Order> orders = busOrder.getSortedOrdersByCustomer(customerID);
            
            if (orders != null && !orders.isEmpty()) {
                for (DTO_Order order : orders) {
                    JPanel orderPanel = createOrderPanel(order);
                    
                    if (order.getOrderNo().equals(orderNo)) {
                        orderPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                        new Timer(3000, e -> {
                            orderPanel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                                BorderFactory.createEmptyBorder(3, 5, 3, 5)
                            ));
                            ((Timer)e.getSource()).stop();
                        }).start();
                    }
                    panelShow.add(orderPanel);
                }
            } else {
                showEmptyOrderMessage();
            }
            
            panelShow.revalidate();
            panelShow.repaint();
               // 3. Auto-switch to this tab
               Container parent = this.getParent();
               while (parent != null) {
                   if (parent instanceof JTabbedPane) {
                       ((JTabbedPane)parent).setSelectedComponent(this);
                       break;
                   }
                   parent = parent.getParent();
               }
           });
       }
   }
    
     public void onOrderDeleted(String customerID, String orderNo) {
        if (this.customerID.equals(customerID)) {
            SwingUtilities.invokeLater(() -> {
                // Remove the order panel if it exists
                for (Component comp : panelShow.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel orderPanel = (JPanel) comp;
                        for (Component innerComp : orderPanel.getComponents()) {
                            if (innerComp instanceof JPanel) {
                                JPanel headerPanel = (JPanel) innerComp;
                                for (Component label : headerPanel.getComponents()) {
                                    if (label instanceof JLabel) {
                                        JLabel orderLabel = (JLabel) label;
                                        if (orderLabel.getText().contains(orderNo)) {
                                            panelShow.remove(orderPanel);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                // If no orders left, show empty message
                if (panelShow.getComponentCount() == 0) {
                    showEmptyOrderMessage();
                }
                
                panelShow.revalidate();
                panelShow.repaint();
            });
        }
    }
    
}