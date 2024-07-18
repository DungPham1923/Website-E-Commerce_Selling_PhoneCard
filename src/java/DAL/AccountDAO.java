
package DAL;

import Model.AccountLogin;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.*;


public class AccountDAO extends DBContext {

    public AccountLogin getByUsernamePassword(String username, String password) {
        try {
            String sql = "SELECT `authentication`.`ID`, " +
                     "`authentication`.`UserID`, " +
                     "`authentication`.`UserKey`, " +
                     "`authentication`.`PassWord`, " +
                     "`authentication`.`CreatedAt`, " +
                     "`authentication`.`UpdatedAt`, " +
                     "`authentication`.`DeletedAt`, " +
                     "`authentication`.`IsDelete`, " +
                     "`authentication`.`DeletedByID` " +
                     "FROM `dbprojectswp391_v1`.`authentication` " +
                     "WHERE `authentication`.`UserKey` = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            //stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                AccountLoginDAO ald = new AccountLoginDAO();
                UserDAO u = new UserDAO();
                AccountLogin account = new AccountLogin();
                account.setID(rs.getInt("ID"));
                account.setUser(u.getUserById(rs.getInt("UserID")));
                account.setUserName(username);
                account.setPassword(rs.getString("PassWord"));
                account.setCreatedAt(rs.getDate("CreatedAt"));
                account.setUpdatedAt(rs.getDate("UpdatedAt"));
                account.setUpdatedAt(rs.getDate("DeletedAt"));
                account.setIsDelete(rs.getBoolean("IsDelete"));
                account.setDeletedByID(rs.getInt("DeletedByID"));
                return account;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AccountDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }
}
