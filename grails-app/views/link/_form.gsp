<%@ page import="uk.co.mdc.pathways.Link" %>



<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'refId', 'error')} ">
	<label for="refId">
		<g:message code="link.refId.label" default="Ref Id" />
		
	</label>
	<g:textField name="refId" value="${linkInstance?.refId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="link.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${linkInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'pathwaysModel', 'error')} ">
	<label for="pathwaysModel">
		<g:message code="link.pathwaysModel.label" default="Pathways Model" />
		
	</label>
	<g:select id="pathwaysModel" name="pathwaysModel.id" from="${uk.co.mdc.pathways.PathwaysModel.list()}" optionKey="id" value="${linkInstance?.pathwaysModel?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: linkInstance, field: 'extension', 'error')} ">
	<label for="extension">
		<g:message code="link.extension.label" default="Extension" />
		
	</label>
	
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

