<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link href='http://fonts.googleapis.com/css?family=Michroma' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Orbitron' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<g:javascript>
		    window.appContext = '${request.contextPath}';
		</g:javascript>
		<g:javascript library="jquery" plugin="jquery" />
		<r:require module="jquery-ui"/>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	  <div id="maincontainer">

			<div id="contentwrapper">
				<div id="contentcolumn">

					<g:layoutBody/>
				</div>
			</div>

			<div id="leftcolumn">
					<div id="left_menu">
						<div class="left_menu" id="user_menu">
							<sec:ifLoggedIn>
								        Welcome <sec:loggedInUserInfo field="username" /> </br>
								        <g:link controller="Logout">Logout</g:link>
								<!-- END #login -->
							</sec:ifLoggedIn>
							<sec:ifNotLoggedIn>
								<g:link controller="Login">login </g:link> || <a href="#"><g:link controller="Register">register </g:link> </a>
							</sec:ifNotLoggedIn>
						</div>
						
						
						
						<sec:ifLoggedIn>
						<div class="left_menu" id="mdr_menu">
							<ul>
								<li><strong>metadata registry</strong></li>
								<li><g:link controller="ConceptualDomain">conceptual domains </g:link></li>
								<li><g:link controller="ValueDomain">value domains </g:link></li>
								<li><g:link controller="DataElement">data elements </g:link></li>
								<li><g:link controller="DataType">data types </g:link></li>
								<li><g:link controller="UmlModel">uml </g:link></li>
								<li><g:link controller="Document">documents </g:link></li>
							</ul>
						</div>
						</sec:ifLoggedIn>
						
					</div>
			</div>

			<div id="rightcolumn">
				<div class="innertube"></div>
			</div>

			<div id="footer"><a href="#">MDC</a></div>

		</div>	
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
