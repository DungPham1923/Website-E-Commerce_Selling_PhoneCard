/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.ProductCard;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author quyhi
 */
public class ProductCartDAO extends DBContext {

    public ProductCard getProductCartByID(int id) {
        String sql = "select * from productcard where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
            if (rs.next()) {
                ProductCard productcart = new ProductCard(rs.getInt("ID"),
                        pcdao.getProductCategoriesById(rs.getInt("ProductCategoriesID")),
                        rs.getString("Seri"),
                        rs.getString("Code"), rs.getDate("CreatedAt"),
                        rs.getDate("ExpiredDate"), rs.getDate("UpdatedAt"), rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"), rs.getInt("DeletedBy"));
                return productcart;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        ProductCartDAO c = new ProductCartDAO();
        System.out.println(c.getProductCartByID(1));
    }
}
