/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;

import ict.db.CheckInOutDB;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class TestDropCheck {
     public static void main(String[] args){
      String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "root";
        CheckInOutDB db = new CheckInOutDB(url, username, password);
        db.dropTable();
    } 
}
