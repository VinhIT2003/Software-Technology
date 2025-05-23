
package com.User.order.GUI;


public interface OrderUpdateListener  {
    void onOrderDeleted(String customerID, String orderNo);
    void onOrderPlaced(String customerID, String orderNo);
}