
<%@ page import="uk.co.mdc.catalogue.Relationship" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'relationship.label', default: 'Relationship')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-relationship" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-relationship" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="objectXId" title="${message(code: 'relationship.objectXId.label', default: 'Object XI d')}" />
					
						<g:sortableColumn property="objectYId" title="${message(code: 'relationship.objectYId.label', default: 'Object YI d')}" />
					
						<th><g:message code="relationship.relationshipType.label" default="Relationship Type" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${relationshipInstanceList}" status="i" var="relationshipInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${relationshipInstance.id}">${fieldValue(bean: relationshipInstance, field: "objectXId")}</g:link></td>
					
						<td>${fieldValue(bean: relationshipInstance, field: "objectYId")}</td>
					
						<td>${fieldValue(bean: relationshipInstance, field: "relationshipType")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${relationshipInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
