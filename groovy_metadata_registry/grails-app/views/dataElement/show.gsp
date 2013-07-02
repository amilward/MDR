
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
			
				<g:if test="${dataElementInstance?.parentId}">
				<li class="fieldcontain">
					<span id="parentId-label" class="property-label"><g:message code="dataElement.parentId.label" default="Parent Id" /></span>
					
						<span class="property-value" aria-labelledby="parentId-label"><g:fieldValue bean="${dataElementInstance}" field="parentId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="dataElement.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${dataElementInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.subElements}">
				<li class="fieldcontain">
					<span id="subElements-label" class="property-label"><g:message code="dataElement.subElements.label" default="Sub Elements" /></span>
					
						<g:each in="${dataElementInstance.subElements}" var="s">
						<span class="property-value" aria-labelledby="subElements-label"><g:link controller="dataElement" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.vdId}">
				<li class="fieldcontain">
					<span id="vdId-label" class="property-label"><g:message code="dataElement.vdId.label" default="Vd Id" /></span>
					
						<span class="property-value" aria-labelledby="vdId-label"><g:fieldValue bean="${dataElementInstance}" field="vdId"/></span>
					
				</li>
				</g:if>
			
			</ol>
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
