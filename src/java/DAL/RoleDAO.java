package DAL;

import Model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDAO extends DBContext {

    public Role getRoleById(int roleId) {
        Role r = null;
        String sql = "SELECT `userrole`.`ID`,\n"
                + "    `userrole`.`RoleName`,\n"
                + "    `userrole`.`Description`,\n"
                + "    `userrole`.`CreatedAt`,\n"
                + "    `userrole`.`UpdatedAt`,\n"
                + "    `userrole`.`DeletedAt`,\n"
                + "    `userrole`.`IsDelete`\n"
                + "FROM `dbprojectswp391_v1`.`userrole`"
                + "WHERE `userrole`.`ID` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                r = new Role(rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return r;
    }

    public Role getUserRoleById(int id) {
        String sql = "SELECT `userrole`.`ID`,\n"
                + "    `userrole`.`RoleName`,\n"
                + "    `userrole`.`Description`,\n"
                + "    `userrole`.`CreatedAt`,\n"
                + "    `userrole`.`UpdatedAt`,\n"
                + "    `userrole`.`DeletedAt`,\n"
                + "    `userrole`.`IsDelete`\n"
                + "FROM `dbprojectswp391_v1`.`userrole`"
                + "Where `userrole`.`ID` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Role userRole = new Role(rs.getInt("ID"),
                        rs.getString("roleName"),
                        rs.getString("description"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt"),
                        rs.getDate("deletedAt"),
                        rs.getBoolean("isDelete"));
                return userRole;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
