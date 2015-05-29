package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    
    public final static String host = "127.0.0.1";
    public final static String db = "tsogtbayar_db";
    public final static String user = "root";
    public final static String pass = "acep123";
    
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DATABASE_URL = "jdbc:mysql://"+host+"/"+db+"?useUnicode=yes&characterEncoding=UTF-8";
    
    private static Connection connect(){
        Connection connection = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL,user,pass);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static ResultSet select(String query) {
        ResultSet result = null;
        try {
            PreparedStatement stmnt = connect().prepareStatement(query);
            result = stmnt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean insert(String query) {
        try {
            PreparedStatement stmnt = connect().prepareStatement(query);
            return stmnt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static boolean delete(String query) {
        try {
            PreparedStatement stmnt = connect().prepareStatement(query);
            return stmnt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static boolean update(String query) {
        try {
            PreparedStatement stmnt = connect().prepareStatement(query);
            return stmnt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

