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
public class CurrentAdminDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public CurrentAdminDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public Admin getCurrentAdmin() {
        String sql = "select * from CurrentAdmin";
        Admin ad = null;
        AdminDAO addao = new AdminDAO();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                ad = new Admin();
                ad = addao.getAdminByAdminID(rs.getInt("adminID"));
            }
        } catch (Exception e) {
        }
        return ad;
    }

    public int updateCurrentAdmin(Admin ad) {
        String sql = "Update CurrentAdmin"
                + " Set adminID = ?";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setInt(1, ad.getAdminID());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }
}
