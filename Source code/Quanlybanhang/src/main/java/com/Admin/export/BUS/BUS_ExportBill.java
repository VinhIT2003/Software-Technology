package com.Admin.export.BUS;

import com.Admin.export.DAO.DAO_ExportBill;
import com.Admin.export.DTO.DTO_BillExported;
import com.Admin.export.DTO.DTO_BillExportedDetail;
import com.Admin.export.DTO.DTO_BillExport;
import com.ComponentandDatabase.Components.CustomDialog;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BUS_ExportBill {
    private final DAO_ExportBill daoExportBill;

    public BUS_ExportBill() {
        this.daoExportBill = new DAO_ExportBill();
    }
    
    public DTOProfile_cus getCustomerInfo(String customerID) throws SQLException {
        return daoExportBill.getCustomerInfoByID(customerID);
    }
    
  
    public DTOProfile_cus getCustomerInfoSafe(String customerID) {
        try {
            return daoExportBill.getCustomerInfoByID(customerID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean insertBillExported(DTO_BillExported bill) {
        return daoExportBill.insertBillExported(bill);
    }
    
    public boolean insertBillDetail(DTO_BillExportedDetail detail, List<String> imeiList) {
        return daoExportBill.insertBillExportedDetail(detail, imeiList);
   }


     public boolean updateProductQuantity(DTO_BillExportedDetail detail) {
        try {
            // Gọi phương thức từ DAO và truyền toàn bộ đối tượng detail
            return daoExportBill.updateProductQuantity(detail);
        } catch (SQLException e) {
            System.err.println("Error updating product quantity in BUS layer: " + e.getMessage());
            return false;
        }
    }
     
    public List<DTO_BillExportedDetail> getAllBillDetails() {
        try {
            return daoExportBill.getAllBillDetails();
        } catch (SQLException e) {
            e.printStackTrace();
               CustomDialog.showError("Data upload eror ! ");
            return Collections.emptyList();
        }
    }
    
    public List<DTO_BillExport> getAllBillExported() {
        try {
            return daoExportBill.getAllBillExported();
        } catch (SQLException e) {
            e.printStackTrace();
               CustomDialog.showError("Data upload eror ! ");
            return Collections.emptyList();
        }
    }
    
    public boolean exportToExcel(String filePath) {
        return daoExportBill.exportToExcel(filePath);
    }
    
    
     public List<DTO_BillExportedDetail> searchBillDetails(String searchType, String searchKeyword) {
        // Có thể thêm logic nghiệp vụ ở đây trước khi gọi DAO
        return daoExportBill.searchBillDetails(searchType, searchKeyword);
    }
    
     public String getWarranry(String productID) {
        try {
            return daoExportBill.getWarranty(productID);
        } catch (SQLException e) {
            e.printStackTrace();
               CustomDialog.showError("Data upload eror ! ");
            return null;
        }
    }
     
}