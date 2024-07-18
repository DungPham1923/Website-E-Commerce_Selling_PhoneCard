

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


public class FilterByPrice extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FilterByPrice</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterByPrice at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String price = request.getParameter("price");
        String brandId = request.getParameter("brandid");
        ProductCategoriesDAO pdcDAO = new ProductCategoriesDAO();
        double productPrice = 0;
        BrandDAO brandDao = new BrandDAO();
        int brandid = 0;
        try {
            productPrice = Double.parseDouble(price);
            if(brandId != null){
                brandid = Integer.parseInt(brandId);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        String nameBrand = "";
        String priceb = pdcDAO.formatPrice(productPrice);
        if(brandDao.getNameBrand(brandid) != null){
            nameBrand = brandDao.getNameBrand(brandid); 
        }else{
            nameBrand = "Tất cả";
        }
        List<ProductCategories> dataProduct = pdcDAO.filterListProductByPrice(productPrice,  brandid);
        if(dataProduct == null || !dataProduct.isEmpty()){
            request.setAttribute("thongbao", "Không có tìm kiếm cho lựa chọn này!");
        }
        request.setAttribute("price", priceb);
        request.setAttribute("brandName", nameBrand);
        request.setAttribute("dataProduct", dataProduct);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short descriptionn";
    }

}
