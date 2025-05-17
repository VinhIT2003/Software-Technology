package com.Admin.product.GUI;

import com.Admin.product.BUS.BusProduct;
import com.Admin.product.DTO.DTOProduct;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyTextField;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;

import java.awt.*;

public class Form_Product extends JPanel  implements ProductUpdateObserver {
    private JPanel panel, panelSearch;
    private MyButton bntSearch, bntNew, bntEdit, bntDelete, bntIMEI, bntRefresh, bntExportFile;
    private MyCombobox<String> cmbSearchProduct;
    public MyTable tableProduct;
    private JLabel lblTitle; 
    private MyTextField txtSearch;
    private BusProduct busProduct;

    public Form_Product() {
        initComponents();
        init();
        ProductUpdateNotifier.getInstance().registerObserver(this);
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
            panelSearch.setBounds(670,20, 600, 60);
            
  
            // Tạo txtSearch
            txtSearch = new MyTextField();
            txtSearch.setHint("Enter the search key word...");
            txtSearch.setBounds(210, 10, 250, 35); // Đặt vị trí và kích thước
            txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
            txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
            panelSearch.add(txtSearch); // Thêm vào giao diện
            
            // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
           String[] items = {"Product.ID", "Product Name", "Brand.ID", "Available", "Unavailable"};
           cmbSearchProduct= new MyCombobox<>(items);
           cmbSearchProduct.setBounds(30, 10, 165,35);
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
            bntSearch.setBounds(txtSearch.getX() + txtSearch.getWidth() + 10, 10, 120, 35);
           bntSearch.addActionListener((e) -> {
            String selectedColumn = cmbSearchProduct.getSelectedItem().toString();
            String keyword = txtSearch.getText().trim();

            DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
            BusProduct busProduct = new BusProduct();
            busProduct.searchProduct(keyword, selectedColumn, model);  // Gọi hàm mới dùng void
      });


            
            panelSearch.add(bntSearch);
            panel.add(panelSearch);
            
           bntNew = new MyButton("New", 0);
           bntNew.setBackgroundColor(Color.WHITE); // Màu nền
           bntNew.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntNew.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntNew.setFont(new Font("sansserif", Font.BOLD, 16));
           bntNew.setForeground(Color.BLACK);
           bntNew.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\new.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
           bntNew.setBounds(150, 20, 100, 35);
          bntNew.addActionListener(e -> {
            NewProduct newProductFrame = new NewProduct();
             newProductFrame.setVisible(true);
           
      });


           panel.add(bntNew);
           
          bntRefresh = new MyButton("Refresh", 20);
          bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
          bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
          bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
          bntRefresh.setBounds(10, 20, 130, 35); // Tăng chiều rộng để icon không bị che mất
          bntRefresh.setFont(new Font("sansserif", Font.BOLD, 16));
          bntRefresh.setForeground(Color.BLACK);
          bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          bntRefresh.addActionListener((e) -> {
              initRefreshButton();
          });
          panel.add(bntRefresh);
          
           bntEdit = new MyButton("Edit", 0);
           bntEdit.setBackgroundColor(Color.WHITE); // Màu nền
           bntEdit.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntEdit.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntEdit.setFont(new Font("sansserif", Font.BOLD, 16));
           bntEdit.setForeground(Color.BLACK);
           bntEdit.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\edit.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
           bntEdit.setBounds(250, 20, 100, 35);

            bntEdit.addActionListener(e -> {
          // 1. Kiểm tra dòng được chọn
          int selectedRow = tableProduct.getSelectedRow();
          if (selectedRow == -1) {
              CustomDialog.showError("Please choose the product to edit");
              return;
          }

          busProduct= new BusProduct();
          // 2. Lấy Product_ID từ dòng được chọn
          String productID = tableProduct.getValueAt(selectedRow, 0).toString();

          // 3. Gọi phương thức getProductById() từ DAO/Service
          DTOProduct product = busProduct.getProductById(productID);
          if (product == null) {
            CustomDialog.showError("Product information is not found !");
              return;
          }

          // 4. Tạo và thiết lập form Edit
          EditProduct editFrame = new EditProduct();

         editFrame.showDetail(product);

          // 5. Hiển thị form
          editFrame.setVisible(true);
      });
            panel.add(bntEdit);
            
            bntDelete = new MyButton("Delete", 0);
            bntDelete.setBackgroundColor(Color.WHITE); // Màu nền
            bntDelete.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
            bntDelete.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
            bntDelete.setBounds(350,20,120, 35); // Tăng chiều rộng để icon không bị che mất
            bntDelete.setFont(new Font("sansserif", Font.BOLD, 16));
            bntDelete.setForeground(Color.BLACK);
            bntDelete.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\delete.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
            // Add click event for Delete button
            bntDelete.addActionListener(e -> {
                // 1. Check selected row
                int selectedRow = tableProduct.getSelectedRow();
                if (selectedRow == -1) {
                    CustomDialog.showError("Please select a product to delete!");
                    return;
                }

                // 2. Get Product_ID from selected row
                String productId = tableProduct.getValueAt(selectedRow, 0).toString();
                String productName = tableProduct.getValueAt(selectedRow, 1).toString();

               boolean confirm= CustomDialog.showOptionPane(
                    "Confirm Deletion",
                    "Are you sure you want to delete: "+productName+" ?",
                    UIManager.getIcon("OptionPane.questionIcon"),
                    Color.decode("#FF6666")
            );

                // 4. If user confirms deletion
                if (confirm) {
                    try {
                        // 5. Call deleteProduct method from BUS layer
                        boolean isDeleted = busProduct.deleteProduct(productId);

                        if (isDeleted) {                  
                          // 6. Refresh table data
                            DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
                            model.setRowCount(0); // Clear old data
                            busProduct.uploadProduct(model); // Reload new data
                            tableProduct.adjustColumnWidths(); // Adjust columns
                        } 
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        CustomDialog.showError("System error while deleting product: " + ex.getMessage());
                    }
                }
            });

            panel.add(bntDelete);
            
           bntIMEI = new MyButton("IMEI", 0);
           bntIMEI.setBackgroundColor(Color.WHITE); // Màu nền
           bntIMEI.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntIMEI.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntIMEI.setBounds(460,20,120, 35); // Tăng chiều rộng để icon không bị che mất
           bntIMEI.setFont(new Font("sansserif", Font.BOLD, 16));
           bntIMEI.setForeground(Color.BLACK);
           bntIMEI.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\imei.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           
           bntIMEI.addActionListener((e) -> {
               IMEIProduct IMEI= new IMEIProduct();
               IMEI.setVisible(true);
           });
           panel.add(bntIMEI);
           
           bntExportFile = new MyButton("",20);
           bntExportFile.setBackgroundColor(Color.WHITE); // Màu nền
           bntExportFile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntExportFile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntExportFile.setBounds(580, 20, 70, 40); // Tăng chiều rộng để icon không bị che mất
           bntExportFile.setFont(new Font("sansserif", Font.BOLD, 18));
           bntExportFile.setForeground(Color.BLACK);
           bntExportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 30, 30, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntExportFile.addActionListener((e) -> {
              JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Excel file");

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();

                    // Thêm phần mở rộng nếu chưa có
                    if (!path.toLowerCase().endsWith(".xlsx")) {
                        path += ".xlsx";
        }
               busProduct= new BusProduct();
               busProduct.exportFile(path);
            }
         });
           panel.add(bntExportFile);

