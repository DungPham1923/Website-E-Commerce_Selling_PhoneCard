package Controller.manageruseraccount;

import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import DAL.OrderDAO;
import Model.AccountLogin;
import Model.OrderDetails;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserDetail extends BaseRequireAuthentication {

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        doGet(req, resp, account);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userID = request.getParameter("userID");
        int uId = 0;
        if (userID != null) {
            uId = Integer.parseInt(userID);
        }
        AccountLoginDAO algDAO = new AccountLoginDAO();
        AccountLogin accountLogin = algDAO.getAccountByID(uId);
        try (PrintWriter out = response.getWriter()) {
            out.print("<div class='container form-container'>"
                    + "<form action='/TheCardWebsite/custom' method='post'>"
                    + "  <div class='form-group'>"
                    + "    <label for='id'>ID người dùng</label>"
                    + "    <input type='text' class='form-control' id='id' name='id' value='" + accountLogin.getID() + "' readonly>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='username'>Mã người dùng</label>"
                    + "    <input type='text' class='form-control' id='username' name='username' value='" + accountLogin.getUserName() + "' readonly>"
                    + "  </div>"
                    + "  <div class='form-group password-wrapper'>"
                    + "    <label for='password'>Mật khẩu</label>"
                    + "    <input type='password' class='form-control' id='password' name='password' value='" + accountLogin.getPassword() + "'>"
                    + "    <i class='fas fa-eye toggle-password' onclick='togglePasswordVisibility()'></i>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='firstname'>Tên người dùng</label>"
                    + "    <input type='text' class='form-control' id='firstname' name='firstname' value='" + accountLogin.getUser().getFirstName() + "'>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='lastname'>Họ người dùng</label>"
                    + "    <input type='text' class='form-control' id='lastname' name='lastname' value='" + accountLogin.getUser().getLastName() + "'>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='email'>Email</label>"
                    + "    <input type='email' class='form-control' id='email' name='email' value='" + accountLogin.getUser().getEmail() + "'>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='phone'>Số điện thoại</label>"
                    + "    <input type='text' class='form-control' id='phone' name='phone' value='" + accountLogin.getUser().getPhone() + "'>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='balance'>Số dư</label>"
                    + "    <input type='text' class='form-control' id='balance' name='balance' value='" + accountLogin.getUser().getBalance() + "'>"
                    + "  </div>"
                    + "  <div class='form-group'>"
                    + "    <label for='createdAt'>Ngày tạo tài khoản</label>"
                    + "    <input type='text' class='form-control' id='createdAt' name='createdAt' value='" + accountLogin.getUser().getCreatedAt() + "' readonly>"
                    + "  </div>"
                    + "  <button type='submit' class='btn btn-primary' name='action' value='update'>Cập nhật thông tin</button>"
                    + "  <button type='submit' class='btn btn-danger' name='action' value='resetpass'>Đặt mật khẩu mặc định</button>"
                    + "</form>"
                    + "</div");
        }
    }

}
