package com.User.dashboard_user.DTO;

import java.sql.Date;

public class DTOProfile_cus {

    private String customerID;
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String contact;
    private String address;

    public DTOProfile_cus() {
    }

    public DTOProfile_cus(String customerID, String fullName, String gender, Date dateOfBirth, String email,
                          String contact, String address) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
  @Override
    public String toString() {
        return "DTOAccount_cus{" +
                "customerID='" + customerID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    
}

