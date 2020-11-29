/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.UserBean;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author a1
 */
public class UserDB {
    private String url ="", username="", password="";
    
    public UserDB(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public Connection getConnection() throws SQLException, IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url,username,password);
    }
    
    public void createUserTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try{
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql =
                    "Create table if not exists users (" +
                    "userId varchar(10) not null," +
                    "name varchar(25) not null," +
                    "password varchar(25) not null," +
                    "role varchar(25) not null," +
                    "primary key (userId))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex!= null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean addRecord(String userid, String name, String pw, String role){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            cnnct = getConnection();
            String preQueryStatement = "Insert into users values (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,userid);
            pStmnt.setString(2,name);
            pStmnt.setString(3,pw);
            pStmnt.setString(4,role);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(name+ " is added");
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    public UserBean queryUserByID(String id){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean cb = null;
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if(rs.next()){
                cb = new UserBean();
                cb.setUserId(rs.getString(1));
                cb.setName(rs.getString(2));
                cb.setPw(rs.getString(3));
                cb.setRole(rs.getString(4));
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return cb;
    }
    
    public ArrayList<UserBean> queryCustByName(String name){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean cb = null;
        ArrayList<UserBean> arraylist= new ArrayList<>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where name like ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,"%"+name+"%");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                cb = new UserBean();
                cb.setUserId(rs.getString(1));
                cb.setName(rs.getString(2));
                cb.setPw(rs.getString(3));
                cb.setRole(rs.getString(4));
                arraylist.add(cb);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    public ArrayList<UserBean> queryCustByTel(String tel){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean cb = null;
        ArrayList<UserBean> arraylist= new ArrayList<>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where tel like ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,"%"+tel+"%");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                cb = new UserBean();
                cb.setUserId(rs.getString(1));
                cb.setName(rs.getString(2));
                cb.setPw(rs.getString(3));
                cb.setRole(rs.getString(4));
                arraylist.add(cb);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    public ArrayList<UserBean> queryCust(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean cb = null;
        ArrayList<UserBean> arraylist= new ArrayList<>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                cb = new UserBean();
                cb.setUserId(rs.getString(1));
                cb.setName(rs.getString(2));
                cb.setPw(rs.getString(3));
                cb.setRole(rs.getString(4));
                arraylist.add(cb);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    public boolean delRecord(String userId){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "DELETE FROM users WHERE userId =?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,userId);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(userId+ " is deleted");
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    public boolean editRecord(UserBean ub){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "update users set name=?, password=?, role=? where userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,ub.getName());
            pStmnt.setString(2,ub.getPw());
            pStmnt.setString(3,ub.getRole());
            pStmnt.setString(4,ub.getUserId());
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(ub.getUserId()+ " is updated");
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    public void dropCustTable(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "drop table customer";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("table is dropped");
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void dropDB(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "drop database itp4511_db";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("database is dropped");
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void createDB(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "Create database itp4511_db";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("database is created");
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public String isValidUser(String userId, String pw) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String userRole = "";
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM users WHERE userId = ? and password = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userId);
            pStmnt.setString(2, pw);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                userRole = rs.getString(4);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userRole;
    }
}
