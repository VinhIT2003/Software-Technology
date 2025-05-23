package com.Admin.statistics.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.awt.BasicStroke;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;
import org.jfree.chart.axis.NumberAxis;
import java.math.BigDecimal;
import java.util.concurrent.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.*;

public class DAO_linechart {
    private TimeSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private ScheduledExecutorService scheduler;
    private static final int REFRESH_INTERVAL = 60;
    private String statisticsType = "month";
    private volatile boolean isRunning = true;
    private Connection activeConnection;

    public DAO_linechart() {
        dataset = new TimeSeriesCollection();
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

    public void setStatisticsType(String type) {
        if (Arrays.asList("day", "month", "year").contains(type)) {
            this.statisticsType = type;
            refreshData();
        }
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    private void createChart() {
        chart = ChartFactory.createTimeSeriesChart(
            "Revenue Statistics (" + statisticsType + ")",
            statisticsType.equals("day") ? "Date" : 
                (statisticsType.equals("month") ? "Month" : "Year"),
            "Revenue (VND)",
            dataset,
            true,
            true,
            false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesShapesVisible(0, true);
        plot.setRenderer(renderer);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat(
            statisticsType.equals("day") ? "dd MMM yyyy" : 
                (statisticsType.equals("month") ? "MMM yyyy" : "yyyy"),
            Locale.ENGLISH
        ));

        // 🔹 Fix Y-axis formatting (disable scientific notation)
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("#,###.00")); // Ensures numbers like 10,000,000.00

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
    }
    private synchronized void refreshData() {
        try {
            Map<String, BigDecimal> revenueData = fetchDataFromDatabase();
            if (!revenueData.isEmpty()) {
                updateDataset(revenueData);
            }
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private Map<String, BigDecimal> fetchDataFromDatabase() throws SQLException {
        Map<String, BigDecimal> data = new TreeMap<>();
        String dateFormat = statisticsType.equals("day") ? "yyyy-MM-dd" : 
                          statisticsType.equals("month") ? "yyyy-MM" : "yyyy";

        String sql = "SELECT FORMAT(bd.Date_Exported, '" + dateFormat + "') AS period, " +
                     "SUM(bd.Total_Price_After) AS total_revenue " +
                     "FROM Bill_Exported_Details bd " +
                     "GROUP BY FORMAT(bd.Date_Exported, '" + dateFormat + "') " +
                     "ORDER BY period";

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
                    String period = rs.getString("period");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    data.put(period, revenue);
                }
            }
        } catch (SQLException e) {
            closeActiveConnection();
            throw e;
        }

        return data;
    }

    private void updateDataset(Map<String, BigDecimal> revenueData) {
        dataset.removeAllSeries();
        TimeSeries series = new TimeSeries("Revenue");

        revenueData.forEach((period, revenue) -> {
            String[] parts = period.split("-");
            int year = Integer.parseInt(parts[0]);
            
            if (statisticsType.equals("day")) {
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                series.add(new Day(day, month, year), revenue);
            } 
            else if (statisticsType.equals("month")) {
                int month = Integer.parseInt(parts[1]);
                series.add(new Month(month, year), revenue);
            } 
            else {
                series.add(new Year(year), revenue);
            }
        });

        dataset.addSeries(series);
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