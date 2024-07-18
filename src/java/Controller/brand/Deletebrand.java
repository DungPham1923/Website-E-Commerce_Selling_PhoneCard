package Controller.brand;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAL.BrandDAO;
import Controller.authentication.BaseRequireAuthentication;
import Model.AccountLogin;
import Model.Brand;
import java.util.List;

@WebServlet("/deleteBrand")
public class Deletebrand extends BaseRequireAuthentication {

    private final BrandDAO brandDAO = new BrandDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idStr = request.getParameter("brandId");
        String uid = request.getParameter("userId");
        if(uid == null){
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        if (idStr != null && !idStr.isEmpty() && uid!=null && !uid.isEmpty()) {
            int id = Integer.parseInt(idStr);
            int userId = Integer.parseInt(uid);
            boolean deleted = brandDAO.deleteBrand(id, userId);

            if (deleted) {
                request.setAttribute("deleteSuccess", true);
            } else {
                request.setAttribute("deleteSuccess", false);
            }
        } else {
            // Handle case where ID parameter is missing
            request.setAttribute("deleteSuccess", false);
        }

        List<Brand> brands = brandDAO.getListBrand();
        request.setAttribute("brands", brands);
        request.setAttribute("userId", uid);
        request.getRequestDispatcher("managebrand.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account)
            throws ServletException, IOException {
        if(account == null || account.getUser().getRole().getID() != 1) {
            response.sendRedirect("login.jsp");
        }else{
            processRequest(request, response);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to delete a brand by ID";
    }
}
