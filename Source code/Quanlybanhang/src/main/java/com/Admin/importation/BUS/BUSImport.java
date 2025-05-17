package com.Admin.importation.BUS;

import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.importation.DAO.DAOImport;
import com.Admin.importation.DTO.DTOBill_Imported;
import com.Admin.importation.DTO.DTOBill_ImportedDetails;
import com.Admin.importation.DTO.DTOBill_ImportedFullDetails;
import com.Admin.importation.DTO.DTO_productStock;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Collections;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Workbook;

public class BUSImport {

    private DAOImport daoImport;

    public BUSImport() {
        daoImport = new DAOImport();  // Khởi tạo DAO để sử dụng
    }

    public ArrayList<DTO_productStock> getAllProductStock() {
        return daoImport.showDataStock();  // Gọi hàm từ DAO
    }
    
     public boolean insertBillImported(DTOBill_Imported bill) {
         return daoImport.insertBillImported(bill);
     }
     
     public boolean insertBillImportedDetails(DTOBill_ImportedDetails detail) {
         return daoImport.insertBillImportedDetails(detail);
     }
     
    public List<DTOBill_ImportedFullDetails> getAllBillFullDetails() {
        try {
            return daoImport.getAllBillFullDetails();
        } 
        catch(SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Error loading bill details: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<DTOBill_Imported> getAllBillImported() {
        try {
            return daoImport.getAllBillImported();
        } 
        catch(SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Error loading bill details: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    
    public List<DTOBill_ImportedFullDetails> searchBillDetails(String searchType, String keyword) {
        try {
            return daoImport.searchBillDetails(searchType, keyword);
        } catch (SQLException e) {
            // Xử lý lỗi tìm kiếm
            return Collections.emptyList();
        }
    }
    
     public void createBillImportedSheet(Workbook workbook) throws SQLException {
         daoImport.createBillImportedSheet(workbook);
     }
     public void createBillImportedDetailsSheet(Workbook workbook, List<DTOBill_ImportedFullDetails> billDetails){
         daoImport.createBillImportedDetailsSheet(workbook, billDetails);
     }
      
}
