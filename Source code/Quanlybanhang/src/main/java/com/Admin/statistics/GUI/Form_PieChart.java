
package com.Admin.statistics.GUI;

import com.Admin.statistics.BUS.BUS_Chart;
import com.ComponentandDatabase.Components.MyButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartPanel;


public class Form_PieChart extends JPanel {
   private JPanel panel;
    private JLabel lblTitle;
    private MyButton bntRefresh;
    private BUS_Chart busChart;
    private ChartPanel chartPanel;
    public Form_PieChart() {
        initComponents();
        init();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1530, 860)); // Giữ kích thước nhưng không ép buộc vị trí
        setBackground(Color.WHITE); // Kiểm tra hiển thị
    }

   private void init() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1270, 700);
        panel.setBackground(Color.WHITE);
        panel.setBorder(null);
        add(panel);

        // Khởi tạo BUS
        busChart = new BUS_Chart();
        
        // Lấy ChartPanel từ BUS
        chartPanel = busChart.getPieChartPanel();
        chartPanel.setBounds(50, 100, 1170, 550); // Đặt vị trí và kích thước cho biểu đồ
        panel.add(chartPanel);

        // Tiêu đề
        lblTitle = new JLabel("Statistical chart of category popularity");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 50, 1170, 40);
        panel.add(lblTitle);

        // Nút Refresh
        bntRefresh = new MyButton("Refresh", 20);
        bntRefresh.setBackgroundColor(Color.WHITE);
        bntRefresh.setPressedColor(Color.decode("#D3D3D3"));
        bntRefresh.setHoverColor(Color.decode("#EEEEEE"));
        bntRefresh.setBounds(1100, 40, 140, 35);
        bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
        bntRefresh.setForeground(Color.BLACK);
        bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 
                               25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        
        // Xử lý sự kiện refresh
        bntRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshChart();
            }
        });
        
        panel.add(bntRefresh);
    }

    private void refreshChart() {
        // Xóa chartPanel cũ
        panel.remove(chartPanel);
        
        // Tạo lại chartPanel mới
        chartPanel = busChart.getPieChartPanel();
        chartPanel.setBounds(50, 100, 1170, 550);
        panel.add(chartPanel);
        
        // Cập nhật giao diện
        panel.revalidate();
        panel.repaint();
    }

    @Override
    protected void finalize() throws Throwable {
        // Dọn dẹp tài nguyên khi không dùng nữa
        busChart.shutdownChartUpdaters();
        super.finalize();
    }
}
