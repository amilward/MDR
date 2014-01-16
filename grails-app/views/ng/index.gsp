<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="ng">
    <title>Model Catalogue NG</title>
</head>

<body>
<!-- Static navbar -->
<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>

        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a href="#">Separated link</a></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <login-toolbar></login-toolbar>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="container" ng-controller="HomeCtrl">

    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>App NG</h1>

        <p>This example is a quick exercise to illustrate how the default, static and fixed to top navbar work. It includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>

        <p>To see the difference between static and fixed top navbars, just scroll.</p>

        <p>
            <a class="btn btn-lg btn-primary" ng-click="fetch()" role="button">Fetch</a>
        </p>
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