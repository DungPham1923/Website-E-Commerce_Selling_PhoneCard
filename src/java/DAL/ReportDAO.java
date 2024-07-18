/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Report;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ReportDAO extends DBContext{
    
    public void insertReport(int userid,int orderdetailid, int productcardid,String ProductCategoriesName, String BrandName){
        String sql = "insert into ReportProductCard(UserID,OrderDetailID,ProductCardID,ProductCategoriesName,BrandName,Status,CreatedAt) values(?,?,?,?,?,'No repli & No compensation yet',NOW());";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setInt(2, orderdetailid);
            ps.setInt(3, productcardid);
            ps.setString(4, ProductCategoriesName);
            ps.setString(5, BrandName);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public String selectReportExist(int orderdetailid){
        String sql = "SELECT * FROM ReportProductCard where OrderDetailID = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderdetailid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String exit = rs.getString("OrderDetailID");
                return exit;
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    public void updateAcceptStatusReport(String orderdetailID){
        String sql = "Update ReportProductCard set Status = 'Replied & Compensated' where OrderDetailID = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, orderdetailID);
            ps.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void updateRefuseStatusReport(String orderdetailID){
        String sql = "Update ReportProductCard set Status = 'Replied & No compensation' where OrderDetailID = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, orderdetailID);
            ps.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public List<Report> selectAllReport(){
        List<Report> list = new ArrayList<>();
        ProductCartDAO pc = new ProductCartDAO();
        String sql = "select * from ReportProductCard;";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Report(rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getInt("OrderDetailID"),
                        pc.getProductCartByID(rs.getInt("ProductCardID")),
                        rs.getString("ProductCategoriesName"),
                        rs.getString("BrandName"),
                        rs.getString("Status"),
                        rs.getDate("CreatedAt")));
            }
            return list;
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    public static void main(String[] args) {
        ReportDAO e = new ReportDAO();
        //System.out.println(e.selectReportExist(20));
        System.out.println(e.selectAllReport());
    }
}
