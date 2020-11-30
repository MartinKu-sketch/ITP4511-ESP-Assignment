/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.CheckInOutBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class CheckInOutDB {

    private String url = "", username = "", password = "";

    public CheckInOutDB(String url, String username, String password) {
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
                    = "CREATE TABLE `check_in_out` (\n"
                    + "  `borrow_id` int(10) NOT NULL,\n"
                    + "  `start` date NOT NULL ,\n"
                    + "  `end` date,"
                    + "primary key (borrow_id))";
            stmnt.execute(sql);
            stmnt.close();
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

    public boolean addRecord(int borrow_id, LocalDate start, LocalDate end) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into check_in_out values (?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, borrow_id);
            pStmnt.setObject(2, start);
            pStmnt.setObject(3, end);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(borrow_id + " is added");
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

    public CheckInOutBean queryCheckByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CheckInOutBean cb = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from check_in_out where borrow_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                cb = new CheckInOutBean();
                cb.setBorrow_id(rs.getInt(1));
                cb.setStart((Date) rs.getObject(2));
                cb.setEnd((Date) rs.getObject(3));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cb;
    }

    public ArrayList<CheckInOutBean> queryCheck() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CheckInOutBean cb = null;
        ArrayList<CheckInOutBean> arraylist = new ArrayList<CheckInOutBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from check_in_out";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                cb = new CheckInOutBean();
                cb.setBorrow_id(rs.getInt(1));
                cb.setStart((Date) rs.getObject(2));
                cb.setEnd((Date) rs.getObject(3));
                arraylist.add(cb);
                System.out.println("id: " + cb.getBorrow_id());
                System.out.println("count: " +arraylist.size());
            }
            System.out.println("size: " + arraylist.size());
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return arraylist;
    }

    public boolean delRecord(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM check_in_out WHERE borrow_id =?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is deleted");
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

    public void dropTable() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "drop table check_in_out";
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
