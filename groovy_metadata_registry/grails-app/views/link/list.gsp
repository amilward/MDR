
<%@ page import="uk.co.mdc.pathways.Link" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'link.label', default: 'Link')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-link" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-link" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="refId" title="${message(code: 'link.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'link.description.label', default: 'Description')}" />
					
						<th><g:message code="link.pathwaysModel.label" default="Pathways Model" /></th>
					
						<g:sortableColumn property="extension" title="${message(code: 'link.extension.label', default: 'Extension')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'link.name.label', default: 'Name')}" />
					
						<th><g:message code="link.source.label" default="Source" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${linkInstanceList}" status="i" var="linkInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${linkInstance.id}">${fieldValue(bean: linkInstance, field: "refId")}</g:link></td>
					
						<td>${fieldValue(bean: linkInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: linkInstance, field: "pathwaysModel")}</td>
					
						<td>${fieldValue(bean: linkInstance, field: "extension")}</td>
					
						<td>${fieldValue(bean: linkInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: linkInstance, field: "source")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${linkInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
