<%@ page import="uk.co.mdc.model.DataElement" %>



<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'definition', 'error')} ">
	<label for="definition">
		<g:message code="dataElement.definition.label" default="Definition" />
		
	</label>
	<g:textField name="definition" value="${dataElementInstance?.definition}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataElement.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${dataElementInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'parentId', 'error')} required">
	<label for="parentId">
		<g:message code="dataElement.parentId.label" default="Parent Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="parentId" type="number" value="${dataElementInstance.parentId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="dataElement.refId.label" default="Ref Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refId" type="number" value="${dataElementInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'subElements', 'error')} ">
	<label for="subElements">
		<g:message code="dataElement.subElements.label" default="Sub Elements" />
		
	</label>
	<g:select name="subElements" from="${uk.co.mdc.model.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${dataElementInstance?.subElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'vdId', 'error')} required">
	<label for="vdId">
		<g:message code="dataElement.vdId.label" default="Vd Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="vdId" type="number" value="${dataElementInstance.vdId}" required=""/>
</div>

