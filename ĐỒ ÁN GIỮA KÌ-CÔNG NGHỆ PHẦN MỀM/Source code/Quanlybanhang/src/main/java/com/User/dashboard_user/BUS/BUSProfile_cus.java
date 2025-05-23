
package com.User.dashboard_user.BUS;

import com.User.dashboard_user.DAO.DAOProfile_cus;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyCombobox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;

public class BUSProfile_cus {

    private DAOProfile_cus daoProfile;

    public BUSProfile_cus() {
        daoProfile = new DAOProfile_cus(); // Khởi tạo DAO
    }

    public void showProfile(String emailInput, 
                             MyTextField txtID, 
                             MyTextField txtFullName, 
                             MyCombobox<String> cmbGender, 
                             JDateChooser dateOfBirth, 
                             MyTextField txtEmail, 
                             MyTextField txtContact, 
                             JTextArea txtAddress) {
        daoProfile.showProfile(emailInput, txtID, txtFullName, cmbGender, dateOfBirth, txtEmail, txtContact, txtAddress);
    }
    
    public void updateProfile(MyTextField txtID, 
                               MyTextField txtFullName, 
                               MyCombobox<String> cmbGender, 
                               JDateChooser dateOfBirth, 
                               MyTextField txtEmail, 
                               MyTextField txtContact, 
                               JTextArea txtAddress){
        daoProfile.updateProfile(txtID, txtFullName, cmbGender, dateOfBirth, txtEmail, txtContact, txtAddress);
    }
    
    public String getCustomerID(String email) {
        return daoProfile.getCustomerID(email);
    }
    
}
