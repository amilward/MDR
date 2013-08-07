
<%@ page import="uk.co.mdc.model.DataElementConcept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementConcept.label', default: 'Sections (Data Element Concepts)')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataElementConcept" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataElementConcept" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableList">
				<thead>
					<tr>
					
					<g:sortableColumn property="name" title="${message(code: 'dataElementConcept.name.label', default: 'Name')}" />
					
					<g:sortableColumn property="description" title="${message(code: 'dataElementConcept.description.label', default: 'Description')}" />
					
					<g:sortableColumn property="parent" title="${message(code: 'dataElementConcept.parent.label', default: 'Parent')}" />
					
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataElementConceptInstanceList}" status="i" var="dataElementConceptInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dataElementConceptInstance.id}">${fieldValue(bean: dataElementConceptInstance, field: "name")}</g:link></td>
					
						<td><div class="limit_row_height">${fieldValue(bean: dataElementConceptInstance, field: "description")}</div></td>
					
						<td>${dataElementConceptInstance?.parent?.name}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataElementConceptInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
