
package com.Admin.login.BUS;

import com.Admin.login.DAO.ControlLogin;
import com.Admin.login.DAO.ControlRegister;
import com.Admin.login.DTO.DTOAccount_ad;
import com.ComponentandDatabase.Components.CustomDialog;

public class BusAccount_ad {
    private ControlLogin controlLogin = new ControlLogin();
    private ControlRegister controlRegister = new ControlRegister();
    
    public boolean login(String adminID, String password) {
        boolean success = controlLogin.loginCustomer(adminID, password);
        if (success) {
            
        } else {
            CustomDialog.showError("Login failed !");
                    
        }
        return success;
    }

    public DTOAccount_ad getLoggedInAccount() {
        return controlLogin.getAccount();
    }
    public String getName(){
        return controlLogin.getAdminName();
    }
    
    public void registerAd(String idCard, String fullName, String gender,
                                 String contact, String email, String password){
        controlRegister.registerAd(idCard, fullName, gender, contact, email, password);
    }
   
    public String sentOTP(String admin_id){
        return controlLogin.sendOTPToEmail(admin_id);
    }
    public boolean ConfirmOTP(String inputOTP) {
        // Gọi hàm xác nhận OTP từ lớp DAO (controlLogin)
        boolean isValid = controlLogin.confirmOTP(inputOTP);
        return isValid;
    }
    public boolean updatePassword(DTOAccount_ad dto, String confirmPass) {
        return controlLogin.updateNewPassword(dto, confirmPass);
    }
}

