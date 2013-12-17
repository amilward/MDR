<head>
  <meta name="layout" content="register">
</head>
<body>


         

<s2ui:form width='650' height='350' elementId='loginFormContainer'
           titleCode='spring.security.ui.register.description' center='false'>

<g:form action='register' name='registerForm'>

	<g:if test='${emailSent}'>
	<br/>
	<g:message code='spring.security.ui.register.sent'/>
	<br/>
	<a href="${createLink(uri: '/')}">Return to the homepage</a>
	</g:if>
	<g:else>

	<br/>

	<table id="s2ui_registration">
	<tbody>
            
		<s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                         size='40' labelCodeDefault='Username' value="${command.username}"/>

		<s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
		                   size='40' labelCode='user.email.label' labelCodeDefault='E-mail'/>
		                   
      <s2ui:textFieldRow name='firstName' labelCode='user.firstName.label' bean="${command}"
                            labelCodeDefault='First name' value="${command?.firstName}"/>
      <s2ui:textFieldRow name='lastName' labelCode='user.lastName.label' bean="${command}"
                            labelCodeDefault='Last name' value="${command?.lastName}"/>                   
                            
		<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                             size='40' labelCodeDefault='Password' value="${command.password}"/>

		<s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                             size='40' labelCodeDefault='Password (again)' value="${command.password2}"/>

	</tbody>
	</table>
	<g:submitButton name="Create your account" elementId='create' form='registerForm' messageCode='spring.security.ui.register.submit'/>

	</g:else>

</g:form>

</s2ui:form>


<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>