package com.User.home.BUS;

import com.User.home.DAO.ControlHome;
import com.User.home.DTO.productDTO;
import java.util.ArrayList;

public class productBUS {

    ControlHome controlHome = new ControlHome();

    public ArrayList<productDTO> showProduct(String condition) {
        // return kết quả từ DAO
        return controlHome.showProduct(condition);
    }
    
     public productDTO getProductById(String productId){
         return controlHome.getProductById(productId);
     }
    
     public String getBrandByProductId(String productId) {
         return controlHome.getBrandByProductId(productId);
     }
    
}
