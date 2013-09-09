<html>

<head>
	<meta name='layout' content='main'/>
	<g:set var="entityName" value="${message(code: 'aclClass.label', default: 'AclClass')}"/>
	<title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">

	<g:form action="save" name='aclClassCreateForm'>
		<div class="dialog">

			<br/>

			<table>
				<tbody>

					<s2ui:textFieldRow name='className' labelCode='aclClass.className.label' bean="${aclClass}"
					                   labelCodeDefault='Class Name' size='60' value="${aclClass?.className}"/>

					<tr><td>&nbsp;</td></tr>

					<tr class="prop">
						<td valign="top">
							<s2ui:submitButton elementId='create' form='aclClassCreateForm' messageCode='default.button.create.label'/>
						</td>
					</tr>

				</tbody>
			</table>
		</div>

	</g:form>
</div>

<script>
$(document).ready(function() {
	$('#className').focus();

	$("#resizable").resizable({
		minHeight: 150,
		minWidth: 200
	});
});
</script>

</body>
</html>
