
package DAL;

import Model.ProductCard;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ProductCardDAO extends DBContext {

    public List<ProductCard> getProductCardbyIDQuantity(int ID, int quantity) {
        List<ProductCard> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductCard WHERE ProductCategoriesID = ? LIMIT ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);
            ps.setInt(2, quantity);
            ResultSet rs = ps.executeQuery();
            ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
            while (rs.next()) {
                list.add(new ProductCard(rs.getInt("ID"), pcdao.getProductCategoriesById(rs.getInt("ProductCategoriesID")) ,
                        rs.getString("Seri"), rs.getString("Code"), rs.getDate("CreatedAt"),
                        rs.getDate("ExpiredDate"), rs.getDate("UpdatedAt"), rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"), rs.getInt("DeletedBy")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ProductCard getProductCardbyIDQuantity2(int ID, int quantity) {
        String sql = "SELECT * "
                + "FROM ProductCard "
                + "WHERE ProductCategoriesID = ? AND IsDelete = 0 LIMIT ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID);
            ps.setInt(2, quantity);
            ResultSet rs = ps.executeQuery();
            ProductCard pdc = null;
            ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
            if (rs.next()) {
                pdc = new ProductCard(rs.getInt("ID"), pcdao.getProductCategoriesById(rs.getInt("ProductCategoriesID")),
                        rs.getString("Seri"), rs.getString("Code"), rs.getDate("CreatedAt"),
                        rs.getDate("ExpiredDate"), rs.getDate("UpdatedAt"), rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"), rs.getInt("DeletedBy"));
            }
            return pdc;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateIsDeleteProductCard(int cardId) {
        String sql = "UPDATE `dbprojectswp391_v1`.`productcard`\n"
                + "SET\n"
                + "`IsDelete` = 1,\n"
                + "`DeletedAt` = NOW()"
                + "WHERE `ID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cardId);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Thu - Iter 3
    public List<ProductCard> getProductCardByPCID (int id, int index){
        List<ProductCard> list = new ArrayList<>();
        String sql = "select * from productcard\n"
                + "where productcategoriesid = ?\n"
                + "order by id desc\n"
                + "limit 50 offset ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, (index - 1) * 50);
            ResultSet rs = ps.executeQuery();
            ProductCategoriesDAO pcdao = new ProductCategoriesDAO();
            while (rs.next()) {
                list.add(new ProductCard(rs.getInt("ID"), pcdao.getProductCategoriesById(rs.getInt("ProductCategoriesID")),
                        rs.getString("Seri"), rs.getString("Code"), rs.getDate("CreatedAt"),
                        rs.getDate("ExpiredDate"), rs.getDate("UpdatedAt"), rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"), rs.getInt("DeletedBy")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    // Thu - Iter 3
    public int countCardByPCID(int id){
        String sql = "select count(id) from productcard\n"
                + "where productcategoriesid = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
    
    // Thu - Iter 3
    public void deleteCardByID(int id, int pcid){
        String sql = "delete from productcard where id = ?\n"
                + "and productcategoriesid = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, pcid);
            ps.executeUpdate();
            String sql1 = "update productcategories\n"
                    + "set quantity = quantity - 1\n"
                    + "where id = ?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, pcid);
            ps1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Thu - Iter 3
    public void updateCard(int id, String seri, String code, String expiredDate){
        try {
            String sql = "UPDATE productcard\n" +
                 "SET seri = ?, code = ?, expireddate = ?, updatedat = curdate()\n" +
                 "WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, seri);
            ps.setString(2, code);
            ps.setString(3, expiredDate);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Thu = Iter 3
    public boolean checkSeriExist(String seri, Integer cardid){
        try {
            String sql = "select seri from productcard where seri = ? and id != ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, seri);
            ps.setInt(2, cardid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    // Thu = Iter 3
    public boolean checkCodeExist(String code, Integer cardid){
        try {
            String sql = "select code from productcard where code = ? and id != ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.setInt(2, cardid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    ProductCategoriesDAO pc_dao = new ProductCategoriesDAO();
    // Thu - Iter 3
    public void addCard(int pcid, String seri, String code, String expiredDate, int userid){
        try {
            if(pc_dao.checkDeleteCategory(pcid)){
                String sql = "INSERT INTO ProductCard (ProductCategoriesID, Seri, Code, CreatedAt, ExpiredDate, UpdatedAt, DeletedAt, IsDelete, DeletedBy)\n"
                        + "VALUES (?, ?, ?, CURDATE(), ?, CURDATE(), CURDATE(), TRUE, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, pcid);
                ps.setString(2, seri);
                ps.setString(3, code);
                ps.setString(4, expiredDate);
                ps.setInt(5, userid);
                ps.executeUpdate();
            } else {
                String sql = "INSERT INTO ProductCard (ProductCategoriesID, Seri, Code, CreatedAt, ExpiredDate, UpdatedAt, DeletedAt, IsDelete, DeletedBy)\n"
                        + "VALUES (?, ?, ?, CURDATE(), ?, CURDATE(), NULL, FALSE, NULL)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, pcid);
                ps.setString(2, seri);
                ps.setString(3, code);
                ps.setString(4, expiredDate);
                ps.executeUpdate();
            }            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        ProductCardDAO u = new ProductCardDAO();
        System.out.println(u.getProductCardbyIDQuantity2(3, 1));

        List<ProductCard> listproductcard = u.getProductCardbyIDQuantity(3, 2);
//        for (int j = 0; j < listproductcard.size(); j++) {
//            OrderDetails orderdetail = new OrderDetails(orderid, list.get(i).getProductCategories().getId(), list.get(i).getProductCategories().getPrice(), list.get(i).getProductCategories().getDiscount(), list.get(i).getProductCategories().getPrice() - (list.get(i).getProductCategories().getDiscount() * 0.01));
//            orderDao.insertOrderDetail(orderdetail);
//        }
        for (ProductCard x : listproductcard) {
            System.out.println(x);
        }
    }
}
