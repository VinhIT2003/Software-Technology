package com.Admin.order.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.CustomDialog;
import com.Admin.order.DTO.DTO_order;
import com.Admin.order.BUS.BUS_order;
import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;
import java.time.format.DateTimeFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Form_Order extends JPanel {
    private JPanel panel, panelSearch;;
    private JLabel lblStatus; 
    private MyButton bntSearch, bntExportFile, bntDetails, bntRefresh, bntUpdate;
    private MyTextField txtSearch;
    private MyCombobox<String> cmbSearch, cmbStatus;
    private MyTable tableOrder;
    private DefaultTableModel model;
    private BUS_order busOrder;
    public static String orderNo, customerID;
    
    public Form_Order() {
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
           String[] items = {"Order.No", "Customer.ID", "Date Order"};
           cmbSearch= new MyCombobox<>(items);
           cmbSearch.setBounds(12, 10, 135,35);
           cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
           cmbSearch.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
           cmbSearch.repaint();
           cmbSearch.revalidate();

                    // 👉 Thêm đoạn invokeLater để đảm bảo cmbSearch được refresh UI
           SwingUtilities.invokeLater(() -> {
               
             cmbSearch.repaint();
             cmbSearch.revalidate();
              //cmbSearch.updateUI(); // 👈 Bắt buộc để refresh lại giao diện
           });

            panelSearch.add(cmbSearch);
            
            bntSearch= new MyButton("Search", 20);
            bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
            bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
            bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
            bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
            bntSearch.setForeground(Color.WHITE);
            bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
            bntSearch.setBounds(txtSearch.getX() + txtSearch.getWidth() + 10, 10, 110, 35);
            bntSearch.addActionListener(e -> {
                String searchType = (String) cmbSearch.getSelectedItem();
                String keyword = txtSearch.getText().trim();
                String status = (String) cmbStatus.getSelectedItem();

                List<DTO_order> searchResults = busOrder.searchOrders(searchType, keyword, status);
                loadOrderDataToTable(searchResults);

                if (searchResults.isEmpty()) {
                    CustomDialog.showError("No orders found with the given criteria");
                }
            });
            panelSearch.add(bntSearch);
            panel.add(panelSearch);
          
           
           
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
           bntExportFile.setBounds(150, 40, 170, 35); // Tăng chiều rộng để icon không bị che mất
           bntExportFile.setFont(new Font("sansserif", Font.BOLD, 16));
           bntExportFile.setForeground(Color.BLACK);
           bntExportFile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 40, 40, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntExportFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
            fileChooser.setSelectedFile(new File("Order_Details_Export.xlsx"));

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();

                // Đảm bảo có đuôi .xlsx
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                if (busOrder.exportOrderDetailsToExcel(filePath)) {
                    CustomDialog.showSuccess("File exported successfully !");
                } else {
                    CustomDialog.showError("Export failed!");
                }
            }
        });
           
           panel.add(bntExportFile);
          
           
           bntDetails = new MyButton("Details", 0);
           bntDetails.setBackgroundColor(Color.WHITE); // Màu nền
           bntDetails.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntDetails.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntDetails.setFont(new Font("sansserif", Font.BOLD, 16));
           bntDetails.setForeground(Color.BLACK);
           bntDetails.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\details.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
           bntDetails.setBounds(320, 40, 120, 35);
            bntDetails.addActionListener((e) -> {
                showOrderDetails();
            });
           
           panel.add(bntDetails);
          
           String[] items_status = {"Waiting", "Confirmed"};
            cmbStatus = new MyCombobox<>(items_status);
            cmbStatus.setBounds(460, 40, 130, 35);
            cmbStatus.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbStatus.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
            panel.add(cmbStatus);
            
            lblStatus= new JLabel("Status");
            lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
            lblStatus.setForeground(Color.BLACK);
            lblStatus.setBounds(490, 5, 100, 35);
            panel.add(lblStatus);
            
            
           bntUpdate = new MyButton("Update", 20);
           bntUpdate.setBackgroundColor(Color.WHITE); // Màu nền
           bntUpdate.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntUpdate.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntUpdate.setBounds(600, 40, 140, 35); // Tăng chiều rộng để icon không bị che mất
           bntUpdate.setFont(new Font("sansserif", Font.BOLD, 16));
           bntUpdate.setForeground(Color.BLACK);
           bntUpdate.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\update.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntUpdate.addActionListener((e) -> {
               updateOrderStatus();
               
           });    
           panel.add(bntUpdate);
           
           // 1️⃣ Tên cột
        String[] columnNames = {
            "Order.No", "Customer.ID", "Customer Name", "Address", 
            "Contact", "Total Product", "Total Price", "Payment","Date Order", "Time Order", "Status"
        };

        // 2️⃣ Tạo model
        model = new DefaultTableModel(columnNames, 0);


        // 4️⃣ Định dạng font
        Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font headerFont = new Font("SansSerif", Font.BOLD, 16);

        // 5️⃣ Tạo bảng sử dụng MyTable
        tableOrder = new MyTable(
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

        JScrollPane scrollPane = MyTable.createScrollPane(tableOrder, 0, 100, 1275, 700);

        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));
        
        tableOrder.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int selectedRow = tableOrder.getSelectedRow();
               if (selectedRow != -1) {
                   String stateValue = tableOrder.getValueAt(selectedRow, 10).toString();

                   // Đưa giá trị lên combobox
                   cmbStatus.setSelectedItem(stateValue);
               }
           }
       });
        
        
        
        // 8️⃣ Thêm scrollPane vào panel
        panel.add(scrollPane);
          SwingUtilities.invokeLater(() -> {
               busOrder= new BUS_order();
               List<DTO_order> allOrders = busOrder.getAllOrdersSorted();
               loadOrderDataToTable(allOrders);
               tableOrder.adjustColumnWidths();         // Căn chỉnh cột
          });                
       
   }
    
   private void loadOrderDataToTable(List<DTO_order> orders) {
      // Xóa dữ liệu cũ trong bảng
      model.setRowCount(0);

      // Định dạng ngày tháng
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      // Thêm dữ liệu mới
      for (DTO_order order : orders) {
          Object[] rowData = {
              order.getOrderNo(),
              order.getCustomerID(),
              order.getCustomerName(),
              order.getAddress(),
              order.getContact(),
              order.getTotalQuantityProduct(),
              order.getTotalPrice(),
              order.getPayment(),
              order.getDateOrder().format(dateFormatter), // Định dạng ngày
              order.getTimeOrder(),
              order.getStatus()
          };
          model.addRow(rowData);
      }
}
    
    private void showOrderDetails() {
        int selectedRow = tableOrder.getSelectedRow();
        if (selectedRow < 0) {
           CustomDialog.showError("Please select an order to see details ! ");
            return;
        }

        // Lấy thông tin từ dòng được chọn
         orderNo = (String) model.getValueAt(selectedRow, 0);
         customerID = (String) model.getValueAt(selectedRow, 1);

        // Tạo và hiển thị form chi tiết
        order_Details detailsFrame = new order_Details(customerID, orderNo);
        detailsFrame.setSize(900, 600);
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setVisible(true);
    }
    
    private void Refresh(){
        busOrder= new BUS_order();
        List<DTO_order> allOrders = busOrder.getAllOrdersSorted();
        loadOrderDataToTable(allOrders);
        tableOrder.adjustColumnWidths();  
        cmbStatus.setSelectedIndex(0);
        cmbSearch.setSelectedIndex(0);   
    }
    
    private void updateOrderStatus() {
        int selectedRow = tableOrder.getSelectedRow();
        if (selectedRow < 0) {
            CustomDialog.showError("Please select an order to update Status!");
            return;
        }

        orderNo = (String) model.getValueAt(selectedRow, 0); // Get Order No from selected row
        String currentStatus = (String) model.getValueAt(selectedRow, 10); // Current status from table
        String newStatus = (String) cmbStatus.getSelectedItem(); // Get selected status from combobox

        if (newStatus.equals(currentStatus)) {
            CustomDialog.showSuccess("Status is already " + newStatus);
            return;
        }

        // Update directly without confirmation
        boolean success = busOrder.updateOrderStatus(orderNo, newStatus);

        if (success) {
            // Update the table
            model.setValueAt(newStatus, selectedRow, 10);
            CustomDialog.showSuccess("Status updated successfully to " + newStatus + "!");
            List<DTO_order> allOrders = busOrder.getAllOrdersSorted();
            loadOrderDataToTable(allOrders);
            tableOrder.adjustColumnWidths();
        } else {
            CustomDialog.showError("Failed to update status!");
        }
    }
    
}