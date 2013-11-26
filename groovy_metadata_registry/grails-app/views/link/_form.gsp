<%@ page import="uk.co.mdc.pathways.Link" %>



<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'mandatoryInputs', 'error')} ">
	<label for="mandatoryInputs">
		<g:message code="link.mandatoryInputs.label" default="Mandatory Inputs" />
		
	</label>
	<g:select name="mandatoryInputs" from="${uk.co.mdc.model.Collection.list()}" multiple="multiple" optionKey="id" size="5" value="${linkInstance?.mandatoryInputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'mandatoryOutputs', 'error')} ">
	<label for="mandatoryOutputs">
		<g:message code="link.mandatoryOutputs.label" default="Mandatory Outputs" />
		
	</label>
	<g:select name="mandatoryOutputs" from="${uk.co.mdc.model.Collection.list()}" multiple="multiple" optionKey="id" size="5" value="${linkInstance?.mandatoryOutputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'optionalInputs', 'error')} ">
	<label for="optionalInputs">
		<g:message code="link.optionalInputs.label" default="Optional Inputs" />
		
	</label>
	<g:select name="optionalInputs" from="${uk.co.mdc.model.Collection.list()}" multiple="multiple" optionKey="id" size="5" value="${linkInstance?.optionalInputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'optionalOutputs', 'error')} ">
	<label for="optionalOutputs">
		<g:message code="link.optionalOutputs.label" default="Optional Outputs" />
		
	</label>
	<g:select name="optionalOutputs" from="${uk.co.mdc.model.Collection.list()}" multiple="multiple" optionKey="id" size="5" value="${linkInstance?.optionalOutputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="link.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${linkInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'peCollection', 'error')} ">
	<label for="peCollection">
		<g:message code="link.peCollection.label" default="Pe Collection" />
		
	</label>
	<g:select id="peCollection" name="peCollection.id" from="${uk.co.mdc.model.Collection.list()}" optionKey="id" value="${linkInstance?.peCollection?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="link.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${linkInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'source', 'error')} required">
	<label for="source">
		<g:message code="link.source.label" default="Source" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="source" name="source.id" from="${uk.co.mdc.pathways.Node.list()}" optionKey="id" required="" value="${linkInstance?.source?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'target', 'error')} required">
	<label for="target">
		<g:message code="link.target.label" default="Target" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="target" name="target.id" from="${uk.co.mdc.pathways.Node.list()}" optionKey="id" required="" value="${linkInstance?.target?.id}" class="many-to-one"/>
</div>

