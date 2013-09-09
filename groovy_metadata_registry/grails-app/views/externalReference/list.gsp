
<%@ page import="uk.co.mdc.model.ExternalReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalReference.label', default: 'ExternalReference')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-externalReference" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-externalReference" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="attributes" title="${message(code: 'externalReference.attributes.label', default: 'Attributes')}" />
					
						<g:sortableColumn property="externalIdentifier" title="${message(code: 'externalReference.externalIdentifier.label', default: 'External Identifier')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'externalReference.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="url" title="${message(code: 'externalReference.url.label', default: 'Url')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${externalReferenceInstanceList}" status="i" var="externalReferenceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${externalReferenceInstance.id}">${fieldValue(bean: externalReferenceInstance, field: "attributes")}</g:link></td>
					
						<td>${fieldValue(bean: externalReferenceInstance, field: "externalIdentifier")}</td>
					
						<td>${fieldValue(bean: externalReferenceInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: externalReferenceInstance, field: "url")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${externalReferenceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
