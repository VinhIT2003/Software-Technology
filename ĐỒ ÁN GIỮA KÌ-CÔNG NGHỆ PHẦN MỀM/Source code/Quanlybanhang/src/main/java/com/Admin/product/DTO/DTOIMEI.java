
package com.Admin.product.DTO;

public class DTOIMEI {

    private String imeiNo;
    private String productID;
    private String state;

    // Constructor mặc định
    public DTOIMEI() {}

    // Constructor với tất cả các tham số
    public DTOIMEI(String imeiNo, String productID, String state) {
        this.imeiNo = imeiNo;
        this.productID = productID;
        this.state = state;
    }

    // Getter and Setter methods

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DTOIMEI{" +
                "imeiNo='" + imeiNo + '\'' +
                ", productID='" + productID + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

