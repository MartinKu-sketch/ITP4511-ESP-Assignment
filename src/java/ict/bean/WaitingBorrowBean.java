/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class WaitingBorrowBean implements Serializable {

    private int equipment_id, quantity;
    private String equipment_name;
    
    public WaitingBorrowBean() {
       
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    
}
