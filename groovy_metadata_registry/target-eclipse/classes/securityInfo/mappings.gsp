<html>

<head>
	<meta name='layout' content='main'/>
	<title>Mappings</title>
	<parameter name="name" value=" MAPPINGS " />
</head>

<body>

<table>
	<thead>
	<tr>
		<th>Name</th>
		<th>Value</th>
	</tr>
	</thead>
	<tbody>
	<g:each var='entry' in='${configAttributeMap}'>
	<tr>
		<td>${entry.key}</td>
		<td>${entry.value}</td>
	</tr>
	</g:each>
	</tbody>
</table>

</body>
</html>
