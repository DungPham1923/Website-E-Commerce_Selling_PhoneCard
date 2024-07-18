package DAL;

import Model.GoogleLogin;
import Model.*;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

public class UserDAO extends DBContext {

    public User getUserById(int id) {
        String sql = "SELECT `user`.`ID`,\n"
                + "    `user`.`FirstName`,\n"
                + "    `user`.`LastName`,\n"
                + "    `user`.`Email`,\n"
                + "    `user`.`Phone`,\n"
                + "    `user`.`UserBalance`,\n"
                + "    `user`.`RoleID`,\n"
                + "    `user`.`CreatedAt`,\n"
                + "    `user`.`UpdatedAt`,\n"
                + "    `user`.`DeletedAt`,\n"
                + "    `user`.`IsDelete`,\n"
                + "    `user`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`user`"
                + "Where `user`.`ID` = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                RoleDAO r = new RoleDAO();
                User u = new User(rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getDouble("UserBalance"),
                        r.getUserRoleById(rs.getInt("RoleID")),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateInfoUser(User user) {
        String sql = "UPDATE dbprojectswp391_v1.user\n"
                + "SET\n"
                + "FirstName = ?,\n"
                + "LastName = ?,\n"
                + "Phone = ?,\n"
                + "UpdatedAt = now()\n"
                + "WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getPhone());
            st.setInt(4, user.getID());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateBanlance(double banlance, int userid) {
        String sql = "UPDATE dbprojectswp391_v1.user\n"
                + "SET\n"
                + "UserBalance = ?,\n"
                + "UpdatedAt = now()\n"
                + "WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, banlance);
            st.setInt(2, userid);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String formatMoney(double money) {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);
        String balance = df.format(money);
        return balance;
    }

    public void insertNewUser(GoogleLogin user) {
        try {
            String sql = "INSERT INTO `dbprojectswp391_v1`.`user` ("
                    + "`FirstName`, `LastName`, `Email`, `UserBalance`, `RoleID`, `CreatedAt`, `UpdatedAt`) "
                    + "VALUES (?, ?, ?, ?, ?, NOW(), NOW());";
            PreparedStatement stm1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm1.setString(1, user.getGiven_name());
            stm1.setString(2, user.getFamily_name());
            stm1.setString(3, user.getEmail());
            stm1.setDouble(4, 0.0);
            stm1.setInt(5, 2);
            stm1.executeUpdate();
            ResultSet generateKey = stm1.getGeneratedKeys();
            int userID = -1;
            if (generateKey.next()) {
                userID = generateKey.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            GoogleLoginDAO ggld = new GoogleLoginDAO();
            ggld.insertGUser(user, userID);

        } catch (SQLException ex) {
            Logger.getLogger(GoogleLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUserbyEmail(String email) {
        String sql = "SELECT `user`.`ID`,\n"
                + "    `user`.`FirstName`,\n"
                + "    `user`.`LastName`,\n"
                + "    `user`.`Email`,\n"
                + "    `user`.`Phone`,\n"
                + "    `user`.`UserBalance`,\n"
                + "    `user`.`RoleID`,\n"
                + "    `user`.`CreatedAt`,\n"
                + "    `user`.`UpdatedAt`,\n"
                + "    `user`.`DeletedAt`,\n"
                + "    `user`.`IsDelete`,\n"
                + "    `user`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`user`"
                + "Where `user`.`Email` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("ID"),
                        rs.getString("Email"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void checkoutByBalance(int userId, double totalMoney) {
        String sql = "UPDATE `dbprojectswp391_v1`.`user`\n"
                + "SET\n"
                + "`UserBalance` = `UserBalance` - ?,\n"
                + "`UpdatedAt` = NOW()\n"
                + "WHERE `ID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, totalMoney);
            st.setInt(2, userId);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Thu - Iter 3
    public boolean checkAdmin(int userid){
        try {
            String sql = "select id from `user` where id = ? and roleid = 1";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userid);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    public static void main(String[] args) {
        UserDAO u = new UserDAO();

//        User x3 = u.getUserbyEmail("DungPAHE173131@fpt.edu.vn");
//        User x = new User(1, "Dung", "Pham", null, null, 0, null, null, null, null, false, 0);
//       
//        User c = u.getUserById(16);
//        u.checkoutByBalance(1, 500000);
        u.updateBalance(2, "100000");
    }

    public void updateByAdmin(String id, String firstname, String lastname, String email, String phone, String balance) {
        String sql = "UPDATE dbprojectswp391_v1.user\n"
                + "SET\n"
                + "FirstName = ?,\n"
                + "LastName = ?,\n"
                + "Email = ?,\n"
                + "Phone = ?,\n"
                + "UserBalance = ?,\n"
                + "UpdatedAt = now()\n"
                + "WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, balance);
            st.setString(6, id);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteUser(String id) {
        try {
            String sql = "UPDATE `dbprojectswp391_v1`.`user`\n"
                    + "SET `IsDelete` = 1,\n"
                    + "`DeletedAt` = NOW()\n"
                    + "WHERE `ID` = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStatusByAdmin(String userId, boolean active, int adid) {
        try {
            String sql;
            if (active) {
                sql = "UPDATE `dbprojectswp391_v1`.`user` "
                        + "SET `IsDelete` = ?, "
                        + "`DeletedBy` = ?, "
                        + "`DeletedAt` = NOW() "
                        + "WHERE `ID` = ?;";
            } else {
                sql = "UPDATE `dbprojectswp391_v1`.`user` "
                        + "SET `IsDelete` = ?, "
                        + "`DeletedBy` = NULL, "
                        + "`DeletedAt` = NULL "
                        + "WHERE `ID` = ?;";
            }

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, active);
            if (active) {
                stm.setInt(2, adid);
                stm.setString(3, userId);
            } else {
                stm.setString(2, userId);
            }
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateBalance(int id, String vnp_Amount) {
        try {
            double amountToAdd = Double.parseDouble(vnp_Amount);

            String sql = "UPDATE `dbprojectswp391_v1`.`user`\n"
                    + "SET `UserBalance` = `UserBalance` + ?,\n"
                    + "`UpdatedAt` = NOW()\n"
                    + "WHERE `ID` = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDouble(1, amountToAdd);
            stm.setInt(2, id);

            stm.executeUpdate();
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

}
