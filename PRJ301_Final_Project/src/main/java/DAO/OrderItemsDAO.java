/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.OrderItems;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author NHAT
 */
public class OrderItemsDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public OrderItemsDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public ArrayList<OrderItems> getAllOrderItemsByOrderID(int orderID) {
        String sql = "Select * from [OrderItems] where orderID=?";
        ArrayList<OrderItems> listOrIt = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, orderID);
            rs = pre.executeQuery();
            while (rs.next()) {
                OrderItems OrIt = new OrderItems();
                OrIt.setOrderItemID(rs.getInt("orderItemID"));
                OrIt.setOrderID(rs.getInt("orderID"));
                OrIt.setProductID(rs.getString("productID"));
                ProductDAO pdao = new ProductDAO();
                Product p = pdao.getProductById(rs.getString("productID"));
                OrIt.setProductName(p.getProductName());
                OrIt.setUnitPrice(rs.getLong("unitPrice"));
                OrIt.setQuantity(rs.getInt("quantity"));
                listOrIt.add(OrIt);
            }
        } catch (Exception e) {
        }
        return listOrIt;
    }

    public int insertOrderItems(OrderItems oi) {
        String sql = "Insert Into OrderItems(orderID, productID, unitPrice, quantity)"
                + " values(?,?,?,?)";
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, oi.getOrderID());
            pre.setString(2, oi.getProductID());
            pre.setLong(3, oi.getUnitPrice());
            pre.setInt(4, oi.getQuantity());
            return pre.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
}
