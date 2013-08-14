
<%@ page import="uk.co.mdc.forms.Field" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'field.label', default: 'Field')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-field" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-field" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list field">
			
				<g:if test="${fieldInstance?.caption}">
				<li class="fieldcontain">
					<span id="caption-label" class="property-label"><g:message code="field.caption.label" default="Caption" /></span>
					
						<span class="property-value" aria-labelledby="caption-label"><g:fieldValue bean="${fieldInstance}" field="caption"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.field_class}">
				<li class="fieldcontain">
					<span id="field_class-label" class="property-label"><g:message code="field.field_class.label" default="Fieldclass" /></span>
					
						<span class="property-value" aria-labelledby="field_class-label"><g:fieldValue bean="${fieldInstance}" field="field_class"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.field_id}">
				<li class="fieldcontain">
					<span id="field_id-label" class="property-label"><g:message code="field.field_id.label" default="Fieldid" /></span>
					
						<span class="property-value" aria-labelledby="field_id-label"><g:fieldValue bean="${fieldInstance}" field="field_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="field.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${fieldInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.options}">
				<li class="fieldcontain">
					<span id="options-label" class="property-label"><g:message code="field.options.label" default="Options" /></span>
					
						<span class="property-value" aria-labelledby="options-label"><g:fieldValue bean="${fieldInstance}" field="options"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.placeholder}">
				<li class="fieldcontain">
					<span id="placeholder-label" class="property-label"><g:message code="field.placeholder.label" default="Placeholder" /></span>
					
						<span class="property-value" aria-labelledby="placeholder-label"><g:fieldValue bean="${fieldInstance}" field="placeholder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.size}">
				<li class="fieldcontain">
					<span id="size-label" class="property-label"><g:message code="field.size.label" default="Size" /></span>
					
						<span class="property-value" aria-labelledby="size-label"><g:fieldValue bean="${fieldInstance}" field="size"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="field.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${fieldInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fieldInstance?.value}">
				<li class="fieldcontain">
					<span id="value-label" class="property-label"><g:message code="field.value.label" default="Value" /></span>
					
						<span class="property-value" aria-labelledby="value-label"><g:fieldValue bean="${fieldInstance}" field="value"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${fieldInstance?.id}" />
					<g:link class="edit" action="edit" id="${fieldInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
