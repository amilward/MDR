
<%@ page import="uk.co.mdc.model.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dataElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dataElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dataElement">
			
				<g:if test="${dataElementInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="dataElement.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${dataElementInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			
				<g:if test="${dataElementInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="dataElement.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${dataElementInstance}" field="refId"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${dataElementInstance?.dataElementConcept}">
				<li class="fieldcontain">
					<span id="dataElementConcept-label" class="property-label"><g:message code="dataElement.dataElementConcept.label" default="Data Element Concept" /></span>
					
						<span class="property-value" aria-labelledby="dataElementConcept-label"><g:link controller="dataElementConcept" action="show" id="${dataElementInstance?.dataElementConcept?.id}">${dataElementInstance?.dataElementConcept?.name?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="dataElement.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="dataElement" action="show" id="${dataElementInstance?.parent?.id}">${dataElementInstance?.parent?.name?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.definition}">
				<li class="fieldcontain">
					<span id="definition-label" class="property-label"><g:message code="dataElement.definition.label" default="Definition" /></span>
					
						<span class="property-value" aria-labelledby="definition-label"><g:fieldValue bean="${dataElementInstance}" field="definition"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="dataElement.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${dataElementInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				
			</ol>
			
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
		
								</tr>
							</thead>
							<g:each var="valueDomain" in="${dataElementInstance.dataElementValueDomains()}">
								<tr>
									<td><g:link action="show" controller="ValueDomain" id="${valueDomain?.id}">${valueDomain?.name} </g:link></td>
									<td>${valueDomain?.refId}</td>
									<td>${valueDomain?.description}</td>
									<td><g:link action="show" controller="DataType" id="${valueDomain?.dataType?.id}">${valueDomain?.dataType?.name}</g:link></td>
									<td>${valueDomain?.unitOfMeasure} </td>
									<td>${valueDomain?.regexDef} </td>
								</tr>
							</g:each>
					</table>
		
			</g:if>

				
			<g:if test="${dataElementInstance?.subElements}">
					<h1>Sub Elements:</h1>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Reference ID</th>
									<th>Description</th>
									<th>Definition</th>
								</tr>
							</thead>
							<g:each var="dataElement" in="${dataElementInstance.subElements}">
								<tr>
									<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
									<td>${dataElement?.refId}</td>
									<td>${dataElement?.description}</td>
									<td>${dataElement?.definition} </td>
								</tr>
							</g:each>
					</table>
				</g:if>	
				
				
			<g:if test="${dataElementInstance?.externalSynonyms}">
					<h1>External Synonyms:</h1>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>URL</th>
									<th>Attributes</th>
								</tr>
							</thead>
							<g:each var="externalSynonym" in="${dataElementInstance.externalSynonyms}">
								<tr>
									<td><g:link action="show" controller="ExternalSynonym" id="${externalSynonym?.id}">${externalSynonym?.name} </g:link></td>
									<td>${externalSynonym?.url}</td>
									<td>${externalSynonym?.attributes} </td>
								</tr>
							</g:each>
					</table>
				</g:if>	
				
				
			
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dataElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${dataElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
