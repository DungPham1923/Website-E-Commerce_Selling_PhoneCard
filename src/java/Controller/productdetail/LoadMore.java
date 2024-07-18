
package Controller.productdetail;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CommentDAO;
import Model.AccountLogin;
import Model.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class LoadMore extends BaseRequireAuthentication {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve the productID from the request
        String productID = request.getParameter("productId");
        int amount = Integer.parseInt(request.getParameter("amount"));
        CommentDAO cmtDAO = new CommentDAO();
        List<Comment> loadCMt = cmtDAO.getNext3CommentsByPID(productID,amount);

        try (PrintWriter out = response.getWriter()) {
            for (Comment o : loadCMt) {
                out.print("<div class=\"comment-item product\" style=\"margin-top: 10px; border-top: 1px solid gainsboro; padding-bottom: 10px; border-radius: 5px;\">\n"
                        + "    <div class=\"header\">\n"
                        + "        <p class=\"user-name\" style=\"font-weight: bold; color: green; padding: 10px 0px 0px 10px; margin-bottom: 5px;\">" + o.getUser().getLastName() + " " + o.getUser().getFirstName() + "</p>\n"
                        + "        <p class=\"dot\" style=\"margin: 0 5px; color: gray;\">.</p>\n"
                        + "        <p class=\"created-at\" style=\"color: gray; margin: 0px; padding: 10px 0px 0px 0px\">" + o.getCreatedAt() + "</p>\n"
                        + "    </div>\n"
                        + "    <p style=\"padding-left: 30px; margin: 0px; color: black;\">" + o.getMessage() + "</p>\n"
                        + "</div>");
            }
        }
    }
    

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

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
