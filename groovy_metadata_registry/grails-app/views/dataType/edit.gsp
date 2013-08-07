<%@ page import="uk.co.mdc.model.DataType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataType.label', default: 'DataType')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-dataType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-dataType" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dataTypeInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dataTypeInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${dataTypeInstance?.id}" />
				<g:hiddenField name="version" value="${dataTypeInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<g:if test="${dataTypeInstance?.enumerations}">
				
					<h1>Enumerations</h1>
					
					<table>
							<thead>
								<tr>
									<th>Value</th>
									<th>Description</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<g:each var="enumeratedValue" in="${dataTypeInstance.enumerations}">
								<tr>
									<td>${enumeratedValue?.key}</td>
									<td>${enumeratedValue?.value}</td>
									<td><g:link params="[enumeratedValue: "${enumeratedValue?.key}",dataTypeId: "${dataTypeInstance?.id}"]" action="removeEnumeratedValue" controller="DataType">Remove</g:link></td>		
								</tr>
							</g:each>
					</table>
				</g:if>
				
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
