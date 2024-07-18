/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Checkout;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CartDAO;
import Model.AccountLogin;
import Model.Cart;
import Model.CartItem;
import Model.GoogleLogin;
import Model.Order;
import Model.OrderDetails;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class DisplayAllOrderInformation extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String totalAmomuntt = request.getParameter("totalAmount");
        String totalQuantity = request.getParameter("totalQuantity");
        String orderIdString = (String) request.getParameter("orderId");
        int totalAmomunt = 0; 
        if(totalQuantity != null){
            totalAmomunt = Integer.parseInt(totalQuantity);
        }
        
        
       // int totalAmomunt = 10;
        
        String[] selectedProductIds = request.getParameterValues("selectedProducts");
        //String[] totalprice = request.getParameterValues("totalPrice");

        HttpSession session = request.getSession();
        AccountLogin Acc = (AccountLogin) session.getAttribute("account");
        GoogleLogin Accgg = (GoogleLogin) session.getAttribute("gguser");
        CartDAO c = new CartDAO();
        
        if(Acc == null && Accgg == null){
            response.sendRedirect("login.jsp");
        }else{
            if(Acc != null){
                User user = Acc.getUser();
                Cart y = c.selectCartIdByUserID(user.getID());
                if (y == null) {
                    request.setAttribute("notify", "Bạn chưa có hóa đơn thanh toán nào cả");
                    request.setAttribute("email", user.getEmail());
                    request.setAttribute("phone", user.getPhone());
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                } else {
                    int id = (int) y.getID();
                    List<CartItem> listt = c.getListCartitemByCartID(id);
                    List<CartItem> list = new ArrayList<>();
                    for (int i = 0; i < listt.size(); i++) {
                        String idd = String.valueOf(listt.get(i).getID());
                        for (String idp : selectedProductIds) {
                            if (idp.equals(idd)) {
                                list.add(listt.get(i));
                            }
                        }
                    }
                    int totalPrice = 0;
                    for (int i = 0; i < list.size(); i++) {
                        totalPrice += list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() - list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() * (list.get(i).getProductCategories().getDiscount() / 100);
                    }
                    request.setAttribute("id", user.getID());
                    request.setAttribute("totalamomunt", totalAmomunt);
                    request.setAttribute("totalprice", totalPrice);
                    request.setAttribute("list", list);
                    request.setAttribute("email", user.getEmail());
                    request.setAttribute("phone", user.getPhone());
                    
                    Order order = new Order(user.getID(), totalAmomunt,totalPrice);
                    session.setAttribute("order", order);
                    session.setAttribute("userid", user.getID());
                    session.setAttribute("list", list);
                    
                    request.setAttribute("orderId", orderIdString);
                    
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                }
            }else if(Accgg != null){
                User usergg = Accgg.getUser();
                Cart y = c.selectCartIdByUserID(usergg.getID());

                if (y == null) {
                    request.setAttribute("notify", "Bạn chưa có hóa đơn thanh toán nào cả");
                    request.setAttribute("email", usergg.getEmail());
                    request.setAttribute("phone", usergg.getPhone());
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                } else {
                    int id = (int) y.getID();
                    List<CartItem> listt = c.getListCartitemByCartID(id);
                    List<CartItem> list = new ArrayList<>();
                    for (int i = 0; i < listt.size(); i++) {
                        String idd = String.valueOf(listt.get(i).getID());
                        for (String idp : selectedProductIds) {
                            if (idp.equals(idd)) {
                                list.add(listt.get(i));
                            }
                        }
                    }
                    int totalPrice = 0;
                    for (int i = 0; i < list.size(); i++) {
                        totalPrice += list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() - list.get(i).getProductCategories().getPrice() * list.get(i).getQuantity() * (list.get(i).getProductCategories().getDiscount() / 100);
                    }
                    request.setAttribute("id", usergg.getID());
                    request.setAttribute("totalprice", totalPrice);
                    request.setAttribute("totalamomunt", totalAmomunt);
                    request.setAttribute("list", list);
                    request.setAttribute("email", usergg.getEmail());
                    request.setAttribute("phone", usergg.getPhone());
                    
                    Order order = new Order(usergg.getID(),totalAmomunt,totalPrice);
                    session.setAttribute("order", order);
                    session.setAttribute("userid", usergg.getID());
                    session.setAttribute("list", list);
                    
                    request.setAttribute("orderId", orderIdString);
                    
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                }
            }
        }  
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
