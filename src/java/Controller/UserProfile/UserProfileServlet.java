
package Controller.UserProfile;

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


public class UserProfileServlet extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserProfileServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserProfileServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        UserDAO userDao = new UserDAO();
//        HttpSession sess = request.getSession();
//        AccountLogin account = (AccountLogin) sess.getAttribute("account");
//        User user = userDao.getUserById(account.getUser().getID());
//        //User user = (User) sess.getAttribute("account");
//        //user = userDao.getUserById(user.getID());
//        AccountLoginDAO ald = new AccountLoginDAO();
//        //AccountLogin acc = ald.getAccountByID(1);
//        request.setAttribute("user", user);
//        request.setAttribute("account", account);
//        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
//    } 

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        
//    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        UserDAO userDao = new UserDAO();
        HttpSession sess = request.getSession();
        account = (AccountLogin) sess.getAttribute("account");
        User user = userDao.getUserById(account.getUser().getID());
        //User user = (User) sess.getAttribute("account");
        //user = userDao.getUserById(user.getID());
        AccountLoginDAO ald = new AccountLoginDAO();
        //AccountLogin acc = ald.getAccountByID(1);
        request.setAttribute("user", user);
        request.setAttribute("account", account);
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }

}
