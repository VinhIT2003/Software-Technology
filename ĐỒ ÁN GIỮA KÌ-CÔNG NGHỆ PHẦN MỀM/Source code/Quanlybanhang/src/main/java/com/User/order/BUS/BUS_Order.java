
package com.User.order.BUS;


import com.User.order.DAO.DAO_Order;
import com.User.order.DTO.DTO_Order;
import java.util.ArrayList;

public class BUS_Order {
    private DAO_Order daoOrder; // Sửa tên biến cho đúng chính tả
    
    // Khởi tạo DAO trong constructor
    public BUS_Order() {
        this.daoOrder = new DAO_Order();
    }
    
    public boolean addOrderDetail(DTO_Order order) {
        // Kiểm tra null để đảm bảo an toàn
        if (daoOrder == null) {
            System.err.println("DAO_OrderDetails is not initialized!");
            return false;
        }
        return daoOrder.insertOrder(order);
    }
    
      public ArrayList<DTO_Order> getOrdersByCustomer(String customerID){
          return daoOrder.getOrdersByCustomer(customerID);
      }
      
      public ArrayList<DTO_Order> getSortedOrdersByCustomer(String customerID) {
          return daoOrder.getSortedOrdersByCustomer(customerID);
      }
}
