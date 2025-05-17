package com.Admin.statistics.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import java.awt.Color;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.DefaultCategoryDataset;

public class DAO_barchart {
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private ScheduledExecutorService scheduler;
    private static final int REFRESH_INTERVAL = 30;
    private volatile boolean isRunning = true;
    private Connection activeConnection;

    public DAO_barchart() {
        dataset = new DefaultCategoryDataset();
        createChart();
        initAutoRefresh();
    }

    private void initAutoRefresh() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (isRunning) {
                refreshData();
            }
        }, 0, REFRESH_INTERVAL, TimeUnit.SECONDS);
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    private void createChart() {
        chart = ChartFactory.createBarChart(
            "Most Popular Suppliers by ID",
            "Supplier ID",
            "Number of Products Sold",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        applyDistinctColors();

        chart.setBackgroundPaint(Color.WHITE);

        // Tạo ChartPanel với chế độ chỉ xem
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setMouseWheelEnabled(false);  // 🚫 Vô hiệu hóa cuộn bằng chuột
        chartPanel.setDomainZoomable(false);     // 🚫 Không cho phép zoom theo trục ngang
        chartPanel.setRangeZoomable(false);      // 🚫 Không cho phép zoom theo trục dọc
    }


    private synchronized void refreshData() {
        try {
            Map<String, Integer> newData = fetchDataFromDatabase();
            if (!newData.isEmpty()) {
                updateDataset(newData);
            }
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private Map<String, Integer> fetchDataFromDatabase() throws SQLException {
        Map<String, Integer> data = new LinkedHashMap<>();
        String sql = "SELECT s.Sup_ID, COUNT(*) as count FROM Bill_Exported_Details bed " +
                     "JOIN Product p ON bed.Product_ID = p.Product_ID " +
                     "JOIN Category c ON p.Category_ID = c.Category_ID " +
                     "JOIN Supplier s ON c.Sup_ID = s.Sup_ID " +
                     "GROUP BY s.Sup_ID ORDER BY count DESC";

        try {
            if (activeConnection == null || activeConnection.isClosed()) {
                activeConnection = DatabaseConnection.connect();
                activeConnection.setNetworkTimeout(Executors.newSingleThreadExecutor(), 5000);
            }

            try (PreparedStatement pst = activeConnection.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String supplierId = rs.getString("Sup_ID");
                    int count = rs.getInt("count");
                    data.put(supplierId, count);
                }
            }
        } catch (SQLException e) {
            closeActiveConnection();
            throw e;
        }

        return data;
    }

    private void updateDataset(Map<String, Integer> newData) {
        dataset.clear();

        newData.forEach((supplier, count) -> dataset.addValue(count, supplier, "Products"));

        if (chart != null) {
            chart.fireChartChanged();
            applyDistinctColors();
        }
    }

    private void applyDistinctColors() {
        if (chart == null) return;

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        Color[] distinctColors = {
            new Color(31, 119, 180), new Color(255, 127, 14),
            new Color(44, 160, 44), new Color(214, 39, 40),
            new Color(148, 103, 189), new Color(140, 86, 75),
            new Color(227, 119, 194), new Color(127, 127, 127),
            new Color(188, 189, 34), new Color(23, 190, 207)
        };

        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesPaint(i, distinctColors[i % distinctColors.length]);
        }
    }

    private void handleDatabaseError(SQLException e) {
        if (e.getMessage() != null && e.getMessage().contains("Socket closed")) {
            closeActiveConnection();
            return;
        }
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    }

    private void closeActiveConnection() {
        try {
            if (activeConnection != null && !activeConnection.isClosed()) {
                activeConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("⚠ Error closing connection: " + e.getMessage());
        }
        activeConnection = null;
    }

    public void shutdown() {
        isRunning = false;
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
        closeActiveConnection();
    }
}
