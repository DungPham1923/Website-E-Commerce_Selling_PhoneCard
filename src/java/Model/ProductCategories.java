
package Model;

import java.sql.Date;

public class ProductCategories {
    private int Id;
    private Brand Brand;
    private String Name;
    private Double Price;
    private String Image;
    private int Quantity;
    private String Description;
    private double Discount;
    private Date DiscountFrom;
    private Date DiscountTo;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private Boolean IsDelete;
    private int DeletedBy;
    
    public ProductCategories() {}

    public ProductCategories(int Id, Brand Brand, String Name, Double Price, String Image, int Quantity, String Description, double Discount, Date DiscountFrom, Date DiscountTo, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.Id = Id;
        this.Brand = Brand;
        this.Name = Name;
        this.Price = Price;
        this.Image = Image;
        this.Quantity = Quantity;
        this.Description = Description;
        this.Discount = Discount;
        this.DiscountFrom = DiscountFrom;
        this.DiscountTo = DiscountTo;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }

    public ProductCategories(Brand Brand, String Name, Double Price, String Image, int Quantity, String Description, double Discount, Date DiscountFrom, Date DiscountTo, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.Brand = Brand;
        this.Name = Name;
        this.Price = Price;
        this.Image = Image;
        this.Quantity = Quantity;
        this.Description = Description;
        this.Discount = Discount;
        this.DiscountFrom = DiscountFrom;
        this.DiscountTo = DiscountTo;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Brand getBrand() {
        return Brand;
    }

    public void setBrand(Brand Brand) {
        this.Brand = Brand;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Double getPrice() {
        return  Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    public Date getDiscountFrom() {
        return DiscountFrom;
    }

    public void setDiscountFrom(Date DiscountFrom) {
        this.DiscountFrom = DiscountFrom;
    }

    public Date getDiscountTo() {
        return DiscountTo;
    }

    public void setDiscountTo(Date DiscountTo) {
        this.DiscountTo = DiscountTo;
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
    
    

    @Override
    public String toString() {
        return "ProductCategories{" + "Id=" + Id + ", Brand=" + Brand + ", Name=" + Name + ", Price=" + Price + ", Image=" + Image + ", Quantity=" + Quantity + ", Description=" + Description + ", Discount=" + Discount + ", DiscountFrom=" + DiscountFrom + ", DiscountTo=" + DiscountTo + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedBy=" + DeletedBy + '}';
    }

    
    
    
}
