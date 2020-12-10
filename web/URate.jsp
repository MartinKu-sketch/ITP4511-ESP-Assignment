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
        <title>View Utilization Rate</title>
        <link rel="stylesheet" href="css/mystyles.css" type="text/css"/>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <style type="text/css">
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
            .s_bar_con>div{
                display: flex;
                flex-direction: column;
                width:200px;
            }
            .s_bar_con>div>label{
                font-size: 20px;
            }
            .s_bar_con>button{
                box-sizing: border-box;
                cursor: pointer;
                padding: 10px 15px;
                font-size: 15px;
                background-color: #000033;
                color: white;
                border-style: none;
                border-radius: 5px;
                transition-timing-function: ease-in-out;
                transition-duration: .23s;
                font-weight: 700;
                text-transform: uppercase;
                text-decoration: none;
            }
        </style>
    </head>​
    <body>​
        <%
            String[] equName = (String[]) request.getAttribute("equName");
            int[] equID = (int[]) request.getAttribute("equID");
            String role = (String) request.getAttribute("role");
            String selectedEquip = (String) request.getAttribute("selectedEquip");
            String duration = (String) request.getAttribute("duration");
            String Total = (String) request.getAttribute("Total");
            String[] borrowed = (String[]) request.getAttribute("borrowed");
            String leftcolumn = (role.equalsIgnoreCase("tech")) ? "techMenu.jsp" : "stechMenu.jsp";
            System.out.print("t2" + Total);
            System.out.print("b2" + borrowed);

        %>

        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="<%=leftcolumn%>" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>View Utilization Rate</h1>
                        <br>
                        <%
                            if (role.equalsIgnoreCase("stech")) {
                                out.println("<div class=\"btn_pos\">\n"
                                        + "<a href=\"ReportController?action=viewOverdue&limit=10&role=stech\">Overdue</a>\n"
                                        + "<a href=\"ReportController?action=viewURate&role=stech\">Utilization Rate</a>\n"
                                        + "<a href=\"ReportController?action=viewAllBrw&limit=10&role=stech\">Borrowing records</a></div>");
                            }
                        %>

                        <form action="ReportController" class='s_bar_con'>
                            <input type="hidden" name="action" value="viewURate">
                            <input type="hidden" name="role" value="<%=role%>">
                            <input type="hidden" id="data" 
                                   data-duration="<%=duration%>" 
                                   data-equip="<%=selectedEquip%>" 
                                   data-total="<%=Total%>"  >
                            <div>
                                <label for="equipments">Equipments</label>
                                <select name="equipments" id="equipments">
                                    <%
                                        for (int i = 0; i < equName.length; i++) {
                                            String isSelected = "";
                                            if (selectedEquip.equalsIgnoreCase(equName[i])) {
                                                isSelected = "selected";
                                            }
                                            out.println("<option value= " + equID[i] + " " + isSelected + " >" + equName[i] + "</option>");
                                        }
                                    %>
                                </select>
                            </div>
                            <div>
                                <label for="duration">Duration</label>
                                <select name="duration" id="duration">
                                    <%
                                        String isMonth = "";
                                        String isYear = "";
                                        if (duration.equalsIgnoreCase("Monthly")) {
                                            isMonth = "selected";
                                        } else {
                                            isYear = "selected";
                                        }
                                        out.println("<option value=\"Monthly\" " + isMonth + ">Monthly</option>");
                                        out.println("<option value=\"Yearly\" " + isYear + ">Yearly</option>");

                                    %>
                                </select>
                            </div>
                            <button id="viewGraph" type="submit">Check</button>
                        </form>
                        <p>
                            *Formula of Utilization Rate* =
                        </p>   
                        <p style="color:grey; font-size: 13px;">
                            Total Borrowed number (monthly or yearly) / Total Stock / Total number of days * 100%
                        </p>
                        <br><br><br><br>
                        <canvas id="myChart"></canvas>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
    <script>
        window.onload = function () {
            var ctx = document.getElementById('myChart').getContext('2d');
            var data = document.getElementById('data');
            var duration = data.dataset.duration;
            var equip = data.dataset.equip;
            var total = parseInt(data.dataset.total);
//            var borrowed = data.dataset.borrowed;

            var uRate = new Array(<%=borrowed.length%>);
            var unUse = new Array(<%=borrowed.length%>);

            //hardcoded
            if (duration === "Monthly") {
                var label = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

            } else {
                var label = ['2019', '2020'];
            }
        <%
            int index = 0;
            for (index = 0; index < borrowed.length; index++) {
        %>
            for (var i = <%=index%>; i < <%=borrowed.length%>; i++) {
                uRate[i] = parseInt(<%=borrowed[index]%>);
                unUse[i] = -parseInt(<%=borrowed[index]%>);
            }
        <%
            }
        %>
            var barChartData = {
                labels: label,
                datasets: [{
                        label: 'Utilization Rate',
                        backgroundColor: 'rgba(255, 99, 132)',
                        data: uRate
                    }
//                    , 
//                    {
//                        label: 'Unused Rate',
//                        backgroundColor: 'rgba(54, 162, 235)',
//                        data: unUse
//                    }
                ]

            };

            console.log(uRate);
            console.log(unUse);
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    title: {
                        display: true,
                        text: 'Utilization Rate of ' + equip + ' ' + duration
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    responsive: true,
                    scales: {
                        xAxes: [{
//                                stacked: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Duration'
                                }
                            }],
                        yAxes: [{
//                                stacked: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Percentage (%)'
                                },
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                    }
                }
            });
        };
    </script>
</html>

