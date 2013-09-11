
<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-formDesign" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-formDesign" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="refId" title="${message(code: 'formDesign.refId.label', default: 'Ref Id')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'formDesign.name.label', default: 'Name')}" />
					
						<th><g:message code="formDesign.header.label" default="Header" /></th>
					
						<th><g:message code="formDesign.footer.label" default="Footer" /></th>
					
						<g:sortableColumn property="description" title="${message(code: 'formDesign.description.label', default: 'Description')}" />
					
						<th><g:message code="formDesign.collection.label" default="Collection" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${formDesignInstanceList}" status="i" var="formDesignInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${formDesignInstance.id}">${fieldValue(bean: formDesignInstance, field: "refId")}</g:link></td>
					
						<td>${fieldValue(bean: formDesignInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: formDesignInstance, field: "header")}</td>
					
						<td>${fieldValue(bean: formDesignInstance, field: "footer")}</td>
					
						<td>${fieldValue(bean: formDesignInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: formDesignInstance, field: "collection")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${formDesignInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
