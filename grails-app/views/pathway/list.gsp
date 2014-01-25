
<%@ page import="uk.co.mdc.pathways.Pathway" %>
<!DOCTYPE html>
<html>
	<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'pathway.label', default: 'Pathway')}" />
		<title>All pathways</title>
		<parameter name="name" value="Pathways" />
	</head>
	<body>
		<div class="box">
			<div id="list-pathways" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<div id="pathwaysList" >
                    <table width="100%">
                        <thead>
                            <td><g:message code="pathway.name" /></td>
                            <td><g:message code="pathway.description" /></td>
                            <td><g:message code="pathway.userVersion" /></td>
                            <td><g:message code="pathway.isDraft" /></td>
                        </thead>
                        <tbody>
                        <g:each in="${pathways}" var="pathway">
                            <tr>
                                <td><g:link controller="pathway" action="show" id="${pathway.id}">${pathway.name}</g:link></td>
                                <td>${pathway.description}</td>
                                <td>${pathway.userVersion}</td>
                                <td>${pathway.isDraft}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
				</div>
			</div>
		</div>
	</body>
</html>
