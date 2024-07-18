package DAL;

import Model.CartItem;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CartItemDAO extends DBContext {

    private final CartDAO cdao = new CartDAO();
    private final ProductCategoriesDAO pcdao = new ProductCategoriesDAO();

    public List<CartItem> getCartItem(int id) {
        List<CartItem> items = new ArrayList<>();
        String sql = "select * from cartitem\n"
                + "join cart on cart.id = cartitem.cartid\n"
                + "where userid = ?\n"
                + "order by cartitem.id desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CartItem ci = new CartItem(rs.getInt("ID"),
                        cdao.getCartById(rs.getInt("CartID")),
                        pcdao.getProductCategoriesById(rs.getInt("ProductCategoriesID")),
                        rs.getInt("Quantity"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                items.add(ci);
            }
            return items;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void deleteFromCart(int id) {
        String sql = "delete from cartitem where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateQuantity(int cartid, int quantity) {
        String sql = "update cartitem \n"
                + "set quantity = ?,\n"
                + "updatedat = curdate()"
                + "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, cartid);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean checkProductExist(int productid, int cartid) {
        String sql = "select productcategoriesid from CartItem\n"
                + "where productcategoriesid = ? and cartid = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productid);
            st.setInt(2, cartid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void updateProductQuantity(int productid, int quantity, int stockqty, int cartid) {
        String sql = "update cartitem \n"
                + "set quantity = ? + ?,\n"
                + "updatedat = curdate()\n"
                + "where productcategoriesid = ? and cartid = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, stockqty);
            st.setInt(3, productid);
            st.setInt(4, cartid);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addCartItem(int cartid, int productId, int quantity) {
        String sql = "insert into cartitem (cartid, productcategoriesid, quantity, createdat, updatedat)\n"
                + "values (?, ?, ?, curdate(), curdate())";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cartid);
            st.setInt(2, productId);
            st.setInt(3, quantity);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getQuantity(int productid, int cartid) {
        String sql = "select quantity from cartitem \n"
                + "where productcategoriesid = ? and cartid = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productid);
            st.setInt(2, cartid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
    
    public void deleteCartItemByID(CartItem cartitem){
        String sql = "DELETE FROM cartitem WHERE ID = ?;";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cartitem.getID());
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    } 
    
    public CartItem getCartitemByID(String ID) {
        String sql = "SELECT *\n"
                + "FROM CartItem where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ID);
            ResultSet rs = st.executeQuery();
            ProductCategoriesDAO p = new ProductCategoriesDAO();
            if(rs.next()){
                CartItem cartitem = new CartItem(rs.getInt("ID"), rs.getInt("CartID"), p.getProductCategoriesByID(rs.getInt("ProductCategoriesID")), rs.getInt("Quantity"));
                return cartitem;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public CartItem getCartitemByID2(String ID) {
        String sql = "SELECT *\n"
                + "FROM CartItem where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ID);
            ResultSet rs = st.executeQuery();
            ProductCategoriesDAO p = new ProductCategoriesDAO();
            if(rs.next()){
                CartItem cartitem = new CartItem(rs.getInt("ID"), rs.getInt("CartID"), rs.getInt("ProductCategoriesID"), rs.getInt("Quantity"));
                return cartitem;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    
    public CartItem getCartitemBySelect(int userId, int cartitemId){
        String sql = "Select *"
                + "From CartItem c"
                + "INNER JOIN Cart t on c.CartID = t.ID"
                + "Where t.UserId = ? And c.Id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, cartitemId);
        } catch (Exception e) {
        }
        return null;
    }
    
    public static void main(String[] args) {
        CartItemDAO u = new CartItemDAO();
//        List<CartItem> data= u.getCartItem(1);
//        for (CartItem cartItem : data) {
//            System.out.println(cartItem.toString());
//        }
        System.out.println(u.getCartitemByID("6").getProductCategories().getId());
    }
    
}
