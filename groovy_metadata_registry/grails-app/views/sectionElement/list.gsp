
<%@ page import="uk.co.mdc.forms.SectionElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'sectionElement.label', default: 'SectionElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-sectionElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-sectionElement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="label" title="${message(code: 'sectionElement.label.label', default: 'Label')}" />
					
						<g:sortableColumn property="style" title="${message(code: 'sectionElement.style.label', default: 'Style')}" />
					
						<g:sortableColumn property="designOrder" title="${message(code: 'sectionElement.designOrder.label', default: 'Design Order')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'sectionElement.title.label', default: 'Title')}" />
					
						<th><g:message code="sectionElement.dataElementConcept.label" default="Data Element Concept" /></th>
					
						<th><g:message code="sectionElement.formDesign.label" default="Form Design" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${sectionElementInstanceList}" status="i" var="sectionElementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${sectionElementInstance.id}">${fieldValue(bean: sectionElementInstance, field: "label")}</g:link></td>
					
						<td>${fieldValue(bean: sectionElementInstance, field: "style")}</td>
					
						<td>${fieldValue(bean: sectionElementInstance, field: "designOrder")}</td>
					
						<td>${fieldValue(bean: sectionElementInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: sectionElementInstance, field: "dataElementConcept")}</td>
					
						<td>${fieldValue(bean: sectionElementInstance, field: "formDesign")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${sectionElementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
