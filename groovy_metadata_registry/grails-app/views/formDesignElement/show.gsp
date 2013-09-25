
<%@ page import="uk.co.mdc.forms.FormDesignElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesignElement.label', default: 'FormDesignElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-formDesignElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-formDesignElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list formDesignElement">
			
				<g:if test="${formDesignElementInstance?.label}">
				<li class="fieldcontain">
					<span id="label-label" class="property-label"><g:message code="formDesignElement.label.label" default="Label" /></span>
					
						<span class="property-value" aria-labelledby="label-label"><g:fieldValue bean="${formDesignElementInstance}" field="label"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${formDesignElementInstance?.style}">
				<li class="fieldcontain">
					<span id="style-label" class="property-label"><g:message code="formDesignElement.style.label" default="Style" /></span>
					
						<span class="property-value" aria-labelledby="style-label"><g:fieldValue bean="${formDesignElementInstance}" field="style"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${formDesignElementInstance?.designOrder}">
				<li class="fieldcontain">
					<span id="designOrder-label" class="property-label"><g:message code="formDesignElement.designOrder.label" default="Design Order" /></span>
					
						<span class="property-value" aria-labelledby="designOrder-label"><g:fieldValue bean="${formDesignElementInstance}" field="designOrder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${formDesignElementInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="formDesignElement.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${formDesignElementInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${formDesignElementInstance?.formDesign}">
				<li class="fieldcontain">
					<span id="formDesign-label" class="property-label"><g:message code="formDesignElement.formDesign.label" default="Form Design" /></span>
					
						<span class="property-value" aria-labelledby="formDesign-label"><g:link controller="formDesign" action="show" id="${formDesignElementInstance?.formDesign?.id}">${formDesignElementInstance?.formDesign?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${formDesignElementInstance?.preText}">
				<li class="fieldcontain">
					<span id="preText-label" class="property-label"><g:message code="formDesignElement.preText.label" default="Pre Text" /></span>
					
						<span class="property-value" aria-labelledby="preText-label"><g:fieldValue bean="${formDesignElementInstance}" field="preText"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${formDesignElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${formDesignElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
