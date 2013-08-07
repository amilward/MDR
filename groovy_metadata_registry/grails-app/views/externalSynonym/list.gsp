
<%@ page import="uk.co.mdc.model.ExternalSynonym" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalSynonym.label', default: 'ExternalSynonym')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-externalSynonym" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-externalSynonym" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableList">
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'externalSynonym.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="url" title="${message(code: 'externalSynonym.url.label', default: 'Url')}" />
						
						<g:sortableColumn property="attributes" title="${message(code: 'externalSynonym.attributes.label', default: 'Attributes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${externalSynonymInstanceList}" status="i" var="externalSynonymInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${externalSynonymInstance.id}">${fieldValue(bean: externalSynonymInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: externalSynonymInstance, field: "url")}</td>
						
						<td>${fieldValue(bean: externalSynonymInstance, field: "attributes")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${externalSynonymInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
