
<%@ page import="uk.co.mdc.pathways.PathwaysModel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-pathwaysModel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-pathwaysModel" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="refId" title="${message(code: 'pathwaysModel.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'pathwaysModel.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="isDraft" title="${message(code: 'pathwaysModel.isDraft.label', default: 'Is Draft')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'pathwaysModel.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="versionNo" title="${message(code: 'pathwaysModel.versionNo.label', default: 'Version No')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pathwaysModelInstanceList}" status="i" var="pathwaysModelInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pathwaysModelInstance.id}">${fieldValue(bean: pathwaysModelInstance, field: "refId")}</g:link></td>
					
						<td>${fieldValue(bean: pathwaysModelInstance, field: "description")}</td>
					
						<td><g:formatBoolean boolean="${pathwaysModelInstance.isDraft}" /></td>
					
						<td>${fieldValue(bean: pathwaysModelInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: pathwaysModelInstance, field: "versionNo")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pathwaysModelInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
