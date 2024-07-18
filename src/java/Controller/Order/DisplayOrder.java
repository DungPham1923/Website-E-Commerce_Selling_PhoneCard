
package Controller.Order;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.Order;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class DisplayOrder extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayOrder at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        OrderDAO orderDao = new OrderDAO();
//        List<Order> data = null;
//        HttpSession session = request.getSession();
//        AccountLogin account = (AccountLogin) session.getAttribute("account");
//        GoogleLogin accountgg = (GoogleLogin) session.getAttribute("gguser");
//        if (account == null && accountgg == null) {
//            request.setAttribute("thongbao", "Vui lòng đăng nhập để xem đơn hàng!");
//            request.getRequestDispatcher("order.jsp").forward(request, response);
//        } else {
//            if (accountgg != null) {
//                var userID = accountgg.getUser().getID();
//                data = orderDao.getListOrderByUserID(userID);
//                request.setAttribute("data", data);
//                request.getRequestDispatcher("order.jsp").forward(request, response);
//            } else if (account != null) {
//                var userID = account.getUser().getID();
//                data = orderDao.getListOrderByUserID(userID);
//                request.setAttribute("data", data);
//                request.getRequestDispatcher("order.jsp").forward(request, response);
//            }
//        }
//
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        OrderDAO orderDao = new OrderDAO();
        List<Order> data = null;
        HttpSession session = request.getSession();
        account = (AccountLogin) session.getAttribute("account");
        GoogleLogin accountgg = (GoogleLogin) session.getAttribute("gguser");
        if (account == null && accountgg == null) {
            request.setAttribute("thongbao", "Vui lòng đăng nhập để xem đơn hàng!");
            request.getRequestDispatcher("order.jsp").forward(request, response);
        } else {
            if (accountgg != null) {
                var userID = accountgg.getUser().getID();
                data = orderDao.getListOrderByUserID(userID);
                request.setAttribute("data", data);
                request.getRequestDispatcher("order.jsp").forward(request, response);
            } else if (account != null) {
                var userID = account.getUser().getID();
                data = orderDao.getListOrderByUserID(userID);
                request.setAttribute("data", data);
                request.getRequestDispatcher("order.jsp").forward(request, response);
            }
        }
    }

}
