/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;

import ict.db.BorrowDB;
import ict.db.CheckInOutDB;
import ict.db.EquipmentDB;
import ict.db.UserDB;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class InitAllTable {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "root";
        UserDB db = new UserDB(url, username, password);
        //4511 db init
        db.createDB();
        url = "jdbc:mysql://localhost:3306/itp4511_db";
        db = new UserDB(url, username, password);

        //user init
        db.createUserTable();
        db.addRecord("4", "Test1", "123", "Student");
        db.addRecord("5", "Test2", "123", "Technician");
        db.addRecord("6", "Test3", "123", "Senior Technician");
        db.addRecord("7", "Test4", "123", "Student");

        //equip init
        EquipmentDB eq = new EquipmentDB(url, username, password);
        eq.createTable();
        eq.addRecord("Wired Mouse", "available", "good", 20, "true");
        eq.addRecord("Wireless Mouse", "available", "good", 30, "true");
        eq.addRecord("Keyboard", "available", "good", 40, "true");
        eq.addRecord("Notebook", "available", "good", 2, "false");
        eq.addRecord("Mac Book Pro", "unavailable", "good", 0, "true");
        eq.addRecord("ipad", "available", "good", 14, "true");
        eq.addRecord("CD", "available", "good", 2, "true");
        eq.addRecord("DVD", "available", "good", 5, "false");
        eq.addRecord("USB", "available", "good", 7, "false");
        eq.addRecord("Ryzen 3600x", "unavailable", "good", 0, "true");
        
        //borrow init
        BorrowDB bo = new BorrowDB(url, username, password);
        bo.createTable();
        //ready to check in
        bo.addRecord(1, "4", 3, "Accept");
        bo.addRecord(2, "4", 3, "Accept");
        bo.addRecord(3, "7", 3, "Accept");
        //ready to check out
        bo.addRecord(6, "4", 3, "Check-In");
        bo.addRecord(6, "7", 3, "Check-In");
        bo.addRecord(2, "7", 3, "Check-In");
        //ready to accept request
        bo.addRecord(6, "4", 3, "Pending");
        bo.addRecord(9, "4", 3, "Pending");
        bo.addRecord(9, "7", 3, "Pending");
        
        //check in out records
        CheckInOutDB cio = new CheckInOutDB(url, username, password);
        cio.createTable();
        cio.addRecord(4,LocalDate.now(),LocalDate.now().plusDays(14));
        cio.addRecord(5,LocalDate.now(),LocalDate.now().plusDays(14));
        cio.addRecord(6,LocalDate.now(),LocalDate.now().plusDays(14));
    }
}
