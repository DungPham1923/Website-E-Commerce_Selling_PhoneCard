package Controller.Register;

import Controller.Captcha;
import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import Model.AccountLogin;
import Utils.SendEmail;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterServlet extends HttpServlet {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_.]{6,30}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");

        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("password", password);

        AccountLoginDAO accountDAO = new AccountLoginDAO();
        boolean usernameExist = accountDAO.checkUsernameExist(username);
        boolean emailExist = accountDAO.checkEmailExist(email);
        
        //String generatedCaptcha = (String) request.getSession().getAttribute("captcha");
        Captcha classCaptcha = new Captcha();
        String idCaptcha = (String) request.getParameter("idCaptcha"); //lấy từ thẻ input hidden xuống !!
        String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);
        
        // Validate the username
        if (!isValidUsername(username)) {
            request.setAttribute("usernameError", "Tên người dùng không hợp lệ. Nó phải dài từ 6 đến 30 ký tự và chỉ có thể chứa các chữ cái, số, dấu gạch dưới và dấu chấm");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } 
        // Check if username exists
        else if(usernameExist) {
            request.setAttribute("usernameExist", "Tên người dùng này đã tồn tại");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        // Validate the email before creating a new account
        else if (!isValid(email)) {
            request.setAttribute("invalidEmail", "Địa chỉ email này không hợp lệ");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        // Check if email exists
        else if(emailExist){
            request.setAttribute("emailExist", "Email này đã tồn tại");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        // Validate the password
        else if (!isValidPassword(password)) {
            request.setAttribute("passwordError", "Mật khẩu không hợp lệ. Nó phải dài ít nhất 8 ký tự và chứa ít nhất một chữ số và một chữ cái viết hoa");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        // Check valid captcha
        else if (!captcha.equals(codeCaptcha)) {
            request.setAttribute("invalidCaptcha", "Mã captcha không chính xác!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        // All conditions are true
        else {
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("password", password);
            sendMail(email, request);
            classCaptcha.removeCaptchaCode(idCaptcha);
            //request.getRequestDispatcher("verifyuserregisterservlet").forward(request, response);
            response.sendRedirect("verifyuserregisterservlet");
        }
    }

    // Check valid username
    private boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    
    // Check valid password
    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Add the email validation method
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    // send email
    public static void sendMail(String email, HttpServletRequest request) {
        String randomString = SendEmail.generateRandomString();

        // Send an email
        String to = email; // get the email from the form
        String tieuDe = "Mã xác thực tài khoản";
        String noiDung = "Chào bạn, mã xác thực tài khoản của bạn là: " + randomString + ". Mã chỉ có giá trị trong 120s.";
        boolean emailSent = SendEmail.sendEmail(to, tieuDe, noiDung);

        if (emailSent) {
            System.out.println("Email sent successfully!");

            // Store the random string in the session so we can verify it later
            request.getSession().setAttribute("verifyotp", randomString);
        } else {
            System.out.println("Failed to send email.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
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
