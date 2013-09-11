<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" DATA TYPE - ${formDesignInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${formDesignInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
					    	<g:hiddenField id="formDesignId" name="formDesignId" value="${formDesignInstance?.id}" />	
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${formDesignInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<form id="previewForm"></form>
			<g:javascript src="jquery.dform-1.1.0.min.js" />
			<g:javascript src="formDesign.js" />
		</div>
	</body>
</html>