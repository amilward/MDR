
<%@ page import="uk.co.mdc.model.Document" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" DOCUMENT - ${documentInstance?.name}" />
	</head>
	<body>
	<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'document']">
				<g:hiddenField name="id" value="${documentInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${documentInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${documentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${documentInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${documentInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
					<g:if test="${documentInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${documentInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${documentInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${documentInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${documentInstance?.content}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.content.label" default="Content" /></span></td>
						<td class="right_col_show"><g:link controller="Document" action="download" id="${documentInstance?.id}"> download </g:link></td>
					</tr>
				</g:if>
				<g:if test="${documentInstance?.contentType}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.contentType.label" default="Content Type" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${documentInstance}" field="contentType"/></td>
					</tr>
				</g:if>
				<g:if test="${documentInstance?.fileName}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.fileName.label" default="File Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${documentInstance}" field="fileName"/></td>
					</tr>
				</g:if>
				
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${documentInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
