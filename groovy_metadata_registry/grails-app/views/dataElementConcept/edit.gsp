<%@ page import="uk.co.mdc.model.DataElementConcept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementConcept.label', default: 'DataElementConcept')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-dataElementConcept" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-dataElementConcept" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dataElementConceptInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dataElementConceptInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${dataElementConceptInstance?.id}" />
				<g:hiddenField name="version" value="${dataElementConceptInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<g:if test="${dataElementConceptInstance?.subConcepts}">
					<h1>Sub Concepts:</h1>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<g:each var="dataConcept" in="${dataElementConceptInstance.subConcepts}">
								<tr>
									<td><g:link action="show" controller="DataElementConcept" id="${dataConcept?.id}">${dataConcept?.name} </g:link></td>
									<td>${dataConcept?.description}</td>
									<td><g:link params="[subConceptId: "${dataConcept?.id}", conceptId: "${dataElementConceptInstance?.id}"]" action="removeSubConcept" controller="DataElementConcept">Remove</g:link></td>		
								</tr>
							</g:each>
					</table>
				</g:if>
				
				<g:if test="${dataElementConceptInstance?.dataElements}">
					<h1>Data Elements:</h1>
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
						<g:each var="dataElement" in="${dataElementConceptInstance?.dataElements}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td><g:link params="[dataElementId: "${dataElement?.id}", conceptId: "${dataElementConceptInstance?.id}"]" action="removeDataElement" controller="DataElementConcept">Remove</g:link></td>		
							
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
