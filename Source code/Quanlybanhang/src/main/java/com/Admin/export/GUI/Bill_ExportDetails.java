
package com.Admin.export.GUI;

import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import com.Admin.export.BUS.BUS_ExportBill;
import com.Admin.export.DTO.DTO_BillExportedDetail;
import com.ComponentandDatabase.Components.CustomDialog;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;


public class Bill_ExportDetails extends javax.swing.JFrame {
     private JPanel panelSearch;
     private JLabel lblTitle;
     private MyPanel panelTitle;
     private MyCombobox cmbSearch;
     private MyButton bntSearch, bntRefresh;
     private MyTextField txtSearch;
     private MyTable tableBillDetail;
     private BUS_ExportBill busExportBill;
 
     private DefaultTableModel model;
    public Bill_ExportDetails() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        setAlwaysOnTop(true); // Luôn hiển thị trên cùng
        init();
    }

   public void init() {
     // Thiết lập layout chính
     bg.setLayout(new MigLayout("fillx, insets 0", "[grow]", "[][][grow]"));

     // 1. Panel tiêu đề
     panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
     panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

     lblTitle = new JLabel("Sales Invoice Details", JLabel.CENTER);
     lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
     lblTitle.setForeground(Color.WHITE);

     panelTitle.add(lblTitle, "grow, push, align center");
     bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng
     
           // 1️⃣ Tên cột
        String[] columnNames = {
            "Invoice.No", "Admin.ID", "Customer.ID", "Product.ID", 
            "IMei.No", "Unit Price", "Quantity" , "Discount Values", "Total Price Before",
            "Total Price After", "Date Exported", "Time Exported"
        };

        // 2️⃣ Tạo model
        model = new DefaultTableModel(columnNames, 0);


        // 4️⃣ Định dạng font
        Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font headerFont = new Font("SansSerif", Font.BOLD, 16);

        // 5️⃣ Tạo bảng sử dụng MyTable
        tableBillDetail = new MyTable(
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

        JScrollPane scrollPane = MyTable.createScrollPane(tableBillDetail, 10, 150, 950, 630);

        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));
         SwingUtilities.invokeLater(() -> {
             loadBillDetailsData();
              tableBillDetail.adjustColumnWidths();        // Căn chỉnh cột
          });          
        
        
        bg.add(scrollPane, "pos 10 140, w 1430!, h 630!");
        
       
            // Tạo txtSearch
            txtSearch = new MyTextField();
            txtSearch.setHint("Enter the search key word...");
            txtSearch.setBounds(210, 10, 250, 35); // Đặt vị trí và kích thước
            txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
            txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
            bg.add(txtSearch, "pos 510 75, w 300!, h 35!");
            
            // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
            String[] items = {"Invoice.No", "Admin.ID", "Customer.ID", "IMEI.No", "Date"};
            cmbSearch = new MyCombobox<>(items);
            cmbSearch.setBounds(30, 10, 165,35);
            cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbSearch.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
            //cmbSearch.setMaximumRowCount(5); // Giới hạn dòng dropdown nếu dài
            cmbSearch.repaint();
            cmbSearch.revalidate();

           bg.add(cmbSearch, "pos 350 75, w 140!, h 35!");
            
            bntSearch= new MyButton("Search ", 20);
            bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
            bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
            bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
            bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
            bntSearch.setForeground(Color.WHITE);
            bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
            bntSearch.addActionListener((e) -> {
                String searchType = cmbSearch.getSelectedItem().toString();
                String keyword = txtSearch.getText().trim();
                busExportBill= new BUS_ExportBill();
                busExportBill.searchBillDetails(searchType, keyword);
                // Xử lý đặc biệt nếu tìm kiếm theo ngày
                    if (searchType.equals("Date")) {
                        try {
                            // Chuẩn hóa chuỗi ngày tháng (cho phép nhập 1 hoặc 2 chữ số cho ngày/tháng)
                            keyword = normalizeDateString(keyword);

                            // Chuyển từ dd/MM/yyyy sang yyyy-MM-dd để tìm kiếm trong SQL
                            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            inputFormat.setLenient(false); // Không chấp nhận ngày không hợp lệ

                            SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                            // Parse và format lại ngày
                            Date parsedDate = inputFormat.parse(keyword);
                            keyword = sqlFormat.format(parsedDate);
                        } catch (ParseException ex) {
                            CustomDialog.showError("Invalid date format. Please enter date in dd/MM/yyyy format (e.g. 01/01/2023)");
                            return;
                        }
                }
                 // Lấy kết quả từ BUS
                List<DTO_BillExportedDetail> results = busExportBill.searchBillDetails(searchType, keyword);

                // Cập nhật dữ liệu lên bảng
                updateTableData(results);
        });
        bg.add(bntSearch, "pos 820 75, w 120!, h 35");
        
          bntRefresh = new MyButton("Refresh", 20);
          bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
          bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
          bntRefresh.setForeground(Color.BLACK);
          bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          bntRefresh.addActionListener((e) -> {
              Refresh();
          });
          
          bg.add(bntRefresh, "pos 100 75, w 140!, h 35");
        
     
   }

    public void loadBillDetailsData() {
       // Xóa dữ liệu cũ trong model
       model.setRowCount(0);

       // Lấy dữ liệu từ BUS
       busExportBill = new BUS_ExportBill();
       List<DTO_BillExportedDetail> billDetails = busExportBill.getAllBillDetails();

       // Định dạng cho ngày và giờ
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

       // Định dạng tiền tệ
      

       // Thêm dữ liệu mới từ danh sách DTO
       for (DTO_BillExportedDetail detail : billDetails) {
           Object[] rowData = new Object[]{
               detail.getInvoiceNo(),
               detail.getAdminId(),
               detail.getCustomerId(),
               detail.getProductId(),
               detail.getImeiNo(),
               detail.getUnitPrice(),
               detail.getQuantity(),
               detail.getDiscountValues() + "%",
               detail.getTotalPriceBefore(),
               detail.getTotalPriceAfter(),
               dateFormat.format(detail.getDateExported()),
               timeFormat.format(detail.getTimeExported())
           };
           model.addRow(rowData);
       }
    
    // Căn chỉnh cột sau khi load dữ liệu
    SwingUtilities.invokeLater(() -> {
        tableBillDetail.adjustColumnWidths();
    });
} 
    private void updateTableData(List<DTO_BillExportedDetail> data) {
        
           // 1️⃣ Tên cột
        String[] columnNames = {
            "Invoice.No", "Admin.ID", "Customer.ID", "Product.ID", 
            "IMei.No", "Unit Price", "Quantity" , "Discount Values", "Total Price Before",
            "Total Price After", "Date Exported", "Time Exported"
        };
        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames); // columnNames là mảng String chứa tên cột

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        for (DTO_BillExportedDetail detail : data) {
            Object[] row = new Object[]{
                detail.getInvoiceNo(),
                detail.getAdminId(),
                detail.getCustomerId(),
                detail.getProductId(),
                detail.getImeiNo(),
                detail.getUnitPrice(),
                detail.getQuantity(),
                detail.getDiscountValues()+"%",
                detail.getTotalPriceBefore(),
                detail.getTotalPriceAfter(),
                detail.getDateExported() != null ? dateFormat.format(detail.getDateExported()) : "",
                detail.getTimeExported() != null ? timeFormat.format(detail.getTimeExported()) : ""
            };

            // Kiểm tra số lượng cột trước khi thêm
            if (row.length == model.getColumnCount()) {
                model.addRow(row);
            } else {
                System.err.println("Số lượng cột không khớp: " + row.length + " vs " + model.getColumnCount());
            }
        }

        // Đảm bảo không có ô nào đang edit khi cập nhật
        if (tableBillDetail.getCellEditor() != null) {
            tableBillDetail.getCellEditor().stopCellEditing();
        }

        tableBillDetail.adjustColumnWidths();
    }
    private void Refresh(){
        loadBillDetailsData();
        tableBillDetail.adjustColumnWidths();
        cmbSearch.setSelectedIndex(0);
        txtSearch.setText(null);
    }
    
    private String normalizeDateString(String dateString) {
        // Xóa tất cả ký tự không phải số
        String numbersOnly = dateString.replaceAll("[^0-9]", "");

        // Đảm bảo đủ 8 chữ số (thêm số 0 nếu cần)
        if (numbersOnly.length() == 6) {
            numbersOnly = "0" + numbersOnly; // Thêm 0 vào ngày nếu cần
            numbersOnly = numbersOnly.substring(0, 2) + "0" + numbersOnly.substring(2); // Thêm 0 vào tháng nếu cần
        } else if (numbersOnly.length() == 7) {
            // Xác định xem cần thêm 0 vào ngày hay tháng
            if (dateString.indexOf('/') == 1) { // Ngày có 1 chữ số
                numbersOnly = "0" + numbersOnly;
            } else { // Tháng có 1 chữ số
                numbersOnly = numbersOnly.substring(0, 2) + "0" + numbersOnly.substring(2);
            }
        }

        // Định dạng lại thành dd/MM/yyyy
        if (numbersOnly.length() >= 8) {
            return numbersOnly.substring(0, 2) + "/" + numbersOnly.substring(2, 4) + "/" + numbersOnly.substring(4, 8);
        }

        return dateString; // Trả về nguyên bản nếu không thể chuẩn hóa
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1447, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Bill_ExportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bill_ExportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bill_ExportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bill_ExportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bill_ExportDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
