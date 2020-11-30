/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;

import ict.db.BorrowDB;

/**
 *
 * @author user
 */
public class TestAddBorrow {
     public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/itp4511_db?useSSL=false";
        String username = "root";
        String password = "root";
        BorrowDB db = new BorrowDB(url, username, password);
        db.createTable();
        db.addRecord(1, "4", 3, "Accept");
        db.addRecord(1, "4", 3, "Accept");
        db.addRecord(1, "3", 3, "Accept");
        db.addRecord(1, "6", 3, "Check-In");
        db.addRecord(1, "7", 3, "Check-In");
        db.addRecord(1, "7", 3, "Check-In");
    }
}
