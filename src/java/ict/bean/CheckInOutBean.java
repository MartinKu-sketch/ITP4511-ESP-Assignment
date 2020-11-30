/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.time.LocalDate;

/**
 *
 * @author user
 */
public class CheckInOutBean {

    private LocalDate factory, start, end;
    private int borrow_id;

    public CheckInOutBean() {
    }

    public void setStart(java.sql.Date s) {
//        start = factory.now();
        start = s.toLocalDate();
    }

    public void setEnd(java.sql.Date s) {
//        end = start.plusDays(14);
        end = s.toLocalDate();
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        this.borrow_id = borrow_id;
    }

}
