/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import ict.bean.BorrowBean;
import ict.bean.CheckInOutBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author user
 */
public class ProcessAndCheckBrw extends SimpleTagSupport {

    private ArrayList<BorrowBean> records;
    private int limit;
    private String page;
    private String[] equName;
    private ArrayList<CheckInOutBean> dueTimeList;
    private final int RECORD_SIZE = 10;

    public void setRecords(ArrayList<BorrowBean> records) {
        this.records = records;
    }

    public void setEquName(String[] equName) {
        this.equName = equName;
    }

    public void setLimit(String l) {
        this.limit = Integer.parseInt(l);
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setDueTimeList(ArrayList<CheckInOutBean> dueTimeList) {
        this.dueTimeList = dueTimeList;
    }

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            String checkOutCol = (page.equalsIgnoreCase("Check-Out"))
                    ? "<th>Start Date</th><th>End Date</th>\n"
                    : "";

            out.println("<table id=\"usertable\">\n"
                    + "<tr>\n"
                    + "<th>Equipment ID</th>\n"
                    + "<th>Equipment Name</th>\n"
                    + "<th>Student ID</th>\n"
                    + "<th>Quantity</th>\n"
                    + checkOutCol
                    + "<th>Action</th>\n"
                    + "</tr>");

            if (limit > records.size()) {
                limit = records.size();
            }
            int startNum = (records.size() > RECORD_SIZE) ? (limit - RECORD_SIZE) : 0;
            for (int i = startNum; i < limit; i++) {
                BorrowBean ub = records.get(i);
                String dataText
                        = ub.getQuantity() + " " + equName[i]
                        + " from Student ID: " + ub.getUserId();
                if (page.equalsIgnoreCase("processRequest")) {
                    out.println("<tr>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + equName[i] + "</td>"
                            + "<td>" + ub.getUserId() + "</td>"
                            + "<td>" + ub.getQuantity() + "</td>"
                            + "<td><button id=" + ub.getBorrow_id()
                            + " data-text=\"" + dataText + "\" data-stock=\""
                            + ub.getQuantity() + "\" data-eid=\"" + ub.getEquipment_id() + "\" onclick=\"isSubmit(event)\">Accept</button>"
                            + "<button id=" + ub.getBorrow_id()
                            + " data-text=\"" + dataText + "\" data-stock=\""
                            + ub.getQuantity() + "\" data-eid=\"" + ub.getEquipment_id() + "\" onclick=\"isSubmit(event)\">Reject</button></td>"
                            + "</tr>");
                } else if (page.equalsIgnoreCase("Check-In")) {
                    out.println("<tr>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + equName[i] + "</td>"
                            + "<td>" + ub.getUserId() + "</td>"
                            + "<td>" + ub.getQuantity() + "</td>"
                            + "<td><button id=" + ub.getBorrow_id()
                            + " data-text=\"" + dataText + "\" data-stock=\""
                            + ub.getQuantity() + "\" data-eid=\"" + ub.getEquipment_id() + "\" onclick=\"isSubmit(event)\">Check-In</button>");
                } else if (page.equalsIgnoreCase("Check-Out")) {
                    CheckInOutBean cb = dueTimeList.get(i);
                    out.println("<tr>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + equName[i] + "</td>"
                            + "<td>" + ub.getUserId() + "</td>"
                            + "<td>" + ub.getQuantity() + "</td>"
                            + "<td>" + cb.getStart() + "</td>"
                            + "<td>" + cb.getEnd() + "</td>"
                            + "<td><button id=" + ub.getBorrow_id()
                            + " data-text=\"" + dataText + "\" data-stock=\""
                            + ub.getQuantity() + "\" data-eid=\"" + ub.getEquipment_id() + "\" onclick=\"isSubmit(event)\">Check-Out</button>");
                }
            }

            out.println("</table>");
        } catch (IOException e) {
            System.out.println("Error generating prime: " + e);
            e.printStackTrace();
        }
    }
}
