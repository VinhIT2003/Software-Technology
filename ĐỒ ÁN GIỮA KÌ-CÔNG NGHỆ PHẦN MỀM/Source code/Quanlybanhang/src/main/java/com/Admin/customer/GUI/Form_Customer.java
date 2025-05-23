package com.Admin.customer.GUI;

import javax.swing.JPanel;
//import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.JTableHeader;
import com.Admin.customer.BUS.BusCustomer;
import javax.swing.tree.*;
import com.Admin.customer.BUS.BusCustomer;
import com.Admin.customer.DTO.DTOCustomer;
import javax.swing.table.DefaultTableModel;
import com.ComponentandDatabase.Components.MyTable;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyPanel;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyTreeview;
import com.ComponentandDatabase.Components.CustomDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Form_Customer extends JPanel {
    private JPanel panel;
    private JLabel lblStatus; 
    private MyTable Customner;
    private MyTextField txtSearch;
    private MyCombobox<String> cmbSearch, cmbStatus;
    private JPanel panelSearch;
    private MyButton bntSearch, bntRefresh, bntDelete, bntExportfile, bntUpdate;
    private BusCustomer busCustomer;
    private DefaultTableModel model;

    public Form_Customer() {
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

        // 1️⃣ Tên cột
        String[] columnNames = {
            "Customer.ID", "Full Name", "Gender", "Date Of Birth", 
            "Email", "Contact", "Address", "Status"
        };

        // 2️⃣ Tạo model
        model = new DefaultTableModel(columnNames, 0);


        // 4️⃣ Định dạng font
        Font contentFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font headerFont = new Font("SansSerif", Font.BOLD, 16);

        // 5️⃣ Tạo bảng sử dụng MyTable
        Customner = new MyTable(
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

        JScrollPane scrollPane = MyTable.createScrollPane(Customner, 20, 100, 1250, 700);

        // 7️⃣ Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));

        // 8️⃣ Thêm scrollPane vào panel
        panel.add(scrollPane);

        // 9️⃣ Repaint panel sau khi thêm
        panel.revalidate();
        panel.repaint();

        // 🔟 Load dữ liệu vào model sau khi GUI sẵn sàng
        SwingUtilities.invokeLater(() -> {
            loadCustomerData(model);
            Customner.adjustColumnWidths(); // 👈 Gọi sau khi dữ liệu đã được load
        });

           // Bắt sự kiện chọn dòng trong bảng để set giá trị vào combobox State
       Customner.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int selectedRow = Customner.getSelectedRow();
               if (selectedRow != -1) {
                   String stateValue = Customner.getValueAt(selectedRow, 7).toString();

                   // Đưa giá trị lên combobox
                   cmbStatus.setSelectedItem(stateValue);
               }
           }
       });
      
    
         // Tạo panelSearch với màu nền trắng
            panelSearch = new MyPanel(Color.WHITE);
            panelSearch.setLayout(null);
            panelSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            panelSearch.setBounds(650,20, 600, 60);
            
  
            // Tạo txtSearch
            txtSearch = new MyTextField();
            txtSearch.setHint("Enter the search key word...");
            txtSearch.setBounds(200, 10, 250, 35); // Đặt vị trí và kích thước
            txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            txtSearch.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
            txtSearch.setHintFont(new Font("Arial", Font.ITALIC, 14));
            txtSearch.setBackgroundColor(Color.decode("#F5FFFA"));
            panelSearch.add(txtSearch); // Thêm vào giao diện
            
            // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
            String[] items = {"Customer.ID", "Customer Name", "Email", "Contact"};
            cmbSearch = new MyCombobox<>(items);
            cmbSearch.setBounds(30, 10, 160, 35);
            cmbSearch.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbSearch.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
            panelSearch.add(cmbSearch);
            //cmbSearch.refreshUI();
            
              // Tạo danh sách item cho JComboBox
                        // Tạo danh sách item cho JComboBox
            String[] items_status = {"Active", "Inactive"};
            cmbStatus = new MyCombobox<>(items_status);
            cmbStatus.setBounds(380, 40, 110, 35);
            cmbStatus.setCustomFont(new Font("Times New Roman", Font.PLAIN, 15));
            cmbStatus.setCustomColors(Color.WHITE, Color.GRAY, Color.BLACK);
            panel.add(cmbStatus);
            
            lblStatus= new JLabel("Status");
            lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
            lblStatus.setForeground(Color.BLACK);
            lblStatus.setBounds(410, 5, 100, 35);
            panel.add(lblStatus);
            
            
            
            bntSearch= new MyButton("Search", 20);
            bntSearch.setBackgroundColor(Color.decode("#00CC33")); // Màu nền
            bntSearch.setPressedColor(Color.decode("#33CC33")); // Màu khi nhấn
            bntSearch.setHoverColor(Color.decode("#00EE00")); // Màu khi rê chuột vào
            //bntSearch.setBounds(320, 10, 130, 35);
            bntSearch.setFont(new Font("Times New Roman", Font.BOLD, 16));
            bntSearch.setForeground(Color.WHITE);
            bntSearch.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\search.png", 25, 25, 5, SwingConstants.RIGHT, SwingConstants.CENTER);    
            bntSearch.setBounds(txtSearch.getX() + txtSearch.getWidth() + 10, 10, 120, 35);
           bntSearch.addActionListener(e -> {
                try {
                    String selectedColumn = cmbSearch.getSelectedItem().toString();
                    String keyword = txtSearch.getText().trim();
                    String statusFilter = cmbStatus.getSelectedItem().toString();
                     busCustomer= new BusCustomer();
                    // Gọi BUS để lấy danh sách khách hàng đã lọc
                    List<DTOCustomer> resultList = busCustomer.searchCustomer(selectedColumn, keyword, statusFilter);

                    // Xóa dữ liệu cũ
                    model.setRowCount(0); 

                    // Hiển thị dữ liệu mới
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    for (DTOCustomer customer : resultList) {
                        model.addRow(new Object[] {
                            customer.getCustomerID(),
                            customer.getFullName(),
                            customer.getGender(),
                            customer.getDateOfBirth() != null ? sdf.format(customer.getDateOfBirth()) : "",
                            customer.getEmail(),
                            customer.getContact(),
                            customer.getAddress(),
                            customer.getStatus()
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm khách hàng: " + ex.getMessage());
                }
            });


            panelSearch.add(bntSearch);
            panel.add(panelSearch);
             
            bntRefresh = new MyButton("Refresh", 20);
            bntRefresh.setBackgroundColor(Color.WHITE); // Màu nền
            bntRefresh.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
            bntRefresh.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
            bntRefresh.setBounds(0, 40, 140, 35); // Tăng chiều rộng để icon không bị che mất
            bntRefresh.setFont(new Font("sansserif", Font.BOLD, 18));
            bntRefresh.setForeground(Color.BLACK);
            bntRefresh.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\refresh.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
            bntRefresh.addActionListener((ActionEvent e) -> {
                refreshForm();
        });
            panel.add(bntRefresh);
            
            bntDelete = new MyButton("Delete", 20);
            bntDelete.setBackgroundColor(Color.WHITE); // Màu nền
            bntDelete.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
            bntDelete.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
            bntDelete.setBounds(150, 40, 130, 35); // Tăng chiều rộng để icon không bị che mất
            bntDelete.setFont(new Font("sansserif", Font.BOLD, 18));
            bntDelete.setForeground(Color.BLACK);
            bntDelete.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\delete.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
            bntDelete.addActionListener(e -> {
                int selectedRow = Customner.getSelectedRow();
                if (selectedRow == -1) {
                    CustomDialog.showError("Please choose a customer to delete!");
                    return;
                }

                busCustomer = new BusCustomer();
                String customerID = Customner.getValueAt(selectedRow, 0).toString(); // Cột 0 là Customer.ID
                String fullName = busCustomer.getCustomerNameByID(customerID);

                boolean confirm = CustomDialog.showOptionPane(
                        "Confirm Deletion",
                        "Are you sure you want to delete customer: " + fullName + "?",
                        UIManager.getIcon("OptionPane.questionIcon"),
                        Color.decode("#FF6666")
                );

                if (confirm) {
                    try {
                        busCustomer = new BusCustomer();
                        boolean isDeleted = busCustomer.delete(customerID);


                        if (isDeleted) {
                            model.setRowCount(0);
                            loadCustomerData(model);
                            Customner.adjustColumnWidths();
                            CustomDialog.showSuccess("Successfully deleted customer " + fullName + "!");
                        } else {
                            System.out.println("DEBUG: Deletion failed, customer might not exist.");
                            CustomDialog.showError("Delete failure! Customer may not exist or an issue occurred.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        CustomDialog.showError("Error while deleting customer: " + ex.getMessage());
                    }
                }
            });


           panel.add(bntDelete);
            
           bntExportfile = new MyButton("",20);
           bntExportfile.setBackgroundColor(Color.WHITE); // Màu nền
           bntExportfile.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntExportfile.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntExportfile.setBounds(280, 40, 80, 40); // Tăng chiều rộng để icon không bị che mất
           bntExportfile.setFont(new Font("sansserif", Font.BOLD, 18));
           bntExportfile.setForeground(Color.BLACK);
           bntExportfile.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Excel.png", 30, 30, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
          bntExportfile.addActionListener((ActionEvent e) -> {
              busCustomer= new BusCustomer();
              busCustomer.exportFile(Customner);
        });

           panel.add(bntExportfile);
           
           
           bntUpdate = new MyButton("Update", 20);
           bntUpdate.setBackgroundColor(Color.WHITE); // Màu nền
           bntUpdate.setPressedColor(Color.decode("#D3D3D3")); // Màu khi nhấn
           bntUpdate.setHoverColor(Color.decode("#EEEEEE")); // Màu khi rê chuột vào
           bntUpdate.setBounds(500, 40, 140, 35); // Tăng chiều rộng để icon không bị che mất
           bntUpdate.setFont(new Font("sansserif", Font.BOLD, 18));
           bntUpdate.setForeground(Color.BLACK);
           bntUpdate.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\update.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
           bntUpdate.addActionListener(e -> {
                busCustomer = new BusCustomer();
                busCustomer.update(Customner, cmbStatus);

                model.setRowCount(0);  // 👈 Xóa dữ liệu cũ khỏi bảng
                loadCustomerData(model);  // 👈 Tải lại dữ liệu mới
                Customner.adjustColumnWidths(); // 👈 Cập nhật lại chiều rộng cột
      });

           panel.add(bntUpdate);
           panelSearch.repaint();
           panelSearch.revalidate();
            
           this.repaint();
           this.revalidate();          
     
   }
 
        // 3️⃣ Load dữ liệu từ BUS vào model
     private void loadCustomerData(DefaultTableModel model) {
         BusCustomer bus = new BusCustomer(); // Khởi tạo lớp BUS
         List<DTOCustomer> customerList = bus.getAllCustomers(); // Lấy danh sách khách hàng

         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

         for (DTOCustomer customer : customerList) {
             Object[] row = new Object[]{
                 customer.getCustomerID(),
                 customer.getFullName(),
                 customer.getGender(),
                 customer.getDateOfBirth() != null ? sdf.format(customer.getDateOfBirth()) : "",
                 customer.getEmail(),
                 customer.getContact(),
                 customer.getAddress(),
                 customer.getStatus()
             };
             model.addRow(row); // Thêm dòng vào model
         }
     }
     public void refreshForm() {
        if (model != null) {
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            loadCustomerData(model); // Load lại dữ liệu từ DB
        } else {
            System.err.println("Model is null - chưa được khởi tạo");
        }

        // Reset các combo box
        if (cmbStatus != null) cmbStatus.setSelectedIndex(0);
        if (cmbSearch != null) cmbSearch.setSelectedIndex(0);
    }

   
}


