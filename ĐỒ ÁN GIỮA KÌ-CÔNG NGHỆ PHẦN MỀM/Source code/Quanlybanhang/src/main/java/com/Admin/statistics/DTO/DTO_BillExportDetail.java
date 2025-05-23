package com.Admin.statistics.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class DTO_BillExportDetail {
    private String invoiceNo;
    private String adminId;
    private String customerId;
    private String productId;
    private String imeiNo;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal discountValues;
    private BigDecimal totalPriceBefore;
    private BigDecimal totalPriceAfter;
    private Date dateExported;
    private Time timeExported;

    public DTO_BillExportDetail(String invoiceNo, String adminId, String customerId, String productId,
                                   String imeiNo, BigDecimal unitPrice, int quantity, BigDecimal discountValues,
                                   BigDecimal totalPriceBefore, BigDecimal totalPriceAfter,
                                   Date dateExported, Time timeExported) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.productId = productId;
        this.imeiNo = imeiNo;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discountValues = discountValues;
        this.totalPriceBefore = totalPriceBefore;
        this.totalPriceAfter = totalPriceAfter;
        this.dateExported = dateExported;
        this.timeExported = timeExported;
    }

    // Getters and Setters

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountValues() {
        return discountValues;
    }

    public void setDiscountValues(BigDecimal discountValues) {
        this.discountValues = discountValues;
    }

    public BigDecimal getTotalPriceBefore() {
        return totalPriceBefore;
    }

    public void setTotalPriceBefore(BigDecimal totalPriceBefore) {
        this.totalPriceBefore = totalPriceBefore;
    }

    public BigDecimal getTotalPriceAfter() {
        return totalPriceAfter;
    }

    public void setTotalPriceAfter(BigDecimal totalPriceAfter) {
        this.totalPriceAfter = totalPriceAfter;
    }

    public Date getDateExported() {
        return dateExported;
    }

    public void setDateExported(Date dateExported) {
        this.dateExported = dateExported;
    }

    public Time getTimeExported() {
        return timeExported;
    }

    public void setTimeExported(Time timeExported) {
        this.timeExported = timeExported;
    }
   
   @Override
    public String toString() {
        return "DTO_BillExportedDetail{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", productId='" + productId + '\'' +
                ", imeiNo='" + imeiNo + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", discountValues=" + discountValues +
                ", totalPriceBefore=" + totalPriceBefore +
                ", totalPriceAfter=" + totalPriceAfter +
                ", dateExported=" + dateExported +
                ", timeExported=" + timeExported +
                '}';
    }

}
