
<%@ page import="uk.co.mdc.model.ExternalReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'externalReference.label', default: 'ExternalReference')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	<parameter name="name" value=" EXTERNAL REFERENCE - ${externalReferenceInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'externalReference']">
				<g:hiddenField name="id" value="${externalReferenceInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${externalReferenceInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${externalReferenceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${externalReferenceInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${externalReferenceInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${externalReferenceInstance?.externalIdentifier}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.externalIdentifier.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${externalReferenceInstance}" field="externalIdentifier"/></td>
					</tr>
				</g:if>
				<g:if test="${externalReferenceInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${externalReferenceInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${externalReferenceInstance?.url}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.url.label" default="Url" /></span></td>
						<td class="right_col_show"><a href="${externalReferenceInstance.url}" >${externalReferenceInstance.url}</a></td>
					</tr>
				</g:if>
				
				<g:if test="${externalReferenceInstance?.attributes}">
					<tr>
						<g:each var="attribute" in="${externalReferenceInstance.attributes}">
								<tr>
									<td class="left_col_show"><span id="name-label" class="label">${attribute?.key}</span></td>
									<td class="right_col_show">${attribute?.value}</td>
								</tr>
						</g:each>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${externalReferenceInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
