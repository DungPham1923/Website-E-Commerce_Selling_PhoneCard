
package Model;
    
import java.util.Date;

public class AccountLogin {
    private int ID;
    private User User;
    private String UserName;
    private String Password;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Boolean IsDelete;
    private int DeletedByID;
    
    public AccountLogin(){}

    public AccountLogin(int ID, User User, String UserName, String Password, Date CreatedAt, Date UpdatedAt, Boolean IsDelete, int DeletedByID) {
        this.ID = ID;
        this.User= User;
        this.UserName = UserName;
        this.Password = Password;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.IsDelete = IsDelete;
        this.DeletedByID = DeletedByID;
    }

    public AccountLogin(User User, String UserName, String Password, Date CreatedAt, Date UpdatedAt, Boolean IsDelete, int DeletedByID) {
        this.User = User;
        this.UserName = UserName;
        this.Password = Password;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.IsDelete = IsDelete;
        this.DeletedByID = DeletedByID;
    }

    public AccountLogin(int id, int userId, String userKey, String password, String verifyEmail, Date createdAt, Date updatedAt, Date deletedAt, boolean delete, int deletedById) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public AccountLogin(int aInt, User user, String string, String string0, java.sql.Date date, java.sql.Date date0, java.sql.Date date1, boolean aBoolean, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
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

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getDeletedByID() {
        return DeletedByID;
    }

    public void setDeletedByID(int DeletedByID) {
        this.DeletedByID = DeletedByID;
    }

    @Override
    public String toString() {
        return "AccountLogin{" + "ID=" + ID + ", UserID=" + User + ", UserName=" + UserName + ", Password=" + Password + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", IsDelete=" + IsDelete + ", DeletedByID=" + DeletedByID + '}';
    }
    
    
}
