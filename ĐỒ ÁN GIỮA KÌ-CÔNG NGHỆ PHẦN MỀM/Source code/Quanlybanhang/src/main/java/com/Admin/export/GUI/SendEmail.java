package com.Admin.export.GUI;

import com.ComponentandDatabase.Components.CustomDialog;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.User.dashboard_user.DTO.DTOProfile_cus;
import com.Admin.export.BUS.BUS_OrderDetail;
import java.math.RoundingMode;
import java.util.List;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
public class SendEmail {
     private static final String SMTP_HOST = "smtp.gmail.com";
     private static final String SMTP_PORT = "587";
     private static final String EMAIL_USERNAME = "zquangvinh359@gmail.com";
     private static final String EMAIL_PASSWORD = "trwa fmjj zlad vtdg";
     
     // 🔹 Khai báo biến toàn cục
    private static BUS_OrderDetail busOrderDetail;

    // ✅ Khởi tạo biến busOrderDetail trong phương thức static
    static {
        busOrderDetail = new BUS_OrderDetail();
    }
  
    public static boolean sendInvoiceEmail(DTOProfile_cus customer, List<Object[]> orderItems,
                                         double discount, String invoiceNo) {
      // 1. Validate input data
      if (customer == null) {
          System.err.println("Error: Customer information does not exist");
          CustomDialog.showError("Customer information not found");
          return false;
      }

      // 🔹 Check if customer.getEmail() returns null
      String customerEmail = customer.getEmail() != null ? customer.getEmail().trim() : "";
      if (customerEmail.isEmpty()) {
          System.err.println("Error: Customer " + customer.getFullName() + " does not have an email");
          CustomDialog.showError("The customer has not registered an email\nCannot send invoice via email");
          return false;
      }

      // 2. Validate email format
      if (!isValidEmail(customerEmail)) {
          System.err.println("Error: Invalid email: " + customerEmail);
          CustomDialog.showError("Invalid customer email: " + customerEmail);
          return false;
      }

      try {
          // 🔹 Verify that the customer email data exists
          System.out.println("Sending email to: " + customerEmail);


          // Prepare email properties
          Properties props = new Properties();
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", SMTP_HOST);
          props.put("mail.smtp.port", SMTP_PORT);
          props.put("mail.smtp.ssl.protocols", "TLSv1.2");
          props.put("mail.debug", "true");

          // Create session with authentication
          Session session = Session.getInstance(props, new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
              }
          });

