<%@page import="ict.bean.UserBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>​
<!DOCTYPE html>​
<html>
    <head> ​
        <title>Account Management</title>
        <link rel="stylesheet" href="css/mystyles.css" type="text/css"/>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <style type="text/css">
            fieldset {
                padding: 1em;
            }

            label {
                width:20%;
                margin-right:0.5em;
                text-align: right;
                font-weight: bold;
            }

            #usertable-container {
                height: 300px;
                overflow-x: scroll;
            }

            td a {
                margin-right: 20px;
            }

            td a:hover {
                text-decoration: underline;
            }

            form span {
                display: inline-block;
                width:80px;
            }

            #adduserbtn{
                padding:5px 10px;
                cursor: pointer;
                font-size: 11pt;
                background-color: gainsboro;
                color:black;
                border: 2px whitesmoke outset;
            }

            #adduserbtn:hover{
                padding:5px 10px;
                font-size: 11pt;
                background-color: cadetblue;
            }
        </style>
    </head>​
    <body>​
        <jsp:useBean id="users" scope="request" class="java.util.ArrayList<ict.bean.UserBean>"/>
        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="stechMenu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>List of User</h1>
                        <br>
                        <form action="acMgmController" method="post">
                            <input type="hidden" name="action"  value="search" />
                            <span style="width:120px !important;">Search User By</span>
                            <input type="radio" name="searchtype" value="id" class="searchtyperadio" required checked>ID</input>
                            <input type="radio" name="searchtype" value="name" class="searchtyperadio" required>Name</input>
                            <input type="text" name="searchword"/>
                            <span><input type="submit" value="Search"/></span>
                        </form>
                        <br />
                        <div id="usertable-container">
                            <table id="usertable">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Password</th>
                                    <th>Role</th>
                                    <th>Action</th>
                                </tr>
                                <%
                                    for (int i = 0; i < users.size(); i++) {
                                        UserBean ub = users.get(i);
                                        out.println("<tr>"
                                                + "<td>" + ub.getUserId() + "</td>"
                                                + "<td>" + ub.getName() + "</td>"
                                                + "<td>" + ub.getPw() + "</td>"
                                                + "<td>" + ub.getRole() + "</td>"
                                                + "<td><a href=\"acMgmController?action=delete&id=" + ub.getUserId() + "\">Delete</a>"
                                                + "<a href=\"acMgmController?action=edit&id=" + ub.getUserId() + "\">Edit</a></td>"
                                                + "</tr>");
                                    }
                                %>
                            </table>
                        </div>
                        <jsp:useBean id="editub" scope="request" class="ict.bean.UserBean"/>
                        <%
                            String type = editub.getUserId() != null ? "update" : "add";
                            String id = editub.getUserId() != null ? editub.getUserId() : "";
                            String name = editub.getName() != null ? editub.getName() : "";
                            String pw = editub.getPw() != null ? editub.getPw() : "";
                            String role = editub.getRole() != null ? editub.getRole() + "" : "";
                        %>
                        <br/>
                        <hr>
                        <br/>
                        <fieldset>
                            <legend style="font-size: larger;">User <%=type%></legend>
                            <form action="acMgmController" method="post">
                                <input type="hidden" name="action"  value="<%=type%>" />
                                <span>ID: </span><input name="id"  type="text" value="<%=id%>" /> <br><br>
                                <span>Name: </span><input name="name"  type="text" value="<%=name%>" /> <br><br>
                                <span>Password: </span><input name="pw"  type="text" value="<%=pw%>" /> <br><br>
                                <span>Role: </span>
                                <select name="role" style="font-size: 12pt;width:204px;margin-left: -4px;">
                                    <%
                                        if (role.equals("Student")) {
                                            out.print("<option value=\"Student\" selected>Student</option>");
                                            out.print("<option value=\"Technician\">Technician</option>");
                                            out.print("<option value=\"Senior Technician\">Senior Technician</option>");
                                        } else if (role.equals("Technician")) {
                                            out.print("<option value=\"Student\">Student</option>");
                                            out.print("<option value=\"Technician\" selected>Technician</option>");
                                            out.print("<option value=\"Senior Technician\">Senior Technician</option>");
                                        } else if (role.equals("Senior Technician")) {
                                            out.print("<option value=\"Student\">Student</option>");
                                            out.print("<option value=\"Technician\">Technician</option>");
                                            out.print("<option value=\"Senior Technician\" selected>Senior Technician</option>");
                                        } else {
                                            out.print("<option value=\"Student\">Student</option>");
                                            out.print("<option value=\"Technician\">Technician</option>");
                                            out.print("<option value=\"Senior Technician\">Senior Technician</option>");
                                        }
                                    %>                 
                                </select>
                                <br/>
                                <br/>
                                <input type="submit" value="<%=type%>"/>
                                <a href="acMgmController?action=list" id="adduserbtn">Change to Add User</a>
                            </form>
                        </fieldset>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
</html>
