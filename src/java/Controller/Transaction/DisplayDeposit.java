package Controller.Transaction;

import Controller.authentication.BaseRequireAuthentication;
import DAL.BrandDAO;
import DAL.TransactionDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Brand;
import Model.Transaction;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DisplayDeposit extends BaseRequireAuthentication {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet to display brands";
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        UserDAO u = new UserDAO();
        TransactionDAO t = new TransactionDAO();
        
        if (account == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            User user = u.getUserById(account.getUser().getID());
//            if (user.getRole().getID() != 1) {
//                req.getRequestDispatcher("login.jsp").forward(req, resp);
//            } else {
            List<Transaction> list = t.getDepositHistoryByUserID(user.getID());
            req.setAttribute("list", list);
            req.setAttribute("userId", user.getID());
            req.getRequestDispatcher("managedeposit.jsp").forward(req, resp);
//            }
        }
    }
}
