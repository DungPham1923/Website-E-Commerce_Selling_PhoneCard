/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.productdetail;

import Controller.authentication.BaseRequireAuthentication;
import DAL.CommentDAO;
import DAL.ProductCategoriesDAO;
import Model.AccountLogin;
import Model.Comment;
import Model.ProductCategories;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dat
 */
public class ProductDetail extends BaseRequireAuthentication {

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String id = request.getParameter("id");
//        ProductCategoriesDAO PcDao = new ProductCategoriesDAO();
//        CommentDAO cmtDAO = new CommentDAO();
//        ProductCategories pcate = PcDao.getProductCateByID(id);
//        List<ProductCategories> productBrandRelate = PcDao.filterListProductByBrandId(pcate.getBrand().getId());
//
//        List<ProductCategories> toRemove = new ArrayList<>();
//        for (ProductCategories category : productBrandRelate) {
//            if (category.getId() == pcate.getId()) {
//                toRemove.add(category);
//            }
//        }
//        productBrandRelate.removeAll(toRemove);
//
//        List<Comment> comments = cmtDAO.getTop3CommentsByPID(id);
//        List<Comment> allComments = cmtDAO.getListCommentsByPID(id);
//        int countCmt = allComments.size();
//       
//        request.setAttribute("countCmt", countCmt);
//        request.setAttribute("comments", comments);
//        request.setAttribute("productRelate", productBrandRelate);
//        request.setAttribute("productCate", pcate);
//        request.getRequestDispatcher("productdetail.jsp").forward(request, response);
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        String id = request.getParameter("id");
        ProductCategoriesDAO PcDao = new ProductCategoriesDAO();
        CommentDAO cmtDAO = new CommentDAO();
        ProductCategories pcate = PcDao.getProductCateByID(id);
        List<ProductCategories> productBrandRelate = null;
        if(pcate != null){
            productBrandRelate = PcDao.filterListProductByBrandId(pcate.getBrand().getId());
        }
        

        List<ProductCategories> toRemove = new ArrayList<>();
        for (ProductCategories category : productBrandRelate) {
            if (category.getId() == pcate.getId()) {
                toRemove.add(category);
            }
        }
        productBrandRelate.removeAll(toRemove);

        List<Comment> comments = cmtDAO.getTop3CommentsByPID(id);
        List<Comment> allComments = cmtDAO.getListCommentsByPID(id);
        int countCmt = allComments.size();
       
        request.setAttribute("countCmt", countCmt);
        request.setAttribute("comments", comments);
        request.setAttribute("productRelate", productBrandRelate);
        request.setAttribute("productCate", pcate);
        request.getRequestDispatcher("productdetail.jsp").forward(request, response);
    }

}
