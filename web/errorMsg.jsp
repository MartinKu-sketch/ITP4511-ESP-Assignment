<%-- 
    Document   : errorMsg
    Created on : 2020年12月2日, 上午08:06:48
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Occur</title>
    </head>
    <body>
    <center>
        <h1><%=request.getParameter("title")%></h1>
        <h2><%=request.getParameter("desc")%></h2>
        <a href="<%=request.getParameter("link")+"&limit="+request.getParameter("limit")%>">Back</a>
    </center>
</body>
</html>
