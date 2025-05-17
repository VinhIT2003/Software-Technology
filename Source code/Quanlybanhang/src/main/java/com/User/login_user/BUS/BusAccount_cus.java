package com.User.login_user.BUS;

import java.util.regex.Pattern;
import com.User.login_user.DTO.DTOAccount_cus;
import com.User.login_user.DAO.ControlRegister_User;
import com.User.login_user.DAO.ControlLogin_cus;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;

public class BusAccount_cus {

    private ControlRegister_User dao;
    private ControlLogin_cus daoLogin;
    private DTOAccount_cus currentAccount; // Lưu thông tin tài khoản sau khi login

    public BusAccount_cus() {
        dao = new ControlRegister_User();
        daoLogin = new ControlLogin_cus(DatabaseConnection.connect());
    }

    public void registerCustomer(DTOAccount_cus customerDTO) {
        dao.registerCustomer(customerDTO);
    }

    public String getName() {
        if (daoLogin != null) {
            return daoLogin.getCustomerName();  // ✅ Lấy tên từ DAO sau khi login thành công
        }
        return null;
    }

    public DTOAccount_cus login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            CustomDialog.showError("Please fill both your email and password!");
            return null;
        }

        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(emailRegex, email)) {
            CustomDialog.showError("Invalid email format. Please check again!");
            return null;
        }

        // Gọi DAO để login
        currentAccount = daoLogin.login(email, password);

        if (currentAccount == null) {
            CustomDialog.showError("Login failed. Please check your credentials!");
        }
        
        return currentAccount;
    }
    
    public String sentOTP(String email){
        return daoLogin.sendOTPToEmail(email);
    }
    public boolean ConfirmOTP(String inputOTP) {
        // Gọi hàm xác nhận OTP từ lớp DAO (controlLogin)
        boolean isValid = daoLogin.confirmOTP(inputOTP);
        return isValid;
    }
   public boolean updateNewPassword(DTOAccount_cus dto, String confirmPass){
        return daoLogin.updateNewPassword(dto, confirmPass);
    }
}
