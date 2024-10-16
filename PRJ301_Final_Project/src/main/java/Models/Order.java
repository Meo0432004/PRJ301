/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author NHAT
 */
public class Order {

    int orderID;
    int UserID;
    String username;
    String userType;
    long totalPrice;
    String orderStatus;
    Date date;
    boolean isShipping;

    public Order() {
    }

    public Order(int orderID, int UserID, long totalPrice, String orderStatus, Date date, boolean isShipping) {
        this.orderID = orderID;
        this.UserID = UserID;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.date = date;
        this.isShipping = isShipping;
    }

    public Order(int orderID, int UserID, String username, long totalPrice, String orderStatus, Date date, boolean isShipping, String userType) {
        this.orderID = orderID;
        this.UserID = UserID;
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.date = date;
        this.isShipping = isShipping;
        this.userType = userType;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isIsShipping() {
        return isShipping;
    }

    public void setIsShipping(boolean isShipping) {
        this.isShipping = isShipping;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
