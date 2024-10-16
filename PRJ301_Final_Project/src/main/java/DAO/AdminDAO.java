/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NHAT
 */
public class AdminDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public AdminDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public Admin getAdminByUandP(String username, String password) {
        String sql = "Select * from [Admin] where username=? and password=?";
        Admin ad = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                ad = new Admin();
                ad.setAdminID(rs.getInt("adminID"));
                ad.setUsername(rs.getString("username"));
                ad.setPassword(rs.getString("password"));
                ad.setFullname(rs.getString("fullname"));
                ad.setEmail(rs.getString("email"));
                ad.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
        }
        return ad;
    }

    public Admin getAdminByAdminID(int adminID) {
        String sql = "Select * from [Admin] where adminID=?";
        Admin ad = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, adminID);
            rs = pre.executeQuery();
            if (rs.next()) {
                ad = new Admin();
                ad.setAdminID(rs.getInt("adminID"));
                ad.setUsername(rs.getString("username"));
                ad.setPassword(rs.getString("password"));
                ad.setFullname(rs.getString("fullname"));
                ad.setEmail(rs.getString("email"));
                ad.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
        }
        return ad;
    }

    public Admin getAdminByU(String username) {
        String sql = "Select * from [Admin] where username=?";
        Admin ad = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, username);
            rs = pre.executeQuery();
            if (rs.next()) {
                ad = new Admin();
                ad.setAdminID(rs.getInt("adminID"));
                ad.setUsername(rs.getString("username"));
                ad.setPassword(rs.getString("password"));
                ad.setFullname(rs.getString("fullname"));
                ad.setEmail(rs.getString("email"));
                ad.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
        }
        return ad;
    }
}
