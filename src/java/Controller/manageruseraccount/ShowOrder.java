package Controller.manageruseraccount;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import Model.AccountLogin;
import Model.OrderDetails;
import Utils.DateTimeHelper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ShowOrder extends BaseRequireAuthentication {

    private java.util.Date convertSqlDateToUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        doPost(req, resp, account);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userID = request.getParameter("userID");
        int aId = 0;
        if (userID != null) {
            try {
                aId = Integer.parseInt(userID);
            } catch (NumberFormatException e) {
                response.getWriter().print("Invalid userID format.");
                return;
            }
        }
        OrderDAO o = new OrderDAO();
        List<OrderDetails> listOrder = o.getAllOrderDetailByUserID(aId);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (PrintWriter out = response.getWriter()) {
            if (listOrder != null && !listOrder.isEmpty()) {
                for (int i = 0; i < listOrder.size(); i++) {
                    OrderDetails orderDetails = listOrder.get(i);
                    String status = orderDetails.getOrder().getStatus();
                    String statusText = "";
                    String statusColor = "";
                    if ("Unpaid".equals(status)) {
                        statusText = "Chưa thanh toán";
                        statusColor = "red";
                    } else if ("Paid".equals(status)) {
                        statusText = "Đã thanh toán";
                        statusColor = "green";
                    } else {
                        statusText = status; // Default to the original status text if not "Unpaid" or "Paid"
                        statusColor = "black"; // Default color
                    }
                    out.print("<tr>\n"
                            + "    <td>" + (i + 1) + "</td>\n"
                            + "    <td>" + orderDetails.getAmount() + " VNĐ</td>\n"
                            + "    <td style='color: " + statusColor + "'>" + statusText + "</td>\n"
                            + "    <td>" + (orderDetails.getOrder() != null && orderDetails.getOrder().getCreatedAt() != null ? dateFormatter.format(orderDetails.getOrder().getCreatedAt().toLocalDate()) : "Unknown") + "</td>\n"
                            + "</tr>\n");
                }
            } else {
                out.print("<tr>\n"
                        + "    <td colspan=\"4\">Người dùng chưa tạo đơn hàng nào!</td>\n"
                        + "</tr>\n");
            }
        }
    }

}
