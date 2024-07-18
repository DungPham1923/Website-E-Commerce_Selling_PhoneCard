// Thu - Iter 3
package Controller.admin;

import DAL.BrandDAO;
import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.ProductCategories;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@MultipartConfig
public class ViewProductCategoriesServlet extends HttpServlet {

    private final BrandDAO bdao = new BrandDAO();
    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
    private final UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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

        String bid = request.getParameter("brandid");
        int brandid = Integer.parseInt(bid);
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        // Get the size of the list
        List<ProductCategories> listpc = pcdao.filterListProductByBrandId(brandid);
        int countProduct = listpc.size();
        int endPage = countProduct / 10;
        if (countProduct % 10 != 0) {
            endPage++;
        }
        List<ProductCategories> pagingpc = pcdao.getProductCategoriesPagination(index,brandid);
        request.setAttribute("product", pagingpc);
        request.setAttribute("endP", endPage);
        request.setAttribute("tagi", index);

        String namebrand = bdao.getNameBrand(brandid);

        request.setAttribute("brandname", namebrand);
        request.getRequestDispatcher("manageproductcategories.jsp").forward(request, response);
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
