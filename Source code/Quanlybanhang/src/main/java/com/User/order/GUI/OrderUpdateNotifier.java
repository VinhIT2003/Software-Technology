package com.User.order.GUI;

import java.util.ArrayList;
import java.util.List;

public class OrderUpdateNotifier {
    private static final List<OrderUpdateListener> listeners = new ArrayList<>();
    
    public static void addListener(OrderUpdateListener listener) {
        listeners.add(listener);
    }
    
    public static void removeListener(OrderUpdateListener listener) {
        listeners.remove(listener);
    }
    
    public static void notifyOrderPlaced(String customerID, String orderNo) {
        for (OrderUpdateListener listener : listeners) {
            listener.onOrderPlaced(customerID, orderNo);
        }
    }
    
    public static void notifyOrderDeleted(String customerID, String orderNo) {
        for (OrderUpdateListener listener : listeners) {
            listener.onOrderDeleted(customerID, orderNo);
        }
    }
}