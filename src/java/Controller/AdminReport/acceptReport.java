
package Controller.AdminReport;

import DAL.EmailDAO;
import DAL.OrderDAO;
import DAL.ProductCardDAO;
import DAL.ReportDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.OrderDetails;
import Model.ProductCard;
import Model.Role;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class acceptReport extends HttpServlet {


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
        
        if(role2 == 2){
            response.sendRedirect("login.jsp");
            return;
        }

        ReportDAO r = new ReportDAO();
        OrderDAO o = new OrderDAO();
        UserDAO u = new UserDAO();
        
        OrderDetails odetail = o.getOrderDetailsById(id);
        int userID = o.getOrderByOrderID(odetail.getOrderID()).getUserId();
        
        OrderDetails od = o.getOrderDetailsById(id);
        User userr = u.getUserById(userID);
        String email = userr.getEmail();
        
        int userid = userr.getID();
        double amountdetail = od.getAmount();
        double banlance = userr.getBalance() + amountdetail;
        User x = u.getUserById(userID);
        u.updateBanlance(banlance, userid);
        r.updateAcceptStatusReport(id);
        
        EmailDAO.sendEmail(email, "Phản hồi báo cáo sản phẩm | The Card Website",
                "<div class=\"email-content\" style=\"padding: 20px;\">\n"
                + "            <p>Xin chào, "+ x.getFirstName() + " " + x.getLastName() + "</p>\n"
                + "            <p>Báo cáo của bạn đã được nhận và đã được xem xét. Sau quá trình xem xét, chúng tôi nhận thấy có lỗi sản phẩm. Hệ thống sẽ thực hiện hoàn " + amountdetail + " VNĐ vào ví của bạn!</p>\n"
                + "            <p>Lời nhắn mô tả: " + reason + "</p>"
                + "            <p>Xin cảm ơn!</p>\n"
                + "        </div>");
        response.sendRedirect("loadreport");
        
        
        //ProductCardDAO pdcDao = new ProductCardDAO();
        //int productcartegoiesId = od.getProductCategories().getId();
        //ProductCard pdc = pdcDao.getProductCardbyIDQuantity2(productcartegoiesId, 1);
        // Đổi thẻ mới
//        o.insertProductCartOrderDetail(pdc.getID(), od.getID());
//        pdcDao.updateIsDeleteProductCard(pdc.getID());
        
        
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
