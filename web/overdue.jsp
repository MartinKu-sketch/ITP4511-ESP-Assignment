<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.BorrowBean"%>
<%@page import="ict.bean.CheckInOutBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/showReport" prefix="report" %>
<%@taglib uri="/WEB-INF/tlds/pagination" prefix="ipage" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lookup Overdue Item</title>
        <link rel="stylesheet" href="css/mystyles.css" type="text/css"/>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <style type="text/css">
            /*table*/
            #usertable-container {
                /*height: 300px;*/
                margin-bottom: 20px;
                /*overflow-x: scroll;*/
            }

            td a {
                margin-right: 20px;
            }

            td a:hover {
                text-decoration: underline;
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

            /*btn and h1*/
            .innertube h1{
                text-align: center;
            }
            .btn_pos{
                display:flex;
                /*width:100%;*/
                justify-content: space-around;
                margin:5px;
                margin-bottom: 20px;
            }
            .btn_pos a{
                box-sizing: border-box;
                cursor: pointer;
                padding: 10px 15px;
                font-size: 15px;
                background-color: #ffc508;
                color:black;
                border-style: none;
                border-radius: 5px;
                transition-timing-function: ease-in-out;
                transition-duration: .23s;font-weight: 700;
                text-transform: uppercase;
                text-decoration: none;
            }
            .btn_pos a:hover{
                background-color: #ffe9a0;
                filter: drop-shadow(0px 0px 9px rgba(223, 135, 20, 1.0));
            }
            .btn_pos a.unactive{
                background-color: grey;
                color: white;
                filter: none;
            }
            .s_bar_con{
                margin-bottom: 10px;
                display: flex;
                justify-content: space-between;
            }
            .s_bar{
                width: 70%;
                padding:5px;
                font-size: 16px;
            }
            .s_bar~a{
                text-decoration: none;
                box-sizing: border-box;
                cursor: pointer;
                padding: 10px 15px;
                font-size: 15px;
                background-color: #000033;
                color: white;
                border-style: none;
                border-radius: 5px;
                transition-timing-function: ease-in-out;
                transition-duration: .23s;font-weight: 700;
                text-transform: uppercase;
                text-decoration: none;
            }
            .s_bar_con{
                margin-bottom: 10px;
                display: flex;
                justify-content: space-between;
            }
            .s_bar{
                width: 70%;
                padding:5px;
                font-size: 16px;
            }
            .s_bar~*{
                text-decoration: none;
                box-sizing: border-box;
                cursor: pointer;
                padding: 10px 15px;
                font-size: 15px;
                background-color: #000033;
                color: white;
                border-style: none;
                border-radius: 5px;
                transition-timing-function: ease-in-out;
                transition-duration: .23s;font-weight: 700;
                text-transform: uppercase;
                text-decoration: none;
            }
            tr>td:last-child *{
                color: blue;
                border-style: none;
                background-color: transparent;
                font-size: 16px;
                margin:5px;
                cursor: pointer;
            }
            tr>td:last-child *:hover{
                text-decoration: none;
                color: red;
            }
        </style>
    </head>​
    <body>​
        <jsp:useBean id="records" scope="request" class="java.util.ArrayList<ict.bean.BorrowBean>"/>
        <%
            String limit = (String) request.getAttribute("limit");
            String[] equName = (String[]) request.getAttribute("equName");
            ArrayList<CheckInOutBean> dueTimeList = (ArrayList<CheckInOutBean>) request.getAttribute("dueTimeList");
            String role = (String) request.getAttribute("role");
            String leftcolumn = (role.equalsIgnoreCase("tech")) ? "techMenu.jsp" : "stechMenu.jsp";
            
        %>

        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="<%=leftcolumn%>" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>Lookup Overdue Item</h1>
                        <br>
                        <%
                            if (role.equalsIgnoreCase("stech")) {
                                out.println("<div class=\"btn_pos\">\n"
                                        + "<a href=\"ReportController?action=viewOverdue&limit=10&role=stech\">Overdue</a>\n"
                                        + "<a href=\"ReportController?action=viewURate&limit=10&role=stech\">Utilization Rate</a>\n"
                                        + "<a href=\"ReportController?action=viewAllBrw&limit=10&role=stech\">Borrowing records</a></div>");
                            }
                        %>

                        <div>
                            <ipage:page fullsize="<%=records.size()%>" limit="<%= limit%>" role="tech"  page="viewOverdue"  />  
                        </div>
                        <form action="ReportController" class='s_bar_con'>
                            <input type="hidden" name="action" value="searchOverdue">
                            <input type="hidden" name="role" value="<%=role%>">
                            <select name="searchtype">
                                <option value="eid">Equipment ID</option>
                                <option value="sid">Student ID</option>
                            </select>
                            <input type="text" name="searchword" class="s_bar"><button type="submit" >Search</button>
                        </form>
                        <div id="usertable-container">
                            <report:show records="<%=records%>" equName="<%=equName%>" limit="<%=limit%>" page="viewOverdue" dueTimeList="<%=dueTimeList%>" />  
                        </div>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>
</html>
