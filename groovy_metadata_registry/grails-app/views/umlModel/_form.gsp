<%@ page import="uk.co.mdc.model.UmlModel" %>



<div class="fieldcontain ${hasErrors(bean: umlModelInstance, field: 'conceptId', 'error')} required">
	<label for="conceptId">
		<g:message code="umlModel.conceptId.label" default="Concept Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="conceptId" type="number" value="${umlModelInstance.conceptId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: umlModelInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="umlModel.dataElements.label" default="Data Elements" />
		
	</label>
	<g:select name="dataElements" from="${uk.co.mdc.model.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${umlModelInstance?.dataElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: umlModelInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="umlModel.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${umlModelInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: umlModelInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="umlModel.refId.label" default="Ref Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refId" type="number" value="${umlModelInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: umlModelInstance, field: 'valueDomains', 'error')} ">
	<label for="valueDomains">
		<g:message code="umlModel.valueDomains.label" default="Value Domains" />
		
	</label>
	<g:select name="valueDomains" from="${uk.co.mdc.model.ValueDomain.list()}" multiple="multiple" optionKey="id" size="5" value="${umlModelInstance?.valueDomains*.id}" class="many-to-many"/>
</div>

