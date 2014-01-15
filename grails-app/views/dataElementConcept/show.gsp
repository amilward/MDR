
<%@ page import="uk.co.mdc.model.DataElementConcept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'dataElementConcept.label', default: 'Section (Data Element Concept)')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" DATA ELEMENT CONCEPT - ${dataElementConceptInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'dataElementConcept']">
				<g:hiddenField name="id" value="${dataElementConceptInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${dataElementConceptInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${dataElementConceptInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${dataElementConceptInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${dataElementConceptInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${dataElementConceptInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElementConcept.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementConceptInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementConceptInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElementConcept.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementConceptInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementConceptInstance?.parent}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElementConcept.parent.label" default="Parent" /></span></td>
						<td class="right_col_show"><g:link controller="dataElementConcept" action="show" id="${dataElementConceptInstance?.parent?.id}">${dataElementConceptInstance?.parent?.name?.encodeAsHTML()}</g:link></td>
					</tr>
				</g:if>
				
				<g:if test="${dataElementConceptInstance?.subConcepts}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">Sub Concepts</span></td>
						<td class="right_col_show">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Description</th>
								</tr>
							</thead>
							<g:each var="dataConcept" in="${dataElementConceptInstance.subConcepts}">
								<tr>
									<td><g:link action="show" controller="DataElementConcept" id="${dataConcept?.id}">${dataConcept?.name} </g:link></td>
									<td>${dataConcept?.description}</td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				<g:if test="${dataElementConceptInstance?.dataElements}">
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
									<th>Parent</th>
		
								</tr>
							</thead>
							<g:each var="dataElement" in="${dataElementConceptInstance.dataElements}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.description}</td>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.parent?.id}">${dataElement?.parent?.name} </g:link> </td>
							</tr>
						</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${dataElementConceptInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
