
<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="name" value="Form Designs" />
	</head>
	<body>
		<div class="box">
            <div id="list-formDesign" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="formDesignList" ></div>
			<g:javascript library="dataTables"/>
			<r:script disposition="defer">

			$(function() {
				formDesignList();
            });
				
			</r:script>
		</div>
                                                         
        </div>              
	</body>
</html>
