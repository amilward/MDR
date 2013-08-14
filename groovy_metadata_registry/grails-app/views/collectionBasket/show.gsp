
<%@ page import="uk.co.mdc.CollectionBasket" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collectionBasket.label', default: 'CollectionBasket')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-collectionBasket" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-collectionBasket" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:if test="${collectionBasketInstance?.dataElements}">
			<g:form action="saveBasketCollection" controller="Collection">
					<fieldset class="form">
						<div class="fieldcontain required">
							<label for="refId">
								<g:message code="collection.refId.label" default="Reference ID" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="refId" value="" required=""/>
						</div>
						
						<div class="fieldcontain">
							<label for="name">
								<g:message code="collection.name.label" default="Name" />
								
							</label>
							<g:textField name="name" value=""/>
						</div>
						
						<div class="fieldcontain">
							<label for="description">
								<g:message code="collection.description.label" default="Description" />
								
							</label>
							<g:textArea rows="5" cols="40" name="description" value=""/>
						</div>

						<table>
							<tr><th>Data Element</th><th>Mandatory</th><th>Required</th><th>Optional</th><th>Reference</th></tr>
							<g:each in="${collectionBasketInstance.dataElements}" var="d">
							<tr>
								<td><span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.name?.encodeAsHTML()}</g:link></span>
								<g:hiddenField name="dataElementIds" value="${d.id}" /> </td>
								<td><g:radio name="dataElement_${d.id}" value="mandatory"/></td>
								<td><g:radio name="dataElement_${d.id}" value="required"/></td>
								<td><g:radio name="dataElement_${d.id}" value="optional"/></td>
								<td><g:radio name="dataElement_${d.id}" value="reference"/></td>
							</tr>
							</g:each>
						</table>
					</fieldset>
					<fieldset class="buttons">
						<g:hiddenField name="collection_basket_id" value="${collectionBasketInstance?.id}" />
						<g:submitButton name="saveBasketCollection" class="save" value="Save Collection" />
					</fieldset>
				</g:form>
				</g:if>
		</div>
	</body>
</html>
