
package Model;
import java.util.Date;

public class Role {
    private int ID;
    private String Name;
    private String Description;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private boolean IsDeleted;
    
    public Role(){}

    public Role(int ID, String Name, String Description, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDeleted) {
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDeleted = IsDeleted;
    }

    public Role(String Name, String Description, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDeleted) {
        this.Name = Name;
        this.Description = Description;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDeleted = IsDeleted;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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

    public void setDeleteAt(Date DeletedAt) {
        this.DeletedAt = DeletedAt;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDelete(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }
    
    
}
