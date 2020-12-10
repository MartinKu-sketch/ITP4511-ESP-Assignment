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
public class ReportTag extends SimpleTagSupport {

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
            if (limit > records.size()) {
                limit = records.size();
            }
            int startNum = (records.size() > RECORD_SIZE) ? (limit - RECORD_SIZE) : 0;
            if (page.equalsIgnoreCase("viewOverdue")) {
                out.println("<table id=\"usertable\">\n"
                        + "<tr>\n"
                        + "<th>Equipment ID</th>\n"
                        + "<th>Equipment Name</th>\n"
                        + "<th>Quantity</th>\n"
                        + "<th>Borrow ID</th>\n"
                        + "<th>Student ID</th>"
                        + "<th>Start Date</th><th>End Date</th>\n"
                        + "</tr>");

                for (int i = startNum; i < limit; i++) {
                    BorrowBean ub = records.get(i);
                    CheckInOutBean cb = dueTimeList.get(i);

                    out.println("<tr>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + equName[i] + "</td>"
                            + "<td>" + ub.getQuantity() + "</td>"
                            + "<td>" + ub.getBorrow_id() + "</td>"
                            + "<td>" + ub.getUserId() + "</td>"
                            + "<td>" + cb.getStart() + "</td>"
                            + "<td>" + cb.getEnd() + "</td></tr>");

                }
                out.println("</table>");
            }else if(page.equalsIgnoreCase("viewAllBrw")){
                 out.println("<table id=\"usertable\">\n"
                        + "<tr>\n"
                        + "<th>Borrow ID</th>\n"
                        + "<th>Student ID</th>"
                        + "<th>Start Date</th><th>End Date</th>\n"
                        + "<th>Equipment ID</th>\n"
                        + "<th>Equipment Name</th>\n"
                        + "<th>Quantity</th>\n"
                        + "</tr>");

                for (int i = startNum; i < limit; i++) {
                    BorrowBean ub = records.get(i);
                    CheckInOutBean cb = dueTimeList.get(i);

                    out.println("<tr>"
                            + "<td>" + ub.getBorrow_id() + "</td>"
                            + "<td>" + ub.getUserId() + "</td>"
                            + "<td>" + cb.getStart() + "</td>"
                            + "<td>" + cb.getEnd() + "</td>"
                            + "<td>" + ub.getEquipment_id() + "</td>"
                            + "<td>" + equName[i] + "</td>"
                            + "<td>" + ub.getQuantity() + "</td>"
                                    + "</tr>");

                }
                out.println("</table>");
            }
        } catch (IOException e) {
            System.out.println("Error generating prime: " + e);
            e.printStackTrace();
        }
    }
}
