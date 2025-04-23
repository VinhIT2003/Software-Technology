package com.ComponentandDatabase.Components;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MyTextField extends JPanel {
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel prefixIconLabel;
    private JButton eyeButton;
    private boolean isPasswordVisible = false;
    private String hint = "";
    private Font hintFont = new Font("SansSerif", Font.ITALIC, 14);
    private int textOffsetX = 8;

    public MyTextField() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setOpaque(true);  // Đảm bảo JPanel không trong suốt
        setPreferredSize(new Dimension(250, 40));

        // Viền ngoài
        Border outerBorder = BorderFactory.createLineBorder(new Color(7, 164, 121), 2);
        Border innerBorder = new EmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // Label icon bên trái
        prefixIconLabel = new JLabel();
        prefixIconLabel.setPreferredSize(new Dimension(30, 30));
        add(prefixIconLabel, BorderLayout.WEST);

        // TextField
        textField = createTextField();
        add(textField, BorderLayout.CENTER);
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                // Đặt màu nền trắng trước khi vẽ
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                super.paintComponent(g);

                if (getText().isEmpty() && hint != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY);
                    g2.setFont(hintFont);

                    FontMetrics fm = g2.getFontMetrics();
                    int yOffset = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() - 1;
                    g2.drawString(hint, textOffsetX, yOffset);
                }
            }
        };
        tf.setBorder(null);
        tf.setOpaque(true);
        tf.setBackground(getBackground()); // Lấy màu nền của JPanel
        tf.setForeground(Color.BLACK);
        tf.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tf.setMargin(new Insets(5, 5, 5, 5));
        return tf;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }
    
    public void setHintFont(Font font) {
        this.hintFont = font;
        repaint(); // Vẽ lại để cập nhật giao diện
    }


    public void setTextFont(Font font) {
        textField.setFont(font);
        repaint();
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    private ImageIcon resizeIcon(String iconPath, int width, int height) {
    try {
        File file = new File(iconPath);
        if (!file.exists()) {
            System.err.println("File not found: " + iconPath);
            return null;
        }
        BufferedImage img = ImageIO.read(file);
        Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Không thể tải icon: " + e.getMessage());
        return null;
    }
}


    public void setPreFixIcon(String iconPath) {
        ImageIcon resizedIcon = resizeIcon(iconPath, 24, 24);
        if (resizedIcon != null) {
            prefixIconLabel.setIcon(resizedIcon);
        } else {
            prefixIconLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        }
        revalidate();
        repaint();
    }

    private JPasswordField createPasswordField(String hint) {
        return new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                // Đảm bảo nền trắng
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(g);

                if (getPassword().length == 0) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY);
                    g2.setFont(hintFont);

                    FontMetrics fm = g2.getFontMetrics();
                    int yOffset = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() - 1;
                    g2.drawString(hint, textOffsetX, yOffset);
                }
            }
        };
    }

    public JPanel createPasswordFieldWithEyeButton(String hint, String hideIconPath, String showIconPath, Color borderColor, int borderThickness) {
     passwordField = createPasswordField(hint);
     passwordField.setBorder(null);
     passwordField.setOpaque(true);
     passwordField.setBackground(getBackground()); // Đồng bộ màu nền với MyTextField
     passwordField.setForeground(Color.BLACK);
     passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
     passwordField.setPreferredSize(new Dimension(210, 35));

     // Nút hiện/ẩn mật khẩu
     ImageIcon hideIcon = resizeIcon(hideIconPath, 24, 24);
     ImageIcon showIcon = resizeIcon(showIconPath, 24, 24);

        eyeButton = new JButton(hideIcon);
        eyeButton.setPreferredSize(new Dimension(30, 30));
        eyeButton.setBorder(null);
        eyeButton.setOpaque(true); // Đảm bảo giữ màu nền
        eyeButton.setBackground(getBackground()); // Đồng bộ với panel
        eyeButton.setContentAreaFilled(false); // Xóa hiệu ứng nền nổi
        eyeButton.setFocusable(false);
        eyeButton.addActionListener(e -> togglePasswordVisibility(hideIcon, showIcon));
     
// Panel chứa passwordField + eyeButton
     JPanel panel = new JPanel(new BorderLayout());
     panel.setOpaque(true);
     panel.setBackground(getBackground()); // Đồng bộ màu nền
     panel.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness)); // Viền linh hoạt

     panel.add(prefixIconLabel, BorderLayout.WEST);
     panel.add(passwordField, BorderLayout.CENTER);
     panel.add(eyeButton, BorderLayout.EAST);

     return panel;
 }


     public void setBackgroundColor(Color color) {
         setBackground(color);  // Đổi màu nền của JPanel chính
         textField.setBackground(color); // Đổi màu nền của JTextField

         if (passwordField != null) {
             passwordField.setOpaque(true);
             passwordField.setBackground(color); // Đổi màu nền của JPasswordField

             // Kiểm tra nếu parent không null và là một JComponent
             Container parent = passwordField.getParent();
             if (parent instanceof JComponent) {
                 ((JComponent) parent).setBackground(color);
                 ((JComponent) parent).setOpaque(true);
             }
         }
 }
     
    public String getPasswordText() {
        if (passwordField == null) {
            System.err.println("Lỗi: passwordField chưa được khởi tạo!");
            return "";
        }
        return new String(passwordField.getPassword()).trim();
}
  
    private void togglePasswordVisibility(ImageIcon hideIcon, ImageIcon showIcon) {
        isPasswordVisible = !isPasswordVisible;
        passwordField.setEchoChar(isPasswordVisible ? (char) 0 : '*');
        eyeButton.setIcon(isPasswordVisible ? showIcon : hideIcon);
    }
}
