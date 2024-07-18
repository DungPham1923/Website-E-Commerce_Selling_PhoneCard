package Controller.Cart;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CartDAO;
import DAL.CartItemDAO;
import DAL.ProductCategoriesDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;

public class AddToCartServlet extends BaseRequireAuthentication {

    private final CartItemDAO cidao = new CartItemDAO();
    private final CartDAO cdao = new CartDAO();
    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String p = request.getParameter("id");
        String q = request.getParameter("quantity");
        String action = request.getParameter("action");
        String source = request.getParameter("source");

        int productid = Integer.parseInt(p);
        int quantity = Integer.parseInt(q);

        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");
        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userid = (user != null) ? user.getUser().getID() : gguser.getUser().getID();
        int cartid = cdao.getCartByUserId(userid);

        // if this user exists in the cart table 
        if (cartid != -1) {
            System.out.println("Cart exists for user ID: " + userid);

            int stockqty = pcdao.getQuantity(productid);

            if (cidao.checkProductExist(productid, cartid)) {
                System.out.println("Product exists in cart for user ID: " + userid + " Cart ID: " + cartid);

                int cartqty = cidao.getQuantity(productid, cartid);

                System.out.println("Stock quantity: " + stockqty + ", Cart quantity: " + cartqty);

                if ((stockqty - cartqty) < quantity) {
                    request.setAttribute("error", "Bạn đã đạt đến số lượng tối đa có sẵn cho mặt hàng này");
                    redirectToActionPage(request, response, action, source, productid);
                } else {
                    cidao.updateProductQuantity(productid, quantity, cartqty, cartid);
                    System.out.println("Updated product quantity: " + quantity);
                    redirectToActionPageWithSuccess(request, response, action, source, productid);
                }
            } else {
                if (stockqty < quantity) {
                    request.setAttribute("error", "Bạn đã đạt đến số lượng tối đa có sẵn cho mặt hàng này");
                    redirectToActionPage(request, response, action, source, productid);
                } else {
                    cidao.addCartItem(cartid, productid, quantity);
                    System.out.println("Added product to cart: cartid=" + cartid + ", productid=" + productid + ", quantity=" + quantity);
                    redirectToActionPageWithSuccess(request, response, action, source, productid);
                }
            }
        } else {
            cdao.addCart(userid);
            int latestcartid = cdao.getLatestCartId();
            cidao.addCartItem(latestcartid, productid, quantity);
            System.out.println("Created new cart and added product: cartid=" + latestcartid + ", productid=" + productid + ", quantity=" + quantity);
            redirectToActionPageWithSuccess(request, response, action, source, productid);
        }
    }

    // Helper methods for redirection based on action and source
    private void redirectToActionPage(HttpServletRequest request, HttpServletResponse response, String action, String source, int productid) throws ServletException, IOException {
        switch (action) {
            case "add":
                request.getRequestDispatcher("productdetail?id=" + productid).forward(request, response);
                break;
            case "buy":
                if ("productdetail".equals(source)) {
                    request.getRequestDispatcher("productdetail?id=" + productid).forward(request, response);
                } else if ("shop".equals(source)) {
                    request.getRequestDispatcher("shop").forward(request, response);
                }
                break;
            case "shop":
                request.getRequestDispatcher("shop").forward(request, response);
                break;
            default:
                response.sendRedirect("404_error.jsp");
                break;
        }
    }

    private void redirectToActionPageWithSuccess(HttpServletRequest request, HttpServletResponse response, String action, String source, int productid) throws IOException {
        String message = "Đã thêm thẻ vào giỏ thành công";
        switch (action) {
            case "add":
                message = URLEncoder.encode(message, "UTF-8");
                response.sendRedirect("productdetail?id=" + productid + "&success=" + message);
                break;
            case "buy":
                if ("productdetail".equals(source)) {
                    response.sendRedirect("cart");
                } else if ("shop".equals(source)) {
                    response.sendRedirect("cart");
                }
                break;
            case "shop":
                response.sendRedirect("shop?success=" + URLEncoder.encode(message, "UTF-8"));
                break;
            default:
                response.sendRedirect("404_error.jsp");
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
