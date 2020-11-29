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
        custDb.addRecord("1","Peter","12345678","student");
        custDb.addRecord("2","Nancy","12345678","tech");
        custDb.addRecord("3","Larry","12345678","stech");
    }
}
