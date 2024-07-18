package DAL;

import Model.Cart;
import Model.CartItem;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartDAO extends DBContext {

    private final UserDAO udao = new UserDAO();

    public Cart getCartById(int id) {
        String sql = "select * from Cart where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Cart c = new Cart();
                c.setID(rs.getInt("ID"));
                c.setUser(udao.getUserById(rs.getInt("UserID")));
                c.setCreatedAt(rs.getDate("CreatedAt"));
                c.setUpdatedAt(rs.getDate("UpdatedAt"));
                c.setDeletedAt(rs.getDate("DeletedAt"));
                c.setIsDelete(rs.getBoolean("IsDelete"));
                c.setDeletedBy(rs.getInt("DeletedBy"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean checkCartUserExist(int id) {
        String sql = "select UserID from Cart where UserID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void addCart(int userid) {
        String sql = "insert into cart (userid, createdat, updatedat)\n"
                + "values (?, curdate(), curdate())";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userid);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getLatestCartId() {
        String sql = "select ID from Cart order by ID desc limit 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public int getCartByUserId(int userid) {
        String sql = "select ID from Cart where UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public Cart selectCartIdByUserID(int UserID) {
        try {
            String sql = "SELECT *\n"
                    + "FROM Cart where UserID = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, UserID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setID(rs.getInt("ID"));
                cart.setUserID(rs.getInt("UserID"));
                return cart;
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public List<CartItem> getListCartitemByCartID(int CartID) {
        List<CartItem> data = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM CartItem where CartID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, CartID);
            ResultSet rs = st.executeQuery();
            ProductCategoriesDAO p = new ProductCategoriesDAO();
            while (rs.next()) {
                data.add(new CartItem(rs.getInt("ID"),
                        rs.getInt("CartID"),
                        p.getProductCategoriesByID(rs.getInt("ProductCategoriesID")),
                        rs.getInt("Quantity")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    public void deleteCartByUserID(int userId) {
        String sql = "DELETE FROM `dbprojectswp391_v1`.`cart`\n"
                + "WHERE `dbprojectswp391_v1`.`UserID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);
        return df.format(price);
    }

    public static void main(String[] args) {
        CartDAO c = new CartDAO();
        List<CartItem> list = c.getListCartitemByCartID(1);
        System.out.println(list);
    }
}
