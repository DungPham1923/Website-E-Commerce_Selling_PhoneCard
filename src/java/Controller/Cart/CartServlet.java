package Controller.Cart;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CartItemDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.CartItem;
import Model.GoogleLogin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class CartServlet extends BaseRequireAuthentication {
    
    private final CartItemDAO cidao = new CartItemDAO();
    private final UserDAO udao = new UserDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");
        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userid = (user != null) ? user.getUser().getID() : gguser.getUser().getID();
        
        List<CartItem> cartItems = cidao.getCartItem(userid);
        request.setAttribute("cart", cartItems);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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