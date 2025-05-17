package com.Admin.login.DTO;

public class DTOAccount_ad {
    private String adminID;
    private String adminName;
    private String gender;
    private String email;
    private String contact;
    private String password;

    // Constructor không tham số
    public DTOAccount_ad() {}

    // Constructor đầy đủ
    public DTOAccount_ad(String adminID, String adminName, String gender, String email, String contact, String password) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    // Getter và Setter
    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
        @Override
    public String toString() {
        return "DTOAccount_ad{" +
                "adminID='" + adminID + '\'' +
                ", adminName='" + adminName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
