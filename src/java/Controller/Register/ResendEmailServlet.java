package Controller.Register;

import Controller.authentication.BaseRequireAuthentication;
import Model.AccountLogin;
import Utils.SendEmail;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResendEmailServlet extends HttpServlet {

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

            String randomString = SendEmail.generateRandomString();

            // Send an email
            String to = email;
            String tieuDe = "Mã xác thực tài khoản";
            String noiDung = "Chào bạn, mã xác thực tài khoản của bạn là: " + randomString + ". Mã chỉ có giá trị trong 120s.";
            boolean emailSent = SendEmail.sendEmail(to, tieuDe, noiDung);

            if (emailSent) {
                System.out.println("Email sent successfully!");
                // Store the random string in the session to verify later
                request.getSession().setAttribute("verifyotp", randomString);
                request.setAttribute("resentSuccess", "Gửi OTP thành công");
            } else {
                System.out.println("Failed to send email.");
            }
        }
        request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
//        processRequest(req, resp);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
//        processRequest(req, resp);
//    }

}
