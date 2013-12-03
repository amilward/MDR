<html>

<head>
<meta name="layout" content="main_no-sidebar">
<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
<title>Activate pending users</title>
</head>

<body>

	<h3>
		Activate pending users
	</h3>


		<table>
			<thead>
				<tr>
					<td><strong>Username</strong></td>
					<td></td>
				</tr>
			</thead>

			<g:if test='${pendingUsers == null || pendingUsers.empty}'>
				<tr><td><g:message code="spring.security.ui.role_no_users" /></td></tr>
			</g:if>
			<g:each var="u" in="${pendingUsers}">
				<tr>
					<td>
						${u.username.encodeAsHTML()}
					</td>
					<td><g:link controller='user' action='activate' id='${u.id}'>Activate</g:link></td>
			</g:each>
		</table>

</body>
</html>
