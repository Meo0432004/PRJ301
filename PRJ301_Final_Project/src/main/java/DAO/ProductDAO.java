/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Category;
import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHAT
 */
public class ProductDAO {

    private Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pre;
    String sql = "";

    public ProductDAO() {
        try {
            conn = DB.DBConnection.getConnection();
            if (conn == null) {
                throw new Exception("Database connection failed.");
            }
        } catch (Exception e) {
            System.out.println("Error in ProductDAO constructor: " + e.getMessage());
        }
    }

    public Product getNewestProduct() {
        sql = "Select Top 1 * from Product where productQuantity > 0 Order by productID desc";
        Product p = new Product();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
            }
        } catch (Exception e) {
        }
        return p;
    }

    public List<Product> getAllProduct(String type) {
        if (type.equals("user")) {
            sql = "Select * From Product where productQuantity > 0";
        } else if (type.equals("admin")) {
            sql = "Select * From Product";
        }
        List<Product> listP = new ArrayList<>();
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
                CategoryDAO cdao = new CategoryDAO();
                Category c = cdao.getCategoryByID(rs.getString("categoryID"));
                p.setCategoryName(c.getCategoryName());
                listP.add(p);
            }
        } catch (Exception e) {
        }
        return listP;
    }

    public ArrayList<Product> getAllProductsByName(String pName) {
        ArrayList<Product> listAllProducts = new ArrayList<>();
        String query = "select * from product where productName like ?";
        try {
            pre = conn.prepareStatement(query);
            pre.setString(1, "%" + pName + "%");
            rs = pre.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
                CategoryDAO cdao = new CategoryDAO();
                Category c = cdao.getCategoryByID(rs.getString("categoryID"));
                p.setCategoryName(c.getCategoryName());

                listAllProducts.add(p);
            }
        } catch (Exception e) {
        }
        return listAllProducts;
    }

    public Product getProductById(String pId) {
        String query = "select * from product where productID = ?";
        try {
            pre = conn.prepareStatement(query);
            pre.setString(1, pId);
            rs = pre.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
                CategoryDAO cdao = new CategoryDAO();
                Category c = cdao.getCategoryByID(rs.getString("categoryID"));
                p.setCategoryName(c.getCategoryName());

                return p;
            }
        } catch (Exception e) {
        }

        return null;
    }

    public ArrayList<Product> getAllProductsByCategoryID(String cId, String type) {
        ArrayList<Product> listAllProducts = new ArrayList<>();
        String query = "select * from product where categoryID = ?";
        if (type.equals("user")) {
            query += " And productQuantity>0";
        }
        try {

            pre = conn.prepareStatement(query);
            pre.setString(1, cId);
            rs = pre.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
                CategoryDAO cdao = new CategoryDAO();
                Category c = cdao.getCategoryByID(rs.getString("categoryID"));
                p.setCategoryName(c.getCategoryName());

                listAllProducts.add(p);
            }
        } catch (Exception e) {
        }
        return listAllProducts;
    }

    public Product getNewestProductByCategoryID(String cId) {
        String query = "SELECT TOP 1 * FROM product WHERE categoryID = ? ORDER BY productID DESC";
        try {
            pre = conn.prepareStatement(query);
            pre.setString(1, cId);
            rs = pre.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getString("productID"));
                p.setCategoryID(rs.getString("categoryID"));
                p.setProductName(rs.getString("productName"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setProductPrice(rs.getLong("productPrice"));
                p.setProductQuantity(rs.getInt("productQuantity"));
                p.setProductImage(rs.getString("productImage"));
                CategoryDAO cdao = new CategoryDAO();
                Category c = cdao.getCategoryByID(rs.getString("categoryID"));
                p.setCategoryName(c.getCategoryName());

                return p;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int insertProduct(Product p) {
        String sql = "Insert Into product([productID], [categoryID], [productName], [productDescription], [productPrice], [productQuantity], [productImage])"
                + " Values(?,?,?,?,?,?,?)";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.getProductID());
            pre.setString(2, p.getCategoryID());
            pre.setString(3, p.getProductName());
            pre.setString(4, p.getProductDescription());
            pre.setLong(5, p.getProductPrice());
            pre.setInt(6, p.getProductQuantity());
            pre.setString(7, p.getProductImage());

            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public int updateProduct(Product p) {
        String sql = "Update Product "
                + "Set [categoryID]=?, [productName]=?, [productDescription]=?, [productPrice]=?, [productQuantity]=?, [productImage]=?"
                + " Where [productID]=?";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.getCategoryID());
            pre.setString(2, p.getProductName());
            pre.setString(3, p.getProductDescription());
            pre.setLong(4, p.getProductPrice());
            pre.setInt(5, p.getProductQuantity());
            pre.setString(6, p.getProductImage());
            pre.setString(7, p.getProductID());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }

    public int deleteProduct(Product p) {
        String sql = "Delete from Product"
                + " Where productID=?";
        int count = 0;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, p.getProductID());
            count = pre.executeUpdate();
        } catch (Exception e) {
        }
        return count;
    }
}
