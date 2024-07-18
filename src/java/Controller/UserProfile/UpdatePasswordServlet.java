package Controller.UserProfile;

import Controller.Captcha;
import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatePasswordServlet extends BaseRequireAuthentication {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdatePasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public static boolean isStrongPassword(String password) {
        // Độ dài tối thiểu của mật khẩu
        if (password.length() < 8) {
            return false;
        }

        // Các biến kiểm tra các tiêu chí khác nhau
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Các ký tự đặc biệt
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",.<>?/";

        // Duyệt qua từng ký tự trong mật khẩu
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialChars.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }

        // Kiểm tra xem tất cả các điều kiện đều đúng
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        //get các  trường thông tin
//        String captcha = request.getParameter("captcha");
//        String oldpassword = request.getParameter("oldpassword");
//        String newpassword = request.getParameter("newpassword");
//        String renewpassword = request.getParameter("renewpassword");
//
//        //get captcha session
//        HttpSession sess = request.getSession();
//        String captchaSess = (String) sess.getAttribute("captcha");
//
//        //get account
//        AccountLoginDAO ald = new AccountLoginDAO();
//        //AccountLogin acc = ald.getAccountByID(1);
//
//        //lấy session
//        AccountLogin account = null;
//        GoogleLogin gglogin = null;
//        User user = null;
//        UserDAO userDao = new UserDAO();
//
//        //user = (User) userDao.getUserById(account.getUser().getID());
//
//        String thongbao = "";
//
//        if (sess.getAttribute("account") != null) {
//            account = (AccountLogin) sess.getAttribute("account");
//            user = (User) userDao.getUserById(account.getUser().getID());
//        }else {
//            account = null;
//            gglogin = null;
//            user = null;
//            if (account == null || user == null) {
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
//        }
//
//        if (account.getPassword() != null) {
//            if (!account.getPassword().equalsIgnoreCase(oldpassword)) {
//                thongbao = "Mật khẩu cũ không chính xác!";
//            } else if (isStrongPassword(newpassword)) {
//                thongbao += "Mật khẩu mới không hợp lệ!";
//            } else if (!newpassword.equalsIgnoreCase(renewpassword)) {
//                thongbao += "Mật khẩu nhập lại không chính xác!";
//            } else {
//                thongbao += "";
//            }
//        } else {
//            thongbao = "null rồi";
//        }
////        if (sess.getAttribute("account") != null) {
////            account = (AccountLogin) sess.getAttribute("account");
////            
////        } else {
////            account = null;
////            if (account == null) {
////                request.getRequestDispatcher("login.jsp").forward(request, response);
////            }
////        }
//
//        //check validate
//        //String thongbao = acc.getPassword();
//        request.setAttribute("thongbao", thongbao);
//
////        User user = userDao.getUserById(account.getUser().getID());
//        //request.setAttribute("user", user);
//        //request.setAttribute("account", account);
//        //sess.invalidate();
//        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String captcha = request.getParameter("captcha");
//        String oldpassword = request.getParameter("oldpassword");
//        String newpassword = request.getParameter("newpassword");
//        String renewpassword = request.getParameter("renewpassword");
//
//        //get captcha session
//        UserDAO u = new UserDAO();
//        HttpSession sess = request.getSession();
//        String captchaSess = (String) sess.getAttribute("captcha");
//
//        //get account
//        //User x = (User) sess.getAttribute("account");
//        AccountLoginDAO ald = new AccountLoginDAO();
//        AccountLogin account = null;
//        if(sess.getAttribute("account") != null){
//            account = (AccountLogin) sess.getAttribute("account");
//        }else{
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        }
//        //test
//        User x = u.getUserById(account.getUser().getID());
//        //check validate
//        //String thongbao = acc.getPassword();
//        String thongbao = "";
//        String error = "";
//        if (!account.getPassword().equalsIgnoreCase(oldpassword)) {
//            error = "Mật khẩu cũ không chính xác!";
//            request.setAttribute("oldpassword", oldpassword);
//            request.setAttribute("newpassword", newpassword);
//            request.setAttribute("renewpassword", renewpassword);
//        } else if (!isValidPassword(newpassword)) {   
//            error = "Mật khẩu mới không hợp lệ!";
//            request.setAttribute("oldpassword", oldpassword);
//            request.setAttribute("newpassword", newpassword);
//            request.setAttribute("renewpassword", renewpassword);
//        } else if (!newpassword.equalsIgnoreCase(renewpassword)) {
//            error = "Mật khẩu nhập lại không chính xác!";
//        } else if (!captcha.equalsIgnoreCase(captchaSess)) {
//            request.setAttribute("oldpassword", oldpassword);
//            request.setAttribute("newpassword", newpassword);
//            request.setAttribute("renewpassword", renewpassword);
//            error = "Vui lòng điền đúng captcha!";
//        } else {
//            AccountLogin accnew = new AccountLogin(account.getID(),
//                    x,
//                    account.getUserName(),
//                    newpassword,
//                    null,
//                    null,
//                    Boolean.FALSE,
//                    0);
//            ald.updateAccount(accnew);
//            thongbao = "Thông tin mật khẩu của bạn đã được cập nhật!";
//        }
//        request.setAttribute("thongbao", thongbao);
//        request.setAttribute("error", error);
//        
//        sess.removeAttribute("captcha");
//        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
//    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String captcha = request.getParameter("captcha");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String renewpassword = request.getParameter("renewpassword");
        UserDAO u = new UserDAO();
        HttpSession sess = request.getSession();

        Captcha classCaptcha = new Captcha();
        String idCaptcha = (String) request.getParameter("idCaptcha"); //lấy từ thẻ input hidden xuống !!
        String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);

        AccountLoginDAO ald = new AccountLoginDAO();
        account = null;
        if(sess.getAttribute("account") != null){
            account = (AccountLogin) sess.getAttribute("account");
        }else{
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        User x = u.getUserById(account.getUser().getID());
        String thongbao = "";
        String error = "";
        if (!account.getPassword().equalsIgnoreCase(oldpassword)) {
            error = "Mật khẩu cũ không chính xác!";
            request.setAttribute("oldpassword", oldpassword);
            request.setAttribute("newpassword", newpassword);
            request.setAttribute("renewpassword", renewpassword);
        } else if (!isValidPassword(newpassword)) {   
            error = "Mật khẩu mới không hợp lệ!";
            request.setAttribute("oldpassword", oldpassword);
            request.setAttribute("newpassword", newpassword);
            request.setAttribute("renewpassword", renewpassword);
        } else if (!newpassword.equalsIgnoreCase(renewpassword)) {
            error = "Mật khẩu nhập lại không chính xác!";
        } else if (!captcha.equalsIgnoreCase(codeCaptcha)) {
            request.setAttribute("oldpassword", oldpassword);
            request.setAttribute("newpassword", newpassword);
            request.setAttribute("renewpassword", renewpassword);
            error = "Vui lòng điền đúng captcha!";
        } else {
            AccountLogin accnew = new AccountLogin(account.getID(),
                    x,
                    account.getUserName(),
                    newpassword,
                    null,
                    null,
                    Boolean.FALSE,
                    0);
            ald.updateAccount(accnew);
            thongbao = "Thông tin mật khẩu của bạn đã được cập nhật!";
            classCaptcha.removeCaptchaCode(idCaptcha);
        }
        request.setAttribute("thongbao", thongbao);
        request.setAttribute("error", error);
        
        sess.removeAttribute("captcha");
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String captcha = request.getParameter("captcha");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String renewpassword = request.getParameter("renewpassword");

        //get captcha session
        HttpSession sess = request.getSession();
        String captchaSess = (String) sess.getAttribute("captcha");

        //get account
        AccountLoginDAO ald = new AccountLoginDAO();
        //AccountLogin acc = ald.getAccountByID(1);

        //lấy session
        account = null;
        GoogleLogin gglogin = null;
        User user = null;
        UserDAO userDao = new UserDAO();

        //user = (User) userDao.getUserById(account.getUser().getID());

        String thongbao = "";

        if (sess.getAttribute("account") != null) {
            account = (AccountLogin) sess.getAttribute("account");
            user = (User) userDao.getUserById(account.getUser().getID());
        }else {
            account = null;
            gglogin = null;
            user = null;
            if (account == null || user == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

        if (account.getPassword() != null) {
            if (!account.getPassword().equalsIgnoreCase(oldpassword)) {
                thongbao = "Mật khẩu cũ không chính xác!";
            } else if (isStrongPassword(newpassword)) {
                thongbao += "Mật khẩu mới không hợp lệ!";
            } else if (!newpassword.equalsIgnoreCase(renewpassword)) {
                thongbao += "Mật khẩu nhập lại không chính xác!";
            } else {
                thongbao += "";
            }
        } else {
            thongbao = "null rồi";
        }
//        if (sess.getAttribute("account") != null) {
//            account = (AccountLogin) sess.getAttribute("account");
//            
//        } else {
//            account = null;
//            if (account == null) {
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
//        }

        //check validate
        //String thongbao = acc.getPassword();
        request.setAttribute("thongbao", thongbao);

//        User user = userDao.getUserById(account.getUser().getID());
        //request.setAttribute("user", user);
        //request.setAttribute("account", account);
        //sess.invalidate();
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }

}
