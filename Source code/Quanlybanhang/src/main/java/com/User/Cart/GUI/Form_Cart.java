package com.User.Cart.GUI;

import com.Admin.product.DTO.DTOProduct;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyRadioButton;
import com.User.order.DTO.DTO_OrderDetails;
import com.User.order.BUS.BUS_OrderDetails;
import com.User.order.DTO.DTO_Order;
import com.User.order.BUS.BUS_Order;
import com.User.home.DTO.productDTO;
import com.User.home.GUI.productDeteails;
import com.User.home.GUI.CartUpdateListener;
import com.User.order.GUI.OrderUpdateListener;
import com.User.order.GUI.Order_Form;
import com.User.home.BUS.productBUS;
import com.User.Cart.BUS.BUSCart;
import com.User.Cart.DTO.DTOCart;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Form_Cart extends JPanel implements CartUpdateListener {
    private JPanel panel, panelShow;
    private JLabel lblPayment;
    private JScrollPane scrollShow;
    private productBUS proBUS;
    private MyButton bntOrder;
    private BUSCart cartBUS;
    private String currentCustomerID; // Thêm biến để lưu ID khách hàng hiện tại 
    private MyRadioButton momo, cash;
    private BUS_Order busOrder;
    private BUS_OrderDetails busOrderDetails;
    public  ArrayList<DTOCart> cartItems;
    ArrayList<productDTO> productsInCart;
    private OrderUpdateListener orderUpdateListener;

    public Form_Cart(String customerID) {
        this.currentCustomerID = customerID;
        this.cartBUS = new BUSCart();
        initComponents();
        initProductDisplayArea();
        updateProductList();
        productDeteails.addCartUpdateListener(this);
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1530, 860));
        setBackground(Color.WHITE);
         
        
    }
      public void setOrderUpdateListener(OrderUpdateListener listener) {
        this.orderUpdateListener = listener;
    }
    
    
    public void updateProductList() {
        panelShow.removeAll();
        proBUS = new productBUS();
        
        // Lấy danh sách sản phẩm trong giỏ hàng
        cartItems = cartBUS.getCartItemsByCustomer(currentCustomerID);
        productsInCart = new ArrayList<>();
        
        // Lấy thông tin chi tiết của từng sản phẩm trong giỏ hàng
        for (DTOCart cartItem : cartItems) {
            productDTO product = proBUS.getProductById(cartItem.getProductID());
            if (product != null) {
                // Cập nhật số lượng theo giỏ hàng
                product.setQuantity(cartItem.getQuantity());
                productsInCart.add(product);
            }
        }
        
        displayProducts(productsInCart);
    }

    private void initProductDisplayArea() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1530, 860);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Sử dụng GridLayout thay vì FlowLayout
        panelShow = new JPanel(new GridLayout(0, 4, 5, 8));
        panelShow.setBackground(Color.WHITE);
        panelShow.setBorder(null);

        scrollShow = new JScrollPane(panelShow);
        scrollShow.setBounds(0, 50, 1250, 500);
        scrollShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollShow.setBorder(null);
        panel.add(scrollShow); 
        
        
      lblPayment= new JLabel("Payment: ");
      lblPayment.setFont(new Font("sansserif", Font.BOLD, 18));
      lblPayment.setForeground(Color.BLACK);
      lblPayment.setBounds(350, 580, 100, 35);
      panel.add(lblPayment);

                    // Momo icon resized
      JLabel momoIcon = new JLabel(loadScaledIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\momo.png", 30, 30));
      momoIcon.setBounds(480, 580, 30, 30);
      panel.add(momoIcon);

      // Radio button
      momo = new MyRadioButton("Momo", null, 0, "Select Momo");
      momo.setBounds(520, 580, 150, 30);
      momo.addActionListener(e -> {
        if (momo.isSelected()) {
            try {
                Desktop.getDesktop().browse(new URI("https://momo.vn"));
            } catch (Exception ex) {
                ex.printStackTrace();
               CustomDialog.showError("Can't open the Momo website !");
            }
        }
    });

      panel.add(momo);

      // Cash icon resized
      JLabel cashIcon = new JLabel(loadScaledIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\cash.png", 30, 30));
      cashIcon.setBounds(640, 580, 30, 30);
      panel.add(cashIcon);

      // Radio button
      cash = new MyRadioButton("Cash", null, 0, "Select Cash");
      cash.setBounds(680, 580, 150, 30);
      panel.add(cash);
      
            // Nhóm 2 radio button lại để chỉ chọn được 1 cái
      ButtonGroup group = new ButtonGroup();
      group.add(momo);
      group.add(cash);

       
     bntOrder = new MyButton("Order", 20);
     bntOrder.setBackgroundColor(Color.decode("#FFA500"));
     bntOrder.setPressedColor(Color.decode("#FF7F50"));
     bntOrder.setHoverColor(Color.decode("#FFCC66"));
     bntOrder.setFont(new Font("Times New Roman", Font.BOLD, 16));
     bntOrder.setForeground(Color.WHITE);
     bntOrder.setBounds(520, 650, 130, 35);
     bntOrder.addActionListener((e) -> {
         Order();
     });
     panel.add(bntOrder);
 }

    
    public void displayProducts(ArrayList<productDTO> products) {
        panelShow.removeAll();

          if (products.isEmpty()) {
            // Tạm thời đặt layout thành null để có thể sử dụng setBounds
            panelShow.setBorder(null);
            panelShow.setLayout(null);


            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn giữa label

            JLabel noProducts = new JLabel("Your cart is empty", SwingConstants.CENTER);
            noProducts.setFont(new Font("Arial", Font.BOLD, 20));

            // Thêm label vào centerPanel
            centerPanel.add(noProducts, BorderLayout.CENTER);

            // Đặt vị trí và kích thước theo ý muốn
            centerPanel.setBounds(500, 200, 200, 50);
            centerPanel.setBackground(Color.WHITE);

            panelShow.add(centerPanel);

        } else {
             panelShow.setLayout(new GridLayout(0, 4, 5, 8));
            // Không cần productsContainer nữa vì GridLayout tự động xuống dòng
            for (productDTO product : products) {
                JPanel productPanel = createProductPanel(product);
                panelShow.add(productPanel);
            }
        }

        panelShow.revalidate();
        panelShow.repaint();
    }

     
   private JPanel createProductPanel(productDTO product) {
    JPanel panelcreate = new JPanel(new BorderLayout(3, 3)); // Giảm khoảng cách dọc
        panelcreate.setPreferredSize(new Dimension(280, 240)); // Giảm chiều cao từ 250 xuống 240
        panelcreate.setBackground(Color.WHITE);
        panelcreate.setBorder(null);
        panelcreate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(3, 5, 3, 5) // Giảm padding top và bottom
        ));

        // Product Image
        ImageIcon icon = new ImageIcon(product.getImage());
        Image img = icon.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH); // Giảm chiều cao ảnh
        JLabel imageLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0)); // Giảm khoảng cách dưới ảnh
        panelcreate.add(imageLabel, BorderLayout.NORTH);

      // Product Details
      JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
      detailsPanel.setBackground(Color.WHITE);

     // Thêm các thông tin với font và padding tối ưu
     addCompactDetail(detailsPanel, "ID: " + product.getProductID(), Font.PLAIN, 12);
     addCompactDetail(detailsPanel, "Name: " + product.getProductName(), Font.PLAIN, 12);
     addCompactDetail(detailsPanel, "Price: " + product.getPrice() + " VNĐ", Font.PLAIN, 12);
     addCompactDetail(detailsPanel, "Quantity: " + product.getQuantity(), Font.PLAIN, 12);
     addCompactDetail(detailsPanel, "Status: " + getStatusText(product), Font.PLAIN, 12);

     panelcreate.add(detailsPanel, BorderLayout.CENTER);

     // Action Buttons - Gọn hơn
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3)); // Giảm khoảng cách
     buttonPanel.setBackground(Color.WHITE);

     MyButton detailBtn = new MyButton("Details", 8);
     detailBtn.setPreferredSize(new Dimension(75, 22)); // Nút nhỏ hơn
     detailBtn.addActionListener((e) -> {
         CartDetails details = new CartDetails();
         details.setVisible(true);
         details.displayProductDetails(product);
     });
     buttonPanel.add(detailBtn);
     
     MyButton bntDelete = new MyButton("Delete", 8);
     bntDelete.setPreferredSize(new Dimension(75, 22)); // Nút nhỏ hơn
     bntDelete.setBackgroundColor(Color.decode("#f44336")); 
     bntDelete.setHoverColor(Color.decode("#FF6633"));      
     bntDelete.setPressedColor(Color.decode("#d32f2f")); 
      bntDelete.setForeground(Color.WHITE);
      bntDelete.addActionListener(e -> {
       boolean confirm = CustomDialog.showOptionPane(
                          "Confirm Deletion",
                          "Are you sure you want to delete this Product ! ",
                           UIManager.getIcon("OptionPane.questionIcon"),
                          Color.decode("#FF6666")
                  );
        if (confirm) {
            deleteProductFromCart(product.getProductID());
            
        }
   });
    
    
    
    buttonPanel.add(bntDelete);
    
     panelcreate.add(buttonPanel, BorderLayout.SOUTH);

     return panelcreate;
 }
   
        // Hàm load ảnh và resize
    private ImageIcon loadScaledIcon(String path, int width, int height) {
         ImageIcon originalIcon = new ImageIcon(path);
         Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
         return new ImageIcon(scaledImage);
     }

  

    private void addCompactDetail(JPanel panel, String text, int fontStyle, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0)); // Giảm padding
        panel.add(label);
    }
    private String getStatusText(productDTO product) {
        return product.getQuantity() == 0 ? "Out of Stock" : product.getStatus();
    }
    
    
    private void deleteProductFromCart(String productId) {
        // 1. Xóa từ cơ sở dữ liệu
        boolean success = cartBUS.removeFromCart(currentCustomerID, productId);

        if (success) {
            // 2. Cập nhật giao diện
            updateProductList();
            CustomDialog.showSuccess("This product deleted from cart successfully !");
            
        } else {
            CustomDialog.showError("The product from cart delete failure !");
        }
    }
    
     public void Order() {
          // Kiểm tra nếu giỏ hàng trống
          if (productsInCart == null || productsInCart.isEmpty()) {
              CustomDialog.showError("Your cart is empty!");
              return;
          }

          // Kiểm tra phương thức thanh toán đã được chọn
          String paymentMethod = getSelectedPaymentMethod();
          if (paymentMethod == null) {
              CustomDialog.showError("Please select a payment method!");
              return;
          }

          // Tạo Order_No ngẫu nhiên 8 chữ số
          String orderNo = String.format("%08d", new java.util.Random().nextInt(100000000));

          // Lấy thời gian hiện tại
          java.time.LocalDate currentDate = java.time.LocalDate.now();
          java.time.LocalTime currentTime = java.time.LocalTime.now().withNano(0);

          // Tính tổng quantity và tổng price
          int totalQuantity = 0;
          BigDecimal totalPrice = BigDecimal.ZERO;

          for (productDTO product : productsInCart) {
              totalQuantity += product.getQuantity();
              totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(product.getQuantity())));
          }

          try {
              // Tạo và thêm Order trước
              DTO_Order order = new DTO_Order();
              order.setOrderNo(orderNo);
              order.setCustomerID(currentCustomerID);
              order.setTotalQuantityProduct(totalQuantity);
              order.setTotalPrice(totalPrice);
              order.setPayment(paymentMethod);  // Sử dụng paymentMethod đã lấy
              order.setDateOrder(currentDate);
              order.setTimeOrder(currentTime);

               busOrder = new BUS_Order();
              boolean orderSuccess = busOrder.addOrderDetail(order);

              if (!orderSuccess) {
                  CustomDialog.showError("Failed to create order!");
                  return;
              }

              // Thêm các Order Details
              boolean allDetailsSuccess = true;
               busOrderDetails = new BUS_OrderDetails();

              for (productDTO product : productsInCart) {
                  DTO_OrderDetails orderDetail = new DTO_OrderDetails();
                  orderDetail.setOrderNo(orderNo);
                  orderDetail.setCustomerID(currentCustomerID);
                  orderDetail.setProductID(product.getProductID());
                  orderDetail.setPrice(product.getPrice());
                  orderDetail.setQuantity(product.getQuantity());
                  orderDetail.setDateOrder(currentDate);
                  orderDetail.setTimeOrder(currentTime);
                  orderDetail.setStatus("Waiting");

                  boolean detailSuccess = busOrderDetails.addOrderDetail(orderDetail);
                  if (!detailSuccess) {
                      allDetailsSuccess = false;
                      System.err.println("Failed to insert product: " + product.getProductID());
                  }
              }

              if (allDetailsSuccess) {
                  // Xóa giỏ hàng sau khi đặt hàng thành công
                  cartBUS.clearCart(currentCustomerID);
                  updateProductList();
                  CustomDialog.showSuccess("Products ordered successfully! Order No: " + orderNo);
                  panelShow.setBorder(null);
                  
               if (orderUpdateListener != null) {
                orderUpdateListener.onOrderPlaced(currentCustomerID, orderNo);
            }
            
            // Tự động chuyển sang tab Order_Form
              switchToOrderForm();
        }
               else {
                  // Nếu có lỗi khi thêm details, xóa order đã tạo
                  CustomDialog.showError("Some items could not be ordered. Please try again!");
              }
          } catch (Exception e) {
              e.printStackTrace();
              CustomDialog.showError("An error occurred while processing your order!");
          }
      }

      // Hàm lấy phương thức thanh toán được chọn
      private String getSelectedPaymentMethod() {
          if (momo.isSelected()) {
              return "Momo";
          } else if (cash.isSelected()) {
              return "Cash";
          }
          return null;
      }

    
    @Override
    public void onCartUpdated(String customerID) {
        if (this.currentCustomerID.equals(customerID)) {
            // Cập nhật UI trong EDT (Event Dispatch Thread)
            SwingUtilities.invokeLater(() -> {
                updateProductList();
            });
        }
    }

    // Hủy đăng ký khi không cần thiết
    @Override
    public void removeNotify() {
        productDeteails.removeCartUpdateListener(this);
        super.removeNotify();
    }
    
    private void switchToOrderForm() {
        Container parent = getParent();
        while (parent != null) {
            if (parent instanceof JTabbedPane) {
                JTabbedPane tabbedPane = (JTabbedPane) parent;
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    if (tabbedPane.getComponentAt(i) instanceof Order_Form) {
                        tabbedPane.setSelectedIndex(i);
                        break;
                    }
                }
                break;
            }
            parent = parent.getParent();
        }
    }
}