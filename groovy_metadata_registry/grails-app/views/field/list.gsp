
<%@ page import="uk.co.mdc.forms.Field" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'field.label', default: 'Field')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-field" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-field" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="caption" title="${message(code: 'field.caption.label', default: 'Caption')}" />
					
						<g:sortableColumn property="field_class" title="${message(code: 'field.field_class.label', default: 'Fieldclass')}" />
					
						<g:sortableColumn property="field_id" title="${message(code: 'field.field_id.label', default: 'Fieldid')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'field.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="options" title="${message(code: 'field.options.label', default: 'Options')}" />
					
						<g:sortableColumn property="placeholder" title="${message(code: 'field.placeholder.label', default: 'Placeholder')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fieldInstanceList}" status="i" var="fieldInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fieldInstance.id}">${fieldValue(bean: fieldInstance, field: "caption")}</g:link></td>
					
						<td>${fieldValue(bean: fieldInstance, field: "field_class")}</td>
					
						<td>${fieldValue(bean: fieldInstance, field: "field_id")}</td>
					
						<td>${fieldValue(bean: fieldInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: fieldInstance, field: "options")}</td>
					
						<td>${fieldValue(bean: fieldInstance, field: "placeholder")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fieldInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