          // Create email message
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(EMAIL_USERNAME));
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
          message.setSubject("Your Invoice: " + invoiceNo + " - Thank you for your purchase!");

          // Create email content
          String htmlContent = buildEmailContent(customer, orderItems, discount, invoiceNo);
          message.setContent(htmlContent, "text/html; charset=utf-8");

          // Send email
          Transport.send(message);
          System.out.println("Email sent successfully to " + customerEmail);
          return true;

      } catch (MessagingException e) {
          System.err.println("Failed to send email: " + e.getMessage());
          e.printStackTrace();
          return false;
      } catch (Exception e) {
          System.err.println("Unexpected error: " + e.getMessage());
          e.printStackTrace();
          return false;
      }
  }

    
    private static String buildEmailContent(DTOProfile_cus customer, List<Object[]> orderItems, 
                                         double discount, String invoiceNo) {
       StringBuilder html = new StringBuilder();

       // Email header with increased max-width
       html.append("<html><head><meta charset='UTF-8'></head>");
       html.append("<body style='font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0;'>");
       html.append("<div style='max-width: 700px; margin: 20px auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>");

       // Thank you message (unchanged)
       html.append("<h2 style='color: #2c3e50; margin-top: 0;'>Dear ")
          .append(customer.getFullName() != null ? customer.getFullName() : "Valued Customer")
          .append(",</h2>");
       html.append("<p style='margin-bottom: 10px;'>Thank you for shopping with us! Your order will be delivered within the next few days.</p>");
       html.append("<p style='margin-bottom: 20px;'>Please keep your phone handy for delivery updates.</p>");

       // Invoice summary (unchanged)
       html.append("<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px;'>");
       html.append("<h3 style='color: #2c3e50; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 5px;'>Invoice Summary</h3>");

       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
       html.append("<table style='width: 100%; border-collapse: collapse;'>");
       html.append("<tr><td style='padding: 5px;'><strong>Invoice No:</strong></td><td style='padding: 5px;'>").append(invoiceNo).append("</td></tr>");
       html.append("<tr><td style='padding: 5px;'><strong>Date:</strong></td><td style='padding: 5px;'>").append(dateFormat.format(new Date())).append("</td></tr>");

       // Calculate totals (unchanged)
       BigDecimal total = BigDecimal.ZERO;
       int totalItems = 0;
       BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
           BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
       );

       for (Object[] item : orderItems) {
           if (item == null || item.length < 5) continue;

           BigDecimal unitPrice = item[3] instanceof BigDecimal ? (BigDecimal)item[3] : BigDecimal.ZERO;
           int quantity = item[4] instanceof Number ? ((Number)item[4]).intValue() : 0;

           BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(quantity))
                                        .multiply(discountMultiplier);
           total = total.add(itemTotal);
           totalItems += quantity;
       }

       html.append("<tr><td style='padding: 5px;'><strong>Total Items:</strong></td><td style='padding: 5px;'>").append(totalItems).append("</td></tr>");
       html.append("<tr><td style='padding: 5px;'><strong>Discount:</strong></td><td style='padding: 5px;'>").append(discount).append("%</td></tr>");
       html.append("<tr><td style='padding: 5px;'><strong>Total Amount:</strong></td><td style='padding: 5px;'>").append(formatCurrency(total.toString())).append(" VND</td></tr>");
       html.append("</table></div>");

       // Order details with improved layout
       String currentOrderNo = orderItems.isEmpty() ? "" : orderItems.get(0)[0].toString();
       html.append("<div style='margin-top: 20px;'>");
       html.append("<h3 style='display: inline-block; color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 5px; margin: 0;'>Order Details</h3>");
       html.append("<span style='margin-left: 10px; font-weight: bold; color: #2c3e50;'>Order No: ").append(currentOrderNo).append("</span>");
       html.append("</div>");

       // Improved table with better column widths and text wrapping
       html.append("<table style='width: 100%; border-collapse: collapse; margin-bottom: 20px;'>");
       html.append("<colgroup>");
       html.append("<col style='width: 15%;'>");  // Product ID
       html.append("<col style='width: 35%;'>");  // Product Name (wider)
       html.append("<col style='width: 12%;'>");  // Quantity (slightly wider for full word)
       html.append("<col style='width: 19%;'>");  // Unit Price
       html.append("<col style='width: 19%;'>");  // Subtotal
       html.append("</colgroup>");

       // Table header with "Quantity" instead of "Qty"
       html.append("<thead><tr style='background-color: #f5f5f5;'>");
       html.append("<th style='padding: 10px 8px; text-align: left; border-bottom: 2px solid #ddd;'>Product ID</th>");
       html.append("<th style='padding: 10px 8px; text-align: left; border-bottom: 2px solid #ddd;'>Product Name</th>");
       html.append("<th style='padding: 10px 8px; text-align: center; border-bottom: 2px solid #ddd;'>Quantity</th>");
       html.append("<th style='padding: 10px 8px; text-align: right; border-bottom: 2px solid #ddd;'>Unit Price</th>");
       html.append("<th style='padding: 10px 8px; text-align: right; border-bottom: 2px solid #ddd;'>Subtotal</th>");
       html.append("</tr></thead><tbody>");

       for (Object[] item : orderItems) {
           if (item == null || item.length < 5) continue;

           String productID = item[2] != null ? item[2].toString() : "";
           String productName = busOrderDetail.getProductName(productID);

           BigDecimal unitPrice = item[3] instanceof BigDecimal ? (BigDecimal)item[3] : BigDecimal.ZERO;
           int quantity = item[4] instanceof Number ? ((Number)item[4]).intValue() : 0;
           BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity))
                                        .multiply(discountMultiplier);

           html.append("<tr>");
           html.append("<td style='padding: 10px 8px; border-bottom: 1px solid #eee; font-family: monospace;'>").append(productID).append("</td>");
           html.append("<td style='padding: 10px 8px; border-bottom: 1px solid #eee; white-space: normal; word-wrap: break-word;'>").append(productName != null ? productName : "").append("</td>");
           html.append("<td style='padding: 10px 8px; text-align: center; border-bottom: 1px solid #eee;'>").append(quantity).append("</td>");
           html.append("<td style='padding: 10px 8px; text-align: right; border-bottom: 1px solid #eee; font-family: monospace;'>").append(formatCurrency(unitPrice.toString())).append("</td>");
           html.append("<td style='padding: 10px 8px; text-align: right; border-bottom: 1px solid #eee; font-family: monospace;'>").append(formatCurrency(subtotal.toString())).append("</td>");
           html.append("</tr>");
       }
       html.append("</tbody></table>");

       // Footer (unchanged)
       html.append("<div style='margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee;'>");
       html.append("<p style='margin-bottom: 5px;'>If you have any questions about your order, please contact our customer service.</p>");
       html.append("<p style='margin: 0;'>Best regards,<br><strong>Laptop Vang</strong></p>");
       html.append("</div></div></body></html>");

       return html.toString();
   }
    private static String formatCurrency(String amount) {
        try {
            BigDecimal value = new BigDecimal(amount);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            return df.format(value);
        } catch (Exception e) {
            return amount;
        }
    }
    
    private static boolean isValidEmail(String email) {
        // Kiểm tra định dạng email cơ bản
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

}