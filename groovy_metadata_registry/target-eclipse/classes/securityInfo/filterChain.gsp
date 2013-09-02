<html>

<head>
	<meta name='layout' content='main'/>
	<title>FILTER CHAIN</title>
	<parameter name="name" value=" FILTER CHAIN " />
</head>

<body>

<table>
	<thead>
	<tr>
		<th>URL Pattern</th>
		<th>Filters</th>
	</tr>
	</thead>
	<tbody>
	<g:each var='entry' in='${filterChainMap}'>
	<tr>
		<td>${entry.key}</td>
		<td>
			<g:each var='filter' in='${entry.value}'>
			${filter.getClass().name}<br/>
			</g:each>
		</td>
	</tr>
	</g:each>
	</tbody>
</table>

</body>
</html>
