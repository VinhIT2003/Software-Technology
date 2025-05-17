package com.Admin.export.DTO;

// Kế thừa từ DTO_BillExported
public class DTO_BillExport extends DTO_BillExported {
    private String description;

    // Constructor mặc định
    public DTO_BillExport() {
        super();
    }

    // Constructor đầy đủ (gọi đến constructor của lớp cha)
    public DTO_BillExport(String invoiceNo, String adminId, String customerId, int totalProduct, String description) {
        super(invoiceNo, adminId, customerId, totalProduct);
        this.description = description;
    }

    // Getter & Setter cho `description`
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Ghi đè phương thức `toString()` để hiển thị `description`
    @Override
    public String toString() {
        return "DTO_BillExport{" +
                "invoiceNo='" + getInvoiceNo() + '\'' +
                ", adminId='" + getAdminId() + '\'' +
                ", customerId='" + getCustomerId() + '\'' +
                ", totalProduct=" + getTotalProduct() +
                ", description='" + description + '\'' +
                '}';
    }
}
