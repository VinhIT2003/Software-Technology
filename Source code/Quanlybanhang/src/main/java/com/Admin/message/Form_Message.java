
package com.Admin.message;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.*;

import java.awt.*;

public class Form_Message extends JPanel {
    private JPanel panel;
    private JLabel lblTitle; 
    public Form_Message() {
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
        panel.setBounds(200, 52, 1530, 860); // Giữ nguyên layout của các thành phần
        panel.setBackground(Color.WHITE); // Màu xanh dương
        add(panel);

        // Tiêu đề
        lblTitle = new JLabel("Form Message");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(280, 130, 250, 50); // Định vị đúng bên trong panel
        lblTitle.setForeground(Color.BLACK);
        panel.add(lblTitle);
       
   }
}
