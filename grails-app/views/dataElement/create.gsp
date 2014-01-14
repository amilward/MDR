<%@ page import="uk.co.mdc.model.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-duallistbox.css')}" type="text/css">
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<parameter name="name" value=" CREATE DATA ELEMENT " />
	</head>
	<body>
		<header>
			<g:form url="[action:'save',controller:'dataElement']">
				<g:hiddenField name="id" value="${dataElementInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav nav-tabs">
							    <li class="active"><g:link action="create" ><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="saveCreate()">Save</a></li>
							   </ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		
		<div class="box">
		<div id="create-dataElement" class="content scaffold-create" role="main">
			<!--  <div class="help">
				<a href="#" >Help �</a>
				<p>$ { DataElement.HELP }</p>
			</div> -->
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dataElementInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dataElementInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form id="createForm" method="post" url="[action:'save',controller:'dataElement']">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</div>
	</body>
</html>
