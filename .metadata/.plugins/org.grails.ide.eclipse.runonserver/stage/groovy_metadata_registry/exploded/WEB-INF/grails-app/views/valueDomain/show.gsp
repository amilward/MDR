
<%@ page import="uk.co.mdc.model.ValueDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'valueDomain.label', default: 'ValueDomain')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-valueDomain" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-valueDomain" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list valueDomain">
			
				<g:if test="${valueDomainInstance?.conceptualDomain}">
				<li class="fieldcontain">
					<span id="conceptualDomain-label" class="property-label"><g:message code="valueDomain.conceptualDomain.label" default="Conceptual Domain" /></span>
					
						<span class="property-value" aria-labelledby="conceptualDomain-label"><g:link controller="conceptualDomain" action="show" id="${valueDomainInstance?.conceptualDomain?.id}">${valueDomainInstance?.conceptualDomain?.name?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				
				<g:if test="${valueDomainInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="valueDomain.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${valueDomainInstance}" field="name"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${valueDomainInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="valueDomain.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${valueDomainInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.dataType}">
				<li class="fieldcontain">
					<span id="dataType-label" class="property-label"><g:message code="valueDomain.dataType.label" default="Data Type" /></span>
					
						<span class="property-value" aria-labelledby="dataType-label"><g:fieldValue bean="${valueDomainInstance}" field="dataType"/></span>
					
				</li>
				</g:if>
			
				
				<g:if test="${valueDomainInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="valueDomain.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${valueDomainInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.regexDef}">
				<li class="fieldcontain">
					<span id="regexDef-label" class="property-label"><g:message code="valueDomain.regexDef.label" default="Regex Def" /></span>
					
						<span class="property-value" aria-labelledby="regexDef-label"><g:fieldValue bean="${valueDomainInstance}" field="regexDef"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.unitOfMeasure}">
				<li class="fieldcontain">
					<span id="unitOfMeasure-label" class="property-label"><g:message code="valueDomain.unitOfMeasure.label" default="Unit Of Measure" /></span>
					
						<span class="property-value" aria-labelledby="unitOfMeasure-label"><g:fieldValue bean="${valueDomainInstance}" field="unitOfMeasure"/></span>
					
				</li>
				</g:if>
			
			</ol>
			
			
			<g:if test="${valueDomainInstance?.dataElementValueDomains}">
			
				<h1>Associated Data Elements:</h1>
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
						<g:each var="dataElement" in="${valueDomainInstance.dataElementValueDomains()}">
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
			
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${valueDomainInstance?.id}" />
					<g:link class="edit" action="edit" id="${valueDomainInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
