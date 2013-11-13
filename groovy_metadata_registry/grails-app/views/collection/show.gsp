
<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" COLLECTION - ${collectionInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'collection']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${collectionInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${collectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${collectionInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${collectionInstance?.name}')">Delete</a></li>
							    <li><g:link action="create" controller="FormDesign" params='["collectionId": "${collectionInstance?.id}","createFromCollection": "true"]'>Generate Form Design</g:link></li>
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
				<g:if test="${collectionInstance?.refId}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.collectionConcept.label" default="Reference Id" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${collectionInstance}" field="refId"/></td>
					</tr>
				</g:if>
				<g:if test="${collectionInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${collectionInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${collectionInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${collectionInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${collectionInstance?.dataElementCollections()}">
					<tr>
						<td colspan="2"><span id="name-label" class="label">Data Elements</span></td>
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
									<th>Schema Specification</th>
		
								</tr>
							</thead>
							<g:each var="dataElement" in="${collectionInstance.mandatoryDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td>Mandatory</td>
							</tr>
							</g:each>
							<g:each var="dataElement" in="${collectionInstance.requiredDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td>Required</td>
							</tr>
							</g:each>
							<g:each var="dataElement" in="${collectionInstance.optionalDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td>Optional</td>
							</tr>
							</g:each>
							<g:each var="dataElement" in="${collectionInstance.referenceDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
								<td>Reference (X)</td>
							</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${collectionInstance.id}" /></sec:ifAnyGranted>
			</div>
	</body>
</html>
