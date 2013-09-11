
<%@ page import="uk.co.mdc.forms.InputField" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'inputField.label', default: 'InputField')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-inputField" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-inputField" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list inputField">
			
				<g:if test="${inputFieldInstance?.defaultValue}">
				<li class="fieldcontain">
					<span id="defaultValue-label" class="property-label"><g:message code="inputField.defaultValue.label" default="Default Value" /></span>
					
						<span class="property-value" aria-labelledby="defaultValue-label"><g:fieldValue bean="${inputFieldInstance}" field="defaultValue"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.placeholder}">
				<li class="fieldcontain">
					<span id="placeholder-label" class="property-label"><g:message code="inputField.placeholder.label" default="Placeholder" /></span>
					
						<span class="property-value" aria-labelledby="placeholder-label"><g:fieldValue bean="${inputFieldInstance}" field="placeholder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.maxCharacters}">
				<li class="fieldcontain">
					<span id="maxCharacters-label" class="property-label"><g:message code="inputField.maxCharacters.label" default="Max Characters" /></span>
					
						<span class="property-value" aria-labelledby="maxCharacters-label"><g:fieldValue bean="${inputFieldInstance}" field="maxCharacters"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.unitOfMeasure}">
				<li class="fieldcontain">
					<span id="unitOfMeasure-label" class="property-label"><g:message code="inputField.unitOfMeasure.label" default="Unit Of Measure" /></span>
					
						<span class="property-value" aria-labelledby="unitOfMeasure-label"><g:fieldValue bean="${inputFieldInstance}" field="unitOfMeasure"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.dataType}">
				<li class="fieldcontain">
					<span id="dataType-label" class="property-label"><g:message code="inputField.dataType.label" default="Data Type" /></span>
					
						<span class="property-value" aria-labelledby="dataType-label"><g:link controller="dataType" action="show" id="${inputFieldInstance?.dataType?.id}">${inputFieldInstance?.dataType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.format}">
				<li class="fieldcontain">
					<span id="format-label" class="property-label"><g:message code="inputField.format.label" default="Format" /></span>
					
						<span class="property-value" aria-labelledby="format-label"><g:fieldValue bean="${inputFieldInstance}" field="format"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.options}">
				<li class="fieldcontain">
					<span id="options-label" class="property-label"><g:message code="inputField.options.label" default="Options" /></span>
					
						<span class="property-value" aria-labelledby="options-label"><g:fieldValue bean="${inputFieldInstance}" field="options"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.instructions}">
				<li class="fieldcontain">
					<span id="instructions-label" class="property-label"><g:message code="inputField.instructions.label" default="Instructions" /></span>
					
						<span class="property-value" aria-labelledby="instructions-label"><g:fieldValue bean="${inputFieldInstance}" field="instructions"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inputFieldInstance?.renderType}">
				<li class="fieldcontain">
					<span id="renderType-label" class="property-label"><g:message code="inputField.renderType.label" default="Render Type" /></span>
					
						<span class="property-value" aria-labelledby="renderType-label"><g:fieldValue bean="${inputFieldInstance}" field="renderType"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${inputFieldInstance?.id}" />
					<g:link class="edit" action="edit" id="${inputFieldInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
