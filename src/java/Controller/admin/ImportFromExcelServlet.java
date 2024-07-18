package Controller.admin;

import DAL.ProductCardDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import java.io.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@MultipartConfig
public class ImportFromExcelServlet extends HttpServlet {

    private final ProductCardDAO pcdao = new ProductCardDAO();
    private static final long serialVersionUID = 1L;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private final UserDAO udao = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pcid = request.getParameter("pcid");

        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");

        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userid = (user != null) ? user.getUser().getID() : gguser.getUser().getID();

        if (!udao.checkAdmin(userid)) {
            response.sendRedirect("login.jsp");
            return;
        }

        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "No file uploaded!");
            request.getRequestDispatcher("productcard?pcid=" + pcid).forward(request, response);
            return;
        }

        // Backend file type validation
        String fileName = filePart.getSubmittedFileName();
        if (!isValidExcelFile(fileName)) {
            request.setAttribute("error", "Please select a valid Excel file (.xls or .xlsx).");
            request.getRequestDispatcher("productcard?pcid=" + pcid).forward(request, response);
            return;
        }

        try (InputStream fileContent = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Skip header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Check if the row is empty
                if (isEmptyRow(row)) {
                    continue; // Skip empty rows
                }

                // Check if cells exist before accessing them
                Cell productCategoryIdCell = row.getCell(0);
                Cell seriCell = row.getCell(1);
                Cell codeCell = row.getCell(2);
                Cell dateCell = row.getCell(3);

                if (productCategoryIdCell == null || seriCell == null || codeCell == null || dateCell == null) {
                    // Handle missing cell case
                    request.setAttribute("error", "One or more cells are missing in row " + (row.getRowNum() + 1));
                    request.getRequestDispatcher("productcard?pcid=" + pcid).forward(request, response);
                    return;
                }

                int productCategoryId = (int) productCategoryIdCell.getNumericCellValue();
                String seri = seriCell.getStringCellValue();
                String code = codeCell.getStringCellValue();

                // Handle date cell
                Date expiredDate;
                if (dateCell.getCellType() == CellType.STRING) {
                    // If the cell contains a date as a string, parse it
                    expiredDate = dateFormat.parse(dateCell.getStringCellValue());
                } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                    // If the cell contains a numeric value formatted as a date
                    expiredDate = dateCell.getDateCellValue();
                } else {
                    throw new IllegalStateException("Unexpected cell type: " + dateCell.getCellType());
                }

                // Convert the java.util.Date to java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(expiredDate.getTime());

                // Check if seri already exists
                if (pcdao.checkSeriExist(seri, -1)) {
                    request.setAttribute("error", "Seri đã tồn tại.");
                    // Check if code already exists
                } else if (pcdao.checkCodeExist(code, -1)) {
                    request.setAttribute("error", "Code đã tồn tại.");
                } else {
                    // Call your method to add card to the database
                    pcdao.addCard(productCategoryId, seri, code, sqlDate.toString(), userid);
                }
            }

            request.setAttribute("success", "File uploaded and processed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing file: " + e.getMessage());
        }

        request.getRequestDispatcher("productcard?pcid=" + pcid).forward(request, response);
    }

    private boolean isEmptyRow(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false; // Row is not empty
            }
        }
        return true; // Row is empty
    }

    private boolean isValidExcelFile(String fileName) {
        return fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
