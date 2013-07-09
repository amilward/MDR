<%@ page import="uk.co.mdc.model.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-dataElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-dataElement" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dataElementInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dataElementInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${dataElementInstance?.id}" />
				<g:hiddenField name="version" value="${dataElementInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
			
				<g:if test="${dataElementInstance?.dataElementValueDomains}">
					<h1>Associated Value Domains:</h1>
						<table>
							<thead>
											<tr>
												<th>Name</th>
												<th>Reference ID</th>
												<th>Description</th>
												<th>Data Type</th>
												<th>Unit of Measure</th>
												<th>Regex definition</th>
												<th>&nbsp;</th>
					
											</tr>
										</thead>
										<g:each var="valueDomain" in="${dataElementInstance.dataElementValueDomains()}">
											<tr>
												<td>${valueDomain?.name}</td>
												<td>${valueDomain?.refId}</td>
												<td>${valueDomain?.description}</td>
												<td>${valueDomain?.dataType} </td>
												<td>${valueDomain?.unitOfMeasure} </td>
												<td>${valueDomain?.regexDef} </td>
												<td><g:link params="[valueDomainId: "${valueDomain?.id}", dataElementId: "${dataElementInstance?.id}"]" action="removeValueDomain" controller="DataElement">Remove</g:link></td>
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
