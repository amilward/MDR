<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="ng">
    <title>Model Catalogue NG</title>
</head>

<body>
<appng-toolbar></appng-toolbar>

<div class="container" ng-view></div>

<div id="footer">
    <div class="container">
        <p class="text-muted">2013 © Model Catalog</p>
    </div>
</div>
<g:javascript disposition="defer">
            var sec = {
                loginUrl: "<%=loginUrl%>",
                rememberMe: "<%=rememberMe%>"
            }
</g:javascript>
<g:javascript disposition="defer" library="appng"/>
</body>
</html>