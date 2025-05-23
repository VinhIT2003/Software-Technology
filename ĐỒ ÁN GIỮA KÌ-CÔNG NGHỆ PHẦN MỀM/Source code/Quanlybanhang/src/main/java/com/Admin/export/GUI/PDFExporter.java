package com.Admin.export.GUI;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import com.ComponentandDatabase.Components.CustomDialog;
import com.itextpdf.text.Rectangle;
import java.util.Locale;
import java.text.DecimalFormat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Phrase;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.Admin.export.BUS.BUS_ExportBill;
import com.Admin.export.BUS.BUS_OrderDetail;

public class PDFExporter {
    private JPanel panelBill, panelTitle;
    private String txtAdminID;
    private String txtAdminName;
    private DTOProfile_cus customer;
    private BUS_OrderDetail busOrderDetail;
    private List<Object[]> orderItems;
    private double discount;
    private String imeiNumbers;
    private BUS_ExportBill bus_ExportBill;

    public PDFExporter(JPanel panelBill, String adminID, String adminName, 
                      DTOProfile_cus customer, BUS_OrderDetail busOrderDetail, 
                      List<Object[]> orderItems, double discount, String imeiNumbers) {
        this.panelBill = panelBill;
        this.txtAdminID = adminID;
        this.txtAdminName = adminName;
        this.customer = customer;
        this.busOrderDetail = busOrderDetail;
        this.orderItems = orderItems;
        this.discount = discount;
        this.imeiNumbers = imeiNumbers;
    }

    public void exportToPDF() {
        // 1. Create export directory if not exists
        File exportDir = new File("D:\\Bill_Exported");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        // 2. Generate invoice number and filename
        String invoiceNo = generateInvoiceNo();
        String fileName = "D:\\Bill_Exported\\" + invoiceNo + ".pdf";

        try {
            // 3. Create PDF document with proper margins
            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // 4. Add content to PDF with improved formatting
            addPdfHeader(document, invoiceNo);
            addAdminCustomerInfo(document);
            addOrderDetails(document);
            addOrderSummary(document);
            addImeiInfo(document);
            addFooter(document);

            document.close();
            
        } catch (Exception e) {
            CustomDialog.showError("Error exporting PDF !");
            e.printStackTrace();
        }
    }

