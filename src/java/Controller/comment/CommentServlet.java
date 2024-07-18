/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.comment;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CommentDAO;
import DAL.ProductCategoriesDAO;
import Model.AccountLogin;
import Model.Comment;
import Model.ProductCategories;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dat
 */
public class CommentServlet extends BaseRequireAuthentication {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountLogin accountLogin = (AccountLogin) session.getAttribute("account");
        if (accountLogin == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int userId = accountLogin.getUser().getID();
        String pid = request.getParameter("pid");
        int PCateID = Integer.parseInt(pid);
        ProductCategoriesDAO PCateDAO = new ProductCategoriesDAO();
        ProductCategories pc = PCateDAO.getProductCateByID(pid);
        String productCateName = pc.getName();
        String brandName = pc.getBrand().getName();
        
        
        String title = accountLogin.getUser().getFirstName() + " " + accountLogin.getUser().getLastName() + " comment!";
        String msg = request.getParameter("comment");

        try {
            CommentDAO cmtDAO = new CommentDAO();
            cmtDAO.insertComment(PCateID, userId, title, msg,productCateName, brandName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("productdetail?id=" + pid);
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
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
