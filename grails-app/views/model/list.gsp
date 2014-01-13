
<%@ page import="uk.co.mdc.model.Model" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'model.label', default: 'Model')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Models" />
	</head>
	<body>
		<div class="box">
			<div id="list-model" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="modelList" ></div>
					<g:javascript library="model"/>
					<r:script disposition="defer">
		
					$(function() {
						modelList();
		            });
						
					</r:script>
			</div>                                           
        </div>  
	</body>
</html>