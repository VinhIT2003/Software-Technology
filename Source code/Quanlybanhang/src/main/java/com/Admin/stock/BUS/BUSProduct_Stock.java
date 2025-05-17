
package com.Admin.stock.BUS;

import com.Admin.stock.DAO.DAOProduct_Stock; // Import DAO
import com.Admin.stock.DTO.DTOProduct_Stock;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BUSProduct_Stock {
    private DAOProduct_Stock daoProductStock;

    // Constructor
    public BUSProduct_Stock() {
        daoProductStock = new DAOProduct_Stock();
    }

    // Hàm BUS gọi DAO
    public void getAllProductStock(JTable table) {
        daoProductStock.getAllProductStock(table);
    }
    
      public DTOProduct_Stock getProductDetailByID(String productID){
          return daoProductStock.getProductDetailByID(productID);
      }
      
      public void cleanProduct_Stock(){
          daoProductStock.cleanAllProduct_Stock();
      }
     
      public void importFile(File excelFile){
          daoProductStock.importExcel(excelFile);
      }
      
      public void exportFile(JTable table){
          daoProductStock.exportFile(table);
      }
       public void searchProduct_Stock(String keyword, String selected, DefaultTableModel model){
        daoProductStock.searchProduct(keyword, selected, model);
    }
      
}
