<%@ page import="org.codehaus.groovy.grails.plugins.PluginManagerHolder"%>
<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils"%>
<%@ page import="grails.plugins.springsecurity.SecurityConfigType"%>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<g:javascript>
    window.appContext = '${request.contextPath}';
    var root = location.protocol + '//' + location.host + window.appContext;
</g:javascript>


<r:require modules="application"/>


<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"/>
<![endif]-->

<g:set var="activeNavItem" value="${pageProperty(name: 'page.name')}" />

<g:layoutHead />
<r:layoutResources />
</head>
<body>

	<g:render template="/pathwaysModel/createPathwayModal" />
	<g:render template="/formDesign/createFormModal" />
    <!-- BEGIN WRAP. We use the wrap to enable a sticky footer element-->
    <div id="wrap">
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${createLink(uri: '/')}">MDC</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">

<sec:ifLoggedIn>
                        <li><a href="${createLink(uri: '/dashboard/')}">Dashboard</a></li>
                        <!-- Metadata curation menu -->
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Data Curation <b class="caret"></b></a>
                            <ul class="dropdown-menu">

                                <li class="dropdown-header">Models</li>
                                <li><g:link controller="Model" action='List'>List</g:link></li>
                                <li><g:link controller="Model" action='create'>Create</g:link></li>
                                <li class="dropdown-header">Data Elements</li>
                                <li><g:link controller="DataElement" action='List'>List</g:link></li>
                                <li><g:link controller="DataElement" action='create'>Create</g:link></li>
                                <li class="dropdown-header">Conceptual Domains</li>
                                <li><g:link controller="ConceptualDomain" action='List'>List</g:link></li>
                                <li><g:link controller="ConceptualDomain" action='create'>Create</g:link></li>
                                <li class="dropdown-header">Value Domain</li>
                                <li><g:link controller="ValueDomain" action='List'>List</g:link></li>
                                <li><g:link controller="ValueDomain" action='create'>Create</g:link></li>
                                <li class="dropdown-header">Relationships</li>
                                <li><g:link controller="Relationship" action='List'>List</g:link></li>
                                <li><g:link controller="Relationship" action='create'>Create</g:link></li>
                                <li class="dropdown-header">Relationship Types</li>
                                <li><g:link controller="RelationshipType" action='List'>List</g:link></li>
                                <li><g:link controller="RelationshipType" action='create'>Create</g:link></li>

 
                            </ul>
                        </li>

                        <!-- Pathways menu -->
                        <li class="dropdown">
                            <a id="nav-pathway-expand" class="dropdown-toggle" data-toggle="dropdown" href="#">Pathways <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="nav-pathway-link"><g:link elementId="listPathwaysLink" action="list" controller="PathwaysModel"> List pathways </g:link></li>
                                <li><a id="createPathwayLink" href="#" data-toggle="modal" data-target="#createPathwayModal"> Create pathway </a></li>
                            </ul>
                        </li>

                        <!-- Form design menu -->
                        <li class="dropdown">
                            <a id="nav-form-expand" class="dropdown-toggle" data-toggle="dropdown" href="#"> Forms <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="nav-form-link"><g:link action="list" controller="FormDesign"> List Forms</g:link></li>
                                <li><a id="createFormLink" href="#"> Create Form </a></li>
                            </ul>
                        </li>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
                        <!-- Admin menu -->
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Administration <b class="caret"></b></a>
                            <ul class="dropdown-menu">

                                <li class="dropdown-header">Roles</li>
                                <li><g:link controller="role" action='search'>Search roles</g:link></li>
                                <li><g:link controller="role" action='create'>Create role</g:link></li>
                                <li class="dropdown-header">Users</li>
                                <li><g:link controller="user" action='search'>Search users</g:link></li>
                                <li><g:link controller="user" action='create'>Create user</g:link></li>
                                <li><g:link controller="registrationCode" action='search'> <g:message code="spring.security.ui.menu.registrationCode" /> </g:link></li>
                                <li><g:link controller="role" action='listPendingUsers'>Activate pending users</g:link></li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Import/Export</li>
                                <li><g:link mapping="importData">Import data</g:link></li>
                            </ul>
                        </li>
    </sec:ifAnyGranted>
</sec:ifLoggedIn>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
<sec:ifLoggedIn>
                        <li><g:link class="btn btn-inverse" controller="logout"> Logout </g:link></li>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
                        <li><g:link class="btn btn-inverse" controller="login" action="auth" > Login </g:link></li>
</sec:ifNotLoggedIn>
                    </ul>
                </div>
            </div>
        </nav>