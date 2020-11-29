/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;
import ict.bean.UserBean;
import ict.db.UserDB;
/**
 *
 * @author a1
 */
public class TestQueryCustById {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/itp4511_db?useSSL=false";
        String username = "root";
        String password = "root";
        UserDB db = new UserDB(url, username, password);
        UserBean cb = db.queryUserByID("1");
        System.out.println("ID: "+cb.getUserId());
            System.out.println("Name: "+cb.getName());
            System.out.println("Pw: "+cb.getPw());
            System.out.println("Role: "+cb.getRole()+"\n");
    }
}
