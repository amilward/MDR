<%@ page import="uk.co.mdc.model.ConceptualDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conceptualDomain.label', default: 'ConceptualDomain')}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-duallistbox.css')}" type="text/css">
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<parameter name="name" value=" EDIT CONCEPTUAL DOMAIN - ${conceptualDomainInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'conceptualDomain']">
				<g:hiddenField name="id" value="${conceptualDomainInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav nav-tabs">
						  		<li><g:link action="show" id="${conceptualDomainInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li class="active"><g:link action="edit" id="${conceptualDomainInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${conceptualDomainInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="updateForm()">Update</a></li>
							    <li><a href="#" onclick="deleteItem('${conceptualDomainInstance?.name}')">Delete</a></li>
								<li><g:link action="list" ><g:message code="default.button.list.label" default="List" /></g:link></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
		<div id="edit-conceptualDomain" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${conceptualDomainInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${conceptualDomainInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form id="updateForm" method="post" url="[action:'update',controller:'conceptualDomain']">
				<g:hiddenField name="id" value="${conceptualDomainInstance?.id}" />
				<g:hiddenField name="version" value="${conceptualDomainInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>

				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		</div>
	</body>
</html>
