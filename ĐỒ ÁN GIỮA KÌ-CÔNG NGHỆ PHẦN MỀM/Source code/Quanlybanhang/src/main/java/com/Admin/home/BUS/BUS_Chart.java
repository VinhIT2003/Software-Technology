package com.Admin.home.BUS;

import com.Admin.home.DAO.DAO_Chart;
import org.jfree.chart.ChartPanel;

public class BUS_Chart {
    private DAO_Chart daoChart;

    public BUS_Chart() {
        this.daoChart = new DAO_Chart(); // Khởi tạo DAO
    }

    public ChartPanel getbarChart() {
        return daoChart.getChartPanel(); // Lấy biểu đồ từ DAO
    }

    public void shutdownChartService() {
        daoChart.shutdown(); // Dừng bộ lập lịch nếu cần
    }
}
