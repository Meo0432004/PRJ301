/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Shipping;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author NHAT
 */
public class ShippingDAO {
    
    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";
    
    public ShippingDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }
    
    public ArrayList<Shipping> getAllShipping() {
        String sql = "Select * from Shipping";
        ArrayList<Shipping> listSh = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Shipping sh = new Shipping();
                sh.setShippingID(rs.getInt("shippingID"));
                sh.setOrderID(rs.getInt("orderID"));
                sh.setUserID(rs.getInt("userID"));
                UserDAO udao = new UserDAO();
                User u = udao.getUserByUserID(rs.getInt("userID"));
                sh.setUsername(u.getUsername());
                sh.setShippingProvince(rs.getString("shippingProvinceCity"));
                sh.setShippingDistrict(rs.getString("shippingDistrict"));
                listSh.add(sh);
            }
        } catch (Exception e) {
        }
        return listSh;
    }
    
    public Shipping getAllShippingByOrderID(int orderID) {
        String sql = "Select Top 1 * from Shipping where orderID=?";
        Shipping sh = null;
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            pre.setInt(1, orderID);
            if (rs.next()) {
                sh = new Shipping();
                sh.setShippingID(rs.getInt("shippingID"));
                sh.setOrderID(rs.getInt("orderID"));
                sh.setUserID(rs.getInt("userID"));
                sh.setShippingProvince(rs.getString("shippingProvinceCity"));
                sh.setShippingDistrict(rs.getString("shippingDistrict"));
            }
        } catch (Exception e) {
        }
        return sh;
    }
    
    public int insertShipping(Shipping s) {
        String sql = "Insert into Shipping(orderID, userID, shippingProvinceCity, shippingDistrict)"
                + " Values (?,?,?,?)";
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, s.getOrderID());
            pre.setInt(2, s.getUserID());
            pre.setString(3, s.getShippingProvince());
            pre.setString(4, s.getShippingDistrict());
            return pre.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
}
