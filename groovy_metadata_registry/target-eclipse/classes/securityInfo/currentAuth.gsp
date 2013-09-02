<html>

<head>
	<meta name='layout' content='main'/>
	<title>Current Auth</title>
	<parameter name="name" value=" CURRENT AUTHORISATION " />
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
	<tr>
		<td>Authorities</td>
		<td>${auth.authorities}</td>
	</tr>
	<tr>
		<td>Credentials</td>
		<td>${auth.credentials}</td>
	</tr>
	<tr>
		<td>Details</td>
		<td>${auth.details}</td>
	</tr>
	<tr>
		<td>Principal</td>
		<td>${auth.principal}</td>
	</tr>
	<tr>
		<td>Name</td>
		<td>${auth.name}</td>
	</tr>
	</tbody>
</table>

</body>
</html>
