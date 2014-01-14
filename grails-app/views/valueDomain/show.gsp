
<%@ page import="uk.co.mdc.model.ValueDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'valueDomain.label', default: 'ValueDomain')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" VALUE DOMAIN - ${valueDomainInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'valueDomain']">
				<g:hiddenField name="id" value="${valueDomainInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${valueDomainInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${valueDomainInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${valueDomainInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${valueDomainInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
					<g:if test="${valueDomainInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${valueDomainInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${valueDomainInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.conceptualDomain}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.conceptualDomain.label" default="Conceptual Domain" /></span></td>
						<td class="right_col_show">${valueDomainInstance?.conceptualDomain?.name}</td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.dataType}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.dataType.label" default="Data Type" /></span></td>
						<td class="right_col_show"><g:link controller="DataType" action="show" id="${valueDomainInstance?.dataType?.id}">${valueDomainInstance?.dataType?.name}</g:link></td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.regexDef}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.regexDef.label" default="Regex Definition" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${valueDomainInstance}" field="regexDef"/></td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.unitOfMeasure}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.unitOfMeasure.label" default="Regex Definition" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${valueDomainInstance}" field="unitOfMeasure"/></td>
					</tr>
				</g:if>
				<g:if test="${relationshipTypes}">
					<g:each var="relationshipType" in="${relationshipTypes}">
					
					<tr>
							<td colspan="2"><span id="name-label" class="label">${relationshipType.name}</span></td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
									<th></th>
								</tr>
							</thead>
							<g:each var="relation" in="${valueDomainInstance.relations(relationshipType.name)}">
								<tr>
									<td><g:link action="show" controller="${relation.getClass().getSimpleName()}" id="${relation?.id}">${relation?.name} </g:link></td>
									<td>${relation?.description}</td>
									<td>${relation?.relationshipType}</td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
					
					</g:each>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${valueDomainInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
