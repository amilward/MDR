
<%@ page import="uk.co.mdc.model.DataElementConcept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'dataElementConcept.label', default: 'Sections (Data Element Concepts)')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Data Element Concepts" />
	</head>
	<body>
		<div class="box">
		<div id="list-dataElementConcept" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="dataElementConceptList" ></div>
			<g:javascript library="dataElementConcept"/>
			<r:script disposition="defer">

			$(function() {
				dataElementConceptList();
            });
				
			</r:script>
		</div>
                                                         
        </div> 
	</body>
</html>
