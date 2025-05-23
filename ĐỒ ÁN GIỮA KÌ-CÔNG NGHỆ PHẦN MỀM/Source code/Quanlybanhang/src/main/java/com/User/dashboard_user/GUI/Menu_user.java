package com.User.dashboard_user.GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.*; // Import đầy đủ các sự kiện
import com.ComponentandDatabase.Components.CustomDialog;


public class Menu_user extends JPanel {
    
    private JLabel lblTitle, lblHome, lblCart, lblOrder, lblProfile, lblProduct, lblExport, lblStatistics, lblMessage, lblExit, lblMenu, lblImport, lblStock, lblInsurance;
    private JPanel menuPanel; // Panel chứa tất cả label
    private JLabel lastHoveredLabel = null; // Lưu label được hover trước đó
     private boolean isMenuExpanded = false;
    public Dashboard_user parentFrame;
    private Color hoverColor = Color.decode("#87CEEB"); // Màu khi hover
    private Color selectedColor = Color.decode("#87CEEB");

    private Color transparentColor = new Color(0, 0, 0, 0); // Màu trong suốt
    private JLabel selectedLabel = null; // Label đang được chọn
    public CustomDialog cs;
      // Biến lưu label đang được hover


    public Menu_user(Dashboard_user parentFrame) {
        this.parentFrame=parentFrame;
        initComponents();
        init(); 
        setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(null); // Dùng null layout để tự thiết lập vị trí các thành phần
    }

  
    private void init() {
     try {
        // Tạo panel chứa menu
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setOpaque(false); // Giữ panel trong suốt
        menuPanel.setBounds(0, 0, 300, 1000); // Điều chỉnh theo UI của bạn
        
        lblProfile = createLabel("Your Information", "profile.png", 50);
        // Label Menu (bấm vào để mở/đóng)
        lblMenu = createLabel("Menu", "menu.png",120);
 
        lblMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggleMenu();
                }
            });

        // Đọc ảnh từ đường dẫn tuyệt đối
//        lblTitle = createLabelWithIcon("Sales Management<br><p style='margin-left: 20px;'>Application</p>",
//                "Title_icon.png", 20, 20, 260, 55, true);

        lblHome = createLabel("Home", "home.png", 190);
        lblCart = createLabel("Cart", "cart.png", 280);
        lblOrder = createLabel("Order", "order.png", 370);
        lblExit= createLabel("Exit", "exit.png", 470);
//        
        
        // Thêm hiệu ứng hover
        addHoverEffectForExit(lblMenu);
        addHoverEffectForExit(lblHome);
        addHoverEffectForExit(lblCart);
        addHoverEffectForExit(lblOrder);
        addHoverEffectForExit(lblProfile);
        addHoverEffectForExit(lblExit);

        // Thêm các label vào menuPanel
        //menuPanel.add(lblTitle);
        menuPanel.add(lblHome);
        menuPanel.add(lblCart);
        menuPanel.add(lblOrder);
        menuPanel.add(lblProfile);
        menuPanel.add(lblExit);
        menuPanel.add(lblMenu);