                    // 1️⃣ Tên cột
         String[] columnNames = {
             "Product.ID", "Product Name", "Price", "Quantity", "Status", "Broken Quantity",
             "Category.ID", "Category Name", "Brand.ID", "Brand Name", "Contact"
         };

         // 2️⃣ Tạo model
         DefaultTableModel model = new DefaultTableModel(columnNames, 0);

         

         // 4️⃣ Font hiển thị
         Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
         Font headerFont = new Font("SansSerif", Font.BOLD, 16);

         // 5️⃣ Tạo bảng sử dụng MyTable
         tableProduct = new MyTable(
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
         JScrollPane scrollPane = MyTable.createScrollPane(tableProduct, 5, 110, 1270, 700);

         // 7️⃣ Tùy chỉnh thanh cuộn
         scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
         scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

         // 8️⃣ Thêm scrollPane vào panel
         panel.add(scrollPane);

         SwingUtilities.invokeLater(() -> {
               busProduct = new BusProduct(); // Có thể khai báo sẵn ở đầu lớp GUI
               busProduct.uploadProduct(model);
              tableProduct.adjustColumnWidths();           // Căn chỉnh cột
          });
             
   }
    
    // Thêm phương thức refresh
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        busProduct.uploadProduct(model);
        tableProduct.adjustColumnWidths();
    }
    
    @Override
    public void onProductUpdated() {
        SwingUtilities.invokeLater(() -> {
            refreshTable();
        });
    }
    
    // Thêm nút Refresh (nếu chưa có)
    private void initRefreshButton() {
        bntRefresh.addActionListener(e -> refreshTable());
    }
    
    // Khi form đóng (nếu là JFrame)
    @Override
    public void removeNotify() {
        super.removeNotify();
        ProductUpdateNotifier.getInstance().unregisterObserver(this);
    }
}
