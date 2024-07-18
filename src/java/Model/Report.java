/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;


public class Report {
    private int Id;
    private int UserId;
    private int OrderDetailId;
    private ProductCard ProductCardId;
    private String ProductCategoriesName;
    private String BrandName;
    private String status;
    private Date CreatedAt;
    private Date UpdateAt;

    public Report(int Id, int UserId, int OrderDetailId, ProductCard ProductCardId, String ProductCategoriesName, String BrandName, String status, Date CreatedAt) {
        this.Id = Id;
        this.UserId = UserId;
        this.OrderDetailId = OrderDetailId;
        this.ProductCardId = ProductCardId;
        this.ProductCategoriesName = ProductCategoriesName;
        this.BrandName = BrandName;
        this.status = status;
        this.CreatedAt = CreatedAt;
    }

    public String getProductCategoriesName() {
        return ProductCategoriesName;
    }

    public void setProductCategoriesName(String ProductCategoriesName) {
        this.ProductCategoriesName = ProductCategoriesName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(int OrderDetailId) {
        this.OrderDetailId = OrderDetailId;
    }

    public ProductCard getProductCardId() {
        return ProductCardId;
    }

    public void setProductCardId(ProductCard ProductCardId) {
        this.ProductCardId = ProductCardId;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Date getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(Date UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

    @Override
    public String toString() {
        return "Report{" + "Id=" + Id + ", UserId=" + UserId + ", OrderDetailId=" + OrderDetailId + ", ProductCardId=" + ProductCardId + ", status=" + status + ", CreatedAt=" + CreatedAt + ", UpdateAt=" + UpdateAt + '}';
    }

    
}
