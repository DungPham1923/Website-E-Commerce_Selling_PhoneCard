// Thu - Iter 3
package Controller.admin;

import DAL.BrandDAO;
import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

@MultipartConfig
public class UpdateCategoryStatusServlet extends HttpServlet {

    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
    private final BrandDAO bdao = new BrandDAO();
    private final UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int pcid = Integer.parseInt(request.getParameter("pcid"));
            int brandid = Integer.parseInt(request.getParameter("brandid"));
            String action = request.getParameter("action");

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

            boolean success = false;

            try {
                if (bdao.checkDeleteBrand(brandid)) {
                    success = false;
                }
                // Check the action and call the appropriate method
                if ("deactivate".equals(action) && !bdao.checkDeleteBrand(brandid)) {
                    pcdao.updateCategoryDeleteStatus(pcid, userid);
                    success = true;
                } else if ("activate".equals(action) && !bdao.checkDeleteBrand(brandid)) {
                    pcdao.updateCategoryActiveStatus(pcid, userid);
                    success = true;
                }
            } catch (Exception e) {
                System.out.println(e);
                success = false;
            }

            // Respond with success or failure
            if (success) {
                out.write("{\"success\": true}");
            } else {
                out.write("{\"success\": false, \"message\": \"Cập nhật trạng thái thất bại!\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        } finally {
            out.close();
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
