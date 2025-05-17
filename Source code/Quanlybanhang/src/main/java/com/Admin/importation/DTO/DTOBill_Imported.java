
package com.Admin.importation.DTO;

import java.math.BigDecimal;

public class DTOBill_Imported {

    private String invoiceNo;
    private String adminId;
    private int totalProduct;
    private BigDecimal totalPrice;

    public DTOBill_Imported() {
    }

    public DTOBill_Imported(String invoiceNo, String adminId, int totalProduct, BigDecimal totalPrice) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.totalProduct = totalProduct;
        this.totalPrice = totalPrice;
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

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "DTOBill_Imported{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", totalProduct=" + totalProduct +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
