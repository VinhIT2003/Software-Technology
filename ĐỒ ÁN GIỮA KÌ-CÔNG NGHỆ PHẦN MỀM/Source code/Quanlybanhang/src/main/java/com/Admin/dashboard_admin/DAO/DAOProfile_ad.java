
package com.Admin.dashboard_admin.DAO;

import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import com.Admin.dashboard_admin.DTO.DTOProfile_ad;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOProfile_ad {

    public DTOProfile_ad showProfile_ad(String adminID, JPanel panelUpload) {
        DTOProfile_ad profile = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT Admin_ID, Admin_Name, Gender, Email, Contact, Image FROM Admin WHERE Admin_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, adminID);
            rs = ps.executeQuery();

            if (rs.next()) {
                profile = new DTOProfile_ad(
                        rs.getString("Admin_ID"),
                        rs.getString("Admin_Name"),
                        rs.getString("Gender"),
                        rs.getString("Email"),
                        rs.getString("Contact"),
                        rs.getString("Image")
                );

                String imagePath = rs.getString("Image");
                if (imagePath != null && !imagePath.trim().isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        ImageIcon icon = new ImageIcon(
                                new ImageIcon(imageFile.getAbsolutePath()).getImage()
                                        .getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                        JLabel lblImage = new JLabel(icon);
                        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
                        lblImage.setVerticalAlignment(SwingConstants.CENTER);

                        panelUpload.removeAll();
                        panelUpload.add(lblImage, "pos 0.5al 0.5al");  // dùng MigLayout đúng cú pháp
                        panelUpload.setVisible(true);
                        panelUpload.revalidate();
                        panelUpload.repaint();
                    } else {
                        System.out.println("⚠ Ảnh không tồn tại tại: " + imagePath);
                    }
                } else {
                    System.out.println("⚠ Không có đường dẫn ảnh trong CSDL.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return profile;
    }

    
    public boolean updateProfile(DTOProfile_ad profile) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
          conn = DatabaseConnection.connect();

          String query = "SELECT * FROM Admin WHERE Admin_ID = ?";
          pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
          pstmt.setString(1, profile.getAdminID());

          rs = pstmt.executeQuery();

          if (rs.next()) {
              rs.updateString("Admin_Name", profile.getAdminName());
              rs.updateString("Gender", profile.getGender());
              rs.updateString("Email", profile.getEmail());
              rs.updateString("Contact", profile.getContact());

              // Giữ lại ảnh cũ nếu không update ảnh mới
              String newImage = profile.getImage();
              if (newImage != null && !newImage.trim().isEmpty()) {
                  rs.updateString("Image", newImage);
              }

              rs.updateRow(); // Cập nhật bản ghi
              return true;
          } else {
              return false; // Không tìm thấy AdminID
          }

      } catch (Exception e) {
          e.printStackTrace();
          return false;
      } finally {
          try {
              if (rs != null) rs.close();
              if (pstmt != null) pstmt.close();
              if (conn != null) conn.close();
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      }
  }

    public String getAdminName(String adminID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String adminName = null;

        try {
            conn = DatabaseConnection.connect();
            String sql = "SELECT Admin_Name FROM Admin WHERE Admin_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, adminID);
            rs = ps.executeQuery();

            if (rs.next()) {
                adminName = rs.getString("Admin_Name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các resource
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return adminName;
    }
  
}
