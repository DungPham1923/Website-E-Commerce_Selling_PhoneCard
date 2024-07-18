package Controller.Order;

import Controller.authentication.BaseRequireAuthentication;
import DAL.OrderDAO;
import DAL.ReportDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.Order;
import Model.OrderDetails;
import Model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

public class DisplayOrderDetails extends BaseRequireAuthentication {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gọi lại phương thức doGet để xử lý phương thức POST như phương thức GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị chi tiết đơn hàng cho một ID đơn hàng cụ thể";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String orderId = request.getParameter("orderdetailsID");
        int oId = 0;
        if (orderId != null) {
            oId = Integer.parseInt(orderId);
        }
        OrderDAO o = new OrderDAO();
        ReportDAO r = new ReportDAO();
        List<OrderDetails> list = o.getAllOrderDetailsByOderId(oId);
        try (PrintWriter out = response.getWriter()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProductcard() != null) {
                    if (r.selectReportExist(list.get(i).getID()) == null) {
                        out.print("                            <tr>\n"
                                + "                                <td> " + (i + 1) + "</td>"
                                + "                                <td>" + list.get(i).getProductCategories().getName() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductcard().getSeri() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductcard().getCode() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductPrice() + " VNĐ</td> \n"
                                + "                                <td>" + list.get(i).getDiscount() + " %</td> \n"
                                + "                                <td>" + list.get(i).getAmount() + " VNĐ</td> \n"
                                + "                                <td><a style='font-size: 13px; color : red' class='btn btn-outline-danger btn-square ml-1'  onclick='reportProduct(" + list.get(i).getID() + ")'> Báo cáo</a></td>\n"
                                + "                            </tr>\n");
                    } else {
                        out.print("                            <tr>\n"
                                + "                                <td> " + (i + 1) + "</td>"
                                + "                                <td>" + list.get(i).getProductCategories().getName() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductcard().getSeri() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductcard().getCode() + "</td>\n"
                                + "                                <td>" + list.get(i).getProductPrice() + " VNĐ</td> \n"
                                + "                                <td>" + list.get(i).getDiscount() + " %</td> \n"
                                + "                                <td>" + list.get(i).getAmount() + " VNĐ</td> \n"
                                + "                                <td> </td>\n"
                                + "                            </tr>\n");
                    }
                } else {
                    out.print("                            <tr>\n"
                            + "                                <td> " + (i + 1) + "</td>"
                            + "                                <td>" + list.get(i).getProductCategories().getName() + "</td>\n"
                            + "                                <td>********</td>\n"
                            + "                                <td>********</td>\n"
                            + "                                <td>" + list.get(i).getProductPrice() + " VNĐ</td> \n"
                            + "                                <td>" + list.get(i).getDiscount() + " %</td> \n"
                            + "                                <td>" + list.get(i).getAmount() + " VNĐ</td> \n"
                            + "                                <td> </td>\n"
                            + "                            </tr>\n");

                }
            }
        }
    }
}
