
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

public class SearchOrderServlet extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchOrderServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchOrderServlet at " + request.getContextPath () + "</h1>");
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
        
        String orderId = request.getParameter("orderId");
        OrderDAO orderDao = new OrderDAO();
        
        int orderID = 0;
        int index = 1;
        
        int totalOrders = orderDao.getTotalOrder();
        int amountPage = (totalOrders + 25 - 1) / 25;
        try {
            if(orderId != null){ 
                orderID = Integer.parseInt(orderId);
                amountPage = 1;
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        if(account == null || account.getUser().getRole().getID() != 1){
            response.sendRedirect("login.jsp");
        }else{
            List<Order> dataOrder; 
            if(orderID != 0){
                dataOrder = orderDao.getListOrderByID(orderID);
            }else{
                dataOrder = orderDao.getAllOrder(index);
            }
            if(orderId != null || !orderId.isEmpty()){ 
                request.setAttribute("dataOrder", dataOrder);
                request.setAttribute("amountPage", amountPage);
                request.setAttribute("orderId", orderId);
                request.setAttribute("index", index);    
                request.getRequestDispatcher("manageorder.jsp").forward(request, response);
            }
            
        }
    }

}
