/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;
import ict.bean.UserBean;
import ict.db.UserDB;
import java.util.ArrayList;
/**
 *
 * @author a1
 */
public class TestEdit {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "";
        UserDB custDb = new UserDB(url, username, password);
        UserBean cb = new UserBean();
        cb.setUserId("1");
        cb.setName("Hugo");
        cb.setPw("23457893");
        cb.setRole("student");
        custDb.editRecord(cb);
    }
}
