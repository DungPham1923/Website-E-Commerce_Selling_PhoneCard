/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.manageruseraccount;

import DAL.AccountLoginDAO;
import DAL.UserDAO;
import Model.AccountLogin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Dat
 */
public class CustomAccount extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String balance = request.getParameter("balance");
        String createAt = request.getParameter("createAt");
        AccountLogin alg = (AccountLogin) request.getSession().getAttribute("account");
        
        String msg = "";
        if("update".equalsIgnoreCase(action)){
            AccountLoginDAO algDao = new AccountLoginDAO();
            algDao.updateLoginInfo(id,password);
            
            UserDAO uDao = new UserDAO();
            uDao.updateByAdmin(id, firstname, lastname, email, phone, balance);
            
            msg = "Cập nhật thành công!";
        }else{
            AccountLoginDAO algDao = new AccountLoginDAO();
            algDao.updateLoginInfo(id, "12345678");
            
            msg = "Đổi mật khẩu về mặc định thành công!";
        }
        
//        AccountLoginDAO algDao = new AccountLoginDAO();
//        List<AccountLogin> allAcc = algDao.getAllAccount();
//        request.setAttribute("allAcc", allAcc);
        request.getSession().setAttribute("msgCustom", msg);
        response.sendRedirect("manageruseracc");
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
