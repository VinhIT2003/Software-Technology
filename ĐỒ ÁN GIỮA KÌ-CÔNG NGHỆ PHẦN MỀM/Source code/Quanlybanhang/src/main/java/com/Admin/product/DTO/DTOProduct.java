package com.Admin.product.DTO;

import java.math.BigDecimal;

public class DTOProduct {

    private String productID;
    private String productName;
    private String cpu;
    private String ram;
    private String graphicsCard;
    private String operatingSystem;
    private BigDecimal price;
    private int quantity;
    private String warrantyPeriod;
    private String status;
    private int spoiledQuantity;
    private String categoryID;
    private String image;

    // Constructor mặc định
    public DTOProduct() {}

    // Constructor với tất cả các tham số
    public DTOProduct(String productID, String productName, String cpu, String ram, String graphicsCard,
                      String operatingSystem, BigDecimal price, int quantity, String warrantyPeriod, 
                      String status, int spoiledQuantity, String categoryID, String image) {
        this.productID = productID;
        this.productName = productName;
        this.cpu = cpu;
        this.ram = ram;
        this.graphicsCard = graphicsCard;
        this.operatingSystem = operatingSystem;
        this.price = price;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.spoiledQuantity = spoiledQuantity;
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
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSpoiledQuantity() {
        return spoiledQuantity;
    }

    public void setSpoiledQuantity(int spoiledQuantity) {
        this.spoiledQuantity = spoiledQuantity;
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
                ", quantity=" + quantity +
                ", warrantyPeriod='" + warrantyPeriod + '\'' +
                ", status='" + status + '\'' +
                ", spoiledQuantity=" + spoiledQuantity +
                ", categoryID='" + categoryID + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
