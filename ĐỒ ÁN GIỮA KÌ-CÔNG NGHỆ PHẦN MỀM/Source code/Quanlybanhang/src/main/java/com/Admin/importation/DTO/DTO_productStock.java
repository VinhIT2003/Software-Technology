package com.Admin.importation.DTO;

import java.math.BigDecimal;

public class DTO_productStock {

    private String productID;
    private String productName;
    private BigDecimal price;
    private String categoryID;
    private String brandID;
    private int quantityInStock;

    public DTO_productStock() {}

    public DTO_productStock(String productID, String productName, BigDecimal price, String categoryID, String brandID, int quantityInStock) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.categoryID = categoryID;
        this.brandID = brandID;
        this.quantityInStock = quantityInStock;
    }

    // Getters and Setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "DTO_productStock{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", categoryID='" + categoryID + '\'' +
                ", brandID='" + brandID + '\'' +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
