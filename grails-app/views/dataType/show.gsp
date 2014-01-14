
<%@ page import="uk.co.mdc.model.DataType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="metadata_curation">
		<g:set var="entityName" value="${message(code: 'dataType.label', default: 'DataType')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" DATA TYPE - ${dataTypeInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'dataType']">
				<g:hiddenField name="id" value="${dataTypeInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${dataTypeInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${dataTypeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${dataTypeInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${dataTypeInstance?.name}')">Delete</a></li>
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
			<table class="table table-hovered">
				<tbody>
				<g:if test="${dataTypeInstance?.name}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataType.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:fieldValue bean="${dataTypeInstance}" field="name"/></td>
					</tr>
				</g:if>
				<g:if test="${dataTypeInstance?.enumerated}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataType.enumerated.label" default="Enumerated" /></span></td>
						<td class="right_col_show"><g:formatBoolean boolean="${dataTypeInstance?.enumerated}" /></td>
					</tr>
				</g:if>
				
				<g:if test="${dataTypeInstance?.enumerations}">
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">Enumerations</span></td>
						<td class="right_col_show">
							<table>
							<thead>
								<tr>
									<th>Value / Code</th>
									<th>Description / Definition</th>
								</tr>
							</thead>
							<g:each var="enumeratedValue" in="${dataTypeInstance.enumerations}">
								<tr>
									<td>${enumeratedValue?.key}</td>
									<td>${enumeratedValue?.value}</td>
								</tr>
							</g:each>
							</table>
						</td>
					</tr>
				</g:if>
				</tbody>
			</table>
			<sec:ifAnyGranted roles="ROLE_ADMIN"><g:objectHistory persistedObjectId="${dataTypeInstance.id}" /></sec:ifAnyGranted>
		</div>
	</body>
</html>
