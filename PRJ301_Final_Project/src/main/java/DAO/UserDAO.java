/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NHAT
 */
public class UserDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public UserDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public User getUserByU(String username) {
        String sql = "Select * from [User] where username=?";
        User u = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, username);
            rs = pre.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUserID(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPhone(rs.getString(5));
                u.setUserProvinceCity(rs.getString(6));
                u.setUserDistrict(rs.getString(7));
                u.setUserType(rs.getString(8));
                u.setUserCreateAt(rs.getDate(9));
            }
        } catch (Exception e) {
        }
        return u;
    }

    public User getUserByUserID(int userID) {
        String sql = "Select * from [User] where [userID] = ?";
        User u = null;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userID);
            rs = pre.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUserID(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPhone(rs.getString(5));
                u.setUserProvinceCity(rs.getString(6));
                u.setUserDistrict(rs.getString(7));
                u.setUserType(rs.getString(8));
                u.setUserCreateAt(rs.getDate(9));
            }
        } catch (Exception e) {
        }
        return u;
    }

    public User getUserByUAndP(String username, String password) {
        User u = null;
        String sql = "Select * from [User] where [username]=? and [password]=?";
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUserID(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPhone(rs.getString(5));
                u.setUserProvinceCity(rs.getString(6));
                u.setUserDistrict(rs.getString(7));
                u.setUserType(rs.getString(8));
                u.setUserCreateAt(rs.getDate(9));
            }
        } catch (Exception e) {
        }
        return u;
    }

    public int InsertUser(User u, String userType) {
        String sql = "Insert into [User]([username], [password], email, phone, userProvinceCity, userDistrict, userType) "
                + " Values (?, ?, ?, ?, ?, ?, ?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, u.getUsername());
            pre.setString(2, u.getPassword());
            pre.setString(3, u.getEmail());
            pre.setString(4, u.getPhone());
            pre.setString(5, u.getUserProvinceCity());
            pre.setString(6, u.getUserDistrict());
            pre.setString(7, userType);
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public int UpdateUser(User u) {
        String sql = "Update [User]"
                + " Set [username]=?,[password]=?, email=?, phone=?, userProvinceCity=?, userDistrict=? "
                + " Where [userID]=?";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, u.getUsername());
            pre.setString(2, u.getPassword());
            pre.setString(3, u.getEmail());
            pre.setString(4, u.getPhone());
            pre.setString(5, u.getUserProvinceCity());
            pre.setString(6, u.getUserDistrict());
            pre.setInt(7, u.getUserID());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

}
