
<%@ page import="uk.co.mdc.pathways.Link" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'link.label', default: 'Link')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-link" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-link" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list link">
			
				<g:if test="${linkInstance?.mandatoryInputs}">
				<li class="fieldcontain">
					<span id="mandatoryInputs-label" class="property-label"><g:message code="link.mandatoryInputs.label" default="Mandatory Inputs" /></span>
					
						<g:each in="${linkInstance.mandatoryInputs}" var="m">
						<span class="property-value" aria-labelledby="mandatoryInputs-label"><g:link controller="collection" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.mandatoryOutputs}">
				<li class="fieldcontain">
					<span id="mandatoryOutputs-label" class="property-label"><g:message code="link.mandatoryOutputs.label" default="Mandatory Outputs" /></span>
					
						<g:each in="${linkInstance.mandatoryOutputs}" var="m">
						<span class="property-value" aria-labelledby="mandatoryOutputs-label"><g:link controller="collection" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.optionalInputs}">
				<li class="fieldcontain">
					<span id="optionalInputs-label" class="property-label"><g:message code="link.optionalInputs.label" default="Optional Inputs" /></span>
					
						<g:each in="${linkInstance.optionalInputs}" var="o">
						<span class="property-value" aria-labelledby="optionalInputs-label"><g:link controller="collection" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.optionalOutputs}">
				<li class="fieldcontain">
					<span id="optionalOutputs-label" class="property-label"><g:message code="link.optionalOutputs.label" default="Optional Outputs" /></span>
					
						<g:each in="${linkInstance.optionalOutputs}" var="o">
						<span class="property-value" aria-labelledby="optionalOutputs-label"><g:link controller="collection" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="link.refId.label" default="Ref Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${linkInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="link.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${linkInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.peCollection}">
				<li class="fieldcontain">
					<span id="peCollection-label" class="property-label"><g:message code="link.peCollection.label" default="Pe Collection" /></span>
					
						<span class="property-value" aria-labelledby="peCollection-label"><g:link controller="collection" action="show" id="${linkInstance?.peCollection?.id}">${linkInstance?.peCollection?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="link.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${linkInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.source}">
				<li class="fieldcontain">
					<span id="source-label" class="property-label"><g:message code="link.source.label" default="Source" /></span>
					
						<span class="property-value" aria-labelledby="source-label"><g:link controller="node" action="show" id="${linkInstance?.source?.id}">${linkInstance?.source?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${linkInstance?.target}">
				<li class="fieldcontain">
					<span id="target-label" class="property-label"><g:message code="link.target.label" default="Target" /></span>
					
						<span class="property-value" aria-labelledby="target-label"><g:link controller="node" action="show" id="${linkInstance?.target?.id}">${linkInstance?.target?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${linkInstance?.id}" />
					<g:link class="edit" action="edit" id="${linkInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
