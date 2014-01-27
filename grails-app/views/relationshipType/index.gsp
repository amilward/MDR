
<%@ page import="uk.co.mdc.catalogue.RelationshipType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'relationshipType.label', default: 'RelationshipType')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-relationshipType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-relationshipType" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="xYRelationship" title="${message(code: 'relationshipType.xYRelationship.label', default: 'XYR elationship')}" />
					
						<g:sortableColumn property="yXRelationship" title="${message(code: 'relationshipType.yXRelationship.label', default: 'YXR elationship')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'relationshipType.name.label', default: 'Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${relationshipTypeInstanceList}" status="i" var="relationshipTypeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${relationshipTypeInstance.id}">${fieldValue(bean: relationshipTypeInstance, field: "xYRelationship")}</g:link></td>
					
						<td>${fieldValue(bean: relationshipTypeInstance, field: "yXRelationship")}</td>
					
						<td>${fieldValue(bean: relationshipTypeInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${relationshipTypeInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
