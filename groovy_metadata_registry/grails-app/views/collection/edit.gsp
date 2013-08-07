<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-collection" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-collection" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
			<g:form method="post" >
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
				<g:hiddenField name="version" value="${collectionInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				
				
				<g:if test="${collectionInstance?.dataElementCollections}">
			
				<h1>Data Elements in collection:</h1>
				<table>
						<thead>
							<tr>
								<th>Name</th>
								<th>Reference ID</th>
								<th>Description</th>
								<th>Definition</th>
								<th>Parent</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<g:each var="dataElement" in="${collectionInstance.dataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td><g:link params="[dataElementId: "${dataElement?.id}",collectionId: "${collectionInstance?.id}"]" action="removeDataElement" controller="collection">Remove</g:link></td>
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
