package com.Admin.insurance.GUI;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import com.ComponentandDatabase.Components.CustomDialog;
import com.itextpdf.text.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Phrase;
import com.Admin.insurance.DTO.DTO_CustomerInfo;
import com.Admin.insurance.DTO.DTOProductInfo;
import java.util.Locale;

public class PDF_Insurance {
    private JPanel panelBill;
    private String adminID;
    private String adminName;
    private String imei;
    private DTO_CustomerInfo customer;
    private DTOProductInfo productInfo;
    private Date startDate;
    private Date endDate;
    private String description;

    public PDF_Insurance(JPanel panelBill, String adminID, String adminName, 
                        String imei, DTO_CustomerInfo customer, 
                        DTOProductInfo productInfo, Date startDate, 
                        Date endDate, String description) {
        this.panelBill = panelBill;
        this.adminID = adminID;
        this.adminName = adminName;
        this.imei = imei;
        this.customer = customer;
        this.productInfo = productInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public void exportToPDF() {
        // 1. Tạo thư mục xuất nếu chưa tồn tại
        File exportDir = new File("D:\\Warranty_Bills");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        // 2. Tạo tên file PDF
        String warrantyNo = generateWarrantyNo();
        String fileName = "D:\\Warranty_Bills\\" + warrantyNo + ".pdf";

        try {
            // 3. Tạo tài liệu PDF
            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // 4. Thêm nội dung vào PDF
            addPdfHeader(document, warrantyNo);
            addAdminCustomerInfo(document);
            addProductInfo(document);
            addWarrantyDetails(document);
            addSignatures(document);
            addTermsAndConditions(document);
            document.close();
            
          
        } catch (Exception e) {
            CustomDialog.showError("Error exporting !");
            e.printStackTrace();
        }
    }

    private String generateWarrantyNo() {
        // Kiểm tra panelBill để lấy số phiếu bảo hành nếu có
        JPanel billBody = getBillBody();
        for (Component comp : billBody.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("WARRANTY INVOICE No:")) {
                    return label.getText().replace("WARRANTY INVOICE No:", "").trim();
                }
            }
        }
        // Nếu không tìm thấy, tạo số mới
        return "WR-" + String.format("%010d", new Random().nextInt(1_000_000_000)) + 
               (customer != null ? "-" + customer.getCustomerID() : "");
    }

    private JPanel getBillBody() {
        // Tương tự như trong PDFExporter
        if (panelBill.getComponentCount() > 0) {
            Component northComp = panelBill.getComponent(0);
            if (panelBill.getComponentCount() > 1) {
                Component centerComp = panelBill.getComponent(1);
                if (centerComp instanceof JPanel) {
                    JPanel billContent = (JPanel) centerComp;
                    if (billContent.getComponentCount() > 0) {
                        Component scrollComp = billContent.getComponent(0);
                        if (scrollComp instanceof JScrollPane) {
                            JScrollPane scrollPane = (JScrollPane) scrollComp;
                            return (JPanel) scrollPane.getViewport().getView();
                        }
                    }
                }
            }
        }
        return new JPanel();
    }

    private void addPdfHeader(Document document, String warrantyNo) throws DocumentException {
        // Tiêu đề chính
        Font titleFont = getVietnameseFont(18, Font.BOLD);
        titleFont.setColor(BaseColor.BLUE);
        Paragraph title = new Paragraph("PRODUCT WARRANTY CERTIFICATE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15f);
        document.add(title);
        
          // Thông tin hóa đơn
      Font infoFont = getVietnameseFont(12, Font.NORMAL);
      Font boldFont = getVietnameseFont(12, Font.BOLD);

      // Định dạng ngày giờ với AM/PM
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
      String formattedDate = dateFormat.format(new Date());

      Paragraph info = new Paragraph();
      info.add(new Chunk("Invoice No: ", infoFont));
      info.add(new Chunk(warrantyNo, boldFont));
      info.add("\n");
      info.add(new Chunk("Date: " + formattedDate, infoFont));
      info.setAlignment(Element.ALIGN_CENTER);
      info.setSpacingAfter(20f);
      document.add(info);      
    }

    private void addAdminCustomerInfo(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{1, 1});

        BaseColor headerBgColor = new BaseColor(0, 51, 102);

        // Thông tin Admin
        LinkedHashMap<String, String> adminInfo = new LinkedHashMap<>();
        adminInfo.put("Admin.ID:", adminID);
        adminInfo.put("Admin Name", adminName);

        // Thông tin Khách hàng
        LinkedHashMap<String, String> customerInfo = new LinkedHashMap<>();
        if (customer != null) {
            customerInfo.put("Customer.ID:", customer.getCustomerID());
            customerInfo.put("Customer Name:", customer.getFullName());
            customerInfo.put("Address:", customer.getAddress());
            customerInfo.put("Contact", customer.getContact());
        }

        PdfPCell adminCell = new PdfPCell(createInfoSection("ADMIN INFORMATION", adminInfo, getVietnameseFont(12, Font.NORMAL), headerBgColor));
        adminCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell customerCell = new PdfPCell(createInfoSection("CUSTOMER INFORMATION", customerInfo, getVietnameseFont(12, Font.NORMAL), headerBgColor));
        customerCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(adminCell);
        table.addCell(customerCell);
        document.add(table);

        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addProductInfo(Document document) throws DocumentException {
        if (productInfo == null) return;

        LinkedHashMap<String, String> productData = new LinkedHashMap<>();
        productData.put("IMEI.No:", imei);
        productData.put("Product.ID:", productInfo.getProductId());
        productData.put("Product Name:", productInfo.getProductName());
        productData.put("Category.ID:", productInfo.getCategoryId());
        productData.put("Brand:", productInfo.getBrand());
        productData.put("Original Warranty:", productInfo.getWarrantyPeriod());

        PdfPTable productTable = createInfoSection("PRODUCT INFORMATION", productData, getVietnameseFont(12, Font.NORMAL), new BaseColor(0, 51, 102));
        productTable.setSpacingBefore(15f);
        document.add(productTable);

        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addWarrantyDetails(Document document) throws DocumentException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        LinkedHashMap<String, String> warrantyData = new LinkedHashMap<>();
        warrantyData.put("Warranty Start Date:", startDate != null ? dateFormat.format(startDate) : "N/A");
        warrantyData.put("Warranty End Date:", endDate != null ? dateFormat.format(endDate) : "N/A");
        warrantyData.put("Issue Description:", description != null ? description : "");

        PdfPTable warrantyTable = createInfoSection("WARRANTY INFORMATION", warrantyData, getVietnameseFont(12, Font.NORMAL), new BaseColor(0, 51, 102));
        warrantyTable.setSpacingBefore(15f);
        document.add(warrantyTable);

        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addTermsAndConditions(Document document) throws DocumentException {
        Font titleFont = getVietnameseFont(14, Font.BOLD);
        titleFont.setColor(BaseColor.BLUE);
        Paragraph title = new Paragraph("\n\nPRODUCT WARRANTY CERTIFICATE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(20f);
        title.setSpacingAfter(10f);
        document.add(title);

        Font contentFont = getVietnameseFont(12, Font.NORMAL);
        String terms = "1. Warranty applies only to manufacturing defects.\n"
             + "2. No warranty for damages caused by impact or liquid exposure.\n"
             + "3. ID card must be presented when requesting service.\n"
             + "4. Warranty is non-transferable.\n";
          

        Paragraph termsPara = new Paragraph(terms, contentFont);
        termsPara.setSpacingAfter(20f);
        document.add(termsPara);
    }

    private void addSignatures(Document document) throws DocumentException {
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(30f);
        signatureTable.setWidths(new float[]{1, 1});

        // Chữ ký nhân viên (bên trái)
        PdfPCell staffCell = new PdfPCell();
        staffCell.setBorder(Rectangle.NO_BORDER);
        staffCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph staffPara = new Paragraph();
        staffPara.add(new Chunk("\t\tWARRANTY STAFF\n\n\n", getVietnameseFont(12, Font.BOLD)));
        staffPara.add(new Chunk("\n\n\n" + "\t\t\t"+(adminName != null ? adminName : ""), getVietnameseFont(12, Font.NORMAL)));

        
        staffCell.addElement(staffPara);
        signatureTable.addCell(staffCell);

        // Chữ ký khách hàng (bên phải)
        PdfPCell customerCell = new PdfPCell();
        customerCell.setBorder(Rectangle.NO_BORDER);
        customerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph customerPara = new Paragraph();
        customerPara.add(new Chunk("\t\tCUSTOMER\n\n\n", getVietnameseFont(12, Font.BOLD)));
        customerPara.add(new Chunk("\n\n\n" + "\t\t\t"+(customer != null ? customer.getFullName() : ""), getVietnameseFont(12, Font.NORMAL)));

        customerCell.addElement(customerPara);
        signatureTable.addCell(customerCell);

        document.add(signatureTable);
    }

    // Các phương thức hỗ trợ
    private PdfPTable createInfoSection(String title, LinkedHashMap<String, String> data, Font font, BaseColor headerBgColor) {
        PdfPTable section = new PdfPTable(2);
        section.setWidthPercentage(100);
        section.setSpacingBefore(5f);

        // Tiêu đề
        Font titleFont = getVietnameseFont(12, Font.BOLD);
        titleFont.setColor(BaseColor.WHITE);

        PdfPCell titleCell = new PdfPCell(new Phrase(title, titleFont));
        titleCell.setBackgroundColor(headerBgColor);
        titleCell.setColspan(2);
        titleCell.setPadding(5);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        section.addCell(titleCell);

        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String labelText = entry.getKey() != null ? entry.getKey() : " ";
                String valueText = entry.getValue() != null ? entry.getValue() : " ";

                section.addCell(createInfoCell(labelText, true, font));
                section.addCell(createInfoCell(valueText, false, font));
            }
        }

        return section;
    }

    private PdfPCell createInfoCell(String text, boolean isLabel, Font font) {
        if (font == null) {
            font = getVietnameseFont(10, isLabel ? Font.BOLD : Font.NORMAL);
        }

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setMinimumHeight(20f);

        return cell;
    }

    private void addLineSeparator(Document document, float lineWidth, float percentageWidth, BaseColor color) throws DocumentException {
        Paragraph p = new Paragraph();
        LineSeparator ls = new LineSeparator(lineWidth, percentageWidth, color, Element.ALIGN_CENTER, -2);
        p.add(ls);
        document.add(p);
    }

     
    private static Font getVietnameseFont(float size, int style) {
        try {
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, size, style);
        } catch (Exception e) {
            e.printStackTrace();
            return FontFactory.getFont(FontFactory.HELVETICA, size, style); // Font dự phòng nếu có lỗi
        }
    }

}
