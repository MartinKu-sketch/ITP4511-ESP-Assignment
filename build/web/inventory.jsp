<%-- 
    Document   : inventory
    Created on : 2020年11月30日, 上午01:50:50
    Author     : user
--%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/showInventory" prefix="ict" %>
<%@taglib uri="/WEB-INF/tlds/pagination" prefix="ipage" %>
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

            /*            label {
                            width:20%;
                            margin-right:0.5em;
                            text-align: right;
                            font-weight: bold;
                        }*/

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
            form>div{
                display:flex;
                flex-direction: column;
                /*justify-content: space-between;*/
            }
            form span{
                display: inline-block;
                width:200px;
                margin-bottom: 10px;
                /*background-color: #4CAF50;*/
            }
            form input[type=text],form input[type=number]{
                padding:10px;
                margin-bottom: 10px;
            }
            input[type=reset]:hover,.sub_btn:hover {
                padding: 5px 10px;
                font-size: 11pt;
                background-color: cadetblue;
            }input[type=reset],.sub_btn{
                padding: 5px 10px;
                cursor: pointer;
                font-size: 11pt;
                background-color: gainsboro;
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
            textarea,input[type=number]{
                font-size: 12pt;
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
                            <ipage:page fullsize="<%=inventory.size()%>" limit="<%=limit%>" page="list"  />  
                        </div>

                        <!--                        <div class='s_bar_con'>
                                                    <input type="text" class="s_bar"><a>Search</a>
                                                </div>-->

                        <div id="usertable-container">
                            <ict:showInventory equips="<%=inventory%>" limit="<%=limit%>" page="technician"  />  

                        </div>
                        <jsp:useBean id="editB" scope="request" class="ict.bean.EquipmentBean"/>
                        <%
                            String type = editB.getEquipment_id() != 0 ? "Update" : "Add";
                            System.out.print("editB = " + Integer.toString(editB.getEquipment_id()));
                            String id = Integer.toString(editB.getEquipment_id()) != null ? Integer.toString(editB.getEquipment_id()) : "";
                            String name = editB.getEquipment_name() != null ? editB.getEquipment_name() : "";
                            String status = editB.getStatus() != null ? editB.getStatus() : "";
                            String description = editB.getDescription() != null ? editB.getDescription() : "";
                            int stock = editB.getStock() != 0 ? editB.getStock() : 0;
                            String visibility = editB.getVisibility() != null ? editB.getVisibility() : "";
                            String isChecked = ("true".equalsIgnoreCase(visibility)) ? "checked" : "";
                            String isAva = ("available".equalsIgnoreCase(status)) ? "checked" : "";
                        %>
                        <hr>
                        <br/>
                        <fieldset>
                            <legend style="font-size: larger;"><%=type%> Equipment</legend>
                            <form action="InventoryController" method="get" name="daForm">
                                <input type="hidden" name="action"  value="<%=type%>" />
                                <input type="hidden" name="id"  value="<%=id%>" />

                                <div><span>Equipment Name: </span><input name="name"  type="text" value="<%=name%>" /></div>
                                <div >
                                    <span >Status: </span> 
                                    <div style="flex-direction: row;">
                                        <input style="margin-right:5px;margin-bottom: 10px;" type="checkbox" id="ava" name="status" value="true" <%=isAva%>>
                                        <label for="ava">
                                            Available
                                        </label>

                                    </div>
                                </div>
                                <div><span>Description: </span><textarea style="margin-bottom:10px;padding: 10px;" name="desc" cols="20" rows="5" /><%=description%></textarea></div>
                                <div><span>Stock: </span><input name="stock"  type="number" value="<%=stock%>" /></div>
                                <div style="flex-direction: row;">
                                    <input style="margin-right:5px;" type="checkbox" id="ji" name="visibility" value="true" <%=isChecked%>>
                                    <label for="ji">
                                        Show in Borrow List
                                    </label>

                                </div>
                                <br>
                                <button id="<%=id%>" class="sub_btn" onclick="isSubmit(event)"><%=type%></button>
                                <input type="reset" value="Reset"/>
                                <!--<a href="acMgmController?action=list" id="adduserbtn">Change to Add User</a>-->
                            </form>
                        </fieldset>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​

    <script>
        function isSubmit(e) {
            var target = event.target || event.srcElement;

            var txt = target.textContent;
            var id = target.id
            if(txt === "Add"){
                var r = confirm("Perform " + txt + " action ?");
            }else{
                
            var r = confirm("Perform " + txt + " action for id= " + id + " ?");
            }
            console.log(txt + " : " + id);
            if (r == true) {
                if ("Delete" === txt) {
                    window.location.href = "InventoryController?action=" + txt + "&id=" + id;
                } else {
                    document.daForm.submit();
                }
            } else {
                return;
            }
//  
        }
    </script>
</html>
