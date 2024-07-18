package Model;

import java.sql.Date;

public class ProductCard {
    private int ID;
    private ProductCategories productCategories;
    private String Seri;
    private String Code;
    private java.sql.Date CreatedAt;
    private java.sql.Date ExpiredDate;
    private java.sql.Date UpdatedAt;
    private java.sql.Date DeletedAt;
    private Boolean IsDelete;
    private int DeletedBy;

    public ProductCard() {
    }

    public ProductCard(int ID, ProductCategories productCategories, String Seri, String Code, Date CreatedAt, Date ExpiredDate, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.ID = ID;
        this.productCategories = productCategories;
        this.Seri = Seri;
        this.Code = Code;
        this.CreatedAt = CreatedAt;
        this.ExpiredDate = ExpiredDate;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ProductCategories getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(ProductCategories productCategories) {
        this.productCategories = productCategories;
    }

    public String getSeri() {
        return Seri;
    }

    public void setSeri(String Seri) {
        this.Seri = Seri;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Date getExpiredDate() {
        return ExpiredDate;
    }

    public void setExpiredDate(Date ExpiredDate) {
        this.ExpiredDate = ExpiredDate;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date DeletedAt) {
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
        return "ProductCard{" + "ID=" + ID + ", productCategories=" + productCategories + ", Seri=" + Seri + ", Code=" + Code + ", CreatedAt=" + CreatedAt + ", ExpiredDate=" + ExpiredDate + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedBy=" + DeletedBy + '}';
    }
}