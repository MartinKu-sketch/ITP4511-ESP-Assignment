/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.BorrowBean;
import ict.bean.CheckInOutBean;
import ict.bean.EquipmentBean;
import ict.bean.UserBean;
import ict.bean.WaitingBorrowBean;
import ict.bean.WaitingBorrowListBean;
import ict.db.BorrowDB;
import ict.db.CheckInOutDB;
import ict.db.EquipmentDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "BorrowController", urlPatterns = {"/BorrowController"})
public class BorrowController extends HttpServlet {

    private BorrowDB db;
    private EquipmentDB edb;
    private CheckInOutDB cdb;
    private final String DEFAULT_LIMIT = "5";
    WaitingBorrowListBean wblb = null;

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        String limit = request.getParameter("limit");
        wblb = (WaitingBorrowListBean) session.getAttribute("wblb");
        if ("borrow".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                for (int i = 0; i < wblb.getArraylist().size(); i++) {
                    UserBean userid = (UserBean) session.getAttribute("userId");
                    int equipment_id = wblb.getArraylist().get(i).getEquipment_id();
                    int quantity = wblb.getArraylist().get(i).getQuantity();
                    String status = "Pending";
                    db.addRecord(equipment_id, userid.getUserId(), quantity, status);
                }
                wblb.getArraylist().clear();
                response.sendRedirect("BorrowController?action=studentlist&limit=5");
            }
        } else if ("Add".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equipslist = edb.queryEquipByVisibility();
                request.setAttribute("inventory", equipslist);
                request.setAttribute("limit", limit);
                String equipment_id = request.getParameter("id");
                String quantity = request.getParameter("quantity");
                EquipmentBean equips = edb.queryEquipByID(Integer.parseInt(equipment_id));
                WaitingBorrowBean wbb = new WaitingBorrowBean();
                wbb.setEquipment_id(equips.getEquipment_id());
                wbb.setEquipment_name(equips.getEquipment_name());
                wbb.setQuantity(Integer.parseInt(quantity));
                if (wblb.getArraylist().isEmpty()) {
                    wblb.getArraylist().add(wbb);
                } else {
                    for (int i = 0; i < wblb.getArraylist().size(); i++) {
                        if (wblb.getArraylist().get(i).getEquipment_id() == equips.getEquipment_id()) {
                            wblb.getArraylist().get(i).setQuantity(wblb.getArraylist().get(i).getQuantity() + Integer.parseInt(quantity));
                            break;
                        } else if (i == wblb.getArraylist().size() - 1) {
                            wblb.getArraylist().add(wbb);
                            break;
                        }
                    }
                }
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/equipmentBorrow.jsp");
                rd.forward(request, response);
            }
        } else if ("Remove".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equipslist = edb.queryEquipByVisibility();
                request.setAttribute("inventory", equipslist);
                request.setAttribute("limit", limit);
                String equipment_id = request.getParameter("id");
                String quantity = request.getParameter("quantity");
                EquipmentBean equips = edb.queryEquipByID(Integer.parseInt(equipment_id));
                WaitingBorrowBean wbb = new WaitingBorrowBean();
                wbb.setEquipment_id(equips.getEquipment_id());
                wbb.setEquipment_name(equips.getEquipment_name());
                wbb.setQuantity(Integer.parseInt(quantity));
                if (!wblb.getArraylist().isEmpty()) {
                    for (int i = 0; i < wblb.getArraylist().size(); i++) {
                        if (wblb.getArraylist().get(i).getEquipment_id() == equips.getEquipment_id()) {
                            wblb.getArraylist().get(i).setQuantity(wblb.getArraylist().get(i).getQuantity() - Integer.parseInt(quantity));
                            if (wblb.getArraylist().get(i).getQuantity() <= 0) {
                                wblb.getArraylist().remove(i);
                            }
                            break;
                        }
                    }
                }
                request.setAttribute("waitingborrowlist", wblb);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/equipmentBorrow.jsp");
                rd.forward(request, response);
            }
        } else if ("studentlist".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equipslist = edb.queryEquipByVisibility();
                request.setAttribute("inventory", equipslist);
                request.setAttribute("limit", limit);
                request.setAttribute("waitingborrowlist", wblb);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/equipmentBorrow.jsp");
                rd.forward(request, response);
            }
        } else if ("clear".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equipslist = edb.queryEquipByVisibility();
                request.setAttribute("inventory", equipslist);
                request.setAttribute("limit", limit);
                wblb.getArraylist().clear();
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/equipmentBorrow.jsp");
                rd.forward(request, response);
            }
        } else if ("viewRequest".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<BorrowBean> records = db.queryBorrowByStatus("pending");
                String[] equName = new String[records.size()];
                for (int i = 0; i < records.size(); i++) {
                    equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                }
                request.setAttribute("records", records);
                request.setAttribute("equName", equName);
                request.setAttribute("procLimit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/processBorrowReq.jsp");
                rd.forward(request, response);
            }
        } else if ("procRequest".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String userReq = request.getParameter("request");
                String brwID = request.getParameter("id");
                BorrowBean ub = db.queryBorrowByID(Integer.parseInt(brwID));
                int stock = ub.getQuantity();
                int eid = ub.getEquipment_id();
                if (stock > edb.queryQtyByID(eid)) {
                    userReq = "Reject";
                    ub.setStatus(userReq);
                    db.editRecord(ub);
                    String title = "Stock not Enough";
                    String desc = "Request has been rejected automatically";
                    String link = "BorrowController?action=viewRequest&limit=10";
                    response.sendRedirect("errorMsg.jsp?title=" + title + "&desc=" + desc + "&link=" + link);
                } else {
                    ub.setStatus(userReq);
                    db.editRecord(ub);
                    response.sendRedirect("BorrowController?action=viewRequest&limit=10");
                }
            }
        } else if ("search".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<BorrowBean> records = null;
                String searchtype = request.getParameter("searchtype");
                int searchword = Integer.parseInt(request.getParameter("searchword"));
                if (searchtype.equals("eid")) {
                    records = db.queryBorrowByEquipID(searchword);
                } else if (searchtype.equals("sid")) {
                    records = db.queryBorrowByStudentID(searchword);
                }
                String[] equName = new String[records.size()];
                for (int i = 0; i < records.size(); i++) {
                    equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                }
                request.setAttribute("records", records);
                request.setAttribute("equName", equName);
                request.setAttribute("procLimit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/processBorrowReq.jsp");
                rd.forward(request, response);
            }
        } else if ("stuSearch".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                UserBean user = (UserBean) session.getAttribute("userId");
                int userid = Integer.parseInt(user.getUserId());
                ArrayList<BorrowBean> records = new ArrayList<>();
                String searchtype = request.getParameter("searchtype");
                String searchword = request.getParameter("searchword");
                ArrayList<BorrowBean> cinRecords = db.queryBorrowByStatusAndSID("Check-In", userid);
                String[] equName2 = new String[cinRecords.size()];
                ArrayList<CheckInOutBean> cbRecords = new ArrayList<>();
                if (searchtype.equals("bid")) {
                    records.clear();
                    try {
                        BorrowBean bb = db.queryBorrowByIDAndSid(Integer.parseInt(searchword), userid);
                        if (bb != null) {
                            records.add(bb);
                        }
                    } catch (NumberFormatException ex) {

                    }

                } else if (searchtype.equals("status")) {
                    records = db.queryBorrowByStatusAndSID(searchword, userid);
                }
                String[] equName = new String[records.size()];
                for (int i = 0; i < records.size(); i++) {
                    equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                }
                for (int i = 0; i < cinRecords.size(); i++) {
                    equName2[i] = edb.queryEquipNameByID(cinRecords.get(i).getEquipment_id());
                    cbRecords.add(cdb.queryCheckByID(cinRecords.get(i).getBorrow_id()));
                }
                request.setAttribute("cbRecords", cbRecords);
                request.setAttribute("cinRecords", cinRecords);
                request.setAttribute("records", records);
                request.setAttribute("equName", equName);
                request.setAttribute("equName2", equName2);
                request.setAttribute("procLimit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/borrowRecord.jsp");
                rd.forward(request, response);
            }
        } else if ("borrowRecord".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Student")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                UserBean user = (UserBean) session.getAttribute("userId");
                int userid = Integer.parseInt(user.getUserId());
                ArrayList<BorrowBean> records = db.queryBorrowByStudentID(userid);
                ArrayList<BorrowBean> cinRecords = db.queryBorrowByStatusAndSID("Check-In", userid);
                String[] equName = new String[records.size()];
                String[] equName2 = new String[cinRecords.size()];
                ArrayList<CheckInOutBean> cbRecords = new ArrayList<>();
                for (int i = 0; i < records.size(); i++) {
                    equName[i] = edb.queryEquipNameByID(records.get(i).getEquipment_id());
                }
                for (int i = 0; i < cinRecords.size(); i++) {
                    equName2[i] = edb.queryEquipNameByID(cinRecords.get(i).getEquipment_id());
                    cbRecords.add(cdb.queryCheckByID(cinRecords.get(i).getBorrow_id()));
                }
                request.setAttribute("cbRecords", cbRecords);
                request.setAttribute("cinRecords", cinRecords);
                request.setAttribute("records", records);
                request.setAttribute("equName", equName);
                request.setAttribute("equName2", equName2);
                request.setAttribute("procLimit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/borrowRecord.jsp");
                rd.forward(request, response);
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            result = true;
        }
        return result;
    }

    private String isAuthenticatedRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserBean bean = (UserBean) session.getAttribute("userId");
        return bean.getRole();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
