
<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-collection" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-collection" class="content scaffold-list" role="main">
			<h1>Collections</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableList">
				<thead>
					<tr>
					
						<g:sortableColumn property="refId" title="${message(code: 'collection.refId.label', default: 'Ref ID')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'collection.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'collection.description.label', default: 'Description')}" />
					
						
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${collectionInstanceList}" status="i" var="collectionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: collectionInstance, field: "refId")}</td>
						
						<td><g:link action="show" id="${collectionInstance.id}">${fieldValue(bean: collectionInstance, field: "name")}</g:link></td>
						
						<td><div class="limit_row_height">${fieldValue(bean: collectionInstance, field: "description")}</div></td>
					
						
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${collectionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
