package com.Admin.category.DTO;

public class DTOCategory {
    private String categoryID;
    private String categoryName;
    private String supID;
    private String supName;
    private String address;
    private String contact;

    public DTOCategory() {
    }

    public DTOCategory(String categoryID, String categoryName, String supID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.supID = supID;
    }


    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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
}
