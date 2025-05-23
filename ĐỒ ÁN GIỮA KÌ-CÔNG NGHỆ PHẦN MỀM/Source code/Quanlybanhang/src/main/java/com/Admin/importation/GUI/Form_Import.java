package com.Admin.importation.GUI;

import com.Admin.dashboard_admin.GUI.Dashboard_ad;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.importation.BUS.BUSImport;
import com.Admin.importation.DTO.DTO_productStock;
import com.Admin.importation.DTO.DTOBill_Imported;
import com.Admin.importation.DTO.DTOBill_ImportedDetails;
import com.Admin.importation.DTO.DTOBill_ImportedFullDetails;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JFileChooser;              // Hộp thoại chọn file
import javax.swing.filechooser.FileNameExtensionFilter; // Bộ lọc file (Excel .xlsx)
import java.io.File;                          // Đối tượng tệp
import java.util.HashMap;
import java.util.Map;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.table.TableColumnModel;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Form_Import extends JPanel {
    private JPanel panel, panelSearch, billBody;
    private MyPanel panelBill, panelTitle;
    private JLabel lblAdminID, lblAdminName, lblQuantity, lblInvoice;
    private MyButton  bntSearch, bntExportFile, bntDetails, bntRefresh, bntAddBill, bntExport;
    private MyTextField txtSearch, txtAdminID, txtAdminName;
    private BUSImport busImport;
    private MyCombobox<String> cmbSearch;
    private MyTable tableProductStock;
    public static String invoiceNo;
    public static String orderNo;
    private List<Object[]> selectedProducts = new ArrayList<>();
    private JSpinner spinerQuantity;
    private DefaultTableModel model;
    public Form_Import() {
        initComponents();
        init();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1530, 860)); // Giữ kích thước nhưng không ép buộc vị trí
        setBackground(Color.WHITE); // Kiểm tra hiển thị
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1530, 860); // Giữ nguyên layout của các thành phần
        panel.setBackground(Color.WHITE); // Màu xanh dương
        add(panel);
        
        
        bntRefresh = new MyButton("Refresh", 20);
        bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
        bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
        bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
        bntRefresh.setBounds(10, 40, 140, 35); // Tăng chiều rộng để icon không bị che mất
        bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
        bntRefresh.setForeground(Color.BLACK);
        bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntRefresh.addActionListener((e) -> {
            Refresh();
        });
        panel.add(bntRefresh);
        
        bntExportFile = new MyButton("Export File", 0);
        bntExportFile.setBackgroundColor(Color.WHITE); // Màu nền
        bntExportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
        bntExportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
        bntExportFile.setBounds(190, 40, 170, 35); // Tăng chiều rộng để icon không bị che mất
        bntExportFile.setFont(new Font("sansserif", Font.BOLD, 16));
        bntExportFile.setForeground(Color.BLACK);
        bntExportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
       bntExportFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");
            fileChooser.setSelectedFile(new File("Bill_Imported_Report.xlsx"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Đảm bảo có phần mở rộng ".xlsx"
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                boolean success = exportToExcel(filePath);

                if (success) {
                   CustomDialog.showSuccess("File exported successfully !");
                } else {
                   
                }
            }
        });

        
        panel.add(bntExportFile);
        
        
        lblAdminID= new JLabel("Admin.ID");
        lblAdminID.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAdminID.setForeground(Color.BLACK);
        lblAdminID.setBounds(430, 5, 100, 35);
        panel.add(lblAdminID);
       
          txtAdminID = new MyTextField();
          txtAdminID.setBorder(BorderFactory.createLineBorder(Color.GRAY));
          txtAdminID.setTextColor(Color.RED); // Đặt màu mong muốn
          txtAdminID.setLocked(true); // Gọi sau cũng không sao
          txtAdminID.setTextFont(new Font("Times New Roman", Font.BOLD, 16));
          txtAdminID.setBackgroundColor(Color.WHITE);
          txtAdminID.setBounds(400, 40, 130, 35);
          txtAdminID.setText(Dashboard_ad.adminID);
          panel.add(txtAdminID);
          
           
         lblAdminName= new JLabel("Admin Name");
         lblAdminName.setFont(new Font("Arial", Font.PLAIN, 16));
         lblAdminName.setForeground(Color.BLACK);
         lblAdminName.setBounds(610, 5, 100, 35);
         panel.add(lblAdminName);
         
          txtAdminName = new MyTextField();
          txtAdminName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
          txtAdminName.setTextColor(Color.BLUE); // Đặt màu mong muốn
          txtAdminName.setLocked(true); // Gọi sau cũng không sao
          txtAdminName.setTextFont(new Font("Times New Roman", Font.BOLD, 16));
          txtAdminName.setBackgroundColor(Color.WHITE);
          txtAdminName.setBounds(570, 40, 160, 35);
          txtAdminName.setText(Dashboard_ad.getAdminName(txtAdminID.getText().strip()));
          panel.add(txtAdminName);
       
        String[] items = {"Product.ID", "Product Name", "Brand.ID"};
           cmbSearch= new MyCombobox<>(items);
           cmbSearch.setBounds(50, 100, 135,35);
           cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
           cmbSearch.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
           cmbSearch.repaint();
           cmbSearch.revalidate();
           panel.add(cmbSearch);
           
              
          txtSearch = new MyTextField();
          txtSearch.setHint("Enter the search key word...");
          txtSearch.setBounds(220, 100, 230, 35); // Đặt vị trí và kích thước
          txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
          txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
          panel.add(txtSearch);
         
         bntSearch= new MyButton("Search", 20);
         bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
         bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
         bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
         bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
         bntSearch.setForeground(Color.WHITE);
         bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);     
         bntSearch.setBounds(480, 100, 110, 35);
         panel.add(bntSearch);
    
         
        panelBill = new MyPanel();
        panelBill.setLayout(new BorderLayout());
        panelBill.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Chỉ 1 viền chính
        panelBill.setBackground(Color.WHITE);
        panelBill.setBounds(820, 20, 450, 680);
        panel.add(panelBill);

       // Tạo panel title "Bill For Order" (không thêm border riêng)
       JPanel paneltitle = new JPanel();
       paneltitle.setBackground(Color.RED);
       paneltitle.setPreferredSize(new Dimension(450, 30)); // Fixed height
       JLabel lblTitle = new JLabel("BILL FOR IMPORT", JLabel.CENTER);
       lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
       lblTitle.setForeground(Color.WHITE);
       paneltitle.add(lblTitle);
       panelBill.add(paneltitle, BorderLayout.NORTH);

       // Tạo panel content chính với scroll (không border)
       JPanel billContent = new JPanel();
       billContent.setLayout(new BorderLayout());
       billContent.setBackground(Color.WHITE);

       // Các panel con (không border)
       billBody = new JPanel();
       billBody.setLayout(new BoxLayout(billBody, BoxLayout.Y_AXIS));
       billBody.setBackground(Color.WHITE);

       // Scroll pane chính (không border)
       JScrollPane mainScrollPane = new JScrollPane(billBody);
       mainScrollPane.setBorder(null); // Loại bỏ border mặc định của scroll pane
       mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

       // Thêm vào billContent
       billContent.add(mainScrollPane, BorderLayout.CENTER);
       panelBill.add(billContent, BorderLayout.CENTER);
      
       panel.add(panelBill);
       
          // 1️⃣ Tên cột
        String[] columnNames = {
            "Product.ID", "Product Name","Price", "Category.ID", "Brand.ID", 
             "Quantity in Stock"
        };

        // 2️⃣ Tạo model
        model = new DefaultTableModel(columnNames, 0);


        // 4️⃣ Định dạng font
        Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font headerFont = new Font("SansSerif", Font.BOLD, 16);

        // 5️⃣ Tạo bảng sử dụng MyTable
        tableProductStock = new MyTable(
            model,
            new Color(255, 255, 255),  // Nền bảng
            new Color(0, 0, 0),        // Chữ bảng
            new Color(250, 219, 216),  // Nền dòng được chọn
            new Color(0, 0, 0),        // Chữ dòng được chọn
            Color.decode("#FF6666"),   // Nền tiêu đề
            new Color(255, 255, 255),  // Chữ tiêu đề
            contentFont,
            headerFont
        );

        JScrollPane scrollPane = MyTable.createScrollPane(tableProductStock, 10, 150, 790, 550);
        SwingUtilities.invokeLater(() -> {
              loadDataStock();
              tableProductStock.adjustColumnWidths();         // Căn chỉnh cột
          });          
        
        tableProductStock.getSelectionModel().addListSelectionListener(event -> {
         if (!event.getValueIsAdjusting() && tableProductStock.getSelectedRow() != -1) {
             int selectedRow = tableProductStock.getSelectedRow();

             // Kiểm tra nếu dữ liệu tồn kho hợp lệ
             Object quantityObj = model.getValueAt(selectedRow, 5);
             if (quantityObj instanceof Number) {
                 int quantityInStock = ((Number) quantityObj).intValue();

                 // Cập nhật Spinner với số lượng tồn kho làm max
                 SpinnerNumberModel quantityModel = new SpinnerNumberModel(
                     quantityInStock,   // Giá trị ban đầu
                     0,                 // Giá trị tối thiểu
                     quantityInStock,   // Giá trị tối đa = tồn kho
                     1                  // Bước nhảy
                 );
                 spinerQuantity.setModel(quantityModel);
                 spinerQuantity.setEnabled(true);

             } else {
                 System.err.println("Error: Không thể lấy số lượng tồn kho.");
             }
         }
     });

        
        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));
        panel.add(scrollPane);
       
        bntDetails = new MyButton("Bill Details", 0);
        bntDetails.setBackgroundColor(Color.WHITE); // Màu nền
        bntDetails.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
        bntDetails.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
        bntDetails.setFont(new Font("sansserif", Font.BOLD, 16));
        bntDetails.setForeground(Color.BLACK);
        bntDetails.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\bill_export.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
        bntDetails.setBounds(620, 100, 150, 35);
        bntDetails.addActionListener((e) -> {
            Bill_ImportDetails billDetails= new Bill_ImportDetails();
            billDetails.setVisible(true);
        });
        
        panel.add(bntDetails);
        
        lblQuantity= new JLabel("Quantity in Stock");
        lblQuantity.setFont(new Font("sansserif", Font.BOLD, 18));
        lblQuantity.setForeground(Color.BLACK);
        lblQuantity.setBounds(200,730, 220, 35);
        panel.add(lblQuantity);
        
      SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 0, 1000, 1);
      spinerQuantity = new JSpinner(quantityModel);
      JComponent editor = spinerQuantity.getEditor();
      JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
      textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
       textField.setBackground(Color.WHITE);
       spinerQuantity.setBounds(380, 730, 60, 30);
       panel.add(spinerQuantity);
        
        bntAddBill = new MyButton("Add Bill", 20);
        bntAddBill.setBackgroundColor(Color.decode("#2196F3")); // Xanh dương chính
        bntAddBill.setHoverColor(Color.decode("#42A5F5"));       // Hover sáng hơn
        bntAddBill.setPressedColor(Color.decode("#1976D2"));     // Nhấn đậm hơn
        bntAddBill.setFont(new Font("Times New Roman", Font.BOLD, 16));
        bntAddBill.setForeground(Color.WHITE);
        bntAddBill.setBounds(610, 732, 110, 35);
        bntAddBill.addActionListener(e -> {
            // Lấy dòng được chọn từ bảng
            int selectedRow = tableProductStock.getSelectedRow();

            if (selectedRow == -1) {
                CustomDialog.showError("Please select a product first!");
                return;
            }

            // Lấy thông tin sản phẩm từ bảng
            String productID = model.getValueAt(selectedRow, 0).toString();       // Product.ID
            String productName = model.getValueAt(selectedRow, 1).toString();     // Product Name
            BigDecimal price = new BigDecimal(model.getValueAt(selectedRow, 2).toString());             // Price
            String categoryID = model.getValueAt(selectedRow, 3).toString();      // Category.ID
            String brandID = model.getValueAt(selectedRow, 4).toString();         // Brand.ID
            int quantity = (int) spinerQuantity.getValue();    
           

            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                CustomDialog.showError("Quantity must be greater than 0!");
                return;
            }

            // Thêm vào danh sách sản phẩm đã chọn
            selectedProducts.add(new Object[]{
                productID,
                productName,
                price,
                categoryID,
                brandID,
                quantity
            });

            // Cập nhật hóa đơn
            createImportBillContent(selectedProducts);
        });
        
        panel.add(bntAddBill);
       
        bntExport = new MyButton("Generate/Save Bill", 20);
        bntExport.setBackgroundColor(Color.decode("#009688")); // Xanh dương chính
        bntExport.setHoverColor(Color.decode("#00695C"));       // Hover sáng hơn
        bntExport.setPressedColor(Color.decode("#00796B")); 
        bntExport.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bntExport.setForeground(Color.WHITE);
        bntExport.setBounds(950, 720, 200, 60);
        bntExport.addActionListener((e) -> {
            boolean confirm = CustomDialog.showOptionPane(
                "Confirm Exportation",
                "Are you sure want to export bill?",
                UIManager.getIcon("OptionPane.questionIcon"),
                Color.decode("#FF6666")
            );

            if (confirm) {
                try {
                    String adminID = txtAdminID.getText().trim();
                    String adminName = txtAdminName.getText().trim();

                    // 1. Xuất file PDF trước
                    PDFImporter pdfImport = new PDFImporter(panelBill, adminID, adminName, selectedProducts);
                    pdfImport.exportToPDF();

                    // 2. Xử lý dữ liệu để gộp các sản phẩm trùng và tính toán lại
                    Map<String, Object[]> processedProducts = new LinkedHashMap<>();
                    for (Object[] product : selectedProducts) {
                        String productId = product[0].toString();
                        // Nếu sản phẩm đã tồn tại, chỉ cập nhật Quantity (lấy giá trị cuối cùng)
                        if (processedProducts.containsKey(productId)) {
                            processedProducts.get(productId)[5] = product[5]; // Cập nhật Quantity
                        } else {
                            processedProducts.put(productId, product.clone());
                        }
                    }

                    // 3. Tính toán tổng số lượng và tổng giá sau khi đã gộp sản phẩm trùng
                    int totalProducts = processedProducts.size();
                    BigDecimal totalPrice = BigDecimal.ZERO;

                    // 4. Tạo danh sách chi tiết bill đã xử lý
                    List<DTOBill_ImportedDetails> details = new ArrayList<>();
                    LocalDate currentDate = LocalDate.now();
                    LocalTime currentTime = LocalTime.now();

                    for (Object[] product : processedProducts.values()) {
                        String productId = product[0].toString();
                        BigDecimal unitPrice = (BigDecimal) product[2];
                        int quantity = (int) product[5];
                        BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

                        // Tạo DTO cho chi tiết bill
                        DTOBill_ImportedDetails detail = new DTOBill_ImportedDetails();
                        detail.setInvoiceNo(invoiceNo);
                        detail.setAdminId(adminID);
                        detail.setProductId(productId);
                        detail.setQuantity(quantity);
                        detail.setUnitPrice(unitPrice);
                        detail.setTotalPrice(itemTotal);
                        detail.setDateImported(currentDate);
                        detail.setTimeImported(currentTime);

                        details.add(detail);
                        totalPrice = totalPrice.add(itemTotal);
                    }

                    // 5. Tạo DTO cho bill chính
                    DTOBill_Imported bill = new DTOBill_Imported();
                    bill.setInvoiceNo(invoiceNo);
                    bill.setAdminId(adminID);
                    bill.setTotalProduct(totalProducts);
                    bill.setTotalPrice(totalPrice);

                    // 6. Thực hiện insert vào database
                    busImport = new BUSImport();

                    // Insert bill chính
                    boolean billInserted = busImport.insertBillImported(bill);

                    // Insert các chi tiết bill
                    boolean allDetailsInserted = true;
                    for (DTOBill_ImportedDetails detail : details) {
                        if (!busImport.insertBillImportedDetails(detail)) {
                            allDetailsInserted = false;
                            break;
                        }
                    }

                    // 7. Hiển thị thông báo kết quả
                    if (billInserted && allDetailsInserted) {
                        CustomDialog.showSuccess("Bill exported PDF and data saved successfully!");
                         billBody.removeAll();
                         billBody.revalidate();
                         billBody.repaint();
                    } else {
                        CustomDialog.showError("Error saving data to database!");
                    }

                } catch (Exception ex) {
                    CustomDialog.showError("Export error: " + ex.getMessage());
                    ex.printStackTrace();
                }
                
            }
        });
        panel.add(bntExport);
       
   }
    
    public void Refresh(){
        SwingUtilities.invokeLater(() -> {
             loadDataStock();
             tableProductStock.adjustColumnWidths();        // Căn chỉnh cột
           });     
         cmbSearch.setSelectedIndex(0);
         billBody.removeAll();
         billBody.revalidate();
         billBody.repaint();

 }
    
    
    private void loadDataStock() {
        busImport = new BUSImport();  // Khởi tạo lớp BUS
         List<DTO_productStock> productList = busImport.getAllProductStock();  // Lấy dữ liệu

         model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

         for (DTO_productStock product : productList) {
           Object[] row = {
               product.getProductID(),
               product.getProductName(),
               product.getPrice(),
               product.getCategoryID(),
               product.getBrandID(),
               product.getQuantityInStock()
           };
           model.addRow(row);  // Thêm dòng vào bảng
       }
   }
    
    private JLabel createSeparator() {
        JLabel separator = new JLabel("===================================================");
        separator.setForeground(Color.GRAY);
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        return separator;
    }

    // Hàm thêm dòng thông tin
    private void addInfoRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setBackground(Color.WHITE);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 12));
        
        rowPanel.add(lblLabel);
        rowPanel.add(lblValue);
        panel.add(rowPanel);
    }

    
    private void createImportBillContent(List<Object[]> importItems) {
        billBody = getBillBody();
        if (billBody == null) {
            CustomDialog.showError("Bill display area not found!");
            return;
        }

        billBody.removeAll();
        billBody.setLayout(new BoxLayout(billBody, BoxLayout.Y_AXIS));
        billBody.setBackground(Color.WHITE);
        String adminID= txtAdminID.getText().strip();
        // ===== 0. Invoice No =====
        invoiceNo = String.format("%010d", new Random().nextInt(1_000_000_000)) + "-"+adminID;
        lblInvoice = new JLabel("Transfer Invoice No: " + invoiceNo, SwingConstants.CENTER);
        lblInvoice.setFont(new Font("Arial", Font.BOLD, 16));
        lblInvoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        billBody.add(lblInvoice);
        billBody.add(Box.createVerticalStrut(15));

        // ===== 1. Admin Information =====
        JPanel adminPanel = createSectionPanel("ADMIN INFORMATION");
        addInfoRow(adminPanel, "Admin ID:", txtAdminID.getText());
        addInfoRow(adminPanel, "Admin Name:", txtAdminName.getText());
        billBody.add(adminPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(10));

        // ===== 2. Transfer Information =====
        JPanel transferPanel = createSectionPanel("TRANSFER INFORMATION");
        addInfoRow(transferPanel, "From:", "Warehouse");
        addInfoRow(transferPanel, "To:", "Store");
        addInfoRow(transferPanel, "Transfer Date:", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        billBody.add(transferPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(10));

        // ===== 3. Product Details =====
        JPanel productPanel = createSectionPanel("PRODUCT DETAILS");

        String[] columns = {"No.", "Product ID", "Product Name", "Category", "Brand", "Quantity", "Unit Price", "Total"};
        DefaultTableModel billModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;
        int totalQuantity = 0;
        int stt = 1;

        Map<String, Integer> productIdToRowMap = new HashMap<>();

        for (Object[] item : importItems) {
            String productID = item[0].toString();
            int newQty = (int) item[5]; // Lấy số lượng mới

            if (productIdToRowMap.containsKey(productID)) {
                int existingRow = productIdToRowMap.get(productID);

                // Cập nhật số lượng mới thay vì cộng dồn
                billModel.setValueAt(newQty, existingRow, 5);

                // Cập nhật Total
                BigDecimal unitPrice = new BigDecimal(((String) billModel.getValueAt(existingRow, 6)).replaceAll("[^\\d]", ""));
                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(newQty));
                billModel.setValueAt(String.format("%,d VND", totalPrice.intValue()), existingRow, 7);
                continue;
            }

            // Nếu là sản phẩm mới, thêm vào danh sách
            String productName = item[1].toString();
            BigDecimal unitPrice = (BigDecimal) item[2];
            String categoryID = item[3].toString();
            String brandID = item[4].toString();

            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(newQty));
            totalAmount = totalAmount.add(totalPrice);
            totalItems++;
            totalQuantity += newQty;

            billModel.addRow(new Object[]{
                stt++,
                productID,
                productName,
                categoryID,
                brandID,
                newQty,
                String.format("%,d VND", unitPrice.intValue()),
                String.format("%,d VND", totalPrice.intValue())
            });

            productIdToRowMap.put(productID, billModel.getRowCount() - 1);
        }


        JTable productTable = new JTable(billModel);
        productTable.setRowHeight(35);
        //productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // Căn giữa tiêu đề và làm đậm chữ
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 12)); // Làm đậm tiêu đề
        headerRenderer.setBackground(Color.DARK_GRAY); // Nền tối
        headerRenderer.setForeground(Color.WHITE); // Chữ trắng

        // Áp dụng cho tất cả cột tiêu đề
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        

        // Điều chỉnh độ rộng cột tối ưu
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(150);

        JScrollPane tableScroll = new JScrollPane(productTable);
        tableScroll.setPreferredSize(new Dimension(800, Math.min(importItems.size() * 35 + 50, 300)));
        productPanel.add(tableScroll);

        billBody.add(productPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(15));

        // ===== 4. Summary =====
        JPanel summaryPanel = createSectionPanel("TRANSFER SUMMARY");
        totalItems = billModel.getRowCount();
            totalQuantity = 0;
            totalAmount = BigDecimal.ZERO;

            for (int row = 0; row < billModel.getRowCount(); row++) {
                totalQuantity += (int) billModel.getValueAt(row, 5);

                String priceText = ((String) billModel.getValueAt(row, 7)).replaceAll("[^\\d]", "");
                totalAmount = totalAmount.add(new BigDecimal(priceText));
            }
        addInfoRow(summaryPanel, "Total Products:", String.valueOf(totalItems));
        addInfoRow(summaryPanel, "Total Quantity:", String.valueOf(totalQuantity));
        addInfoRow(summaryPanel, "Total Amount:", String.format("%,d VND", totalAmount.intValue()));
        billBody.add(summaryPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(15));

        billBody.revalidate();
        billBody.repaint();
    }

   
        private JPanel getBillBody() {
            // Kiểm tra cấu trúc panel theo đúng cách bạn đã thiết kế
            if (panelBill.getComponentCount() > 0) {
                Component northComp = panelBill.getComponent(0); // Panel tiêu đề (NORTH)

                // Lấy component CENTER (index 1 nếu có cả NORTH và CENTER)
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

            // Fallback: tạo mới nếu không tìm thấy (đảm bảo không bao giờ null)
            JPanel newBody = new JPanel();
            newBody.setLayout(new BoxLayout(newBody, BoxLayout.Y_AXIS));
            newBody.setBackground(Color.WHITE);

            // Tạo lại cấu trúc scroll pane nếu cần
            JScrollPane scrollPane = new JScrollPane(newBody);
            scrollPane.setBorder(null);

            // Tạo lại cấu trúc billContent
            JPanel billContent = new JPanel(new BorderLayout());
            billContent.add(scrollPane, BorderLayout.CENTER);

            // Cập nhật lại panelBill
            panelBill.removeAll();
            panelBill.add(panelTitle, BorderLayout.NORTH);
            panelBill.add(billContent, BorderLayout.CENTER);
            panelBill.revalidate();

            return newBody;
         }
        
        
     public JPanel createSectionPanel(String title) {
        JPanel panelcreate = new JPanel();
        panelcreate.setLayout(new BoxLayout(panelcreate, BoxLayout.Y_AXIS));
        panelcreate.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelcreate.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        panelcreate.add(titleLabel);
        panelcreate.add(Box.createVerticalStrut(5));
        panelcreate.add(separator);
        panelcreate.add(Box.createVerticalStrut(5));

        return panelcreate;
    }
      
      public boolean exportToExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {

            busImport= new BUSImport();
            List<DTOBill_ImportedFullDetails> billDetails = busImport.getAllBillFullDetails();

            // Tạo sheet cho Bill_Imported
            busImport.createBillImportedSheet(workbook);

            // Tạo sheet cho Bill_Imported_Details
            busImport.createBillImportedDetailsSheet(workbook, billDetails);

            // Ghi file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                return true;
            }
   
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            CustomDialog.showError("Error saving file ! " + e.getMessage());
            return false;
        }
    }

     
     
}

