package com.Admin.insurance.DAO;

import com.Admin.insurance.DTO.DTO_Insurance;
import com.Admin.insurance.DTO.DTO_InsuranceDetails;
import com.ComponentandDatabase.Database_Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.format.DateTimeFormatter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;

public class DAO_Warranty {

    public boolean insertBillWarranty(DTO_Insurance insurance) throws SQLException {
        String sql = "INSERT INTO Insurance (Insurance_No, Admin_ID, Customer_ID, Start_Date_Insurance, End_Date_Insurance) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, insurance.getInsuranceNo());
            pst.setString(2, insurance.getAdminId());
            pst.setString(3, insurance.getCustomerId());
            pst.setDate(4, java.sql.Date.valueOf(insurance.getStartDateInsurance()));
            pst.setDate(5, java.sql.Date.valueOf(insurance.getEndDateInsurance()));

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Trả về `true` nếu thành công, `false` nếu thất bại
        } catch (SQLException e) {
            System.err.println("Error inserting warranty bill: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }
    }
    
    public boolean insertBillWarrantyDetails(DTO_InsuranceDetails insuranceDetails) throws SQLException {
        String sql = "INSERT INTO Insurance_Details (Insurance_No, Admin_ID, Customer_ID, Product_ID, IMEI_No, Description, Date_Insurance, Time_Insurance) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, insuranceDetails.getInsuranceNo());
            pst.setString(2, insuranceDetails.getAdminId());
            pst.setString(3, insuranceDetails.getCustomerId());
            pst.setString(4, insuranceDetails.getProductId());
            pst.setString(5, insuranceDetails.getiMeiNo());
            pst.setString(6, insuranceDetails.getDescription());
            pst.setDate(7, Date.valueOf(insuranceDetails.getDateInsurance()));  // Chuyển đổi LocalDate sang SQL Date
            pst.setTime(8, Time.valueOf(insuranceDetails.getTimeInsurance()));  // Chuyển đổi LocalTime sang SQL Time

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0; // Trả về `true` nếu thành công, `false` nếu thất bại
        } catch (SQLException e) {
            System.err.println("Error inserting warranty bill details: " + e.getMessage());
            throw e; // Rethrow lỗi để xử lý ở cấp cao hơn
        }
    }
    
    public List<DTO_Insurance> getAllInsurance() throws SQLException {
        String sql = "SELECT * FROM Insurance";
        List<DTO_Insurance> insuranceList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String insuranceNo = rs.getString("Insurance_No");
                String adminId = rs.getString("Admin_ID");
                String customerId = rs.getString("Customer_ID");
                LocalDate startDateInsurance = rs.getDate("Start_Date_Insurance").toLocalDate();
                LocalDate endDateInsurance = rs.getDate("End_Date_Insurance").toLocalDate();

                // Tạo đối tượng DTO_Insurance và thêm vào danh sách
                DTO_Insurance insurance = new DTO_Insurance(
                    insuranceNo, adminId, customerId, startDateInsurance, endDateInsurance
                );
                insuranceList.add(insurance);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all insurance records: " + e.getMessage());
            throw e; // Ném lỗi lên để xử lý ở cấp cao hơn
        }

