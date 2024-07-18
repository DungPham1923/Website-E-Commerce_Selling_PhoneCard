/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.changepassword;

import Controller.Captcha;
import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import Model.AccountLogin;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Changepassword extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet changepassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changepassword at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String newPass = request.getParameter("newpassword");
//        String confirmPass = request.getParameter("confirmpassword");
//        String captcha = request.getParameter("captcha");
//
//        HttpSession session = request.getSession();
//        String sessCaptcha = (String) session.getAttribute("captcha");
//        int userId = 0;
//        try {
//            if (session.getAttribute("userid") != null) {
//                userId = (int) session.getAttribute("userid");
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        if (session.getAttribute("confirmchange") != null) {
//            if (newPass == null || confirmPass == null || newPass.equals("") || confirmPass.equals("")) {
//                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
//                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
//            } else if (!newPass.equals(confirmPass)) {
//                request.setAttribute("error", "Nhập lại mật khẩu không chính xác!");
//                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
//            } else if (!isValidPassword(newPass)) {
//                request.setAttribute("error", "Mật khẩu phải lớn hơn 8 kí tự, bao gồm hoa thường và số!");
//                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
//            } else if (!captcha.equals(sessCaptcha)) {
//                request.setAttribute("error", "Captcha nhập không đúng!");
//                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
//            } else {
//                AccountLoginDAO acc = new AccountLoginDAO();
//                acc.changePass(userId, newPass);
//                request.setAttribute("confirm", "Đổi mật khẩu thành công, vui lòng đăng nhập lại để tiếp tục!");
//                session.removeAttribute("confirmchange");
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
//        } else {
//            request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
//        }
//
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String newPass = request.getParameter("newpassword");
        String confirmPass = request.getParameter("confirmpassword");
        String captcha = request.getParameter("captcha");

        HttpSession session = request.getSession();
        //String sessCaptcha = (String) session.getAttribute("captcha");
        Captcha classCaptcha = new Captcha();
        String idCaptcha = (String) request.getParameter("idCaptcha"); //lấy từ thẻ input hidden xuống !!
        String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);
        int userId = 0;
        try {
            if (session.getAttribute("userid") != null) {
                userId = (int) session.getAttribute("userid");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (session.getAttribute("confirmchange") != null) {
            if (newPass == null || confirmPass == null || newPass.equals("") || confirmPass.equals("")) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            } else if (!newPass.equals(confirmPass)) {
                request.setAttribute("error", "Nhập lại mật khẩu không chính xác!");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            } else if (!isValidPassword(newPass)) {
                request.setAttribute("error", "Mật khẩu phải lớn hơn 8 kí tự, bao gồm hoa thường và số!");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            } else if (!captcha.equals(codeCaptcha)) {
                request.setAttribute("error", "Captcha nhập không đúng!");
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            } else {
                AccountLoginDAO acc = new AccountLoginDAO();
                acc.changePass(userId, newPass);
                request.setAttribute("confirm", "Đổi mật khẩu thành công, vui lòng đăng nhập lại để tiếp tục!");
                session.removeAttribute("confirmchange");
                classCaptcha.removeCaptchaCode(idCaptcha);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
