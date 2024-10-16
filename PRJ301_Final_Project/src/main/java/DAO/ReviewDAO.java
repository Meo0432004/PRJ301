/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Review;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author NHAT
 */
public class ReviewDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public ReviewDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public int insertReview(Review r) {
        String sql = "Insert Into Review([productID], [customerID], [reviewText])"
                + " Values(?, ?, ?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, r.getProductID());
            pre.setInt(2, r.getCustomerID());
            pre.setString(3, r.getReviewText());

            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public ArrayList<Review> getReviewByProductID(String productID) {
        String sql = "Select * from Review where productID = ?";
        ArrayList<Review> listR = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, productID);
            rs = pre.executeQuery();
            while (rs.next()) {
                Review r = new Review();
                r.setProductID(rs.getString("productID"));
                r.setCustomerID(rs.getInt("customerID"));
                r.setReviewText(rs.getString("reviewText"));
                UserDAO udao = new UserDAO();
                User u = udao.getUserByUserID(rs.getInt("customerID"));
                r.setCustomerUsername(u.getUsername());
                listR.add(r);
            }
        } catch (Exception e) {
        }
        return listR;
    }
}
