
<%@ page import="uk.co.mdc.catalogue.ConceptualDomain" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conceptualDomain.label', default: 'ConceptualDomain')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" CONCEPTUAL DOMAIN - ${conceptualDomainInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'conceptualDomain']">
				<g:hiddenField name="id" value="${conceptualDomainInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav nav-tabs">
						  		<li class="active"><g:link action="show" id="${conceptualDomainInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${conceptualDomainInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${conceptualDomainInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${conceptualDomainInstance?.name}')">Delete</a></li>
								<li><g:link action="list" ><g:message code="default.button.list.label" default="List" /></g:link></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${conceptualDomainInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" ><g:message code="conceptualDomain.name.label" default="Conceptual Domain" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${conceptualDomainInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${conceptualDomainInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" ><g:message code="conceptualDomain.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${conceptualDomainInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${conceptualDomainInstance?.valueDomains}">
					<tr>
						<td colspan="2"><span id="name-label" >Associated Value Domains</span></td>
					</tr>
					<tr>
						<td colspan="2">
							<table class="table table-bordered">
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
									<th>Data Type</th>
									<th>Unit of Measure</th>
		
								</tr>
							</thead>
							<g:each var="valueDomain" in="${conceptualDomainInstance.valueDomains}">
								<tr>
									<td><g:link action="show" controller="ValueDomain" id="${valueDomain?.id}">${valueDomain?.name} </g:link></td>
									<td>${valueDomain?.description}</td>
									<td><g:link action="show" controller="DataType" id="${valueDomain?.dataType?.id}">${valueDomain?.dataType?.name}</g:link></td>
									<td>${valueDomain?.unitOfMeasure} </td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${conceptualDomainInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
