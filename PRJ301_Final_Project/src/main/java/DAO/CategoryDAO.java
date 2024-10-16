/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author NHAT
 */
public class CategoryDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public CategoryDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public ArrayList<Category> getAllCategory() {
        sql = "Select * From Category";
        ArrayList<Category> listC = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryID(rs.getString("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setCategoryDescription(rs.getString("categoryDescription"));
                listC.add(c);
            }
        } catch (Exception e) {
        }
        return listC;
    }

    public Category getCategoryByID(String ID) {
        sql = "Select * From Category where CategoryID=?";
        Category c = new Category();
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, ID);
            rs = pre.executeQuery();
            if (rs.next()) {
                c.setCategoryID(rs.getString("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setCategoryDescription(rs.getString("categoryDescription"));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public int insertCategory(Category c) {
        sql = "Insert into Category(categoryID, categoryName, categoryDescription)"
                + " Values(?,?,?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, c.getCategoryID());
            pre.setString(2, c.getCategoryName());
            pre.setString(3, c.getCategoryDescription());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public int updateCategory(Category c) {
        sql = "Update Category"
                + " Set categoryName=?, categoryDescription=?"
                + " Where categoryID=?";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, c.getCategoryName());
            pre.setString(2, c.getCategoryDescription());
            pre.setString(3, c.getCategoryID());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }
}
