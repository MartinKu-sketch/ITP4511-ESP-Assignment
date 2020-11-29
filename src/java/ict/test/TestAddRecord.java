/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;
import ict.db.UserDB;
/**
 *
 * @author a1
 */
public class TestAddRecord {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "root";
        UserDB custDb = new UserDB(url, username, password);
        custDb.addRecord("4","Test1","123","Student");
        custDb.addRecord("5","Test2","123","Technician");
        custDb.addRecord("6","Test3","123","Senior Technician");
    }
}
