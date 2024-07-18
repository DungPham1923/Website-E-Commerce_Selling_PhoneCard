
package Controller.CheckoutByBanking;

import Controller.authentication.BaseRequireAuthentication;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CheckoutBankingReturn extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckoutBankingReturn</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutBankingReturn at " + request.getContextPath () + "</h1>");
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_BankCode = request.getParameter("vnp_BankCode");
        String vnp_PayDate = request.getParameter("vnp_PayDate");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        UserDAO userDao = new UserDAO();
        User userDeposit = userDao.getUserById(account.getUser().getID());
        
        
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        String calculatedHash = Config.hashAllFields(fields);

        if (calculatedHash.equals(vnp_SecureHash)) {
            String statusMessage;
            if ("00".equals(vnp_TransactionStatus)) {
                statusMessage = "Thành công";
            } else {
                statusMessage = "Không thành công";
            }

            response.getWriter().println("Mã giao dịch thanh toán: " + vnp_TxnRef);
            response.getWriter().println("Số tiền: " + vnp_Amount);
            response.getWriter().println("Mô tả giao dịch: " + vnp_OrderInfo);
            response.getWriter().println("Mã lỗi thanh toán: " + vnp_ResponseCode);
            response.getWriter().println("Mã giao dịch tại CTT VNPAY-QR: " + vnp_TransactionNo);
            response.getWriter().println("Mã ngân hàng thanh toán: " + vnp_BankCode);
            response.getWriter().println("Thời gian thanh toán: " + vnp_PayDate);
            response.getWriter().println("Tình trạng giao dịch: " + statusMessage);

//            request.setAttribute("transactionStatus", statusMessage);
//            request.getRequestDispatcher("/paymentResult.jsp").forward(request, response);
        } else {
            System.out.println("Chữ ký không hợp lệ");
            response.getWriter().println("Invalid signature");
        }
    }

}
