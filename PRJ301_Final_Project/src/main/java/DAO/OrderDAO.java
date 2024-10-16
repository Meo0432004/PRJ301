/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Order;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHAT
 */
public class OrderDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public OrderDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public ArrayList<Order> getAllOrder() {
        String sql = "Select * from [Order]";
        ArrayList<Order> listO = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderID(rs.getInt("orderID"));
                o.setUserID(rs.getInt("userID"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setTotalPrice(rs.getLong("totalPrice"));
                o.setDate(rs.getDate("date"));
                o.setIsShipping(rs.getBoolean("isShipping"));
                UserDAO udao = new UserDAO();
                User u = udao.getUserByUserID(rs.getInt("userID"));
                o.setUsername(u.getUsername());
                o.setUserType(u.getUserType());
                listO.add(o);
            }
        } catch (Exception e) {
        }
        return listO;
    }
    
    public ArrayList<Order> getAllOrderByUserID(int userID) {
        String sql = "Select * from [Order] where userID=?";
        ArrayList<Order> listO = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userID);
            rs = pre.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderID(rs.getInt("orderID"));
                o.setUserID(rs.getInt("userID"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setTotalPrice(rs.getLong("totalPrice"));
                o.setDate(rs.getDate("date"));
                o.setIsShipping(rs.getBoolean("isShipping"));
                UserDAO udao = new UserDAO();
                User u = udao.getUserByUserID(rs.getInt("userID"));
                o.setUsername(u.getUsername());
                o.setUserType(u.getUserType());
                listO.add(o);
            }
        } catch (Exception e) {
        }
        return listO;
    }

    public int insertOrder(Order o) {
        String sql = "Insert into [Order](userID, totalPrice, isShipping) "
                + " Values(?,?,?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, o.getUserID());
            pre.setLong(2, o.getTotalPrice());
            pre.setBoolean(3, o.isIsShipping());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public Order getOrderLasted() {
        String sql = "Select Top 1 * From [Order] Order by [OrderID] Desc";
        Order o = null;
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                o = new Order();
                o.setOrderID(rs.getInt("OrderID"));
                o.setUserID(rs.getInt("userID"));
                o.setTotalPrice(rs.getLong("totalPrice"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setDate(rs.getDate("date"));
                o.setIsShipping(rs.getBoolean("isShipping"));
            }
        } catch (Exception e) {
        }
        return o;
    }
}
