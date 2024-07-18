
package Model;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private int TotalQuantity;
    private double totalAmount;
    private String status;
    private java.sql.Date CreatedAt;
    private java.sql.Date UpdatedAt;
    private java.sql.Date DeletedAt;
    private Boolean IsDelete;
    private int DeletedBy;

    public Order() {
    }

    public Order(int id, int userId, int TotalQuantity, double totalAmount, String status, java.sql.Date CreatedAt, java.sql.Date UpdatedAt, java.sql.Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.id = id;
        this.userId = userId;
        this.TotalQuantity = TotalQuantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }

    public Order(int userId, int TotalQuantity, double totalAmount) {
        this.userId = userId;
        this.TotalQuantity = TotalQuantity;
        this.totalAmount = totalAmount;
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

    public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.sql.Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(java.sql.Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public java.sql.Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(java.sql.Date UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }

    public java.sql.Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(java.sql.Date DeletedAt) {
        this.DeletedAt = DeletedAt;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(int DeletedBy) {
        this.DeletedBy = DeletedBy;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", TotalQuantity=" + TotalQuantity + ", totalAmount=" + totalAmount + ", status=" + status + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedBy=" + DeletedBy + '}';
    }
}
