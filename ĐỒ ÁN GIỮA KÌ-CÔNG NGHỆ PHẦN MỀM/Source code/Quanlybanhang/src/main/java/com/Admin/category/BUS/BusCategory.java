package com.Admin.category.BUS;

import com.Admin.category.DAO.ControlCategory;
import com.Admin.category.DTO.DTOCategory;
import java.io.File;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BusCategory {
    private ControlCategory dao;

    public BusCategory() {
        dao = new ControlCategory();
    }

    // Thêm danh mục
    public boolean addCategory(DTOCategory category) {
        if (dao.isDuplicateID(category.getCategoryID())) {
            System.out.println("Category ID đã tồn tại.");
            return false;
        }
        return dao.insertCategory(category);
    }

    // Cập nhật danh mục
    public boolean updateCategory(DTOCategory category) {
        return dao.updateCategory(category);
    }

    // Xóa danh mục
    public boolean deleteCategory(String categoryID) {
        return dao.deleteCategory(categoryID);
    }

    // Lấy danh sách tất cả danh mục
    public List<DTOCategory> getAllCategories() {
        return dao.getAllCategories();
    }

    public List<DTOCategory> searchCategory(String keyword, String selectedItem) {
        return dao.searchCategories(keyword, selectedItem);
    }

    // Kiểm tra trùng mã
    public boolean isDuplicateID(String categoryID) {
        return dao.isDuplicateID(categoryID);
    }

   // Trong lớp BUS (Business logic layer)
    public void loadCategoryToTable(DefaultTableModel model) {
        dao.loadCategoryToTable(model);
    }
    
    public List<String> getAllSupplierIDs() {
       return dao.getAllSupplierIDs();
}
    public DTOCategory getCategoryID(String categoryID) {
      return dao.getCategoryByID(categoryID);
}
    public void refreshTable(DefaultTableModel model){
       dao.loadCategoryToTable(model);
}
    public void importFile(File file){
     dao.importFile(file);
 }
    public void exportFile(JTable table){
        dao.exportFile(table);
    }
}
