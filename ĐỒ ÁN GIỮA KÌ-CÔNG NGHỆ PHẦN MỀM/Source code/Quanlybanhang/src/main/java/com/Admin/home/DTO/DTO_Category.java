
package com.Admin.home.DTO;

public class DTO_Category {
    private String categoryID;
    private String categoryName;
    private String supID;
    private String supName;
    private String address;
    private String contact;

    public DTO_Category() {
    }

    public DTO_Category(String categoryID, String categoryName, String supID) {
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
    
    @Override
    public String toString() {
        return "DTO_Category{" +
               "categoryID='" + categoryID + '\'' +
               ", categoryName='" + categoryName + '\'' +
               ", supID='" + supID + '\'' +
               ", supName='" + supName + '\'' +
               ", address='" + address + '\'' +
               ", contact='" + contact + '\'' +
               '}';
    }
    
}
