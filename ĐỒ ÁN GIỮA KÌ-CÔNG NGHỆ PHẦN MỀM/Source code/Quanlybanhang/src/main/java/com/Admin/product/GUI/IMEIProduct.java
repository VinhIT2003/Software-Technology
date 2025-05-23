
package com.Admin.product.GUI;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.product.BUS.BusIMEI;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import com.Admin.category.DTO.DTOCategory;
import com.Admin.product.BUS.BusProduct;
import com.Admin.product.DTO.DTOProduct;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import net.miginfocom.swing.MigLayout;  // Layout manager chính
import net.miginfocom.layout.*;         // (Tùy chọn) Nếu cần các constraint nâng cao
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import java.awt.Cursor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class IMEIProduct extends javax.swing.JFrame {
     private int mouseX, mouseY;
    private JLabel lblTitle, lblState; 
//            
     private MyPanel panelTitle, panelSearch;
     private MyTextField txtSearch, txtProductName, txtCPU, txtRam, txtCard, txtPrice, txtwaranty;
     private MyCombobox cmbSearch, cmbState;
     private MyButton bntSearch, bntClean, bntImportfile, bnteExportfile, bntRefresh, bntUpdate;
     private MyTable tableIMEI;
     private BusIMEI busIMEI;
//     private JSpinner spinnerQuantity, spinderBrokenQuantity;
//     private JMenu menu;
//     private String image;
//      private BusProduct busProduct;

    public IMEIProduct() {
       
        initComponents();
       setSize(880, 750);
        setLocationRelativeTo(null);    
       setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
            // Thêm đoạn này vào đây:
      bg.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mousePressed(java.awt.event.MouseEvent evt) {
              mouseX = evt.getX();
              mouseY = evt.getY();
          }
      });

    bg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseDragged(java.awt.event.MouseEvent evt) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
        }
    });
        init();
    }
     public void init() {
        // Thiết lập MigLayout cho panel chính với khoảng cách nhỏ giữa các hàng
        bg.setLayout(new MigLayout("insets 0, fill", "[grow]", "[40!][pref!][grow]")); // thêm [pref!] kiểm soát panelSearch

        // 1. Panel tiêu đề
        panelTitle = new MyPanel(new MigLayout("fill, insets 0"));
        panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

        lblTitle = new JLabel("IMEI Details", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        panelTitle.add(lblTitle, "grow, push, align center");

        // Thêm panelTitle vào bg với khoảng cách dưới nhỏ
        bg.add(panelTitle, "growx, h 40!, wrap, gapbottom 2");

        // 2. Panel Search
        panelSearch = new MyPanel(Color.WHITE);
        panelSearch.setLayout(new MigLayout("insets 10", "[]10[]10[]", "[]"));
        panelSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Combobox tìm kiếm
        String[] items = {"IMEI.No", "Product.ID"};
        cmbSearch = new MyCombobox<>(items);
        cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
        cmbSearch.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
        panelSearch.add(cmbSearch, "w 130!, h 35!");

        // Text search
        txtSearch = new MyTextField();
        txtSearch.setHint("Enter the search key word...");
        txtSearch.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
        txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
        txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panelSearch.add(txtSearch, "w 250!, h 35!");

        // Button Search
        bntSearch = new MyButton("Search", 20);
        bntSearch.setBackgroundColor(Color.decode("#00CC33"));
        bntSearch.setPressedColor(Color.decode("#33CC33"));
        bntSearch.setHoverColor(Color.decode("#00EE00"));
        bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
        bntSearch.setForeground(Color.WHITE);
        bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntSearch.addActionListener(e -> {
            String selectedColumn = cmbSearch.getSelectedItem().toString();
            String keyword = txtSearch.getText();
            String state = cmbState.getSelectedItem() != null ? cmbState.getSelectedItem().toString() : "";

            busIMEI =new BusIMEI();
            busIMEI.Search(tableIMEI, selectedColumn, keyword, state);
        });

        panelSearch.add(bntSearch, "w 120!, h 35!");

        // Thêm panelSearch sát lên trên
        bg.add(panelSearch, "align center, wrap, gaptop 0");


        bnteExportfile= new MyButton("Export", 20);
        bnteExportfile.setBackgroundColor(Color.WHITE); // Màu nền
        bnteExportfile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
        bnteExportfile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
        bnteExportfile.setFont(new Font("sansserif", Font.BOLD, 16));
        bnteExportfile.setForeground(Color.BLACK);
        bnteExportfile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
                // Gắn sự kiện cho nút Refresh
        bnteExportfile.addActionListener((e) -> {
            busIMEI= new BusIMEI();
            busIMEI.exportFile(tableIMEI);
        });

        bg.add(bnteExportfile, "pos 350 150, w 120!, h 30!");

                    // 1️⃣ Tên cột
                 String[] columnNames = {
                     "IMEI.No", "Product.ID", "Product Name", "Category.ID", "Brand.ID", "State"

                 };

                 // 2️⃣ Tạo model
                 DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                 // 3️⃣ Font hiển thị
                 Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
                 Font headerFont = new Font("SansSerif", Font.BOLD, 16);

                 // 4️⃣ Tạo bảng sử dụng MyTable
                 tableIMEI = new MyTable(
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

                 // 5️⃣ ScrollPane chứa bảng
                 JScrollPane scrollPane = MyTable.createScrollPane(tableIMEI, 0, 0, 0, 0);

                 // 6️⃣ Tùy chỉnh thanh cuộn
                 scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
                 scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

                 // 7️⃣ Thêm scrollPane vào panel
                 bg.add(scrollPane, "pos 80 220, w 720!, h 480!");
                 
                 
         SwingUtilities.invokeLater(() -> {
               busIMEI = new BusIMEI(); // Có thể khai báo sẵn ở đầu lớp GUI
               busIMEI.uploadtoTable(tableIMEI);
               tableIMEI.adjustColumnWidths();           // Căn chỉnh cột
          });
          
                // Bắt sự kiện chọn dòng trong bảng để set giá trị vào combobox State
       tableIMEI.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int selectedRow = tableIMEI.getSelectedRow();
               if (selectedRow != -1) {
                   // Lấy giá trị từ cột "State" (giả sử cột thứ 5, tức index 5)
                   String stateValue = tableIMEI.getValueAt(selectedRow, 5).toString();

                   // Đưa giá trị lên combobox
                   cmbState.setSelectedItem(stateValue);
               }
           }
       });

       
          bntRefresh = new MyButton("Refresh", 20);
          bntRefresh.setBackgroundColor(Color.WHITE);
          bntRefresh.setPressedColor(Color.decode("#D3D3D3"));
          bntRefresh.setHoverColor(Color.decode("#EEEEEE"));
          bntRefresh.setFont(new Font("sansserif", Font.PLAIN, 16));
          bntRefresh.setForeground(Color.BLACK);
          bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 
                                25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          
          bntRefresh.addActionListener((e) -> {
              busIMEI= new BusIMEI();
              busIMEI.Refresh(tableIMEI);
              cmbState.setSelectedIndex(0);
              cmbSearch.setSelectedIndex(0);
          });
          bg.add(bntRefresh, "pos 50 150, w 130!, h 30!");
          
        bntImportfile = new MyButton("Import", 20);
        bntImportfile.setBackgroundColor(Color.WHITE); // Màu nền
        bntImportfile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
        bntImportfile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
        bntImportfile.setFont(new Font("sansserif", Font.BOLD, 16));
        bntImportfile.setForeground(Color.BLACK);
        bntImportfile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
                // Gắn sự kiện cho nút Refresh

        bntImportfile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose an Excel file to import");

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                busIMEI = new BusIMEI();
                busIMEI.importExcelIMEI(selectedFile);                      
         SwingUtilities.invokeLater(() -> {
            // Có thể khai báo sẵn ở đầu lớp GUI
               busIMEI.uploadtoTable(tableIMEI);
               tableIMEI.adjustColumnWidths();           // Căn chỉnh cột
          });
            }
        });
        bg.add(bntImportfile, "pos 200 150, w 120!, h 30!");
       
        lblState= new JLabel("State");
        lblState.setFont(new Font("Arial", Font.PLAIN, 16));
        lblState.setForeground(Color.BLACK);
        bg.add(lblState,"pos 550 150, w 80!, h 30!");
        
        
        // Combobox tìm kiếm
        String[] items_state = {"Useable", "Damaged"};
        cmbState = new MyCombobox<>(items_state);
        cmbState.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
        cmbState.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
        bg.add(cmbState, "pos 610 150, w 120!, h 30!");
        
       
        bntUpdate = new MyButton("Update", 20);
        bntUpdate.setBackgroundColor(Color.decode("#2196F3")); // Màu chính
        bntUpdate.setPressedColor(Color.decode("#0D47A1"));    // Khi nhấn (đậm hơn)
        bntUpdate.setHoverColor(Color.decode("#42A5F5"));      // Khi rê chuột (nhạt hơn chút)
        bntUpdate.setFont(new Font("Times New Roman", Font.BOLD, 16));
        bntUpdate.setForeground(Color.WHITE); 
      
      bntUpdate.addActionListener(e -> {
            int selectedRow = tableIMEI.getSelectedRow();

            if (selectedRow == -1) {
               CustomDialog.showError("Please select a IMEI to update !");
                return;
            }

            String imeiNo = tableIMEI.getValueAt(selectedRow, 0).toString();  // Assuming column 0 is IMei_No
            String newState = cmbState.getSelectedItem().toString();
            busIMEI= new BusIMEI();
            busIMEI.updateState(imeiNo, newState); // Call method in BUS
            // Reload table (depending on how you load data)
            busIMEI.uploadtoTable(tableIMEI);
            tableIMEI.adjustColumnWidths();

            CustomDialog.showSuccess("State updated successfully!");
        });
        
        bg.add(bntUpdate,  "pos 750 145, w 100!, h 35!");
        
         
        bntClean = new MyButton("Clean", 20);
        bntClean.setBackgroundColor(Color.WHITE);
        bntClean.setPressedColor(Color.decode("#D3D3D3"));
        bntClean.setHoverColor(Color.decode("#EEEEEE"));
        bntClean.setFont(new Font("sansserif", Font.PLAIN, 16));
        bntClean.setForeground(Color.BLACK);
        bntClean.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\clean.png", 
                                25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntClean.addActionListener(e -> {
            boolean confirm = CustomDialog.showOptionPane(
                        "Confirm Cleaning",
                        "Are you sure you want to clean all data?",
                        UIManager.getIcon("OptionPane.questionIcon"),
                        Color.decode("#FF6666")
       );

            if (confirm) {
                busIMEI.cleanIMEIData();
                busIMEI.uploadtoTable(tableIMEI); // Refresh lại bảng
                tableIMEI.adjustColumnWidths();
            }
        });

            bg.add(bntClean, "pos 730  60, w 130!, h 30!");

                // Xử lý resize font tiêu đề
                panelTitle.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        float fontSize = Math.min(20f, Math.max(12f, panelTitle.getWidth() / 20f));
                        lblTitle.setFont(lblTitle.getFont().deriveFont(fontSize));
                    }
                });
            }

    
   
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        bg = new javax.swing.JLayeredPane();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1414, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
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
            java.util.logging.Logger.getLogger(IMEIProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IMEIProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IMEIProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IMEIProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IMEIProduct().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.JLayeredPane bg;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration                   
}
