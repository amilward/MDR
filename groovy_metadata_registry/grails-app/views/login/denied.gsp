<head>
<meta name='layout' content='main' />
<title><g:message code="springSecurity.denied.title" /></title>
</head>

<body>
<div class='body'>
	<div class='errors'><g:message code="springSecurity.denied.message" /></div>
	
	<sec:ifAllGranted roles="ROLE_PENDING">
	<br/>
	<div>
    <p><strong>Thanks for registering with us . An administrator needs to activate the account before you can use the site.</strong></p> 
    <p>We will contact you once this has been done</p>
  </div>
  </sec:ifAllGranted>
</div>
</body>
