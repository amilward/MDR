
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
			
				<g:if test="${conceptualDomainInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="conceptualDomain.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${conceptualDomainInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conceptualDomainInstance?.refid}">
				<li class="fieldcontain">
					<span id="refid-label" class="property-label"><g:message code="conceptualDomain.refid.label" default="Refid" /></span>
					
						<span class="property-value" aria-labelledby="refid-label"><g:fieldValue bean="${conceptualDomainInstance}" field="refid"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conceptualDomainInstance?.valueDomains}">
				<li class="fieldcontain">
					<span id="valueDomains-label" class="property-label"><g:message code="conceptualDomain.valueDomains.label" default="Value Domains" /></span>
					
						<g:each in="${conceptualDomainInstance.valueDomains}" var="v">
						<span class="property-value" aria-labelledby="valueDomains-label"><g:link controller="valueDomain" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
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
