
package com.Admin.dashboard_admin.DTO;

public class DTOProfile_ad {
    private String adminID;
    private String adminName;
    private String gender;
    private String email;
    private String contact;
    private String image;

    // Constructor không tham số
    public DTOProfile_ad() {}

    // Constructor đầy đủ
    public DTOProfile_ad(String adminID, String adminName, String gender, String email, String contact, String image) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
        this.image= image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
        @Override
    public String toString() {
        return "DTOAccount_ad{" +
                "adminID='" + adminID + '\'' +
                ", adminName='" + adminName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

}
