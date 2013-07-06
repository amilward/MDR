
<%@ page import="uk.co.mdc.model.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataElement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="parentId" title="${message(code: 'dataElement.parentId.label', default: 'Parent Id')}" />
					
						<g:sortableColumn property="definition" title="${message(code: 'dataElement.definition.label', default: 'Definition')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'dataElement.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="refId" title="${message(code: 'dataElement.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="vdId" title="${message(code: 'dataElement.vdId.label', default: 'Vd Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataElementInstanceList}" status="i" var="dataElementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dataElementInstance.id}">${fieldValue(bean: dataElementInstance, field: "parentId")}</g:link></td>
					
						<td>${fieldValue(bean: dataElementInstance, field: "definition")}</td>
					
						<td>${fieldValue(bean: dataElementInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: dataElementInstance, field: "refId")}</td>
					
						<td>${fieldValue(bean: dataElementInstance, field: "vdId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataElementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
