
package com.Admin.insurance.GUI;

import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.insurance.BUS.BUS_Warranty;
import com.Admin.insurance.DTO.DTO_InsuranceDetails;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;  // Cho DateTimeFormatter
import java.util.Locale;                   // Cho Locale
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

public class WarrantyDetails extends javax.swing.JFrame {
        private JLabel lblTitle;
        private MyPanel panelTitle;
        private MyCombobox cmbSearch;
        private MyButton bntSearch, bntRefresh;
        private MyTextField txtSearch;
        private MyTable tableBillDetail;
        private DefaultTableModel model;
        private BUS_Warranty busWarranty;
    
    public WarrantyDetails() {
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

        lblTitle = new JLabel("Warranty Invoice Details", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);

        panelTitle.add(lblTitle, "grow, push, align center");
        bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng
      
        
           // 1️⃣ Tên cột
        String[] columnNames = {
            "Invoice.No", "Admin.ID", "Customer.ID", "Product.ID", 
            "IMei.No", "Description", "Date" , "Time"
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

        JScrollPane scrollPane = MyTable.createScrollPane(tableBillDetail, 10, 150, 1050, 560);

        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));
         SwingUtilities.invokeLater(() -> {
             loadBillDetailsData();
            tableBillDetail.adjustColumnWidths();
           
        });
        
        bg.add(scrollPane, "pos 10 140, w 1055!, h 560!");
    
        // Tạo txtSearch
        txtSearch = new MyTextField();
        txtSearch.setHint("Enter the search key word...");
        txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
        txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
        txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
        bg.add(txtSearch, "pos 430 75, w 300!, h 35!");
        
         // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
        String[] items = {"Invoice.No", "Admin.ID", "Customer.ID", "IMEI.No", "Date"};
        cmbSearch = new MyCombobox<>(items);
        cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            //cmbSearch.setMaximumRowCount(5); // Giới hạn dòng dropdown nếu dài
        cmbSearch.repaint();
        cmbSearch.revalidate();
        bg.add(cmbSearch, "pos 290 75, w 130!, h 35!");
    
        bntSearch= new MyButton("Search ", 20);
        bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
        bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
        bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
        bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
        bntSearch.setForeground(Color.WHITE);
        bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
        bntSearch.addActionListener(e -> {
            try {
                busWarranty = new BUS_Warranty();
                String searchType = (String) cmbSearch.getSelectedItem();
                String keyword = txtSearch.getText().trim();

                // Nếu tìm kiếm theo ngày, chuẩn hóa định dạng cho SQL
                if ("Date".equals(searchType)) {
                    keyword = normalizeDateString(keyword); // Chuẩn hóa ngày về `yyyy-MM-dd`
                }

                List<DTO_InsuranceDetails> results = busWarranty.searchInsuranceDetails(searchType, keyword);

                if (results == null || results.isEmpty()) {
                    CustomDialog.showError("No warranty records found for the given search criteria!");
                    return;
                }

                updateTableWithSearchResults(results);

            } catch (Exception ex) {
                ex.printStackTrace();
                CustomDialog.showError("An error occurred while searching: " + ex.getMessage());
            }
        });

        
        
        bg.add(bntSearch, "pos 740 75, w 120!, h 35");
   
    }
    public void loadBillDetailsData() {
        // Xóa dữ liệu cũ trong model
        model.setRowCount(0);

        // Lấy dữ liệu từ BUS
        busWarranty = new BUS_Warranty();
        List<DTO_InsuranceDetails> billDetails = busWarranty.getAllInsuranceDetails();

        // Định dạng cho ngày và giờ
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        // Thêm dữ liệu mới từ danh sách DTO
        for (DTO_InsuranceDetails detail : billDetails) {
            try {
                // Kiểm tra dữ liệu trước khi thêm vào bảng
                String formattedDate = detail.getDateInsurance() != null ? 
                    detail.getDateInsurance().format(dateFormatter) : "N/A";

                String formattedTime = detail.getTimeInsurance() != null ? 
                    detail.getTimeInsurance().format(timeFormatter) : "N/A";

                Object[] rowData = new Object[]{
                    detail.getInsuranceNo(),  // Thêm Invoice.No nếu cần
                    detail.getAdminId(),
                    detail.getCustomerId(),
                    detail.getProductId(),
                    detail.getiMeiNo(),
                    detail.getDescription(),
                    formattedDate,
                    formattedTime
                };
                model.addRow(rowData);
            } catch (Exception e) {
                System.err.println("Error processing row: " + e.getMessage());
                // Thêm hàng với thông tin lỗi nếu cần
                Object[] errorRow = new Object[]{
                    "Error", "Error", "Error", "Error", 
                    "Error", "Error", "Error", "Error"
                };
                model.addRow(errorRow);
            }
        }

        // Căn chỉnh cột sau khi load dữ liệu
        SwingUtilities.invokeLater(() -> {
            tableBillDetail.adjustColumnWidths();
            // In ra console để debug
            System.out.println("Columns in model: " + model.getColumnCount());
            System.out.println("Columns in table: " + tableBillDetail.getColumnCount());
        });
    }
    
    private void updateTableWithSearchResults(List<DTO_InsuranceDetails> results) {
        // Xóa dữ liệu cũ và hiển thị kết quả mới
        model.setRowCount(0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        for (DTO_InsuranceDetails detail : results) {
            // Kiểm tra tránh lỗi nếu `dateInsurance` hoặc `timeInsurance` bị null
            String formattedDate = (detail.getDateInsurance() != null) ? detail.getDateInsurance().format(dateFormatter) : "N/A";
            String formattedTime = (detail.getTimeInsurance() != null) ? detail.getTimeInsurance().format(timeFormatter) : "N/A";

            model.addRow(new Object[]{
                detail.getInsuranceNo(),
                detail.getAdminId(),
                detail.getCustomerId(),
                detail.getProductId(),
                detail.getiMeiNo(),
                detail.getDescription(),
                formattedDate,
                formattedTime
            });
        }

        tableBillDetail.adjustColumnWidths();
    }

    private String normalizeDateString(String dateString) {
        try {
            // Nhận dạng các định dạng đầu vào có thể có
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date;

            if (dateString.contains("/")) {
                date = inputFormat1.parse(dateString);
            } else if (dateString.contains("-")) {
                date = inputFormat2.parse(dateString);
            } else {
                return dateString; // Trả về nguyên bản nếu không phù hợp
            }

            return sqlFormat.format(date); // Chuyển đổi thành `yyyy-MM-dd` cho SQL Server
        } catch (ParseException e) {
            System.err.println("Error converting date format: " + e.getMessage());
            return dateString; // Trả về nguyên bản nếu không chuyển đổi được
        }
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
            .addGap(0, 1085, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
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
            java.util.logging.Logger.getLogger(WarrantyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WarrantyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WarrantyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WarrantyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WarrantyDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
