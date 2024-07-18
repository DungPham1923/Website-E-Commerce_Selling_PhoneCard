package Controller.UserProfile;

import Controller.authentication.BaseRequireAuthentication;
import DAL.AccountLoginDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UpdateInfoUserServlet extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateInfoUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateInfoUserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    //Check => By Pass Name
    public String cleanUserName(String name) {
        // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
        name = name.trim();

        // Loại bỏ khoảng trắng thừa giữa các từ
        String[] words = name.split("\\s+");
        String cleanedName = String.join(" ", words);

        return cleanedName;
    }

    //Check phone
    private static final Set<String> VALID_PREFIXES = new HashSet<>(Arrays.asList(
            "086", "096", "097", "098", "039", "038", "037", "036", "035", "034", "033", "032",
            "091", "094", "088", "083", "084", "085", "081", "082", "070", "076", "077", "078",
            "079", "089", "090", "093"
    ));

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra độ dài 10 chữ số
        if (phoneNumber.length() != 10) {
            return false;
        }
        // Kiểm tra xem tất cả các ký tự có phải là chữ số không
        if (!phoneNumber.matches("\\d{10}")) {
            return false;
        }
        // Lấy 3 chữ số đầu tiên
        String prefix = phoneNumber.substring(0, 3);

        // Kiểm tra xem 3 chữ số đầu tiên có thuộc danh sách hợp lệ không
        return VALID_PREFIXES.contains(prefix);
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String firstname = request.getParameter("firstname");
//        String lastname = request.getParameter("lastname");
//        String phone = request.getParameter("phone");
//
//        UserDAO ud = new UserDAO();
//        AccountLoginDAO ald = new AccountLoginDAO();
//        HttpSession session = request.getSession();
//        User user;
//        AccountLogin account = null;
//        GoogleLogin gglogin = null;
//        if (session.getAttribute("account") != null) {
//            account = (AccountLogin) session.getAttribute("account");
//            user = ud.getUserById(account.getUser().getID());
//        } else {
//            gglogin = (GoogleLogin) session.getAttribute("gguser");
//            user = ud.getUserById(gglogin.getUser().getID());
//        }
//
//        String thongbao = "";
//        if (firstname.isEmpty() || firstname == null || firstname.equals("")
//                || lastname.isEmpty() || lastname == null || lastname.equals("")
//                || phone.isEmpty() || phone == null || phone.equals("")) {
//            thongbao = "Vui lòng điền đầy đủ các thông tin!";
//        } else if (!isValidPhoneNumber(phone)) {
//            thongbao = "Số điện thoại không hợp lệ\n"
//                    + "            firstname = cleanUserName(firstname);!";
//        } else {
//            lastname = cleanUserName(lastname);
//            //User user = new User(usersession.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//
//            user = new User(user.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//            ud.updateInfoUser(user);
//            thongbao = "Thông tin đã cập nhật thành công!";
//            User newUserInfo = ud.getUserById(user.getID());
//            AccountLogin acc = ald.getAccountByID(account.getID());
//            request.setAttribute("user", newUserInfo);
//            request.setAttribute("thongbao", thongbao);
//            request.setAttribute("account", acc);
//            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
//        }
//    }


