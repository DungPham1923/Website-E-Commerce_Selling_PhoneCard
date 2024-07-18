package DAL;

import Model.Order;
import Model.OrderDetails;
import Model.ProductCard;
import com.mysql.cj.xdevapi.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext {

    public int insertOrder(Order order) {
        String sql = "INSERT INTO Orders (UserID, TotalQuantity, TotalAmount, Status, CreatedAt) VALUES (?, ?, ?, 'Unpaid', NOW())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalQuantity());
            ps.setDouble(3, order.getTotalAmount());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Thêm đơn hàng không thành công, không có hàng hóa được thêm vào.");
            }

            // Lấy orderId đã được sinh ra từ cơ sở dữ liệu
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    return orderId;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void insertOrderDetail(OrderDetails detail) {
        String sql = "INSERT INTO OrderDetails (OrderID, ProductCategoriesID, ProductCategoriesName, BrandName, ProductPrice, Discount, Amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        detail.getProductCategoriesName();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, detail.getOrderID());
            ps.setInt(2, detail.getProductCategoriesID());
            ps.setString(3, detail.getProductCategoriesName());
            ps.setString(4, detail.getProductCategoriesName());
            ps.setDouble(5, detail.getProductPrice());
            ps.setDouble(6, detail.getDiscount());
            ps.setDouble(7, detail.getAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Order selectOrder(int userID) {
        String sql = "SELECT *\n"
                + "FROM Orders\n"
                + "WHERE ID = (SELECT MAX(id) FROM Orders WHERE UserID = ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ID"));
                order.setUserId(rs.getInt("UserID"));
                order.setTotalQuantity(rs.getInt("TotalQuantity"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatus(rs.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Order getOrderByUserID(int userID) {

        String query = "SELECT * FROM Orders WHERE UserID = ?";  // Corrected here
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderByOrderID(int orderID) {

        String query = "SELECT * FROM Orders WHERE ID = ?";  // Corrected here
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderNewByUserID(int userId) {
        String query = "SELECT * FROM `Orders` WHERE `UserID` = ? ORDER BY Id DESC LIMIT 1;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderNewByID(String orderId) {
        String query = "SELECT * FROM `Orders` WHERE `ID` LIKE ? ;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderNewByID(int orderId) {
        String query = "SELECT * FROM `Orders` WHERE `ID` LIKE ? ;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Dũng-Iter3
    public List<Order> getPendingOrders(int userId) {
        List<Order> orderPendingList = new ArrayList<Order>();
        String query = "SELECT * FROM `Orders` WHERE ID = ? AND `UserID` = ? AND Status = 'Unpaid' ORDER BY Id DESC ;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));
                orderPendingList.add(o);
            }
            return orderPendingList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getListOrderByUserID(int userID) {
        List<Order> data = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE UserID = ? ORDER BY ID DESC";  // Corrected here
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));
                data.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Order> getAllOrder(int indexPage) {
        List<Order> data = new ArrayList<>();
        int size = 25;
        String query = "SELECT * FROM Orders WHERE 1 = 1 ORDER BY ID ASC LIMIT ?, ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (indexPage - 1) * size); // Vị trí bắt đầu
            ps.setInt(2, size); // Số lượng bản ghi cần lấy
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));
                data.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Order> getListOrderByID(int orderId) {
        List<Order> data = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setUserId(rs.getInt("UserID"));
                o.setTotalQuantity(rs.getInt("TotalQuantity"));
                o.setTotalAmount(rs.getDouble("TotalAmount"));
                o.setStatus(rs.getString("Status"));
                o.setCreatedAt(rs.getDate("CreatedAt"));
                o.setUpdatedAt(rs.getDate("UpdatedAt"));
                o.setDeletedAt(rs.getDate("DeletedAt"));
                o.setIsDelete(rs.getBoolean("IsDelete"));
                o.setDeletedBy(rs.getInt("DeletedBy"));
                data.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getTotalOrder() {
        String query = "SELECT COUNT(*) AS total FROM Orders";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trường hợp có lỗi hoặc không có kết quả, trả về 0 hoặc giá trị mặc định khác phù hợp
    }

    public List<OrderDetails> getAllOrderDetailsByOderId(int OrderID) {
        List<OrderDetails> orders = new ArrayList<>();
        ProductCategoriesDAO productcategoriesdao = new ProductCategoriesDAO();
        String query = "SELECT * FROM orderdetails where OrderID = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            OrderDAO o = new OrderDAO();
            ProductCartDAO pc = new ProductCartDAO();
            while (rs.next()) {
                orders.add(new OrderDetails(rs.getInt("ID"),
                        rs.getInt("OrderID"),
                        productcategoriesdao.getProductCategoriesByID(rs.getInt("ProductCategoriesID")),
                        pc.getProductCartByID(rs.getInt("ProductCardID")),
                        rs.getDouble("ProductPrice"),
                        rs.getDouble("Discount"),
                        rs.getDouble("Amount")));
            }
            return orders;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

//    public Order getOrderByOrderID(int orderID) {
//        String query = "SELECT * FROM Orders WHERE ID = ?";  // Corrected here
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, orderID);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                Order o = new Order();
//                o.setId(rs.getInt("ID"));
//                o.setUserId(rs.getInt("UserID"));
//                o.setTotalQuantity(rs.getInt("TotalQuantity"));
//                o.setTotalAmount(rs.getDouble("TotalAmount"));
//                o.setStatus(rs.getString("Status"));
//                o.setCreatedAt(rs.getDate("CreatedAt"));
//                o.setUpdatedAt(rs.getDate("UpdatedAt"));
//                o.setDeletedAt(rs.getDate("DeletedAt"));
//                o.setIsDelete(rs.getBoolean("IsDelete"));
//                o.setDeletedBy(rs.getInt("DeletedBy"));
//
//                return o;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public List<OrderDetails> getAllOrderDetailsByOderId2(int OrderID) {
        List<OrderDetails> orders = new ArrayList<>();
        ProductCategoriesDAO productcategoriesdao = new ProductCategoriesDAO();
        String query = "SELECT * FROM orderdetails where OrderID = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, OrderID);
            ResultSet rs = ps.executeQuery();
            OrderDAO o = new OrderDAO();
            ProductCartDAO pc = new ProductCartDAO();
            while (rs.next()) {
                orders.add(new OrderDetails(rs.getInt("ID"), rs.getInt("OrderID"),
                        rs.getInt("ProductCategoriesID"),
                        rs.getInt("ProductCardID"), rs.getDouble("ProductPrice"),
                        rs.getDouble("Discount"), rs.getDouble("Amount"), o.getOrderByOrderID(OrderID)));
            }
            return orders;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<OrderDetails> getAllOrderDetailByUserID(int userID) {
        List<OrderDetails> allOrderDetails = new ArrayList<>();
        List<Order> orders = getListOrderByUserID(userID);

        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
                List<OrderDetails> orderDetails = getAllOrderDetailsByOderId2(order.getId());
                if (orderDetails != null) {
                    allOrderDetails.addAll(orderDetails);
                }
            }
        }
        return allOrderDetails;
    }

    public void updateStatusOrder(int orderId) {
        String sql = "UPDATE `dbprojectswp391_v1`.`orders`\n"
                + "SET\n"
                + "`Status` = 'Paid',\n"
                + "`UpdatedAt` = NOW()\n"
                + "WHERE `ID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            st.executeUpdate();
            //st.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertProductCartOrderDetail(int pdcId, int orderDetailId) {
        String sql = "UPDATE `dbprojectswp391_v1`.`orderdetails`\n"
                + "SET\n"
                + "ProductCardID = ?\n"
                + "WHERE `ID` = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pdcId);
            st.setInt(2, orderDetailId);
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public OrderDetails getOrderDetailsById(String ID) {
        ProductCategoriesDAO productcategoriesdao = new ProductCategoriesDAO();
        String query = "SELECT * FROM orderdetails where ID = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            ProductCartDAO pc = new ProductCartDAO();
            if (rs.next()) {
                OrderDetails od = new OrderDetails();
                od.setID(rs.getInt("ID"));
                od.setOrderID(rs.getInt("OrderID"));
                od.setProductCategories(productcategoriesdao.getProductCategoriesByID(rs.getInt("ProductCategoriesID")));
                od.setProductcard(pc.getProductCartByID(rs.getInt("ProductCardID")));
                od.setProductPrice(rs.getDouble("ProductPrice"));
                od.setAmount(rs.getDouble("Amount"));
                od.setDiscount(rs.getDouble("Discount"));
                return od;
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public OrderDetails getOrderDetailsByintId(int ID) {
        ProductCategoriesDAO productcategoriesdao = new ProductCategoriesDAO();
        String query = "SELECT * FROM orderdetails where ID = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            ProductCartDAO pc = new ProductCartDAO();
            if (rs.next()) {
                OrderDetails od = new OrderDetails();
                od.setID(rs.getInt("ID"));
                od.setOrderID(rs.getInt("OrderID"));
                od.setProductCategories(productcategoriesdao.getProductCategoriesByID(rs.getInt("ProductCategoriesID")));
                od.setProductcard(pc.getProductCartByID(rs.getInt("ProductCardID")));
                od.setProductPrice(rs.getDouble("ProductPrice"));
                od.setAmount(rs.getDouble("Amount"));
                od.setDiscount(rs.getDouble("Discount"));
                return od;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        OrderDAO o = new OrderDAO();
        OrderDetails s = new OrderDetails(1, 1, "Vi", "Vietel", 1000, 0, 1000);
        o.insertOrderDetail(s);
    }
}
