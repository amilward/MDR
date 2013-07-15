
<%@ page import="uk.co.mdc.model.ConceptualDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conceptualDomain.label', default: 'ConceptualDomain')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-conceptualDomain" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-conceptualDomain" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'conceptualDomain.name.label', default: 'Name')}" />
						
						<g:sortableColumn property="description" title="${message(code: 'conceptualDomain.description.label', default: 'Description')}" />
						
						<g:sortableColumn property="refId" title="${message(code: 'conceptualDomain.refId.label', default: 'Ref Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${conceptualDomainInstanceList}" status="i" var="conceptualDomainInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${conceptualDomainInstance.id}">${fieldValue(bean: conceptualDomainInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: conceptualDomainInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: conceptualDomainInstance, field: "refId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${conceptualDomainInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
