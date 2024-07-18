/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.depositmoney;

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
public class DepositMoney extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountLogin alg = (AccountLogin) request.getSession().getAttribute("account");
        UserDAO uDao = new UserDAO();

        if (alg == null) {
            String wrongAcc = "Bạn cần đăng nhập để vào trang này!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.setAttribute("wrongCaptcha", ""); // Assuming you clear captcha errors if any
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            User thisUser = uDao.getUserById(alg.getUser().getID());
            request.setAttribute("userDeposit", thisUser);
            request.getRequestDispatcher("depositmoney.jsp").forward(request, response);
        }
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
