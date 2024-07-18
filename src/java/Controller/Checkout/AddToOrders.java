package Controller.Checkout;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import DAL.CartDAO;
import DAL.CartItemDAO;
import DAL.OrderDAO;
import DAL.ProductCardDAO;
import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Cart;
import Model.CartItem;
import Model.Order;
import Model.OrderDetails;
import Model.ProductCard;
import Model.User;
import com.google.gson.JsonObject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AddToOrders extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    List<CartItem> dataItem = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String[] selectedProductIds = request.getParameterValues("selectedProducts");

        UserDAO userDao = new UserDAO();
        CartDAO cartDAO = new CartDAO();
        CartItemDAO ciDao = new CartItemDAO();
        ProductCategoriesDAO pcDao = new ProductCategoriesDAO();
        BrandDAO brandDao = new BrandDAO();
        User user = account.getUser();

        //List<CartItem> list = ciDao.getCartItem(user.getID()); //tất cả item trong giỏ của user
        List<CartItem> list = new ArrayList<CartItem>();
        if (selectedProductIds != null) {
            for (String idp : selectedProductIds) {
                list.add(ciDao.getCartitemByID(idp));
                dataItem.add(ciDao.getCartitemByID(idp));
            }
        }

        int quantity = 0;
        double totalAmount = 0;
        for (int i = 0; i < list.size(); i++) {
            quantity += list.get(i).getQuantity();
            totalAmount += list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() - list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() * (list.get(i).getProductCategories().getDiscount() / 100);
        }
        //InsertOrder
        Order order = new Order(user.getID(), quantity, totalAmount);
        OrderDAO orderDao = new OrderDAO();
        int orderId = orderDao.insertOrder(order);
        
        //request.setAttribute("orderId", orderId);
        int userid = account.getUser().getID();  //;(int) session.getAttribute("userid");
        ProductCardDAO productcartdao = new ProductCardDAO();
        for (int i = 0; i < list.size(); i++) {
            List<ProductCard> listproductcard = productcartdao.getProductCardbyIDQuantity(list.get(i).getProductCategories().getId(), list.get(i).getQuantity());
            for (int j = 0; j < listproductcard.size(); j++) {
                OrderDetails orderdetail = new OrderDetails(
                        orderId,
                        list.get(i).getProductCategories().getId(), 
                        pcDao.getNameProductCategoriesByID(list.get(i).getProductCategories().getId()),
                        brandDao.getNameBrandByPctID(list.get(i).getProductCategories().getId()),
                        list.get(i).getProductCategories().getPrice(),
                        list.get(i).getProductCategories().getDiscount(), 
                        list.get(i).getProductCategories().getPrice() - (list.get(i).getProductCategories().getPrice() * list.get(i).getProductCategories().getDiscount() * 0.01));
                orderDao.insertOrderDetail(orderdetail);
            }
        }
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("orderId", orderId);

        // Gửi JSON response về client
        response.setContentType("application/json");
        out.print(jsonResponse.toString());
        out.flush();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        //processRequest(req, resp);
        UserDAO userDao = new UserDAO();
        CartDAO cartDAO = new CartDAO();
        CartItemDAO ciDao = new CartItemDAO();
        User user = account.getUser();
        user.getID();
        for (CartItem x : dataItem) {
            ciDao.deleteCartItemByID(x);
        }
        List<CartItem> listCartItem = ciDao.getCartItem(user.getID());
        if (listCartItem.isEmpty()) {
            cartDAO.deleteCartByUserID(user.getID());
        }
        dataItem.clear();
    }

}
