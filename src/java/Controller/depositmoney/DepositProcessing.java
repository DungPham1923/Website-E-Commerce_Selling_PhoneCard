/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.depositmoney;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import DAL.TransactionDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.Order;
import Model.Transaction;
import Model.User;
import Utils.DateTimeHelper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.jstl.core.Config;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dat
 */
public class DepositProcessing extends BaseRequireAuthentication {

    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response, AccountLogin account)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");
        double amount = Double.parseDouble(vnp_Amount); 
        double dividedAmount = amount / 100;
        String dividedAmountStr = String.valueOf(dividedAmount);
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_BankCode = request.getParameter("vnp_BankCode");
        String vnp_PayDate = request.getParameter("vnp_PayDate");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        UserDAO userDao = new UserDAO();
        User userDeposit = userDao.getUserById(account.getUser().getID());
        OrderDAO orderDAO = new OrderDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

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
        String calculatedHash = DepositConfig.hashAllFields(fields);

        if (calculatedHash.equals(vnp_SecureHash)) {
            String statusMessage;
            if ("00".equals(vnp_TransactionStatus)) {
                statusMessage = "Success";
            } else {
                statusMessage = "Failed";
            }
            userDao.updateBalance(userDeposit.getID(), dividedAmountStr);
            Timestamp createdAt = getCurrentTimestamp();
            DateTimeHelper dateTimeHelper = new DateTimeHelper();
            java.sql.Date createTime = dateTimeHelper.convertUtilDateToSqlDate(createdAt);

            Order orderTransaction = new Order(0, userDeposit.getID(), 1, dividedAmount, "Paid", createTime, null, null, false, 0);
            int orderNew = orderDAO.insertOrder(orderTransaction);
            Order newestOrder = orderDAO.getOrderByOrderID(orderNew);
            
            transactionDAO.insertTransaction(userDeposit, newestOrder, dividedAmount, "Deposit", vnp_TxnRef, vnp_BankCode, statusMessage);
            
            String name = userDeposit.getFirstName() +  userDeposit.getLastName();
            request.setAttribute("username", name);
            request.setAttribute("transactiontype", "Nạp tiền");
            request.setAttribute("ordercode", orderNew);
            request.setAttribute("description", "Nạp tiền vào ví bằng VNPay");
            request.setAttribute("amount", newestOrder.getTotalAmount());
            
            request.setAttribute("transactioncode", vnp_TxnRef);
            request.setAttribute("amountpayment", dividedAmount);
            request.setAttribute("descriptionpayment", vnp_OrderInfo);
            request.setAttribute("bankcode", vnp_BankCode);
            request.setAttribute("timepayment", vnp_PayDate);
            request.setAttribute("status", statusMessage);

            request.getRequestDispatcher("/paymentbanking.jsp").forward(request, response);
        } else {
            System.out.println("Chữ ký không hợp lệ");
            response.getWriter().println("Invalid signature");
        }

    }

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp, account);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp, account);
    }

}
