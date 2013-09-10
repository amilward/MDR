
<%@ page import="uk.co.mdc.model.ExternalReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalReference.label', default: 'ExternalReference')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="External References" />
	</head>
	<body>
		
		<div class="box">
            <div id="list-externalReference" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="externalReferenceList" ></div>
			<g:javascript library="dataTables"/>
			<r:script disposition="defer">

			$(function() {
			 
				externalReferenceList();
            });
				
			</r:script>
		</div>
                                                         
        </div>                
	</body>
</html>
