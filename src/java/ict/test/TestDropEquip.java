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
public class TestDropEquip {
    public static void main(String[] args){
       String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "root";
        EquipmentDB db = new EquipmentDB(url, username, password);
        db.dropEquipTable();
    } 
}