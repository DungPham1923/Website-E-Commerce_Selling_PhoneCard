package Controller.ShopProduct;

import DAL.BrandDAO;
import DAL.ProductCategoriesDAO;
import Model.Brand;
import Model.ProductCategories;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class FilterByBrand extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FilterByBrand</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterByBrand at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        doPost(request, response);
//        PrintWriter out = response.getWriter();
//        //String name = request.getParameter("name");
//        BrandDAO b = new BrandDAO(); 
//        List<Brand> brandList = b.getListBrand();
//        for (Brand o : brandList) {
//            out.print("<h5> + "+ o.getName()+ "</h5>");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String idBrand = request.getParameter("brandid");
        String price = request.getParameter("price");
        String size = request.getParameter("size");
        String index = "";
        if(request.getParameter("idPage") != null){
            index = request.getParameter("idPage");
        }
        

        BrandDAO brandDao = new BrandDAO();
        ProductCategoriesDAO productDAO = new ProductCategoriesDAO();
        int brandId = 0;
        double pricef = 0;

        try {
            brandId = Integer.parseInt(idBrand);
            if (price != null) {
                pricef = Double.parseDouble(price);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        String nameBrand = "";
        if (brandDao.getNameBrand(brandId) != null) {
            nameBrand = brandDao.getNameBrand(brandId);
        } else {
            nameBrand = "Tất cả";
        }

        int sizePage = 0;
        if (size != null) {
            sizePage = Integer.parseInt(size);
        } else {
            sizePage = 4;
        }
        int indexPage = 1;
        if (index != null || index.isEmpty()) {
            indexPage = Integer.parseInt(index);
        } else {
            indexPage = 1;
        }

        List<ProductCategories> dataProduct = productDAO.filterListProductByBrandId(brandId, pricef, indexPage, sizePage);
        String thongbao = null;
        if (dataProduct == null || dataProduct.size() == 0) {
            thongbao = "Không có tìm kiếm nào cho lựa chọn này!";
        }
        if (pricef != 0) {
            request.setAttribute("price", pricef);
        }
        //Xử lí phân trang hiện tại
        int amountPage = 0;
        amountPage = dataProduct.size() / sizePage;
        if (dataProduct.size() / sizePage != 0) {
            amountPage++;
        }

        request.setAttribute("amountPage", amountPage);
        request.setAttribute("index", indexPage);
        request.setAttribute("thongbao", thongbao);
        request.setAttribute("dataProduct", dataProduct);
        request.setAttribute("brandName", nameBrand);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short descriptionn";
    }
}
