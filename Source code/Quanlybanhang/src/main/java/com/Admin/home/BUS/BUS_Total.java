package com.Admin.home.BUS;

import com.Admin.home.DAO.DAO_Total;
import com.Admin.home.DTO.*;

public class BUS_Total {
    private DAO_Total daoTotal;

    public BUS_Total() {
        this.daoTotal = new DAO_Total();
    }

    public DTO_Product getTotalProducts() {
        return daoTotal.showTotalProduct();
    }

    public DTO_Customer getTotalCustomers() {
        return daoTotal.showTotalCustomer();
    }

    public DTO_Orders getTotalOrders() {
        return daoTotal.showTotalOrder();
    }

    public DTO_BillExported getTotalBillExports() {
        return daoTotal.showTotalBillExport();
    }

    public DTO_Insurance getTotalInsurances() {
        return daoTotal.showTotalInsurance();
    }

    public DTO_Supplier getTotalSuppliers() {
        return daoTotal.showTotalSupplier();
    }

    public DTO_Category getTotalCategories() {
        return daoTotal.showTotalCategory();
    }

    public DTO_BillImport getTotalBillImports() {
        return daoTotal.showTotalBillImport();
    }
}
