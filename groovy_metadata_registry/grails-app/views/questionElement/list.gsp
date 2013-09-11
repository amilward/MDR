
<%@ page import="uk.co.mdc.forms.QuestionElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'questionElement.label', default: 'QuestionElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-questionElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-questionElement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="label" title="${message(code: 'questionElement.label.label', default: 'Label')}" />
					
						<g:sortableColumn property="style" title="${message(code: 'questionElement.style.label', default: 'Style')}" />
					
						<g:sortableColumn property="designOrder" title="${message(code: 'questionElement.designOrder.label', default: 'Design Order')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'questionElement.title.label', default: 'Title')}" />
					
						<th><g:message code="questionElement.dataElement.label" default="Data Element" /></th>
					
						<th><g:message code="questionElement.valueDomain.label" default="Value Domain" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${questionElementInstanceList}" status="i" var="questionElementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${questionElementInstance.id}">${fieldValue(bean: questionElementInstance, field: "label")}</g:link></td>
					
						<td>${fieldValue(bean: questionElementInstance, field: "style")}</td>
					
						<td>${fieldValue(bean: questionElementInstance, field: "designOrder")}</td>
					
						<td>${fieldValue(bean: questionElementInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: questionElementInstance, field: "dataElement")}</td>
					
						<td>${fieldValue(bean: questionElementInstance, field: "valueDomain")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${questionElementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
