<%@page import="ict.bean.UserBean"%>
<%@page import="ict.db.UserDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>​
<!DOCTYPE html>​
<%!
    String dbUser = "root";
    String dbPassword = "root";
    String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_DB?useSSL=false";
    UserDB udb = new UserDB(dbUrl, dbUser, dbPassword);
%>
<html>
    <head> ​
        <title>Profile</title>
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
        </style>
    </head>​
    <body>​
        <%
            session = request.getSession();
            UserBean bean = (UserBean) session.getAttribute("userId");
            UserBean user = udb.queryUserByID(bean.getUserId());
            String username = user.getName();
        %>
        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="techMenu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>Welcome! <%=username%></h1>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
</html>