    private String generateInvoiceNo() {
        JPanel billBody = getBillBody();
        for (Component comp : billBody.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Invoice No:")) {
                    return label.getText().replace("Invoice No:", "").trim();
                }
            }
        }
        return String.format("%010d", new Random().nextInt(1_000_000_000)) + "-" + 
               customer.getCustomerID();
    }

    private JPanel getBillBody() {
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

        JPanel newBody = new JPanel();
        newBody.setLayout(new BoxLayout(newBody, BoxLayout.Y_AXIS));
        newBody.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(newBody);
        scrollPane.setBorder(null);

        JPanel billContent = new JPanel(new BorderLayout());
        billContent.add(scrollPane, BorderLayout.CENTER);

        panelBill.removeAll();
        panelBill.add(panelTitle, BorderLayout.NORTH);
        panelBill.add(billContent, BorderLayout.CENTER);
        panelBill.revalidate();

        return newBody;
    }

    private void addPdfHeader(Document document, String invoiceNo) throws DocumentException {
      // Tiêu đề chính
      Font titleFont = getVietnameseFont(18, Font.BOLD);
      titleFont.setColor(BaseColor.BLUE);
      Paragraph title = new Paragraph("SALES INVOICE", titleFont);
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
      info.add(new Chunk(invoiceNo, boldFont));
      info.add("\n");
      info.add(new Chunk("Date: " + formattedDate, infoFont));

      info.setAlignment(Element.ALIGN_CENTER);
      info.setSpacingAfter(20f);
      document.add(info);
  }

    private void addAdminCustomerInfo(Document document) throws DocumentException {
        // Two-column layout for admin and customer info
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{1, 1});

        // Màu nền cho phần tiêu đề (giống bảng Orders)
        BaseColor headerBgColor = new BaseColor(0, 51, 102); 

        // Tạo danh sách thông tin Admin
        LinkedHashMap<String, String> adminInfo = new LinkedHashMap<>();
        adminInfo.put("Admin ID:", txtAdminID);
        adminInfo.put("Admin Name:", txtAdminName);

        // Tạo danh sách thông tin Customer
        LinkedHashMap<String, String> customerInfo = new LinkedHashMap<>();
        customerInfo.put("Customer ID:", customer.getCustomerID());
        customerInfo.put("Customer Name:", customer.getFullName());
        customerInfo.put("Address:", customer.getAddress());
        customerInfo.put("Contact:", customer.getContact());

        // Admin information section
        PdfPCell adminCell = new PdfPCell(createInfoSection("ADMIN INFORMATION", adminInfo, getVietnameseFont(12, Font.NORMAL), headerBgColor));
        adminCell.setBorder(Rectangle.NO_BORDER);

        // Customer information section
        PdfPCell customerCell = new PdfPCell(createInfoSection("CUSTOMER INFORMATION", customerInfo, getVietnameseFont(12, Font.NORMAL), headerBgColor));
        customerCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(adminCell);
        table.addCell(customerCell);
        document.add(table);

        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    
   private PdfPTable createInfoSection(String title, LinkedHashMap<String, String> data, Font font, BaseColor headerBgColor) {
        PdfPTable section = new PdfPTable(2);
        section.setWidthPercentage(100);
        section.setSpacingBefore(5f);

        // Tiêu đề với màu chữ trắng
        Font titleFont = getVietnameseFont(12, Font.BOLD);
        titleFont.setColor(BaseColor.WHITE); // Đổi màu chữ thành trắng

        PdfPCell titleCell = new PdfPCell(new Phrase(title, titleFont));
        titleCell.setBackgroundColor(headerBgColor); // Nền giống bảng Orders
        titleCell.setColspan(2);
        titleCell.setPadding(5);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        section.addCell(titleCell);

        // Kiểm tra nếu `data` không bị null hoặc rỗng
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
       // Kiểm tra nếu `font` bị null thì dùng font tiếng Việt mặc định
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


    private void addOrderDetails(Document document) throws DocumentException {
       // Section title with Order No
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Paragraph section = new Paragraph();

        // Add "ORDER DETAILS" text
        Chunk orderDetailsChunk = new Chunk("ORDER DETAILS", sectionFont);
        section.add(orderDetailsChunk);

        // Add Order No if available
        if (!orderItems.isEmpty()) {
            String orderNo = orderItems.get(0)[0].toString();
            Font orderNoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
            section.add(new Chunk("   (Order No: " + orderNo + ")", orderNoFont));
        }
    
    section.setSpacingAfter(10f);
    document.add(section);

        // Create table with 7 columns
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(110); // Mở rộng bảng hơn so với trang PDF
        table.setSpacingBefore(10f);
        table.setSpacingAfter(15f);

        // Column widths (Increased width for Product ID, Quantity, and Discount)
        float[] columnWidths = {1.2f, 2.0f, 3.5f, 2.0f, 2.5f, 1.8f, 2.5f, 2.8f}; 
        table.setWidths(columnWidths);


        // Table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        BaseColor headerBgColor = new BaseColor(0, 51, 102);

        addTableHeader(table, "No.", headerFont, headerBgColor);
        addTableHeader(table, "Product ID", headerFont, headerBgColor);
        addTableHeader(table, "Product Name", headerFont, headerBgColor);
        addTableHeader(table, "Quantity", headerFont, headerBgColor);
        addTableHeader(table, "Unit Price", headerFont, headerBgColor);
        addTableHeader(table, "Discount", headerFont, headerBgColor);
        addTableHeader(table, "Total", headerFont, headerBgColor);
        addTableHeader(table, "Warranty_Period", headerFont, headerBgColor);
        // Table data
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        int rowNum = 1;
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (Object[] item : orderItems) {
            bus_ExportBill= new BUS_ExportBill();
            String productId = item[2].toString();
            BigDecimal unitPrice = (BigDecimal) item[3];
            int quantity = (int) item[4];
            String warranty= bus_ExportBill.getWarranry(productId);
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(quantity))
                                    .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discount / 100)));
            // Alternate row color for better readability
            BaseColor rowColor = rowNum % 2 == 0 ? new BaseColor(248, 248, 248) : BaseColor.WHITE;

            table.addCell(createTableCell(String.valueOf(rowNum++), rowFont, rowColor));
            table.addCell(createTableCell(productId, rowFont, rowColor));
            table.addCell(createTableCell(busOrderDetail.getProductName(productId), rowFont, rowColor));
            table.addCell(createTableCell(String.valueOf(quantity), rowFont, rowColor));
            table.addCell(createTableCell(formatCurrency(unitPrice.toString()), rowFont, rowColor));
            table.addCell(createTableCell(discount + "%", rowFont, rowColor));
            table.addCell(createTableCell(formatCurrency(itemTotal.toString()), rowFont, rowColor));
            table.addCell(createTableCell(warranty, rowFont, rowColor));
            
            grandTotal = grandTotal.add(itemTotal);
        }

        document.add(table);
        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addTableHeader(PdfPTable table, String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setPaddingTop(7);
        cell.setPaddingBottom(7);
        cell.setNoWrap(false); // 🔹 Ngăn tiêu đề bị xuống dòng (Tự động giãn ô)
        table.addCell(cell);
    }


    private PdfPCell createTableCell(String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    private BigDecimal calculateTotal(Object price, Object quantity) {
        BigDecimal unitPrice = new BigDecimal(price.toString());
        int qty = Integer.parseInt(quantity.toString());
        return unitPrice.multiply(BigDecimal.valueOf(qty))
                      .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discount/100)));
    }

  private String formatCurrency(String amount) {
    try {
        // Chuyển đổi sang BigDecimal để xử lý số thập phân chính xác
        BigDecimal value = new BigDecimal(amount.replaceAll("[^\\d.]", ""));
        
        // Định dạng số với 2 chữ số thập phân, không dấu phân cách hàng nghìn
        DecimalFormat df = new DecimalFormat("0.00");
        df.setDecimalSeparatorAlwaysShown(true); // Luôn hiển thị phần thập phân
        df.setGroupingUsed(false); // Không dùng dấu phân cách hàng nghìn
        
        return df.format(value);
    } catch (Exception e) {
        return amount; // Trả về nguyên bản nếu có lỗi
    }
}

    private void addOrderSummary(Document document) throws DocumentException {
       // Calculate grand total and total quantity
       BigDecimal grandTotal = BigDecimal.ZERO;
       int totalQuantity = 0;

       for (Object[] item : orderItems) {
           grandTotal = grandTotal.add(calculateTotal(item[3], item[4]));
           totalQuantity += ((Number) item[4]).intValue(); // Sum up quantities
       }

       // Tạo bảng chứa cả Payment method và Order Summary
       PdfPTable containerTable = new PdfPTable(2);
       containerTable.setWidthPercentage(100);
       containerTable.setSpacingBefore(15f);
       containerTable.setWidths(new float[]{1, 1}); // 2 cột bằng nhau

       // Cột trái - Payment Method
       PdfPCell paymentCell = new PdfPCell();
       paymentCell.setBorder(Rectangle.NO_BORDER);

       if (!orderItems.isEmpty()) {
           String orderNo = orderItems.get(0)[0].toString();
           String paymentMethod = busOrderDetail.getPayment(orderNo);

           if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
               paymentMethod = "N/A"; // đảm bảo luôn có gì đó để hiển thị
           }

           Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
           Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

           Paragraph paymentParagraph = new Paragraph();
           paymentParagraph.setAlignment(Element.ALIGN_LEFT); // căn lề trái
           paymentParagraph.setSpacingBefore(10f);
           paymentParagraph.setSpacingAfter(10f);

           paymentParagraph.add(new Phrase("Payment Method: ", labelFont));
           paymentParagraph.add(new Phrase(paymentMethod, valueFont));

           paymentCell.addElement(paymentParagraph);

       } else {
           // Trường hợp không có orderItems → để vẫn hiển thị "No Payment Info"
           Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.GRAY);
           paymentCell.addElement(new Phrase("No payment information available.", labelFont));
       }

       containerTable.addCell(paymentCell);

       // Cột phải - Order Summary (dạng label)
       PdfPCell summaryCell = new PdfPCell();
       summaryCell.setBorder(Rectangle.NO_BORDER);

       Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
       Font valueFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.RED);

       Paragraph summaryParagraph = new Paragraph();
       summaryParagraph.setAlignment(Element.ALIGN_RIGHT);
       summaryParagraph.setSpacingBefore(10f);

       summaryParagraph.add(new Phrase("Total Quantity: ", labelFont));
       summaryParagraph.add(new Phrase(String.valueOf(totalQuantity) + "\n", valueFont));

       summaryParagraph.add(new Phrase("Total Price: ", labelFont));
       summaryParagraph.add(new Phrase(formatCurrency(grandTotal.toString()), valueFont));

       summaryCell.addElement(summaryParagraph);
       containerTable.addCell(summaryCell);

       document.add(containerTable);
  }

    private PdfPCell createTableCell(String text, Font font, BaseColor bgColor, int alignment) {
        // Kiểm tra nếu giá trị đầu vào bị null
        if (text == null) text = " ";
        if (font == null) font = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Tạo nội dung ô bảng
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));

        // Kiểm tra nếu có màu nền
        if (bgColor != null) {
            cell.setBackgroundColor(bgColor);
        }

        // Thiết lập thuộc tính ô
        cell.setBorder(Rectangle.BOX); // Đảm bảo có viền
        cell.setBorderWidth(0.75f);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setPadding(6);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        return cell;
    }


    private void addImeiInfo(Document document) throws DocumentException {
        if (imeiNumbers != null && !imeiNumbers.trim().isEmpty()) {
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
            Paragraph imeiTitle = new Paragraph("IMEI INFORMATION", titleFont);
            imeiTitle.setSpacingBefore(25f); // Tăng khoảng cách trước tiêu đề
            document.add(imeiTitle);

            // Thêm khoảng trống trước bảng
            document.add(new Paragraph("\n")); // Đẩy bảng xuống sâu hơn

            // Create bordered box for IMEI numbers
            PdfPTable imeiTable = new PdfPTable(1);
            imeiTable.setWidthPercentage(100);
            imeiTable.setSpacingBefore(10f); // Đẩy bảng xuống thêm một chút
            imeiTable.setSpacingAfter(15f);

            PdfPCell imeiCell = new PdfPCell(new Phrase(imeiNumbers, 
                FontFactory.getFont(FontFactory.HELVETICA, 10)));
            imeiCell.setPadding(12); // Tăng khoảng cách bên trong ô
            imeiCell.setBorderWidth(0.5f);
            imeiCell.setBorderColor(BaseColor.LIGHT_GRAY);
            imeiTable.addCell(imeiCell);

            document.add(imeiTable);
        }
    }


    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph();
        footer.add(new Chunk("Thank you for your purchase!", 
            FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, BaseColor.DARK_GRAY)));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20f);
        document.add(footer);
        
        // Add decorative footer line
        addLineSeparator(document, 0.5f, 50f, BaseColor.GRAY);
    }

    private void addLineSeparator(Document document, float lineWidth, float percentage, BaseColor color) 
            throws DocumentException {
        Paragraph line = new Paragraph();
        LineSeparator ls = new LineSeparator(lineWidth, percentage, color, Element.ALIGN_CENTER, -1);
        line.add(new Chunk(ls));
        line.setSpacingAfter(10f);
        document.add(line);
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