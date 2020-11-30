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
public class TestCreateUserT {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "";
        UserDB db = new UserDB(url, username, password);
        db.createUserTable();
    }
}
