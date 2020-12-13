/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.EquipmentBean;
import ict.bean.UserBean;
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
@WebServlet(name = "InventoryController", urlPatterns = {"/InventoryController"})
public class InventoryController extends HttpServlet {

    private EquipmentDB db;
    private final String DEFAULT_LIMIT = "5";

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new EquipmentDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String limit = request.getParameter("limit");

        if ("list".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equips = db.queryEquip();
                request.setAttribute("inventory", equips);
                request.setAttribute("limit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/inventory.jsp");
                rd.forward(request, response);
            }
        } else if ("studentlist".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList<EquipmentBean> equips = db.queryEquipByVisibility();
                request.setAttribute("inventory", equips);
                request.setAttribute("limit", limit);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/equipmentBorrow.jsp");
                rd.forward(request, response);
            }
        } else if ("Delete".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                if (id != null) {
                    db.delRecord(Integer.parseInt(id));
                    response.sendRedirect("InventoryController?action=list&limit=5");
                }
            }
        } else if ("Edit".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                if (id != null) {
                    ArrayList equips = db.queryEquip();
                    request.setAttribute("inventory", equips);
                    request.setAttribute("limit", DEFAULT_LIMIT);
                    EquipmentBean eb = db.queryEquipByID(Integer.parseInt(id));
                    request.setAttribute("editB", eb);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/inventory.jsp");
                    rd.forward(request, response);
                }
            }
        } else if ("Add".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                if (status.equalsIgnoreCase("true")) {
                    status = "available";
                } else {
                    status = "unavailable";
                }
                String desc = request.getParameter("desc");
                int stock = Integer.parseInt(request.getParameter("stock"));
                String visibility = ("true".equalsIgnoreCase(request.getParameter("visibility"))) ? "true" : "false";
                db.addRecord(name, status, desc, stock, visibility);
                response.sendRedirect("InventoryController?action=list&limit=5");
            }
        } else if ("Update".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Technician") && !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String status = ("true".equalsIgnoreCase(request.getParameter("status"))) ? "available" : "unavailable";
                String desc = request.getParameter("desc");
                int stock = Integer.parseInt(request.getParameter("stock"));
                String visibility = ("true".equalsIgnoreCase(request.getParameter("visibility"))) ? "true" : "false";

                EquipmentBean ub = new EquipmentBean();
                ub.setEquipment_id(Integer.parseInt(id));
                ub.setEquipment_name(name);
                ub.setStatus(status);
                ub.setDescription(desc);
                ub.setStock(stock);
                ub.setVisibility(visibility);
                db.editRecord(ub);
                response.sendRedirect("InventoryController?action=list&limit=5");
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
