package com.Admin.customer.BUS;

import com.Admin.customer.DAO.ControlCustomer;
import com.Admin.customer.DTO.DTOCustomer;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class BusCustomer {
    private ControlCustomer customerDAO;

    // Constructor khởi tạo DAO
    public BusCustomer() {
        customerDAO = new ControlCustomer();
    }

    // Hàm gọi DAO để lấy danh sách khách hàng
    public List<DTOCustomer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
    public List<DTOCustomer> searchCustomer(String selectedColumn, String keyword, String statusFilter) {
      return customerDAO.searchCustomer(selectedColumn, keyword, statusFilter);
}
    public void update(JTable table, JComboBox<String> cmbStatus){
        customerDAO.update(table, cmbStatus);
    }
    public String getCustomerNameByID(String customerID){
        return customerDAO.getCustomerNameByID(customerID);
    }
    public boolean delete(String customerID) {
        return customerDAO.deleteCustomer(customerID);
    }
    public void exportFile(JTable table){
        customerDAO.exportFile(table);
    }
}
