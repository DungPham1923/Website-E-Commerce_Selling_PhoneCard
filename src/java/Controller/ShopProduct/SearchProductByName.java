package Controller.ShopProduct;

import DAL.ProductCategoriesDAO;
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

public class SearchProductByName extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchProductByName</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchProductByName at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get request
        String productname = request.getParameter("name");
        String size = request.getParameter("size");
        String idBrand = request.getParameter("idBrand");
        String price = request.getParameter("price");
        String index = request.getParameter("idPage");

        // Kiểm tra nếu từ khóa tìm kiếm không hợp lệ
        if (productname == null || productname.trim().isEmpty()) {
            request.setAttribute("error", "Từ khóa tìm kiếm không hợp lệ!");
            request.setAttribute("namesearch", productname);
            request.getRequestDispatcher("shop.jsp").forward(request, response);
            return;
        }

        ProductCategoriesDAO productDao = new ProductCategoriesDAO();
        String[] searchTerms = productname.split("\\s+");

        List<ProductCategories> resultList = new ArrayList<>();
        Map<Integer, ProductCategories> uniqueItems = new HashMap<>();
        Map<Integer, Integer> idCount = new HashMap<>();

        int indexPage = 1;
        int sizePage = 8;
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

        List<ProductCategories> tempData = null;
        for (String term : searchTerms) {
            tempData = productDao.filterListProductByName(indexPage, sizePage, term);
            if (tempData != null && !tempData.isEmpty()) {
                for (ProductCategories product : tempData) {
                    int id = product.getId();
                    if (uniqueItems.containsKey(id)) {
                        idCount.put(id, idCount.get(id) + 1);
                    } else {
                        uniqueItems.put(id, product);
                        idCount.put(id, 1);
                    }
                }
            }
        }

        // Xác định số lần xuất hiện lớn nhất
        int maxCount = 0;
        if (!idCount.isEmpty()) {
            maxCount = Collections.max(idCount.values());
        }

        // Tạo danh sách các bản ghi có số lần xuất hiện lớn nhất
        List<ProductCategories> mostFrequentItems = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : idCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostFrequentItems.add(uniqueItems.get(entry.getKey()));
            }
        }

        // Nếu không tìm thấy dữ liệu với bất kỳ từ khóa nào
        if (mostFrequentItems.isEmpty()) {
            request.setAttribute("thongbao", "Không có sản phẩm nào cho tìm kiếm này!");
        }

        int amountProduct = mostFrequentItems.size();
        int amountPage = amountProduct / sizePage;
        if (amountProduct / sizePage != 0) {
            amountPage++;
        }

        // Gửi dữ liệu đến trang shop.jsp
        request.setAttribute("namesearch", productname);
        request.setAttribute("amountPage", amountPage);
        request.setAttribute("dataProduct", mostFrequentItems);
        request.setAttribute("index", indexPage);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short descriptionn";
    }

}
