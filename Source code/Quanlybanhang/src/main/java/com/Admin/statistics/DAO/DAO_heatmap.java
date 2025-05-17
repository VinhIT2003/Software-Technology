package com.Admin.statistics.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.awt.Color;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.*;

public class DAO_heatmap {
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private ScheduledExecutorService scheduler;
    private static final int REFRESH_INTERVAL = 60;
    private volatile boolean isRunning = true;
    private Connection activeConnection;

    public DAO_heatmap() {
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
        if (chartPanel == null) {
            createChart();
        }
        return chartPanel;
    }

    private void createChart() {
        dataset = new DefaultCategoryDataset();
        
        CategoryAxis xAxis = new CategoryAxis("Months");
        NumberAxis yAxis = new NumberAxis("Sales Quantity");

        AreaRenderer renderer = new AreaRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        renderer.setSeriesPaint(1, new Color(255, 153, 0));
        renderer.setSeriesPaint(2, new Color(204, 0, 102));

        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        chart = new JFreeChart("Sales Trend (Area Chart)", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        chart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(false);     // 🚫 Không cho phép zoom theo trục ngang
        chartPanel.setRangeZoomable(false);
    }

    private synchronized void refreshData() {
        try {
            Map<String, Map<String, Integer>> newData = fetchDataFromDatabase();
            if (!newData.isEmpty()) {
                updateDataset(newData);
            }
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private Map<String, Map<String, Integer>> fetchDataFromDatabase() throws SQLException {
        Map<String, Map<String, Integer>> data = new LinkedHashMap<>();
        
        String sql = "SELECT FORMAT(bd.Date_Exported, 'yyyy-MM') AS month, " +
                   "p.Product_ID, SUM(bd.Quantity) AS total_sold " +
                   "FROM Bill_Exported_Details bd " +
                   "JOIN Product p ON bd.Product_ID = p.Product_ID " +
                   "JOIN Bill_Exported b ON bd.Invoice_No = b.Invoice_No " +
                   "GROUP BY p.Product_ID, FORMAT(bd.Date_Exported, 'yyyy-MM') " +
                   "ORDER BY FORMAT(bd.Date_Exported, 'yyyy-MM')";

        try {
            // Use active connection or create new one
            if (activeConnection == null || activeConnection.isClosed()) {
                activeConnection = DatabaseConnection.connect();
                // Set timeout to prevent hanging
                activeConnection.setNetworkTimeout(Executors.newSingleThreadExecutor(), 5000);
            }

            try (PreparedStatement pst = activeConnection.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String productId = rs.getString("Product_ID");
                    String month = rs.getString("month");
                    int totalSold = rs.getInt("total_sold");
                    data.computeIfAbsent(month, k -> new LinkedHashMap<>()).put(productId, totalSold);
                }
            }
        } catch (SQLException e) {
            closeActiveConnection();
            throw e;
        }

        return data;
    }

    private void updateDataset(Map<String, Map<String, Integer>> newData) {
        if (dataset == null) {
            dataset = new DefaultCategoryDataset();
        }
        dataset.clear();

        for (Map.Entry<String, Map<String, Integer>> monthEntry : newData.entrySet()) {
            String month = monthEntry.getKey();
            for (Map.Entry<String, Integer> productEntry : monthEntry.getValue().entrySet()) {
                dataset.addValue(productEntry.getValue(), productEntry.getKey(), month);
            }
        }

        if (chart != null) {
            chart.fireChartChanged();
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
            System.err.println("Error closing connection: " + e.getMessage());
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