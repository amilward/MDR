
<%@ page import="uk.co.mdc.forms.FormSpecification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formSpecification.label', default: 'FormSpecification')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-formSpecification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-formSpecification" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list formSpecification">
			
				<g:if test="${formSpecificationInstance?.collection}">
				<li class="fieldcontain">
					<span id="collection-label" class="property-label"><g:message code="formSpecification.collection.label" default="Collection" /></span>
					
						<span class="property-value" aria-labelledby="collection-label"><g:link controller="collection" action="show" id="${formSpecificationInstance?.collection?.id}">${formSpecificationInstance?.collection?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${formSpecificationInstance?.fields}">
				<li class="fieldcontain">
					<span id="fields-label" class="property-label"><g:message code="formSpecification.fields.label" default="Fields" /></span>
					
						<g:each in="${formSpecificationInstance.fields}" var="f">
						<span class="property-value" aria-labelledby="fields-label"><g:link controller="field" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${formSpecificationInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="formSpecification.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${formSpecificationInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${formSpecificationInstance?.id}" />
					<g:link class="edit" action="edit" id="${formSpecificationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		<form id="previewForm"></form>
		<g:javascript src="jquery.dform-1.1.0.min.js" />
		<g:javascript src="formspec.js" />
	</body>
</html>
