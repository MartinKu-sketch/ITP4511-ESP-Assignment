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
                height: 350px;
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
                            ArrayList<EquipmentBean> equips = (ArrayList<EquipmentBean>) request.getAttribute("inventory");
                            String limit = (String) request.getAttribute("limit");
                        %>
                        <h1>List of Equipment</h1>
                        <br/>
                        <div id="usertable-container">
                            <ict:showInventory equips='<%=equips%>' limit='<%=limit%>' page='student'/>
                            <ipage:page fullsize='<%=equips.size()%>' limit='<%=limit%>' page='studentlist'/>
                        </div>
                        <hr>
                        <br/>
                        <h1>Waiting to submit equipments</h1>
                        <br>
                        <table id="usertable">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Quantity</th>
                            </tr>
                            <jsp:useBean id="waitingborrowlist" scope="request" class="ict.bean.WaitingBorrowListBean"/>
                            <%
                                for (int i = 0; i < waitingborrowlist.getArraylist().size(); i++) {
                                    WaitingBorrowBean wbb = waitingborrowlist.getArraylist().get(i);
                                    out.println("<tr>"
                                            + "<td>" + wbb.getEquipment_id() + "</td>"
                                            + "<td>" + wbb.getEquipment_name() + "</td>"
                                            + "<td>" + wbb.getQuantity() + "</td>"
                                            + "</tr>");
                                }
                            %>
                        </table>
                        <br/>
                        <a class="paginationbtn" href="BorrowController?action=clear&limit=5">Clear</a>
                        <a class="paginationbtn" href="BorrowController?action=borrow&limit=5">Send Borrow Request</a>
                    </div>
                </div>
            </div>​
        </div>​
    </div>​
</body>​
</html>
