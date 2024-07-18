package DAL;

import Model.AccountLogin;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountLoginDAO extends DBContext {

    public AccountLogin getAccountByID(int userId) {
        AccountLogin a;
        String sql = "SELECT `authentication`.`ID`,\n"
                + "    `authentication`.`UserID`,\n"
                + "    `authentication`.`UserKey`,\n"
                + "    `authentication`.`PassWord`,\n"
                + "    `authentication`.`VerifyEmail`,\n"
                + "    `authentication`.`CreatedAt`,\n"
                + "    `authentication`.`UpdatedAt`,\n"
                + "    `authentication`.`DeletedAt`,\n"
                + "    `authentication`.`IsDelete`,\n"
                + "    `authentication`.`DeletedByID`\n"
                + "FROM `dbprojectswp391_v1`.`authentication`"
                + "WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                UserDAO u = new UserDAO();
                User user = u.getUserById(userId);
                a = new AccountLogin(rs.getInt("ID"),
                        user,
                        rs.getString("UserKey"),
                        rs.getString("PassWord"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedByID"));
                return a;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<AccountLogin> getAllAccount() {
        List<AccountLogin> accounts = new ArrayList<>();
        String sql = "SELECT `authentication`.`ID`,\n"
                + "    `authentication`.`UserID`,\n"
                + "    `authentication`.`UserKey`,\n"
                + "    `authentication`.`PassWord`,\n"
                + "    `authentication`.`VerifyEmail`,\n"
                + "    `authentication`.`CreatedAt`,\n"
                + "    `authentication`.`UpdatedAt`,\n"
                + "    `authentication`.`DeletedAt`,\n"
                + "    `authentication`.`IsDelete`,\n"
                + "    `authentication`.`DeletedByID`\n"
                + "FROM `dbprojectswp391_v1`.`authentication`";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserDAO u = new UserDAO();
                User user = u.getUserById(rs.getInt("UserID"));
                AccountLogin account = new AccountLogin(
                        rs.getInt("ID"),
                        user,
                        rs.getString("UserKey"),
                        rs.getString("PassWord"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedByID")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
        return accounts;
    }

    public void updateAccount(AccountLogin account) {
        String sql = "UPDATE `dbprojectswp391_v1`.`authentication`\n"
                + "SET\n"
                + "`PassWord` = ?,\n"
                + "`UpdatedAt` = NOW()\n"
                + "WHERE `ID` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, account.getPassword());
            st.setInt(2, account.getID());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkUsernameExist(String username) {
        String sql = "SELECT `authentication`.`UserKey` "
                + "FROM `dbprojectswp391_v1`.`authentication` "
                + "WHERE `authentication`.`UserKey` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkEmailExist(String email) {
        String sql = "SELECT `user`.`Email` "
                + "FROM `dbprojectswp391_v1`.`user` "
                + "WHERE `user`.`Email` = ?";
        //String sql = "SELECT email FROM `User` WHERE Email = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void register(String username, String email, String password) {
        String sql = "INSERT INTO `dbprojectswp391_v1`.`user` (`Email`, `RoleID`, `CreatedAt`, `UpdatedAt`) "
                + "VALUES (?, 2, NOW(), NOW());";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.executeUpdate();

            String sql_1 = "SELECT * FROM `dbprojectswp391_v1`.`user` ORDER BY ID DESC LIMIT 1;";
            PreparedStatement st_1 = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st_1.executeQuery();
            if (rs_1.next()) {
                int userid = rs_1.getInt("ID");

                String sql_2 = "INSERT INTO `dbprojectswp391_v1`.`authentication` (`UserID`, `UserKey`, `PassWord`, `CreatedAt`, `UpdatedAt`) "
                        + "VALUES (?, ?, ?, NOW(), NOW());";

                PreparedStatement st_2 = connection.prepareStatement(sql_2);
                st_2.setInt(1, userid);
                st_2.setString(2, username); // assuming 'username' is supposed to be 'UserKey'
                st_2.setString(3, password);
                st_2.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void register2(String username, String email, String password) {
        String sql = "INSERT INTO `dbprojectswp391_v1`.`user` (`Email`, `RoleID`, `CreatedAt`, `UpdatedAt`) "
                + "VALUES (?, 2, NOW(), NOW());";
        //String sql = "INSERT INTO User (Email, RoleID, CreatedAt, UpdatedAt)\n"
        //        + "VALUES (?, 2, NOW(), NOW());";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.executeUpdate();
            String sql_1 = "SELECT *\n"
                    + "FROM `dbprojectswp391_v1`.`user` \n"
                    + "ORDER BY ID DESC LIMIT 1";
            //String sql_1 = "SELECT * FROM `User` ORDER BY ID DESC LIMIT 1;";

            PreparedStatement st_1 = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st_1.executeQuery();
            if (rs_1.next()) {
                int userid = rs_1.getInt(1);

                String sql_2 = "INSERT INTO `dbprojectswp391_v1`.`authentication`\n"
                        + "`UserID`,\n"
                        + "`UserKey`,\n"
                        + "`PassWord`,\n"
                        + "`CreatedAt`,\n"
                        + "`UpdatedAt`,\n"
                        + "VALUES\n"
                        + "?,\n"
                        + "?,\n"
                        + "?,\n"
                        + "NOW(),\n"
                        + "NOW(),\n";
                //String sql_2 = "INSERT INTO AccountLogin (UserID, UserName, PassWord, CreatedAt, UpdatedAt)\n"
                //        + "VALUES (?, ?, ?, NOW(), NOW());";
                PreparedStatement st_2 = connection.prepareStatement(sql_2);
                st_2.setInt(1, userid);
                st_2.setString(2, username);
                st_2.setString(3, password);
                st_2.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void changePass(int id, String newPass) {
        try {
            String sql = "UPDATE `dbprojectswp391_v1`.`authentication`\n"
                    + "SET\n"
                    + "`PassWord` = ?,\n"
                    + "`UpdatedAt` = NOW()\n"
                    + "WHERE `UserID` = ? ;";
            //String sql = "UPDATE AccountLogin SET PassWord = ? WHERE UserID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newPass);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        AccountLoginDAO a = new AccountLoginDAO();
        a.updateLoginStatus("2", false, 1);
    }

    public void updateLoginInfo(String id, String password) {
        try {
            String sql = "UPDATE `dbprojectswp391_v1`.`authentication`\n"
                    + "SET\n"
                    + "`PassWord` = ?,\n"
                    + "`UpdatedAt` = NOW()\n"
                    + "WHERE `UserID` = ? ;";
            //String sql = "UPDATE AccountLogin SET PassWord = ? WHERE UserID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, id);
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteAccount(String id) {
        try {
            String sql = "UPDATE `dbprojectswp391_v1`.`authentication`\n"
                    + "SET `IsDelete` = 1,\n"
                    + "`DeletedAt` = NOW()\n"
                    + "WHERE `UserID` = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<AccountLogin> findUserByName(String name) {
        ArrayList<AccountLogin> listUserSearch = new ArrayList<>();

        String sql = "SELECT * FROM Authentication WHERE UserKey LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set the parameter for the prepared statement
            pstmt.setString(1, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                // Process the result set
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int userId = rs.getInt("UserID");
                    User u = new User();
                    UserDAO udao = new UserDAO();
                    u = udao.getUserById(userId);
                    String userKey = rs.getString("UserKey");
                    String password = rs.getString("PassWord");
                    Date createdAt = rs.getDate("CreatedAt");
                    Date updatedAt = rs.getDate("UpdatedAt");
                    boolean isDelete = rs.getBoolean("IsDelete");
                    int deletedById = rs.getInt("DeletedByID");

                    // Create a new AccountLogin object and add it to the list
                    AccountLogin accountLogin = new AccountLogin(id, u, userKey, password, createdAt, updatedAt, isDelete, deletedById);
                    listUserSearch.add(accountLogin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listUserSearch;
    }

    public void updateLoginStatus(String userId, boolean active, int adid) {
        try {
            String sql;
            if (active) {
                sql = "UPDATE `dbprojectswp391_v1`.`authentication` "
                        + "SET `IsDelete` = ?, "
                        + "`DeletedAt` = NOW(), "
                        + "`DeletedByID` = ? "
                        + "WHERE `UserID` = ?;";
            } else {
                sql = "UPDATE `dbprojectswp391_v1`.`authentication` "
                        + "SET `IsDelete` = ?, "
                        + "`DeletedAt` = NULL, "
                        + "`DeletedByID` = NULL "
                        + "WHERE `UserID` = ?;";
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

}
