
package com.Admin.stock.GUI;

import com.Admin.product.BUS.BusProduct;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import com.Admin.stock.BUS.BUSProduct_Stock;
import com.Admin.stock.DTO.DTOProduct_Stock;
import com.ComponentandDatabase.Components.CustomDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;

import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class Form_Stock extends JPanel {
    private JPanel panel, panelSearch;
    private JLabel lblTitle;
    private MyButton bntSearch, bntRefresh, bntImportFile, bntExportFile, bntDetails, bntClean;
    private MyCombobox<String> cmbSearchProduct;;
    private MyTextField txtSearch;
     public MyTable tableProduct_stock;
     private BUSProduct_Stock busProduct_stock;
    public Form_Stock() {
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

                 // Tạo panelSearch với màu nền trắng
            panelSearch = new MyPanel(Color.WHITE);
            panelSearch.setLayout(null);
            panelSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            panelSearch.setBounds(750,20, 520, 60);
            
  
            // Tạo txtSearch
            txtSearch = new MyTextField();
            txtSearch.setHint("Enter the search key word...");
            txtSearch.setBounds(160, 10, 230, 35); // Đặt vị trí và kích thước
            txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
            txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
            panelSearch.add(txtSearch); // Thêm vào giao diện
            
            // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
           String[] items = {"Product.ID", "Product Name", "Brand.ID"};
           cmbSearchProduct= new MyCombobox<>(items);
           cmbSearchProduct.setBounds(8, 10, 145,35);
           cmbSearchProduct.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
           cmbSearchProduct.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
           cmbSearchProduct.repaint();
           cmbSearchProduct.revalidate();

                    // 👉 Thêm đoạn invokeLater để đảm bảo cmbSearch được refresh UI
           SwingUtilities.invokeLater(() -> {
               
             cmbSearchProduct.repaint();
             cmbSearchProduct.revalidate();
              //cmbSearch.updateUI(); // 👈 Bắt buộc để refresh lại giao diện
           });

            panelSearch.add(cmbSearchProduct);
            
            bntSearch= new MyButton("Search", 20);
            bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
            bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
            bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
            bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
            bntSearch.setForeground(Color.WHITE);
            bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
            bntSearch.setBounds(txtSearch.getX() + txtSearch.getWidth() + 10, 10, 110, 35);
              bntSearch.addActionListener((e) -> {
            String selectedColumn = cmbSearchProduct.getSelectedItem().toString();
            String keyword = txtSearch.getText().trim();

            DefaultTableModel model = (DefaultTableModel) tableProduct_stock.getModel();
                busProduct_stock = new BUSProduct_Stock();
                busProduct_stock.searchProduct_Stock(keyword, selectedColumn, model);  // Gọi hàm mới dùng void
      });
            panelSearch.add(bntSearch);
            panel.add(panelSearch);
            
          bntRefresh = new MyButton("Refresh", 20);
          bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
          bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntRefresh.setBounds(10, 30, 140, 35); // Tăng chiều rộng để icon không bị che mất
          bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
          bntRefresh.setForeground(Color.BLACK);
          bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          bntRefresh.addActionListener((e) -> {
              refreshTable();
          });
          panel.add(bntRefresh);
          
            
          bntImportFile = new MyButton("Import File", 0);
          bntImportFile.setBackgroundColor(Color.WHITE); // Màu nền
          bntImportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntImportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntImportFile.setBounds(255, 30, 170, 35); // Tăng chiều rộng để icon không bị che mất
          bntImportFile.setFont(new Font("sansserif", Font.BOLD, 16));
          bntImportFile.setForeground(Color.BLACK);
          bntImportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          bntImportFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose an Excel file to import");

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                busProduct_stock = new BUSProduct_Stock();
                busProduct_stock.importFile(selectedFile);
         SwingUtilities.invokeLater(() -> {
            // Có thể khai báo sẵn ở đầu lớp GUI
               busProduct_stock.getAllProductStock(tableProduct_stock);
               tableProduct_stock.adjustColumnWidths();           // Căn chỉnh cột
          });
            }
  });
          
          panel.add(bntImportFile);
          
           bntExportFile = new MyButton("Export File", 0);
           bntExportFile.setBackgroundColor(Color.WHITE); // Màu nền
           bntExportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntExportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntExportFile.setBounds(440, 30, 170, 35); // Tăng chiều rộng để icon không bị che mất
           bntExportFile.setFont(new Font("sansserif", Font.BOLD, 16));
           bntExportFile.setForeground(Color.BLACK);
           bntExportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntExportFile.addActionListener((e) -> {
               busProduct_stock= new BUSProduct_Stock();
               busProduct_stock.exportFile(tableProduct_stock);
           });
           
           panel.add(bntExportFile);
          
           bntDetails = new MyButton("Details", 0);
           bntDetails.setBackgroundColor(Color.WHITE); // Màu nền
           bntDetails.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntDetails.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntDetails.setFont(new Font("sansserif", Font.BOLD, 16));
           bntDetails.setForeground(Color.BLACK);
           bntDetails.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\details.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
           bntDetails.setBounds(620, 30, 120, 35);
             bntDetails.addActionListener(e -> {
          // 1. Kiểm tra dòng được chọn
          int selectedRow = tableProduct_stock.getSelectedRow();
          if (selectedRow == -1) {
              CustomDialog.showError("Please choose the product to see details !");
              return;
          }

          // 2. Lấy Product_ID từ dòng được chọn
          String productID = tableProduct_stock.getValueAt(selectedRow, 0).toString();
          busProduct_stock= new BUSProduct_Stock();
          // 3. Gọi phương thức getProductById() từ DAO/Service
          DTOProduct_Stock product = busProduct_stock.getProductDetailByID(productID);
          if (product == null) {
               CustomDialog.showError("Product information is not found !");
              return;
          }

          // 4. Tạo và thiết lập form Edit
                 ProductDetails details= new ProductDetails();
                 details.showDetail(product);

          // 5. Hiển thị form
          details.setVisible(true);
      });

           panel.add(bntDetails);         
           
        
            // 1️⃣ Tên cột
             String[] columnNames = {
                 "Product.ID", "Product Name", "Price", "Quantity","Category.ID", "Category Name", "Brand.ID", "Brand Name", "Contact"
             };

             // 2️⃣ Tạo model
             DefaultTableModel model = new DefaultTableModel(columnNames, 0);



             // 4️⃣ Font hiển thị
             Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
             Font headerFont = new Font("SansSerif", Font.BOLD, 16);

             // 5️⃣ Tạo bảng sử dụng MyTable
             tableProduct_stock = new MyTable(
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

             // 6️⃣ ScrollPane chứa bảng
             JScrollPane scrollPane = MyTable.createScrollPane(tableProduct_stock,50, 110, 1110, 700);

             // 7️⃣ Tùy chỉnh thanh cuộn
             scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
             scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

             // 8️⃣ Thêm scrollPane vào panel
             panel.add(scrollPane);

             SwingUtilities.invokeLater(() -> {
               busProduct_stock= new BUSProduct_Stock();
               busProduct_stock.getAllProductStock(tableProduct_stock);
               tableProduct_stock.adjustColumnWidths();         // Căn chỉnh cột
          });   
             
              
        bntClean = new MyButton("Clean", 0);
        bntClean.setBackgroundColor(Color.WHITE);
        bntClean.setPressedColor(Color.decode("#D3D3D3"));
        bntClean.setHoverColor(Color.decode("#EEEEEE"));
        bntClean.setFont(new Font("sansserif", Font.PLAIN, 16));
        bntClean.setForeground(Color.BLACK);
        bntClean.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\clean.png", 
                                25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
        bntClean.setBounds(150, 30, 110, 35);
        bntClean.addActionListener(e -> {
            busProduct_stock= new BUSProduct_Stock();
            boolean confirm = CustomDialog.showOptionPane(
                        "Confirm Cleaning",
                        "Are you sure you want to clean all data?",
                        UIManager.getIcon("OptionPane.questionIcon"),
                        Color.decode("#FF6666")
       );

            if (confirm) {
                busProduct_stock.cleanProduct_Stock();
                busProduct_stock.getAllProductStock(tableProduct_stock);
                tableProduct_stock.adjustColumnWidths();
            }
        });

        
        
        panel.add(bntClean);
             
             
   }
    
     // Thêm phương thức refresh
    public void refreshTable() {
        busProduct_stock= new BUSProduct_Stock();
        DefaultTableModel model = (DefaultTableModel) tableProduct_stock.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        busProduct_stock.getAllProductStock(tableProduct_stock);
        tableProduct_stock.adjustColumnWidths();
        cmbSearchProduct.setSelectedIndex(0);
        txtSearch.setText(null);
    }
  
    
}
