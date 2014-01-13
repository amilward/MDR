<%@ page import="uk.co.mdc.ModelBasket" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'collectionBasket.label', default: 'CollectionBasket')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" COLLECTION BASKET" />
	</head>
	<body>
		<div class="box">
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		<g:if test="${errors}">
			<p>${errors}</p>
		</g:if>
		<g:if test="${modelBasketInstance?.dataElements}">
		<div id="show-collectionBasket" class="content scaffold-show" role="main">
			
			<g:form action="saveBasketModel" controller="Model">
			
			<table class="table table-hovered">
				<tbody>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField value="${name}" name="name" required=""/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="description-label" class="label"><g:message code="collection.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea value="${description}" rows="5" cols="40" name="description" /></td>
					</tr>
					<tr>
						<td colspan="2">
						<table>
							<tr><th>Data Element</th><th>Mandatory</th><th>Required</th><th>Optional</th><th>Reference</th></tr>
							<g:each in="${modelBasketInstance.dataElements}" var="d">
							<tr>
								<td><span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.name?.encodeAsHTML()}</g:link></span>
								<g:hiddenField name="dataElementIds" value="${d.id}" /> </td>
								<td><g:radio checked="checked" name="dataElement_${d.id}" value="mandatory"/></td>
								<td><g:radio name="dataElement_${d.id}" value="required"/></td>
								<td><g:radio name="dataElement_${d.id}" value="optional"/></td>
								<td><g:radio name="dataElement_${d.id}" value="reference"/></td>
							</tr>
							</g:each>
						</table>
					</td>
				</tbody>
			</table>
			
					<fieldset class="buttons">
						<g:hiddenField name="collection_basket_id" value="${modelBasketInstance?.id}" />
						<g:submitButton name="saveBasketCollection" class="save" value="Save Collection" />
					</fieldset>
				</g:form>
				</div>
				</g:if>
		</div>
	
	</body>
</html>
