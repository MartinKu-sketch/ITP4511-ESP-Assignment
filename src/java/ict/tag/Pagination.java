/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author user
 */
public class Pagination extends SimpleTagSupport {

    private int fullsize;
    private int limit;
    private String page, role;
    private int RECORD_SIZE;

    public void setFullsize(int s) {
        this.fullsize = s;
    }

    public void setLimit(String s) {
        System.out.print(s);
        this.limit = Integer.parseInt(s);
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            
            RECORD_SIZE =(page.equals("studentlist") || page.equals("list") )?5:10;
            int currentPage = limit / RECORD_SIZE;
            int totalPage = (int) Math.ceil((double) fullsize / RECORD_SIZE);
            System.out.print(currentPage);
            System.out.print(totalPage);
            String temp = "";

            if (page.equals("studentlist")) {
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive paginationbtn\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"paginationbtn\" href=\"BorrowController?action=" + page + "&limit=" + (limit - 5) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive paginationbtn\">Next</a>";
                } else {
                    temp += "<a class=\"paginationbtn\" href=\"BorrowController?action=" + page + "&limit=" + (limit + 5) + "\">Next</a>";
                }
            } else if (page.equals("list")) {
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"InventoryController?action=list&limit=" + (limit - 5) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"InventoryController?action=list&limit=" + (limit + 5) + "\">Next</a>";
                }
            }else if (page.equalsIgnoreCase("processRequest")) {
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"BorrowController?action=viewRequest&limit=" + (limit - 10) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"BorrowController?action=viewRequest&limit=" + (limit + 10) + "\">Next</a>";
                }
            }else if (page.equalsIgnoreCase("Check-In")) {//view check in records
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"CheckInOutController?action=viewCheckIn&limit=" + (limit - 10) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"CheckInOutController?action=viewCheckIn&limit=" + (limit + 10) + "\">Next</a>";
                }
            }else if (page.equalsIgnoreCase("Check-Out")) {//view check out records
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"CheckInOutController?action=viewCheckOut&limit=" + (limit - 10) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"CheckInOutController?action=viewCheckOut&limit=" + (limit + 10) + "\">Next</a>";
                }
            }else if (page.equalsIgnoreCase("viewOverdue")) {//view check out records
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"ReportController?action=viewOverdue&role="+role+"limit=" + (limit - 10) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"ReportController?action=viewOverdue&role="+role+"&limit=" + (limit + 10) + "\">Next</a>";
                }
            }else if (page.equalsIgnoreCase("viewAllBrw")) {//view check out records
                if (currentPage == 1) {
                    temp += "<div class=\"btn_pos\">"
                            + "<a class=\"unactive\">Prev</a>";

                } else {
                    temp += "<div class=\"btn_pos\">"
                            + "<a href=\"ReportController?action=viewAllBrw&role="+role+"limit=" + (limit - 10) + "\">Prev</a>";
                }

                if (totalPage <= 1 || totalPage == currentPage) {
                    temp += "<a class=\"unactive\">Next</a>";
                } else {
                    temp += "<a href=\"ReportController?action=viewAllBrw&role="+role+"&limit=" + (limit + 10) + "\">Next</a>";
                }
            }

            out.println(temp + "</div>");

        } catch (IOException e) {
            System.out.println("Error generating : " + e);
            e.printStackTrace();
        }
    }
}
