/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.UserBean;
import ict.db.UserDB;
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
 * @author saver
 */
@WebServlet(name = "AcMgmController", urlPatterns = {"/acMgmController"})
public class AcMgmController extends HttpServlet {

    private UserDB db;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if ("list".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList users = db.queryCust();
                request.setAttribute("users", users);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/accountManagement.jsp");
                rd.forward(request, response);
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                if (id != null) {
                    db.delRecord(id);
                    response.sendRedirect("acMgmController?action=list");
                }
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                if (id != null) {
                    ArrayList users = db.queryCust();
                    request.setAttribute("users", users);
                    UserBean ub = db.queryUserByID(id);
                    request.setAttribute("editub", ub);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/accountManagement.jsp");
                    rd.forward(request, response);
                }
            }
        } else if ("add".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String pw = request.getParameter("pw");
                String role = request.getParameter("role");
                db.addRecord(id, name, pw, role);
                response.sendRedirect("acMgmController?action=list");
            }
        } else if ("update".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String pw = request.getParameter("pw");
                String role = request.getParameter("role");
                UserBean ub = new UserBean();
                ub.setUserId(id);
                ub.setName(name);
                ub.setPw(pw);
                ub.setRole(role);
                db.editRecord(ub);
                response.sendRedirect("acMgmController?action=list");
            }
        } else if ("search".equalsIgnoreCase(action)) {
            if (!isAuthenticated(request) || !isAuthenticatedRole(request).equals("Senior Technician")) {
                response.sendRedirect("/build/NoPermission.jsp");
            } else {
                ArrayList users = null;
                String searchtype = request.getParameter("searchtype");
                String searchword = request.getParameter("searchword");
                if (searchtype.equals("id")) {
                    users = db.queryUserByIDs(searchword);
                } else if (searchtype.equals("name")) {
                    users = db.queryUserByName(searchword);
                }
                request.setAttribute("users", users);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/accountManagement.jsp");
                rd.forward(request, response);
                response.sendRedirect("acMgmController?action=list");
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
}
