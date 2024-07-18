package Controller.CheckoutByBanking;

import Controller.authentication.BaseRequireAuthentication;
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
import Model.Transaction;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CheckoutByBanking extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckoutByBanking</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutByBanking at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private static int OrderId = 0;
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();
    private ProductCardDAO pdcDAO = new ProductCardDAO();
    private ProductCategoriesDAO productCateDAO = new ProductCategoriesDAO();
    private static ConcurrentLinkedQueue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private static boolean processing = false;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        OrderDAO orderDao = new OrderDAO();
        if (account.getUser() == null) {
            response.sendRedirect("login.jsp");
        } else {
            //Data cần gửi kèm để xử lí thanh toán
            String orderType = "other";
            String orderId = request.getParameter("orderId");
            Order pendingOrder = orderDao.getOrderNewByID(orderId);
            double number = pendingOrder.getTotalAmount() * 100;
            String numberString = String.format("%.0f", number);
            if (processing) {
                out.print("<h6>Bạn đang có 1 giao dịch thanh toán qua VNPay chưa hoàn tất! Hiện không thể tiếp tục thanh toán đơn hàng mới! Quay lại lịch sử đơn hàng để thanh toán sau!</h6>");
                return;
            }
            orderQueue.add(pendingOrder);
            processing = true;
            //------------------------------------------------------------------
            String bankCode = "NCB";

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";

            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", Config.vnp_Version);
            vnp_Params.put("vnp_Command", Config.vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(numberString));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = null;
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String formattedTimestamp = formatters.format(now);

            //Xử lí kiểm tra order hợp lệ mới cho tiến hành đi tới vnpay thanh toán
            if (pendingOrder != null && pendingOrder.getStatus().equals("Paid")) {
                return;
            }

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
                    break;
                }
            }
            if (!alreadyCheckout) { //tồn tại lỗi quantity. cho return
                return;
            } else {
                response.sendRedirect(paymentUrl);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            TransactionDAO transDao = new TransactionDAO();
            account = (AccountLogin) request.getSession().getAttribute("account");
            GoogleLogin gglogin = (GoogleLogin) request.getSession().getAttribute("gguser");

            if (account == null && gglogin == null) {
                out.print("<h5>Vui lòng đăng nhập để tiếp tục <a href=\"login.jsp\">Click</a></h5>");
                return;
            }

            User user = getUserFromSession(account, gglogin);
            if (user == null) {
                out.print("<h5>Vui lòng đăng nhập để tiếp tục <a href=\"login.jsp\">Click</a></h5>");
                return;
            }

            Map<String, String> vnpParams = getVnpParams(request);
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");

            if (!isValidSignature(vnpParams, vnp_SecureHash)) {
                out.print("Invalid signature");
                return;
            }

            String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            String vnp_Amount = request.getParameter("vnp_Amount");
            String vnp_BankCode = request.getParameter("vnp_BankCode");
            String vnp_PayDate = request.getParameter("vnp_PayDate");

            if ("00".equals(vnp_TransactionStatus)) {
                //processOrder(vnp_Amount, user, vnp_TxnRef, vnp_BankCode, transDao, out, request, response);
                while (!orderQueue.isEmpty()) {
                    Order order = orderQueue.poll();
                    Thread.sleep(2000);

                    Map<Integer, Integer> productCategoryCount = new HashMap<>();
                    List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(order.getId());
                    for (OrderDetails od : listOrderTest) {
                        int productCategoriesId = od.getProductCategories().getId();
                        productCategoryCount.put(productCategoriesId, productCategoryCount.getOrDefault(productCategoriesId, 0) + 1);
                    }

                    double totalPayment = Double.parseDouble(vnp_Amount);
                    if ("Unpaid".equals(order.getStatus())) {
                        orderDAO.updateStatusOrder(order.getId());
                    }

                    List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
                    for (OrderDetails od : listDetail) {
                        int productCategoriesId = od.getProductCategories().getId();
                        ProductCard pdc = pdcDAO.getProductCardbyIDQuantity2(productCategoriesId, 1);

                        orderDAO.insertProductCartOrderDetail(pdc.getID(), od.getID());
                        pdcDAO.updateIsDeleteProductCard(pdc.getID());

                        productCateDAO.updateQuantityAfterCheckout(productCategoriesId, productCategoryCount.get(productCategoriesId));
                    }

                    Transaction trans = new Transaction(user.getID(), order.getId(), totalPayment, "Payment", vnp_TxnRef, vnp_BankCode, "Success");
                    transDao.addTransitionAfterCheckoutByBanking(trans);

                    request.setAttribute("username", user.getFirstName() + " " + user.getLastName());
                    request.setAttribute("transactiontype", "Thanh toán");
                    request.setAttribute("ordercode", order.getId());
                    request.setAttribute("description", "Thanh toán hóa đơn mua thẻ");
                    request.setAttribute("amount", formatMoney(totalPayment / 100));

                    request.setAttribute("username", user.getFirstName() + user.getLastName());
                    request.setAttribute("transactiontype", "Thanh toán");
                    request.setAttribute("transactioncode", vnp_TxnRef);
                    double vnpAmount = Double.parseDouble(vnp_Amount);
                    request.setAttribute("amountpayment", formatMoney(vnpAmount / 100));
                    request.setAttribute("descriptionpayment", "Thanh toán hóa đơn mua hàng bằng VNPAY");
                    request.setAttribute("bankcode", vnp_BankCode);

                    String inputDateFormat = "yyyyMMddHHmmss";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
                    Date date = inputFormat.parse(vnp_PayDate);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = outputFormat.format(date);
                    request.setAttribute("timepayment", formattedDate);
                    request.setAttribute("status", "Thành công");
                    processing = false;
                    request.getRequestDispatcher("paymentbanking.jsp").forward(request, response);
                }
            } else {
                processing = false;
                response.sendRedirect("order");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    private User getUserFromSession(AccountLogin account, GoogleLogin gglogin) {
        if (account != null) {
            return userDAO.getUserById(account.getUser().getID());
        } else if (gglogin != null) {
            return userDAO.getUserById(gglogin.getUser().getID());
        }
        return null;
    }

    private Map<String, String> getVnpParams(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()),
                        URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
            }
        }
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        return fields;
    }

    private String formatMoney(double money) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(money);
    }

    private boolean isValidSignature(Map<String, String> fields, String vnp_SecureHash) {
        String calculatedHash = Config.hashAllFields(fields);
        return calculatedHash.equals(vnp_SecureHash);
    }

    private void processOrder(String vnp_Amount, User user, String vnp_TxnRef, String vnp_BankCode, TransactionDAO transDao, PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            Thread.sleep(2000);

            Map<Integer, Integer> productCategoryCount = new HashMap<>();
            List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(order.getId());
            for (OrderDetails od : listOrderTest) {
                int productCategoriesId = od.getProductCategories().getId();
                productCategoryCount.put(productCategoriesId, productCategoryCount.getOrDefault(productCategoriesId, 0) + 1);
            }

            double totalPayment = Double.parseDouble(vnp_Amount);
            if ("Unpaid".equals(order.getStatus())) {
                orderDAO.updateStatusOrder(order.getId());
            }

            List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
            for (OrderDetails od : listDetail) {
                int productCategoriesId = od.getProductCategories().getId();
                ProductCard pdc = pdcDAO.getProductCardbyIDQuantity2(productCategoriesId, 1);

                orderDAO.insertProductCartOrderDetail(pdc.getID(), od.getID());
                pdcDAO.updateIsDeleteProductCard(pdc.getID());

                productCateDAO.updateQuantityAfterCheckout(productCategoriesId, productCategoryCount.get(productCategoriesId));
            }

            Transaction trans = new Transaction(user.getID(), order.getId(), totalPayment, "Payment", vnp_TxnRef, vnp_BankCode, "Success");
            transDao.addTransitionAfterCheckoutByBanking(trans);
        }
    }
}
//    @Override
//    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
//        //doPost(req, resp, account);
//        TransactionDAO transDao = new TransactionDAO();
//        response.setContentType("text/html;charset=UTF-8");
//        account = (AccountLogin) request.getSession().getAttribute("account");
//        GoogleLogin gglogin = (GoogleLogin) request.getSession().getAttribute("gguser");
//        PrintWriter out = response.getWriter();
//
//        //Data return
//        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
//        String vnp_Amount = request.getParameter("vnp_Amount");
//        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
//        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
//        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
//        String vnp_BankCode = request.getParameter("vnp_BankCode");
//        String vnp_PayDate = request.getParameter("vnp_PayDate");
//        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
//        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//
//        Map fields = new HashMap();
//        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
//            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
//            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                fields.put(fieldName, fieldValue);
//            }
//        }
//        fields.remove("vnp_SecureHashType");
//        fields.remove("vnp_SecureHash");
//        String calculatedHash = Config.hashAllFields(fields);
//        String statusMessage = "";
//
//        try {
//            //Nếu chưa đăng nhập: Out
//            User user = new User();
//            if (account == null && gglogin == null) {
//                out.print("<h5>Vui lòng đăng nhập để tiếp tục " + "<a href=\"login.jsp\">Click</a> </h5>");
//            } else if (account != null) {
//                user = userDAO.getUserById(account.getUser().getID());
//            } else if (gglogin != null) {
//                user = userDAO.getUserById(gglogin.getUser().getID());
//            }
//            //Bắt đầu lấy queue ra xử lí. 
//            if (calculatedHash.equals(vnp_SecureHash)) {
//                if ("00".equals(vnp_TransactionStatus)) {
//                    statusMessage = "Success";
//                } else {
//                    statusMessage = "Failed";
//                }
//            } else {
//                System.out.println("Chữ ký không hợp lệ");
//                //response.getWriter().println("Invalid signature");
//            }
//
//            if (calculatedHash.equals(vnp_SecureHash)) {
//                if ("00".equals(vnp_TransactionStatus)) {//xử lí nếu thành công
//                    processing = true;
//                    if (processing) {
//                        return;
//                    }
//                    while (!orderQueue.isEmpty()) {
//                        Order order = orderQueue.poll();
//                        Thread.sleep(2000);
//
//                        //Lấy ra số lượng chi tiết từng item trong order
//                        Map<Integer, Integer> productCategoryCount = new HashMap<>();
//                        List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(order.getId());
//                        if (!listOrderTest.isEmpty()) {
//                            for (int i = 0; i < listOrderTest.size(); i++) {
//                                int productcartegoiesId = listOrderTest.get(i).getProductCategories().getId();
//                                productCategoryCount.put(productcartegoiesId, productCategoryCount.getOrDefault(productcartegoiesId, 0) + 1);
//                            }
//                        }
//                        //lấy ra 
//                        double totalPayment = Double.parseDouble(vnp_Amount);
//                        if (order.getStatus().equals("Unpaid")) {
//                            orderDAO.updateStatusOrder(order.getId());
//                        }
//                        //Thêm thẻ vào order detail
//                        List<OrderDetails> listDetail = orderDAO.getAllOrderDetailsByOderId(order.getId());
//                        if (!listDetail.isEmpty()) {
//                            for (int i = 0; i < listDetail.size(); i++) {
//                                //xem nó muốn thẻ nào
//                                int productcartegoiesId = listDetail.get(i).getProductCategories().getId();
//
//                                //vào bảng productcard lấy ra thẻ đó
//                                ProductCard pdc = pdcDAO.getProductCardbyIDQuantity2(productcartegoiesId, 1);
//
//                                //thêm thẻ này vào orderdetail
//                                orderDAO.insertProductCartOrderDetail(pdc.getID(), listDetail.get(i).getID());
//
//                                //thêm xong thì update trạng thái thẻ này đã bán
//                                pdcDAO.updateIsDeleteProductCard(pdc.getID());
//
//                                //cập nhật số lượng thẻ trong productcateogies
//                                for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
//                                    int productCategoriesId = entry.getKey();
//                                    int quantityRequired = entry.getValue();
//                                    productCateDAO.updateQuantityAfterCheckout(productCategoriesId, quantityRequired);
//                                }
//                            }
//                        }
//                        //Cập nhật transaction
//                        Transaction trans = new Transaction(user.getID(), order.getId(), totalPayment, "Payment", vnp_TxnRef, vnp_BankCode, "Success");
//                        transDao.addTransitionAfterCheckoutByBalance(trans);
//                        response.getWriter().println("Thanh toán thành công");
//                    }
//                } else {//xử lí nếu thất bại
//                    orderQueue.poll();
//                }//end if 2
//            } //end if 1
//
//        } catch (Exception ex) {
//            System.out.println("Error:" + ex);
//        }
//    }//end get

//end class
//        response.getWriter().println("Mã giao dịch thanh toán: " + vnp_TxnRef);
//        response.getWriter().println("Mã giao dịch thanh toán: " + OrderId);
//        response.getWriter().println("Số tiền: " + vnp_Amount);
//        response.getWriter().println("Mô tả giao dịch: " + vnp_OrderInfo);
//        response.getWriter().println("Mã lỗi thanh toán: " + vnp_ResponseCode);
//        response.getWriter().println("Mã giao dịch tại CTT VNPAY-QR: " + vnp_TransactionNo);
//        response.getWriter().println("Mã ngân hàng thanh toán: " + vnp_BankCode);
//        response.getWriter().println("Thời gian thanh toán: " + vnp_PayDate);
//        response.getWriter().println("Tình trạng giao dịch: " + statusMessage);
