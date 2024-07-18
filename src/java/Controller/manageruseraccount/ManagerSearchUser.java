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
import java.util.ArrayList;
import java.util.List;

public class ManagerSearchUser extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        AccountLoginDAO adao = new AccountLoginDAO();
        List<AccountLogin> listUserSearch = new ArrayList<>();
        if (userName != null) {
            String[] searchTerms = userName.split("\\s+");
            for (String searchTerm : searchTerms) {
                List<AccountLogin> partialResults = adao.findUserByName(searchTerm);
                listUserSearch.addAll(partialResults);
            }
            for (AccountLogin accountLogin : listUserSearch) {
                for (AccountLogin accountLogin1 : listUserSearch) {
                    if (accountLogin.getID() == accountLogin1.getID()) {
                        listUserSearch.remove(accountLogin1);
                    }
                }
            }
        }
        request.getSession().setAttribute("listUserSearch", listUserSearch);
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
