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
<link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}"
	type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'theme.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'datatable/jquery.dataTables.css')}"
	type="text/css">

<g:javascript src="modernizr-2.6.2-respond-1.1.0.min.js" />

<g:javascript>
		    window.appContext = '${request.contextPath}';
		    var root = location.protocol + '//' + location.host + window.appContext;
		</g:javascript>
		
<g:javascript library="jquery_lib" />
<g:javascript library="jquery" plugin="jquery"/>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<g:set var="activeNavItem" value="${pageProperty(name: 'page.name')}" />

<g:layoutHead />
<r:layoutResources />
</head>
<body>

  <g:render template="/pathwaysModel/createPathwayModal" />

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
								<a class="btn btn-inverse" rel="tooltip" href="#"
									data-original-title="Document" data-placement="bottom"> <i
									class="icon-file"></i>
								</a> <a href="#helpModal" class="btn btn-inverse" rel="tooltip"
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
										<g:form url='[controller: "searchable", action: "index"]'
											class="main-search" id="searchableForm" name="searchableForm"
											method="get">
											<g:textField name="q" class="input-block-level"
												placeholder="Search Registry..." value="${params.q}" />
											<button id="searchBtn" type="submit" class="btn btn-inverse">
												<i class="icon-search"></i>
											</button>
										</g:form>
									</div>
								</div>
							</div>
						</div>

						<!-- /.topnav -->
						<div class="nav-collapse collapse">
							<!-- .nav -->
							<ul class="nav">
								<li class="active"><a href="index.html">Dashboard</a></li>

								<li class="dropdown"><a data-toggle="dropdown"
									class="dropdown-toggle" href="#"> Profile <b class="caret"></b>
								</a>
									<ul class="dropdown-menu">
										<li><a href="#">Options</a></li>
										<li><a href="#">My Collections</a></li>
										<li><a href="#">My Forms</a></li>
									</ul></li>
								<sec:ifLoggedIn>

									<li class="dropdown"><a data-toggle="dropdown"
										class="dropdown-toggle" href="#"> Pathways <b
											class="caret"></b>
									</a>
										<ul class="dropdown-menu">
											<li><g:link action="list" controller="PathwaysModel">
													<i class="icon-angle-right"></i> List pathways</g:link></li>
<!--
											<li><g:link action="create" controller="PathwaysModel"><i class="icon-angle-right"></i> Create pathway (old style)</g:link></li>
