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
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Dat
 */
public class ManageUserAccount extends BaseRequireAuthentication {

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String[] userIds = request.getParameterValues("userIds");
        AccountLogin alg = (AccountLogin) request.getSession().getAttribute("account");

        String msg;
        UserDAO u = new UserDAO();
        if (alg == null || alg.getUser().getRole().getID() != 1) {
            response.sendRedirect("login.jsp");
        }
        if (userIds != null) {
            for (String id : userIds) {
                int idInt = Integer.parseInt(id);
                User userChangeStt = u.getUserById(idInt);
                boolean stt = userChangeStt.getIsDelete();
                boolean sttUpdat = !stt;
                AccountLoginDAO algDao = new AccountLoginDAO();
                algDao.updateLoginStatus(id, sttUpdat, alg.getID());

                UserDAO uDao = new UserDAO();
                uDao.updateStatusByAdmin(id, sttUpdat, alg.getID());
            }
            msg = "Thay đổi trạng thái thành công!";
        } else {
            msg = "Không người dùng nào được chọn!";
        }
        request.getSession().setAttribute("msgCustom", msg);
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        AccountLoginDAO algDao = new AccountLoginDAO();
        List<AccountLogin> allAcc;
        List<AccountLogin> allAccSearch = (List<AccountLogin>) request.getSession().getAttribute("listUserSearch");
        request.getSession().removeAttribute("listUserSearch");
        if (allAccSearch != null) {
            allAcc = allAccSearch;
        } else {
            allAcc = algDao.getAllAccount();
        }
        String msg = (String) request.getSession().getAttribute("msgCustom");
        request.getSession().removeAttribute("msgCustom");

        request.setAttribute("msgCustom", msg);
        request.setAttribute("allAcc", allAcc);
        request.getRequestDispatcher("manageaccount.jsp").forward(request, response);
    }

}
