package Controller.ForgotPassword;


import Controller.authentication.BaseRequireAuthentication;
import DAL.EmailDAO;
import Model.AccountLogin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResendEmailForgot extends BaseRequireAuthentication {
 
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        if ("resend".equals(action)) {
            String email = (String) request.getSession().getAttribute("email");
            if (email == null || email.isEmpty()) {
                // Handle case where email is not found in the request
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in request.");
                return;
            }

            String randomString = EmailDAO.generateRandomString();

            // Send an email
            String to = email;
            String tieuDe = "Mã xác thực tài khoản";
            String noiDung = "Chào bạn, mã OTP xác thực tài khoản của bạn là: " + "<b>" + randomString + "</b>" + ". Mã có giá trị trong 60s.";
            boolean emailSent = EmailDAO.sendEmail(to, tieuDe, noiDung);
            HttpSession session = request.getSession();
            if (emailSent) {
                System.out.println("Email sent successfully!");
                // Store the random string in the session to verify later
                request.getSession().setAttribute("otprequest", randomString);
                scheduler.schedule(() -> session.removeAttribute("otprequest"), 60, TimeUnit.SECONDS);
                request.setAttribute("resentSuccess", "Gửi OTP thành công");
            } else {
                System.out.println("Failed to send email.");
            }
        }
        request.getRequestDispatcher("verifyemailrequest.jsp").forward(request, response);
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

}