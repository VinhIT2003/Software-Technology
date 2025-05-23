
package com.Admin.home.DTO;

import java.time.LocalDate;


public class DTO_Insurance {
    private String insuranceNo;
    private String adminId;
    private String customerId;
    private LocalDate startDateInsurance;
    private LocalDate endDateInsurance;

    public DTO_Insurance() {
    }

    public DTO_Insurance(String insuranceNo, String adminId, String customerId,
                         LocalDate startDateInsurance, LocalDate endDateInsurance) {
        this.insuranceNo = insuranceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.startDateInsurance = startDateInsurance;
        this.endDateInsurance = endDateInsurance;
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

    public LocalDate getStartDateInsurance() {
        return startDateInsurance;
    }

    public void setStartDateInsurance(LocalDate startDateInsurance) {
        this.startDateInsurance = startDateInsurance;
    }

    public LocalDate getEndDateInsurance() {
        return endDateInsurance;
    }

    public void setEndDateInsurance(LocalDate endDateInsurance) {
        this.endDateInsurance = endDateInsurance;
    }

    @Override
    public String toString() {
        return "DTO_Insurance{" +
                "insuranceNo='" + insuranceNo + '\'' +
                ", adminId='" + adminId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", startDateInsurance=" + startDateInsurance +
                ", endDateInsurance=" + endDateInsurance +
                '}';
    }
}

