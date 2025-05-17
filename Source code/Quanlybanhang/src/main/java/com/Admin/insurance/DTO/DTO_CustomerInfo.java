package com.Admin.insurance.DTO;

public class DTO_CustomerInfo {
    private String customerID;
    private String fullName;
    private String address;
    private String contact;

    // Constructor
    public DTO_CustomerInfo(String customerID, String fullName, String address, String contact) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.address = address;
        this.contact = contact;
    }

    // Getter & Setter
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

    // Phương thức toString() để dễ dàng kiểm tra dữ liệu
    @Override
    public String toString() {
        return "DTOCustomer{" +
                "customerID='" + customerID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
