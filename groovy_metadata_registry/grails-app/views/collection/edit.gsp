<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-duallistbox.css')}" type="text/css">
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<parameter name="name" value=" EDIT COLLECTION - ${collectionInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'collection']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li><g:link action="show" id="${collectionInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li class="active"><g:link action="edit" id="${collectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${collectionInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="updateForm()">Update</a></li>
							    <li><a href="#" onclick="deleteItem('${collectionInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
		<div id="edit-collection" class="content scaffold-edit" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${collectionInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${collectionInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form id="updateForm" method="post" url="[action:'update',controller:'collection']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
				<g:hiddenField name="version" value="${collectionInstance?.version}" />
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
