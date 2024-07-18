package Controller.ManageOrderList;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import Model.AccountLogin;
import Model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayOrderListServlet extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayOrderListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayOrderListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        processRequest(request, response);
//    } 
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        processRequest(request, response);
//    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        String idpage = request.getParameter("idPage");
        OrderDAO orderDao = new OrderDAO();
        int idPage = 1;
        if(idpage!= null){
            idPage = Integer.parseInt(idpage);
        }
        int totalOrders = orderDao.getTotalOrder();
        int amountPage = (totalOrders + 25 - 1) / 25;
        if (account == null || account.getUser().getRole().getID() != 1) {
            response.sendRedirect("login.jsp");
        } else {
            List<Order> dataOrder = orderDao.getAllOrder(idPage);
            request.setAttribute("dataOrder", dataOrder);
            request.setAttribute("amountPage", amountPage);
            request.setAttribute("index", idPage);
            request.getRequestDispatcher("manageorder.jsp").forward(request, response);
        }

    }

}
