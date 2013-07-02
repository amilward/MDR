
<%@ page import="uk.co.mdc.model.UmlModel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'umlModel.label', default: 'UmlModel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-umlModel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-umlModel" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="conceptId" title="${message(code: 'umlModel.conceptId.label', default: 'Concept Id')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'umlModel.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="refId" title="${message(code: 'umlModel.refId.label', default: 'Ref Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${umlModelInstanceList}" status="i" var="umlModelInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${umlModelInstance.id}">${fieldValue(bean: umlModelInstance, field: "conceptId")}</g:link></td>
					
						<td>${fieldValue(bean: umlModelInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: umlModelInstance, field: "refId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${umlModelInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
