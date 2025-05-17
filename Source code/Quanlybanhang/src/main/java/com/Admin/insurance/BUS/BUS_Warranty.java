package com.Admin.insurance.BUS;

import com.Admin.insurance.DAO.DAO_Warranty;
import com.Admin.insurance.DTO.DTO_Insurance;
import com.Admin.insurance.DTO.DTO_InsuranceDetails;
import java.sql.SQLException;
import java.util.List;

public class BUS_Warranty {
    private DAO_Warranty daoWarranty;

    // Constructor để khởi tạo DAO
    public BUS_Warranty() {
        daoWarranty = new DAO_Warranty();
    }

    // Phương thức gọi DAO để thêm hóa đơn bảo hành
    public boolean insertBillWarranty(DTO_Insurance insurance) {
        try {
            return daoWarranty.insertBillWarranty(insurance);
        } catch (SQLException e) {
            System.err.println("Error inserting warranty bill: " + e.getMessage());
            return false; // Trả về `false` nếu có lỗi
        }
    }

    // Phương thức gọi DAO để thêm chi tiết hóa đơn bảo hành
    public boolean insertBillWarrantyDetails(DTO_InsuranceDetails insuranceDetails) {
        try {
            return daoWarranty.insertBillWarrantyDetails(insuranceDetails);
        } catch (SQLException e) {
            System.err.println("Error inserting warranty bill details: " + e.getMessage());
            return false; // Trả về `false` nếu có lỗi
        }
    }
    
     public List<DTO_InsuranceDetails> getAllInsuranceDetails(){
         try {
            return daoWarranty.getAllInsuranceDetails();
        } catch (SQLException e) {
            System.err.println("Error inserting warranty bill details: " + e.getMessage());
            return null;
        }
     }
     
     public List<DTO_InsuranceDetails> searchInsuranceDetails(String searchType, String keyword) {
        try {
            return daoWarranty.searchInsuranceDetails(searchType, keyword);
        } catch (SQLException e) {
            return null; // Hoặc có thể trả về danh sách rỗng: return new ArrayList<>()
        }
    }
     
     public boolean exportToExcel(String filePath) {
         return daoWarranty.exportToExcel(filePath);
     }
}
    
