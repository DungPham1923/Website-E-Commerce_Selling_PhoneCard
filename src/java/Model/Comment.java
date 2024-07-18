/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;

/**
 *
 * @author Dat
 */
public class Comment {
    private int commentID;
    private User user;
    private ProductCategories productCategories;
    private String title ;
    private String message ;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private boolean IsDelete;
    private int DeletedByID;

    public Comment() {
    }

    public Comment(int commentID, User user, ProductCategories productCategories, String title, String message, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID) {
        this.commentID = commentID;
        this.user = user;
        this.productCategories = productCategories;
        this.title = title;
        this.message = message;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedByID = DeletedByID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductCategories getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(ProductCategories productCategories) {
        this.productCategories = productCategories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "Comment{" + "commentID=" + commentID + ", user=" + user + ", productCategories=" + productCategories + ", title=" + title + ", message=" + message + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedByID=" + DeletedByID + '}';
    }
    
    
    
}
