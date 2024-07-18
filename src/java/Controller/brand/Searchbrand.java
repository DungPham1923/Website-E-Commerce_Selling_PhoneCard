package Controller.brand;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import Model.AccountLogin;
import Model.Brand;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Searchbrand extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin accountLogin)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if(accountLogin == null){
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        String keyword = request.getParameter("keyword");
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> brands = brandDAO.searchBrands(keyword);
        
        request.setAttribute("brands", brands);
        request.setAttribute("userId", accountLogin.getID());
        request.setAttribute("key", keyword);
        request.getRequestDispatcher("managebrand.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin accountLogin)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for searching brands";
    }
}
