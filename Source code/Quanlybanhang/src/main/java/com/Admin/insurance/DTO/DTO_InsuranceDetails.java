package com.Admin.insurance.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class DTO_InsuranceDetails {

    private String insuranceNo;
    private String adminId;
    private String customerId;
    private String productId;
    private String iMeiNo;
    private String description;
    private LocalDate dateInsurance;
    private LocalTime timeInsurance;

    public DTO_InsuranceDetails() {
    }

    public DTO_InsuranceDetails(String insuranceNo, String adminId, String customerId,
                                String productId, String iMeiNo, String description,
                                LocalDate dateInsurance, LocalTime timeInsurance) {
        this.insuranceNo = insuranceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.productId = productId;
        this.iMeiNo = iMeiNo;
        this.description = description;
        this.dateInsurance = dateInsurance;
        this.timeInsurance = timeInsurance;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
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

    public String getiMeiNo() {
        return iMeiNo;
    }

    public void setiMeiNo(String iMeiNo) {
        this.iMeiNo = iMeiNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateInsurance() {
        return dateInsurance;
    }

    public void setDateInsurance(LocalDate dateInsurance) {
        this.dateInsurance = dateInsurance;
    }

    public LocalTime getTimeInsurance() {
        return timeInsurance;
    }

    public void setTimeInsurance(LocalTime timeInsurance) {
        this.timeInsurance = timeInsurance;
    }

    @Override
    public String toString() {
        return "DTO_InsuranceDetails{" +
                "insuranceNo='" + insuranceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", productId='" + productId + '\'' +
                ", iMeiNo='" + iMeiNo + '\'' +
                ", description='" + description + '\'' +
                ", dateInsurance=" + dateInsurance +
                ", timeInsurance=" + timeInsurance +
                '}';
    }
}
