package com.Admin.statistics.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import java.awt.Color;
import org.jfree.chart.*;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class DAO_piechart {
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private ScheduledExecutorService scheduler;
    private static final int REFRESH_INTERVAL = 30;
    private volatile boolean isRunning = true;
    private Connection activeConnection;

    public DAO_piechart() {
        dataset = new DefaultPieDataset();
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
        chart = ChartFactory.createPieChart(
            "Product Distribution by Category ID",
            dataset,
            true,
            true,
            false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.02);
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})",
            new java.text.DecimalFormat("0"),
            new java.text.DecimalFormat("0.0%")
        );
        plot.setLabelGenerator(labelGenerator);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        chartPanel.setMouseWheelEnabled(true);
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
        String sql = "SELECT c.Category_ID, COUNT(*) as count " +
                     "FROM Product p " +
                     "JOIN Category c ON p.Category_ID = c.Category_ID " +
                     "GROUP BY c.Category_ID " +
                     "ORDER BY count DESC";

        try {
            if (activeConnection == null || activeConnection.isClosed()) {
                activeConnection = DatabaseConnection.connect();
                activeConnection.setNetworkTimeout(Executors.newSingleThreadExecutor(), 5000);
            }

            try (PreparedStatement pst = activeConnection.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String categoryId = rs.getString("Category_ID");
                    int count = rs.getInt("count");
                    data.put(categoryId, count);
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

        newData.forEach((categoryId, count) -> dataset.setValue(categoryId, count));

        if (chart != null) {
            chart.fireChartChanged();
            applyDistinctColors();
        }
    }

    private void applyDistinctColors() {
        if (chart == null) return;

        PiePlot plot = (PiePlot) chart.getPlot();
        Color[] distinctColors = {
            new Color(31, 119, 180), new Color(255, 127, 14),
            new Color(44, 160, 44), new Color(214, 39, 40),
            new Color(148, 103, 189), new Color(140, 86, 75),
            new Color(227, 119, 194), new Color(127, 127, 127),
            new Color(188, 189, 34), new Color(23, 190, 207)
        };

        int i = 0;
        for (Object key : dataset.getKeys()) {
            plot.setSectionPaint(key.toString(), distinctColors[i % distinctColors.length]);
            i++;
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
