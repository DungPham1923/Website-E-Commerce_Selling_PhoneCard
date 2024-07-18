package Controller.Register;

import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import Model.AccountLogin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VerifyUserRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");

        if (email == null || username == null || password == null) {
            response.sendRedirect("register.jsp"); // Redirect to registration page if any value is missing
            return;
        }

        // Set attributes for forwarding to JSP
        request.setAttribute("email", email);
        request.setAttribute("username", username);
        request.setAttribute("password", password);

        request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = (String) request.getSession().getAttribute("verifyotp");
        String verifyotp = request.getParameter("otp");
        String username = (String) request.getSession().getAttribute("username");
        String email = (String) request.getSession().getAttribute("email");
        String password = (String) request.getSession().getAttribute("password");

        // Store OTP in a cookie
        Cookie otpCookie = new Cookie("otp", otp);
        otpCookie.setMaxAge(60); 
        response.addCookie(otpCookie);

        // When verifying OTP
        Cookie[] cookies = request.getCookies();
        String storedOtp = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("otp")) {
                storedOtp = cookie.getValue();
            }
        }

        if (otp != null && !otp.isEmpty() && verifyotp != null) {
            if (otp.equals(verifyotp) && storedOtp != null) {
                AccountLoginDAO accountDAO = new AccountLoginDAO();
                accountDAO.register(username, email, password);
                request.setAttribute("verifySuccess", "Bạn đã đăng ký thành công tài khoản của mình. Click OK để trở về trang đăng nhập");
                request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
                //response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("invalidOTP", "Mã OTP không chính xác");
                // Re-forward to the same JSP with the invalid OTP message
                request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("register.jsp"); // Redirect to registration page if OTP or verifyotp is missing
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String email = (String) request.getSession().getAttribute("email");
//        String username = (String) request.getSession().getAttribute("username");
//        String password = (String) request.getSession().getAttribute("password");
//
//        if (email == null || username == null || password == null) {
//            response.sendRedirect("register.jsp"); // Redirect to registration page if any value is missing
//            return;
//        }
//
//        // Set attributes for forwarding to JSP
//        request.setAttribute("email", email);
//        request.setAttribute("username", username);
//        request.setAttribute("password", password);
//
//        request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String otp = (String) request.getSession().getAttribute("verifyotp");
//        String verifyotp = request.getParameter("otp");
//        String username = (String) request.getSession().getAttribute("username");
//        String email = (String) request.getSession().getAttribute("email");
//        String password = (String) request.getSession().getAttribute("password");
//
//        // Store OTP in a cookie
//        Cookie otpCookie = new Cookie("otp", otp);
//        otpCookie.setMaxAge(60); 
//        response.addCookie(otpCookie);
//
//        // When verifying OTP
//        Cookie[] cookies = request.getCookies();
//        String storedOtp = null;
//
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("otp")) {
//                storedOtp = cookie.getValue();
//            }
//        }
//
//        if (otp != null && !otp.isEmpty() && verifyotp != null) {
//            if (otp.equals(verifyotp) && storedOtp != null) {
//                AccountLoginDAO accountDAO = new AccountLoginDAO();
//                accountDAO.register(username, email, password);
//                request.setAttribute("verifySuccess", "Bạn đã đăng ký thành công tài khoản của mình. Click OK để trở về trang đăng nhập");
//                request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
//                response.sendRedirect("login.jsp");
//
//            } else {
//                request.setAttribute("invalidOTP", "Mã OTP không chính xác");
//                // Re-forward to the same JSP with the invalid OTP message
//                request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
//            }
//        } else {
//            response.sendRedirect("register.jsp"); // Redirect to registration page if OTP or verifyotp is missing
//        }
//    }
}
