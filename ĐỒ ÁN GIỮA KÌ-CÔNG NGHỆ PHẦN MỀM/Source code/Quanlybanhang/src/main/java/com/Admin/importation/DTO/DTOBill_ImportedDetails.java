package com.Admin.importation.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class DTOBill_ImportedDetails {

    private String invoiceNo;
    private String adminId;
    private String productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private LocalDate dateImported;
    private LocalTime timeImported;

    public DTOBill_ImportedDetails() {
    }

    public DTOBill_ImportedDetails(String invoiceNo, String adminId, String productId, int quantity,
                                   BigDecimal unitPrice, BigDecimal totalPrice,
                                   LocalDate dateImported, LocalTime timeImported) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.dateImported = dateImported;
        this.timeImported = timeImported;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDateImported() {
        return dateImported;
    }

    public void setDateImported(LocalDate dateImported) {
        this.dateImported = dateImported;
    }

    public LocalTime getTimeImported() {
        return timeImported;
    }

    public void setTimeImported(LocalTime timeImported) {
        this.timeImported = timeImported;
    }

    @Override
    public String toString() {
        return "DTOBill_ImportedDetails{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", dateImported=" + dateImported +
                ", timeImported=" + timeImported +
                '}';
    }
}
