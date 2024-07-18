package Model;
// 

import DAL.GoogleLoginDAO;
import java.util.Date;
//
//public class GoogleLogin {
//    private int ID;
//    private User User;
//    private String Email;
//    private boolean VerifyedEmail; 
//    private String Given_name;
//    private String Family_name;
//    private String GooogleID;
//    private Date CreatedAt;
//    private Date UpdatedAt;
//    private Date DeletedAt;
//    private boolean IsDelete;
//    private int DeletedByID;
//
//    public GoogleLogin(){}
//
//    public GoogleLogin(int ID, User User, String Email, boolean VerifyedEmail, String Given_name, String Family_name, String GooogleID, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID) {
//        this.ID = ID;
//        this.User = User;
//        this.Email = Email;
//        this.VerifyedEmail = VerifyedEmail;
//        this.Given_name = Given_name;
//        this.Family_name = Family_name;
//        this.GooogleID = GooogleID;
//        this.CreatedAt = CreatedAt;
//        this.UpdatedAt = UpdatedAt;
//        this.DeletedAt = DeletedAt;
//        this.IsDelete = IsDelete;
//        this.DeletedByID = DeletedByID;
//    }
//
//    public GoogleLogin(User User, String Email, boolean VerifyedEmail, String Given_name, String Family_name, String GooogleID, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID) {
//        this.User = User;
//        this.Email = Email;
//        this.VerifyedEmail = VerifyedEmail;
//        this.Given_name = Given_name;
//        this.Family_name = Family_name;
//        this.GooogleID = GooogleID;
//        this.CreatedAt = CreatedAt;
//        this.UpdatedAt = UpdatedAt;
//        this.DeletedAt = DeletedAt;
//        this.IsDelete = IsDelete;
//        this.DeletedByID = DeletedByID;
//    }
//
//    public GoogleLogin(int ID, User User, boolean VerifyedEmail, String GooogleID, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID) {
//        this.ID = ID;
//        this.User = User;
//        this.VerifyedEmail = VerifyedEmail;
//        this.GooogleID = GooogleID;
//        this.CreatedAt = CreatedAt;
//        this.UpdatedAt = UpdatedAt;
//        this.DeletedAt = DeletedAt;
//        this.IsDelete = IsDelete;
//        this.DeletedByID = DeletedByID;
//    }
//
//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String Email) {
//        this.Email = Email;
//    }
//    
//    
//    
//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }
//
//    public User getUser() {
//        return User;
//    }
//
//    public void setUser(User User) {
//        this.User = User;
//    }
//
//    public boolean isVerifyedEmail() {
//        return VerifyedEmail;
//    }
//
//    public void setVerifyedEmail(boolean VerifyedEmail) {
//        this.VerifyedEmail = VerifyedEmail;
//    }
//
//    public String getGiven_name() {
//        return Given_name;
//    }
//
//    public void setGiven_name(String Given_name) {
//        this.Given_name = Given_name;
//    }
//
//    public String getFamily_name() {
//        return Family_name;
//    }
//
//    public void setFamily_name(String Family_name) {
//        this.Family_name = Family_name;
//    }
//
//    public String getGooogleID() {
//        return GooogleID;
//    }
//
//    public void setGooogleID(String GooogleID) {
//        this.GooogleID = GooogleID;
//    }
//
//    public Date getCreatedAt() {
//        return CreatedAt;
//    }
//
//    public void setCreatedAt(Date CreatedAt) {
//        this.CreatedAt = CreatedAt;
//    }
//
//    public Date getUpdatedAt() {
//        return UpdatedAt;
//    }
//
//    public void setUpdatedAt(Date UpdatedAt) {
//        this.UpdatedAt = UpdatedAt;
//    }
//
//    public Date getDeletedAt() {
//        return DeletedAt;
//    }
//
//    public void setDeletedAt(Date DeletedAt) {
//        this.DeletedAt = DeletedAt;
//    }
//
//    public boolean isIsDelete() {
//        return IsDelete;
//    }
//
//    public void setIsDelete(boolean IsDelete) {
//        this.IsDelete = IsDelete;
//    }
//
//    public int getDeletedByID() {
//        return DeletedByID;
//    }
//
//    public void setDeletedByID(int DeletedByID) {
//        this.DeletedByID = DeletedByID;
//    }
//
//    @Override
//    public String toString() {
//        return "GoogleLogin{" + "ID=" + ID + ", User=" + User + ", VerifyedEmail=" + VerifyedEmail + ", Given_name=" + Given_name + ", Family_name=" + Family_name + ", GooogleID=" + GooogleID + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedByID=" + DeletedByID + '}';
//    }
//
//    
//}

/**
 *
 * @author Dat
 */
public class GoogleLogin {
//DAT

    private String id;
    private String email;
    private boolean verified_email;
    private String given_name;
    private String family_name;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private boolean IsDelete;
    private int DeletedByID;
    private User user;

    public GoogleLogin() {
    }

    public GoogleLogin(String id, String email, boolean verified_email, String given_name, String family_name, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID, User user) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.given_name = given_name;
        this.family_name = family_name;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedByID = DeletedByID;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
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

    public boolean isIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getDeletedByID() {
        return DeletedByID;
    }

    public void setDeletedByID(int DeletedByID) {
        this.DeletedByID = DeletedByID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoogleLogin{" + "id=" + id + ", email=" + email + ", verified_email=" + verified_email + ", given_name=" + given_name + ", family_name=" + family_name + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedByID=" + DeletedByID + ", user=" + user + '}';
    }
    
    
}
