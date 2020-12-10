/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.BorrowBean;
import ict.bean.CheckInOutBean;
import ict.bean.EquipmentBean;
import ict.db.BorrowDB;
import ict.db.CheckInOutDB;
import ict.db.EquipmentDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "ReportController", urlPatterns = {"/ReportController"})
public class ReportController extends HttpServlet {

    private BorrowDB db;
    private EquipmentDB edb;
    private CheckInOutDB cdb;
    private final String DEFAULT_LIMIT = "10";

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new BorrowDB(dbUrl, dbUser, dbPassword);
        edb = new EquipmentDB(dbUrl, dbUser, dbPassword);
        cdb = new CheckInOutDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String limit = request.getParameter("limit");
        String role = request.getParameter("role");

        if ("viewAllBrw".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = db.queryBorrowByStatus("Check-In");
            ArrayList<BorrowBean> records2 = db.queryBorrowByStatus("Check-Out");
            records.addAll(records2);
            ArrayList<CheckInOutBean> dueTimeList = new ArrayList<CheckInOutBean>();
            String[] equName = new String[records.size()];
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                dueTimeList.add(cdb.queryCheckByID(records.get(i).getBorrow_id()));
            }
            for (int i = 0; i < dueTimeList.size(); i++) {
                System.out.print("viewAllBrw, dueTimeList :" + dueTimeList.get(i).getBorrow_id());
            }
            System.out.print("viewAllBrw, records :" + records.size());
            System.out.print("viewAllBrw, dueTimeList :" + dueTimeList.size());
            System.out.print("viewAllBrw, equ :" + equName.length);
            request.setAttribute("records", records);
            request.setAttribute("equName", equName);
            request.setAttribute("dueTimeList", dueTimeList);
            request.setAttribute("limit", limit);
            request.setAttribute("role", role);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/viewAllBorrowRecords.jsp");
            rd.forward(request, response);
        } else if ("viewURate".equalsIgnoreCase(action)) {
            ArrayList<EquipmentBean> records = edb.queryEquip();
            String[] equName = new String[records.size()];
            int[] equID = new int[records.size()];
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                equID[i] = records.get(i).getEquipment_id();
            }
            int Total = 0;
            int[] borrowed;
            String selectedEquip = "";
            ArrayList<BorrowBean> borrowList = null;
            String tempDuration = "Monthly";
            if (request.getParameter("duration") != null) {
                tempDuration = request.getParameter("duration");
            }

            if (request.getParameter("equipments") != null) {
                int tempID = Integer.parseInt(request.getParameter("equipments"));
                Total = edb.queryQtyByID(tempID);
                borrowList = db.queryBIDAndQtyByEID(tempID);
                int[] monthlyData = new int[12];
                //hardcode
                int[] annualData = new int[2];
                for (int i = 0; i < borrowList.size(); i++) {
                    CheckInOutBean cb = cdb.checkTimeByID( borrowList.get(i).getBorrow_id() );
                    if (tempDuration.equalsIgnoreCase("Monthly")) {
                        if(cb.getEnd().getYear() != LocalDate.now().getYear())
                            continue;
                        int month = cb.getEnd().getMonth().getValue();
                        monthlyData[month-1] += borrowList.get(i).getQuantity();
                    }else if(tempDuration.equalsIgnoreCase("Yearly")){
                        int year = cb.getEnd().getYear();
                        int index = (year == 2019)?0:1;
                        annualData[index] += borrowList.get(i).getQuantity();
                    }
                }

                borrowed = (tempDuration.equalsIgnoreCase("Monthly"))?monthlyData:annualData;
                
                selectedEquip = edb.queryEquipNameByID(tempID);
            } else {
                Total = edb.queryQtyByID(equID[0]);
                borrowList = db.queryBIDAndQtyByEID(equID[0]);
                int[] monthlyData = new int[12];
                for (int i = 0; i < borrowList.size(); i++) {
                    CheckInOutBean cb = cdb.checkTimeByID( borrowList.get(i).getBorrow_id() );
                    if (tempDuration.equalsIgnoreCase("Monthly")) {
                        if(cb.getEnd().getYear() != LocalDate.now().getYear())
                            continue;
                        int month = cb.getEnd().getMonth().getValue();
                        monthlyData[month - 1] += borrowList.get(i).getQuantity();
                    }
                }
                borrowed = monthlyData;
                selectedEquip = edb.queryEquipNameByID(equID[0]);
            }
            
            
            String[] resultArray = new String[borrowed.length];
            for(int i = 0; i < borrowed.length; i++){
                double percent = 0;
//                double percent = borrowed[i] / (double)Total * 100;
if (tempDuration.equalsIgnoreCase("Monthly")) {
                percent = (borrowed[i] / (double)Total) / 30 * 100;
}else{
     percent = (borrowed[i] / (double)Total) / 365 *  100;
}
                resultArray[i] = String.valueOf(percent);
                System.out.print(i+" result="+resultArray[i]);
            }

            request.setAttribute("equName", equName);
            request.setAttribute("equID", equID);
            request.setAttribute("Total", String.valueOf(Total));
            request.setAttribute("borrowed", resultArray);
            request.setAttribute("selectedEquip", selectedEquip);
            request.setAttribute("duration", tempDuration);
            request.setAttribute("role", role);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/URate.jsp");
            rd.forward(request, response);
        } else if ("viewOverdue".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = db.queryBorrowByStatus("Check-In");
            ArrayList<BorrowBean> filteredRecords = new ArrayList<BorrowBean>();
            ArrayList<CheckInOutBean> dueTimeList = new ArrayList<CheckInOutBean>();
            for (int i = 0; i < records.size(); i++) {
                boolean isOverdue = cdb.queryIsOverdue(records.get(i).getBorrow_id());
                if (isOverdue) {
                    filteredRecords.add(records.get(i));
                }
                System.out.print("isOverdue :" + isOverdue);
            }
            String[] equName = new String[filteredRecords.size()];
            for (int i = 0; i < filteredRecords.size(); i++) {
                equName[i] = edb.queryEquipNameByID(filteredRecords.get(i).getEquipment_id());
                dueTimeList.add(cdb.queryCheckByID(filteredRecords.get(i).getBorrow_id()));
            }
            
            request.setAttribute("records", filteredRecords);
            request.setAttribute("equName", equName);
            request.setAttribute("dueTimeList", dueTimeList);
            request.setAttribute("limit", limit);
            request.setAttribute("role", role);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/overdue.jsp");
            rd.forward(request, response);
        } else if ("searchOverdue".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = null;
            ArrayList<BorrowBean> filteredRecords = new ArrayList<BorrowBean>();
            ArrayList<CheckInOutBean> dueTimeList = new ArrayList<CheckInOutBean>();
            String searchtype = request.getParameter("searchtype");
            if (request.getParameter("searchword").equalsIgnoreCase("")) {
                response.sendRedirect("ReportController?action=viewOverdue&limit=10&role=" + role);

                return;
            }
            int searchword = Integer.parseInt(request.getParameter("searchword"));
            String status = "Check-In";

            //get borrow by equip id or student id
            if (searchtype.equals("eid")) {
                records = db.queryBorrowByStatusAndEID(status, searchword);
            } else if (searchtype.equals("sid")) {
                records = db.queryBorrowByStatusAndSID(status, searchword);
            }

            for (int i = 0; i < records.size(); i++) {
                boolean isOverdue = cdb.queryIsOverdue(records.get(i).getBorrow_id());
                if (isOverdue) {
                    filteredRecords.add(records.get(i));
                }
                System.out.print("isOverdue :" + isOverdue);
            }
            String[] equName = new String[filteredRecords.size()];
            for (int i = 0; i < filteredRecords.size(); i++) {
                equName[i] = edb.queryEquipNameByID(filteredRecords.get(i).getEquipment_id());
                dueTimeList.add(cdb.queryCheckByID(filteredRecords.get(i).getBorrow_id()));
            }
            System.out.print("records :" + filteredRecords.size());
            System.out.print("dueTimeList :" + dueTimeList.size());
            System.out.print("equ :" + equName.length);
            request.setAttribute("records", filteredRecords);
            request.setAttribute("equName", equName);
            request.setAttribute("dueTimeList", dueTimeList);
            request.setAttribute("limit", DEFAULT_LIMIT);
            request.setAttribute("role", role);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/overdue.jsp");
            rd.forward(request, response);
        } else if ("searchBorrow".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = null;
            ArrayList<BorrowBean> records2 = null;
            ArrayList<CheckInOutBean> dueTimeList = new ArrayList<CheckInOutBean>();
            String searchtype = request.getParameter("searchtype");
            if (request.getParameter("searchword").equalsIgnoreCase("")) {
                response.sendRedirect("ReportController?action=viewAllBrw&limit=10&role=" + role);
                return;
            }
            int searchword = Integer.parseInt(request.getParameter("searchword"));

            //get borrow by student id
            records = db.queryBorrowByStatusAndSID("Check-In", searchword);
            records2 = db.queryBorrowByStatusAndSID("Check-Out", searchword);
            records.addAll(records2);

            String[] equName = new String[records.size()];
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                dueTimeList.add(cdb.queryCheckByID(records.get(i).getBorrow_id()));
            }
            System.out.print("records :" + records.size());
            System.out.print("dueTimeList :" + dueTimeList.size());
            System.out.print("equ :" + equName.length);
            request.setAttribute("records", records);
            request.setAttribute("equName", equName);
            request.setAttribute("dueTimeList", dueTimeList);
            request.setAttribute("limit", DEFAULT_LIMIT);
            request.setAttribute("role", role);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/viewAllBorrowRecords.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
