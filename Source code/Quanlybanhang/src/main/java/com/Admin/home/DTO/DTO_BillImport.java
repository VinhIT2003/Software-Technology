
package com.Admin.home.DTO;
import java.math.BigDecimal;
public class DTO_BillImport {
    private String invoiceNo;
    private String adminID;
    private int totalProduct;
    private BigDecimal totalPrice;

    public DTO_BillImport() {
    }

    public DTO_BillImport(String invoiceNo, String adminID, int totalProduct, BigDecimal totalPrice) {
        this.invoiceNo = invoiceNo;
        this.adminID = adminID;
        this.totalProduct = totalProduct;
        this.totalPrice = totalPrice;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
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
        return "DTO_BillImport{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", adminID='" + adminID + '\'' +
                ", totalProduct=" + totalProduct +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
