
<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Collections" />
	</head>
	<body>
		<div class="box">
			<div id="list-collection" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="collectionList" ></div>
					<g:javascript library="dataTables"/>
					<r:script disposition="defer">
		
					$(function() {
						collectionList();
		            });
						
					</r:script>
			</div>                                           
        </div>  
	</body>
</html>