        return insuranceList;
    }

    
    public List<DTO_InsuranceDetails> getAllInsuranceDetails() throws SQLException {
        String sql = "SELECT * FROM Insurance_Details";
        List<DTO_InsuranceDetails> insuranceDetailsList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String insuranceNo = rs.getString("Insurance_No");
                String adminId = rs.getString("Admin_ID");
                String customerId = rs.getString("Customer_ID");
                String productId = rs.getString("Product_ID");
                String iMeiNo = rs.getString("IMei_No");
                String description = rs.getString("Description");
                LocalDate dateInsurance = rs.getDate("Date_Insurance").toLocalDate();
                LocalTime timeInsurance = rs.getTime("Time_Insurance").toLocalTime();

                // Tạo đối tượng DTO_InsuranceDetails và thêm vào danh sách
                DTO_InsuranceDetails insuranceDetails = new DTO_InsuranceDetails(
                    insuranceNo, adminId, customerId, productId, 
                    iMeiNo, description, dateInsurance, timeInsurance
                );
                insuranceDetailsList.add(insuranceDetails);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all insurance details: " + e.getMessage());
            throw e;
        }

        return insuranceDetailsList;
    }
    
    
    public List<DTO_InsuranceDetails> searchInsuranceDetails(String searchType, String keyword) throws SQLException {
        String sql;
        List<DTO_InsuranceDetails> searchResults = new ArrayList<>();

        // Xây dựng câu SQL tùy thuộc vào loại tìm kiếm
        switch (searchType) {
            case "Invoice.No":
                sql = "SELECT * FROM Insurance_Details WHERE Insurance_No LIKE ?";
                break;
            case "Admin.ID":
                sql = "SELECT * FROM Insurance_Details WHERE Admin_ID LIKE ?";
                break;
            case "Customer.ID":
                sql = "SELECT * FROM Insurance_Details WHERE Customer_ID LIKE ?";
                break;
            case "IMEI.No":
                sql = "SELECT * FROM Insurance_Details WHERE IMei_No LIKE ?";
                break;
            case "Date":
                sql = "SELECT * FROM Insurance_Details WHERE Date_Insurance = ?";
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho câu truy vấn
            if (searchType.equals("Date")) {
                // Xử lý đặc biệt cho trường hợp tìm kiếm theo ngày
                try {
                    LocalDate searchDate = LocalDate.parse(keyword);
                    pst.setDate(1, Date.valueOf(searchDate));
                } catch (Exception e) {
                    throw new SQLException("Invalid date format. Please use yyyy-MM-dd");
                }
            } else {
                // Thêm % để tìm kiếm phần tử chứa từ khóa
                pst.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTO_InsuranceDetails details = new DTO_InsuranceDetails(
                        rs.getString("Insurance_No"),
                        rs.getString("Admin_ID"),
                        rs.getString("Customer_ID"),
                        rs.getString("Product_ID"),
                        rs.getString("IMei_No"),
                        rs.getString("Description"),
                        rs.getDate("Date_Insurance").toLocalDate(),
                        rs.getTime("Time_Insurance").toLocalTime()
                    );
                    searchResults.add(details);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching insurance details: " + e.getMessage());
            throw e;
        }

        return searchResults;
    }
    
   public boolean exportToExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo sheet cho Insurance
            createInsuranceSheet(workbook);

            // Tạo sheet cho Insurance_Details
            createInsuranceDetailsSheet(workbook);

            // Ghi file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                return true;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createInsuranceSheet(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Insurance");

        // Tạo header
        String[] headers = {"Insurance No", "Admin ID", "Customer ID", "Start Date", "End Date"};
        createHeaderRow(workbook, sheet, headers);

        // Lấy dữ liệu từ database
        List<DTO_Insurance> insurances = getAllInsurance();

        // Định dạng dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Định dạng ngày tháng
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(dataStyle);
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));

        // Thêm dữ liệu
        int rowNum = 1;
        for (DTO_Insurance insurance : insurances) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(insurance.getInsuranceNo());
            row.createCell(1).setCellValue(insurance.getAdminId());
            row.createCell(2).setCellValue(insurance.getCustomerId());

            // Xử lý ngày bắt đầu
            Cell startDateCell = row.createCell(3);
            if (insurance.getStartDateInsurance() != null) {
                startDateCell.setCellValue(Date.valueOf(insurance.getStartDateInsurance()));
                startDateCell.setCellStyle(dateStyle);
            } else {
                startDateCell.setCellValue("N/A");
            }

            // Xử lý ngày kết thúc
            Cell endDateCell = row.createCell(4);
            if (insurance.getEndDateInsurance() != null) {
                endDateCell.setCellValue(Date.valueOf(insurance.getEndDateInsurance()));
                endDateCell.setCellStyle(dateStyle);
            } else {
                endDateCell.setCellValue("N/A");
            }

            // Áp dụng style cho các ô còn lại
            for (int i = 0; i < 3; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createInsuranceDetailsSheet(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Insurance_Details");

        // Tạo header
        String[] headers = {"Insurance No", "Admin ID", "Customer ID", "Product ID", "IMEI No", "Description", "Date Insurance", "Time Insurance"};
        createHeaderRow(workbook, sheet, headers);

        // Lấy dữ liệu từ database
        List<DTO_InsuranceDetails> details = getAllInsuranceDetails();

        // Định dạng dữ liệu
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // Định dạng ngày tháng
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(dataStyle);
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));

        // Định dạng thời gian
        CellStyle timeStyle = workbook.createCellStyle();
        timeStyle.cloneStyleFrom(dataStyle);
        timeStyle.setDataFormat(workbook.createDataFormat().getFormat("HH:mm:ss"));

        // Thêm dữ liệu
        int rowNum = 1;
        for (DTO_InsuranceDetails detail : details) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(detail.getInsuranceNo());
            row.createCell(1).setCellValue(detail.getAdminId());
            row.createCell(2).setCellValue(detail.getCustomerId());
            row.createCell(3).setCellValue(detail.getProductId());
            row.createCell(4).setCellValue(detail.getiMeiNo());
            row.createCell(5).setCellValue(detail.getDescription());

            // Xử lý ngày bảo hiểm
            Cell dateCell = row.createCell(6);
            if (detail.getDateInsurance() != null) {
                dateCell.setCellValue(Date.valueOf(detail.getDateInsurance()));
                dateCell.setCellStyle(dateStyle);
            } else {
                dateCell.setCellValue("N/A");
            }

            // Xử lý thời gian bảo hiểm
            Cell timeCell = row.createCell(7);
            if (detail.getTimeInsurance() != null) {
                // Chuyển LocalTime sang java.sql.Time
                timeCell.setCellValue(Time.valueOf(detail.getTimeInsurance()));
                timeCell.setCellStyle(timeStyle);
            } else {
                timeCell.setCellValue("N/A");
            }

            // Áp dụng style cho các ô còn lại
            for (int i = 0; i < 6; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
    
    
    
}

