<%@ page import="uk.co.mdc.pathways.PathwaysModel" %>

<div class="fieldcontain ${hasErrors(bean: pathwaysModelInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="pathwaysModel.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${pathwaysModelInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pathwaysModelInstance, field: 'isDraft', 'error')} ">
	<label for="isDraft">
		<g:message code="pathwaysModel.isDraft.label" default="Is Draft" />
		
	</label>
	<g:checkBox name="isDraft" value="${pathwaysModelInstance?.isDraft}" />
</div>

<div class="fieldcontain ${hasErrors(bean: pathwaysModelInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="pathwaysModel.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${pathwaysModelInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pathwaysModelInstance, field: 'pathwayElements', 'error')} ">
	<label for="pathwayElements">
		<g:message code="pathwaysModel.pathwayElements.label" default="Pathway Elements" />
		
	</label>
	<g:select name="pathwayElements" from="${uk.co.mdc.pathways.PathwayElement.list()}" multiple="multiple" optionKey="id" size="5" value="${pathwaysModelInstance?.pathwayElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pathwaysModelInstance, field: 'userVersion', 'error')} ">
	<label for="versionNo">
		<g:message code="pathwaysModel.versionNo.label" default="Version No" />
		
	</label>
	<g:textField name="versionNo" value="${pathwaysModelInstance?.versionNo}"/>
</div>

