package com.Admin.home.DAO;

import com.Admin.home.DTO.DTO_Product;
import com.Admin.home.DTO.DTO_Customer;
import com.Admin.home.DTO.DTO_Orders;
import com.Admin.home.DTO.DTO_BillExported;
import com.Admin.home.DTO.DTO_Insurance;
import com.Admin.home.DTO.DTO_Supplier;
import com.Admin.home.DTO.DTO_Category;
import com.Admin.home.DTO.DTO_BillImport;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Total {

    public DTO_Product showTotalProduct() {
        DTO_Product productSummary = new DTO_Product();
        String sql = "SELECT COUNT(*) AS total FROM Product";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                productSummary.setQuantity(rs.getInt("total")); // Lưu số lượng vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting products: " + e.getMessage());
            e.printStackTrace();
        }

        return productSummary; // Trả về DTO chứa tổng số sản phẩm
    }

    public DTO_Customer showTotalCustomer() {
        DTO_Customer customerSummary = new DTO_Customer();
        String sql = "SELECT COUNT(*) AS total FROM Customer";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                customerSummary.setCustomerID("Total Customers");
                customerSummary.setStatus(String.valueOf(rs.getInt("total"))); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting customers: " + e.getMessage());
            e.printStackTrace();
        }

        return customerSummary; // Trả về DTO chứa tổng số khách hàng
    }

    public DTO_Orders showTotalOrder() {
        DTO_Orders orderSummary = new DTO_Orders();
        String sql = "SELECT COUNT(*) AS total FROM Orders"; // Giả sử bảng lưu đơn hàng là "Orders"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                orderSummary.setOrderNo("Total Orders");
                orderSummary.setStatus(String.valueOf(rs.getInt("total"))); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting orders: " + e.getMessage());
            e.printStackTrace();
        }

        return orderSummary; // Trả về DTO chứa tổng số đơn hàng
    }

    public DTO_BillExported showTotalBillExport() {
        DTO_BillExported billExportSummary = new DTO_BillExported();
        String sql = "SELECT COUNT(*) AS total FROM Bill_Exported"; // Giả sử bảng lưu hóa đơn xuất là "BillExported"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                billExportSummary.setInvoiceNo("Total Bill Exported");
                billExportSummary.setTotalProduct(rs.getInt("total")); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting bill exports: " + e.getMessage());
            e.printStackTrace();
        }

        return billExportSummary; // Trả về DTO chứa tổng số hóa đơn xuất
    }

    public DTO_Insurance showTotalInsurance() {
        DTO_Insurance insuranceSummary = new DTO_Insurance();
        String sql = "SELECT COUNT(*) AS total FROM Insurance"; // Giả sử bảng lưu bảo hành là "Insurance"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                insuranceSummary.setInsuranceNo("Total Insurances");
                insuranceSummary.setCustomerId(String.valueOf(rs.getInt("total"))); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting insurances: " + e.getMessage());
            e.printStackTrace();
        }

        return insuranceSummary; // Trả về DTO chứa tổng số bảo hành
    }

    public DTO_Supplier showTotalSupplier() {
        DTO_Supplier supplierSummary = new DTO_Supplier();
        String sql = "SELECT COUNT(*) AS total FROM Supplier"; // Giả sử bảng lưu nhà cung cấp là "Supplier"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                supplierSummary.setSupID("Total Suppliers");
                supplierSummary.setContact(String.valueOf(rs.getInt("total"))); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting suppliers: " + e.getMessage());
            e.printStackTrace();
        }

        return supplierSummary; // Trả về DTO chứa tổng số nhà cung cấp
    }

    public DTO_Category showTotalCategory() {
        DTO_Category categorySummary = new DTO_Category();
        String sql = "SELECT COUNT(*) AS total FROM Category"; // Giả sử bảng lưu danh mục là "Category"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                categorySummary.setCategoryID("Total Categories");
                categorySummary.setContact(String.valueOf(rs.getInt("total"))); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting categories: " + e.getMessage());
            e.printStackTrace();
        }

        return categorySummary; // Trả về DTO chứa tổng số danh mục
    }
    
    public DTO_BillImport showTotalBillImport() {
        DTO_BillImport billImportSummary = new DTO_BillImport();
        String sql = "SELECT COUNT(*) AS total FROM Bill_Imported"; // Giả sử bảng lưu hóa đơn nhập là "BillImport"

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                billImportSummary.setInvoiceNo("Total Bill Imports");
                billImportSummary.setTotalProduct(rs.getInt("total")); // Lưu tổng số vào DTO
            }

        } catch (SQLException e) {
            System.err.println("Error counting bill imports: " + e.getMessage());
            e.printStackTrace();
        }

        return billImportSummary; // Trả về DTO chứa tổng số hóa đơn nhập
    }
    
    
}
