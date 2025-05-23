package com.ComponentandDatabase.Components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private int radius; // Bo góc
    private Color backgroundColor = new Color(7, 164, 121);
    private Color pressedColor = new Color(5, 130, 95);
    private Color hoverColor = new Color(10, 190, 140);
    private boolean isHovering = false;

    // Constructor
    public MyButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Thêm sự kiện chuột để đổi màu khi hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    // Set màu nền bình thường
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    // Set màu khi nhấn
    public void setPressedColor(Color color) {
        this.pressedColor = color;
    }

    // Set màu khi hover (rê chuột vào)
    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

 // Set icon cho button với khả năng chỉnh vị trí
    public void setButtonIcon(String iconPath, int width, int height, int iconTextGap, int horizontalPos, int verticalPos) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        setIcon(icon);
        setIconTextGap(iconTextGap); // Khoảng cách giữa icon và chữ
        setHorizontalTextPosition(horizontalPos); // Vị trí ngang của text so với icon
        setVerticalTextPosition(verticalPos); // Vị trí dọc của text so với icon
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Xác định màu dựa trên trạng thái
        if (getModel().isPressed()) {
            g2.setColor(pressedColor); // Khi nhấn
        } else if (isHovering) {
            g2.setColor(hoverColor); // Khi hover
        } else {
            g2.setColor(backgroundColor); // Bình thường
        }

        // Vẽ button với góc bo tròn
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();

        super.paintComponent(g);
    }
}
