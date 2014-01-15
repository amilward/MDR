
<%@ page import="uk.co.mdc.model.ValueDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'valueDomain.label', default: 'ValueDomain')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Value Domain" />
	</head>
	<body>
		<div class="box">
			<div id="list-valueDomain" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="valueDomainList" ></div>
				<g:javascript library="valueDomain"/>
				<r:script disposition="defer">
	
				$(function() {
					valueDomainList();
	            });
					
				</r:script>
			</div>
		</div>
	</body>
</html>
