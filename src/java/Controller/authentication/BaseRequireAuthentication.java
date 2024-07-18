
package Controller.authentication;

import DAL.AccountDAO;
import Model.AccountLogin;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class BaseRequireAuthentication extends HttpServlet {

    private AccountLogin getAuthenticatedAccount(HttpServletRequest req) {
        AccountLogin account = (AccountLogin) req.getSession().getAttribute("account");
        if (account == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                String username = null;
                String password = null;
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("username")) {
                        username = cooky.getValue();
                    }

                    if (cooky.getName().equals("password")) {
                        password = cooky.getValue();
                    }

                    if (username != null && password != null) {
                        break;
                    }
                }

                if (username != null && password != null) {
                    AccountDAO db = new AccountDAO();
                    account = db.getByUsernamePassword(username, password);
                    req.getSession().setAttribute("account", account);
                }
            }
        }
        return account;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountLogin account = getAuthenticatedAccount(req);
        if (account != null) {
            doGet(req, resp, account);
        } else{
            doGet(req, resp, null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountLogin account = getAuthenticatedAccount(req);
        if (account != null) {
            doPost(req, resp, account);
        } else{
            doGet(req, resp, null);
        }

    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account)
            throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account)
            throws ServletException, IOException;

}
