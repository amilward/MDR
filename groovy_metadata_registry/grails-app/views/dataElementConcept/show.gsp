
<%@ page import="uk.co.mdc.model.DataElementConcept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementConcept.label', default: 'Section (Data Element Concept)')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dataElementConcept" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dataElementConcept" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dataElementConcept">
			
				<g:if test="${dataElementConceptInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="dataElementConcept.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${dataElementConceptInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementConceptInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="dataElementConcept.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${dataElementConceptInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementConceptInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="dataElementConcept.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="dataElementConcept" action="show" id="${dataElementConceptInstance?.parent?.id}">${dataElementConceptInstance?.parent?.name?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementConceptInstance?.subConcepts}">
					<h1>Sub Concepts:</h1>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
								</tr>
							</thead>
							<g:each var="dataConcept" in="${dataElementConceptInstance.subConcepts}">
								<tr>
									<td><g:link action="show" controller="DataElementConcept" id="${dataConcept?.id}">${dataConcept?.name} </g:link></td>
									<td>${dataConcept?.description}</td>
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
	
							</tr>
						</thead>
						<g:each var="dataElement" in="${dataElementConceptInstance?.dataElements}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
							</tr>
						</g:each>
					</table>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dataElementConceptInstance?.id}" />
					<g:link class="edit" action="edit" id="${dataElementConceptInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
