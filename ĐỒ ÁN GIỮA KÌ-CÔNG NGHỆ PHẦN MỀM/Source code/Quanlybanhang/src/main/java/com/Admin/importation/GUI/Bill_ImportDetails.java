
package com.Admin.importation.GUI;


import com.Admin.importation.BUS.BUSImport;
import com.Admin.importation.DTO.DTOBill_Imported;
import com.Admin.importation.DTO.DTOBill_ImportedFullDetails;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Bill_ImportDetails extends javax.swing.JFrame {
     private JPanel panelSearch;
     private JLabel lblTitle;
     private MyPanel panelTitle;
     private MyCombobox cmbSearch;
     private MyButton bntSearch, bntRefresh;
     private MyTextField txtSearch;
     private MyTable tableBillDetail;
     private BUSImport busImport;
  
     
     private DefaultTableModel model;
    public Bill_ImportDetails() {
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

     lblTitle = new JLabel("Bill Importation Details", JLabel.CENTER);
     lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
     lblTitle.setForeground(Color.WHITE);

     panelTitle.add(lblTitle, "grow, push, align center");
     bg.add(panelTitle, "growx, h 40!, wrap"); // wrap để component sau xuống dòng
     
           // 1️⃣ Tên cột
        String[] columnNames = {
            "Invoice.No", "Admin.ID", "Admin Name", "Product.ID", 
            "Product Name", "Category ID", "Brand ID" , "Quantity", "Unit Price",
            "Total Price", "Date Imported", "Time Imported"
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
            tableBillDetail.adjustColumnWidths();
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
            String[] items = {"Invoice.No", "Admin.ID", "Product.ID", "Date"};
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
            bntSearch.addActionListener(e -> {
                String searchType = (String) cmbSearch.getSelectedItem();
                String keyword = txtSearch.getText().trim();


                try {
                    // Chuẩn hóa từ khóa nếu tìm kiếm theo ngày
                    String searchKeyword = searchType.equals("Date") ? normalizeDateString(keyword) : keyword;

                    List<DTOBill_ImportedFullDetails> searchResults = busImport.searchBillDetails(searchType, searchKeyword);

                    // Xóa dữ liệu cũ và hiển thị kết quả mới
                    model.setRowCount(0);
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

                    for (DTOBill_ImportedFullDetails detail : searchResults) {
                        model.addRow(new Object[]{
                            detail.getInvoiceNo(),
                            detail.getAdminId(),
                            detail.getAdminName(),
                            detail.getProductId(),
                            detail.getProductName(),
                            detail.getCategoryId(),
                            detail.getBrandId(),
                            detail.getQuantity(),
                            detail.getUnitPrice(),
                            detail.getTotalPrice(),
                            detail.getDateImported().format(dateFormatter),
                            detail.getTimeImported().format(timeFormatter)
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    CustomDialog.showError("Error searching...: " + ex.getMessage());
                }
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
            try {
                // Xóa dữ liệu cũ trong model
                model.setRowCount(0);

                // Lấy dữ liệu từ BUS
                busImport = new BUSImport();
                List<DTOBill_ImportedFullDetails> billDetails = busImport.getAllBillFullDetails();

              DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
              DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

                // Thêm dữ liệu vào table
                for (DTOBill_ImportedFullDetails detail : billDetails) {
                    model.addRow(new Object[]{
                        detail.getInvoiceNo(),
                        detail.getAdminId(),
                        detail.getAdminName(),
                        detail.getProductId(),
                        detail.getProductName(),
                        detail.getCategoryId(),
                        detail.getBrandId(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getTotalPrice(),
                        detail.getDateImported().format(dateFormatter),  // Định dạng LocalDate
                        detail.getTimeImported().format(timeFormatter)   // Định dạng LocalTime
                    });
                }

                // Căn chỉnh cột
                SwingUtilities.invokeLater(() -> {
                    tableBillDetail.adjustColumnWidths();
                });
            } catch (Exception e) {
                e.printStackTrace();
                CustomDialog.showError("Lỗi khi tải dữ liệu: " + e.getMessage());
            }
        }

   
    
    private void Refresh(){
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(Bill_ImportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bill_ImportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bill_ImportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bill_ImportDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bill_ImportDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    // End of variables declaration                   
}
