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
            }

            label {
                width:20%;
                margin-right:0.5em;
                text-align: right;
                font-weight: bold;
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
                        <h1>Welcome! Student</h1>
                    </div>
                </div>​
            </div>​
        </div>​
    </body>​
</html>
