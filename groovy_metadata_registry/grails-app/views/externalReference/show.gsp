
<%@ page import="uk.co.mdc.model.ExternalReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalReference.label', default: 'ExternalReference')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-externalReference" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-externalReference" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list externalReference">
			
				<g:if test="${externalReferenceInstance?.attributes}">
				<li class="fieldcontain">
					<span id="attributes-label" class="property-label"><g:message code="externalReference.attributes.label" default="Attributes" /></span>
					
						<span class="property-value" aria-labelledby="attributes-label"><g:fieldValue bean="${externalReferenceInstance}" field="attributes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${externalReferenceInstance?.externalIdentifier}">
				<li class="fieldcontain">
					<span id="externalIdentifier-label" class="property-label"><g:message code="externalReference.externalIdentifier.label" default="External Identifier" /></span>
					
						<span class="property-value" aria-labelledby="externalIdentifier-label"><g:fieldValue bean="${externalReferenceInstance}" field="externalIdentifier"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${externalReferenceInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="externalReference.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${externalReferenceInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${externalReferenceInstance?.url}">
				<li class="fieldcontain">
					<span id="url-label" class="property-label"><g:message code="externalReference.url.label" default="Url" /></span>
					
						<span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${externalReferenceInstance}" field="url"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${externalReferenceInstance?.id}" />
					<g:link class="edit" action="edit" id="${externalReferenceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
