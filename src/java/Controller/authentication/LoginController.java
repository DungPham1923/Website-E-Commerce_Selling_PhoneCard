
package Controller.authentication;

import Controller.Captcha;
import DAL.AccountDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginController extends BaseRequireAuthentication {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userCaptcha = request.getParameter("captcha");
        HttpSession session = request.getSession();
        //String sessionCaptcha = (String) request.getSession().getAttribute("captcha");

        //sửa từ đây
        Captcha classCaptcha = new Captcha();
        String idCaptcha = (String) request.getParameter("idCaptcha"); //lấy từ thẻ input hidden xuống !!
        String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);

        String remember = request.getParameter("remember");
        AccountDAO Adb = new AccountDAO();
        AccountLogin thisAccount = Adb.getByUsernamePassword(username, password);

        if (thisAccount == null) {
            String wrongAcc = "Tài khoản hoặc mật khẩu sai hoặc không tồn tại!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.setAttribute("wrongCaptcha", ""); // clear captcha error
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (!password.equalsIgnoreCase(thisAccount.getPassword())) {
            String wrongAcc = "Mật khẩu sai!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.setAttribute("wrongCaptcha", ""); // clear captcha error
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if(thisAccount.getIsDelete() == true){
            String wrongAcc = "Tài khoản của bạn đã bị xóa!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.setAttribute("wrongCaptcha", ""); // clear captcha error
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }else if (!userCaptcha.equals(codeCaptcha)) {
            String wrongCaptcha = "Captcha không chính xác!";
            request.setAttribute("wrongCaptcha", wrongCaptcha);
            request.setAttribute("wrongAcc", ""); // clear account error
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            session.setAttribute("account", thisAccount);
            if ("true".equals(remember)) {
                Cookie usernameCookie = new Cookie("username", username);
                Cookie passwordCookie = new Cookie("password", password);
                Cookie c_rem = new Cookie("crem", remember);
                usernameCookie.setMaxAge(3600 * 24 * 7);
                passwordCookie.setMaxAge(3600 * 24 * 7);
                c_rem.setMaxAge(7 * 24 * 3600);
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
                response.addCookie(c_rem);
            } else {
                Cookie usernameCookie = new Cookie("username", "");
                Cookie passwordCookie = new Cookie("password", "");
                Cookie c_rem = new Cookie("crem", "");
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
                response.addCookie(c_rem);
            }
            UserDAO userDao = new UserDAO();
            User user = (User) userDao.getUserById(thisAccount.getUser().getID());

            
            
            String productDetailID = "";
            if (request.getParameter("pid") != null) {
                productDetailID = (String) request.getParameter("pid");
            }
            
            if (productDetailID.isBlank() || productDetailID.isEmpty()) {
                response.sendRedirect("/TheCardWebsite/home.jsp");
            }else{
                response.sendRedirect("productdetail?id=" + productDetailID);
            }
            
        }
        if(session.getAttribute("account") != null){
            classCaptcha.removeCaptchaCode(idCaptcha);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold> 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}
