<!-- --------------------------------------------------------------- -->
<!-- ----- Build using the opensource METIS - twitter bootstrap dashboard----------- -->
<!-- --------------------------------------------------------------- -->
<%@ page import="org.codehaus.groovy.grails.plugins.PluginManagerHolder"%>
<%@ page
	import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils"%>
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
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'bootstrap.min.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'bootstrap-responsive.min.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'datatable/jquery.dataTables.css')}"
	type="text/css">

<g:javascript library="jquery" plugin="jquery" />
<!--  FIXME, there's duplication here -->
<g:javascript library="modernizr_lib" />
<g:javascript>
        window.appContext = '${request.contextPath}';
        var root = location.protocol + '//' + location.host + window.appContext;
    </g:javascript>


<g:javascript library="application"/>


<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<g:set var="activeNavItem" value="${pageProperty(name: 'page.name')}" />

<g:layoutHead />
<r:layoutResources />
</head>
<body>

	<g:render template="/pathwaysModel/createPathwayModal" />
	<g:render template="/formDesign/createFormModal" />
	
	<!-- BEGIN WRAP -->
	<div id="wrap">

		<!-- BEGIN TOP BAR -->
		<div id="top">
			<!-- .navbar -->
			<div class="navbar navbar-inverse navbar-static-top">
				<div class="navbar-inner">
					<div class="container-fluid">
						<a class="btn btn-navbar" data-toggle="collapse"
							data-target=".nav-collapse"> <span class="icon-bar"></span> <span
							class="icon-bar"></span> <span class="icon-bar"></span>
						</a> <a class="brand" href="${createLink(uri: '/')}">MDC</a>
						<!-- .topnav -->
						<div class="btn-toolbar topnav">
							<div class="btn-group">
								<a id="changeSidebarPos" class="btn btn-success" rel="tooltip"
									data-original-title="Show / Hide Sidebar"
									data-placement="bottom"> <i class="icon-resize-horizontal"></i>
								</a>
							</div>
							<div class="btn-group">
								<a href="#helpModal" class="btn btn-inverse" rel="tooltip"
									data-placement="bottom" data-original-title="Help"
									data-toggle="modal"> <i class="icon-question-sign"></i>
								</a>
							</div>
							<div class="btn-group">
								<g:link data-placement="bottom" class="btn btn-inverse"
									data-original-title="Logout" rel="tooltip" controller="logout">
									<i class="icon-angle-right"></i>
									<i class="icon-off"></i>
								</g:link>
							</div>
						</div>


						<div class="search-bar">
							<div class="row-fluid">
								<div class="span12">
									<div class="search-bar-inner">
										<a id="menu-toggle" href="#menu" data-toggle="collapse"
											class="accordion-toggle btn btn-inverse visible-phone"
											rel="tooltip" data-placement="bottom"
											data-original-title="Show/Hide Menu"> <i
											class="icon-sort"></i>
										</a>
									</div>
								</div>
							</div>
						</div>

						<!-- /.topnav -->
						<div class="nav-collapse collapse">
							<!-- .nav -->
							<ul id="navbar" class="nav">
								<li><a href="${createLink(uri: '/')}">Dashboard</a></li>

								<li class="dropdown"><a data-toggle="dropdown"
									class="dropdown-toggle" href="#"> Profile <b class="caret"></b>
								</a>
									<ul class="dropdown-menu">
										<li><a href="#">Options</a></li>
										<li><a href="#">My Collections</a></li>
										<li><a href="#">My Forms</a></li>
									</ul></li>
								<sec:ifLoggedIn>

                  <!-- Value domains menu -->
                  <li id="nav-model-link"><g:link action="list" controller="ValueDomain"><i class="icon-angle-right"></i> Data model </g:link></li>
                  
                  <!-- Pathways menu -->
                  <li class="dropdown"><a id="nav-pathway-expand" data-toggle="dropdown"
                    class="dropdown-toggle" href="#"> Pathways <b
                      class="caret"></b>
                  </a>
                    <ul class="dropdown-menu">
                      <li id="nav-pathway-link"><g:link elementId="listPathwaysLink" action="list" controller="PathwaysModel">
                          <i class="icon-angle-right"></i> List pathways</g:link></li>

                      <li><a id="createPathwayLink" href="#"> <i
                          class="icon-angle-right"></i> Create pathway
                      </a></li>
   
										</ul></li>

									<!-- Form design menu -->
									
									<li class="dropdown"><a id="nav-form-expand"
										data-toggle="dropdown" class="dropdown-toggle" href="#">
											Forms <b class="caret"></b>
									</a>

										<ul class="dropdown-menu">
											<li id="nav-form-link"><g:link action="list"
													controller="FormDesign">
													<i class="icon-angle-right"></i> List Forms</g:link></li>

											<li><a id="createFormLink" href="#"> <i
						                          class="icon-angle-right"></i> Create Form
						                      </a></li>

										</ul></li>



									<sec:ifAnyGranted roles="ROLE_ADMIN">
										<li class="dropdown"><a data-toggle="dropdown"
											class="dropdown-toggle" href="#"> Administration <b
												class="caret"></b>
										</a>
											<ul class="dropdown-menu">
												<li><g:link controller="user" action='search'>Search users</g:link></li>
												<li><g:link controller="user" action='create'>Create user</g:link></li>
												<li><g:link controller="registrationCode"
														action='search'>
														<g:message code="spring.security.ui.menu.registrationCode" />
													</g:link></li>

												<li><g:link controller="role" action='search'>Search roles</g:link></li>
												<li><g:link controller="role" action='create'>Create role</g:link></li>

												<li><g:link controller="role" action='listPendingUsers'>Activate pending users</g:link></li>
												<li><g:link mapping="importData">Import data</g:link></li>
											</ul></li>
										<!--
                    <g:if
                      test='${SpringSecurityUtils.securityConfig.securityConfigType == SecurityConfigType.Requestmap}'>

                      <li class="dropdown"><a data-toggle="dropdown"
                        class="dropdown-toggle" href="#"> <g:message
                            code="spring.security.ui.menu.requestmaps" /><b
                          class="caret"></b>
                      </a>
                        <ul class="dropdown-menu">
                          <li><g:link controller="requestmap" action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                          <li><g:link controller="requestmap" action='create'>
                              <g:message code="spring.security.ui.create" />
                            </g:link></li>
                        </ul></li>

                    </g:if>

                    <g:if
                      test='${SpringSecurityUtils.securityConfig.rememberMe.persistent}'>
                      <li class="dropdown"><a data-toggle="dropdown"
                        class="dropdown-toggle" href="#"> <g:message
                            code="spring.security.ui.menu.persistentLogins" /><b
                          class="caret"></b>
                      </a>
                        <ul class="dropdown-menu">
                          <li><g:link controller="persistentLogin"
                              action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                        </ul></li>
                    </g:if>

                    <li class="dropdown"><a data-toggle="dropdown"
                      class="dropdown-toggle" href="#"> <g:message
                          code="spring.security.ui.menu.appinfo" /><b class="caret"></b>
                    </a>
                      <ul class="dropdown-menu">
                        <li><g:link action='config' controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.config' />
                          </g:link></li>
                        <li><g:link action='mappings' controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.mappings' />
                          </g:link></li>
                        <li><g:link action='currentAuth'
                            controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.auth' />
                          </g:link></li>
                        <li><g:link action='usercache'
                            controller='securityInfo'>
                            <g:message
                              code='spring.security.ui.menu.appinfo.usercache' />
                          </g:link></li>
                        <li><g:link action='filterChain'
                            controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.filters' />
                          </g:link></li>
                        <li><g:link action='logoutHandler'
                            controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.logout' />
                          </g:link></li>
                        <li><g:link action='voters' controller='securityInfo'>
                            <g:message code='spring.security.ui.menu.appinfo.voters' />
                          </g:link></li>
                        <li><g:link action='providers'
                            controller='securityInfo'>
                            <g:message
                              code='spring.security.ui.menu.appinfo.providers' />
                          </g:link></li>
                      </ul></li>
