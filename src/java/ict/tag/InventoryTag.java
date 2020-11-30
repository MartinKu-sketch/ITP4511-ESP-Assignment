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
public class InventoryTag extends SimpleTagSupport {

    private ArrayList<EquipmentBean> equips;
    private int limit;
    private String page;
    private final int RECORD_SIZE = 5;

    public void setEquips(ArrayList<EquipmentBean> equips) {
        this.equips = equips;
    }

    public void setLimit(String l) {
        this.limit = Integer.parseInt(l);
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.println("<table id=\"usertable\">\n"
                    + "<tr>\n"
                    + "<th>ID</th>\n"
                    + "<th>Name</th>\n"
                    + "<th>Status</th>\n"
                    + "<th>Stock</th>\n"
                    + "<th>Action</th>\n"
                    + "</tr>");

            limit = (limit > equips.size()) ? equips.size() : limit;

            int startNum = limit - RECORD_SIZE;
            for (int i = startNum; i < limit; i++) {
                String disabled="";
                EquipmentBean ub = equips.get(i);
//                String cbStr = "";
//                if ("true".equalsIgnoreCase(ub.getVisibility())) {
//                    cbStr = "<input type='checkbox' name='visible' checked>";
//                } else {
//                    cbStr = "<input type='checkbox' name='visible' >";
//                }
                if (page.equals("technician")) {
                    out.println("<tr>"
                            //                        + "<td>" + cbStr + "</td>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + ub.getEquipment_name() + "</td>"
                            + "<td>" + ub.getStatus() + "</td>"
                            + "<td>" + ub.getStock() + "</td>"
                            + "<td><button id=" + ub.getEquipment_id() + " onclick=\"isSubmit(event)\">Delete</button>"
                            + "<a href=\"acMgmController?action=edit&id=" + ub.getEquipment_id() + "\">Edit</a></td>"
                            + "</tr>");
                } else if (page.equals("student")) {
                    if(ub.getStock()==0)
                        disabled = "disabled";
                    out.println("<tr>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + ub.getEquipment_name() + "</td>"
                            + "<td>" + ub.getStatus() + "</td>"
                            + "<td>" + ub.getStock() + "</td>"
                            + "<td>"
                            + "<form action=\"BorrowController\" method=\"post\">Quantity:"
                            + "<input type=\"hidden\" name=\"limit\" value=\"5\"/>"
                            + "<input type=\"hidden\" name=\"id\" value='" + ub.getEquipment_id() +"'/>"
                            + "<input name=\"quantity\" style='width:40px;font-size:10pt;margin-right:5px;' type='number' min='1' max='" + ub.getStock() + "' value=\"1\"/>"
                            + "<input type=\"submit\" name=\"action\" value=\"Add\" "+disabled+"/>"
                            + "<input type=\"submit\" name=\"action\" value=\"Remove\"/></form></td>"
                            + "</tr>");
                }
            }

            out.println("</table>");
        } catch (IOException e) {
            System.out.println("Error generating prime: " + e);
            e.printStackTrace();
        }
    }
}
