
<%@ page import="uk.co.mdc.forms.InputField" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'inputField.label', default: 'InputField')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-inputField" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-inputField" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="defaultValue" title="${message(code: 'inputField.defaultValue.label', default: 'Default Value')}" />
					
						<g:sortableColumn property="placeholder" title="${message(code: 'inputField.placeholder.label', default: 'Placeholder')}" />
					
						<g:sortableColumn property="maxCharacters" title="${message(code: 'inputField.maxCharacters.label', default: 'Max Characters')}" />
					
						<g:sortableColumn property="unitOfMeasure" title="${message(code: 'inputField.unitOfMeasure.label', default: 'Unit Of Measure')}" />
					
						<th><g:message code="inputField.dataType.label" default="Data Type" /></th>
					
						<g:sortableColumn property="format" title="${message(code: 'inputField.format.label', default: 'Format')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${inputFieldInstanceList}" status="i" var="inputFieldInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${inputFieldInstance.id}">${fieldValue(bean: inputFieldInstance, field: "defaultValue")}</g:link></td>
					
						<td>${fieldValue(bean: inputFieldInstance, field: "placeholder")}</td>
					
						<td>${fieldValue(bean: inputFieldInstance, field: "maxCharacters")}</td>
					
						<td>${fieldValue(bean: inputFieldInstance, field: "unitOfMeasure")}</td>
					
						<td>${fieldValue(bean: inputFieldInstance, field: "dataType")}</td>
					
						<td>${fieldValue(bean: inputFieldInstance, field: "format")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${inputFieldInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
