<html>

<head>
	<meta name='layout' content='main'/>
	<title><g:message code='spring.security.ui.role.search'/></title>
	<parameter name="name" value=" SEARCH ROLES " />
</head>

<body>

<div>

	<g:form action='roleSearch' name='roleSearchForm'>

		<br/>

		<table>
			<tbody>
			<tr>
				<td><g:message code='role.authority.label' default='Authority'/>:</td>
				<td><g:textField name='authority' class='textField' size='50' maxlength='255' autocomplete='off' value='${authority}'/></td>
			</tr>
			<tr><td colspan='2'>&nbsp;</td></tr>
			<tr>
				<td colspan='2'>
				
				<fieldset class="buttons">
				<g:submitButton elementId='search' form='roleSearchForm' name="search" class="btn" value="search" />
				</fieldset>
				</td>
			</tr>
			</tbody>
		</table>
	</g:form>


	<g:if test='${searched}'>

<%
def queryParams = [authority: authority]
%>

	<div class="list">
	<table>
		<thead>
		<tr>
			<g:sortableColumn property="authority" title="${message(code: 'role.authority.label', default: 'Authority')}" params="${queryParams}"/>
		</tr>
		</thead>

		<tbody>
		<g:each in="${results}" status="i" var="role">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td><g:link action="edit" id="${role.id}">${fieldValue(bean: role, field: "authority")}</g:link></td>
		</tr>
		</g:each>
		</tbody>
	</table>
	</div>

	<div class="paginateButtons">
		<g:paginate total="${totalCount}" params="${queryParams}" />
	</div>

	<div style="text-align:center">
		<s2ui:paginationSummary total="${totalCount}"/>
	</div>

	</g:if>

</div>

<script>
$(document).ready(function() {
	$("#authority").focus().autocomplete({
		minLength: 2,
		cache: false,
		source: "${createLink(action: 'ajaxRoleSearch')}"
	});
});
</script>

</body>
</html>
