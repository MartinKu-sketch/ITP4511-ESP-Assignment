/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;
import ict.db.EquipmentDB;

/**
 *
 * @author user
 */
public class TestCreateAndAddEquip {
     public static void main(String[] args){
       String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "root";
        EquipmentDB db = new EquipmentDB(url, username, password);
        db.createTable();
         db.addRecord("Wired Mouse","available","good",20,"true");
         db.addRecord("Wireless Mouse","available","good",30,"true");
         db.addRecord("Keyboard","available","good",40,"true");
         db.addRecord("Notebook","available","good",2,"true");
//         db.addRecord("Mac Book Pro","unavailable","good",3,"true");
    }
    
}
