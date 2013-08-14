
<%@ page import="uk.co.mdc.model.Collection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collection.label', default: 'Collection')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-collection" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-collection" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list collection">
			
			<g:if test="${collectionInstance?.refId}">
				<li class="fieldcontain">
					<span id="refId-label" class="property-label"><g:message code="collection.refId.label" default="Reference Id" /></span>
					
						<span class="property-value" aria-labelledby="refId-label"><g:fieldValue bean="${collectionInstance}" field="refId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${collectionInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="collection.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${collectionInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${collectionInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="collection.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${collectionInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				
			
			</ol>
			
			<g:if test="${collectionInstance.mandatoryDataElementCollections()!=null}">
			
				<h1>Mandatory Data Elements in collection:</h1>
				<table>
						<thead>
							<tr>
								<th>Name</th>
								<th>Reference ID</th>
								<th>Description</th>
								<th>Definition</th>
								<th>SubElements</th>
	
							</tr>
						</thead>
						<g:each var="dataElement" in="${collectionInstance.mandatoryDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:each var="subElement" in="${dataElement.subElements}">
										<g:link action="show" controller="DataElement" id="${subElement?.id}">${subElement?.name} </g:link> </br>
								</g:each></td>
							</tr>
						</g:each>
				</table>
			</g:if>	
			
			<g:if test="${collectionInstance.requiredDataElementCollections()!=null}">
			
				<h1>Required Data Elements in collection:</h1>
				<table>
						<thead>
							<tr>
								<th>Name</th>
								<th>Reference ID</th>
								<th>Description</th>
								<th>Definition</th>
								<th>SubElements</th>
	
							</tr>
						</thead>
						<g:each var="dataElement" in="${collectionInstance.requiredDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td>
								<ul>
									<g:each var="subElement" in="${dataElement.subElements}">
										<li><g:link action="show" controller="DataElement" id="${subElement?.id}">${subElement?.name} </g:link></li>
									</g:each>
								</ul>
								</td>
							</tr>
						</g:each>
				</table>
			</g:if>	
			
			<g:if test="${collectionInstance.optionalDataElementCollections()!=null}">
			
				<h1>Optional Data Elements in collection:</h1>
				<table>
						<thead>
							<tr>
								<th>Name</th>
								<th>Reference ID</th>
								<th>Description</th>
								<th>Definition</th>
								<th>SubElements</th>
	
							</tr>
						</thead>
						<g:each var="dataElement" in="${collectionInstance.optionalDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:each var="subElement" in="${dataElement.subElements}">
										<g:link action="show" controller="DataElement" id="${subElement?.id}">${subElement?.name} </g:link> </br>
								</g:each></td>
							</tr>
						</g:each>
				</table>
			</g:if>	
			
			<g:if test="${collectionInstance.referenceDataElementCollections()!=null}">
			
				<h1>Reference (x) Data Elements in collection:</h1>
				<table>
						<thead>
							<tr>
								<th>Name</th>
								<th>Reference ID</th>
								<th>Description</th>
								<th>Definition</th>
								<th>SubElements</th>
	
							</tr>
						</thead>
						<g:each var="dataElement" in="${collectionInstance.referenceDataElementCollections()}">
							<tr>
								<td><g:link action="show" controller="DataElement" id="${dataElement?.id}">${dataElement?.name} </g:link></td>
								<td>${dataElement?.refId}</td>
								<td>${dataElement?.description}</td>
								<td>${dataElement?.definition} </td>
								<td><g:each var="subElement" in="${dataElement.subElements}">
										<g:link action="show" controller="DataElement" id="${subElement?.id}">${subElement?.name} </g:link> </br>
								</g:each></td>
							</tr>
						</g:each>
				</table>
			</g:if>	
			
			
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${collectionInstance?.id}" />
					<g:link class="create" action="create" controller="FormSpecification" params="[collectionId: "${collectionInstance?.id}"]">Generate Form</g:link>
					<g:link class="edit" action="edit" id="${collectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
