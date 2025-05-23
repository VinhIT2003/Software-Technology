
package com.Admin.importation.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class DTOBill_ImportedFullDetails extends DTOBill_ImportedDetails {
    private String adminName;
    private String productName;
    private String categoryId;
    private String brandId;

    public DTOBill_ImportedFullDetails() {
        super();
    }

    public DTOBill_ImportedFullDetails(String invoiceNo, String adminId, String adminName, 
                                      String productId, String productName, String categoryId, 
                                      String brandId, int quantity, BigDecimal unitPrice, 
                                      BigDecimal totalPrice, LocalDate dateImported, 
                                      LocalTime timeImported) {
        super(invoiceNo, adminId, productId, quantity, unitPrice, totalPrice, dateImported, timeImported);
        this.adminName = adminName;
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    // Getter và Setter
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return super.toString() + 
               " DTOBill_ImportedFullDetails{" +
               "adminName='" + adminName + '\'' +
               ", productName='" + productName + '\'' +
               ", categoryId='" + categoryId + '\'' +
               ", brandId='" + brandId + '\'' +
               '}';
    }
}
