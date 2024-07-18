/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.ManageFeedback;

import DAL.CommentDAO;
import DAL.EmailDAO;
import Model.AccountLogin;
import Model.Comment;
import Model.GoogleLogin;
import Model.Role;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ReplyFeedback extends HttpServlet {

   
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
        
        CommentDAO c = new CommentDAO();
        Comment comment = c.getCommentsByID(id);
        c.updateIsDeleteFeedback(id);
        String email = comment.getUser().getEmail();
        EmailDAO.sendEmail(email, "Phản hồi đánh giá sản phẩm | The Card Website",
                "<div class=\"email-content\" style=\"padding: 20px;\">\n"
                + "            <p>Xin chào, "+ comment.getUser().getFirstName() + " " + comment.getUser().getLastName() + "</p>\n"
                + "            <p>Đánh giá của bạn đã được tiếp nhận và xem xét!</p>\n"
                + "            <p>\""+  comment.getMessage() +"\"</p>\n"       
                + "            <p>Lời nhắn mô tả: " + reason + "</p>"
                + "            <p>Xin cảm ơn!</p>\n"
                + "        </div>");
        
        response.sendRedirect("displayfeedback");
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
    }

}
