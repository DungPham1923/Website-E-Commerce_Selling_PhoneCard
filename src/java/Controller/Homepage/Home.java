package Controller.Homepage;

import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import DAL.BrandDAO;
import DAL.GoogleLoginDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Brand;
import Model.GoogleLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;

public class Home extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Home</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Home at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
//        HttpSession sess = request.getSession();
//        AccountLoginDAO ald = new AccountLoginDAO();
//        UserDAO userDao = new UserDAO();
//        GoogleLoginDAO gld = new GoogleLoginDAO();
//
//        User user = null;
//        account = null;
//        GoogleLogin gglogin = null;
//        if (sess.getAttribute("account") != null) {
//            account = (AccountLogin) sess.getAttribute("account");
//            user = (User) userDao.getUserById(account.getUser().getID());
//        } else if (sess.getAttribute("gguser") != null) {
//            gglogin = (GoogleLogin) sess.getAttribute("gguser");
//            user = (User) userDao.getUserById(gglogin.getUser().getID());
//        } else {
//            user = null;
//            account = null;
//        }
//        String balance = null;
//        if (user != null) {
//            DecimalFormat df = new DecimalFormat("#,###");
//            df.setMaximumFractionDigits(0);
//            balance = df.format(user.getBalance());
//        }
//
//        BrandDAO brandDao = new BrandDAO();
//        List<Brand> dataBrand = brandDao.getListBrand();
//        request.setAttribute("dataBrand", dataBrand);
        request.getRequestDispatcher("home.jsp").forward(request, response);
//        if(account != null){
//            
//        }else{
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        }
        
    }

}
