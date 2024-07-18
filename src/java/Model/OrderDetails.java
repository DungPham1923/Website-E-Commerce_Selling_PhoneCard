/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quyhi
 */
public class OrderDetails {
    private int ID;
    private int OrderID;     
    private ProductCategories productCategories;
    private int ProductCategoriesID;
    private ProductCard productcard;
    private String ProductCategoriesName;
    private String BrandName;
    private int ProductCardID;
    private double ProductPrice;
    private double Discount;
    private double Amount;
    private Order order;
    
    public OrderDetails() {
    }

    public OrderDetails(int ID, int OrderID, ProductCategories productCategories, ProductCard productcard, double ProductPrice, double Discount, double Amount) {
        this.ID = ID;
        this.OrderID = OrderID;
        this.productCategories = productCategories;
        this.productcard = productcard;
        this.ProductPrice = ProductPrice;
        this.Discount = Discount;
        this.Amount = Amount;
    }

    public OrderDetails(int OrderID, int ProductCategoriesID, int ProductCardID, double ProductPrice, double Discount, double Amount) {
        this.OrderID = OrderID;
        this.ProductCategoriesID = ProductCategoriesID;
        this.ProductCardID = ProductCardID;
        this.ProductPrice = ProductPrice;
        this.Discount = Discount;
        this.Amount = Amount;
    }
    
   
    public OrderDetails(int ID, int OrderID, int ProductCategoriesID, int ProductCardID, double ProductPrice, double Discount, double Amount, Order order) {
        this.ID = ID;
        this.OrderID = OrderID;
        this.ProductCategoriesID = ProductCategoriesID;
        this.ProductCardID = ProductCardID;
        this.ProductPrice = ProductPrice;
        this.Discount = Discount;
        this.Amount = Amount;
        this.order = order;
    }
    public OrderDetails(int ID, int OrderID, int ProductCategoriesID, int ProductCardID, double ProductPrice, double Discount, double Amount) {
        this.ID = ID;
        this.OrderID = OrderID;
        this.ProductCategoriesID = ProductCategoriesID;
        this.ProductCardID = ProductCardID;
        this.ProductPrice = ProductPrice;
        this.Discount = Discount;
        this.Amount = Amount;
    }

    public OrderDetails(int OrderID, int ProductCategoriesID, String ProductCategoriesName, String BrandName, double ProductPrice, double Discount, double Amount) {
        this.OrderID = OrderID;
        this.ProductCategoriesID = ProductCategoriesID;
        this.ProductCategoriesName = ProductCategoriesName;
        this.BrandName = BrandName;
        this.ProductPrice = ProductPrice;
        this.Discount = Discount;
        this.Amount = Amount;
    }
    
    
    public ProductCategories getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(ProductCategories productCategories) {
        this.productCategories = productCategories;
    }

    public ProductCard getProductcard() {
        return productcard;
    }

    public void setProductcard(ProductCard productcard) {
        this.productcard = productcard;
    }

    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getProductCategoriesID() {
        return ProductCategoriesID;
    }

    public void setProductCategoriesID(int ProductCategoriesID) {
        this.ProductCategoriesID = ProductCategoriesID;
    }

    public int getProductCardID() {
        return ProductCardID;
    }

    public void setProductCardID(int ProductCardID) {
        this.ProductCardID = ProductCardID;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double ProductPrice) {
        this.ProductPrice = ProductPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
    
    

    @Override
    public String toString() {
        return "OrderDetails{" + "ID=" + ID + ", OrderID=" + OrderID + ", productCategories=" + productCategories + ", ProductCategoriesID=" + ProductCategoriesID + ", productcard=" + productcard + ", ProductCardID=" + ProductCardID + ", ProductPrice=" + ProductPrice + ", Discount=" + Discount + ", Amount=" + Amount + ", order=" + order + '}';
    }
    
}
