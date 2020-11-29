/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;
import java.io.Serializable;

/**
 *
 * @author saver
 */
public class UserBean implements Serializable{
    private String userId,name,pw,role;
    
    public String getPw(){
        return pw;
    }
    
    public String getUserId(){
        return userId;
    }
    
    public String getName(){
        return name;
    }
    
    public String getRole(){
        return role;
    }
    
    public void setPw(String pw){
        this.pw = pw;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setRole(String role){
        this.role = role;
    }
}
