package com.Admin.home.GUI;

import com.Admin.home.BUS.BUS_Total;
import com.Admin.home.BUS.BUS_Chart;
import com.Admin.home.DTO.*;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyButton;
import org.jfree.chart.ChartPanel;
import javax.swing.*;
import java.awt.*;

public class Form_Home extends JPanel {
    private JPanel panelContainer, chartContainer;
    private MyPanel panelTotalCustomer, panelTotalOrder, panelTotalBillExport, panelTotalWarranty, 
            panelTotalSupplier, panelTotalCategory, panelTotalProduct, panelTotalBillImport;
    private JLabel lblTotalCustomer, lblTotalOrder, lblTotalBillExport, lblTotalWarranty, 
            lblTotalSupplier, lblTotalCategory, lblTotalProduct, lblTotalBillImport;
    private MyButton bntRefresh;
    private BUS_Total busTotal;
    private BUS_Chart busChart;

    public Form_Home() {
        busTotal = new BUS_Total(); // Khởi tạo BUS
        busChart = new BUS_Chart();
        initComponents();
        updatePanelData(); // Cập nhật dữ liệu từ BUS
        loadChart();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F7FA"));

        panelContainer = new JPanel(new GridLayout(2, 4, 10, 10));
        panelContainer.setBackground(Color.WHITE);
        panelContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        panelTotalCustomer = createInfoPanel("Total Customers", "\uD83D\uDC65", Color.decode("#1CB5E0"), Color.decode("#000046"), lblTotalCustomer = new JLabel());
        panelTotalOrder = createInfoPanel("Total Orders", "\uD83D\uDCDA", Color.decode("#E74C3C"), Color.decode("#641E16"), lblTotalOrder = new JLabel());
        panelTotalBillExport = createInfoPanel("Total Bill Exports", "\uD83D\uDCB8", Color.decode("#28B463"), Color.decode("#145A32"), lblTotalBillExport = new JLabel());
        panelTotalWarranty = createInfoPanel("Total Warranties", "\uD83D\uDEE0", Color.decode("#F39C12"), Color.decode("#7E5109"), lblTotalWarranty = new JLabel());
        panelTotalSupplier = createInfoPanel("Total Suppliers", "\uD83C\uDFE2", Color.decode("#8E44AD"), Color.decode("#4A235A"), lblTotalSupplier = new JLabel());
        panelTotalCategory = createInfoPanel("Total Categories", "\uD83D\uDCDD", Color.decode("#D35400"), Color.decode("#641E16"), lblTotalCategory = new JLabel());
        panelTotalProduct = createInfoPanel("Total Products", "\uD83D\uDECD", Color.decode("#1ABC9C"), Color.decode("#0B5345"), lblTotalProduct = new JLabel());
        panelTotalBillImport = createInfoPanel("Total Bill Imports", "\uD83D\uDCB8", Color.decode("#C0392B"), Color.decode("#78281F"), lblTotalBillImport = new JLabel());

        panelContainer.add(panelTotalCustomer);
        panelContainer.add(panelTotalOrder);
        panelContainer.add(panelTotalBillExport);
        panelContainer.add(panelTotalWarranty);
        panelContainer.add(panelTotalSupplier);
        panelContainer.add(panelTotalCategory);
        panelContainer.add(panelTotalProduct);
        panelContainer.add(panelTotalBillImport);
        
        
        // Khởi tạo nút Refresh
        bntRefresh = new MyButton("Refresh", 20);
        bntRefresh.setBackgroundColor(Color.WHITE);
        bntRefresh.setPressedColor(Color.decode("#D3D3D3"));
        bntRefresh.setHoverColor(Color.decode("#EEEEEE"));
        bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
        bntRefresh.setForeground(Color.BLACK);
        bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 
                                   25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Color.WHITE);
        topContainer.setBorder(null);
        topContainer.add(panelContainer, BorderLayout.NORTH);
        topContainer.add(bntRefresh, BorderLayout.WEST); // Thêm nút vào góc trên bên phải
        add(topContainer, BorderLayout.NORTH);

        chartContainer = new JPanel();
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setBorder(null);
        chartContainer.setPreferredSize(new Dimension(900, 480)); 
    
        add(chartContainer, BorderLayout.CENTER);
    }

    private MyPanel createInfoPanel(String title, String icon, Color startColor, Color endColor, JLabel lblValue) {
        MyPanel panel = new MyPanel(startColor, endColor, MyPanel.VERTICAL_GRADIENT);
        panel.setLayout(new GridLayout(3, 1)); 
        panel.setPreferredSize(new Dimension(200, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28)); 
        lblIcon.setForeground(Color.WHITE);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        lblValue.setFont(new Font("Arial", Font.BOLD, 22));
        lblValue.setForeground(Color.WHITE);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblIcon);
        panel.add(lblTitle);
        panel.add(lblValue);
        return panel;
    }

    private void updatePanelData() {
        lblTotalCustomer.setText(String.valueOf(busTotal.getTotalCustomers().getStatus()));
        lblTotalOrder.setText(String.valueOf(busTotal.getTotalOrders().getStatus()));
        lblTotalBillExport.setText(String.valueOf(busTotal.getTotalBillExports().getTotalProduct()));
        lblTotalWarranty.setText(String.valueOf(busTotal.getTotalInsurances().getCustomerId())); 
        lblTotalSupplier.setText(String.valueOf(busTotal.getTotalSuppliers().getContact()));
        lblTotalCategory.setText(String.valueOf(busTotal.getTotalCategories().getContact()));
        lblTotalProduct.setText(String.valueOf(busTotal.getTotalProducts().getQuantity()));
        lblTotalBillImport.setText(String.valueOf(busTotal.getTotalBillImports().getTotalProduct()));
    }
    
    private void loadChart() {
        ChartPanel chartPanel = busChart.getbarChart(); // Lấy biểu đồ từ BUS
        chartContainer.removeAll(); // Xóa dữ liệu cũ
        chartContainer.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ vào giao diện
        chartContainer.revalidate(); // Cập nhật lại giao diện
        chartContainer.repaint(); // Vẽ lại giao diện
    }
}
