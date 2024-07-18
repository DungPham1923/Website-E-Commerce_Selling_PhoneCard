
package Controller.Checkout;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private ProcessingCheckoutQueue processCheckout;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        processCheckout = new ProcessingCheckoutQueue();
        processCheckout.startBackgroundJob();
        System.out.println("Background job started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (processCheckout != null) {
            processCheckout.stopBackgroundJob();
            System.out.println("Background job stopped.");
        }
    }
}
