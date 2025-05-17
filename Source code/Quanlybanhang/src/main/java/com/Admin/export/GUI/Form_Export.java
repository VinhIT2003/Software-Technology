
package com.Admin.export.GUI;

import com.Admin.dashboard_admin.GUI.Dashboard_ad;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import com.Admin.export.BUS.BUS_OrderDetail;
import com.Admin.export.BUS.BUS_ExportBill;
import com.Admin.export.DTO.DTO_Oderdetails;
import com.Admin.export.DTO.DTO_BillExported;
import com.Admin.export.DTO.DTO_BillExportedDetail;
import com.Admin.product.DTO.DTOIMEI;
import com.User.order.GUI.OrderUpdateNotifier;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.ComponentandDatabase.Components.CustomDialog;
import javax.swing.border.LineBorder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.math.BigDecimal;  // Cho BigDecimal
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class Form_Export extends JPanel {
    private JPanel panel, panelSearch,billBody;
    private MyPanel panelBill, panelTitle;
    private JLabel lblAdminID, lblAdminName, lblIMEI, lblDiscount, lblInvoice;
    private MyButton bntSearch, bntSearchOrder, bntExportFile, bntDetails, bntRefresh, bntAddBill, bntExport, bntPDF;
    private MyTextField txtSearch,txtSearchOrder, txtAdminID, txtAdminName;
    private JTextArea txtIMEI;
    private MyCombobox<String> cmbSearch, cmbSearchOrder;
    private MyTable tableOrderDetails, tableBillExportDetail;
    public static String invoiceNo;
    public static String orderNo;
    private DefaultTableModel model;
    private JSpinner spinnerDiscount;
    private BUS_OrderDetail busOrderDetail;
    private BUS_ExportBill busExportBill;
    private DTOProfile_cus customer;

    public Form_Export() {
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
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
            fileChooser.setSelectedFile(new File("Bill_Export_Report.xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Đảm bảo có đuôi .xlsx
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                busExportBill = new BUS_ExportBill();
                boolean success = busExportBill.exportToExcel(filePath);

                if (success) {
                    CustomDialog.showSuccess("Export successful! File saved at: " + filePath);
                } else {
                    CustomDialog.showError("Export failed. Please try again.");
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
       
          String[] itemsOrder = {"Order.No", "Customer.ID", "Date Order"};
           cmbSearchOrder= new MyCombobox<>(itemsOrder);
           cmbSearchOrder.setBounds(50, 100, 135,35);
           cmbSearchOrder.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
           cmbSearchOrder.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
           cmbSearchOrder.repaint();
           cmbSearchOrder.revalidate();

                    // 👉 Thêm đoạn invokeLater để đảm bảo cmbSearch được refresh UI
           SwingUtilities.invokeLater(() -> {
               
             cmbSearchOrder.repaint();
             cmbSearchOrder.revalidate();
              //cmbSearch.updateUI(); // 👈 Bắt buộc để refresh lại giao diện
           });

           panel.add(cmbSearchOrder);
          
           
          txtSearchOrder = new MyTextField();
          txtSearchOrder.setHint("Enter the search key word...");
          txtSearchOrder.setBounds(220, 100, 230, 35); // Đặt vị trí và kích thước
          txtSearchOrder.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
          txtSearchOrder.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
          txtSearchOrder.setHintFont(new Font("Arial", Font.ITALIC, 14));
          txtSearchOrder.setBackgroundColor(Color.decode("#F5FFFA"));
          panel.add(txtSearchOrder);
         
         bntSearchOrder= new MyButton("Search", 20);
         bntSearchOrder.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
         bntSearchOrder.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
         bntSearchOrder.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
         bntSearchOrder.setFont(new Font("Times New Roman", Font.BOLD, 16));
         bntSearchOrder.setForeground(Color.WHITE);
         bntSearchOrder.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);     
         bntSearchOrder.setBounds(480, 100, 110, 35);
         bntSearchOrder.addActionListener(e -> {
            String searchType = (String) cmbSearchOrder.getSelectedItem();
            String keyword = txtSearchOrder.getText().trim();

             busOrderDetail = new BUS_OrderDetail();
            List<DTO_Oderdetails> searchResults = busOrderDetail.searchOrderDetails(searchType, keyword);

            // Gọi phương thức hiển thị kết quả lên table
            displaySearchResults(searchResults);
        });
         panel.add(bntSearchOrder);
            
 
           bntDetails = new MyButton("Bill Details", 0);
           bntDetails.setBackgroundColor(Color.WHITE); // Màu nền
           bntDetails.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntDetails.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntDetails.setFont(new Font("sansserif", Font.BOLD, 16));
           bntDetails.setForeground(Color.BLACK);
           bntDetails.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\bill_export.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
           bntDetails.setBounds(620, 100, 150, 35);
           bntDetails.addActionListener((e) -> {
               Bill_ExportDetails billDetail= new Bill_ExportDetails();
               billDetail.setVisible(true);
           });
           panel.add(bntDetails);
           



// Tạo panelBill với layout và style đơn giản      
            
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
       JLabel lblTitle = new JLabel("BILL FOR ORDER", JLabel.CENTER);
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
      
            // 1️⃣ Tên cột
        String[] columnNames = {
            "Order.No", "Customer.ID", "Product.ID", "Price", 
            "Quantity", "Date Order", "Time Order" , "Status"
        };

        // 2️⃣ Tạo model
        model = new DefaultTableModel(columnNames, 0);


        // 4️⃣ Định dạng font
        Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font headerFont = new Font("SansSerif", Font.BOLD, 16);

        // 5️⃣ Tạo bảng sử dụng MyTable
        tableOrderDetails = new MyTable(
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

        JScrollPane scrollPane = MyTable.createScrollPane(tableOrderDetails, 10, 150, 790, 550);

        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

           SwingUtilities.invokeLater(() -> {
              loadConfirmedOrderDetailsToTable();
               tableOrderDetails.adjustColumnWidths();         // Căn chỉnh cột
          });          
        
          tableOrderDetails.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    int[] selectedRows = tableOrderDetails.getSelectedRows();
                    if (selectedRows.length > 0) {
                        // Kiểm tra cùng Order.No
                        String firstOrderNo = tableOrderDetails.getValueAt(selectedRows[0], 0).toString();
                        List<DTOIMEI> allImeis = new ArrayList<>();
                        int totalQuantity = 0;

                        for (int row : selectedRows) {
                            String currentOrderNo = tableOrderDetails.getValueAt(row, 0).toString();
                            if (!currentOrderNo.equals(firstOrderNo)) {
                                throw new Exception("All selected items must have the same Order.No!");
                            }

                            String productID = tableOrderDetails.getValueAt(row, 2).toString();
                            int quantity = Integer.parseInt(tableOrderDetails.getValueAt(row, 4).toString());
                            totalQuantity += quantity;

                            // Lấy IMEI cho sản phẩm này với giới hạn số lượng
                            List<DTOIMEI> productImeis = busOrderDetail.getIMEIByProductIDWithLimit(productID, quantity);
                            allImeis.addAll(productImeis);
                        }

                        // Hiển thị tất cả IMEI
                        displayIMEIs(allImeis, totalQuantity);
                    }
                } catch (Exception ex) {
                    CustomDialog.showError(ex.getMessage());
                    txtIMEI.setText("");
                }
            }
        });
           
              
           
        // 8️⃣ Thêm scrollPane vào panel
        panel.add(scrollPane);   
        
        lblIMEI= new JLabel("IMEI");
        lblIMEI.setFont(new Font("sansserif", Font.BOLD, 18));
        lblIMEI.setForeground(Color.BLACK);
        lblIMEI.setBounds(50,730, 100, 35);
        panel.add(lblIMEI);

        txtIMEI = new JTextArea();
        txtIMEI.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtIMEI.setLineWrap(true); // Tự động xuống dòng khi hết chiều rộng
        txtIMEI.setWrapStyleWord(true); // Xuống dòng theo từ
        txtIMEI.setBackground(Color.WHITE); // Nền trắng
        txtIMEI.setBorder(new LineBorder(Color.GRAY)); // Viền đơn màu xám

        JScrollPane scrollIMEI= new JScrollPane(txtIMEI);
        scrollIMEI.setBorder(new LineBorder(Color.GRAY)); // Viền cho JScrollPane
        scrollIMEI.setBounds(100, 710, 400, 88); // Thay đổi tọa độ và kích thước nếu cần
        panel.add(scrollIMEI);
        
        lblDiscount= new JLabel("Discount");
        lblDiscount.setFont(new Font("sansserif", Font.BOLD, 18));
        lblDiscount.setForeground(Color.BLACK);
        lblDiscount.setBounds(520,730, 130, 35);
        panel.add(lblDiscount);
        
                // 1. Thay đổi SpinnerNumberModel để sử dụng Double thay vì Integer
        SpinnerNumberModel discountModel = new SpinnerNumberModel(
            0.0,    // initial value (double)
            0.0,    // minimum value
            100.0,  // maximum value
            0.5     // step size (cho phép giá trị thập phân)
        );

        // 2. Tạo spinner với model mới
        spinnerDiscount = new JSpinner(discountModel);

        // 3. Thiết lập định dạng hiển thị (2 số thập phân)
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerDiscount, "0.0");
        spinnerDiscount.setEditor(editor);

        // 4. Tuỳ chỉnh font và màu nền
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerDiscount.getEditor()).getTextField();
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        textField.setBackground(Color.WHITE);

        // 5. Đặt vị trí và thêm vào panel
        spinnerDiscount.setBounds(610, 732, 60, 30);
        panel.add(spinnerDiscount);
        

        bntAddBill = new MyButton("Add Bill", 20);
        bntAddBill.setBackgroundColor(Color.decode("#2196F3")); // Xanh dương chính
        bntAddBill.setHoverColor(Color.decode("#42A5F5"));       // Hover sáng hơn
        bntAddBill.setPressedColor(Color.decode("#1976D2"));     // Nhấn đậm hơn
        bntAddBill.setFont(new Font("Times New Roman", Font.BOLD, 16));
        bntAddBill.setForeground(Color.WHITE);
        bntAddBill.setBounds(700, 732, 110, 35);

        // Hàm xử lý khi nhấn nút Add Bill
        bntAddBill.addActionListener(e -> {
            try {
                busExportBill = new BUS_ExportBill();

                // 1. Lấy các dòng được chọn (kiểm tra cùng Order.No)
                List<Object[]> orderItems = getMultipleOrderInfo();
                if (orderItems.isEmpty()) {
                    throw new Exception("No order items selected!");
                }

                // 2. Lấy thông tin chung
                String customerID = orderItems.get(0)[1].toString();
                double discount = ((Number) spinnerDiscount.getValue()).doubleValue();
                String imeiNumbers = txtIMEI.getText().trim();
                customer = busExportBill.getCustomerInfoSafe(customerID);

                // 3. Xóa nội dung cũ trước khi tạo hóa đơn mới
                billBody = getBillBody();
                if (billBody != null) {
                    billBody.removeAll();
                }

                // 4. Tạo hóa đơn mới
                createBillContent(orderItems, discount, imeiNumbers, customer);

                // 5. Cập nhật lại giao diện
                billBody.revalidate();
                billBody.repaint();

            } catch (Exception ex) {
                CustomDialog.showError(ex.getMessage());
                ex.printStackTrace(); // In lỗi ra console để debug
            }
        });


        panel.add(bntAddBill);
        
        bntExport = new MyButton("Generate/Save Bill", 20);
        bntExport.setBackgroundColor(Color.decode("#009688")); // Xanh dương chính
        bntExport.setHoverColor(Color.decode("#00695C"));       // Hover sáng hơn
        bntExport.setPressedColor(Color.decode("#00796B")); 
        bntExport.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bntExport.setForeground(Color.WHITE);
        bntExport.setBounds(950, 720, 200, 60);
        bntExport.addActionListener(e -> {
            boolean confirm = CustomDialog.showOptionPane(
                "Confirm Exportation",
                "Are you sure want to export bill ?",
                UIManager.getIcon("OptionPane.questionIcon"),
                Color.decode("#FF6666")
            );

            if (confirm) {
                try {
                    confirmExport();
                     OrderUpdateNotifier.notifyOrderDeleted(customer.getCustomerID(), orderNo);
                    Refresh();
                } catch (Exception ex) {
                    CustomDialog.showError("Export error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
    });
            
        panel.add(bntExport);  
  }
    
    private void loadConfirmedOrderDetailsToTable() {
        busOrderDetail = new BUS_OrderDetail();
        List<DTO_Oderdetails> orderDetailsList = busOrderDetail.getConfirmedOrderDetailsOldestFirst();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (DTO_Oderdetails detail : orderDetailsList) {
            Object[] row = {
                detail.getOrderNo(),
                detail.getCustomerID(),
                detail.getProductID(),
                detail.getPrice(),
                detail.getQuantity(),
                detail.getDateOrder().format(dateFormatter),
                detail.getTimeOrder().toString(),
                detail.getStatus()
            };
            model.addRow(row);
        }
   }

    private void displaySearchResults(List<DTO_Oderdetails> results) {
        model = (DefaultTableModel) tableOrderDetails.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (DTO_Oderdetails detail : results) {
            Object[] rowData = {
                detail.getOrderNo(),
                detail.getCustomerID(),
                detail.getProductID(),
                detail.getPrice(),
                detail.getQuantity(),
                detail.getDateOrder().format(dateFormatter),
                detail.getTimeOrder().toString(),
                detail.getStatus()
            };
            model.addRow(rowData);
        }

        if (model.getRowCount() == 0) {
            return;
        }
     }
    
    public void Refresh(){
       SwingUtilities.invokeLater(() -> {
              loadConfirmedOrderDetailsToTable();
               tableOrderDetails.adjustColumnWidths();         // Căn chỉnh cột
          });     
        cmbSearchOrder.setSelectedIndex(0);
        txtIMEI.setText(null);
        billBody.removeAll();
        billBody.revalidate();
        billBody.repaint();

    }
    
    private void displayIMEIs(List<DTOIMEI> imeiList, int totalQuantity) {
      try {
          StringBuilder sb = new StringBuilder();

          // Giới hạn số lượng IMEI theo totalQuantity
          int displayCount = Math.min(totalQuantity, imeiList.size());

          for (int i = 0; i < displayCount; i++) {
              DTOIMEI imei = imeiList.get(i);
              sb.append(imei.getImeiNo());
              if (i < displayCount - 1) {
                  sb.append(", ");
              }
          }

          // Format IMEI: hiển thị hàng ngang, tự động xuống dòng khi hết chỗ
          txtIMEI.setText(sb.toString());
          txtIMEI.setLineWrap(true);
          txtIMEI.setWrapStyleWord(false);

          // Cảnh báo nếu số lượng IMEI không đủ
          if (imeiList.size() < totalQuantity) {
              CustomDialog.showError("Only " + imeiList.size() + " IMEIs available (required: " + totalQuantity + ")");
          }
      } catch (Exception ex) {
          CustomDialog.showError(ex.getMessage());
          txtIMEI.setText("");
      }
  }


    
    // Hàm tạo separator
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

    // Hàm tạo bảng thông tin
    private JTable createInfoTable(String[] columns, Object[][] data) {
        JTable table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setEnabled(false); // Không cho chỉnh sửa
        return table;
    }

    // Hàm lấy billBody an toàn (phiên bản mới phù hợp với cấu trúc của bạn)
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
    private void createBillContent(List<Object[]> orderItems, double discount, String imeiNumbers, DTOProfile_cus customer) {
        billBody = getBillBody();
        if (billBody == null) {
            CustomDialog.showError("Bill display area not found!");
            return;
        }

        billBody.removeAll();
        billBody.setLayout(new BoxLayout(billBody, BoxLayout.Y_AXIS));
        billBody.setBackground(Color.WHITE);

        // ===== 0. Invoice No =====
        invoiceNo = String.format("%010d", new Random().nextInt(1_000_000_000)) + "-" + customer.getCustomerID();
        lblInvoice = new JLabel("Invoice No: " + invoiceNo, SwingConstants.CENTER);
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

        // ===== 2. Customer Information =====
        JPanel customerPanel = createSectionPanel("CUSTOMER INFORMATION");
        addInfoRow(customerPanel, "Customer ID:", customer.getCustomerID());
        addInfoRow(customerPanel, "Customer Name:", customer.getFullName());
        addInfoRow(customerPanel, "Address:", customer.getAddress());
        addInfoRow(customerPanel, "Contact:", customer.getContact());
        billBody.add(customerPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(10));

        // ===== 3. Order Details =====
        JPanel orderPanel = createSectionPanel("ORDER DETAILS");
        addInfoRow(orderPanel, "Order No:", orderItems.get(0)[0].toString());

        String[] columns = {"No.", "Product ID", "Product Name", "Quantity", "Unit Price", "Discount", "Total Price", "Waranty Period"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        BigDecimal totalNetPay = BigDecimal.ZERO;
        int totalProducts = 0;
        int stt = 1;

        for (Object[] item : orderItems) {
            String productID = item[2].toString();
            BigDecimal unitPrice = (BigDecimal) item[3];
            int quantity = (int) item[4];

            BigDecimal discountAmount = unitPrice.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100));
            BigDecimal totalPrice = unitPrice.subtract(discountAmount).multiply(BigDecimal.valueOf(quantity));

            totalNetPay = totalNetPay.add(totalPrice);
            totalProducts += quantity;

            model.addRow(new Object[]{
                stt++,
                productID,
                busOrderDetail.getProductName(productID),
                quantity,
                String.format("%,d VND", unitPrice.intValue()),
                discount + "%",
                String.format("%,d VND", totalPrice.intValue())
                
                
                
            });
        }

        JTable productTable = new JTable(model);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.setRowHeight(35);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        productTable.setFillsViewportHeight(true);

        // Adjust column widths
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // No.
        columnModel.getColumn(1).setPreferredWidth(100);  // Product ID
        columnModel.getColumn(2).setPreferredWidth(320);  // Product Name
        columnModel.getColumn(3).setPreferredWidth(80);   // Quantity
        columnModel.getColumn(4).setPreferredWidth(160);  // Unit Price
        columnModel.getColumn(5).setPreferredWidth(80);   // Discount
        columnModel.getColumn(6).setPreferredWidth(160);  // Total Price
        columnModel.getColumn(7).setPreferredWidth(200);

        JScrollPane tableScroll = new JScrollPane(productTable);
        tableScroll.setPreferredSize(new Dimension(710, Math.min(orderItems.size() * 30 + 50, 300)));
        orderPanel.add(tableScroll);

        billBody.add(orderPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(15));

        // ===== 4. Order Summary =====
        JPanel summaryPanel = createSectionPanel("ORDER SUMMARY");
        addInfoRow(summaryPanel, "Total Products:", String.valueOf(totalProducts));
        addInfoRow(summaryPanel, "Total Net Pay:", String.format("%,d VND", totalNetPay.intValue()));
        billBody.add(summaryPanel);
        billBody.add(createSeparator());
        billBody.add(Box.createVerticalStrut(15));

        // ===== 5. IMEI Information =====
        if (imeiNumbers != null && !imeiNumbers.trim().isEmpty()) {
            JPanel imeiPanel = createSectionPanel("IMEI INFORMATION");

            JTextArea imeiArea = new JTextArea(imeiNumbers);
            imeiArea.setEditable(false);
            imeiArea.setFont(new Font("Arial", Font.PLAIN, 12));
            imeiArea.setBackground(Color.WHITE);
            imeiArea.setLineWrap(true);
            imeiArea.setWrapStyleWord(false);
            imeiArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JScrollPane imeiScroll = new JScrollPane(imeiArea);
            imeiScroll.setPreferredSize(new Dimension(600, 80));
            imeiScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            imeiPanel.add(imeiScroll);
            billBody.add(imeiPanel);
            billBody.add(createSeparator());
            billBody.add(Box.createVerticalStrut(15));
        }

        billBody.revalidate();
        billBody.repaint();
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

    private List<Object[]> getMultipleOrderInfo() throws Exception {
        int[] selectedRows = tableOrderDetails.getSelectedRows();
        if (selectedRows.length == 0) {
            throw new Exception("Please select at least one order!");
        }

        // Kiểm tra cùng Order.No
        String firstOrderNo = tableOrderDetails.getValueAt(selectedRows[0], 0).toString();
        List<Object[]> orderInfos = new ArrayList<>();

        for (int row : selectedRows) {
            String currentOrderNo = tableOrderDetails.getValueAt(row, 0).toString();
            if (!currentOrderNo.equals(firstOrderNo)) {
                throw new Exception("All selected orders must have the same Order.No!");
            }

            orderInfos.add(new Object[]{
                currentOrderNo,
                tableOrderDetails.getValueAt(row, 1).toString(),
                tableOrderDetails.getValueAt(row, 2).toString(),
                new BigDecimal(tableOrderDetails.getValueAt(row, 3).toString()),
                Integer.parseInt(tableOrderDetails.getValueAt(row, 4).toString())
            });
        }

        return orderInfos;
    }

        // Hàm thêm phần IMEI vào hóa đơn
    private void addImeiSectionToBill(String imeiNumbers) {
        JPanel billBody = getBillBody();
        if (billBody != null) {
            // Tạo panel IMEI
            JPanel imeiPanel = createSectionPanel("IMEI INFORMATION");
            JTextArea imeiArea = new JTextArea(imeiNumbers);
            imeiArea.setEditable(false);
            imeiArea.setFont(new Font("Arial", Font.PLAIN, 12));
            imeiArea.setBackground(Color.WHITE);
            imeiPanel.add(new JScrollPane(imeiArea));

            // Lấy tất cả components hiện có
            Component[] components = billBody.getComponents();
            billBody.removeAll();

            // Thêm lại tất cả components trừ summaryPanel (thường là component cuối)
            for (int i = 0; i < components.length - 1; i++) {
                billBody.add(components[i]);
            }

            // Thêm IMEI panel và separator
            billBody.add(imeiPanel);
            billBody.add(createSeparator());
            billBody.add(Box.createVerticalStrut(5));

            // Thêm lại summaryPanel (component cuối)
            billBody.add(components[components.length - 1]);

            billBody.revalidate();
            billBody.repaint();
        }
 }
  
    private void confirmExport() throws Exception {
        // Lấy danh sách sản phẩm và IMEI
        List<Object[]> orderItems = getMultipleOrderInfo();
        String imeiList = txtIMEI.getText().strip();
        List<String> imeis = !imeiList.isEmpty() ? 
            Arrays.asList(imeiList.split(",")) : 
            Collections.emptyList();

        if (orderItems.isEmpty()) {
            throw new Exception("No products to export!");
        }

        // Tạo và xuất PDF
        PDFExporter exporter = new PDFExporter(
            panelBill, txtAdminID.getText(), txtAdminName.getText(),
            customer, busOrderDetail, orderItems,
            ((Number) spinnerDiscount.getValue()).doubleValue(),
            imeiList
        );
        exporter.exportToPDF();

        // Xử lý dữ liệu
        processExportData(orderItems, imeis);
    }

    private void processExportData(List<Object[]> orderItems, List<String> imeis) throws Exception {
        // 1. Insert Bill Exported
        DTO_BillExported bill = new DTO_BillExported();
        bill.setInvoiceNo(invoiceNo);
        bill.setAdminId(txtAdminID.getText());
        bill.setCustomerId(customer.getCustomerID());
        bill.setTotalProduct(orderItems.size());

        if (!busExportBill.insertBillExported(bill)) {
            throw new Exception("Failed to insert exported bill!");
        }

        // Get the original discount value once
        BigDecimal discountPercent = BigDecimal.valueOf(((Number) spinnerDiscount.getValue()).doubleValue());

        // Track current position in IMEI list
        int imeiIndex = 0;

        for (Object[] item : orderItems) {
            String productID = item[2].toString();
            int quantity = ((Number) item[4]).intValue();
            BigDecimal unitPrice = (BigDecimal) item[3];

            // Get IMEIs for current product
            List<String> productImeis = new ArrayList<>();
            for (int i = 0; i < quantity && imeiIndex < imeis.size(); i++) {
                productImeis.add(imeis.get(imeiIndex++));
            }

            // Process each IMEI for this product
            for (String imei : productImeis) {
                processSingleImeiItem(
                    item,
                    imei,
                    discountPercent,
                    unitPrice,
                    productID
                );
            }
        }

        cleanupAfterExport(imeis, orderItems.get(0)[0].toString());
    }

    private void processSingleImeiItem(Object[] item, String imei, BigDecimal discountPercent, 
                                     BigDecimal unitPrice, String productID) throws Exception {
        // Calculate discount for single item
        BigDecimal discountAmount = unitPrice.multiply(discountPercent)
                                           .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal priceAfterDiscount = unitPrice.subtract(discountAmount);

        // Create export detail
        DTO_BillExportedDetail detail = createExportDetail(
            productID, 
            imei, 
            unitPrice, 
            1, // Quantity per IMEI is always 1
            discountPercent, // Original discount percentage
            unitPrice,       // Price before discount for single item
            priceAfterDiscount
        );

        if (!busExportBill.insertBillDetail(detail, Collections.singletonList(imei)) || 
            !busExportBill.updateProductQuantity(detail)) {
            throw new Exception("Không thể xử lý IMEI: " + imei);
        }
    }

      private boolean requiresImei(String productId) {
          return productId.startsWith("IMEI_REQUIRED_"); // Giả định: sản phẩm cần IMEI có ID bắt đầu bằng "IMEI_REQUIRED_"
      }


        private DTO_BillExportedDetail createExportDetail(
            String productID, String imei, BigDecimal unitPrice, int quantity,
            BigDecimal discountValue, BigDecimal totalBefore, BigDecimal totalAfter
        ) {
            return new DTO_BillExportedDetail(
                invoiceNo,
                txtAdminID.getText(),
                customer.getCustomerID(),
                productID,
                imei,
                unitPrice,
                quantity,
                discountValue,
                totalBefore,
                totalAfter,
                new java.sql.Date(System.currentTimeMillis()),
                new java.sql.Time(System.currentTimeMillis())
            );
        }

    private void cleanupAfterExport(List<String> imeis, String orderNo) {
        // Xóa IMEI
        imeis.forEach(imei -> busOrderDetail.deleteIMEI(imei.trim()));

        // Xóa order
        busOrderDetail.deleteOrder(orderNo);
    }
    

}
