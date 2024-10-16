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
public class User {
    int userID;
    String username;
    String password;
    String email;
    String phone;
    String userProvinceCity;
    String userDistrict;
    String userType;
    Date userCreateAt;

    public User() {
    }

    public User(int userID, String username, String password, String email, String phone, String userProvinceCity, String userDistrict, String userType, Date userCreateAt) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userProvinceCity = userProvinceCity;
        this.userDistrict = userDistrict;
        this.userType = userType;
        this.userCreateAt = userCreateAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserProvinceCity() {
        return userProvinceCity;
    }

    public void setUserProvinceCity(String userProvinceCity) {
        this.userProvinceCity = userProvinceCity;
    }

    public String getUserDistrict() {
        return userDistrict;
    }

    public void setUserDistrict(String userDistrict) {
        this.userDistrict = userDistrict;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getUserCreateAt() {
        return userCreateAt;
    }

    public void setUserCreateAt(Date userCreateAt) {
        this.userCreateAt = userCreateAt;
    }

    
}
