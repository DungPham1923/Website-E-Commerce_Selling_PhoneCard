// Thu - Iter 3
package Controller.admin;

import DAL.ProductCardDAO;
import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.ProductCard;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class ViewProductCardServlet extends HttpServlet {
    private final ProductCategoriesDAO pc_dao = new ProductCategoriesDAO();
    private final ProductCardDAO pcdao = new ProductCardDAO();
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
        
        if(!udao.checkAdmin(userid)){
            response.sendRedirect("login.jsp");
            return;
        }
        
        String id = request.getParameter("pcid");
        
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);
        int productcategoriesid = Integer.parseInt(id);
        
        int countProduct = pcdao.countCardByPCID(productcategoriesid);
        int endPage = countProduct / 50;
        if (countProduct % 50 != 0) {
            endPage++;
        }        
        List<ProductCard> list = pcdao.getProductCardByPCID(productcategoriesid, index);
        
        String pc_name = pc_dao.getProductCategoriesById(productcategoriesid).getName();
        
        request.setAttribute("productcard", list);
        request.setAttribute("endP", endPage);
        request.setAttribute("tagi", index); 
        request.setAttribute("pcname", pc_name);
        request.getRequestDispatcher("manageproductcard.jsp").forward(request, response);
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