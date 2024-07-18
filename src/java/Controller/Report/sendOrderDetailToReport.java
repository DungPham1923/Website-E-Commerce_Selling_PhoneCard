
package Controller.Report;

import DAL.OrderDAO;
import DAL.ProductCategoriesDAO;
import DAL.ReportDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.OrderDetails;
import Model.ProductCategories;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class sendOrderDetailToReport extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String idorderdetail = request.getParameter("idorderdetail");
        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");
        
        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userid = (user != null) ? user.getUser().getID() : gguser.getUser().getID();
        
        ProductCategoriesDAO pcate = new ProductCategoriesDAO();
        ReportDAO r = new ReportDAO();
        OrderDAO o = new OrderDAO();
        OrderDetails od = o.getOrderDetailsById(idorderdetail);
        
        int productCardID = od.getProductcard().getID();
        
        int ProductCategoriesID = od.getProductcard().getProductCategories().getId();// getProductCategoriesID();
        ProductCategories productCategories = pcate.getProductCategoriesByID(ProductCategoriesID);
        String brandiD = productCategories.getBrand().getName();
        
        r.insertReport(userid, od.getID(),productCardID,productCategories.getName() ,brandiD);
        response.sendRedirect("order");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
