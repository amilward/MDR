<%@ page import="uk.co.mdc.model.DataElement" %>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataElement.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dataElementInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="dataElement.refId.label" default="Ref Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refId" type="number" value="${dataElementInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataElement.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${dataElementInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'definition', 'error')} ">
	<label for="definition">
		<g:message code="dataElement.definition.label" default="Definition" />
		
	</label>
	<g:textField name="definition" value="${dataElementInstance?.definition}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="dataElement.parent.label" default="Parent" />
		
	</label>
	<g:select id="parent" name="parent.id" from="${uk.co.mdc.model.DataElement.list()}" optionKey="id" value="${dataElementInstance?.parent?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'dataElementValueDomains', 'error')} ">
	<label for="dataElementValueDomains">
		<g:message code="dataElement.dataElementValueDomains.label" default="Add Associated Value Domain" />
		
	</label>
	
	<g:select
			name="valueDomains"
			from="${valueDomains}"
			value="${params.list('valueDomains')}"
			optionKey="id"
			optionValue="name"
			multiple="true"
			size="6"
		/>

</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'subElements', 'error')} ">
	<label for="subElements">
		<g:message code="dataElement.subElements.label" default="Sub Elements" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${dataElementInstance?.subElements?}" var="s">
    <li><g:link controller="dataElement" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="dataElement" action="create" params="['dataElement.id': dataElementInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'dataElement.label', default: 'DataElement')])}</g:link>
</li>
</ul>

</div>

