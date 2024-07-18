package DAL;

import Model.Brand;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO extends DBContext {

    public Brand getBrandByName(String name) {
        String sql = "SELECT ID, BrandName, Image, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy "
                + "FROM brands WHERE BrandName = ?";
        Brand brand = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching brand by ID: " + e.getMessage());
        }
        return brand;
    }

    public Brand getBrandById(int Id) {
        String sql = "SELECT `brands`.`ID`,\n"
                + "    `brands`.`BrandName`,\n"
                + "    `brands`.`Image`,\n"
                + "    `brands`.`CreatedAt`,\n"
                + "    `brands`.`UpdatedAt`,\n"
                + "    `brands`.`DeletedAt`,\n"
                + "    `brands`.`IsDelete`,\n"
                + "    `brands`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`brands`"
                + "WHERE `brands`.`ID` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();
            Brand brand = null;
            while (rs.next()) {
                brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                return brand;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Brand> getListBrand() {
        List<Brand> data = new ArrayList<Brand>();
        String sql = "SELECT `brands`.`ID`,\n"
                + "    `brands`.`BrandName`,\n"
                + "    `brands`.`Image`,\n"
                + "    `brands`.`CreatedAt`,\n"
                + "    `brands`.`UpdatedAt`,\n"
                + "    `brands`.`DeletedAt`,\n"
                + "    `brands`.`IsDelete`,\n"
                + "    `brands`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`brands`\n"
                + "WHERE `brands`.`IsDelete` = 0;";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            Brand brand = null;
            while (rs.next()) {
                brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(brand);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<Brand> getListBrand2() {
        List<Brand> data = new ArrayList<Brand>();
        String sql = "SELECT `brands`.`ID`,\n"
                + "    `brands`.`BrandName`,\n"
                + "    `brands`.`Image`,\n"
                + "    `brands`.`CreatedAt`,\n"
                + "    `brands`.`UpdatedAt`,\n"
                + "    `brands`.`DeletedAt`,\n"
                + "    `brands`.`IsDelete`,\n"
                + "    `brands`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`brands`\n";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            Brand brand = null;
            while (rs.next()) {
                brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(brand);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public String getNameBrand(int id) {
        String sql = "SELECT `brands`.`ID`,\n"
                + "    `brands`.`BrandName`,\n"
                + "    `brands`.`Image`,\n"
                + "    `brands`.`CreatedAt`,\n"
                + "    `brands`.`UpdatedAt`,\n"
                + "    `brands`.`DeletedAt`,\n"
                + "    `brands`.`IsDelete`,\n"
                + "    `brands`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`brands`"
                + "WHERE `brands`.`ID` = ?";
        Brand brand = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String brandName = "";
        if (brand != null) {
            brandName = brand.getName().trim();
        } else {
            brandName = "Tất cả";
        }
        return brandName;
    }

    public List<Brand> searchBrands(String keyword) {
        List<Brand> data = new ArrayList<>();
        String sql = "SELECT ID, BrandName, Image, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy "
                + "FROM brands WHERE BrandName LIKE ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand(rs.getInt("ID"),
                        rs.getString("BrandName"),
                        rs.getString("Image"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(brand);
            }
        } catch (Exception e) {
            System.out.println("Error searching brands: " + e.getMessage());
        }
        return data;
    }

//    public boolean deleteBrand(int brandId, int userId) {
//        String sql = "Update brands SET DeletedAt = NOW(), IsDelete = true, DeletedBy = ? WHERE ID = ?";
//        String deleteCategories = "Update productcategories SET DeletedAt = NOW(), IsDelete = true, DeletedBy = ? WHERE brandID =?";
//        String deleteProduct = "UPDATE productcard p "
//                + "JOIN productcategories c ON p.ProductCategoriesID = c.ID "
//                + "SET p.DeletedAt = NOW(), p.IsDelete = TRUE, p.DeletedBy = ? "
//                + "WHERE c.BrandID = ?";
//        try {
//            connection.setAutoCommit(false);
//
//            try (PreparedStatement ps1 = connection.prepareStatement(deleteProduct)) {
//                ps1.setInt(1, userId);
//                ps1.setInt(2, brandId);
//                ps1.executeUpdate();
//            }
//
//            try (PreparedStatement ps2 = connection.prepareStatement(deleteCategories)) {
//                ps2.setInt(1, userId);
//                ps2.setInt(2, brandId);
//                ps2.executeUpdate();
//            }
//
//            try (PreparedStatement ps3 = connection.prepareStatement(sql)) {
//                ps3.setInt(1, userId);
//                ps3.setInt(2, brandId);
//                ps3.executeUpdate();
//            }
//
//            connection.commit();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                connection.rollback(); // Rollback transaction in case of error
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            return false;
//        }
//    }
    public boolean deleteBrand(int brandId, int userId) {
        String sql = "Update brands SET DeletedAt = NOW(), IsDelete = true, DeletedBy = ? WHERE ID = ?";
//        String deleteCategories = "Update productcategories SET DeletedAt = NOW(), IsDelete = true, DeletedBy = ? WHERE brandID =?";
//        String deleteProduct = "UPDATE productcard p "
//                + "JOIN productcategories c ON p.ProductCategoriesID = c.ID "
//                + "SET p.DeletedAt = NOW(), p.IsDelete = TRUE, p.DeletedBy = ? "
//                + "WHERE c.BrandID = ?";
        try {
            connection.setAutoCommit(false);

//            try (PreparedStatement ps1 = connection.prepareStatement(deleteProduct)) {
//                ps1.setInt(1, userId);
//                ps1.setInt(2, brandId);
//                ps1.executeUpdate();
//            }
//
//            try (PreparedStatement ps2 = connection.prepareStatement(deleteCategories)) {
//                ps2.setInt(1, userId);
//                ps2.setInt(2, brandId);
//                ps2.executeUpdate();
//            }
            try (PreparedStatement ps3 = connection.prepareStatement(sql)) {
                ps3.setInt(1, userId);
                ps3.setInt(2, brandId);
                ps3.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public String getNameBrandByPctID(int pctId) {
        String sql = "SELECT B.BrandName\n"
                + "FROM Brands B\n"
                + "INNER JOIN Productcategories P ON P.BrandID = B.ID\n"
                + "WHERE P.ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pctId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("BrandName");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addBrand(Brand brand) {
        String sql = "INSERT INTO brands (BrandName, Image, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy) VALUES (?, ?, NOW(), null, null, false, null)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, brand.getName());
            st.setString(2, brand.getImage());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean restoreBrand(int id) {
        String sql = "UPDATE brands SET IsDelete=false, DeletedBy=null, DeletedAt=null WHERE ID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error updating brand: " + e.getMessage());
        }
        return false;
    }

    public boolean updateBrand(Brand newBrand) {
        String sql = "UPDATE brands SET BrandName=?, Image=?, UpdatedAt=NOW() WHERE ID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newBrand.getName());
            st.setString(2, newBrand.getImage());
            st.setInt(3, newBrand.getId());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error updating brand: " + e.getMessage());
        }
        return false;
    }

    public boolean checkDeleteBrand(int id) {
        try {
            String sql = "select id from brands where id = ? and isdelete = true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
        BrandDAO u = new BrandDAO();
        //System.out.println(u.getNameBrandByPctID(1));
        //u.getListBrand();
        //u.deleteBrand(7, 1);
        List<Brand> list = u.getListBrand();
        for (Brand b : list) {
            System.out.println(b.toString());
        }
    }
}
