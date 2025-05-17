
package com.Admin.importation.GUI;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.HashMap;
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
public class PDFImporter {
    private JPanel panelBill;
    private String txtAdminID;
    private String txtAdminName;
    private List<Object[]> importItems;
    
    public PDFImporter(JPanel panelBill, String adminID, String adminName, List<Object[]> importItems) {
        this.panelBill = panelBill;
        this.txtAdminID = adminID;
        this.txtAdminName = adminName;
        this.importItems = importItems;
    }

    public void exportToPDF() {
        // 1. Create export directory if not exists
        File exportDir = new File("D:\\Bill_Imported");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        // 2. Generate invoice number and filename
        String invoiceNo = generateInvoiceNo();
        String fileName = "D:\\Bill_Imported\\" + invoiceNo + ".pdf";

        try {
            // 3. Create PDF document with proper margins
            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // 4. Add content to PDF
            addPdfHeader(document, invoiceNo);
            addAdminInfo(document);
            addTransferInfo(document);
            addProductDetails(document);
            addSummary(document);
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
                if (label.getText().startsWith("Transfer Invoice No:")) {
                    return label.getText().replace("Transfer Invoice No:", "").trim();
                }
            }
        }
        return "TRANS-" + String.format("%010d", new Random().nextInt(1_000_000_000)) + "-" + txtAdminID;
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
        return null;
    }

    private void addPdfHeader(Document document, String invoiceNo) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph title = new Paragraph("IMPORT TRANSFER BILL", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15f);
        document.add(title);

        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        String formattedDate = dateFormat.format(new Date());

        Paragraph info = new Paragraph();
        info.add(new Chunk("Transfer Invoice No: ", infoFont));
        info.add(new Chunk(invoiceNo, boldFont));
        info.add("\n");
        info.add(new Chunk("Date: " + formattedDate, infoFont));

        info.setAlignment(Element.ALIGN_CENTER);
        info.setSpacingAfter(20f);
        document.add(info);
    }

    private void addAdminInfo(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        BaseColor headerBgColor = new BaseColor(0, 51, 102);

        LinkedHashMap<String, String> adminInfo = new LinkedHashMap<>();
        adminInfo.put("Admin ID:", txtAdminID);
        adminInfo.put("Admin Name:", txtAdminName);

        // Admin information section
        PdfPCell adminCell = new PdfPCell(createInfoSection("ADMIN INFORMATION", adminInfo, getVietnameseFont(12, Font.NORMAL), headerBgColor));
        adminCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(adminCell);
        
        document.add(table);
        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addTransferInfo(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        BaseColor headerBgColor = new BaseColor(0, 51, 102);

        LinkedHashMap<String, String> transferInfo = new LinkedHashMap<>();
        transferInfo.put("From:", "Warehouse");
        transferInfo.put("To:", "Store");
        transferInfo.put("Transfer Date:", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        PdfPCell transferCell = new PdfPCell(createInfoSection("TRANSFER INFORMATION", transferInfo, 
            FontFactory.getFont(FontFactory.HELVETICA, 12), headerBgColor));
        transferCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(transferCell);
        
        document.add(table);
        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

   private void addProductDetails(Document document) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Paragraph section = new Paragraph("PRODUCT DETAILS", sectionFont);
        section.setSpacingAfter(10f);
        document.add(section);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(15f);

        float[] columnWidths = {1.0f, 1.5f, 3f, 1.5f, 1.5f, 1.8f, 1.8f, 1.8f};
        table.setWidths(columnWidths);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        BaseColor headerBgColor = new BaseColor(0, 51, 102);

        addTableHeader(table, "No.", headerFont, headerBgColor);
        addTableHeader(table, "Product ID", headerFont, headerBgColor);
        addTableHeader(table, "Product Name", headerFont, headerBgColor);
        addTableHeader(table, "Category", headerFont, headerBgColor);
        addTableHeader(table, "Brand", headerFont, headerBgColor);
        addTableHeader(table, "Quantity", headerFont, headerBgColor);
        addTableHeader(table, "Unit Price", headerFont, headerBgColor);
        addTableHeader(table, "Total", headerFont, headerBgColor);

        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        int rowNum = 1;
        BigDecimal grandTotal = BigDecimal.ZERO;

        // Sử dụng Map để theo dõi sản phẩm đã xử lý
        Map<String, Object[]> processedProducts = new LinkedHashMap<>();

        // Xử lý importItems để gộp các sản phẩm trùng
        for (Object[] item : importItems) {
            String productId = item[0].toString();
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng
            if (processedProducts.containsKey(productId)) {
                Object[] existingItem = processedProducts.get(productId);
                existingItem[5] = item[5]; // Cập nhật số lượng mới
            } else {
                processedProducts.put(productId, item.clone()); // Thêm sản phẩm mới
            }
        }

        // Thêm các sản phẩm đã xử lý vào bảng
        for (Object[] item : processedProducts.values()) {
            String productId = item[0].toString();
            String productName = item[1].toString();
            BigDecimal unitPrice = (BigDecimal) item[2];
            String category = item[3].toString();
            String brand = item[4].toString();
            int quantity = (int) item[5];
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

            BaseColor rowColor = rowNum % 2 == 0 ? new BaseColor(248, 248, 248) : BaseColor.WHITE;

            table.addCell(createTableCell(String.valueOf(rowNum++), rowFont, rowColor));
            table.addCell(createTableCell(productId, rowFont, rowColor));
            table.addCell(createTableCell(productName, rowFont, rowColor));
            table.addCell(createTableCell(category, rowFont, rowColor));
            table.addCell(createTableCell(brand, rowFont, rowColor));
            table.addCell(createTableCell(String.valueOf(quantity), rowFont, rowColor));
            table.addCell(createTableCell(formatCurrency(unitPrice.toString()), rowFont, rowColor));
            table.addCell(createTableCell(formatCurrency(totalPrice.toString()), rowFont, rowColor));

            grandTotal = grandTotal.add(totalPrice);
        }

        document.add(table);
        addLineSeparator(document, 0.5f, 95f, BaseColor.LIGHT_GRAY);
    }

    private void addSummary(Document document) throws DocumentException {
        // Sử dụng Map để gộp các sản phẩm trùng (giống trong addProductDetails)
        Map<String, Object[]> processedProducts = new HashMap<>();
        for (Object[] item : importItems) {
            String productId = item[0].toString();
            if (processedProducts.containsKey(productId)) {
                processedProducts.get(productId)[5] = item[5]; // Cập nhật số lượng mới
            } else {
                processedProducts.put(productId, item.clone());
            }
        }

        // Tính toán các giá trị tổng từ processedProducts
        int totalItems = processedProducts.size();
        int totalQuantity = processedProducts.values().stream()
                                           .mapToInt(item -> (int) item[5])
                                           .sum();
        BigDecimal totalAmount = processedProducts.values().stream()
                                               .map(item -> ((BigDecimal) item[2]).multiply(BigDecimal.valueOf((int) item[5])))
                                               .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo font cho label và giá trị
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.RED);

        // Tạo paragraph chứa tất cả thông tin
        Paragraph summary = new Paragraph();
        summary.setAlignment(Element.ALIGN_RIGHT);
        summary.setSpacingBefore(15f);
        summary.setSpacingAfter(10f);

        // Thêm từng dòng thông tin
        summary.add(new Chunk("Total Products: ", labelFont));
        summary.add(new Chunk(String.valueOf(totalItems), valueFont));
        summary.add(Chunk.NEWLINE);

        summary.add(new Chunk("Total Quantity: ", labelFont));
        summary.add(new Chunk(String.valueOf(totalQuantity), valueFont));
        summary.add(Chunk.NEWLINE);

        summary.add(new Chunk("Total Price: ", labelFont));
        summary.add(new Chunk(formatCurrency(totalAmount.toString()), valueFont));

        // Thêm đường kẻ ngăn cách (nằm chính giữa)
        LineSeparator line = new LineSeparator();
        line.setPercentage(50);
        line.setAlignment(LineSeparator.ALIGN_CENTER);

        // Thêm vào document
        document.add(summary);
        document.add(new Chunk(line));
    }
        
        
    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph();
        footer.add(new Chunk("Thank you for your business!", 
            FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY)));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20f);
        document.add(footer);
    }

    private PdfPTable createInfoSection(String title, LinkedHashMap<String, String> data, Font font, BaseColor headerBgColor) {
        PdfPTable section = new PdfPTable(2);
        section.setWidthPercentage(100);
        section.setSpacingBefore(5f);

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        titleFont.setColor(BaseColor.WHITE);

        PdfPCell titleCell = new PdfPCell(new Phrase(title, titleFont));
        titleCell.setBackgroundColor(headerBgColor);
        titleCell.setColspan(2);
        titleCell.setPadding(5);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        section.addCell(titleCell);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            section.addCell(createInfoCell(entry.getKey(), true, font));
            section.addCell(createInfoCell(entry.getValue(), false, font));
        }

        return section;
    }

    private PdfPCell createInfoCell(String text, boolean isLabel, Font font) {
        Font cellFont = isLabel ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12) : font;
        PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setMinimumHeight(20f);
        return cell;
    }

    private void addTableHeader(PdfPTable table, String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setPaddingTop(7);
        cell.setPaddingBottom(7);
        table.addCell(cell);
    }

    private PdfPCell createTableCell(String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }

    private void addLineSeparator(Document document, float lineWidth, float percentage, BaseColor color) 
            throws DocumentException {
        LineSeparator line = new LineSeparator(lineWidth, percentage, color, Element.ALIGN_CENTER, -1);
        document.add(new Chunk(line));
    }

    private String formatCurrency(String amount) {
        try {
            BigDecimal value = new BigDecimal(amount);
            DecimalFormat formatter = new DecimalFormat("###,### VND");
            return formatter.format(value);
        } catch (Exception e) {
            return amount;
        }
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
