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

@MultipartConfig
public class AddCategoryServlet extends HttpServlet {
    private final UserDAO udao = new UserDAO();
    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int brandid = Integer.parseInt(request.getParameter("brandid"));
        String name = request.getParameter("categoryname");
        String priceString = request.getParameter("price");
        String description = request.getParameter("description");

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

        boolean hasError = false;
        String errorMessage = "";

        // Validate category name length
        if (name.length() > 40) {
            errorMessage = "Tên danh mục không được quá 40 ký tự.";
            hasError = true;
        } else if (!priceString.matches("\\d+(\\.\\d+)?")) {  // Validate price format
            errorMessage = "Giá không hợp lệ.";
            hasError = true;
        } else {
            double price = Double.parseDouble(priceString);
            if (description.length() > 255) {  // Validate description length
                errorMessage = "Mô tả không được quá 255 ký tự.";
                hasError = true;
            } else if (pcdao.checkExistedPrice(price, -1, brandid)) {  // Check if price already exists
                errorMessage = "Giá của danh mục này đã tồn tại.";
                hasError = true;
            } else {
                // No validation errors, proceed to add the category
                pcdao.addCategory(name, price, description, brandid, userid);
                request.setAttribute("success", "Danh mục này được thêm thành công.");
                request.getRequestDispatcher("productcategories?brandid=" + brandid).forward(request, response);
                return;
            }
        }

        if (hasError) {
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("productcategories?brandid=" + brandid).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
