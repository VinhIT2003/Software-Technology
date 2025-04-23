package com.Admin.dashboard_admin;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Box;
import java.util.HashSet;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.Set;
import com.ComponentandDatabase.Components.MyButton;
import com.ComponentandDatabase.Components.MyPanel;
import com.Admin.home.Form_Home;
import com.Admin.category.GUI.Form_Category;
import com.Admin.order.Form_Order;
import com.Admin.customer.GUI.Form_Customer;
import com.Admin.product.GUI.Form_Product;
import com.Admin.importation.Form_Import;
import com.Admin.export.Form_Export;
import com.Admin.stock.Form_Stock;
import com.Admin.insurance.Form_Insurance;
import com.Admin.statistics.Form_Statistics;
import com.Admin.message.Form_Message;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dashboard_ad extends JFrame {
    private JPanel bg, contentPanel;
    private JLabel title, lblDateTime, title_exit;
    private MyButton logout;
    private MyPanel panelTitle;
    private Menu menu;
    private CardLayout cardLayout;
    private Set<JLabel> hoveredLabels = new HashSet<>();
    private boolean isFullScreen = true;

    public Dashboard_ad() {
        initComponents();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F11) {
                    toggleFullScreen();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    exitFullScreen();
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }
  private void initComponents() {
    // --- PANEL NỀN CHÍNH ---
    bg = new JPanel(new BorderLayout());

    // --- PANEL TIÊU ĐỀ ---
    panelTitle = new MyPanel(new BorderLayout());
    panelTitle.setPreferredSize(new Dimension(0, 50));
    panelTitle.setGradientColors(Color.decode("#1CB5E0"), Color.decode("#4682B4"), MyPanel.VERTICAL_GRADIENT);

    // --- Icon và text cho tiêu đề trung tâm ---
    ImageIcon titleIcon = new ImageIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\Title_icon.png");
    Image img = titleIcon.getImage();
    Image resizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    titleIcon = new ImageIcon(resizedImg);
    title = new JLabel("Sales Management System Application || Developed by Quang Vinh", titleIcon, JLabel.CENTER);
    title.setFont(new Font("Times New Roman", Font.BOLD, 20));
    title.setForeground(Color.WHITE);

    // --- Icon và text cho nút exit ở bên trái ---
    ImageIcon exit_icon = new ImageIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\exit_full_screen.png");
    Image img_exit = exit_icon.getImage();
    Image resizedImg_exit = img_exit.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    exit_icon = new ImageIcon(resizedImg_exit);
    title_exit = new JLabel("", exit_icon, JLabel.LEFT);
    title_exit.setFont(new Font("Times New Roman", Font.BOLD, 18));
    title_exit.setForeground(Color.WHITE);
     addHoverEffectForExit(title_exit);

    // --- Đặt title_exit vào panel trái ---
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10)); // căn trái, padding 20px ngang, 10px dọc
    leftPanel.setOpaque(false); // cho trong suốt
    leftPanel.add(title_exit);

    // --- Panel bên phải chứa thời gian và logout ---
    logout = new MyButton("Logout", 20);
    logout.setBackgroundColor(Color.decode("#E55454"));
    logout.setPressedColor(Color.decode("#C04444"));
    logout.setHoverColor(Color.decode("#FF7F7F"));
    logout.setFont(new Font("Times New Roman", Font.BOLD, 18));
    logout.setForeground(Color.WHITE);
    logout.setButtonIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\Admin_icon\\logout.png", 25, 25, 10, SwingConstants.RIGHT, SwingConstants.CENTER);
    logout.addActionListener(e -> requestFocusInWindow());

    lblDateTime = new JLabel();
    lblDateTime.setFont(new Font("Times New Roman", Font.BOLD, 18));
    lblDateTime.setForeground(Color.WHITE);
    updateDateTime();

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
    rightPanel.setOpaque(false);
    rightPanel.add(Box.createHorizontalGlue());
    rightPanel.add(lblDateTime);
    rightPanel.add(Box.createHorizontalStrut(100));
    rightPanel.add(logout);

    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setOpaque(false);
    containerPanel.add(rightPanel, BorderLayout.EAST);

    // --- Gộp mọi thứ vào panelTitle ---
    panelTitle.add(leftPanel, BorderLayout.WEST);
    panelTitle.add(title, BorderLayout.CENTER);
    panelTitle.add(containerPanel, BorderLayout.EAST);
    bg.add(panelTitle, BorderLayout.NORTH);

    // --- MENU & CONTENT ---
    menu = new Menu(this);
    menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
    menu.setPreferredSize(new Dimension(260, 0));

    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);

    JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.add(menu, BorderLayout.WEST);
    wrapper.add(contentPanel, BorderLayout.CENTER);

    bg.add(wrapper, BorderLayout.CENTER);

    // --- THÊM FORM ---
    contentPanel.add(new Form_Home(), "Home");
    contentPanel.add(new Form_Category(), "Category");
    contentPanel.add(new Form_Order(), "Order");
    contentPanel.add(new Form_Customer(), "Customer");
    contentPanel.add(new Form_Product(), "Product");
    contentPanel.add(new Form_Import(), "Import");
    contentPanel.add(new Form_Export(), "Export");
    contentPanel.add(new Form_Stock(), "Stock");
    contentPanel.add(new Form_Insurance(), "Insurance");
    contentPanel.add(new Form_Statistics(), "Statistics");
    contentPanel.add(new Form_Message(), "Message");
    contentPanel.revalidate();
    contentPanel.repaint();


    // --- FRAME SETTINGS ---
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dashboard Admin");
    setFullScreen(); // Full màn hình
    setContentPane(bg);

    // --- CẬP NHẬT GIỜ ---
    Timer timer = new Timer(1000, e -> updateDateTime());
    timer.start();
}


    private void updateLayout() {
        int w = getWidth() - 260; // 260 là chiều rộng menu
        int h = getHeight() - 50; // 50 là chiều cao panelTitle
        contentPanel.setBounds(0, 0, w, h);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void setFullScreen() {
        dispose();
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        isFullScreen = true;
    }

    private void exitFullScreen() {
        if (isFullScreen) {
            toggleFullScreen();
        }
    }

    private void toggleFullScreen() {
        dispose();
        if (isFullScreen) {
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
        } else {
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
        isFullScreen = !isFullScreen;
    }

    private void updateDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("'Date:' dd/MM/yyyy  '-' HH:mm:ss");
        String currentTime = sdf.format(new java.util.Date());
        lblDateTime.setText(currentTime);
    }

   public void showForm(String formName) {
    SwingUtilities.invokeLater(() -> {
        cardLayout.show(contentPanel, formName);
        contentPanel.revalidate();
        contentPanel.repaint();
    });
}



     private void addHoverEffectForExit(JLabel label) {
        Color hoverColor = new Color(173, 216, 230, 200); // Màu xanh nhạt
        Color transparentColor = new Color(0, 0, 0, 0);   // Trong suốt

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Đảm bảo không có label nào còn giữ hiệu ứng hover cũ
                for (JLabel hoveredLabel : hoveredLabels) {
                    if (hoveredLabel != label) {
                        resetLabelBackground(hoveredLabel); // Reset background các label khác
                    }
                }

                hoveredLabels.clear(); // Clear danh sách hover
                hoveredLabels.add(label); // Thêm label hiện tại vào danh sách

                SwingUtilities.invokeLater(() -> {
                    label.setOpaque(true);
                    label.setBackground(hoverColor);
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label.revalidate();
                    label.repaint();
                });
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetLabelBackground(label);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                exitFullScreen();
            }
        });
    }

    private void resetLabelBackground(JLabel label) {
        SwingUtilities.invokeLater(() -> {
            label.setOpaque(false);
            label.setBackground(new Color(0, 0, 0, 0));
            label.revalidate();
            label.repaint();
        });
    } 

    
    public static void main(String args[]) {
       try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Dashboard_ad().setVisible(true));
    }
}