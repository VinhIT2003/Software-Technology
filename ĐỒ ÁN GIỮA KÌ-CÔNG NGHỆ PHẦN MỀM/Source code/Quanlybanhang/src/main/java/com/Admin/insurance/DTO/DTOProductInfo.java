
package com.Admin.insurance.DTO;

public class DTOProductInfo {
    private String productId;
    private String productName;
    private String categoryId;
    private String brand;
    private String warrantyPeriod;

    // Constructor
    public DTOProductInfo(String productId, String productName, 
                          String categoryId, String brand, String warrantyPeriod) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Optional: toString() for easier debugging
    @Override
    public String toString() {
        return "DTOProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", brand='" + brand + '\'' +
                ", warrantyPeriod='" + warrantyPeriod + '\'' +
                '}';
    }
}

