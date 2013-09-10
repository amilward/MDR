<html>

<head>
	<meta name='layout' content='main'/>
	<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}"/>
	<title><g:message code="default.create.label" args="[entityName]"/></title>
	<parameter name="name" value=" CREATE ROLE " />
</head>

<body>

<div class="body">

	<g:form action="save" name='roleCreateForm'>
		<div class="dialog">

			<br/>

			<table>
				<tbody>

					<s2ui:textFieldRow name='authority' labelCode='role.authority.label' bean="${role}"
					                   size='50' labelCodeDefault='Authority' value="${role?.authority}"/>

					<tr><td>&nbsp;</td></tr>

					<tr class="prop">
						<td valign="top">
						<fieldset class="buttons">
							<g:submitButton elementId='create' form='roleCreateForm' name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
						</fieldset>
						</td>
					</tr>

				</tbody>
			</table>
		</div>

	</g:form>

</div>

<script>
$(document).ready(function() {
	$('#authority').focus();
});
</script>

</body>
</html>
