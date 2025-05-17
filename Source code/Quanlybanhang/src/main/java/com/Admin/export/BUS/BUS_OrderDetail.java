package com.Admin.export.BUS;

import com.Admin.export.DAO.DAO_OrderDetail;
import com.Admin.export.DTO.DTO_Oderdetails;
import com.Admin.product.DTO.DTOIMEI;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BUS_OrderDetail {
    private final DAO_OrderDetail daoOrderDetail;

    public BUS_OrderDetail() {
        this.daoOrderDetail = new DAO_OrderDetail();
    }

   
    public List<DTO_Oderdetails> getConfirmedOrderDetailsOldestFirst() {
        try {
            return daoOrderDetail.getConfirmedOrderDetailsOldestFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về list rỗng thay vì null
        }
    }

   
  
    public List<DTO_Oderdetails> getConfirmedOrderDetailsByOrderNo(String orderNo) {
        try {
            return daoOrderDetail.getConfirmedOrderDetailsByOrderNo(orderNo);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    
    public int countConfirmedOrderDetails() {
        try {
            List<DTO_Oderdetails> details = daoOrderDetail.getConfirmedOrderDetailsOldestFirst();
            return details.size(); // Không cần kiểm tra null vì DAO luôn trả về list
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTotalConfirmedQuantity() {
        try {
            List<DTO_Oderdetails> details = daoOrderDetail.getConfirmedOrderDetailsOldestFirst();
            return details.stream()
                         .mapToInt(DTO_Oderdetails::getQuantity)
                         .sum();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
     public List<DTO_Oderdetails> searchOrderDetails(String searchType, String keyword) {
        try {
            return daoOrderDetail.searchOrderDetails(searchType, keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Trả về list rỗng nếu có lỗi
        }
    }
     
    public List<DTOIMEI> getIMEIByProductID(String productID) {
        try {
            return daoOrderDetail.getIMEIByProductID(productID);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
 
    public List<DTOIMEI> getIMEIByProductIDWithLimit(String productID, int limit) {
        try {
            List<DTOIMEI> allIMEIs = daoOrderDetail.getIMEIByProductID(productID);
            return allIMEIs.subList(0, Math.min(limit, allIMEIs.size()));
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
     public String getProductName(String productID) {
        try {
            return daoOrderDetail.getProductName(productID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // hoặc trả về chuỗi rỗng "" tùy theo yêu cầu
        }
    }
    
     public boolean deleteIMEI(String imeiNo) {
         return daoOrderDetail.deleteIMEI(imeiNo);
     }
     public boolean deleteOrder(String orderNo) {
         return daoOrderDetail.deleteOrderByOrderNo(orderNo);
     }
     
    public String getPayment(String orderNo) {
        try {
            return daoOrderDetail.getPayment(orderNo);
        } catch (SQLException e) {
            System.err.println("BUS: Error getting payment method: " + e.getMessage());
            return "N/A"; // Xử lý fallback nếu DAO bị lỗi
        }
    }
     
}