//        
       
        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.showForm("Home_user");
            }
        });
        
         lblProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MyProfile_cus profile= new MyProfile_cus();
                profile.setVisible(true);
            }
        });
        
          lblCart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.showForm("Form_Cart");
            
            }
        });
          
          lblOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.showForm("Order_Form");
            }
        });
         
         
        

      lblExit.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            boolean confirm = cs.showOptionPane(
                    "Exit",
                    "Are you sure to exit ?",
                    UIManager.getIcon("OptionPane.questionIcon"),
                    Color.decode("#FF6666")
            );

              if (confirm) {
                   System.exit(0);
            }
        }
});

        // Thêm menuPanel vào frame
        setLayout(null);
        add(menuPanel);
   } catch (IOException e) {
        e.printStackTrace();
    }
}

    
    private void addHoverEffectForExit(JLabel label) {
     label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // Áp dụng hover nếu label không được chọn
            if (label != selectedLabel) {
                setLabelHoverStyle(label);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Chỉ reset nếu label không được chọn
            if (label != selectedLabel) {
                resetLabelStyle(label);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Nếu click vào label đang được chọn thì bỏ chọn
            if (label == selectedLabel) {
                resetLabelStyle(label);
                selectedLabel = null;
            } 
            // Nếu click vào label khác
            else {
                // Bỏ chọn label cũ (nếu có)
                if (selectedLabel != null) {
                    resetLabelStyle(selectedLabel);
                }
                
                // Chọn label mới
                selectedLabel = label;
                setLabelSelectedStyle(label);
            }
            
        }
    });
}

    // Style khi hover
    private void setLabelHoverStyle(JLabel label) {
        label.setOpaque(true);
        label.setBackground(hoverColor);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.revalidate();
        label.repaint();
    }

    // Style khi được chọn (dính cứng)
    private void setLabelSelectedStyle(JLabel label) {
        label.setOpaque(true);
        label.setBackground(selectedColor); // Màu khác với hover để phân biệt
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.revalidate();
        label.repaint();
    }

    // Reset style về mặc định
    private void resetLabelStyle(JLabel label) {
        label.setOpaque(false);
        label.setBackground(transparentColor);
        label.setCursor(Cursor.getDefaultCursor());
        label.revalidate();
        label.repaint();
    }

   // Tạo label thường (không phải tiêu đề)
    private JLabel createLabel(String text, String iconName, int yPosition) throws IOException {
        return createLabelWithIcon(text, iconName, 20, yPosition, 260, 35, false);
    }

    // Tạo label có icon (dùng chung cho tiêu đề và menu)
    private JLabel createLabelWithIcon(String text, String iconName, int x, int y, int width, int height, boolean isTitle) throws IOException {
        // Kiểm tra file ảnh
        File file = new File("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\" + iconName);
        if (!file.exists()) {
            System.err.println("⚠️ File not found: " + file.getAbsolutePath());
            return new JLabel(text);
        }

        // Đọc và resize ảnh
        Image img = ImageIO.read(file).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);

        // Tạo JLabel
        JLabel label = new JLabel("<html>" + text + "</html>", icon, JLabel.LEFT);
        label.setFont(isTitle ? new Font("Arial", Font.BOLD | Font.ITALIC, 20) : new Font("sansserif", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        label.setIconTextGap(15);
        label.setOpaque(false); // Ban đầu trong suốt
        return label;
    }

    private void resetLabelBackground(JLabel label) {
        SwingUtilities.invokeLater(() -> {
            label.setOpaque(false);
            label.setBackground(new Color(0, 0, 0, 0));
            menuPanel.revalidate();
            menuPanel.repaint();
        });
    }

     // Hiển thị hoặc ẩn các label con khi bấm vào lblMenu
    private void toggleMenu() {
        isMenuExpanded = !isMenuExpanded;
        setMenuVisibility(isMenuExpanded);
    }

    private void setMenuVisibility(boolean isVisible) {
        lblHome.setVisible(isVisible);
        lblCart.setVisible(isVisible);
        lblOrder.setVisible(isVisible);
//        lblCategory.setVisible(isVisible);
//        lblProduct.setVisible(isVisible);
//        lblImport.setVisible(isVisible);
//        lblExport.setVisible(isVisible);
//        lblStock.setVisible(isVisible);
//        lblInsurance.setVisible(isVisible);
//        lblStatistics.setVisible(isVisible);
//        lblMessage.setVisible(isVisible);
        lblExit.setVisible(isVisible);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    
      @Override
    protected void paintChildren(Graphics grphcs) {
    Graphics2D g2 = (Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Tạo hiệu ứng Gradient với màu sắc nhẹ nhàng hơn
    GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#4682B4"));
    g2.setPaint(g);

    // Vẽ nền không bo góc
    g2.fillRect(0, 0, getWidth(), getHeight());

    super.paintChildren(grphcs);
}

}
