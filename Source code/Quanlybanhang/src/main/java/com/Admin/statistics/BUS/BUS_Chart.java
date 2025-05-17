package com.Admin.statistics.BUS;

import com.Admin.statistics.DAO.DAO_barchart;
import com.Admin.statistics.DAO.DAO_piechart; 
import com.Admin.statistics.DAO.DAO_heatmap;
import com.Admin.statistics.DAO.DAO_linechart;
import org.jfree.chart.ChartPanel;

public class BUS_Chart {
    private DAO_barchart daoBarChart;
    private DAO_piechart daoPieChart; // Thêm instance cho pie chart
    private DAO_heatmap daoHeatmap;
    private DAO_linechart daoLineChart;
            
    public BUS_Chart() {
        daoBarChart = new DAO_barchart();
        daoPieChart = new DAO_piechart(); // Khởi tạo pie chart
        daoHeatmap= new DAO_heatmap();
        daoLineChart= new DAO_linechart();
    }

    // Phương thức cho bar chart
    public ChartPanel getBarChartPanel() {
        return daoBarChart.getChartPanel();
    }

    // Phương thức mới cho pie chart
    public ChartPanel getPieChartPanel() {
        return daoPieChart.getChartPanel();
    }
    
    public ChartPanel getHeatmap(){
        return daoHeatmap.getChartPanel();
    }
   
    public ChartPanel getLineChart(){
        return daoLineChart.getChartPanel();
    }
   

    public void shutdownChartUpdaters() {
        daoBarChart.shutdown();
        daoPieChart.shutdown(); // Đóng cả pie chart
        daoHeatmap.shutdown();
        daoLineChart.shutdown();
    }
}