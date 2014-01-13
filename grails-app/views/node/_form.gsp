<%@ page import="uk.co.mdc.pathways.Node" %>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="node.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${nodeInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'pathwaysModel', 'error')} ">
	<label for="pathwaysModel">
		<g:message code="node.pathwaysModel.label" default="Pathways Model" />
		
	</label>
	<g:select id="pathwaysModel" name="pathwaysModel.id" from="${uk.co.mdc.pathways.PathwaysModel.list()}" optionKey="id" value="${nodeInstance?.pathwaysModel?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'x', 'error')} ">
	<label for="x">
		<g:message code="node.x.label" default="X" />
		
	</label>
	<g:textField name="x" value="${nodeInstance?.x}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'y', 'error')} ">
	<label for="y">
		<g:message code="node.y.label" default="Y" />
		
	</label>
	<g:textField name="y" value="${nodeInstance?.y}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'mandatoryInputs', 'error')} ">
	<label for="mandatoryInputs">
		<g:message code="node.mandatoryInputs.label" default="Mandatory Inputs" />
		
	</label>
	<g:select name="mandatoryInputs" from="${uk.co.mdc.model.Model.list()}" multiple="multiple" optionKey="id" size="5" value="${nodeInstance?.mandatoryInputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'mandatoryOutputs', 'error')} ">
	<label for="mandatoryOutputs">
		<g:message code="node.mandatoryOutputs.label" default="Mandatory Outputs" />
		
	</label>
	<g:select name="mandatoryOutputs" from="${uk.co.mdc.model.Model.list()}" multiple="multiple" optionKey="id" size="5" value="${nodeInstance?.mandatoryOutputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="node.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${nodeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'optionalInputs', 'error')} ">
	<label for="optionalInputs">
		<g:message code="node.optionalInputs.label" default="Optional Inputs" />
		
	</label>
	<g:select name="optionalInputs" from="${uk.co.mdc.model.Model.list()}" multiple="multiple" optionKey="id" size="5" value="${nodeInstance?.optionalInputs*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nodeInstance, field: 'optionalOutputs', 'error')} ">
	<label for="optionalOutputs">
		<g:message code="node.optionalOutputs.label" default="Optional Outputs" />
		
	</label>
	<g:select name="optionalOutputs" from="${uk.co.mdc.model.Model.list()}" multiple="multiple" optionKey="id" size="5" value="${nodeInstance?.optionalOutputs*.id}" class="many-to-many"/>
</div>

