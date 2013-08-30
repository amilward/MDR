
<%@ page import="uk.co.mdc.model.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" DATA ELEMENT - ${dataElementInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'dataElement']">
				<g:hiddenField name="id" value="${dataElementInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${dataElementInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${dataElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${dataElementInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${dataElementInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${dataElementInstance?.refId}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.refId.label" default="Reference ID" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementInstance}" field="refId"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.name.label" default="Data Element" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.description}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementInstance}" field="description"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.definition}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.definition.label" default="Definition" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataElementInstance}" field="definition"/></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.dataElementConcept}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.dataElementConcept.label" default="Data Element Concept" /></span></td>
						<td class="right_col_show"><g:link controller="dataElementConcept" action="show" id="${dataElementInstance?.dataElementConcept?.id}">${dataElementInstance?.dataElementConcept?.name?.encodeAsHTML()}</g:link></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.parent}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.parent.label" default="Parent" /></span></td>
						<td class="right_col_show"><g:link controller="dataElement" action="show" id="${dataElementInstance?.parent?.id}">${dataElementInstance?.parent?.name?.encodeAsHTML()}</g:link></td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.dataElementValueDomains}">
					<tr>
							<td colspan="2"><span id="name-label" class="label">Associated Value Domains</span></td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Reference ID</th>
									<th>Description</th>
									<th>Data Type</th>
									<th>Unit of Measure</th>
									<th>Regex definition</th>
		
								</tr>
							</thead>
							<g:each var="valueDomain" in="${dataElementInstance.dataElementValueDomains()}">
								<tr>
									<td><g:link action="show" controller="ValueDomain" id="${valueDomain?.id}">${valueDomain?.name} </g:link></td>
									<td>${valueDomain?.refId}</td>
									<td>${valueDomain?.description}</td>
									<td><g:link action="show" controller="DataType" id="${valueDomain?.dataType?.id}">${valueDomain?.dataType?.name}</g:link></td>
									<td>${valueDomain?.unitOfMeasure} </td>
									<td>${valueDomain?.regexDef} </td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.subElements}">
					<tr>
						<td colspan="2"><span id="name-label" class="label">Sub Elements</span></td>
					</tr>
					<tr>
						<td colspan="2">
							<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Reference ID</th>
									<th>Description</th>
									<th>Definition</th>
								</tr>
							</thead>
							<g:each var="dataElement" in="${dataElementInstance.subElements}">
								<tr>
									<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
									<td>${dataElement?.refId}</td>
									<td>${dataElement?.description}</td>
									<td>${dataElement?.definition} </td>
								</tr>
							</g:each>
						</table>
						</td>
					</tr>
				</g:if>
				<g:if test="${dataElementInstance?.externalSynonyms}">
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
							<g:each var="externalSynonym" in="${dataElementInstance.externalSynonyms}">
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
		</div>
	</body>
</html>
