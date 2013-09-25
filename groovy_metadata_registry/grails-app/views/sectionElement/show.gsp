
<%@ page import="uk.co.mdc.forms.SectionElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'sectionElement.label', default: 'SectionElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-sectionElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-sectionElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list sectionElement">
			
				<g:if test="${sectionElementInstance?.label}">
				<li class="fieldcontain">
					<span id="label-label" class="property-label"><g:message code="sectionElement.label.label" default="Label" /></span>
					
						<span class="property-value" aria-labelledby="label-label"><g:fieldValue bean="${sectionElementInstance}" field="label"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.style}">
				<li class="fieldcontain">
					<span id="style-label" class="property-label"><g:message code="sectionElement.style.label" default="Style" /></span>
					
						<span class="property-value" aria-labelledby="style-label"><g:fieldValue bean="${sectionElementInstance}" field="style"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.designOrder}">
				<li class="fieldcontain">
					<span id="designOrder-label" class="property-label"><g:message code="sectionElement.designOrder.label" default="Design Order" /></span>
					
						<span class="property-value" aria-labelledby="designOrder-label"><g:fieldValue bean="${sectionElementInstance}" field="designOrder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="sectionElement.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${sectionElementInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.dataElementConcept}">
				<li class="fieldcontain">
					<span id="dataElementConcept-label" class="property-label"><g:message code="sectionElement.dataElementConcept.label" default="Data Element Concept" /></span>
					
						<span class="property-value" aria-labelledby="dataElementConcept-label"><g:link controller="dataElementConcept" action="show" id="${sectionElementInstance?.dataElementConcept?.id}">${sectionElementInstance?.dataElementConcept?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.formDesign}">
				<li class="fieldcontain">
					<span id="formDesign-label" class="property-label"><g:message code="sectionElement.formDesign.label" default="Form Design" /></span>
					
						<span class="property-value" aria-labelledby="formDesign-label"><g:link controller="formDesign" action="show" id="${sectionElementInstance?.formDesign?.id}">${sectionElementInstance?.formDesign?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.formDesignElement}">
				<li class="fieldcontain">
					<span id="formDesignElement-label" class="property-label"><g:message code="sectionElement.formDesignElement.label" default="Form Design Element" /></span>
					
						<span class="property-value" aria-labelledby="formDesignElement-label"><g:link controller="formDesignElement" action="show" id="${sectionElementInstance?.formDesignElement?.id}">${sectionElementInstance?.formDesignElement?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.preText}">
				<li class="fieldcontain">
					<span id="preText-label" class="property-label"><g:message code="sectionElement.preText.label" default="Pre Text" /></span>
					
						<span class="property-value" aria-labelledby="preText-label"><g:fieldValue bean="${sectionElementInstance}" field="preText"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.presentationElements}">
				<li class="fieldcontain">
					<span id="presentationElements-label" class="property-label"><g:message code="sectionElement.presentationElements.label" default="Presentation Elements" /></span>
					
						<g:each in="${sectionElementInstance.presentationElements}" var="p">
						<span class="property-value" aria-labelledby="presentationElements-label"><g:link controller="presentationElement" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${sectionElementInstance?.questionElements}">
				<li class="fieldcontain">
					<span id="questionElements-label" class="property-label"><g:message code="sectionElement.questionElements.label" default="Question Elements" /></span>
					
						<g:each in="${sectionElementInstance.questionElements}" var="q">
						<span class="property-value" aria-labelledby="questionElements-label"><g:link controller="questionElement" action="show" id="${q.id}">${q?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${sectionElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${sectionElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
