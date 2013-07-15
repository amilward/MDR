
<%@ page import="uk.co.mdc.model.ConceptualDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conceptualDomain.label', default: 'ConceptualDomain')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-conceptualDomain" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-conceptualDomain" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list conceptualDomain">
			
				<g:if test="${conceptualDomainInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="conceptualDomain.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${conceptualDomainInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conceptualDomainInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="conceptualDomain.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${conceptualDomainInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conceptualDomainInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="conceptualDomain.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${conceptualDomainInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conceptualDomainInstance?.valueDomains}">
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
										<g:each var="valueDomain" in="${conceptualDomainInstance?.valueDomains?}">
											<tr>
												<td><g:link action="show" controller="ValueDomain" id="${valueDomain?.id}">${valueDomain?.name} </g:link></td>
												<td>${valueDomain?.refId}</td>
												<td>${valueDomain?.description}</td>
												<td>${valueDomain?.dataType} </td>
												<td>${valueDomain?.unitOfMeasure} </td>
												<td>${valueDomain?.regexDef} </td>
											</tr>
										</g:each>
					</table>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${conceptualDomainInstance?.id}" />
					<g:link class="edit" action="edit" id="${conceptualDomainInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
