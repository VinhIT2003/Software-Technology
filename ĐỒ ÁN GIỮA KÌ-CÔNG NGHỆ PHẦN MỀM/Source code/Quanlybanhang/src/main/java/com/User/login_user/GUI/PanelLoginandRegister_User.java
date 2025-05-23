
package com.User.login_user.GUI;

import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.CustomDialog;
import com.ComponentandDatabase.Components.MyButton;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.util.Date;
import com.User.login_user.BUS.BusAccount_cus;
import com.User.login_user.DTO.DTOAccount_cus;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.User.dashboard_user.GUI.Dashboard_user;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class PanelLoginandRegister_User extends javax.swing.JLayeredPane {
    private CardLayout cardLayout;
    private JPanel container;
    private MyTextField txtIDCard;
    private MyTextField txtFullName;
    private JRadioButton rdoMale;
    private JRadioButton rdoFemale;
    private JDateChooser dateOfBirth;
    private MyTextField txtEmail;
    public static MyTextField txtEmailLogin;
    private MyTextField txtContact;
    private JTextArea txtAddress;
    private MyTextField txtPassword;
    private MyTextField txtPasswordLogin;
    private ButtonGroup genderGroup;

    private BusAccount_cus busAccount;
    private CustomDialog cs;
    private static int mouseX, mouseY; // Biến lưu vị trí chuột

    public PanelLoginandRegister_User() {
        initComponents();
        initLogin();
        initRegister();
        login.setVisible(true);
        register.setVisible(false);
      
    }
    private void initRegister() {
        // Đặt layout của panel là null (không sử dụng layout manager)
        register.setLayout(null);

        // Tạo label "Create Account" và căn giữa
        JLabel label_title = new JLabel("Register Account");
        label_title.setFont(new Font("Times new roman", Font.BOLD, 30));
        label_title.setForeground(new Color(7, 164, 121));

        // Đặt tọa độ và kích thước cho label
        label_title.setBounds(160, 30, 280, 40); 
        register.add(label_title);
        
        // Tạo label "Create Account" và căn giữa
        JLabel lbl_idcard = new JLabel("ID Card");
        lbl_idcard.setFont(new Font("goudy old style", Font.PLAIN, 20));
        lbl_idcard.setForeground(Color.BLACK);

        // Đặt tọa độ và kích thước cho label
        lbl_idcard.setBounds(60, 100, 200, 30); 
        register.add(lbl_idcard);
        
        //   Tạo MyTextField cho ID Card
        txtIDCard = new MyTextField();
        txtIDCard.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
        txtIDCard.setHint("Enter your ID Card");
        txtIDCard.setBorder(BorderFactory.createLineBorder(new Color(7, 164, 121), 2));
        txtIDCard.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\ID_card.jpg");

       // Đặt tọa độ và kích thước cho MyTextField
        txtIDCard.setBounds(160, 100, 200, 35);
        register.add(txtIDCard);
        
        //Label Full Name
        JLabel lbl_fullname = new JLabel("Full Name");
        lbl_fullname.setFont(new Font("goudy old style", Font.PLAIN, 20));
        lbl_fullname.setForeground(Color.BLACK);

        // Đặt tọa độ và kích thước cho label
        lbl_fullname.setBounds(60, 170, 200, 35); 
        register.add(lbl_fullname);
        
         // Tạo MyTextField cho tên đăng nhập (email)
        txtFullName= new MyTextField();
        txtFullName.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
        txtFullName.setHint("Enter your full name");
        txtFullName.setBorder(BorderFactory.createLineBorder(new Color(7, 164, 121), 2));
        txtFullName.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\mail.png");
        // Đặt tọa độ và kích thước cho MyTextField
        txtFullName.setBounds(160, 170, 220, 35);
        register.add(txtFullName);

        // Tạo label "Gender"
       JLabel lblGender = new JLabel("Gender");
       lblGender.setFont(new Font("goudy old style", Font.PLAIN, 20));
       lblGender.setForeground(Color.BLACK);
       lblGender.setBounds(60, 240, 100, 30); // Đặt vị trí bên trái MyTextField
       register.add(lblGender);
       
        // Tạo RadioButton "Male"
        rdoMale = new JRadioButton("Male");
        rdoMale.setFont(new Font("Arial", Font.PLAIN, 16));
        rdoMale.setBounds(160, 240, 70, 30);
        rdoMale.setBackground(Color.WHITE);
        register.add(rdoMale);
//
//        // Tạo RadioButton "Female"
        rdoFemale = new JRadioButton("Female");
        rdoFemale.setFont(new Font("Arial", Font.PLAIN, 16));
        rdoFemale.setBounds(250, 240, 80, 30);
        rdoFemale.setBackground(Color.WHITE);
        register.add(rdoFemale);

        // Nhóm hai RadioButton vào một ButtonGroup để đảm bảo chỉ có thể chọn một
        genderGroup = new ButtonGroup();
        genderGroup.add(rdoMale);
        genderGroup.add(rdoFemale);
        
        // Tạo label "DOB"
       JLabel lblDOB = new JLabel("Date of Birth");
       lblDOB.setFont(new Font("goudy old style", Font.PLAIN, 20));
       lblDOB.setForeground(Color.BLACK);
       lblDOB.setBounds(60,300, 120, 30); // Đặt vị trí bên trái MyTextField
       register.add(lblDOB);

                // Tạo JDateChooser
         dateOfBirth = new JDateChooser();
         dateOfBirth.setFont(new Font("Times New Roman", Font.PLAIN, 18));
         dateOfBirth.setDateFormatString("dd/MM/yyyy");  // Định dạng ngày theo kiểu dd/MM/yyyy
         dateOfBirth.setBounds(180, 295, 180, 35);
         dateOfBirth.setBackground(Color.WHITE);

         // Thêm JDateChooser vào panel register (thay vì add(dateChooser))
         register.add(dateOfBirth);
         
              // Tạo label "Email"
       JLabel lblEmail = new JLabel("Email");
       lblEmail.setFont(new Font("goudy old style", Font.PLAIN, 20));
       lblEmail.setForeground(Color.BLACK);
       lblEmail.setBounds(60,363, 120, 30); // Đặt vị trí bên trái MyTextField
       register.add(lblEmail);

      // Tạo MyTextField cho tên đăng nhập (email)
        txtEmail = new MyTextField();
        txtEmail.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
        txtEmail.setHint("Enter your Email");
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(7, 164, 121), 2));
        txtEmail.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\mail.png");

            // Đặt tọa độ và kích thước cho MyTextField
        txtEmail.setBounds(150, 358, 250, 35);
        register.add(txtEmail);
        
           // Tạo label "Contact"
       JLabel lblContact = new JLabel("Contact");
       lblContact.setFont(new Font("goudy old style", Font.PLAIN, 20));
       lblContact.setForeground(Color.BLACK);
       lblContact.setBounds(60,425, 120, 30); // Đặt vị trí bên trái MyTextField
       register.add(lblContact);
       
       // Tạo MyTextField cho Contact 
        txtContact = new MyTextField();
        txtContact.setTextFont(new Font("Times new roman", Font.PLAIN, 16));
        txtContact.setHint("Enter your contact");
        txtContact.setBorder(BorderFactory.createLineBorder(new Color(7, 164, 121), 2));
        txtContact.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\contact.png");
           // Đặt tọa độ và kích thước cho MyTextField
        txtContact.setBounds(150, 423, 230, 35);
        register.add(txtContact);
        
            // Tạo label "Address"
      JLabel lblAddress = new JLabel("Address");
      lblAddress.setFont(new Font("goudy old style", Font.PLAIN, 20));
      lblAddress.setForeground(Color.BLACK);
      lblAddress.setBounds(60, 500, 120, 30); // Đặt vị trí bên trái JTextField
      register.add(lblAddress);

            // Tạo JTextArea giống CTkTextbox (hỗ trợ nhiều dòng)
        txtAddress = new JTextArea();
        txtAddress.setFont(new Font("Times new roman", Font.PLAIN, 16));
        txtAddress.setBorder(BorderFactory.createLineBorder(new Color(7, 164, 121), 2));
        txtAddress.setLineWrap(true); // Xuống dòng tự động khi hết chiều rộng
        txtAddress.setWrapStyleWord(true); // Xuống dòng theo từ (không cắt từ)

        // Đặt JScrollPane để có thể cuộn nếu nội dung dài
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        scrollAddress.setBounds(150, 480, 280, 80); // Điều chỉnh kích thước để hiển thị nhiều dòng
        register.add(scrollAddress);
        
       // Tạo label "Password"
      JLabel lblpass = new JLabel("Password");
      lblpass.setFont(new Font("goudy old style", Font.PLAIN, 20));
      lblpass.setForeground(Color.BLACK);
      lblpass.setBounds(60, 585, 120, 30); // Đặt vị trí bên trái JTextField
      register.add(lblpass);
     
            // Tạo đối tượng MyTextField
     txtPassword = new MyTextField();
     txtPassword.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
     txtPassword.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\pass.png");
     txtPassword.setHint("Enter password");
     txtPassword.setEnabled(true);

     // Màu viền và độ dày viền
     Color borderColor = new Color(7, 164, 121);
     int borderThickness = 2;

     // Gọi phương thức tạo password field có nút ẩn/hiện và truyền màu viền động
     JPanel passwordPanel = txtPassword.createPasswordFieldWithEyeButton(
         "Enter password",
         "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\hidepass.png",
         "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\showpass.png",
         borderColor,
         borderThickness
     );

     // Cập nhật vị trí
     passwordPanel.setBounds(155, 585, 230, 35);

     // Thêm vào giao diện
     register.add(passwordPanel);

       
        MyButton signup = new MyButton("SIGN UP", 20);
        signup.setBackgroundColor(new Color(0, 150, 136)); // Màu nền
        signup.setPressedColor(new Color(0, 100, 90)); // Màu khi nhấn
        signup.setHoverColor(new Color(0, 180, 150)); // Màu khi rê chuột vào
        signup.setBounds(200, 650, 200, 35);
        signup.setFont(new Font("Times New Roman", Font.BOLD, 18));
        signup.setForeground(Color.WHITE);

        // Thêm vào panel
        register.add(signup);

        // Xử lý sự kiện khi nhấn nút
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                busAccount= new BusAccount_cus();
                String idCard = txtIDCard.getText().trim();
                String fullName = txtFullName.getText().trim();
                String gender = rdoMale.isSelected() ? "Male" : (rdoFemale.isSelected() ? "Female" : "");
                        // Kiểm tra ngày sinh
                java.util.Date utilDob = dateOfBirth.getDate();
                if (utilDob == null) {
                    CustomDialog.showError("Please fill in all required fields! ");
                    return; // Dừng sự kiện nếu ngày sinh không hợp lệ
                }
                java.sql.Date sqlDob = new java.sql.Date(utilDob.getTime());
                String email = txtEmail.getText().trim();
                String contact = txtContact.getText().trim();
                String address = txtAddress.getText().trim();
                String password = txtPassword.getPasswordText().trim();
                String status = "Inactive";
                DTOAccount_cus DTOAccount= new DTOAccount_cus(idCard, fullName, gender, sqlDob, email, contact, address, password, status);
                busAccount.registerCustomer(DTOAccount);

            }
        });
        
    }
    
        private void initLogin(){
      // Đặt layout của panel là null (không sử dụng layout manager)
            login.setLayout(null);

            // Tạo label "Create Account" và căn giữa
            JLabel label_title = new JLabel("Sign In");
            label_title.setFont(new Font("sansserif", Font.BOLD, 30));
            label_title.setForeground(new Color(7, 164, 121));

            // Đặt tọa độ và kích thước cho label
            label_title.setBounds(180, 230, 280, 40); 
            login.add(label_title);
            
             // Tạo đối tượng MyTextField email
            txtEmailLogin = new MyTextField();
            txtEmailLogin.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
            txtEmailLogin.setHint("Email");
            txtEmailLogin.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\mail.png");

            txtEmailLogin.setBorder(null);
            txtEmailLogin.setBackgroundColor(Color.decode("#E0F2E9")); // ✅ Gọi hàm mới để đổi màu
            txtEmailLogin.setBounds(100, 300, 280, 35);
            login.add(txtEmailLogin);

             // Tạo đối tượng MyTextField password
            txtPasswordLogin = new MyTextField();
            txtPasswordLogin.setBorder(null);
            Color backgroundColor = Color.decode("#E0F2E9"); // Màu xanh nhạt
            txtPasswordLogin.setBackgroundColor(backgroundColor);
            txtPasswordLogin.setTextFont(new Font("Times New Roman", Font.PLAIN, 16));
            txtPasswordLogin.setPreFixIcon("D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\pass.png");
            txtPasswordLogin.setHint("Enter password");

            // Gọi phương thức tạo password field có nút ẩn/hiện
            JPanel passwordPanel = txtPasswordLogin.createPasswordFieldWithEyeButton(
                "Password", 
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\hidepass.png",
                "D:\\Đồ án Java\\Quanlybanhang\\src\\main\\resources\\Icons\\User_icon\\showpass.png",
                backgroundColor,   // Màu viền đồng bộ
                0 // Không cần viền
            );

            // Đồng bộ màu nền cho toàn bộ passwordPanel
            passwordPanel.setOpaque(true);
            passwordPanel.setBackground(backgroundColor);
            passwordPanel.setBounds(100, 350, 280, 35);

            // Thêm vào giao diện
            login.add(passwordPanel);

           
        MyButton forget = new MyButton("Forgot password ?", 20);
        forget.setBackgroundColor(Color.WHITE); // Màu nền trắng
        forget.setPressedColor(new Color(200, 200, 200)); // Màu xám nhạt khi nhấn
        forget.setHoverColor(new Color(220, 220, 220)); // Màu xám sáng khi rê chuột vào
        forget.setBounds(150, 420, 200, 35);
        forget.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));

        forget.setForeground(new Color(0, 150, 136)); // Màu chữ xanh đậm để dễ nhìn
        forget.addActionListener((e) -> {
            String email= txtEmailLogin.getText().strip();
            
            if (email.isEmpty()) {
              CustomDialog.showError("Please enter your Email before proceeding!");
                return; // không thực hiện tiếp
            }

            busAccount = new BusAccount_cus();
            String sentOtp = busAccount.sentOTP(email);

            if (sentOtp != null) {
                OTPFrame_cus OTP = new OTPFrame_cus();
                OTP.setVisible(true);
            } else {
                CustomDialog.showError("Failed to send OTP. Please check your Email or try again later.");
            }
        });

        // Thêm vào panel
        login.add(forget);
        
        //           
        MyButton signin = new MyButton("SIGN IN", 20);
        signin.setBackgroundColor(new Color(0, 150, 136)); // Màu nền
        signin.setPressedColor(new Color(0, 100, 90)); // Màu khi nhấn
        signin.setHoverColor(new Color(0, 180, 150)); // Màu khi rê chuột vào
        signin.setBounds(130, 480, 230, 35);
        signin.setFont(new Font("Times New Roman", Font.BOLD, 18));
        signin.setForeground(Color.WHITE);

        // Khi nhấn vào nút
        signin.addActionListener((e) -> {
            String email = txtEmailLogin.getText().strip();
            String password = txtPasswordLogin.getPasswordText().strip();

           busAccount = new BusAccount_cus();  // ✅ Tạo BUS mới

            DTOAccount_cus account = busAccount.login(email, password);  // ✅ Gọi login
            if (account != null) {
                String fullName = busAccount.getName();  // ✅ Lấy tên từ BUS
                CustomDialog.showSuccess("Welcome " + fullName + "!");
                
                JFrame Login_User = (JFrame) SwingUtilities.getWindowAncestor(signin);
                Login_User.setVisible(false); // hoặc dispose nếu không cần
                
                Dashboard_user dashboard= new Dashboard_user();
                dashboard.setVisible(true);
            }
            // Nếu account == null thì đã có CustomDialog lỗi bên trong BUS rồi
        });
        
        login.add(signin);
 
    }
        
    public void showRegister(boolean show){
        if(show){
            login.setVisible(true);
            register.setVisible(false);
            
         }
        else{
            login.setVisible(false);
            register.setVisible(true);
        }      
 }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
