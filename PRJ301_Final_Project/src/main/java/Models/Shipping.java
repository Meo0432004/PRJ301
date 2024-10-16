/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author NHAT
 */
public class Shipping {

    int shippingID;
    int OrderID;
    int userID;
    String username;
    String shippingProvince;
    String shippingDistrict;

    public Shipping() {
    }

    public Shipping(int shippingID, int OrderID, int userID, String username, String shippingProvince, String shippingDistrict) {
        this.shippingID = shippingID;
        this.OrderID = OrderID;
        this.userID = userID;
        this.username = username;
        this.shippingProvince = shippingProvince;
        this.shippingDistrict = shippingDistrict;
    }

    public int getShippingID() {
        return shippingID;
    }

    public void setShippingID(int shippingID) {
        this.shippingID = shippingID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShippingProvince() {
        return shippingProvince;
    }

    public void setShippingProvince(String shippingProvince) {
        this.shippingProvince = shippingProvince;
    }

    public String getShippingDistrict() {
        return shippingDistrict;
    }

    public void setShippingDistrict(String shippingDistrict) {
        this.shippingDistrict = shippingDistrict;
    }

    

}
