/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.EquipmentBean;
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
            ArrayList<EquipmentBean> equips = db.queryEquip();
            request.setAttribute("inventory", equips);
            request.setAttribute("limit", limit);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/inventory.jsp");
            rd.forward(request, response);
        } else if ("Delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                db.delRecord( Integer.parseInt(id) );
               response.sendRedirect("InventoryController?action=list&limit=5");
                
                
            }
        } else if ("Edit".equalsIgnoreCase(action)) {
//            String id = request.getParameter("id");
//            String name = request.getParameter("name");
//            String pw = request.getParameter("pw");
//            String role = request.getParameter("role");
//            UserBean ub = new UserBean();
//            ub.setUserId(id);
//            ub.setName(name);
//            ub.setPw(pw);
//            ub.setRole(role);
//            db.editRecord(ub);
//            response.sendRedirect("acMgmController?action=list");
        } else if ("add".equalsIgnoreCase(action)) {
//            String id = request.getParameter("id");
//            String name = request.getParameter("name");
//            String pw = request.getParameter("pw");
//            String role = request.getParameter("role");
//            db.addRecord(id, name, pw, role);
//            response.sendRedirect("acMgmController?action=list");
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
