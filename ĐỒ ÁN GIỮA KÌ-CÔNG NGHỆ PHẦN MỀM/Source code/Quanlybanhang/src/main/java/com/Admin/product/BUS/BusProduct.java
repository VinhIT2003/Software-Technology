package com.Admin.product.BUS;
import java.util.List;
import javax.swing.JSpinner;
import java.math.BigDecimal;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.product.DAO.DAOProduct;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyCombobox;
import com.Admin.category.DTO.DTOCategory;
import com.Admin.product.DTO.DTOProduct;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class BusProduct {
   
    private String imagePath;

    private DAOProduct daoProduct;

    public BusProduct() {
        daoProduct = new DAOProduct(); // Khởi tạo đối tượng DAOProduct
    }

    // Phương thức xử lý tải ảnh khi nhấn nút Upload
   public void handleUpload(MyButton bntupload, JPanel panel) {
     imagePath = daoProduct.handleUploadButton(bntupload, panel); // Lưu lại đường dẫn ảnh
}
    
    public String getImagePath() {
        return imagePath;
    }

    // ⭐ Thêm phương thức này để gọi từ DAOProduct
    public List<DTOCategory> getAllCategoriesWithSupplier() {
        return daoProduct.getAllCategoriesWithSupplier();
    }
    // ✅ Hàm xử lý lưu sản phẩm
    public void saveProduct(DTOProduct product) {
        daoProduct.saveProduct(product);  // Gọi phương thức saveProduct của DAO để lưu vào DB
    }
    
   public void uploadProduct(DefaultTableModel model){
       daoProduct.uploadProductToTable(model);
   }
    public DTOProduct getProductById(String productId) {
        return daoProduct.getProductById(productId);
    }
    
    public boolean updateProduct(DTOProduct product) {
        try {
            // Gọi phương thức update từ DAO
            boolean updateResult = daoProduct.updateProduct(product);
            
            if (updateResult) {
                CustomDialog.showSuccess("Product updated successfully!");
                return true;
            } else {
               CustomDialog.showError("Product update failed: ID not found.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomDialog.showError("An error occurred while updating the product.");
            return false;
        }
    }
    
    public boolean deleteProduct(String productId) {
        try {
            // 1. Kiểm tra sản phẩm có tồn tại không
            DTOProduct product = daoProduct.getProductById(productId);
            if (product == null) {
                CustomDialog.showError("Can't fine the product to delete !");
                return false;
            }

            // 2. Gọi DAO để thực hiện xóa
            boolean deleteResult = daoProduct.deleteProduct(productId);
            
            if (deleteResult) {
                CustomDialog.showSuccess("Product deleted successfully !");
                return true;
            } else {
                CustomDialog.showError("Product delete failed: ID not found.");
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            CustomDialog.showError("An error occurred while deleting the product." + e.getMessage());
            return false;
        }
    }
    
    public void searchProduct(String keyword, String selected, DefaultTableModel model){
        daoProduct.searchProduct(keyword, selected, model);
    }
    public void exportFile(String path){
        daoProduct.exportProductToExcel(path);
    }
}


