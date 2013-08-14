<%@ page import="uk.co.mdc.forms.Field" %>



<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'caption', 'error')} ">
	<label for="caption">
		<g:message code="field.caption.label" default="Caption" />
		
	</label>
	<g:textField name="caption" value="${fieldInstance?.caption}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'field_class', 'error')} ">
	<label for="field_class">
		<g:message code="field.field_class.label" default="Fieldclass" />
		
	</label>
	<g:textField name="field_class" value="${fieldInstance?.field_class}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'field_id', 'error')} ">
	<label for="field_id">
		<g:message code="field.field_id.label" default="Fieldid" />
		
	</label>
	<g:textField name="field_id" value="${fieldInstance?.field_id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="field.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${fieldInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'options', 'error')} ">
	<label for="options">
		<g:message code="field.options.label" default="Options" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'placeholder', 'error')} ">
	<label for="placeholder">
		<g:message code="field.placeholder.label" default="Placeholder" />
		
	</label>
	<g:textField name="placeholder" value="${fieldInstance?.placeholder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'size', 'error')} required">
	<label for="size">
		<g:message code="field.size.label" default="Size" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="size" type="number" value="${fieldInstance.size}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="field.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${uk.co.mdc.forms.FieldType?.values()}" keys="${uk.co.mdc.forms.FieldType.values()*.name()}" required="" value="${fieldInstance?.type?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fieldInstance, field: 'value', 'error')} ">
	<label for="value">
		<g:message code="field.value.label" default="Value" />
		
	</label>
	<g:textField name="value" value="${fieldInstance?.value}"/>
</div>

