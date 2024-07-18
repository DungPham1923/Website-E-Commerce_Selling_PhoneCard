

package Controller.Checkout;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GetOrderStatus extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetOrderStatus</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetOrderStatus at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Đẩy giá trị vào session
        String orderStatusMessage = "Đơn hàng của bạn đã được xử lý thành công.";
        session.setAttribute("orderStatusMessage", orderStatusMessage);
        
        // Hoặc có thể lấy từ request
        String message = request.getParameter("message");
        session.setAttribute("message", message);

        // Hoặc xóa giá trị từ session
        session.removeAttribute("orderStatusMessage");

        // Hoặc truy xuất giá trị từ session
        String statusMessage = (String) session.getAttribute("orderStatusMessage");
        if (statusMessage != null) {
            System.out.println("Order status message: " + statusMessage);
        }
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
