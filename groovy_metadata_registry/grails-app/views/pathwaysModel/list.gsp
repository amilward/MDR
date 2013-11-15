
<%@ page import="uk.co.mdc.pathways.PathwaysModel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="pathways">
		<g:set var="entityName" value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Pathways" />
	</head>
	<body>
		<div class="box">
			<div id="list-pathways" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="pathwaysList" ></div>
				<g:javascript library="pathwaysList"/>
				<r:script disposition="defer">
	
				$(function() {
					pathwaysList();
	            });
					
				</r:script>
			</div>
		</div>
	</body>
</html>
