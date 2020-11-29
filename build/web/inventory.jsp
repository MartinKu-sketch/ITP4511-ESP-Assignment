<%-- 
    Document   : inventory
    Created on : 2020年11月30日, 上午01:50:50
    Author     : user
--%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/showInventory" prefix="ict" %>
<%@taglib uri="/WEB-INF/tlds/inventoryPagination" prefix="ipage" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Management</title>
        <link rel="stylesheet" href="css/mystyles.css" type="text/css"/>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <style>
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
            .btn_pos{
                display:flex;
                width:100%;
                justify-content: space-around;
                margin:5px;
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
                background-color: black;
                color: white;
            }
            .btn_pos a.unactive:hover{
                background-color: grey;
                color: white;
                filter: none;
            }
            .innertube h1{
                text-align: center;
            }
        </style>
    </head>
    <body>​
        <jsp:useBean id="inventory" scope="request" class="java.util.ArrayList<ict.bean.EquipmentBean>"/>
        <%String limit = (String) request.getAttribute("limit");%>

        <div id="maincontainer">​
            <div id="leftcolumn">​
                <jsp:include page="techMenu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <h1>Inventory Management</h1>
                        <br>
                        <div>
                            <ipage:page fullsize="<%=inventory.size()%>" limit="<%=limit%>"  />  
                        </div>
                       


                        <div id="usertable-container">
                            <ict:showInventory equips="<%=inventory%>" limit="<%=limit%>"  />  
                        </div>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​

    <script>

    </script>
</html>
