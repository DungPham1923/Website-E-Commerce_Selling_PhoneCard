package Controller.ForgotPassword;

import Controller.Captcha;
import Controller.authentication.BaseRequireAuthentication;
import DAL.EmailDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendCodeToEmail extends BaseRequireAuthentication {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        processRequest(request, response);

        String email = request.getParameter("email");
        String captcha = request.getParameter("captcha");
        
        HttpSession session = request.getSession();
        //String captchasession = (String) session.getAttribute("captcha");
        Captcha classCaptcha = new Captcha();
        String idCaptcha = (String) request.getParameter("idCaptcha"); //lấy từ thẻ input hidden xuống !!
        String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);
        // check rỗng
        if (email == null || email.equals("") || captcha == null || captcha.equals("")) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        }

        UserDAO dao = new UserDAO();
        User useremail = dao.getUserbyEmail(email);
        
        
        int count = 0;
        session.setAttribute("countotp", count);
        // check email tồn tại
        if (useremail == null) {
            request.setAttribute("error", "Email không tồn tại");
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        } else if(!captcha.equals(codeCaptcha)){
            request.setAttribute("error", "Sai mã captcha");
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        }else{
            int userid = useremail.getID();
            String otprequest = EmailDAO.generateRandomString();
            session.setAttribute("userid", userid);
            session.setAttribute("otprequest", otprequest);
            scheduler.schedule(() -> session.removeAttribute("otprequest"), 60, TimeUnit.SECONDS);
            session.setAttribute("email", email);
            request.setAttribute("email", email);
            request.getRequestDispatcher("verifyemailrequest.jsp").forward(request, response);
            classCaptcha.removeCaptchaCode(idCaptcha);
            EmailDAO.sendEmail(email, "Mã xác nhận đặt lại mật khẩu" + "", "Chào bạn, mã OTP xác thực tài khoản của bạn là: " + "<b>" + otprequest + "</b>" + ". Mã có giá trị trong 60s.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }

}
