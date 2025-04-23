package com.Admin.product.BUS;

import com.Admin.product.DAO.DAOIMEI;
import javax.swing.JTable;
import java.io.File;

public class BusIMEI {
    
    private DAOIMEI daoIMEI;

    public BusIMEI() {
        daoIMEI = new DAOIMEI();
    }

    public void importExcelIMEI(File excelFile) {
        daoIMEI.importIMEIFromExcel(excelFile);
    }
    public void uploadtoTable(JTable table) {
        daoIMEI.uploadIMEIDataToTable(table);
    }
    public void updateState(String imeiNo, String newState) {
        daoIMEI.updateIMEIStateInDatabase(imeiNo, newState);
    }
    public void Refresh(JTable table){
        daoIMEI.uploadIMEIDataToTable(table);
    }
    public void cleanIMEIData() {
      daoIMEI.cleanAllIMEIData();
}
    public void exportFile(JTable table){
        daoIMEI.exportFile(table);
    }
    public void Search(JTable table, String selectedColumn, String keyword, String stateFilter){
        daoIMEI.searchIMEI(table, selectedColumn, keyword, stateFilter);
    }
}
