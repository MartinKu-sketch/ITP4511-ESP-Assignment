/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.BorrowBean;
import ict.bean.CheckInOutBean;
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
@WebServlet(name = "CheckInOutController", urlPatterns = {"/CheckInOutController"})
public class CheckInOutController extends HttpServlet {

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

        if ("viewCheckIn".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = db.queryBorrowByStatus("Accept");
            String[] equName = new String[records.size()];
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
            }
            request.setAttribute("records", records);
            request.setAttribute("equName", equName);
            request.setAttribute("procLimit", limit);
            request.setAttribute("inOrOut", "Check-In");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/checkInOut.jsp");
            rd.forward(request, response);

        } else if ("viewCheckOut".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = db.queryBorrowByStatus("Check-In");
            String[] equName = new String[records.size()];
            ArrayList<CheckInOutBean> dueTimeList = cdb.queryCheck();
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
            }
            request.setAttribute("records", records);
            request.setAttribute("equName", equName);
            request.setAttribute("dueTimeList", dueTimeList);
            request.setAttribute("procLimit", limit);
            request.setAttribute("inOrOut", "Check-Out");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/checkInOut.jsp");
            rd.forward(request, response);
        } else if ("Check-In".equalsIgnoreCase(action)) {
            String userReq = request.getParameter("request");
            int brwID = Integer.parseInt(request.getParameter("id"));
            BorrowBean ub = db.queryBorrowByID(brwID);
            int stock = ub.getQuantity();
            int eid = ub.getEquipment_id();
            int currentStock = edb.queryQtyByID(eid);
            ub.setStatus(userReq);
            ub.setQuantity(currentStock - stock);
            db.editRecord(ub);
            cdb.addRecord(brwID, LocalDate.now(), LocalDate.now().plusDays(14));
            response.sendRedirect("CheckInOutController?action=viewCheckIn&limit=10");
        } else if ("Check-Out".equalsIgnoreCase(action)) {
            String userReq = request.getParameter("request");
            int brwID = Integer.parseInt(request.getParameter("id"));
            BorrowBean ub = db.queryBorrowByID(brwID);
            int stock = ub.getQuantity();
            int eid = ub.getEquipment_id();
            int currentStock = edb.queryQtyByID(eid);
            ub.setStatus(userReq);
            ub.setQuantity(currentStock + stock);
            db.editRecord(ub);
            cdb.delRecord(brwID);
            response.sendRedirect("CheckInOutController?action=viewCheckOut&limit=10");
        } else if ("search".equalsIgnoreCase(action)) {
            ArrayList<BorrowBean> records = null;
            ArrayList<CheckInOutBean> dueTimeList = new ArrayList<CheckInOutBean>();
            String searchtype = request.getParameter("searchtype");
            int searchword = Integer.parseInt(request.getParameter("searchword"));
            String CheckInOrOut = request.getParameter("inOrOut");
            String status = (CheckInOrOut.equalsIgnoreCase("Check-In")) ? "Accept" : "Check-In";

            //get borrow by equip id or student id
            if (searchtype.equals("eid")) {
                records = db.queryBorrowByStatusAndEID(status, searchword);
            } else if (searchtype.equals("sid")) {
                records = db.queryBorrowByStatusAndSID(status, searchword);
            }

            String[] equName = new String[records.size()];
            for (int i = 0; i < records.size(); i++) {
                equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                if (CheckInOrOut.equalsIgnoreCase("Check-Out")) {
                    dueTimeList.add(cdb.queryCheckByID(records.get(i).getBorrow_id()));
                    request.setAttribute("dueTimeList", dueTimeList);
                }
            }

            request.setAttribute("records", records);
            request.setAttribute("equName", equName);
            request.setAttribute("procLimit", limit);
            request.setAttribute("inOrOut", CheckInOrOut);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/checkInOut.jsp");
            rd.forward(request, response);

        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
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
