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
import java.time.Month;

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
        bo.addRecord(1, "4", 2, "Accept");
        bo.addRecord(2, "4", 2, "Accept");
        bo.addRecord(3, "7", 1, "Accept");
        //ready to check out
        bo.addRecord(4, "4", 3, "Check-In");
        bo.addRecord(8, "7", 4, "Check-In");
        bo.addRecord(6, "7", 6, "Check-In");
        //ready to accept request
        bo.addRecord(7, "4", 100, "Pending");
        bo.addRecord(8, "4", 2, "Pending");
        bo.addRecord(9, "7", 1, "Pending");
        
        //past records eid= 1
        bo.addRecord(1, "4", 20, "Check-Out");
        bo.addRecord(1, "7", 20, "Check-Out");
        bo.addRecord(1, "4", 20, "Check-Out");
        
        bo.addRecord(1, "7", 17, "Check-Out");
        bo.addRecord(1, "4", 15, "Check-Out");
        bo.addRecord(1, "7", 15, "Check-Out");
        
        bo.addRecord(1, "4", 19, "Check-Out");
        bo.addRecord(1, "4", 18, "Check-Out");
        bo.addRecord(1, "4", 13, "Check-Out");
        
        bo.addRecord(1, "4", 16, "Check-Out");
        bo.addRecord(1, "4", 16, "Check-Out");
        bo.addRecord(1, "7", 6, "Check-Out");
        
        //past records eid= 2
        bo.addRecord(2, "4", 29, "Check-Out");
        bo.addRecord(2, "7", 29, "Check-Out");
        bo.addRecord(2, "4", 30, "Check-Out");
        
        bo.addRecord(2, "7", 10, "Check-Out");
        bo.addRecord(2, "4", 18, "Check-Out");
        bo.addRecord(2, "7", 23, "Check-Out");
        
        bo.addRecord(2, "4", 23, "Check-Out");
        
        bo.addRecord(2, "4", 26, "Check-Out");
        bo.addRecord(2, "4", 26, "Check-Out");
        bo.addRecord(2, "7", 28, "Check-Out");
        
        
        //check in out records
        CheckInOutDB cio = new CheckInOutDB(url, username, password);
        cio.createTable();
        cio.addRecord(4, LocalDate.now().minusDays(15), LocalDate.now().minusDays(1));
        cio.addRecord(5,LocalDate.now().minusDays(20), LocalDate.now().minusDays(6));
        cio.addRecord(6,LocalDate.now(),LocalDate.now().plusDays(14));
        
        //for eid = 1, 2019- 2020
        cio.addRecord(10,LocalDate.of(2019,Month.FEBRUARY,1),LocalDate.of(2019,Month.FEBRUARY,15));
        cio.addRecord(11,LocalDate.of(2019,Month.FEBRUARY ,1),LocalDate.of(2019,Month.FEBRUARY,15));
        cio.addRecord(12,LocalDate.of(2019,Month.MARCH,1),LocalDate.of(2019,Month.MARCH,15));
         cio.addRecord(13,LocalDate.of(2019,Month.JULY,1),LocalDate.of(2019,Month.JULY,15));
         
        cio.addRecord(14,LocalDate.of(2020,Month.JUNE ,1),LocalDate.of(2020,Month.JUNE,15));
        cio.addRecord(15,LocalDate.of(2020,Month.JUNE,1),LocalDate.of(2020,Month.JUNE,15));
         cio.addRecord(16,LocalDate.of(2020,Month.JUNE,1),LocalDate.of(2020,Month.JUNE,15));
        cio.addRecord(17,LocalDate.of(2020,Month.SEPTEMBER ,1),LocalDate.of(2020,Month.SEPTEMBER,15));
        
        cio.addRecord(18,LocalDate.of(2020,Month.SEPTEMBER,1),LocalDate.of(2020,Month.SEPTEMBER,15));
        cio.addRecord(19,LocalDate.of(2020,Month.SEPTEMBER,1),LocalDate.of(2020,Month.SEPTEMBER,15));
        cio.addRecord(20,LocalDate.of(2020,Month.NOVEMBER ,1),LocalDate.of(2020,Month.NOVEMBER,15));
        cio.addRecord(21,LocalDate.of(2020,Month.NOVEMBER,1),LocalDate.of(2020,Month.NOVEMBER,15));
        
        //for eid = 1, 2019- 2020
        cio.addRecord(22,LocalDate.of(2019,Month.APRIL,1),LocalDate.of(2019,Month.APRIL,15));
        cio.addRecord(23,LocalDate.of(2019,Month.APRIL ,1),LocalDate.of(2019,Month.APRIL,15));
        cio.addRecord(24,LocalDate.of(2019,Month.APRIL,1),LocalDate.of(2019,Month.APRIL,15));
         cio.addRecord(25,LocalDate.of(2019,Month.OCTOBER,1),LocalDate.of(2019,Month.OCTOBER,15));
         
        cio.addRecord(26,LocalDate.of(2020,Month.MAY ,1),LocalDate.of(2020,Month.MAY,15));
        cio.addRecord(27,LocalDate.of(2020,Month.MAY,1),LocalDate.of(2020,Month.MAY,15));
        cio.addRecord(28,LocalDate.of(2020,Month.MAY,1),LocalDate.of(2020,Month.MAY,15));
        cio.addRecord(29,LocalDate.of(2020,Month.MAY,1),LocalDate.of(2020,Month.MAY,15));
        
        cio.addRecord(30,LocalDate.of(2020,Month.DECEMBER ,1),LocalDate.of(2020,Month.DECEMBER,15));
        cio.addRecord(31,LocalDate.of(2020,Month.DECEMBER,1),LocalDate.of(2020,Month.DECEMBER,15));
    }
}
