
<%@ page import="uk.co.mdc.pathways.Node" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'node.label', default: 'Node')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-node" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-node" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="refId" title="${message(code: 'node.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'node.description.label', default: 'Description')}" />
					
						<th><g:message code="node.peCollection.label" default="Pe Collection" /></th>
					
						<th><g:message code="node.pathwaysModel.label" default="Pathways Model" /></th>
					
						<g:sortableColumn property="x" title="${message(code: 'node.x.label', default: 'X')}" />
					
						<g:sortableColumn property="y" title="${message(code: 'node.y.label', default: 'Y')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${nodeInstanceList}" status="i" var="nodeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${nodeInstance.id}">${fieldValue(bean: nodeInstance, field: "refId")}</g:link></td>
					
						<td>${fieldValue(bean: nodeInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: nodeInstance, field: "peCollection")}</td>
					
						<td>${fieldValue(bean: nodeInstance, field: "pathwaysModel")}</td>
					
						<td>${fieldValue(bean: nodeInstance, field: "x")}</td>
					
						<td>${fieldValue(bean: nodeInstance, field: "y")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${nodeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
