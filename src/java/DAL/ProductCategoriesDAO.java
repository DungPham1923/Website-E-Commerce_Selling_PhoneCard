package DAL;

import Model.Cart;
import Model.ProductCategories;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoriesDAO extends DBContext {

    public ProductCategories getProductCategoriesByID(int id) {
        String sql = "select * \n"
                + "from productcategories \n"
                + "where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            BrandDAO brandDAO = new BrandDAO();
            ProductCategories y;
            if (rs.next()) {
                y = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                return y;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<ProductCategories> getListProduct() {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setString(1, id);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();

            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<ProductCategories> filterListProductByName(int index, int size, String name) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`"
                + "WHERE `productcategories`.`Name` LIKE ? OR `productcategories`.`Description` LIKE ?"
                + "ORDER BY ID LIMIT ?, ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setString(2, "%" + name + "%");
            st.setInt(3, (index - 1) * size);
            st.setInt(4, size);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();

            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<ProductCategories> filterListProductByBrandId(int id, double price, int index, int size) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`";
        //+ "WHERE `productcategories`.`BrandID` = ?;";
        if (id != 0 && price != 0) {
            sql += "WHERE `productcategories`.`BrandID` = " + id + " AND `productcategories`.`Price` = " + price;
        } else if (price == 0 && id != 0) {
            sql += "WHERE `productcategories`.`BrandID` = " + id;
        } else {
            sql += "";
        }
        sql += " ORDER BY `productcategories`.`ID`"
                + " LIMIT ?, ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (index - 1) * size);
            st.setInt(2, size);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();

            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<ProductCategories> filterListProductByPrice(double price, int brandId) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`"
                + "WHERE `productcategories`.`Price` = ? ";
        if (brandId != 0) {
            sql += "AND `productcategories`.`BrandID` = " + brandId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, price);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();

            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    private final BrandDAO bdao = new BrandDAO();

    public ProductCategories getProductCategoriesById(int id) {
        String sql = "select * from ProductCategories where ID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductCategories pc = new ProductCategories();
                pc.setId(rs.getInt("ID"));
                pc.setBrand(bdao.getBrandById(rs.getInt("BrandID")));
                pc.setName(rs.getString("Name"));
                pc.setPrice(rs.getDouble("Price"));
                pc.setImage(rs.getString("Image"));
                pc.setQuantity(rs.getInt("Quantity"));
                pc.setDescription(rs.getString("Description"));
                pc.setDiscount(rs.getDouble("Discount"));
                pc.setDiscountFrom(rs.getDate("DiscountFrom"));
                pc.setDiscountTo(rs.getDate("DiscountTo"));
                pc.setCreatedAt(rs.getDate("CreatedAt"));
                pc.setUpdatedAt(rs.getDate("UpdatedAt"));
                pc.setDeletedAt(rs.getDate("DeletedAt"));
                pc.setIsDelete(rs.getBoolean("IsDelete"));
                pc.setDeletedBy(rs.getInt("DeletedBy"));
                return pc;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<ProductCategories> sortProduct(String sort, int brandId) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories` ";

        if (brandId != 0) {
            sql += " WHERE `productcategories`.`BrandID` = " + brandId + " ORDER BY `productcategories`.`Price` " + sort;
        } else {
            sql += " ORDER BY `productcategories`.`Price` " + sort;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();
            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }
//    public ProductCategories getProductCategoriesByID(int id) {
//        String sql = "select * \n"
//                + "from productcategories \n"
//                + "where ID = ?;";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, id);
//            ResultSet rs = st.executeQuery();
//            
//            BrandDAO brandDAO = new BrandDAO();
//            ProductCategories y;
//            if (rs.next()) {
//                y = new ProductCategories(rs.getInt("ID"),
//                        brandDAO.getBrandById(rs.getInt("BrandID")),
//                        rs.getString("Name"),
//                        rs.getDouble("Price"),
//                        rs.getString("Image"),
//                        rs.getInt("Quantity"),
//                        rs.getString("Description"),
//                        rs.getDouble("Discount"),
//                        rs.getDate("DiscountFrom"),
//                        rs.getDate("DiscountTo"),
//                        rs.getDate("CreatedAt"),
//                        rs.getDate("UpdatedAt"),
//                        rs.getDate("DeletedAt"),
//                        rs.getBoolean("IsDelete"),
//                        rs.getInt("DeletedBy"));
//                return y;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }

    public List<Double> getDistinctPrices() {
        List<Double> pricesList = new ArrayList<>();
        String sql = "SELECT DISTINCT Price FROM ProductCategories";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                pricesList.add(rs.getDouble("Price"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        double[] pricesArray = new double[pricesList.size()];
        for (int i = 0; i < pricesList.size(); i++) {
            pricesArray[i] = pricesList.get(i);
        }

        return pricesList;
    }

    public String formatPrice(double price) {
        String balance = "";
        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);
        balance = df.format(price);
        return balance;
    }

    public int getTotalProductCategories() {
        String sql = "SELECT COUNT(*) FROM ProductCategories \n"
                + "WHERE IsDelete = 0";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getQuantity(int productid) {
        String sql = "select quantity from productcategories where ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public List<ProductCategories> getProductCategoriesByPaging(int index, int size, int idBrand, double price, String name, String sort) { //truyền index page để lấy giá trị offset
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT * FROM ProductCategories WHERE 1=1 AND IsDelete = 0 \n";
        if (idBrand != 0) {
            sql += " AND BrandID = " + idBrand;
        }
        if (price != 0) {
            sql += " AND Price = " + price;
        }
        if (name != null && !name.trim().isEmpty()) {
            sql += " AND (`productcategories`.`Name` LIKE '%" + name + "%' OR `productcategories`.`Description` LIKE '%" + name + "%')";
        }

        if (sort != null) {
            sql += " ORDER BY Price " + sort + " \n"
                    + " LIMIT ?, ?;";
        } else {
            sql += " ORDER BY Id\n"
                    + " LIMIT ?, ?;";
        }

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (index - 1) * size);
            st.setInt(2, size);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();
            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public List<ProductCategories> getAmountProductCategories(int idBrand, double price, String name) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT * FROM ProductCategories WHERE 1=1 AND IsDelete = 0 \n";
        if (idBrand != 0) {
            sql += " AND BrandID = " + idBrand;
        }
        if (price != 0) {
            sql += " AND Price = " + price;
        }
        if (name != null && !name.trim().isEmpty()) {
            sql += " AND Name LIKE '%" + name + "%'";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();
            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public ProductCategories getProductCateByID(String id) {

        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`\n"
                + "WHERE `productcategories`.`ID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();
            ProductCategories productCate = null;
            if (rs.next()) {
                productCate = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
            }
            return productCate;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<ProductCategories> filterListProductByBrandId(int id) {
        List<ProductCategories> data = new ArrayList<ProductCategories>();
        String sql = "SELECT `productcategories`.`ID`,\n"
                + "    `productcategories`.`BrandID`,\n"
                + "    `productcategories`.`Name`,\n"
                + "    `productcategories`.`Price`,\n"
                + "    `productcategories`.`Image`,\n"
                + "    `productcategories`.`Quantity`,\n"
                + "    `productcategories`.`Description`,\n"
                + "    `productcategories`.`Discount`,\n"
                + "    `productcategories`.`DiscountFrom`,\n"
                + "    `productcategories`.`DiscountTo`,\n"
                + "    `productcategories`.`CreatedAt`,\n"
                + "    `productcategories`.`UpdatedAt`,\n"
                + "    `productcategories`.`DeletedAt`,\n"
                + "    `productcategories`.`IsDelete`,\n"
                + "    `productcategories`.`DeletedBy`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`";
        //+ "WHERE `productcategories`.`BrandID` = ?;";
        if (id != 0) {
            sql += "WHERE `productcategories`.`BrandID` = " + id;
        } else {
            sql += "";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();

            ProductCategories product = null;
            while (rs.next()) {
                product = new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public String getNameProductCategoriesByID(int pctId) {
        String sql = "SELECT `Name` FROM `productcategories` WHERE `Id` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pctId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy tên danh mục sản phẩm: " + e.getMessage());
        }
        return null;
    }

    public List<ProductCategories> getProductCategoriesPagination(int index, int brandid) {
        List<ProductCategories> list = new ArrayList<>();
        String sql = "select * from productcategories\n"
                + "where brandid = ?\n"
                + "order by id desc\n"
                + "limit 10 offset ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, brandid);
            st.setInt(2, (index - 1) * 10);
            ResultSet rs = st.executeQuery();
            BrandDAO brandDAO = new BrandDAO();
            while (rs.next()) {
                list.add(new ProductCategories(rs.getInt("ID"),
                        brandDAO.getBrandById(rs.getInt("BrandID")),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("Image"),
                        rs.getInt("Quantity"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getDate("DiscountFrom"),
                        rs.getDate("DiscountTo"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void updateCategoryDeleteStatus(int pcid, int userid) {
        try {
            String sql = "update productcard\n"
                    + "set DeletedAt = curdate(),\n"
                    + "IsDelete = true, \n"
                    + "DeletedBy = ?\n"
                    + "where ProductCategoriesID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setInt(2, pcid);
            ps.executeUpdate();
            String sql1 = "update productcategories\n"
                    + "set DeletedAt = curdate(),\n"
                    + "IsDelete = true, \n"
                    + "DeletedBy = ?\n"
                    + "where ID = ?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, userid);
            ps1.setInt(2, pcid);
            ps1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCategoryActiveStatus(int pcid, int userid) {
        try {
            String sql = "UPDATE productcard "
                    + "SET IsDelete = FALSE, updatedat = CURDATE(), DeletedBy = NULL, DeletedAt = NULL\n"
                    + "WHERE ProductCategoriesID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pcid);
            ps.executeUpdate();
            String sql1 = "update productcategories\n"
                    + "set IsDelete = false, updatedat = CURDATE(), DeletedBy = NULL, DeletedAt = NULL\n"
                    + "where ID = ?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, pcid);
            ps1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Thu - Iter 3
    public boolean checkDeleteCategory(int id) {
        try {
            String sql = "select id from productcategories where id = ? and isdelete = true";
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

    // Thu - Iter 3
    public void updateCategory(int id, String name, double price, String description,
            double discount, String discountFrom, String discountTo) {
        try {
            String sql = "update productcategories\n"
                    + "set name = ?, price = ?, description = ?, discount = ?,\n"
                    + "discountfrom = COALESCE(?, discountfrom),\n"
                    + "discountto = COALESCE(?, discountto), updatedat = curdate()\n"
                    + "where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, description);
            ps.setDouble(4, discount);
            ps.setString(5, !discountFrom.equals("") ? discountFrom : null);
            ps.setString(6, !discountTo.equals("") ? discountTo : null);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Thu - Iter3 
    public void addCategory(String name, double price, String description, int brandid, int userid) {
        try {
            if (bdao.checkDeleteBrand(brandid)) {
                String sql = "INSERT INTO ProductCategories (BrandID, Name, Price, Quantity, Description, Discount, DiscountFrom, DiscountTo, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy)\n"
                        + "VALUES (?, ?, ?, 0, ?, NULL, NULL, NULL, CURDATE(), CURDATE(), CURDATE(), TRUE, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, brandid);
                ps.setString(2, name);
                ps.setDouble(3, price);
                ps.setString(4, description);
                ps.setInt(5, userid);
                ps.executeUpdate();
            } else {
                String sql = "INSERT INTO ProductCategories (BrandID, Name, Price, Quantity, Description, Discount, DiscountFrom, DiscountTo, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy)\n"
                        + "VALUES (?, ?, ?, 0, ?, NULL, NULL, NULL, CURDATE(), CURDATE(), NULL, FALSE, NULL)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, brandid);
                ps.setString(2, name);
                ps.setDouble(3, price);
                ps.setString(4, description);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Thu - Iter 3
    public boolean checkExistedPrice(double price, int id, int brandid) {
        try {
            String sql = "select price from productcategories where price = ? and id != ?\n"
                    + "and brandid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, id);
            ps.setInt(3, brandid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkQuantityValid(int pcdId, int quantity) {
        String sql = "SELECT `productcategories`.`Quantity`\n"
                + "FROM `dbprojectswp391_v1`.`productcategories`"
                + "WHERE `productcategories`.`ID` = ? AND `productcategories`.`IsDelete` = 0";
        int quantityCurrent = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pcdId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quantityCurrent = rs.getInt("Quantity");
                if (quantityCurrent >= quantity) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void updateQuantityAfterCheckout(int pcdId, int quantity) {
        String selectSql = "SELECT `productcategories`.`Quantity` "
                + "FROM `dbprojectswp391_v1`.`productcategories` "
                + "WHERE `productcategories`.`ID` = ? AND `productcategories`.`IsDelete` = 0";
        String updateSql = "UPDATE `dbprojectswp391_v1`.`productcategories` "
                + "SET `Quantity` = ? "
                + "WHERE `ID` = ? AND `IsDelete` = 0";
        int quantityCurrent = 0;

        try (PreparedStatement selectPs = connection.prepareStatement(selectSql)) {
            selectPs.setInt(1, pcdId);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    quantityCurrent = rs.getInt("Quantity");
                }
            }

            int newQuantity = quantityCurrent - quantity;

            try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                updatePs.setInt(1, newQuantity);
                updatePs.setInt(2, pcdId);
                int rowsUpdated = updatePs.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Cập nhật số lượng thành công!");
                } else {
                    System.out.println("Không thể cập nhật số lượng. Vui lòng kiểm tra lại.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ProductCategoriesDAO p = new ProductCategoriesDAO();
        //System.out.println(p.getNameProductCategoriesByID(4));
        //System.out.println(p.checkQuantityValid(1, 5));
        //p.updateQuantityAfterCheckout(1, 2);
        //System.out.println(p.getTotalProductCategories());
        
        List<ProductCategories> list = p.getAmountProductCategories(1, 0, null);
        for (ProductCategories o : list) {
            System.out.println(o.toString());
        }
    }
}
