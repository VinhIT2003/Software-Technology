package com.Admin.stock.DTO;

import java.math.BigDecimal;

public class DTOProduct_Stock {

    private String productID;
    private String productName;
    private String cpu;
    private String ram;
    private String graphicsCard;
    private String operatingSystem;
    private BigDecimal price;
    private int quantity_stock;
    private String warrantyPeriod;
    private String categoryID;
    private String image;

    // Constructor mặc định
    public DTOProduct_Stock() {}

    // Constructor với tất cả các tham số
    public DTOProduct_Stock(String productID, String productName, String cpu, String ram, String graphicsCard,
                      String operatingSystem, BigDecimal price, int quantity_stock, String warrantyPeriod, 
                String categoryID, String image) {
        this.productID = productID;
        this.productName = productName;
        this.cpu = cpu;
        this.ram = ram;
        this.graphicsCard = graphicsCard;
        this.operatingSystem = operatingSystem;
        this.price = price;
        this.quantity_stock = quantity_stock;
        this.warrantyPeriod = warrantyPeriod;
        this.categoryID = categoryID;
        this.image = image;
    }

    // Getter and Setter methods

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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity_stock;
    }

    public void setQuantity(int quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DTOProduct{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", price=" + price +
                ", quantity=" + quantity_stock +
                ", warrantyPeriod='" + warrantyPeriod + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
