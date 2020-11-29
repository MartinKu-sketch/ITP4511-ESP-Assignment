/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.util.*;
import ict.bean.EquipmentBean;

/**
 *
 * @author user
 */
public class InventoryPagination extends SimpleTagSupport {

    private int fullsize;
    private int limit;
    private final int RECORD_SIZE = 5;

    public void setFullsize(int s) {
        this.fullsize = s;
    }

    public void setLimit(String s) {
        this.limit = Integer.parseInt(s);
    }

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            int currentPage = limit / RECORD_SIZE;
            int totalPage = (int) Math.ceil((double) fullsize / RECORD_SIZE);
            System.out.print(currentPage);
            System.out.print(totalPage);
            String temp = "";
            if (currentPage == 1) {
               temp += "<div class=\"btn_pos\">"
                        + "<a class=\"unactive\">Prev</a>";

            } else {
                 temp += "<div class=\"btn_pos\">"
                        + "<a href=\"InventoryController?action=list&limit=" + (limit - 5) + "\">Prev</a>";
            }

            if (totalPage == 1 ||totalPage ==  currentPage) {
                 temp += "<a class=\"unactive\">Next</a>";
            } else {
                 temp += "<a href=\"InventoryController?action=list&limit=" + (limit + 5) + "\">Next</a>";
            }
            
            out.println(temp + "</div>");
            System.out.print(temp);

        } catch (IOException e) {
            System.out.println("Error generating : " + e);
            e.printStackTrace();
        }
    }
}
