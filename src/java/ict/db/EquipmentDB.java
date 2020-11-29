/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.EquipmentBean;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class EquipmentDB {
     private String url ="", username="", password="";
    
    public EquipmentDB(String url, String username, String password){
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
    
    public void createTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try{
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql =
                    "Create table if not exists equipment (" +
                    "equipment_id int(8) NOT NULL AUTO_INCREMENT," +
                    "equipment_name varchar(40) NOT NULL," +
                    "status varchar(20) NOT NULL," +
                    "description varchar(100) DEFAULT NULL," +
                    "stock int(8) NOT NULL," +
                    "visibility varchar(10) NOT NULL," +
                    "primary key (equipment_id))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
            System.out.println("equipment is added");
        }catch(SQLException ex){
            while(ex!= null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean addRecord( String equipment_name, String status, String description, int stock, String visibility){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            cnnct = getConnection();
            String preQueryStatement = "Insert into equipment(equipment_name,status,description,stock,visibility) values (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
//            pStmnt.setInt(1,equipment_id);
            pStmnt.setString(1,equipment_name);
            pStmnt.setString(2,status);
            pStmnt.setString(3,description);
            pStmnt.setInt(4,stock);
            pStmnt.setString(5,visibility);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(equipment_name+ " is added");
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
    
    
    public ArrayList<EquipmentBean> queryEquip(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentBean cb = null;
        ArrayList<EquipmentBean> arraylist= new ArrayList<>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from equipment";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                cb = new EquipmentBean();
                cb.setEquipment_id(rs.getInt(1));
                cb.setEquipment_name(rs.getString(2));
                cb.setStatus(rs.getString(3));
                cb.setDescription(rs.getString(4));
                cb.setStock(rs.getInt(5));
                cb.setVisibility(rs.getString(6));
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
    
    public boolean delRecord(int equipment_id){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "DELETE FROM equipment WHERE equipment_id =?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1,equipment_id);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(equipment_id+ " is deleted");
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
    
    public boolean editRecord(EquipmentBean eb){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "update equipment set equipment_name=?, status=?, description=?,visibility=?,stock=? where userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,eb.getEquipment_name());
            pStmnt.setString(2,eb.getStatus());
            pStmnt.setString(3,eb.getDescription());
            pStmnt.setString(4,eb.getVisibility());
            pStmnt.setInt(5,eb.getStock());
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println(eb.getEquipment_id()+ " is updated");
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
    
    public void dropEquipTable(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "drop table equipment";
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
    

}
