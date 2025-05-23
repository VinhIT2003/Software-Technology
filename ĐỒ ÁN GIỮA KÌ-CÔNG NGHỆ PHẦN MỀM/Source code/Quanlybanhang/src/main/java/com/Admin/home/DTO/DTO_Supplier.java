package com.Admin.home.DTO;

public class DTO_Supplier {
    private String supID;
    private String supName;
    private String address;
    private String contact;

    // Constructor không đối số
    public DTO_Supplier() {
    }

    // Constructor đầy đủ
    public DTO_Supplier(String supID, String supName, String address, String contact) {
        this.supID = supID;
        this.supName = supName;
        this.address = address;
        this.contact = contact;
    }

    // Getter và Setter
    public String getSupID() {
        return supID;
    }

    public void setSupID(String supID) {
        this.supID = supID;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
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

    // toString()
    @Override
    public String toString() {
        return "DTO_Supplier{" +
               "supID='" + supID + '\'' +
               ", supName='" + supName + '\'' +
               ", address='" + address + '\'' +
               ", contact='" + contact + '\'' +
               '}';
    }
}
