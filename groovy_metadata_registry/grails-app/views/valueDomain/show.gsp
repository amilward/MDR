
<%@ page import="uk.co.mdc.model.ValueDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
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
				<g:if test="${valueDomainInstance?.refId}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.valueDomainConcept.label" default="Reference Id" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${valueDomainInstance}" field="refId"/></td>
					</tr>
				</g:if>
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
				<g:if test="${valueDomainInstance?.dataElementValueDomains}">
					<tr>
						<td colspan="2"><span id="name-label" class="label">Associated Data Elements</span></td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Reference ID</th>
									<th>Description</th>
									<th>Parent</th>
		
								</tr>
							</thead>
							<g:each var="dataElement" in="${valueDomainInstance.dataElementValueDomains()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
							</tr>
						</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				<g:if test="${valueDomainInstance?.externalSynonyms}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">External Synonyms</span></td>
						<td class="right_col_show">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>URL</th>
									<th>Attributes</th>
								</tr>
							</thead>
							<g:each var="externalSynonym" in="${valueDomainInstance.externalSynonyms}">
								<tr>
									<td><g:link action="show" controller="ExternalSynonym" id="${externalSynonym?.id}">${externalSynonym?.name} </g:link></td>
									<td>${externalSynonym?.url}</td>
									<td>${externalSynonym?.attributes} </td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${valueDomainInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
