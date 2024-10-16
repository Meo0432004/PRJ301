/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NHAT
 */
public class TransactionDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public TransactionDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public int insertTransaction(Transaction trs, String transactionType) {
            String sql = "Insert into [Transaction]([adminID], [productID], [quantity], [transactionType])"
                    + " values (?,?,?,?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, trs.getAdminID());
            pre.setString(2, trs.getProductID());
            pre.setInt(3, trs.getQuantity());
            pre.setString(4, transactionType);
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }
}
