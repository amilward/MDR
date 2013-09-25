
<%@ page import="uk.co.mdc.model.ConceptualDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conceptualDomain.label', default: 'ConceptualDomain')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Conceptual Domains" />
	</head>
	<body>
	 <div class="box">
		<div id="list-conceptualDomain" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="conceptualDomainList" ></div>
			<g:javascript library="dataTables"/>
			<r:script disposition="defer">

			$(function() {
				conceptualDomainList();
            });
				
			</r:script>
		</div>                                               
       </div>   
	</body>
</html>
