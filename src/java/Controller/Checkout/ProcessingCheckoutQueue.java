package Controller.Checkout;

import DAL.OrderDAO;
import DAL.ProductCardDAO;
import DAL.ProductCategoriesDAO;
import DAL.TransactionDAO;
import DAL.UserDAO;
import Model.Order;
import Model.OrderDetails;
import Model.ProductCard;
import Model.Transaction;
import Model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessingCheckoutQueue {

    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();
    private ProductCardDAO pdcDAO = new ProductCardDAO();
    private ProductCategoriesDAO productCateDAO = new ProductCategoriesDAO();
    TransactionDAO transDao = new TransactionDAO();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startBackgroundJob() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Processing queued orders...");
                try {
                    processQueuedOrders();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProcessingCheckoutQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

    }

    public void stopBackgroundJob() {
        scheduler.shutdown();
    }

    private String processQueuedOrders() throws InterruptedException {
        Map<Integer, Integer> productCategoryCount = new HashMap<>();
        while (true) {
            Order order = RequestQueue.takeRequest();
            List<OrderDetails> listOrderTest = orderDAO.getAllOrderDetailsByOderId(order.getId());
            if (!listOrderTest.isEmpty()) {
                for (int i = 0; i < listOrderTest.size(); i++) {
                    int productcartegoiesId = listOrderTest.get(i).getProductCategories().getId();
                    productCategoryCount.put(productcartegoiesId, productCategoryCount.getOrDefault(productcartegoiesId, 0) + 1);
                }
            }
            if (order != null) {
                Thread.sleep(2000);
                double totalPayment = order.getTotalAmount();
                User user = userDAO.getUserById(order.getUserId());
                if (user.getBalance() < totalPayment) {
                    return "Số dư không đủ để thực hiện thanh toán đơn hàng!";
                } else {
                    userDAO.checkoutByBalance(user.getID(), totalPayment);//Trừ tiền
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

                            //thêm xong thì update trạng thái thẻ này đã bán
                            pdcDAO.updateIsDeleteProductCard(pdc.getID());

                            //cập nhật số lượng thẻ trong productcateogies
                            for (Map.Entry<Integer, Integer> entry : productCategoryCount.entrySet()) {
                                int productCategoriesId = entry.getKey();
                                int quantityRequired = entry.getValue();
                                productCateDAO.updateQuantityAfterCheckout(productCategoriesId, quantityRequired);
                            }

                        }
                    } else {
                    }
                    //Cập nhật transaction
                    //Transaction trans = new Transaction(user.getID(), order.getId(), totalPayment, "Payment", null, null, "Success");
                    //transDao.addTransitionAfterCheckoutByBalance(trans);
                    transDao.updateStatusTransaction(order.getId());
                    return "<h6 style=\"font-family: sans-serif; color: red;\"> Số dư tài khoản: -" + totalPayment + " VNĐ</h6>" + "<h6>Thanh toán thành công, xem thông tin đơn hàng <a href=\"order\" style=\"font-family: sans-serif; color: blue;\">Tại đây</a></h6>";
                }
            } else {
                break;
            }
        }
        return "Thực hiện thanh toán đơn hàng thành công!";
    }
}