//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        //get thông tin từ browser
//
//        String firstname = request.getParameter("firstname");
//        String lastname = request.getParameter("lastname");
//        String phone = request.getParameter("phone");
//
//        UserDAO ud = new UserDAO();
//        AccountLoginDAO ald = new AccountLoginDAO();
//        HttpSession sess = request.getSession();
//        //User usersession;
//        GoogleLogin gglogin = null;
//        AccountLogin account = null;// = (AccountLogin) sess.getAttribute("account");
//        User user = null;// = (User) ud.getUserById(account.getUser().getID());
//        if (sess.getAttribute("account") != null) {
//            account = (AccountLogin) sess.getAttribute("account");
//            user = (User) ud.getUserById(account.getUser().getID());
//        } else if(sess.getAttribute("gguser") != null){
//            gglogin = (GoogleLogin) sess.getAttribute("gguser");
//            if(gglogin != null){
//                user = (User) ud.getUserById(gglogin.getUser().getID());
//            }  
//        }else{
//            account = null;
//            gglogin = null;
//            user = null;
//            if(account == null || gglogin == null || user == null){
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
//        }
//
//        String thongbao = null;     
//        String error = null;
//        if (firstname.isEmpty() || firstname.equals("") || firstname.trim().isEmpty()
//                || lastname.isEmpty()  || lastname.equals("") || lastname.trim().isEmpty()
//                ) {
//            //|| phone.isEmpty()  || phone.equals("") || phone.trim().isEmpty()
//            error = "Vui lòng đảm bảo các thông tin được điền đầy đủ trước khi ấn cập nhật!";
//        } 
////        else if (!isValidPhoneNumber(phone)) {
////            error = "Số điện thoại không hợp lệ!";
////        } 
//        else {
//            firstname = cleanUserName(firstname);
//            lastname = cleanUserName(lastname);
//            //User user = new User(usersession.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//            if (sess.getAttribute("account") != null) {
//                user = new User(account.getUser().getID(), firstname, lastname, null, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//            } else {
//                user = new User(gglogin.getUser().getID(), firstname, lastname, null, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//            }
//            //user = new User(account.getUser().getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
//            ud.updateInfoUser(user);
//
//            thongbao = "Thông tin đã cập nhật thành công!";
//
//        }
////        User newUserInfo = null;
////        AccountLogin acc = null;
////        GoogleLogin ggl = null;
////        if (sess.getAttribute("account") != null) {
////            newUserInfo = ud.getUserById(account.getUser().getID());
////            acc = ald.getAccountByID(account.getUser().getID());
////        } else {
////            newUserInfo = ud.getUserById(gglogin.getUser().getID());
////        }
//
////        User newUserInfo = ud.getUserById(account.getUser().getID());
////        AccountLogin acc = ald.getAccountByID(account.getUser().getID());
//        //request.setAttribute("user", newUserInfo);
//        request.setAttribute("thongbao", thongbao);
//        request.setAttribute("error", error);
//        //request.setAttribute("account", acc);
//        //String thongbao = "Thông tin đã cập nhật thành công!";
//        //request.setAttribute("thongbao", thongbao);
//        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
//    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //get thông tin từ browser

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");

        UserDAO ud = new UserDAO();
        AccountLoginDAO ald = new AccountLoginDAO();
        HttpSession sess = request.getSession();
        //User usersession;
        GoogleLogin gglogin = null;
        account = null;// = (AccountLogin) sess.getAttribute("account");
        User user = null;// = (User) ud.getUserById(account.getUser().getID());
        if (sess.getAttribute("account") != null) {
            account = (AccountLogin) sess.getAttribute("account");
            user = (User) ud.getUserById(account.getUser().getID());
        } else if(sess.getAttribute("gguser") != null){
            gglogin = (GoogleLogin) sess.getAttribute("gguser");
            if(gglogin != null){
                user = (User) ud.getUserById(gglogin.getUser().getID());
            }  
        }else{
            account = null;
            gglogin = null;
            user = null;
            if(account == null || gglogin == null || user == null){
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

        String thongbao = null;     
        String error = null;
        if (firstname.isEmpty() || firstname.equals("") || firstname.trim().isEmpty()
                || lastname.isEmpty()  || lastname.equals("") || lastname.trim().isEmpty()
                ) {
            //|| phone.isEmpty()  || phone.equals("") || phone.trim().isEmpty()
            error = "Vui lòng đảm bảo các thông tin được điền đầy đủ trước khi ấn cập nhật!";
        } 
//        else if (!isValidPhoneNumber(phone)) {
//            error = "Số điện thoại không hợp lệ!";
//        } 
        else {
            firstname = cleanUserName(firstname);
            lastname = cleanUserName(lastname);
            //User user = new User(usersession.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
            if (sess.getAttribute("account") != null) {
                user = new User(account.getUser().getID(), firstname, lastname, null, phone, 0, null, null, null, null, Boolean.FALSE, 0);
            } else {
                user = new User(gglogin.getUser().getID(), firstname, lastname, null, phone, 0, null, null, null, null, Boolean.FALSE, 0);
            }
            //user = new User(account.getUser().getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
            ud.updateInfoUser(user);

            thongbao = "Thông tin đã cập nhật thành công!";

        }
//        User newUserInfo = null;
//        AccountLogin acc = null;
//        GoogleLogin ggl = null;
//        if (sess.getAttribute("account") != null) {
//            newUserInfo = ud.getUserById(account.getUser().getID());
//            acc = ald.getAccountByID(account.getUser().getID());
//        } else {
//            newUserInfo = ud.getUserById(gglogin.getUser().getID());
//        }

//        User newUserInfo = ud.getUserById(account.getUser().getID());
//        AccountLogin acc = ald.getAccountByID(account.getUser().getID());
        //request.setAttribute("user", newUserInfo);
        request.setAttribute("thongbao", thongbao);
        request.setAttribute("error", error);
        //request.setAttribute("account", acc);
        //String thongbao = "Thông tin đã cập nhật thành công!";
        //request.setAttribute("thongbao", thongbao);
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");

        UserDAO ud = new UserDAO();
        AccountLoginDAO ald = new AccountLoginDAO();
        HttpSession session = request.getSession();
        User user;
        account = null;
        GoogleLogin gglogin = null;
        if (session.getAttribute("account") != null) {
            account = (AccountLogin) session.getAttribute("account");
            user = ud.getUserById(account.getUser().getID());
        } else {
            gglogin = (GoogleLogin) session.getAttribute("gguser");
            user = ud.getUserById(gglogin.getUser().getID());
        }

        String thongbao = "";
        if (firstname.isEmpty() || firstname == null || firstname.equals("")
                || lastname.isEmpty() || lastname == null || lastname.equals("")
                || phone.isEmpty() || phone == null || phone.equals("")) {
            thongbao = "Vui lòng điền đầy đủ các thông tin!";
        } else if (!isValidPhoneNumber(phone)) {
            thongbao = "Số điện thoại không hợp lệ\n"
                    + "            firstname = cleanUserName(firstname);!";
        } else {
            lastname = cleanUserName(lastname);
            //User user = new User(usersession.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);

            user = new User(user.getID(), firstname, lastname, phone, phone, 0, null, null, null, null, Boolean.FALSE, 0);
            ud.updateInfoUser(user);
            thongbao = "Thông tin đã cập nhật thành công!";
            User newUserInfo = ud.getUserById(user.getID());
            AccountLogin acc = ald.getAccountByID(account.getID());
            request.setAttribute("user", newUserInfo);
            request.setAttribute("thongbao", thongbao);
            request.setAttribute("account", acc);
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
        }
    }

}
