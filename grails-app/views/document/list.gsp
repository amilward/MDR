
<%@ page import="uk.co.mdc.model.Document" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Documents" />
	</head>
	<body>
		<div class="box">
		<div id="list-document" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
		<div id="documentList" ></div>
			<g:javascript library="dataTables"/>
			<r:script disposition="defer">

			$(function() {
				documentList();
            });
				
			</r:script>
		</div>
                                                         
        </div>   
	</body>
</html>
