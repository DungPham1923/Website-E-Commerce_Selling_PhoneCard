
package Model;

import java.util.Date;

public class CartItem {
    private int ID;
    private int CartID;
    private ProductCategories ProductCategories;
    private int ProductCategoriesID;
    private int Quantity;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private Boolean IsDelete;
    private int DeletedBy;
    private Cart cart;

    public CartItem() {
    }

    public CartItem(int ID, int CartID, ProductCategories ProductCategories, int Quantity) {
        this.ID = ID;
        this.CartID = CartID;
        this.ProductCategories = ProductCategories;
        this.Quantity = Quantity;
    }
    
    public CartItem(int ID, Cart cart, ProductCategories ProductCategories, int Quantity, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.ID = ID;
        this.cart = cart;
        this.ProductCategories = ProductCategories;
        this.Quantity = Quantity;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }
    
    public CartItem(int ID, Cart cart, int ProductCategoriesID, int Quantity, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.ID = ID;
        this.cart = cart;
        this.ProductCategoriesID = ProductCategoriesID;
        this.Quantity = Quantity;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }
    
    public CartItem(int ID, int CartID, int ProductCategoriesID, int Quantity) {
        this.ID = ID;
        this.CartID = CartID;
        this.ProductCategoriesID = ProductCategoriesID;
        this.Quantity = Quantity;
    }

    public int getProductCategoriesID() {
        return ProductCategoriesID;
    }

    public void setProductCategoriesID(int ProductCategoriesID) {
        this.ProductCategoriesID = ProductCategoriesID;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ProductCategories getProductCategories() {
        return ProductCategories;
    }

    public void setProductCategories(ProductCategories productCategories) {
        this.ProductCategories = productCategories;
    }
    

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
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
    public int getCartID() {
        return CartID;
    }

    public void setCartID(int CartID) {
        this.CartID = CartID;
    }
}