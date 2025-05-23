package com.Admin.insurance.BUS;

import com.Admin.insurance.DAO.DAOProductInfo;
import com.Admin.insurance.DTO.DTOProductInfo;
import java.sql.SQLException;

public class BUS_ProductInfo {
    private DAOProductInfo daoProductInfo;

    // Constructor để khởi tạo DAOProductInfo
    public BUS_ProductInfo() {
        daoProductInfo = new DAOProductInfo();
    }

    // Phương thức gọi DAO để lấy thông tin sản phẩm theo IMEI
    public DTOProductInfo getProductInfoByIMEI(String imei) {
        try {
            return daoProductInfo.getProductInfoByIMEI(imei);
        } catch (SQLException e) {
            System.err.println("Error fetching product info: " + e.getMessage());
            return null; // Trả về null nếu có lỗi
        }
    }
     public String getProductID(String imei) {
        try {
            return daoProductInfo.getProductIDByIMEI(imei);
        } catch (SQLException e) {
            System.err.println("Error fetching product info: " + e.getMessage());
            return null; // Trả về null nếu có lỗi
        }
    }
    
    
}
