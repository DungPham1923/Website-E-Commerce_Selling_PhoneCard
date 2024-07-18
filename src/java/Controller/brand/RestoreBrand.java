package Controller.brand;

import DAL.BrandDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestoreBrand extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("brandId");
        
        if(id==null || id.isEmpty()){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Brand ID cannot be null!");
            return;
        }
        int brandId = Integer.parseInt(id);
        BrandDAO brandDAO = new BrandDAO();
        boolean isRestored = brandDAO.restoreBrand(brandId);
        if(isRestored){
            response.sendRedirect("displayBrands");
        }else{
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to restore brand.");
            return;
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
        return "Servlet to add a new brand";
    }
}
