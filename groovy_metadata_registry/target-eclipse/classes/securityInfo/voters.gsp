<html>

<head>
	<meta name='layout' content='main'/>
	<title>Voters</title>
	<parameter name="name" value=" VOTERS " />
</head>

<body>

<table>
	<tbody>
	<g:each var='voter' in='${voters}'>
	<tr><td>${voter.getClass().name}</td></tr>
	</g:each>
	</tbody>
</table>
</body>

</html>
