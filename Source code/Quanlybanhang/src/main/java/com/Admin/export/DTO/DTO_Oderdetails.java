
package com.Admin.export.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class DTO_Oderdetails {
    private String orderNo;
    private String customerID;
    private String productID;
    private BigDecimal price;
    private int quantity;
    private LocalDate dateOrder;
    private LocalTime timeOrder;
    private String status;

    // Constructor không tham số
    public DTO_Oderdetails() {}

    // Constructor đầy đủ tham số
    public DTO_Oderdetails(String orderNo, String customerID, String productID,
                            BigDecimal price, int quantity,
                            LocalDate dateOrder, LocalTime timeOrder, String status) {
        this.orderNo = orderNo;
        this.customerID = customerID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
        this.status = status;
    }

    // Getters và Setters
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public LocalDate getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDate dateOrder) {
        this.dateOrder = dateOrder;
    }

    public LocalTime getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(LocalTime timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString
    @Override
    public String toString() {
        return "DTO_OrderDetails{" +
                "orderNo='" + orderNo + '\'' +
                ", customerID='" + customerID + '\'' +
                ", productID='" + productID + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", dateOrder=" + dateOrder +
                ", timeOrder=" + timeOrder +
                ", status='" + status + '\'' +
                '}';
    }
}


