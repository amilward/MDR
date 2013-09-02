<html>

<head>
	<meta name='layout' content='main'/>
	<title>LOGOUT HANDLERS</title>
	<parameter name="name" value=" LOGOUT HANDLERS " />
</head>

<body>

<table>
	<tbody>
	<g:each var='handler' in='${handlers}'>
	<tr><td>${handler.getClass().name}</td></tr>
	</g:each>
	</tbody>
</table>

</body>
</html>
