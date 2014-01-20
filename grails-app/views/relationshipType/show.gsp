
<%@ page import="uk.co.mdc.model.Relationship; uk.co.mdc.model.RelationshipType; uk.co.mdc.model.CatalogueElement;" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'relationship.label', default: 'Relationship')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" Relationship Type - ${relationshipTypeInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'relationship']">
				<g:hiddenField name="id" value="${relationshipTypeInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav nav-tabs">
						  		<li class="active"><g:link action="show" id="${relationshipTypeInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${relationshipTypeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${relationshipTypeInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${relationshipTypeInstance?.name}')">Delete</a></li>
							    <li><g:link action="list" ><g:message code="default.button.list.label" default="List" /></g:link></li>
							    <li><g:link action="create" controller="FormDesign" params='["relationshipId": "${relationshipTypeInstance?.id}","createFromRelationship": "true"]'>Generate Form Design</g:link></li>
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
				<g:if test="${relationshipTypeInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" ><g:message code="relationship.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${relationshipTypeInstance}" field="name"/></td>
					</tr>
				</g:if>
				
				<g:if test="${Relationship.findByRelationshipType(relationshipTypeInstance)}">

                        <tr>
                            <td colspan="2">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Object X</th>
                                        <th>Object Y</th>
                                    </tr>
                                    </thead>
                                    <g:each var="relationship" in="${Relationship.findAllByRelationshipType(relationshipTypeInstance)}">
                                        <tr>
                                         <td>${CatalogueElement.get(relationship.objectXId)?.name} (${CatalogueElement.get(relationship.objectXId)?.class}) </td>
                                            <td>${CatalogueElement.get(relationship.objectYId)?.name} (${CatalogueElement.get(relationship.objectYId)?.class}) </td>
                                        </tr>
                                    </g:each>
                                </table>
                            </td>
                        </tr>

				</g:if>
				
				
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${relationshipTypeInstance.id}" /></sec:ifAnyGranted>
			</div>
	</body>
</html>
