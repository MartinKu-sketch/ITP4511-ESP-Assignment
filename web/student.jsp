<%@page import="ict.db.CheckInOutDB"%>
<%@page import="ict.bean.CheckInOutBean"%>
<%@page import="ict.db.BorrowDB"%>
<%@page import="ict.bean.BorrowBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.UserDB"%>
<%@page import="ict.bean.UserBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>​
<!DOCTYPE html>​
<%!
    String dbUser = "root";
    String dbPassword = "root";
    String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_DB?useSSL=false";
    UserDB udb = new UserDB(dbUrl, dbUser, dbPassword);
    BorrowDB bdb = new BorrowDB(dbUrl, dbUser, dbPassword);
    CheckInOutDB cdb = new CheckInOutDB(dbUrl, dbUser, dbPassword);
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

            .alert {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top:150px;
                border: 2px solid #999999;
                padding: 20px 15px;
            }

            .alertmsg {
                font-size: 18pt;
            }

            #count {
                color:red;
                text-decoration: underline;
            }
        </style>
    </head>​
    <body>​
        <%
            int count = 0;
            session = request.getSession();
            UserBean bean = (UserBean) session.getAttribute("userId");
            UserBean user = udb.queryUserByID(bean.getUserId());
            String username = user.getName();
            ArrayList<BorrowBean> cinRecords = bdb.queryBorrowByStatusAndSID("Check-In", Integer.parseInt(user.getUserId()));
            for (int i = 0; i < cinRecords.size(); i++) {
                BorrowBean bb = cinRecords.get(i);
                CheckInOutBean cb = cdb.queryCheckByID(cinRecords.get(i).getBorrow_id());
                if (java.time.LocalDate.now().getYear() == cb.getEnd().getYear() && java.time.LocalDate.now().getMonth() == cb.getEnd().getMonth() && cb.getEnd().getDayOfMonth() - java.time.LocalDate.now().getDayOfMonth() <= 0) {
                    count++;
                }
            }
            String alertmsg = "You have <a href='BorrowController?action=borrowRecord'><span id='count'>" + count + "</span></a> overdue items";
            if (count > 0) {
                alertmsg += "! Please check-out as soon as possible!";
            }
            if (count == 0) {
                alertmsg += ". Good Job!";
            }
        %>
        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="studentMenu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>Welcome! <%=username%></h1>
                        <div class="alert">
                            <span><img src="img/bell.gif" width="64px" alt=""/></span>
                            <span class="alertmsg"><%=alertmsg%></span>
                            <span><img src="img/bell.gif" width="64px" alt=""/></span>
                        </div>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
</html>
