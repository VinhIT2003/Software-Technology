package com.User.home.GUI;

import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyCombobox;
import com.ComponentandDatabase.Components.MyTextField;
import com.User.home.BUS.productBUS;
import com.User.home.DTO.productDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Home_user extends JPanel {

    private JPanel panelShow;
    private JScrollPane scrollShow;
    private productBUS proBUS;
    private MyCombobox comboBoxSearch;
    private MyTextField inputText, inputMin, inputMax;
    private String selectedComboBoxItem;
    private JLabel minlbl, maxlbl;

    public Home_user() {
        proBUS = new productBUS();
        initComponents();
        updateProductList(); // Load products on initialization
    }

    private void initComponents() {
        setLayout(null);
        setBounds(0, 0, 1300, 860);
        setBackground(Color.WHITE);

        // Search components
        initSearchComponents();
        
        // Products display area
        initProductDisplayArea();
    }

    private void initSearchComponents() {
        String[] items = {"Product_ID", "Product_Name", "Price"};
        comboBoxSearch = new MyCombobox(items);
        comboBoxSearch.setBounds(325, 60, 160, 30);
        selectedComboBoxItem = (String) comboBoxSearch.getSelectedItem();
        add(comboBoxSearch);

        inputText = new MyTextField();
        inputText.setBounds(495, 60, 370, 30);
        add(inputText);

        minlbl = new JLabel("Min price:");
        minlbl.setBounds(495, 0, 100, 30);
        minlbl.setVisible(false);
        add(minlbl);

        inputMin = new MyTextField();
        inputMin.setBounds(495, 25, 370, 30);
        inputMin.setVisible(false);
        add(inputMin);

        maxlbl = new JLabel("Max price:");
        maxlbl.setBounds(495, 50, 100, 30);
        maxlbl.setVisible(false);
        add(maxlbl);

        inputMax = new MyTextField();
        inputMax.setBounds(495, 75, 370, 30);
        inputMax.setVisible(false);
        add(inputMax);

        comboBoxSearch.addActionListener(e -> {
            selectedComboBoxItem = (String) comboBoxSearch.getSelectedItem();
            boolean isPriceSearch = "Price".equals(selectedComboBoxItem);
            
            inputText.setVisible(!isPriceSearch);
            inputMin.setVisible(isPriceSearch);
            inputMax.setVisible(isPriceSearch);
            minlbl.setVisible(isPriceSearch);
            maxlbl.setVisible(isPriceSearch);
            
            revalidate();
            repaint();
        });

        MyButton btnSearch = new MyButton("Search", 10);
        btnSearch.setBounds(875, 60, 90, 30);
        btnSearch.addActionListener(e -> searchProducts());
        add(btnSearch);

        MyButton reShowAllProducts = new MyButton("All Products", 10);
        reShowAllProducts.setBounds(113, 60, 110, 30);
        reShowAllProducts.addActionListener(e -> updateProductList());
        add(reShowAllProducts);
    }

    private void initProductDisplayArea() {
        panelShow = new JPanel(new GridBagLayout());
        panelShow.setBackground(Color.WHITE);
        panelShow.setBorder(null); // Loại bỏ hoàn toàn border

        scrollShow = new JScrollPane(panelShow);
        scrollShow.setBounds(0, 130, 1250, 700);
        scrollShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollShow.setBorder(null); // Loại bỏ border của scroll pane nếu cần
        add(scrollShow);
    }
    
    private void updateProductList() {
        panelShow.removeAll();
        panelShow.setLayout(new GridLayout(0, 4, 5, 10)); // Quan trọng: thiết lập lại layout
        ArrayList<productDTO> productList = proBUS.showProduct(null);
        displayProducts(productList);
    }

    private void searchProducts() {
        String condition = null;
        if ("Price".equals(selectedComboBoxItem)) {
            try {
                double min = Double.parseDouble(inputMin.getText());
                double max = Double.parseDouble(inputMax.getText());
                condition = "price BETWEEN " + min + " AND " + max;
            } catch (NumberFormatException e) {
                CustomDialog.showError("Please enter valid price values!");
                return;
            }
        } else {
            String searchText = inputText.getText().trim();
            if (searchText.isEmpty()) {
                CustomDialog.showError("Please enter a search term before searching!");
                return;
            }
            condition = selectedComboBoxItem.toLowerCase() + " LIKE '%" + searchText + "%'";
        }

        ArrayList<productDTO> filteredList = proBUS.showProduct(condition);
        displaySearchResults(filteredList);
    }
    private void displaySearchResults(ArrayList<productDTO> products) {
        panelShow.removeAll();

        // 1. Sử dụng FlowLayout với căn lề trái và trên cùng
        panelShow.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelShow.setBackground(Color.WHITE);
        
        // 2. Thêm một panel container để kiểm soát vị trí
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST; // Căn góc trên bên trái
        gbc.weightx = 1; // Chiếm toàn bộ không gian ngang
        gbc.weighty = 1; // Chiếm toàn bộ không gian dọc
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (products.isEmpty()) {
            JLabel noProducts = new JLabel("No products found", SwingConstants.LEFT);
            noProducts.setFont(new Font("Arial", Font.BOLD, 16));
            container.add(noProducts, gbc);
        } else {
            // 3. Tạo panel chứa các sản phẩm với FlowLayout căn trái
            JPanel productsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            productsPanel.setBackground(Color.WHITE);

            for (productDTO product : products) {
                productsPanel.add(createProductPanel(product));
            }

            container.add(productsPanel, gbc);
        }

        panelShow.add(container);
        panelShow.revalidate();
        panelShow.repaint();

        // 4. Cuộn lên đầu trang
        JScrollBar vertical = scrollShow.getVerticalScrollBar();
        vertical.setValue(vertical.getMinimum());
    }

    public void displayProducts(ArrayList<productDTO> products) {
        panelShow.removeAll();
        panelShow.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelShow.setBackground(Color.WHITE);

        // Tạo container chính để đẩy sản phẩm lên trên
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (products.isEmpty()) {
            JLabel noProducts = new JLabel("Your cart is empty", SwingConstants.LEFT);
            noProducts.setFont(new Font("Arial", Font.BOLD, 16));
            container.add(noProducts, gbc);
        } else {
            // Dùng GridBagLayout để chia hàng cột như trước
            JPanel productsPanel = new JPanel(new GridBagLayout());
            productsPanel.setBackground(Color.WHITE);

            GridBagConstraints prodGbc = new GridBagConstraints();
            prodGbc.insets = new Insets(10, 10, 10, 10);
            prodGbc.anchor = GridBagConstraints.NORTHWEST;

            int col = 0, row = 0;
            final int maxCol = 4;

            for (productDTO product : products) {
                JPanel productPanel = createProductPanel(product);
                prodGbc.gridx = col;
                prodGbc.gridy = row;
                productsPanel.add(productPanel, prodGbc);

                col++;
                if (col == maxCol) {
                    col = 0;
                    row++;
                }
            }

            container.add(productsPanel, gbc);
        }

        panelShow.add(container);
        panelShow.revalidate();
        panelShow.repaint();
    }


    
    private JPanel createProductPanel(productDTO product) {
       JPanel panel = new JPanel(new BorderLayout(3, 3)); // Giảm khoảng cách dọc
        panel.setPreferredSize(new Dimension(280, 240)); // Giảm chiều cao từ 250 xuống 240
        panel.setBackground(Color.WHITE);
        panel.setBorder(null);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(3, 5, 3, 5) // Giảm padding top và bottom
        ));

        // Product Image
        ImageIcon icon = new ImageIcon(product.getImage());
        Image img = icon.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH); // Giảm chiều cao ảnh
        JLabel imageLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0)); // Giảm khoảng cách dưới ảnh
        panel.add(imageLabel, BorderLayout.NORTH);

      // Product Details
      JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
      detailsPanel.setBackground(Color.WHITE);

      addDetailLabel(detailsPanel, "ID: " + product.getProductID(), 12);
      addDetailLabel(detailsPanel, "Name: " + product.getProductName(), 12);
      addDetailLabel(detailsPanel, "Price: " + product.getPrice() + " VNĐ", 12);
      addDetailLabel(detailsPanel, "Quantity: " + product.getQuantity(), 12);
      addDetailLabel(detailsPanel, "Status: " + getStatusText(product), 12);

      panel.add(detailsPanel, BorderLayout.CENTER);

      // Action Buttons
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
      buttonPanel.setBackground(Color.WHITE);

      MyButton detailBtn = new MyButton("Details", 8);
      detailBtn.setPreferredSize(new Dimension(80, 25));
      detailBtn.addActionListener((e) -> {
          productDeteails details= new productDeteails();
          details.setVisible(true);
          details.displayProductDetails(product);
      });
      buttonPanel.add(detailBtn);
      panel.add(buttonPanel, BorderLayout.SOUTH);

      return panel;
  }

   private void addDetailLabel(JPanel panel, String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
    }

    private String getStatusText(productDTO product) {
        return product.getQuantity() == 0 ? "Out of Stock" : product.getStatus();
    }

}