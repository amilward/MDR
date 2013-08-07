
<%@ page import="uk.co.mdc.model.DataType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataType.label', default: 'DataType')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataType" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="tableList">
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'dataType.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="enumerated" title="${message(code: 'dataType.enumerated.label', default: 'Enumerated')}" />
						
						<g:sortableColumn property="enumerations" title="${message(code: 'dataType.enumerations.label', default: 'Enumerations')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataTypeInstanceList}" status="i" var="dataTypeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${dataTypeInstance.id}">${fieldValue(bean: dataTypeInstance, field: "name")}</g:link></td>
						
						<td><g:formatBoolean boolean="${dataTypeInstance.enumerated}" /></td>
						
						<td>${fieldValue(bean: dataTypeInstance, field: "enumerations")}</td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataTypeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
