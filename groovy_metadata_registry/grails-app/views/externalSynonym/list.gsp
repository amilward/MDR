
<%@ page import="uk.co.mdc.model.ExternalSynonym" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalSynonym.label', default: 'ExternalSynonym')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="External Synonyms" />
	</head>
	<body>
		<div class="box">
			<div id="list-externalSynonym" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="externalSynonymList" ></div>
				<g:javascript library="dataTables"/>
				<r:script disposition="defer">
	
				$(function() {
					externalSynonymList();
	            });
					
				</r:script>
			</div>                                           
        </div>  
</html>
