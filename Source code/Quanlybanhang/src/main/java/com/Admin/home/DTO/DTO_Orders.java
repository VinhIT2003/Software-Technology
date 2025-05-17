
package com.Admin.home.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class DTO_Orders {
    private String orderNo;
    private String customerID;
    private int totalQuantityProduct;
    private BigDecimal totalPrice;
    private String payment;
    private LocalDate dateOrder;
    private LocalTime timeOrder;
    private String status;
    private String customerName;
    private String address;
    private String contact;

    // Constructor không tham số
    public DTO_Orders() {}

    // Constructor đầy đủ
    public DTO_Orders(String orderNo, String customerID, int totalQuantityProduct,
                     BigDecimal totalPrice, String payment,
                     LocalDate dateOrder, LocalTime timeOrder, String status, String customerName, String address, String contact) {
        this.orderNo = orderNo;
        this.customerID = customerID;
        this.totalQuantityProduct = totalQuantityProduct;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
        this.status= status;
        this.customerName= customerName;
        this.address = address;
        this.contact= contact;
    }

    // Getter và Setter
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

    public int getTotalQuantityProduct() {
        return totalQuantityProduct;
    }

    public void setTotalQuantityProduct(int totalQuantityProduct) {
        this.totalQuantityProduct = totalQuantityProduct;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    
    
    
    // toString()
    @Override
    public String toString() {
        return "DTO_Order{" +
                "orderNo='" + orderNo + '\'' +
                ", customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +    
                ", totalQuantityProduct=" + totalQuantityProduct +
                ", totalPrice=" + totalPrice +
                ", payment='" + payment + '\'' +
                ", dateOrder=" + dateOrder +
                ", timeOrder=" + timeOrder +
                
                '}';
    }
}
