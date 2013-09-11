
<%@ page import="uk.co.mdc.forms.QuestionElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'questionElement.label', default: 'QuestionElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-questionElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-questionElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list questionElement">
			
				<g:if test="${questionElementInstance?.label}">
				<li class="fieldcontain">
					<span id="label-label" class="property-label"><g:message code="questionElement.label.label" default="Label" /></span>
					
						<span class="property-value" aria-labelledby="label-label"><g:fieldValue bean="${questionElementInstance}" field="label"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.style}">
				<li class="fieldcontain">
					<span id="style-label" class="property-label"><g:message code="questionElement.style.label" default="Style" /></span>
					
						<span class="property-value" aria-labelledby="style-label"><g:fieldValue bean="${questionElementInstance}" field="style"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.designOrder}">
				<li class="fieldcontain">
					<span id="designOrder-label" class="property-label"><g:message code="questionElement.designOrder.label" default="Design Order" /></span>
					
						<span class="property-value" aria-labelledby="designOrder-label"><g:fieldValue bean="${questionElementInstance}" field="designOrder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="questionElement.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${questionElementInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.dataElement}">
				<li class="fieldcontain">
					<span id="dataElement-label" class="property-label"><g:message code="questionElement.dataElement.label" default="Data Element" /></span>
					
						<span class="property-value" aria-labelledby="dataElement-label"><g:link controller="dataElement" action="show" id="${questionElementInstance?.dataElement?.id}">${questionElementInstance?.dataElement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.valueDomain}">
				<li class="fieldcontain">
					<span id="valueDomain-label" class="property-label"><g:message code="questionElement.valueDomain.label" default="Value Domain" /></span>
					
						<span class="property-value" aria-labelledby="valueDomain-label"><g:link controller="valueDomain" action="show" id="${questionElementInstance?.valueDomain?.id}">${questionElementInstance?.valueDomain?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.inputField}">
				<li class="fieldcontain">
					<span id="inputField-label" class="property-label"><g:message code="questionElement.inputField.label" default="Input Field" /></span>
					
						<span class="property-value" aria-labelledby="inputField-label"><g:link controller="inputField" action="show" id="${questionElementInstance?.inputField?.id}">${questionElementInstance?.inputField?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.formDesign}">
				<li class="fieldcontain">
					<span id="formDesign-label" class="property-label"><g:message code="questionElement.formDesign.label" default="Form Design" /></span>
					
						<span class="property-value" aria-labelledby="formDesign-label"><g:link controller="formDesign" action="show" id="${questionElementInstance?.formDesign?.id}">${questionElementInstance?.formDesign?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${questionElementInstance?.preText}">
				<li class="fieldcontain">
					<span id="preText-label" class="property-label"><g:message code="questionElement.preText.label" default="Pre Text" /></span>
					
						<span class="property-value" aria-labelledby="preText-label"><g:fieldValue bean="${questionElementInstance}" field="preText"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${questionElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${questionElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
