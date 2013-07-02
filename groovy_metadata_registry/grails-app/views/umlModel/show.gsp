
<%@ page import="uk.co.mdc.model.UmlModel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'umlModel.label', default: 'UmlModel')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-umlModel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-umlModel" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list umlModel">
			
				<g:if test="${umlModelInstance?.conceptId}">
				<li class="fieldcontain">
					<span id="conceptId-label" class="property-label"><g:message code="umlModel.conceptId.label" default="Concept Id" /></span>
					
						<span class="property-value" aria-labelledby="conceptId-label"><g:fieldValue bean="${umlModelInstance}" field="conceptId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${umlModelInstance?.dataElements}">
				<li class="fieldcontain">
					<span id="dataElements-label" class="property-label"><g:message code="umlModel.dataElements.label" default="Data Elements" /></span>
					
						<g:each in="${umlModelInstance.dataElements}" var="d">
						<span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${umlModelInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="umlModel.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${umlModelInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${umlModelInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="umlModel.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${umlModelInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${umlModelInstance?.valueDomains}">
				<li class="fieldcontain">
					<span id="valueDomains-label" class="property-label"><g:message code="umlModel.valueDomains.label" default="Value Domains" /></span>
					
						<g:each in="${umlModelInstance.valueDomains}" var="v">
						<span class="property-value" aria-labelledby="valueDomains-label"><g:link controller="valueDomain" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${umlModelInstance?.id}" />
					<g:link class="edit" action="edit" id="${umlModelInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
