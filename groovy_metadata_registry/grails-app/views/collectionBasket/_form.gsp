<%@ page import="uk.co.mdc.CollectionBasket" %>



<div class="fieldcontain ${hasErrors(bean: collectionBasketInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="collectionBasket.dataElements.label" default="Data Elements" />
		
	</label>
	<g:select name="dataElements" from="${uk.co.mdc.model.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${collectionBasketInstance?.dataElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: collectionBasketInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="collectionBasket.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${uk.co.mdc.SecUser.list()}" optionKey="id" required="" value="${collectionBasketInstance?.user?.id}" class="many-to-one"/>
</div>

