
<%@ page import="uk.co.mdc.model.Model" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'model.label', default: 'Model')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" COLLECTION - ${modelInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'model']">
				<g:hiddenField name="id" value="${modelInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${modelInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${modelInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${modelInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${modelInstance?.name}')">Delete</a></li>
							    <li><g:link action="create" controller="FormDesign" params='["modelId": "${modelInstance?.id}","createFromModel": "true"]'>Generate Form Design</g:link></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-hovered">
				<tbody>
				<g:if test="${modelInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${modelInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${modelInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${modelInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${modelInstance?.relations()}">
					<tr>
						<td colspan="2"><span id="name-label" class="label">Data Elements</span></td>
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
							<g:each var="relation" in="${modelInstance.relations('MandatoryModelElement')}">
							<tr>
								<td><g:link action="show" controller="${relation.getClass().getSimpleName()}" id="${relation?.id}">${relation?.name} </g:link></td>
									<td>${relation?.description}</td>
									<td>${relation?.relationshipType}</td>
							</tr>
							</g:each>
							
							<g:each var="relation" in="${modelInstance.relations('RequiredModelElement')}">
							<tr>
								<td><g:link action="show" controller="${relation.getClass().getSimpleName()}" id="${relation?.id}">${relation?.name} </g:link></td>
									<td>${relation?.description}</td>
									<td>${relation?.relationshipType}</td>
							</tr>
							</g:each>
							<g:each var="relation" in="${modelInstance.relations('OptionalModelElement')}">
							<tr>
								<td><g:link action="show" controller="${relation.getClass().getSimpleName()}" id="${relation?.id}">${relation?.name} </g:link></td>
									<td>${relation?.description}</td>
									<td>${relation?.relationshipType}</td>
							</tr>
							</g:each>
							<g:each var="relation" in="${modelInstance.relations('ReferenceModelElement')}">
							<tr>
								<td><g:link action="show" controller="${relation.getClass().getSimpleName()}" id="${relation?.id}">${relation?.name} </g:link></td>
									<td>${relation?.description}</td>
									<td>${relation?.relationshipType}</td>
							</tr>
							</g:each>
							
						</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${modelInstance.id}" /></sec:ifAnyGranted>
			</div>
	</body>
</html>
