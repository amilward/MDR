
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
			
				<g:if test="${valueDomainInstance?.dataElements}">
				<li class="fieldcontain">
					<span id="dataElements-label" class="property-label"><g:message code="valueDomain.dataElements.label" default="Data Elements" /></span>
					
						<g:each in="${valueDomainInstance.dataElements}" var="d">
						<span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.datatype}">
				<li class="fieldcontain">
					<span id="datatype-label" class="property-label"><g:message code="valueDomain.datatype.label" default="Datatype" /></span>
					
						<span class="property-value" aria-labelledby="datatype-label"><g:fieldValue bean="${valueDomainInstance}" field="datatype"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="valueDomain.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${valueDomainInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${valueDomainInstance?.refid}">
				<li class="fieldcontain">
					<span id="refid-label" class="property-label"><g:message code="valueDomain.refid.label" default="Refid" /></span>
					
						<span class="property-value" aria-labelledby="refid-label"><g:fieldValue bean="${valueDomainInstance}" field="refid"/></span>
					
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
