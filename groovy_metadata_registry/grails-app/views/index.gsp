<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to the MDC Metadata Registry</title>
		<parameter name="name" value="Dashboard" />
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="page-body" role="main">
			<div id="welcome" class="innertube">welcome to the metadata registry</div>
					<sec:ifLoggedIn>
							<p>Glad you're in. Now the fun starts..........</p>
					</sec:ifLoggedIn>
					<sec:ifNotLoggedIn>
							<p>Please log in or register</p>
					</sec:ifNotLoggedIn>
		</div>
	</body>
</html>
