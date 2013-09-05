
<%@ page import="uk.co.mdc.model.ExternalSynonym" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'externalSynonym.label', default: 'ExternalSynonym')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	<parameter name="name" value=" EXTERNAL SYNONYM - ${externalSynonymInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'externalSynonym']">
				<g:hiddenField name="id" value="${externalSynonymInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${externalSynonymInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${externalSynonymInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${externalSynonymInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${externalSynonymInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${externalSynonymInstance?.externalIdentifier}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.externalIdentifier.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${externalSynonymInstance}" field="externalIdentifier"/></td>
					</tr>
				</g:if>
				<g:if test="${externalSynonymInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${externalSynonymInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${externalSynonymInstance?.url}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.url.label" default="Url" /></span></td>
						<td class="right_col_show"><a href="${externalSynonymInstance.url}" >${externalSynonymInstance.url}</a></td>
					</tr>
				</g:if>
				
				<g:if test="${externalSynonymInstance?.attributes}">
					<tr>
						<g:each var="attribute" in="${externalSynonymInstance.attributes}">
								<tr>
									<td class="left_col_show"><span id="name-label" class="label">${attribute?.key}</span></td>
									<td class="right_col_show">${attribute?.value}</td>
								</tr>
						</g:each>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${externalSynonymInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
