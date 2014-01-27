
<%@ page import="uk.co.mdc.catalogue.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Data Elements" />
	</head>
	<body>
		
		<div class="box">
            <div id="list-dataElement" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="dataElementList" ></div>
			<g:javascript library="dataElement"/>
			<r:script disposition="defer">

			$(function() {
				dataElementList();
				dataElementDragStart();
            });
				
			</r:script>
		</div>
                                                         
        </div>                
	</body>
</html>
