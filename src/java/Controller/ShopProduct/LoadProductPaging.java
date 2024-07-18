/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.ShopProduct;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import DAL.ProductCategoriesDAO;
import Model.AccountLogin;
import Model.ProductCategories;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadProductPaging extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoadProductPaging</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadProductPaging at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    public List<ProductCategories> nameFilterAnalysis(int index, int size, int idBrand, double price, String name, String sort) {
        ProductCategoriesDAO productDAO = new ProductCategoriesDAO();
        List<ProductCategories> mostFrequentItems = new ArrayList<>();
        String[] searchTerms = null;
        if (name != null) {
            searchTerms = name.split("\\s+");
            Map<Integer, ProductCategories> uniqueItems = new HashMap<>();
            Map<Integer, Integer> idCount = new HashMap<>();

            //nếu search theo tất cả tên mà ko có sản phẩm nào thì tách từng từ để search
            //if(dataProduct.isEmpty()){
            List<ProductCategories> resultList = new ArrayList<>();
            for (String term : searchTerms) {
                resultList = productDAO.getProductCategoriesByPaging(index, size, idBrand, price, term, sort);
                if (resultList != null && !resultList.isEmpty()) {
                    for (ProductCategories product : resultList) {
                        int id1 = product.getId();
                        if (uniqueItems.containsKey(id1)) {
                            idCount.put(id1, idCount.get(id1) + 1);
                        } else {
                            uniqueItems.put(id1, product);
                            idCount.put(id1, 1);
                        }
                    }
                }
            }
            int maxCount = 0;
            if (!idCount.isEmpty()) {
                maxCount = Collections.max(idCount.values());
            }
            // Tạo danh sách các bản ghi có số lần xuất hiện lớn nhất
            for (Map.Entry<Integer, Integer> entry : idCount.entrySet()) {
                if (entry.getValue() == maxCount) {
                    mostFrequentItems.add(uniqueItems.get(entry.getKey()));
                }
            }
        }

        return mostFrequentItems;
    }

    public List<ProductCategories> getAmountProduct(int idBrand, double price, String name) {
        ProductCategoriesDAO productDAO = new ProductCategoriesDAO();
        List<ProductCategories> mostFrequentItems = new ArrayList<>();
        String[] searchTerms = null;
        if (name != null) {
            searchTerms = name.split("\\s+");
            Map<Integer, ProductCategories> uniqueItems = new HashMap<>();
            Map<Integer, Integer> idCount = new HashMap<>();

            //nếu search theo tất cả tên mà ko có sản phẩm nào thì tách từng từ để search
            //if(dataProduct.isEmpty()){
            List<ProductCategories> resultList = new ArrayList<>();
            for (String term : searchTerms) {
                resultList = productDAO.getAmountProductCategories(idBrand, price, name);
                if (resultList != null && !resultList.isEmpty()) {
                    for (ProductCategories product : resultList) {
                        int id1 = product.getId();
                        if (uniqueItems.containsKey(id1)) {
                            idCount.put(id1, idCount.get(id1) + 1);
                        } else {
                            uniqueItems.put(id1, product);
                            idCount.put(id1, 1);
                        }
                    }
                }
            }
            // }

            int maxCount = 0;
            if (!idCount.isEmpty()) {
                maxCount = Collections.max(idCount.values());
            }

            // Tạo danh sách các bản ghi có số lần xuất hiện lớn nhất
            for (Map.Entry<Integer, Integer> entry : idCount.entrySet()) {
                if (entry.getValue() == maxCount) {
                    mostFrequentItems.add(uniqueItems.get(entry.getKey()));
                }
            }
        }

        return mostFrequentItems;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        BrandDAO brandDao = new BrandDAO();
        ProductCategoriesDAO productDAO = new ProductCategoriesDAO();

        // Get tham số
        String index = request.getParameter("idPage");
        String size = request.getParameter("size");
        String id = request.getParameter("idBrand");
        String price = request.getParameter("price");
        String name = request.getParameter("name");
        String sortRequest = request.getParameter("sort"); 
        // End
        
        // Xử lí index - size
        int sizePage = 8;
        int indexPage = 1;
        try {
            if (size != null && !size.isEmpty()) {
                sizePage = Integer.parseInt(size);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid size parameter: " + e.getMessage());
        }

        try {
            if (index != null && !index.isEmpty()) {
                indexPage = Integer.parseInt(index);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid index parameter: " + e.getMessage());
        }

        // End
        // Xử lí brand
        int idBrand = 0;
        try {
            if (id != null && !id.isEmpty()) {
                idBrand = Integer.parseInt(id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid brand parameter: " + e.getMessage());
        }
        String nameBrand = "";
        if (brandDao.getNameBrand(idBrand) != null) {
            nameBrand = brandDao.getNameBrand(idBrand);
        } else {
            nameBrand = "Tất cả";
        }
        // End

        // Xử lí price
        double priceProduct = 0;
        try {
            if (price != null && !price.isEmpty()) {
                priceProduct = Double.parseDouble(price);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price parameter: " + e.getMessage());
        }
        String priceb = productDAO.formatPrice(priceProduct);
        if(priceProduct ==0){
            priceb = "Tất cả";
        }else{
            priceb += " VNĐ";
        }
        // End
        
        //Xử lí sort
//        if(idBrand == 0 || priceProduct == 0){
//            name = null;
//        }
        // Tính tổng số trang
        int totalProducts = productDAO.getTotalProductCategories();
        int amountPage = (totalProducts + sizePage - 1) / sizePage;

        // Lấy sản phẩm theo trang
//        if(idBrand != 0 || priceProduct != 0){
//            indexPage = 1;
//        }
        List<ProductCategories> dataProduct = productDAO.getProductCategoriesByPaging(indexPage, sizePage, idBrand, priceProduct, name, sortRequest);
        List<ProductCategories> amountProduct = productDAO.getAmountProductCategories(idBrand, priceProduct, name);

        // Xử lí phân tích name để filter nếu có
        if (name != null && !name.trim().isEmpty()) {
            dataProduct = nameFilterAnalysis(indexPage, sizePage, idBrand, priceProduct, name, sortRequest);
            amountProduct = getAmountProduct(idBrand, priceProduct, name);
        }

        // Nếu có filter, tính lại số trang
        if (idBrand != 0 || priceProduct != 0 || (name != null && !name.trim().isEmpty())) {
            int totalFilteredProducts = amountProduct.size();
            amountPage = (totalFilteredProducts + sizePage - 1) / sizePage;
            if(amountPage == 0){
                amountPage++;
            }
        }

        // Nếu trang hiện tại lớn hơn số trang có thì reset index về trang đầu
        if (indexPage > amountPage) {
            indexPage = 1;
            dataProduct = nameFilterAnalysis(indexPage, sizePage, idBrand, priceProduct, name, sortRequest);
        }

        // Set attributes and forward to JSP
        request.setAttribute("amountPage", amountPage);
        request.setAttribute("dataProduct", dataProduct);
        request.setAttribute("namesearch", name);
        request.setAttribute("brandName", nameBrand);
        request.setAttribute("price", priceb);
        request.setAttribute("index", indexPage);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

}
