package com.ComponentandDatabase.Components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MyRadioButton extends JRadioButton {

    // Constructor để tạo MyRadioButton với các tham số tùy chỉnh
    public MyRadioButton(String text, String iconPath, int iconSize, String tooltip) {
        super(text);  // Gọi constructor của JRadioButton với văn bản
        setFocusPainted(false);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Thiết lập icon nếu có
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = loadIcon(iconPath, iconSize, iconSize);
            if (icon != null) {
                setIcon(icon);
                setHorizontalTextPosition(SwingConstants.RIGHT);  // Đặt vị trí văn bản bên phải icon
                setIconTextGap(10); // Khoảng cách giữa icon và văn bản
            }
        }

        // Thiết lập tooltip nếu có
        if (tooltip != null && !tooltip.isEmpty()) {
            setToolTipText(tooltip);
        }
    }

    // Hỗ trợ tải icon từ URL hoặc từ resources
   private static ImageIcon loadIcon(String path, int width, int height) {
    try {
        Image image;
        if (path.startsWith("http")) {
            URL url = new URL(path);
            image = new ImageIcon(url).getImage();
        } else if (path.startsWith("/") || MyRadioButton.class.getResource(path) != null) {
            // Đường dẫn trong resources
            URL iconURL = MyRadioButton.class.getResource(path);
            if (iconURL == null) {
                System.err.println("Không tìm thấy icon trong resources: " + path);
                return null;
            }
            image = new ImageIcon(iconURL).getImage();
        } else {
            // Đường dẫn tuyệt đối ngoài ổ đĩa (VD: D:\abc\...)
            image = new ImageIcon(path).getImage();
        }

        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    } catch (Exception e) {
        System.err.println("Lỗi khi tải icon từ: " + path);
        return null;
    }
}

}
