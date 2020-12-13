<%@page import="ict.bean.UserBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.BorrowBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/showRequestAndCheck" prefix="proc" %>
<%@taglib uri="/WEB-INF/tlds/pagination" prefix="ipage" %>
<!DOCTYPE html>​
<html>
    <head> ​
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Process Borrow Requests</title>
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
                width:100%;
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
            String procLimit = (String) request.getAttribute("procLimit");
            String[] equName = (String[]) request.getAttribute("equName");
            String limit = (String) request.getAttribute("limit");
            String menu = "techMenu.jsp";
            session = request.getSession();
            UserBean bean = (UserBean) session.getAttribute("userId");
            if (bean.getRole().equals("Senior Technician"))
                menu = "stechMenu.jsp";
        %>

        <div id="maincontainer">​
            <div id="leftcolumn">​
                 <jsp:include page="<%=menu%>" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>Process Borrow Request</h1>
                        <br>
                        <div>
                            <ipage:page fullsize="<%=records.size()%>" limit="<%=procLimit%>" page="processRequest"  />
                        </div>
                        <form action="BorrowController" class='s_bar_con'>
                            <input type="hidden" name="action" value="search">
                            <input type="hidden" name="limit" value="10">
                            <select name="searchtype">
                                <option value="eid">Equipment ID</option>
                                <option value="sid">Student ID</option>
                            </select>
                            <input type="text" name="searchword" class="s_bar"><button type="submit" >Search</button>
                        </form>
                        <div id="usertable-container">
                            <proc:procBrw records="<%=records%>" equName="<%=equName%>" limit="<%=procLimit%>" page="processRequest"  />
                        </div>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
    <script>
        function isSubmit(e) {
            var target = event.target || event.srcElement;

            var txt = target.textContent;
            var id = target.id;
            var text = target.dataset.text;
            var stock = target.dataset.stock;
            var eid = target.dataset.eid;
            var DEFAULT_LIMIT = "10";
            var r = confirm(txt + " request of " + text + " ?");
            console.log(txt + " : " + id);
            if (r == true) {
                window.location.href = "BorrowController?action=procRequest&request=" + txt + "&id=" + id + "&limit=" + DEFAULT_LIMIT;
            } else {
                return;
            }
        }
    </script>
</html>