-->
										<!-- 
                    <g:if
                      test="${PluginManagerHolder.pluginManager.hasGrailsPlugin('springSecurityAcl')}">

                      <li class="dropdown"><a data-toggle="dropdown"
                        class="dropdown-toggle" href="#"> ACL <b class="caret"></b>
                      </a>
                        <ul class="dropdown-menu">
                          <li><g:message code="spring.security.ui.menu.aclClass" /></li>
                          <li><g:link controller="aclClass" action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                          <li><g:link controller="aclClass" action='create'>
                              <g:message code="spring.security.ui.create" />
                            </g:link></li>
                          <li><g:message code="spring.security.ui.menu.aclSid" /></li>
                          <li><g:link controller="aclSid" action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                          <li><g:link controller="aclSid" action='create'>
                              <g:message code="spring.security.ui.create" />
                            </g:link></li>
                          <li><g:message
                              code="spring.security.ui.menu.aclObjectIdentity" /></li>
                          <li><g:link controller="aclObjectIdentity"
                              action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                          <li><g:link controller="aclObjectIdentity"
                              action='create'>
                              <g:message code="spring.security.ui.create" />
                            </g:link></li>
                          <li><g:message code="spring.security.ui.menu.aclEntry" /></li>
                          <li><g:link controller="aclEntry" action='search'>
                              <g:message code="spring.security.ui.search" />
                            </g:link></li>
                          <li><g:link controller="aclEntry" action='create'>
                              <g:message code="spring.security.ui.create" />
                            </g:link></li>

                        </ul></li>

                    </g:if>
 -->
									</sec:ifAnyGranted>

								</sec:ifLoggedIn>
							</ul>
							<!-- /.nav -->
						</div>
					</div>
				</div>
			</div>
			<!-- /.navbar -->
		</div>
		<!-- END TOP BAR -->
