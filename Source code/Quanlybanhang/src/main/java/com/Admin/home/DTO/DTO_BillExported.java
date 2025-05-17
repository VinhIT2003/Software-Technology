
package com.Admin.home.DTO;

public class DTO_BillExported {
    private String invoiceNo;
    private String adminId;
    private String customerId;
    private int totalProduct;

    public DTO_BillExported() {
    }

    public DTO_BillExported(String invoiceNo, String adminId, String customerId, int totalProduct) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.totalProduct = totalProduct;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    @Override
    public String toString() {
        return "DTO_BillExported{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalProduct=" + totalProduct +
                '}';
    } 
}
