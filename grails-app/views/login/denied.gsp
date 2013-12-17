<head>
<meta name='layout' content='main' />
<title><g:message code="springSecurity.denied.title" /></title>
</head>

<body>
	<div class='body'>
		<sec:ifAllGranted roles="ROLE_PENDING">
			<br />
			<div>
			<h1>Thanks for registering with us</h1>
				<p>
					<strong>An administrator needs to activate the account before you can use the site.</strong>
				</p>
				<p>We will contact you once this has been done</p>
				<p>
					<em>You will need to log out and then log back in once the
						changes have been applied.</em>
				</p>
			</div>
		</sec:ifAllGranted>
		<sec:ifNotGranted roles="ROLE_PENDING">
			<div class='errors'>
				<g:message code="springSecurity.denied.message" />
			</div>
		</sec:ifNotGranted>
	</div>
</body>
