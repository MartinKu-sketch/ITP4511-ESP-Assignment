/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class WaitingBorrowListBean implements Serializable {

    private ArrayList<WaitingBorrowBean> arraylist;
    
    public WaitingBorrowListBean() {
       arraylist = new ArrayList<>();
    }

    public void setArraylist(ArrayList<WaitingBorrowBean> arraylist) {
        this.arraylist = arraylist;
    }

    public ArrayList<WaitingBorrowBean> getArraylist() {
        return arraylist;
    }

    
}
