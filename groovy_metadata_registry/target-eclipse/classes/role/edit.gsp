<html>

<head>
	<meta name='layout' content='main'/>
	<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}"/>
	<title><g:message code="default.edit.label" args="[entityName]"/></title>
	<parameter name="name" value=" EDIT ROLE - ${role?.authority}" />
</head>

<body>

<g:form action="update" name='roleEditForm'>
<g:hiddenField name="id" value="${role?.id}"/>
<g:hiddenField name="version" value="${role?.version}"/>

<%
def tabData = []
tabData << [name: 'roleinfo', icon: 'icon_role',  messageCode: 'spring.security.ui.role.info']
tabData << [name: 'users',    icon: 'icon_users', messageCode: 'spring.security.ui.role.users']
%>

<s2ui:tabs elementId='tabs' height='150' data="${tabData}">

	<s2ui:tab name='roleinfo' height='150'>
		<table>
		<tbody>
			<s2ui:textFieldRow name='authority' labelCode='role.authority.label' bean="${role}"
                            labelCodeDefault='Authority' value="${role?.authority}"/>
		</tbody>
		</table>
	</s2ui:tab>

	<s2ui:tab name='users' height='150'>
		<g:if test='${users.empty}'>
		<g:message code="spring.security.ui.role_no_users"/>
		</g:if>
		<g:each var="u" in="${users}">
			<g:link controller='user' action='edit' id='${u.id}'>${u.username.encodeAsHTML()}</g:link><br/>
		</g:each>
	</s2ui:tab>

</s2ui:tabs>

<div style='float:left; margin-top: 10px;'>
<fieldset class="buttons">
	<g:submitButton elementId='update' form='roleEditForm' name="update" class="update" value="update" />
	<g:if test='${role}'>
		<s2ui:deleteButton />
	</g:if>
</fieldset>


</div>

</g:form>

<g:if test='${role}'>
<s2ui:deleteButtonForm instanceId='${role.id}'/>
</g:if>

</body>
</html>
