package Controller.ForgotPassword;

import Controller.authentication.BaseRequireAuthentication;
import Model.AccountLogin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class VerifyPassswordOTP extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//
//        String otp = request.getParameter("otp").trim();
//        HttpSession session = request.getSession();
//        String otpsession = (String) session.getAttribute("otprequest");
//        
//        int count = (int) session.getAttribute("countotp");
//        
//        if(count == 3){
//            session.removeAttribute("otprequest");
//            session.removeAttribute("countotp");
//            request.setAttribute("error", "Bạn đã nhập quá 3 lần mã OTP");
//            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
//        }else if(otp == null || otp.equals("")){
//            count++;
//            session.setAttribute("countotp", count);
//            request.setAttribute("errorotp", "Vui lòng nhập mã OTP .Bạn còn " + (3 - count) +" lần nhập");
//            request.getRequestDispatcher("verifyemail.jsp").forward(request, response);            
//        }else if(!otp.equals(otpsession)){
//            count++;
//            session.setAttribute("countotp", count);
//            request.setAttribute("errorotp", "Vui lòng nhập mã OTP .Bạn còn " + (3 - count) +" lần nhập");
//            request.getRequestDispatcher("verifyemailrequest.jsp").forward(request, response);
//        }else{
//            session.setAttribute("confirmchange", otpsession);
//            request.getRequestDispatcher("changepassword.jsp").forward(request, response);
//        }
//        
//        
//    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        processRequest(request, response);

        String otp = request.getParameter("otp").trim();
        HttpSession session = request.getSession();
        String otpsession = (String) session.getAttribute("otprequest");

        int count = 0;
        if (session.getAttribute("countotp") != null) {
            count = (int) session.getAttribute("countotp");
            if (count == 3) {
                session.removeAttribute("otprequest");
                session.removeAttribute("countotp");
                request.setAttribute("error", "Bạn đã nhập quá 3 lần mã OTP");
                request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
            } else if (otp == null || otp.equals("")) {
                count++;
                session.setAttribute("countotp", count);
                request.setAttribute("errorotp", "Vui lòng nhập mã OTP .Bạn còn " + (3 - count) + " lần nhập");
                request.getRequestDispatcher("verifyemail.jsp").forward(request, response);
            } else if (!otp.equals(otpsession)) {
                count++;
                session.setAttribute("countotp", count);
                request.setAttribute("errorotp", "Vui lòng nhập mã OTP .Bạn còn " + (3 - count) + " lần nhập");
                request.getRequestDispatcher("verifyemailrequest.jsp").forward(request, response);
            } else {
                session.setAttribute("confirmchange", otpsession);
                request.getRequestDispatcher("changepassword.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }

}
