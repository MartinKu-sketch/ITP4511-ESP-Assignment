<%@page contentType="text/html" pageEncoding="UTF-8"%>​
<!DOCTYPE html>​
<html>
    <head> ​
        <title>Profile</title>
        <link rel="stylesheet" href="css/mystyles.css" type="text/css"/>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet"> 
        <style type="text/css">
            fieldset {
                padding: 1em;
                font:80%/1 sans-serif;
            }

            label {
                float:left;
                width:20%;
                margin-right:0.5em;
                padding-top:0.2em;
                text-align: right;
                font-weight: bold;
            }
        </style>
    </head>​
    <body>​
        <div id="maincontainer">​
            <div id="topsection">
                <jsp:include page="heading.jsp" />​
            </div>​
            <div id="leftcolumn">​
                <jsp:include page="menu.jsp" />​
            </div>​
            <div id="contentwrapper">​
                <div id="contentcolumn">​
                    <div class="innertube">
                        <jsp:include page="login.jsp" />​
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
</html>
