package Controller.ManageOrderList;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import Model.AccountLogin;
import Model.OrderDetails;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ManageOrderListServlet extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageOrderListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageOrderListServlet at " + request.getContextPath() + "</h1>");
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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        String orderId = request.getParameter("orderId");
        int OrderId = 0;
        try {
            if (orderId != null) {
                OrderId = Integer.parseInt(orderId);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        OrderDAO orderDao = new OrderDAO();
        List<OrderDetails> dataOrder = orderDao.getAllOrderDetailsByOderId(OrderId);
        try (PrintWriter out = response.getWriter()) {
            for (int i = 0; i < dataOrder.size(); i++) {
                out.print("                            <tr>\n"
                        + "                                <td> " + (i + 1) + "</td>"
                        + "                                <td>" + dataOrder.get(i).getProductCategories().getName() + "</td>\n"
                        + "                                <td>********</td>\n"
                        + "                                <td>********</td>\n"
                        + "                                <td>" + dataOrder.get(i).getProductPrice() + " VNĐ</td> \n"
                        + "                                <td>" + dataOrder.get(i).getDiscount() + " %</td> \n"
                        + "                                <td>" + dataOrder.get(i).getAmount() + " VNĐ</td> \n"
                        + "                            </tr>\n");
            }
        }
    }//end
}