-->

											<li><a id="openModalLink" href="#"> <i
													class="icon-angle-right"></i> Create pathway
											</a></li>
										</ul> <script>
											// FIXME ryan refactor into JS file
											$('#openModalLink')
													.click(
															function() {
																$(
																		'#createPathwayModal')
																		.removeClass(
																				"hide");
																$(this)
																		.closest(
																				".dropdown")
																		.removeClass(
																				"open");
																return false;
															});
										</script> 
										
										
										<sec:ifAnyGranted roles="ROLE_ADMIN">

										<li class="dropdown"><a data-toggle="dropdown"
											class="dropdown-toggle" href="#"> <g:message
													code="spring.security.ui.menu.users" /> <b class="caret"></b>
										</a>
											<ul class="dropdown-menu">
												<li><g:link controller="user" action='search'>
														<g:message code="spring.security.ui.search" />
													</g:link></li>
												<li><g:link controller="user" action='create'>
														<g:message code="spring.security.ui.create" />
													</g:link></li>
												<li><g:link controller="registrationCode"
														action='search'>
														<g:message code="spring.security.ui.menu.registrationCode" />
													</g:link></li>
											</ul></li>
										<li class="dropdown"><a data-toggle="dropdown"
											class="dropdown-toggle" href="#"> <g:message
													code="spring.security.ui.menu.roles" /> <b class="caret"></b>
										</a>
											<ul class="dropdown-menu">
												<li><g:link controller="role" action='search'>
														<g:message code="spring.security.ui.search" />
													</g:link></li>
												<li><g:link controller="role" action='create'>
														<g:message code="spring.security.ui.create" />
													</g:link></li>
											</ul></li>

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




		<!-- BEGIN LEFT  -->
		<div id="left">
			<!-- .user-media -->
			<div class="media user-media hidden-phone">
				<a href="" class="user-link"> <g:img dir="img" file="user.gif"
						alt="" class="media-object img-polaroid user-img" />
				</a>

				<div class="media-body hidden-tablet">
					<h5 class="media-heading">
						<sec:ifLoggedIn>
							<sec:loggedInUserInfo field="username" />
						</sec:ifLoggedIn>
					</h5>
					<ul class="unstyled user-info">
						<li><sec:ifLoggedIn>
								<sec:ifAnyGranted roles="ROLE_ADMIN">Administrator</sec:ifAnyGranted>
							</sec:ifLoggedIn></li>
						<!--  <li>Last Access : <br/>
                                <small><i class="icon-calendar"></i></small>
                            </li>-->
					</ul>
				</div>
			</div>
			<!-- /.user-media -->

			<!-- BEGIN MAIN NAVIGATION -->
			<ul id="menu" class="unstyled accordion collapse in">
				<li id="cart" class="accordion-group cart"><a
					data-parent="#menu" data-toggle="collapse"
					class="accordion-toggle cart" data-target="#collection_basket_list">
						<i class="icon-shopping-cart icon-large cart"></i> Collection
						Basket <span class="label label-inverse pull-right">0</span>
				</a>
					<ul class="collapse cart" id="collection_basket_list">
						<li><g:link action="show" params="[id: 1]"
								controller="CollectionBasket">
								<i class="icon-angle-right"></i> view collection </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#collections-nav"> <i
						class="icon-list-ol icon-large"></i> Collections
				</a>
					<ul class="collapse" id="collections-nav">
						<li><g:link action="list" controller="Collection">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="Collection">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#dataElements-nav"> <i
						class="icon-tasks icon-large"></i> Data Elements
				</a>
					<ul class="collapse " id="dataElements-nav">
						<li><g:link action="list" controller="DataElement">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="DataElement">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#valueDomains-nav"> <i
						class="icon-th-large icon-large"></i> Value Domains
				</a>
					<ul class="collapse " id="valueDomains-nav">
						<li><g:link action="list" controller="ValueDomain">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="ValueDomain">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#dataElementConcepts-nav"> <i
						class="icon-sitemap icon-large"></i> Data Element Concepts
				</a>
					<ul class="collapse " id="dataElementConcepts-nav">
						<li><g:link action="list" controller="DataElementConcept">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="DataElementConcept">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#conceptualDomains-nav"> <i
						class="icon-th icon-large"></i> Conceptual Domains
				</a>
					<ul class="collapse " id="conceptualDomains-nav">
						<li><g:link action="list" controller="ConceptualDomain">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="ConceptualDomain">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#dataTypes-nav"> <i
						class="icon-subscript icon-large"></i> Data Types
				</a>
					<ul class="collapse " id="dataTypes-nav">
						<li><g:link action="list" controller="DataType">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="DataType">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#documents-nav"> <i class="icon-book icon-large"></i>
						Documents
				</a>
					<ul class="collapse " id="documents-nav">
						<li><g:link action="list" controller="Document">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="Document">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#externalReference-nav"> <i
						class="icon-external-link icon-large"></i> External References
				</a>
					<ul class="collapse " id="externalReference-nav">
						<li><g:link action="list" controller="ExternalReference">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="ExternalReference">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#formDesign-nav"> <i
						class="icon-file-text-alt icon-large"></i> Form Design
				</a>
					<ul class="collapse " id="formDesign-nav">
						<li><g:link action="list" controller="FormDesign">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="FormDesign">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#pathways-nav"> <i
						class="icon-file-text-alt icon-large"></i> Pathways
				</a>
					<ul class="collapse " id="pathways-nav">
						<li><g:link action="list" controller="PathwaysModel">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="PathwaysModel">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
			</ul>
			<!-- END MAIN NAVIGATION -->

		</div>
		<!-- END LEFT -->

		<!-- BEGIN MAIN CONTENT -->
		<div id="content">

			<!-- .outer -->
			<div class="container-fluid outer">
				<div class="row-fluid">
					<!-- .inner -->
					<div class="span12 inner">
						<g:layoutBody />
						<!-- /.row-fluid -->
						<!--END LATEST COMMENT-->
					</div>
					<!-- /.inner -->
				</div>
				<!-- /.row-fluid -->
			</div>
			<!-- /.outer -->
		</div>
		<!-- END CONTENT -->


		<!-- #push do not remove -->
		<div id="push"></div>
		<!-- /#push -->
	</div>
	<!-- END WRAP -->

	<div class="clearfix"></div>

	<!-- BEGIN FOOTER -->
	<div id="footer">
		<p>2013 © Model Catalog</p>
	</div>
	<!-- END FOOTER -->

	<!-- #helpModal -->
	<div id="helpModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="helpModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="helpModalLabel">
				<i class="icon-external-link"></i> Help
			</h3>
		</div>
		<div class="modal-body">
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
				do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
				enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
				ut aliquip ex ea commodo consequat. Duis aute irure dolor in
				reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
				pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
				culpa qui officia deserunt mollit anim id est laborum.</p>
		</div>
		<div class="modal-footer">

			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		</div>
	</div>
	<!-- /#helpModal -->


	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>

	<g:javascript library="application" />
	<sec:ifLoggedIn>
		<r:script>
            $(function() {
                dashboard();
            });
        </r:script>
	</sec:ifLoggedIn>
	<div id="dialog-confirm" style="display: none"></div>
	<r:layoutResources />
</body>
</html>
