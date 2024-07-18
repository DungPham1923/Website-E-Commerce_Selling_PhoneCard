
package Controller.Checkout;

import DAL.OrderDAO;
import DAL.ProductCardDAO;
import DAL.ProductCategoriesDAO;
import DAL.UserDAO;
import Model.Order;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestQueue {
    
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();
    private ProductCardDAO pdcDAO = new ProductCardDAO();
    private ProductCategoriesDAO productCateDAO = new ProductCategoriesDAO();
    private static final BlockingQueue<Order> queueOderpending = new LinkedBlockingQueue<>();
    
    public static void addRequest(Order orderPending) {
        try {
            queueOderpending.put(orderPending);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static Order takeRequest() {
        try {
            return queueOderpending.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
