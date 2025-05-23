
package com.User.login_user.GUI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.JButton;
//import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import com.ComponentandDatabase.Components.MyButton;


public class PanelCover_User extends javax.swing.JPanel {
    private ActionListener event;
    private final DecimalFormat df= new DecimalFormat("##0.###");
    private MigLayout layout;
    private JLabel title, description, description1, description2;
    private MyButton signin;
    private boolean isSignUp = true; // Mặc định là Sign up

    public PanelCover_User() {
        setOpaque(false);
        setLayout(null);
        initComponents();
        setOpaque(false);
        init();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2= (Graphics2D)grphcs;
        GradientPaint gra= new GradientPaint(0,0, new Color(30,166,97), 0, getHeight(), new Color(22,116,66));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
        
    }
    
     public void addEvent(ActionListener event){
        this.event = event;
    }
     
    private void init() {
        title = new JLabel("Welcome Back!");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(245, 245, 245));
        title.setBounds(200, 150, 300, 40); // Đặt vị trí xuất hiện của title
        add(title);
        
        description = new JLabel("<html>Please register an account as soon as possible<br>"
                         + "<p style='margin-left:60px;'>if you haven't already done so!</p></html>");
        description.setFont(new Font("SanSerif", Font.ITALIC, 20));
        description.setForeground(new Color(245, 245, 245));
        description.setBounds(100, 230, 700, 60); // Tăng chiều cao để hiển thị dòng thứ 2
        add(description);
        
        
        description1 = new JLabel("<html>Please enter the full your information when you<br>"
                         + "<p style='margin-left:60px;'>are registering an account!</p></html>");
        description1.setFont(new Font("SanSerif", Font.ITALIC, 20));
        description1.setForeground(new Color(245, 245, 245));
        description1.setBounds(100, 330, 700, 60); // Tăng chiều cao để hiển thị dòng thứ 2
        add(description1);
        
        description2 = new JLabel("Login with your personal information");
        description2.setFont(new Font("SansSerif", Font.BOLD|Font.ITALIC, 20));
        description2.setForeground(new Color(245, 245, 245));
        description2.setBounds(130, 430, 700, 60); // Đặt vị trí xuất hiện của title
        add(description2);
        
        signin= new MyButton("Sign up", 20);  
        signin.setBackgroundColor(Color.decode("#339900")); // Màu nền
        signin.setPressedColor(new Color(0, 100, 90)); // Màu khi nhấn
        signin.setHoverColor(new Color(0, 180, 150)); // Màu khi rê chuột vào
        signin.setBounds(250, 530, 150, 35);
        signin.setFont(new Font("Times New Roman", Font.BOLD, 18));
        signin.setForeground(Color.WHITE);
        add(signin);
        signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                        // Chuyển đổi giữa Sign up và Sign in
               if (isSignUp) {
                   signin.setText("Sign in"); // Đổi thành Sign in
               } else {
                   signin.setText("Sign up"); // Đổi thành Sign up
               }
               isSignUp = !isSignUp; // Đảo trạng thái
                
                event.actionPerformed(e); 
            }
        });
        
    }
  
}
