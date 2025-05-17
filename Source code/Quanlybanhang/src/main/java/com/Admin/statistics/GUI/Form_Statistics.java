// Form_Statistics.java
package com.Admin.statistics.GUI;

import com.ComponentandDatabase.Components.MyButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_Statistics extends JPanel {
    private JPanel panel;
    private JLabel lblTitle;
    private MyButton bntbarChart, bntpieChart, bntLinechart, bntHeatmap;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public Form_Statistics() {
        initComponents();
        init();
    }

    private void initComponents() {
        setLayout(new BorderLayout()); // Thay đổi sang BorderLayout
        setPreferredSize(new Dimension(1530, 860));
        setBackground(Color.WHITE);
    }

    private void init() {
        // Tạo CardLayout để quản lý các form
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(1530, 860));
        
        // Tạo panel chứa nút điều khiển
        JPanel controlPanel = new JPanel(null);
        controlPanel.setPreferredSize(new Dimension(1530, 100));
        controlPanel.setBackground(Color.WHITE);
        
        // Thêm nút bntbarChart
        bntbarChart = new MyButton("Bar Chart", 20);
        bntbarChart.setBackgroundColor(Color.WHITE);
        bntbarChart.setPressedColor(Color.decode("#D3D3D3"));
        bntbarChart.setHoverColor(Color.decode("#EEEEEE"));
        bntbarChart.setBounds(50, 40, 140, 35);
        bntbarChart.setFont(new Font("sansserif", Font.BOLD, 16));
        bntbarChart.setForeground(Color.BLACK);
        bntbarChart.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\barchart.png", 
                               25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        
        // Thêm ActionListener cho nút
        bntbarChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "barChart");
            }
        });
        
        
        bntLinechart = new MyButton("Line Chart", 20);
        bntLinechart.setBackgroundColor(Color.WHITE);
        bntLinechart.setPressedColor(Color.decode("#D3D3D3"));
        bntLinechart.setHoverColor(Color.decode("#EEEEEE"));
        bntLinechart.setBounds(300, 40, 180, 35);
        bntLinechart.setFont(new Font("sansserif", Font.BOLD, 16));
        bntLinechart.setForeground(Color.BLACK);
        bntLinechart.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\linechart.png", 
                               25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntLinechart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "lineChart");
            }
        });
        
        bntpieChart = new MyButton("Pie Chart", 20);
        bntpieChart.setBackgroundColor(Color.WHITE);
        bntpieChart.setPressedColor(Color.decode("#D3D3D3"));
        bntpieChart.setHoverColor(Color.decode("#EEEEEE"));
        bntpieChart.setBounds(620, 40, 180, 35);
        bntpieChart.setFont(new Font("sansserif", Font.BOLD, 16));
        bntpieChart.setForeground(Color.BLACK);
        bntpieChart.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\pie_chart.png", 
                               25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntpieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "pieChart");
            }
        });
        
        
        bntHeatmap = new MyButton("Area chart", 20);
        bntHeatmap.setBackgroundColor(Color.WHITE);
        bntHeatmap.setPressedColor(Color.decode("#D3D3D3"));
        bntHeatmap.setHoverColor(Color.decode("#EEEEEE"));
        bntHeatmap.setBounds(950, 40, 180, 35);
        bntHeatmap.setFont(new Font("sansserif", Font.BOLD, 16));
        bntHeatmap.setForeground(Color.BLACK);
        bntHeatmap.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\heatmap.png", 
                               25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntHeatmap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "heatmap");
            }
        });
        
        
        
        controlPanel.add(bntLinechart);
        controlPanel.add(bntpieChart);
        controlPanel.add(bntbarChart);
        controlPanel.add(bntHeatmap);
        // Thêm các form vào cardPanel
        cardPanel.add(new Form_BarChart(), "barChart");
        cardPanel.add(new Form_PieChart(), "pieChart"); // Thêm sau nếu cần
        cardPanel.add(new Form_LineChart(), "lineChart"); // Thêm sau nếu cần
        cardPanel.add(new Form_Heatmap(), "heatmap"); // Thêm sau nếu cần
        // Hiển thị form mặc định
        cardLayout.show(cardPanel, "barChart");
        //cardLayout.show(cardPanel, "lineChart");
        
        // Thêm các panel vào form chính
        add(controlPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }
}