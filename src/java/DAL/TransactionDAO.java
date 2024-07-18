package DAL;

import Model.Order;
import Model.Transaction;
import Model.User;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends DBContext {

    public List<Transaction> getDepositHistoryByUserID(int userId) {
        List<Transaction> deposits = new ArrayList<>();
        String sql = "select *from Transaction"
                + " Where UserID = ?"
                + " ORDER BY ID DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getInt("OrderID"),
                        rs.getDouble("Amount"),
                        rs.getString("Type"),
                        rs.getString("PaymentCode"),
                        rs.getString("BankCode"),
                        rs.getString("Status"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy"));
                deposits.add(transaction);
            }
        } catch (Exception e) {
            System.out.println("Error fetching deposit history: " + e.getMessage());
        }

        return deposits;
    }

//    public boolean addTransaction(Transaction transaction) {
//        String sql = "INSERT INTO transactions (UserID, OrderID, Amount, Type, PaymentCode, BankCode, Status, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy) "
//                   + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), null, null, false, null)";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setInt(1, transaction.getUserId());
//            st.setInt(2, transaction.getOrderId());
//            st.setBigDecimal(3, transaction.getAmount());
//            st.setString(4, transaction.getType());
//            st.setString(5, transaction.getPaymentCode());
//            st.setString(6, transaction.getBankCode());
//            st.setString(7, transaction.getStatus());
//            int rowsAffected = st.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.out.println("Error adding transaction: " + e.getMessage());
//        }
//        return false;
//    }
//
//    public boolean deleteTransaction(int transactionId, int userId) {
//        String sql = "UPDATE transactions SET DeletedAt = NOW(), IsDelete = true, DeletedBy = ? WHERE ID = ?";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setInt(1, userId);
//            st.setInt(2, transactionId);
//            int rowsAffected = st.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.out.println("Error deleting transaction: " + e.getMessage());
//        }
//        return false;
//    }
//
//    public boolean restoreTransaction(int id) {
//        String sql = "UPDATE transactions SET IsDelete = false, DeletedBy = null, DeletedAt = null WHERE ID = ?";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setInt(1, id);
//            int rowsAffected = st.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.out.println("Error restoring transaction: " + e.getMessage());
//        }
//        return false;
//    }
//
//    public boolean updateTransaction(Transaction newTransaction) {
//        String sql = "UPDATE transactions SET UserID = ?, OrderID = ?, Amount = ?, Type = ?, PaymentCode = ?, BankCode = ?, Status = ?, UpdatedAt = NOW() WHERE ID = ?";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setInt(1, newTransaction.getUserId());
//            st.setInt(2, newTransaction.getOrderId());
//            st.setBigDecimal(3, newTransaction.getAmount());
//            st.setString(4, newTransaction.getType());
//            st.setString(5, newTransaction.getPaymentCode());
//            st.setString(6, newTransaction.getBankCode());
//            st.setString(7, newTransaction.getStatus());
//            st.setInt(8, newTransaction.getId());
//            int rowsAffected = st.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            System.out.println("Error updating transaction: " + e.getMessage());
//        }
//        return false;
//    }
    public void addTransitionAfterCheckoutByBalance(Transaction trans) {
        String sql = "INSERT INTO `dbprojectswp391_v1`.`transaction` "
                + "(`UserID`, `OrderID`, `Amount`, `Type`, `Status`, `CreatedAt`, `UpdatedAt`) "
                + "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, trans.getUserId());
            ps.setInt(2, trans.getOrderId());
            ps.setDouble(3, trans.getAmount());
            ps.setString(4, trans.getType());
            ps.setString(5, trans.getStatus());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Thêm giao dịch thành công!");
            } else {
                System.out.println("Không thể thêm giao dịch. Vui lòng kiểm tra lại.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addTransitionAfterCheckoutByBanking(Transaction trans) {
        String sql = "INSERT INTO `dbprojectswp391_v1`.`transaction` "
                + "(`UserID`, `OrderID`, `Amount`, `Type`, `PaymentCode` , `BankCode`, `Status`, `CreatedAt`, `UpdatedAt`) "
                + "VALUES (?, ?, ?, ?, ?, ? , ?, NOW(), NOW())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, trans.getUserId());
            ps.setInt(2, trans.getOrderId());
            ps.setDouble(3, trans.getAmount());
            ps.setString(4, trans.getType());
            ps.setString(5, trans.getPaymentCode());
            ps.setString(6, trans.getBankCode());
            ps.setString(7, trans.getStatus());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Thêm giao dịch thành công!");
            } else {
                System.out.println("Không thể thêm giao dịch. Vui lòng kiểm tra lại.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO `dbprojectswp391_v1`.`transaction`\n"
                + "(`UserID`,\n"
                + "`OrderID`,\n"
                + "`Amount`,\n"
                + "`Type`,\n"
                + "`PaymentCode`,\n"
                + "`BankCode`,\n"
                + "`Status`,\n"
                + "`CreatedAt`,\n"
                + "`UpdatedAt`,\n"
                + "`DeletedAt`,\n"
                + "`IsDelete`,\n"
                + "`DeletedBy`)\n"
                + "VALUES\n"
                + "(?, ?, ?, ?, ?, ?, ?, NOW(), null, null, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, transaction.getUser().getID());
            ps.setInt(2, transaction.getOrder().getId());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getType());
            ps.setString(5, transaction.getPaymentCode());
            ps.setString(6, transaction.getBankCode());
            ps.setString(7, transaction.getStatus());
            ps.setBoolean(8, false);
            ps.setInt(9, 0);

            int rowsAffected = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }

            return rowsAffected;
        } catch (SQLException e) {
            System.out.println("Error inserting transaction: " + e.getMessage());
            return 0;
        }
    }

    private static java.sql.Timestamp getCurrentTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    public void insertTransaction(User user, Order orderTransaction, double amountToAdd, String deposit, String vnp_TxnRef, String vnp_BankCode, String statusMessage) {
        java.sql.Timestamp createdAt = getCurrentTimestamp();
        Transaction t = new Transaction(0, user, orderTransaction, amountToAdd, "Deposit", vnp_TxnRef, vnp_BankCode, statusMessage, createdAt, null, null, true, 0);
        TransactionDAO tDao = new TransactionDAO();
        tDao.insertTransaction(t);
        System.out.println(tDao);
    }

    public void updateStatusTransaction(int orderId) {
        String sql = "UPDATE `dbprojectswp391_v1`.`transaction`\n"
                + "SET\n"
                + "`Status` = 'Success',\n"
                + "`UpdatedAt` = NOW()\n"
                + "WHERE `OrderID` = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        TransactionDAO t = new TransactionDAO();
        //System.out.println(transactionDAO.getDepositHistory());
        Transaction trans = new Transaction(1, 1, 100000, "Payment", null, null, "Success");
        //t.addTransitionAfterCheckoutByBalance(trans);

//        for (Transaction o : t.getDepositHistoryByUserID(1)) {
//            System.out.println(o);
//        }
        t.updateStatusTransaction(1);
    }

}
