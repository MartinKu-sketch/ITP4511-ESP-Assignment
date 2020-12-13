<%@page import="ict.bean.CheckInOutBean"%>
<%@page import="ict.bean.WaitingBorrowBean"%>
<%@page import="ict.bean.BorrowBean"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@taglib uri="/WEB-INF/tlds/showInventory.tld" prefix="ict" %>
<%@taglib uri="/WEB-INF/tlds/pagination.tld" prefix="ipage" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>​
<!DOCTYPE html>​
<html>
    <head> ​
        <title>Equipment Reservation</title>
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
                max-height: 300px;
                overflow: scroll;
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

            .btn_pos{
                margin:20px 0;
            }

            .paginationbtn {
                padding:5px 10px;
                cursor: pointer;
                font-size: 11pt;
                background-color: gainsboro;
                color:black;
                border: 2px whitesmoke outset;
            }

            .paginationbtn:hover {
                padding:5px 10px;
                font-size: 11pt;
                background-color: cadetblue;
            }

            .unactive{
                color:#999999 !important;
            }

            .unactive:hover{
                padding:5px 10px;
                cursor: default;
                font-size: 11pt;
                background-color: gainsboro;
                border: 2px whitesmoke outset;
                color:#999999 !important;
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
                padding: 5px 15px;
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

        </style>
    </head>​
    <body>​
        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="studentMenu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <%
                            ArrayList<BorrowBean> records = (ArrayList<BorrowBean>) request.getAttribute("records");
                            String[] equName = (String[]) request.getAttribute("equName");
                            ArrayList<BorrowBean> cinRecords = (ArrayList<BorrowBean>) request.getAttribute("cinRecords");
                            String[] equName2 = (String[]) request.getAttribute("equName2");
                            ArrayList<CheckInOutBean> cbRecords = (ArrayList<CheckInOutBean>) request.getAttribute("cbRecords");
                            String limit = (String) request.getAttribute("procLimit");
                        %>
                        <h1>Recent Borrow Items</h1>
                        <br/>
                        <div id="usertable-container">
                            <table id="usertable">
                                <tr>
                                    <th>Borrow ID</th>
                                    <th>Equipment Name</th>
                                    <th>Quantity</th>
                                    <th>Borrow Date</th>
                                    <th>Due Date</th>
                                </tr>
                                <%
                                    try {
                                        for (int i = 0; i < cinRecords.size(); i++) {
                                            String bgcolor = "";
                                            String color = "";
                                            BorrowBean bb = cinRecords.get(i);
                                            CheckInOutBean cb = cbRecords.get(i);
                                            if (java.time.LocalDate.now().getYear() == cb.getEnd().getYear() && java.time.LocalDate.now().getMonth() == cb.getEnd().getMonth() && cb.getEnd().getDayOfMonth() - java.time.LocalDate.now().getDayOfMonth() <= 3) {
                                                bgcolor = "#ff6100";
                                                color = "white";
                                            }
                                            if (java.time.LocalDate.now().getYear() == cb.getEnd().getYear() && java.time.LocalDate.now().getMonth() == cb.getEnd().getMonth() && cb.getEnd().getDayOfMonth() - java.time.LocalDate.now().getDayOfMonth() <= 0) {
                                                bgcolor = "red";
                                                color = "white";
                                            }
                                            out.println("<tr style=\"background-color:" + bgcolor + "; color:" + color + "\">"
                                                    + "<td>" + bb.getBorrow_id() + "</td>"
                                                    + "<td>" + equName2[i] + "</td>"
                                                    + "<td>" + bb.getQuantity() + "</td>"
                                                    + "<td>" + cb.getStart().toString() + "</td>"
                                                    + "<td>" + cb.getEnd().toString() + "</td>"
                                                    + "</tr>");
                                        }
                                    } catch (Exception ex) {

                                    }
                                %>
                            </table>
                        </div>
                        <br />
                        <hr>
                        <br/>
                        <h1>Borrow Record</h1>
                        <br/>
                        <form action="BorrowController" class='s_bar_con'>
                            <input type="hidden" name="action" value="stuSearch">
                            <select name="searchtype">
                                <option value="bid">Borrow ID</option>
                                <option value="status">Status</option>
                            </select>
                            <input type="text" name="searchword" class="s_bar"><button type="submit" >Search</button>
                        </form>
                        <br/>
                        <div id="usertable-container">
                            <table id="usertable">
                                <tr>
                                    <th>Borrow ID</th>
                                    <th>Equipment Name</th>
                                    <th>Quantity</th>
                                    <th>Status</th>
                                </tr>
                                <%
                                    for (int i = records.size() - 1; i >= 0; i--) {
                                        String bgcolor = "";
                                        String bgcolor2 = "";
                                        BorrowBean bb = records.get(i);
                                        if (bb.getStatus().equals("Pending")) {
                                            bgcolor = "#faac1b";
                                        }
                                        if (bb.getStatus().equals("Accept")) {
                                            bgcolor = "#72c425";
                                        }
                                        if (bb.getStatus().equals("Check-In")) {
                                            bgcolor = "#69d9de";
                                            bgcolor2 = "#69d9de";
                                        }
                                        if (bb.getStatus().equals("Reject")) {
                                            bgcolor = "#fb4747";
                                        }
                                        if (bb.getStatus().equals("Check-Out")) {
                                            bgcolor = "#a5a4a4";
                                        }
                                        out.println("<tr style=\"background-color:" + bgcolor2 + "\">"
                                                + "<td>" + bb.getBorrow_id() + "</td>"
                                                + "<td>" + equName[i] + "</td>"
                                                + "<td>" + bb.getQuantity() + "</td>"
                                                + "<td style=\"background-color:" + bgcolor + "\">" + bb.getStatus() + "</td>"
                                                + "</tr>");
                                    }
                                %>
                            </table>
                        </div>
                        <br />
                        <hr>
                        <br/>
                    </div>
                </div>
            </div>​
        </div>​
    </div>​
</body>​
</html>
