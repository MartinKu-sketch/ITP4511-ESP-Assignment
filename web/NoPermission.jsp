<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>No Permission</title>
    </head>
    <body>
        <p>You have no this permission</p>
        <p>
            <% out.println("<a href=\"main?action=logout\">Go to login</a>");%>
        </p>
    </body>
</html>
