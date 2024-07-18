/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.manageruseraccount;

import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Dat
 */
public class ToggleUserStatus extends BaseRequireAuthentication {

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
        String userId = request.getParameter("userId");
        String isChecked = request.getParameter("isChecked");

        boolean isDelete = Boolean.parseBoolean(isChecked);
        AccountLogin alg = (AccountLogin) request.getSession().getAttribute("account");
        

        AccountLoginDAO algDao = new AccountLoginDAO();
        algDao.updateLoginStatus(userId, isDelete, alg.getID());

        UserDAO uDao = new UserDAO();
        uDao.updateStatusByAdmin(userId, isDelete, alg.getID());
        User user = uDao.getUserById(Integer.parseInt(userId));
        
        String msg = "Đã cập nhật trạng thái cho người dùng!";
        request.getSession().setAttribute("msgCustom", msg);
        response.sendRedirect("manageruseracc");
    }

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
