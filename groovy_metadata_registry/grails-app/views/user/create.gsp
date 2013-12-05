<html>

<head>
	<meta name='layout' content='main_no-sidebar'/>
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
	<title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<h3><g:message code="default.create.label" args="[entityName]"/></h3>

<g:form action="save" name='userCreateForm'>

<%
def tabData = []
tabData << [name: 'userinfo', icon: 'icon_user', messageCode: 'spring.security.ui.user.info']
tabData << [name: 'roles',    icon: 'icon_role', messageCode: 'spring.security.ui.user.roles']
%>

<div>
  <div>
		<table>
		<tbody>

      <s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${user}"
                            labelCodeDefault='Username' value="${user?.username}"/>
      <s2ui:textFieldRow name='firstName' labelCode='user.firstName.label' bean="${user}"
                            labelCodeDefault='First name' value="${user?.firstName}"/>
      <s2ui:textFieldRow name='lastName' labelCode='user.lastName.label' bean="${user}"
                            labelCodeDefault='Last name' value="${user?.lastName}"/>                      
      <s2ui:textFieldRow name='email' labelCode='user.email.label' bean="${user}"
                            labelCodeDefault='Email' value="${user?.email}"/>                      
                            

			<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${user}"
                                labelCodeDefault='Password' value="${user?.password}"/>

			<s2ui:checkboxRow name='enabled' labelCode='user.enabled.label' bean="${user}"
                           labelCodeDefault='Enabled' value="${user?.enabled}"/>

		</tbody>
		</table>
  </div>
  <div>
  <h2>Roles</h2>
		<g:each var="auth" in="${authorityList}">

			<g:if test="${ auth.authority != "ROLE_PENDING" }">
			<div>
			   <g:checkBox name="${auth.authority}" />
			   <g:link controller='role' action='edit' id='${auth.id}'>${auth.authority.encodeAsHTML()}</g:link>
        </div>
			</g:if>
		</g:each>
  </div>
</div>

<div style='float:left; margin-top: 10px; '>
<s2ui:submitButton elementId='create' form='userCreateForm' messageCode='default.button.create.label'/>
</div>

</g:form>

<script>
$(document).ready(function() {
	$('#username').focus();
	<s2ui:initCheckboxes/>
});
</script>

</body>
</html>
