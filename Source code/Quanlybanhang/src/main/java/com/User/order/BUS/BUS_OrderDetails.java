
package com.User.order.BUS;

import com.User.order.DAO.DAO_OrderDetails;
import com.User.order.DTO.DTO_OrderDetails;
import java.util.ArrayList;

public class BUS_OrderDetails {
    private DAO_OrderDetails daoOrderDetails; // Sửa tên biến cho đúng chính tả
    
    // Khởi tạo DAO trong constructor
    public BUS_OrderDetails() {
        this.daoOrderDetails = new DAO_OrderDetails();
    }
    
    public boolean addOrderDetail(DTO_OrderDetails detail) {
        // Kiểm tra null để đảm bảo an toàn
        if (daoOrderDetails == null) {
            System.err.println("DAO_OrderDetails is not initialized!");
            return false;
        }
        return daoOrderDetails.insertOrderDetail(detail);
    }
    
    public ArrayList<DTO_OrderDetails> getOrderDetails(String customerID, String orderNo) {
        return daoOrderDetails.getOrderDetails(customerID, orderNo);
    }
}