/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author NHAT
 */
public class DBConnection {
    public static Connection conn = null;
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://LAPTOP-F90M82DI\\MSSQLSEVER:1433;databaseName=PRJ301_Final_Project;user=sa;password=123456;encrypt=true;trustServerCertificate=true";
        conn = DriverManager.getConnection(url);
        return conn;
    }
    
    public static void closeConnection() throws SQLException{
        if(!conn.isClosed()){
            conn.close();
        }
    }
}
