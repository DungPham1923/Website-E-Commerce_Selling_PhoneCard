package Controller.brand;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Brand;
import Model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/displayBrands")
public class Displaybrand extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> brands = brandDAO.getListBrand();
        request.setAttribute("brands", brands);
        request.getRequestDispatcher("managebrand").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to display brands";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        UserDAO u = new UserDAO(); 
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> brandsList = new ArrayList<Brand>();

        if(account == null){
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }else{
           User user = u.getUserById(account.getUser().getID());
           if(user.getRole().getID() != 1){
               req.getRequestDispatcher("login.jsp").forward(req, resp);
           }else{
               brandsList = brandDAO.getListBrand2();
               req.setAttribute("brands", brandsList);
               req.setAttribute("userId", user.getID());
               req.getRequestDispatcher("managebrand.jsp").forward(req, resp);
           }
        }
    }
}
