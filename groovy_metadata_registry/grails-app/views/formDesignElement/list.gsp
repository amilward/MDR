
<%@ page import="uk.co.mdc.forms.FormDesignElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesignElement.label', default: 'FormDesignElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-formDesignElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-formDesignElement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="label" title="${message(code: 'formDesignElement.label.label', default: 'Label')}" />
					
						<g:sortableColumn property="style" title="${message(code: 'formDesignElement.style.label', default: 'Style')}" />
					
						<g:sortableColumn property="designOrder" title="${message(code: 'formDesignElement.designOrder.label', default: 'Design Order')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'formDesignElement.title.label', default: 'Title')}" />
					
						<th><g:message code="formDesignElement.formDesign.label" default="Form Design" /></th>
					
						<g:sortableColumn property="preText" title="${message(code: 'formDesignElement.preText.label', default: 'Pre Text')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${formDesignElementInstanceList}" status="i" var="formDesignElementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${formDesignElementInstance.id}">${fieldValue(bean: formDesignElementInstance, field: "label")}</g:link></td>
					
						<td>${fieldValue(bean: formDesignElementInstance, field: "style")}</td>
					
						<td>${fieldValue(bean: formDesignElementInstance, field: "designOrder")}</td>
					
						<td>${fieldValue(bean: formDesignElementInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: formDesignElementInstance, field: "formDesign")}</td>
					
						<td>${fieldValue(bean: formDesignElementInstance, field: "preText")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${formDesignElementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
