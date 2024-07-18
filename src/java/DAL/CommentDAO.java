/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dat
 */
public class CommentDAO extends DBContext {

    public void insertComment(int productCateID, int userID, String title, String msg, String productCategoryName, String brandName) throws SQLException {
        try {
            String sql = "INSERT INTO `dbprojectswp391_v1`.`comment` "
                    + "(`ID`, `UserID`, `ProductCategoriesID`, `ProductCategoriesName`, `BrandName`, `Title`, `Message`, `CreatedAt`, `UpdatedAt`, `DeletedAt`, `IsDelete`, `DeletedBy`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, 0);
            statement.setInt(2, userID);
            statement.setInt(3, productCateID);
            statement.setString(4, productCategoryName);
            statement.setString(5, brandName);
            statement.setString(6, title);
            statement.setString(7, msg);

            Timestamp createdAt = getCurrentTimestamp();
            statement.setTimestamp(8, createdAt);
            statement.setTimestamp(9, null);
            statement.setTimestamp(10, null);
            statement.setBoolean(11, false);
            statement.setString(12, null);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    
    public void insertComment(int productCateID, int userID, String title, String msg) throws SQLException {
        try {
            String sql = "INSERT INTO `dbprojectswp391_v1`.`comment` "
                    + "(`UserID`, `ProductCategoriesID`, `Title`, `Message`, `CreatedAt`, `UpdatedAt`, `DeletedAt`, `IsDelete`, `DeletedBy`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, productCateID);
            statement.setString(3, title);
            statement.setString(4, msg);

            Timestamp createdAt = getCurrentTimestamp();
            statement.setTimestamp(5, createdAt);
            statement.setTimestamp(6, null);
            statement.setTimestamp(7, null);
            statement.setBoolean(8, false);
            statement.setString(9, null);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<Comment> getListCommentsByPID(String id) {
        List<Comment> data = new ArrayList<Comment>();
        String sql = "SELECT `comment`.`ID`,\n"
                + "    `comment`.`UserID`,\n"
                + "    `comment`.`ProductCategoriesID`,\n"
                + "    `comment`.`Title`,\n"
                + "    `comment`.`Message`,\n"
                + "    `comment`.`CreatedAt`,\n"
                + "    `comment`.`UpdatedAt`,\n"
                + "    `comment`.`DeletedAt`,\n"
                + "    `comment`.`IsDelete`,\n"
                + "    `comment`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`comment`\n"
                + "WHERE `comment`.`ProductCategoriesID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);  // Set the parameter for the prepared statement
            ResultSet rs = st.executeQuery();
            UserDAO userDAO = new UserDAO();
            ProductCategoriesDAO pdcateDAO = new ProductCategoriesDAO();

            Comment cmt = null;
            while (rs.next()) {
                cmt = new Comment(rs.getInt("ID"),
                        userDAO.getUserById(rs.getInt("UserID")),
                        pdcateDAO.getProductCateByID(rs.getString("ProductCategoriesID")),
                        rs.getString("Title"),
                        rs.getString("Message"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(cmt);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public Comment getCommentsByID(String id) {
        String sql = "SELECT `comment`.`ID`,\n"
                + "    `comment`.`UserID`,\n"
                + "    `comment`.`ProductCategoriesID`,\n"
                + "    `comment`.`Title`,\n"
                + "    `comment`.`Message`,\n"
                + "    `comment`.`CreatedAt`,\n"
                + "    `comment`.`UpdatedAt`,\n"
                + "    `comment`.`DeletedAt`,\n"
                + "    `comment`.`IsDelete`,\n"
                + "    `comment`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`comment`\n"
                + "WHERE `comment`.`ID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);  // Set the parameter for the prepared statement
            ResultSet rs = st.executeQuery();
            UserDAO userDAO = new UserDAO();
            ProductCategoriesDAO pdcateDAO = new ProductCategoriesDAO();

            if (rs.next()) {
                Comment cmt = new Comment(rs.getInt("ID"),
                        userDAO.getUserById(rs.getInt("UserID")),
                        pdcateDAO.getProductCateByID(rs.getString("ProductCategoriesID")),
                        rs.getString("Title"),
                        rs.getString("Message"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                return cmt;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public List<Comment> getListComments() {
        List<Comment> data = new ArrayList<>();
        String sql = "SELECT `comment`.`ID`,\n"
                + "    `comment`.`UserID`,\n"
                + "    `comment`.`ProductCategoriesID`,\n"
                + "    `comment`.`Title`,\n"
                + "    `comment`.`Message`,\n"
                + "    `comment`.`CreatedAt`,\n"
                + "    `comment`.`UpdatedAt`,\n"
                + "    `comment`.`DeletedAt`,\n"
                + "    `comment`.`IsDelete`,\n"
                + "    `comment`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`comment`;\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            UserDAO userDAO = new UserDAO();
            ProductCategoriesDAO pdcateDAO = new ProductCategoriesDAO();

            while (rs.next()) {
                Comment cmt = new Comment(rs.getInt("ID"),
                        userDAO.getUserById(rs.getInt("UserID")),
                        pdcateDAO.getProductCateByID(rs.getString("ProductCategoriesID")),
                        rs.getString("Title"),
                        rs.getString("Message"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(cmt);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }
    
    
    public List<Comment> getTop3CommentsByPID(String id) {
        List<Comment> data = new ArrayList<Comment>();
        String sql = "SELECT `comment`.`ID`,\n"
                + "    `comment`.`UserID`,\n"
                + "    `comment`.`ProductCategoriesID`,\n"
                + "    `comment`.`Title`,\n"
                + "    `comment`.`Message`,\n"
                + "    `comment`.`CreatedAt`,\n"
                + "    `comment`.`UpdatedAt`,\n"
                + "    `comment`.`DeletedAt`,\n"
                + "    `comment`.`IsDelete`,\n"
                + "    `comment`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`comment`\n"
                + "WHERE `comment`.`ProductCategoriesID` = ?\n"
                + "ORDER BY `comment`.`ID` DESC\n"
                + "LIMIT 3;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);  // Set the parameter for the prepared statement
            ResultSet rs = st.executeQuery();
            UserDAO userDAO = new UserDAO();
            ProductCategoriesDAO pdcateDAO = new ProductCategoriesDAO();

            Comment cmt = null;
            while (rs.next()) {
                cmt = new Comment(rs.getInt("ID"),
                        userDAO.getUserById(rs.getInt("UserID")),
                        pdcateDAO.getProductCateByID(rs.getString("ProductCategoriesID")),
                        rs.getString("Title"),
                        rs.getString("Message"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(cmt);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<Comment> getNext3CommentsByPID(String id, int amount) {
        List<Comment> data = new ArrayList<Comment>();
        String sql = "SELECT * \n"
                + "FROM comment\n"
                + "where ProductCategoriesID = ?\n"
                + "ORDER BY ID DESC \n"
                + "LIMIT 3 OFFSET ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            st.setInt(2, amount);
            ResultSet rs = st.executeQuery();
            UserDAO userDAO = new UserDAO();
            ProductCategoriesDAO pdcateDAO = new ProductCategoriesDAO();

            Comment cmt = null;
            while (rs.next()) {
                cmt = new Comment(rs.getInt("ID"),
                        userDAO.getUserById(rs.getInt("UserID")),
                        pdcateDAO.getProductCateByID(rs.getString("ProductCategoriesID")),
                        rs.getString("Title"),
                        rs.getString("Message"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(cmt);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public void updateIsDeleteFeedback(String ID){
        String sql = "Update Comment set IsDelete = 1 where ID = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ID);
            ps.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
// Method to get current timestamp
    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String[] args) throws SQLException {
        CommentDAO cmtDAO = new CommentDAO();
        
        //System.out.println(cmtDAO.getListComments());
        //System.out.println(cmtDAO.getCommentsByID("2"));
        //cmtDAO.updateIsDeleteFeedback("1");
        System.out.println(cmtDAO.getCommentsByID("1"));
    }
}
