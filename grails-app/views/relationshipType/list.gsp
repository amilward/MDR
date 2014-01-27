
<%@ page import="uk.co.mdc.catalogue.RelationshipType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'relationshipType.label', default: 'RelationshipType')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Relationship Types" />
	</head>
	<body>
		<div class="box">
			<div id="list-relationshipType" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="relationshipTypeList" ></div>
				<g:javascript library="relationshipType"/>
				<r:script disposition="defer">
	
				$(function() {
					relationshipTypeList();
	            });
					
				</r:script>
			</div>                                           
        </div>  
	</body>
</html>
