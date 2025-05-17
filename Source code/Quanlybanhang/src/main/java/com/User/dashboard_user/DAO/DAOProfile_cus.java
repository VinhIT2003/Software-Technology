package com.User.dashboard_user.DAO;

import com.ComponentandDatabase.Components.CustomDialog;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.ComponentandDatabase.Components.MyTextField;
import com.ComponentandDatabase.Components.MyCombobox;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Giả sử bạn đã có class ConnectDB để lấy Connection
import com.ComponentandDatabase.Database_Connection.DatabaseConnection; 

public class DAOProfile_cus {

    public void showProfile(String emailInput, 
                             MyTextField txtID, 
                             MyTextField txtFullName, 
                             MyCombobox<String> cmbGender, 
                             JDateChooser dateOfBirth, 
                             MyTextField txtEmail, 
                             MyTextField txtContact, 
                             JTextArea txtAddress) 
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT * FROM Customer WHERE Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, emailInput);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                DTOProfile_cus profile = new DTOProfile_cus();
                profile.setCustomerID(rs.getString("Customer_ID"));
                profile.setFullName(rs.getString("Full_Name"));
                profile.setGender(rs.getString("Gender"));
                profile.setDateOfBirth(rs.getDate("Date_Of_Birth"));
                profile.setEmail(rs.getString("Email"));
                profile.setContact(rs.getString("Contact"));
                profile.setAddress(rs.getString("Address"));

                // Đổ dữ liệu lên giao diện
                txtID.setText(profile.getCustomerID());
                txtFullName.setText(profile.getFullName());

                if ("Male".equalsIgnoreCase(profile.getGender())) {
                    cmbGender.setSelectedItem("Male");
                } else {
                    cmbGender.setSelectedItem("Female");
                }

                if (profile.getDateOfBirth() != null) {
                    dateOfBirth.setDate(profile.getDateOfBirth());
                } else {
                    dateOfBirth.setDate(null);
                }

                txtEmail.setText(profile.getEmail());
                txtContact.setText(profile.getContact());
                txtAddress.setText(profile.getAddress());
            } else {
                System.out.println("Không tìm thấy khách hàng với email: " + emailInput);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    
    public void updateProfile(MyTextField txtID, 
                               MyTextField txtFullName, 
                               MyCombobox<String> cmbGender, 
                               JDateChooser dateOfBirth, 
                               MyTextField txtEmail, 
                               MyTextField txtContact, 
                               JTextArea txtAddress) 
    {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "UPDATE Customer SET Full_Name = ?, Gender = ?, Date_Of_Birth = ?, Contact = ?, Address = ? WHERE Email = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, txtFullName.getText());
            ps.setString(2, cmbGender.getSelectedItem().toString());

            if (dateOfBirth.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(dateOfBirth.getDate());
                ps.setString(3, formattedDate);
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            ps.setString(4, txtContact.getText());
            ps.setString(5, txtAddress.getText());
            ps.setString(6, txtEmail.getText()); // WHERE Email = ?

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                CustomDialog.showSuccess("Imformation updated successfully !");
            } else {
               CustomDialog.showError("Imformation updated failure! Can't find the customer with this email.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    public String getCustomerID(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String customerID = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT Customer_ID FROM Customer WHERE Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                customerID = rs.getString("Customer_ID");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return customerID;
    }
    
}
