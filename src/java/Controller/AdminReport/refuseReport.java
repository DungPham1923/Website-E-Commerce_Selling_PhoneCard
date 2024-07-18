package Controller.AdminReport;

import DAL.EmailDAO;
import DAL.OrderDAO;
import DAL.ReportDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.OrderDetails;
import Model.Role;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class refuseReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession();
        AccountLogin user = (AccountLogin) session.getAttribute("account");
        GoogleLogin gguser = (GoogleLogin) session.getAttribute("gguser");

        if (user == null && gguser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Role role = (user != null) ? user.getUser().getRole() : gguser.getUser().getRole();
        int role2 = role.getID();

        if (role2 == 2) {
            response.sendRedirect("login.jsp");
            return;
        }

        OrderDAO o = new OrderDAO();
        UserDAO u = new UserDAO();
        
        OrderDetails odetail = o.getOrderDetailsById(id);
        int userID = o.getOrderByOrderID(odetail.getOrderID()).getUserId();
        User x = u.getUserById(userID);
        String email = u.getUserById(userID).getEmail();

        ReportDAO r = new ReportDAO();
        r.updateRefuseStatusReport(id);

        EmailDAO.sendEmail(email, "Phản hồi báo cáo sản phẩm | The Card Website",
                "<div class=\"email-content\" style=\"padding: 20px;\">\n"
                + "            <p>Xin chào, "+ x.getFirstName() + " " + x.getLastName() + "</p>\n"
                + "            <p>Báo cáo của bạn đã được nhận và đã được xem xét. Sau quá trình xem xét, chúng tôi nhận thấy không có lỗi sản phẩm. Hệ thống sẽ không thực hiện đền bù!</p>\n"
                + "            <p>Lời nhắn mô tả: " + reason + "</p>"
                + "            <p>Xin cảm ơn!</p>\n"
                + "        </div>");
        response.sendRedirect("loadreport");
        //request.getRequestDispatcher("loadreport").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
