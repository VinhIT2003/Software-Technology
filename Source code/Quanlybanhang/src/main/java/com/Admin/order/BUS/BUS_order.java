package com.Admin.order.BUS;

import com.Admin.order.DAO.DAO_order;
import com.Admin.order.DTO.DTO_order;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.stream.Collectors;  // Thêm dòng import này

public class BUS_order {
    private DAO_order daoOrder;
    
    public BUS_order() {
        this.daoOrder = new DAO_order();
    }
    
    public List<DTO_order> getAllOrdersSorted() {
        return daoOrder.getAllOrdersSorted();
    }
    
    public List<DTO_order> getOrdersByStatus(String status) {
        List<DTO_order> allOrders = daoOrder.getAllOrdersSorted();
        return allOrders.stream()
                       .filter(order -> order.getStatus().equals(status))
                       .collect(Collectors.toList());
    }
    
    public DTO_order getOrderByNo(String orderNo) {
        return daoOrder.getAllOrdersSorted().stream()
                      .filter(order -> order.getOrderNo().equals(orderNo))
                      .findFirst()
                      .orElse(null);
    }
    
     public boolean updateOrderStatus(String orderNo, String newStatus) {
         return daoOrder.updateOrderStatus(orderNo, newStatus);
     }
     
    public boolean exportOrderDetailsToExcel(String filePath) {
        return daoOrder.exportOrderDetailsToExcel(filePath);
    }
    
    public List<DTO_order> searchOrders(String searchType, String keyword, String status) {
        try {
            return daoOrder.searchOrders(searchType, keyword, status);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}