<html>

<head>
	<meta name='layout' content='main'/>
	<title>Providers</title>
	<parameter name="name" value=" PROVIDERS " />
</head>

<body>

<table>
	<tbody>
	<g:each var='provider' in='${providers}'>
	<tr><td>${provider.getClass().name}</td></tr>
	</g:each>
	</tbody>
</table>

</body>
</html>
