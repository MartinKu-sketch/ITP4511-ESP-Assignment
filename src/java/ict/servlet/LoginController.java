/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.UserBean;
import ict.bean.WaitingBorrowListBean;
import ict.db.UserDB;
import java.io.IOException;
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
 * @author a1
 */
@WebServlet(name = "loginControl", urlPatterns = {"/main"})
public class LoginController extends HttpServlet {

    UserDB db;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (!isAuthenticated(request)
                && !("authenticate".equals(action))) {
            doLogin(request, response);
            return;
        }
        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role;
        String userId;
        if (isAuthenticated(request)) {
            HttpSession session = request.getSession();
            UserBean bean = new UserBean();
            bean = (UserBean) session.getAttribute("userId");
            userId = bean.getUserId();
            role = bean.getRole();
        } else {
            userId = request.getParameter("id");
            String password = request.getParameter("pw");
            role = db.isValidUser(userId, password);
        }
        String targetURL;

        if (role.equals("Student")) {
            HttpSession session = request.getSession(true);
            UserBean bean = new UserBean();
            WaitingBorrowListBean wblb = new WaitingBorrowListBean();
            wblb.setArraylist(new ArrayList<>());
            bean.setUserId(userId);
            bean.setRole("Student");
            session.setAttribute("userId", bean);
            session.setAttribute("wblb", wblb);
            targetURL = "student.jsp";
        } else if (role.equals("Technician")) {
            HttpSession session = request.getSession(true);
            UserBean bean = new UserBean();
            bean.setUserId(userId);
            bean.setRole("Technician");
            session.setAttribute("userId", bean);
            targetURL = "tech.jsp";
        } else if (role.equals("Senior Technician")) {
            HttpSession session = request.getSession(true);
            UserBean bean = new UserBean();
            bean.setUserId(userId);
            bean.setRole("Senior Technician");
            session.setAttribute("userId", bean);
            targetURL = "stech.jsp";
        } else {
            targetURL = "loginError.jsp";
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            result = true;
        }
        return result;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetURL = "index.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("userId");
            session.removeAttribute("wblb");
            session.invalidate();
        }
        doLogin(request, response);
    }
}
