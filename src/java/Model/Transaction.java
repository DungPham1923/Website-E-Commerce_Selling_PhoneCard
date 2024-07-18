package Model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int id;
    private int userId;
    private int orderId;
    private User user;
    private Order order;
    private double amount;
    private String type;
    private String paymentCode;
    private String bankCode;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private boolean isDelete;
    private int deletedBy;

    public Transaction() {
        
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", amount=" + amount + ", type=" + type + ", paymentCode=" + paymentCode + ", bankCode=" + bankCode + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", isDelete=" + isDelete + ", deletedBy=" + deletedBy + '}';
    }

    public Transaction(int id, int userId, int orderId, double amount, String type, String paymentCode, String bankCode, String status, Date createdAt, Date updatedAt, Date deletedAt, boolean isDelete, int deletedBy) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.type = type;
        this.paymentCode = paymentCode;
        this.bankCode = bankCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = isDelete;
        this.deletedBy = deletedBy;
    }

    public Transaction(int userId, int orderId, double amount, String type, String paymentCode, String bankCode, String status) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.type = type;
        this.paymentCode = paymentCode;
        this.bankCode = bankCode;
        this.status = status;
    }
    
    public Transaction(int ID, User user, Order order, double amount, String type, String paymentCode, String bankCode, String status, Date createAt, Date updateAt, Date deleteAt, boolean isDelete, int deleteBy) {
        this.id = ID;
        this.user = user;
        this.order = order;
        this.amount = amount;
        this.type = type;
        this.paymentCode = paymentCode;
        this.bankCode = bankCode;
        this.status = status;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
        this.deletedAt = deleteAt;
        this.isDelete = isDelete;
        this.deletedBy = deleteBy;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public int getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(int deletedBy) {
        this.deletedBy = deletedBy;
    }
    
}
