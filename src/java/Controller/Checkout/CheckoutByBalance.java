package Controller.Checkout;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CartItemDAO;
import DAL.OrderDAO;
import DAL.ProductCardDAO;
import DAL.ProductCategoriesDAO;
import DAL.TransactionDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import Model.GoogleLogin;
import Model.Order;
import Model.OrderDetails;
import Model.ProductCard;
import Model.ProductCategories;
import Model.Transaction;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CheckoutByBalance extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckoutByBalance</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutByBalance at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        UserDAO userDao = new UserDAO();
        OrderDAO orderDao = new OrderDAO();
        CartItemDAO cid = new CartItemDAO();
        ProductCardDAO pdcDao = new ProductCardDAO();
        account = (AccountLogin) request.getSession().getAttribute("account");

        if (account == null) {
            out.print("<h5>Vui lòng đăng nhập để tiếp tục " + "<a href=\"login.jsp\">Tại đây</a> </h5>");
        } else {
            User user = userDao.getUserById(account.getUser().getID());
            double balance = user.getBalance();
            Order order = orderDao.getOrderNewByUserID(user.getID());
            double totalMoney = order.getTotalAmount();
            if (balance < totalMoney) {
                out.print("<h6>Số dư không đủ, vui lòng nạp tiền vào tài khoản <a href=\"login.jsp\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
            } else {
                userDao.checkoutByBalance(user.getID(), totalMoney);//Trừ tiền
                //Update trạng thái thanh toán trong order
                orderDao.updateStatusOrder(order.getId());

                //Thêm thẻ vào order detail
                List<OrderDetails> listDetail = orderDao.getAllOrderDetailsByOderId(order.getId());
                for (int i = 0; i < listDetail.size(); i++) {
                    int productcartegoiesId = listDetail.get(i).getProductCategories().getId();
                    ProductCard pdc = pdcDao.getProductCardbyIDQuantity2(productcartegoiesId, 1);
                    orderDao.insertProductCartOrderDetail(pdc.getID(), listDetail.get(i).getID());
                    pdcDao.updateIsDeleteProductCard(pdc.getID());
                }
                //Xóa giỏ
                out.print("<h6 style=\"font-family: sans-serif; color: red;\"> -" + totalMoney + "</h6>");
                out.print("<h6>Thanh toán thành công, xem thông tin đơn hàng <a href=\"order\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
            }
        }
    }

    //Bắt đầu update
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();
    private ProductCardDAO pdcDAO = new ProductCardDAO();
    private ProductCategoriesDAO productCateDAO = new ProductCategoriesDAO();
    private static ConcurrentLinkedQueue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private static boolean processing = false;

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//
//        //Các lớp DAO
//        UserDAO userDao = new UserDAO();
//        OrderDAO orderDao = new OrderDAO();
//        CartItemDAO cid = new CartItemDAO();
//        ProductCardDAO pdcDao = new ProductCardDAO();
//        TransactionDAO transDao = new TransactionDAO();
//        account = (AccountLogin) request.getSession().getAttribute("account");
//        GoogleLogin gglogin = (GoogleLogin) request.getSession().getAttribute("gguser");
//        String orderIdString = (String) request.getParameter("orderId");
//
//        //Verify role
//        try {
//            User user = new User();
//            if (account == null && gglogin == null) {
//                out.print("<h5>Vui lòng đăng nhập để tiếp tục " + "<a href=\"login.jsp\">Click</a> </h5>");
//            } else if (account != null) {
//                user = userDao.getUserById(account.getUser().getID());
//            } else if (gglogin != null) {
//                user = userDao.getUserById(gglogin.getUser().getID());
//            }
//
//            Order pendingOrder = orderDAO.getOrderNewByID(orderIdString);
//            if (pendingOrder != null && pendingOrder.getStatus().equals("Paid")) {
//                out.print("<h5>Đơn hàng này đã được thanh toán! </h5>");
//                return;
//            }
//
//            //Trước khi add queue, kiểm tra xem order này có đủ điều kiện thanh toán: Unpaid + Còn thẻ
//            Map<Integer, Integer> productCategoryCount = new HashMap<>();
//            List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(pendingOrder.getId());
//            if (!listOrderTest.isEmpty()) {
//                for (int i = 0; i < listOrderTest.size(); i++) {
//                    int productcartegoiesId = listOrderTest.get(i).getProductCategories().getId();
//                    productCategoryCount.put(productcartegoiesId, productCategoryCount.getOrDefault(productcartegoiesId, 0) + 1);
//                }
//            }
//            boolean alreadyCheckout = true;
//            for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
//                int productCategoriesId = entry.getKey();
//                int quantityRequired = entry.getValue();
//                alreadyCheckout = productCateDAO.checkQuantityValid(productCategoriesId, quantityRequired);
//
//                if (!alreadyCheckout) {
//                    out.print("<h6 style=\"font-family: sans-serif; color: red;\">Sản phẩm " + productCateDAO.getNameProductCategoriesByID(productCategoriesId)
//                            + " có số lượng yêu cầu lớn hơn số lượng hiện có của hệ thống! Không thể tiến hành thanh toán!</h6>");
//                    break;
//                }
//            }
//            if (!alreadyCheckout) { //tồn tại lỗi quantity. cho return
//                return;
//            }
//
//            orderQueue.add(pendingOrder);
//            //processOrderInQueue(user, out);
//            if (processing) {
//                out.print("<h6>Đơn hàng đã được thêm vào yêu cầu xử lý! Vui lòng đợi xử lý trong giây lát!</h6>");
//                //return;
//            }
//            processing = true;
//            while (!orderQueue.isEmpty()) {
//                Order order = orderQueue.poll();
//                Thread.sleep(2000);
//                double totalPayment = order.getTotalAmount();
//                if (user.getBalance() < totalPayment) {
//                    out.print("<h6>Số dư không đủ, vui lòng nạp tiền vào tài khoản để tiếp tục <a href=\"deposit.jsp\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
//                } else {
//                    userDAO.checkoutByBalance(user.getID(), totalPayment);//Trừ tiền
//                    if (order.getStatus().equals("Unpaid")) {
//                        orderDAO.updateStatusOrder(order.getId());
//                        out.print("<h6>Cập nhật trạng thái đơn hàng thành công!</h6>");
//                    }
//                    //Thêm thẻ vào order detail
//                    List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
//                    if (!listDetail.isEmpty()) {
//                        for (int i = 0; i < listDetail.size(); i++) {
//                            //xem nó muốn thẻ nào
//                            int productcartegoiesId = listDetail.get(i).getProductCategories().getId();
//                            //out.print("<h6>Lấy được thể loại sản phẩm thành công!</h6>");
//                            //vào bảng productcard lấy ra thẻ đó
//                            ProductCard pdc = pdcDAO.getProductCardbyIDQuantity2(productcartegoiesId, 1);
//                            //out.print("<h6>Lấy được thẻ thành công!</h6>");
//                            //thêm thẻ này vào orderdetail
//                            if (pdc.getIsDelete() == false) {
//                                //out.print("<h6>Thẻ này chưa bị xóa!</h6>");
//                            } else {
//                                //out.print("<h6>Thẻ này đã xóa!</h6>");
//                            }
//                            orderDAO.insertProductCartOrderDetail(pdc.getID(), listDetail.get(i).getID());
//                            //out.print("<h6>Thêm thẻ thành công!</h6>");
//                            //thêm xong thì update trạng thái thẻ này đã bán
//                            pdcDAO.updateIsDeleteProductCard(pdc.getID());
//                            //out.print("<h6>Cập nhật thẻ thành công!</h6>");
//
//                            //cập nhật số lượng thẻ trong productcateogies
//                            for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
//                                int productCategoriesId = entry.getKey();
//                                int quantityRequired = entry.getValue();
//                                productCateDAO.updateQuantityAfterCheckout(productCategoriesId, quantityRequired);
//                            }
//                            //out.print("<h6>Cập nhật số lượng còn lại thành công!</h6>");   
//                        }              
//                    } else {
//                        out.print("<h6>Xử lí thất bại, thanh toán lại sau!</h6>");
//                    }
//                    //Cập nhật transaction
//                    Transaction trans = new Transaction(user.getID(), 3, totalPayment, "Payment", null, null, "Success");
//                    transDao.addTransitionAfterCheckoutByBalance(trans);
//                    out.print("<h6 style=\"font-family: sans-serif; color: red;\"> Số dư tài khoản: -" + totalPayment + " VNĐ</h6>");
//                    out.print("<h6>Thanh toán thành công, xem thông tin đơn hàng <a href=\"order\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
//                }
//                processing = false;
//            }
//            processing = false;
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            System.out.println("Lỗi: " + e);
//        }
//
//    }
    private synchronized void processOrderInQueue(User user, PrintWriter out) {
        if (processing) {
            return;
        }
        processing = true;
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order != null) {
                processOrder(order, user, out);
            }
        }
        processing = false;
    }

    private void processOrder(Order order, User user, PrintWriter out) {
        try {
            //out.print("<h5>Vui lòng chờ đợi trong giây lát để hệ thống xử lý! <i class=\"fas fa-spinner fa-spin\"></i></h5>");
            Thread.sleep(3000);
            double totalPayment = order.getTotalAmount();
            if (user.getBalance() < totalPayment) {
                out.print("<h6>Số dư không đủ, vui lòng nạp tiền vào tài khoản để tiếp tục <a href=\"deposit.jsp\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
            } else {
                userDAO.checkoutByBalance(user.getID(), totalPayment);//Trừ tiền
                //Update trạng thái thanh toán trong order
                if (order.getStatus().equals("Unpaid")) {
                    orderDAO.updateStatusOrder(order.getId());
                }
                //Thêm thẻ vào order detail
                List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
                if (!listDetail.isEmpty()) {
                    for (int i = 0; i < listDetail.size(); i++) {
                        //xem nó muốn thẻ nào
                        int productcartegoiesId = listDetail.get(i).getProductCategories().getId();
                        //vào bảng productcard lấy ra thẻ đó
                        ProductCard pdc = pdcDAO.getProductCardbyIDQuantity2(productcartegoiesId, 1);
                        //thêm thẻ này vào orderdetail
                        orderDAO.insertProductCartOrderDetail(pdc.getID(), listDetail.get(i).getID());
                        //thêm xong thì update thẻ này đã bán
                        pdcDAO.updateIsDeleteProductCard(pdc.getID());
                    }
                } else {
                    out.print("<h6>Tải dữ liệu thất bại, vui lòng thanh toán lại sau!</h6>");
                }

                out.print("<h6 style=\"font-family: sans-serif; color: red;\"> -" + totalPayment + " VNĐ</h6>");
                out.print("<h6>Thanh toán thành công, xem thông tin đơn hàng <a href=\"order\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean performPaymentLogic(Order order) {
        boolean paymentSuccessful = true;
        List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
        for (int i = 0; i < listDetail.size(); i++) {
            int getIdProductCard = listDetail.get(i).getProductCardID();
            if (getIdProductCard == 0) {
                paymentSuccessful = false;
            }
        }
        return paymentSuccessful;
    }

    //Hàm test:
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //Các lớp DAO
        UserDAO userDao = new UserDAO();
        OrderDAO orderDao = new OrderDAO();
        CartItemDAO cid = new CartItemDAO();
        ProductCardDAO pdcDao = new ProductCardDAO();
        TransactionDAO transDao = new TransactionDAO();
        account = (AccountLogin) request.getSession().getAttribute("account");
        GoogleLogin gglogin = (GoogleLogin) request.getSession().getAttribute("gguser");
        String orderIdString = (String) request.getParameter("orderId");

        //Verify role
        try {
            User user = new User();
            if (account == null && gglogin == null) {
                out.print("<h5>Vui lòng đăng nhập để tiếp tục " + "<a href=\"login.jsp\">Click</a> </h5>");
            } else if (account != null) {
                user = userDao.getUserById(account.getUser().getID());
            } else if (gglogin != null) {
                user = userDao.getUserById(gglogin.getUser().getID());
            }

            Order pendingOrder = orderDAO.getOrderNewByID(orderIdString);
            if (pendingOrder != null && pendingOrder.getStatus().equals("Paid")) {
                out.print("<h5>Đơn hàng này đã được thanh toán! </h5>");
                return;
            }

            //Trước khi add queue, kiểm tra xem order này có đủ điều kiện thanh toán: Unpaid + Còn thẻ
            Map<Integer, Integer> productCategoryCount = new HashMap<>();
            List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(pendingOrder.getId());
            if (!listOrderTest.isEmpty()) {
                for (int i = 0; i < listOrderTest.size(); i++) {
                    int productcartegoiesId = listOrderTest.get(i).getProductCategories().getId();
                    productCategoryCount.put(productcartegoiesId, productCategoryCount.getOrDefault(productcartegoiesId, 0) + 1);
                }
            }
            boolean alreadyCheckout = true;
            for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
                int productCategoriesId = entry.getKey();
                int quantityRequired = entry.getValue();
                alreadyCheckout = productCateDAO.checkQuantityValid(productCategoriesId, quantityRequired);

                if (!alreadyCheckout) {
                    out.print("<h6 style=\"font-family: sans-serif; color: red;\">Sản phẩm " + productCateDAO.getNameProductCategoriesByID(productCategoriesId)
                            + " có số lượng yêu cầu lớn hơn số lượng hiện có của hệ thống! Không thể tiến hành thanh toán!</h6>");
                    break;
                }
            }
            if (!alreadyCheckout) { //tồn tại lỗi quantity. cho return
                return;
            }

            double totalPayment = pendingOrder.getTotalAmount();
            if (user.getBalance() < totalPayment) {
                out.print("<h6>Số dư không đủ, vui lòng nạp tiền vào tài khoản <a href=\"deposit.jsp\" style=\"font-family: sans-serif; color: red;\">Tại đây</a> để tiếp tục!</h6>");
                return;
            }
            RequestQueue requestQueue = new RequestQueue();
            requestQueue.addRequest(pendingOrder);
            for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
                int productCategoriesId = entry.getKey();
                int quantityRequired = entry.getValue();
                productCateDAO.updateQuantityAfterCheckout(productCategoriesId, quantityRequired);
            }
            Transaction trans = new Transaction(user.getID(), pendingOrder.getId(), totalPayment, "Payment", null, null, "Pending");
            transDao.addTransitionAfterCheckoutByBalance(trans);
            out.print("<h6 style=\"font-family: sans-serif; color: green;\">Đơn hàng đã được thêm vào hàng chờ xử lý! Vui lòng chờ trong giây lát!</h6>"
            + "<h6>Xem đơn hàng<a href=\"order\" style=\"font-family: sans-serif; color: red;\">Tại đây</a><h6>");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e);
        }
    }//end post test
}//end class
