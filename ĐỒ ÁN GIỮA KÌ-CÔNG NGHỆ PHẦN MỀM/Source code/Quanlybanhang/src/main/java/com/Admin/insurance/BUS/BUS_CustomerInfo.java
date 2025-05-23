package com.Admin.insurance.BUS;

import com.Admin.insurance.DAO.DAOCustomerInfo;
import com.Admin.insurance.DTO.DTO_CustomerInfo;
import java.sql.SQLException;

public class BUS_CustomerInfo {
    private DAOCustomerInfo daoCustomerInfo;

    // Constructor để khởi tạo DAO
    public BUS_CustomerInfo() {
        daoCustomerInfo = new DAOCustomerInfo();
    }

    // Phương thức gọi DAO để lấy thông tin khách hàng theo IMEI
    public DTO_CustomerInfo getCustomerInfoByIMEI(String imei) {
        try {
            return daoCustomerInfo.getCustomerInfoByIMEI(imei);
        } catch (SQLException e) {
            System.err.println("Error fetching customer info: " + e.getMessage());
            return null; // Trả về null nếu có lỗi
        }
    }
    public String getCustomerIDByIMEI(String imei){
        try {
            return daoCustomerInfo.getCustomerIDByIMEI(imei); // Gọi DAO và bắt lỗi SQL
        } catch (SQLException e) {
            System.err.println("Error fetching Customer_ID: " + e.getMessage());
            return null; // Trả về null nếu có lỗi SQL
        }
    }

}
