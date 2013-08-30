
<%@ page import="uk.co.mdc.model.DataType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataType.label', default: 'DataType')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Data Types" />
	</head>
	<body>
		<div class="box">
			<div id="list-dataType" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="dataTypeList" ></div>
				<g:javascript library="dataTables"/>
				<r:script disposition="defer">
	
				$(function() {
					dataTypeList();
	            });
					
				</r:script>
			</div>                                           
        </div>  
	</body>
</html>
