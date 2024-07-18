

package Controller.ShopProduct;

import DAL.BrandDAO;
import DAL.ProductCategoriesDAO;
import Model.ProductCategories;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


public class SortProduct extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SortProduct</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SortProduct at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String sort = request.getParameter("request");
        //String desc = request.getParameter("desc");
        String brandId = request.getParameter("brandid");
        BrandDAO brandDao = new BrandDAO();
        int brandid = 0;
        try {
            if(brandId != null){
                brandid = Integer.parseInt(brandId);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        
        ProductCategoriesDAO pcd = new ProductCategoriesDAO();
        List<ProductCategories> dataProduct = null;
        if(sort != null){
            dataProduct = pcd.sortProduct(sort, brandid);
        }
        String nameBrand = "";
        if(brandDao.getNameBrand(brandid) != null){
            nameBrand = brandDao.getNameBrand(brandid);
        }else{
            nameBrand = "Tất cả";
        }
        String sortRequest = "";
        if(sort.equalsIgnoreCase("asc")){
            sortRequest = "Tăng dần";
        }else if(sort.equalsIgnoreCase("desc")){
            sortRequest = "Giảm dần";
        }
        request.setAttribute("sort", sortRequest);
        request.setAttribute("dataProduct", dataProduct);
        request.setAttribute("brandName", nameBrand);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
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
