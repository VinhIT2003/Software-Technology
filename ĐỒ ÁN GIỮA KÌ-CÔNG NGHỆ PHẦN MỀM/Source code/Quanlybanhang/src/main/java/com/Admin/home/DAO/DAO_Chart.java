package com.Admin.home.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Chart {
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public DAO_Chart() {
        dataset = new DefaultCategoryDataset();
        createChart();
        refreshData(); // Tự động tải dữ liệu ngay khi khởi tạo
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    private void createChart() {
        chart = ChartFactory.createBarChart(
            "Total Quantity: Import vs Export",
            "Transaction Type",
            "Total Quantity",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(30, 144, 255)); // Xanh: Import
        renderer.setSeriesPaint(1, new Color(255, 99, 71)); // Đỏ: Export
        renderer.setItemMargin(0.1f); // Giúp hai cột gần nhau hơn

        // Điều chỉnh trục tung
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(true);

        chart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 450)); // Điều chỉnh kích thước
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setDomainZoomable(false);     // 🚫 Không cho phép zoom theo trục ngang
        chartPanel.setRangeZoomable(false); 
    }

    private void refreshData() {
        String sqlImport = "SELECT SUM(Quantity) AS total_import FROM Bill_Imported_Details";
        String sqlExport = "SELECT SUM(Quantity) AS total_export FROM Bill_Exported_Details";

        dataset.clear(); // Xóa dữ liệu cũ

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstImport = conn.prepareStatement(sqlImport);
             PreparedStatement pstExport = conn.prepareStatement(sqlExport);
             ResultSet rsImport = pstImport.executeQuery();
             ResultSet rsExport = pstExport.executeQuery()) {

            if (rsImport.next()) {
                dataset.addValue(rsImport.getDouble("total_import"), "Import", "Import");
            }

            if (rsExport.next()) {
                dataset.addValue(rsExport.getDouble("total_export"), "Export", "Export");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error loading data for chart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void shutdown() {
        System.out.println("Shutting down chart service...");
        closeActiveConnection();
        System.out.println("Chart service stopped.");
    }

    private void closeActiveConnection() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("⚠ Error closing connection: " + e.getMessage());
        }
    }
}
