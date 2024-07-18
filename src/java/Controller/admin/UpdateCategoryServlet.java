package Controller.admin;

import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
public class UpdateCategoryServlet extends HttpServlet {

    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
    private final UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");

        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userid = (user != null) ? user.getUser().getID() : gguser.getUser().getID();
        
        if(!udao.checkAdmin(userid)){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String name = request.getParameter("categoryname");
        String priceString = request.getParameter("price");
        double price = Double.parseDouble(priceString);

        String description = request.getParameter("description");

        String discountString = request.getParameter("discount");
        double discount = Double.parseDouble(discountString);

        String discountFrom = request.getParameter("discountfrom");
        String discountTo = request.getParameter("discountto");
        int id = Integer.parseInt(request.getParameter("categoryid"));
        int brandid = Integer.parseInt(request.getParameter("brandid"));

        boolean hasError = false;
        String errorMessage = "";

        // Validate category name length
        if (name.length() > 40) {
            errorMessage = "Tên danh mục không được quá 40 ký tự.";
            hasError = true;
        } else if (!priceString.matches("\\d+(\\.\\d+)?")) {  // Validate price format
            errorMessage = "Giá không hợp lệ.";
            hasError = true;
        } else if (description.length() > 255) {  // Validate description length
            errorMessage = "Mô tả không được quá 255 ký tự.";
            hasError = true;
        } else if (!discountString.matches("\\d+(\\.\\d+)?")) {  // Validate discount format
            errorMessage = "Khuyến mãi không hợp lệ.";
            hasError = true;
        } else if (discount < 0 || discount > 30) {  // Validate discount range
            errorMessage = "Khuyến mãi phải từ 0 - 30%.";
            hasError = true;
        } else if (discount > 0) {
            // Validate discount dates if discount is greater than 0
            if (discountFrom == null || discountFrom.isEmpty()) {
                errorMessage = "Ngày khuyến mãi từ phải được điền.";
                hasError = true;
            } else if (discountTo == null || discountTo.isEmpty()) {
                errorMessage = "Ngày khuyến mãi đến phải được điền.";
                hasError = true;
            } else {
                // Parse dates and validate
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date discountFromDate = sdf.parse(discountFrom);
                    Date discountToDate = sdf.parse(discountTo);

                    // Get the start of today
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date todayStart = cal.getTime();

                    if (discountFromDate.before(todayStart)) {
                        errorMessage = "Ngày khuyến mãi từ không được là ngày trong quá khứ.";
                        hasError = true;
                    } else if (discountToDate.before(todayStart)) {
                        errorMessage = "Ngày khuyến mãi đến không được là ngày trong quá khứ.";
                        hasError = true;
                    } else if (discountToDate.before(discountFromDate)) {
                        errorMessage = "Ngày khuyến mãi đến phải lớn hơn hoặc bằng ngày khuyến mãi từ.";
                        hasError = true;
                    }
                } catch (ParseException e) {
                    errorMessage = "Ngày khuyến mãi không hợp lệ.";
                    hasError = true;
                }
            }
        }

        if (hasError) {
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("productcategories?brandid=" + brandid).forward(request, response);
        } else if (pcdao.checkExistedPrice(price, id, brandid)) {
            request.setAttribute("error", "Giá của danh mục này đã tồn tại.");
            request.getRequestDispatcher("productcategories?brandid=" + brandid).forward(request, response);
        } else {
            pcdao.updateCategory(id, name, price, description, discount, discountFrom, discountTo);
            request.setAttribute("success", "Danh mục này được cập nhật thành công.");
            request.getRequestDispatcher("productcategories?brandid=" + brandid).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
