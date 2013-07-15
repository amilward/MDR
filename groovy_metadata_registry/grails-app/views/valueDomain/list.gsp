
<%@ page import="uk.co.mdc.model.ValueDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'valueDomain.label', default: 'ValueDomain')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-valueDomain" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-valueDomain" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'valueDomain.name.label', default: 'Name')}" />
						
						<g:sortableColumn property="description" title="${message(code: 'valueDomain.description.label', default: 'Description')}" />						
						
						<g:sortableColumn property="dataType" title="${message(code: 'valueDomain.dataType.label', default: 'Data Type')}" />
					
						<g:sortableColumn property="refId" title="${message(code: 'valueDomain.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="regexDef" title="${message(code: 'valueDomain.regexDef.label', default: 'Regex Def')}" />
						
						<g:sortableColumn property="valueDomain.conceptualDomain" title="${message(code: 'valueDomain.conceptualDomain.label', default: 'Conceptual Domain')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${valueDomainInstanceList}" status="i" var="valueDomainInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${valueDomainInstance.id}">${fieldValue(bean: valueDomainInstance, field: "name")}</g:link></td>
						
						<td>${fieldValue(bean: valueDomainInstance, field: "description")}</td>						
						
						<td>${fieldValue(bean: valueDomainInstance, field: "dataType")}</td>
					
						<td>${fieldValue(bean: valueDomainInstance, field: "refId")}</td>
					
						<td>${fieldValue(bean: valueDomainInstance, field: "regexDef")}</td>
						
						<td><g:link controller="ConceptualDomain" action="show" id="${valueDomainInstance?.conceptualDomain?.id}">${valueDomainInstance?.conceptualDomain?.name}</g:link></td>
					
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${valueDomainInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
