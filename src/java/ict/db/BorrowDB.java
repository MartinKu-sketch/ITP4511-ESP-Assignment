/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.BorrowBean;
import ict.bean.EquipmentBean;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class BorrowDB {

    private String url = "", username = "", password = "";

    public BorrowDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    public void createTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql
                    = "Create table if not exists borrowlist ("
                    + "borrow_id int(10) NOT NULL AUTO_INCREMENT,"
                    + "equipment_id int(8) NOT NULL,"
                    + "userId varchar(10) NOT NULL,"
                    + "quantity int(3) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "primary key (borrow_id))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
            System.out.println("borrowlist is added");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addRecord(int equipment_id, String userId, int quantity, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into borrowlist(equipment_id,userId,quantity,status) values (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipment_id);
            pStmnt.setString(2, userId);
            pStmnt.setInt(3, quantity);
            pStmnt.setString(4, status);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(equipment_id + ": " + quantity + " is added");
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
        return isSuccess;
    }

    public ArrayList<BorrowBean> queryBorrow() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<BorrowBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }
    
    public BorrowBean queryBorrowByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where borrow_id = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
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
        return bb;
    }
    
    
    public ArrayList<BorrowBean> queryBIDAndQtyByEID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<BorrowBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select borrow_id, quantity from borrowlist where equipment_id = ? AND status = ? OR status = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            pStmnt.setString(2, "Check-In");
            pStmnt.setString(3, "Check-Out");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setQuantity(rs.getInt(2));
                arraylist.add(bb);
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
        return arraylist;
    }
    
    public ArrayList<BorrowBean> queryBorrowByEquipID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<BorrowBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where equipment_id = ? AND status = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            pStmnt.setString(2, "Pending");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }
    public ArrayList<BorrowBean> queryBorrowByStudentID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<BorrowBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where userId = ? AND status = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            pStmnt.setString(2, "Pending");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }
    
    public ArrayList<BorrowBean> queryBorrowByStatus(String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where status = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }
    
    public ArrayList<BorrowBean> queryBorrowByStatusAndEID(String status, int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where status = ? AND equipment_id = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            pStmnt.setInt(2, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }
    
    public ArrayList<BorrowBean> queryBorrowByStatusAndSID(String status, int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        BorrowBean bb = null;
        ArrayList<BorrowBean> arraylist = new ArrayList<>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from borrowlist where status = ? AND userId = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            pStmnt.setInt(2, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                bb = new BorrowBean();
                bb.setBorrow_id(rs.getInt(1));
                bb.setEquipment_id(rs.getInt(2));
                bb.setUserId(rs.getString(3));
                bb.setQuantity(rs.getInt(4));
                bb.setStatus(rs.getString(5));
                arraylist.add(bb);
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
        return arraylist;
    }

    public boolean delRecord(int borrow_id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM borrowlist WHERE borrow_id =?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, borrow_id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(borrow_id + " is deleted");
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
        return isSuccess;
    }

    public boolean editRecord(BorrowBean eb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "update borrowlist set equipment_id=?, userId=?, quantity=?,status=? where borrow_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, eb.getEquipment_id());
            pStmnt.setString(2, eb.getUserId());
            pStmnt.setInt(3, eb.getQuantity());
            pStmnt.setString(4, eb.getStatus());
            pStmnt.setInt(5, eb.getBorrow_id());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(eb.getBorrow_id() + " is updated");
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
        return isSuccess;
    }

    public void dropBorrowTable() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null ;

        try {
            cnnct = getConnection();
            String preQueryStatement = "drop table borrowlist";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("table is dropped");
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
    }
}
