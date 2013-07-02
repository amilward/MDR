
<%@ page import="uk.co.mdc.model.Document" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-document" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-document" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'document.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'document.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="content" title="${message(code: 'document.content.label', default: 'Content')}" />
					
						<g:sortableColumn property="contentType" title="${message(code: 'document.contentType.label', default: 'Content Type')}" />
					
						<g:sortableColumn property="fileName" title="${message(code: 'document.fileName.label', default: 'File Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentInstanceList}" status="i" var="documentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentInstance.id}">${fieldValue(bean: documentInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: documentInstance, field: "description")}</td>
					
						<td>
						<g:link controller="Document" action="download" id="${documentInstance?.id}"> download </g:link>
						</td>
					
						<td>${fieldValue(bean: documentInstance, field: "contentType")}</td>
					
						<td>${fieldValue(bean: documentInstance, field: "fileName")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
