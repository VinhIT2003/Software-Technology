// ProductUpdateNotifier.java
package com.Admin.product.GUI;

import java.util.ArrayList;
import java.util.List;

public class ProductUpdateNotifier {
    private static ProductUpdateNotifier instance;
    private List<ProductUpdateObserver> observers = new ArrayList<>();
    
    private ProductUpdateNotifier() {}
    
    public static synchronized ProductUpdateNotifier getInstance() {
        if (instance == null) {
            instance = new ProductUpdateNotifier();
        }
        return instance;
    }
    
    public void registerObserver(ProductUpdateObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void unregisterObserver(ProductUpdateObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyProductUpdated() {
        for (ProductUpdateObserver observer : new ArrayList<>(observers)) {
            observer.onProductUpdated();
        }
    }
}