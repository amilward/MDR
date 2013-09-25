<%@ page import="uk.co.mdc.forms.InputField" %>



<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'defaultValue', 'error')} ">
	<label for="defaultValue">
		<g:message code="inputField.defaultValue.label" default="Default Value" />
		
	</label>
	<g:textField name="defaultValue" value="${inputFieldInstance?.defaultValue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'placeholder', 'error')} ">
	<label for="placeholder">
		<g:message code="inputField.placeholder.label" default="Placeholder" />
		
	</label>
	<g:textField name="placeholder" value="${inputFieldInstance?.placeholder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'maxCharacters', 'error')} ">
	<label for="maxCharacters">
		<g:message code="inputField.maxCharacters.label" default="Max Characters" />
		
	</label>
	<g:field name="maxCharacters" type="number" value="${inputFieldInstance.maxCharacters}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'unitOfMeasure', 'error')} ">
	<label for="unitOfMeasure">
		<g:message code="inputField.unitOfMeasure.label" default="Unit Of Measure" />
		
	</label>
	<g:textField name="unitOfMeasure" value="${inputFieldInstance?.unitOfMeasure}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'dataType', 'error')} ">
	<label for="dataType">
		<g:message code="inputField.dataType.label" default="Data Type" />
		
	</label>
	<g:select id="dataType" name="dataType.id" from="${uk.co.mdc.model.DataType.list()}" optionKey="id" value="${inputFieldInstance?.dataType?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'format', 'error')} ">
	<label for="format">
		<g:message code="inputField.format.label" default="Format" />
		
	</label>
	<g:textField name="format" value="${inputFieldInstance?.format}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'options', 'error')} ">
	<label for="options">
		<g:message code="inputField.options.label" default="Options" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'instructions', 'error')} ">
	<label for="instructions">
		<g:message code="inputField.instructions.label" default="Instructions" />
		
	</label>
	<g:textField name="instructions" value="${inputFieldInstance?.instructions}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inputFieldInstance, field: 'renderType', 'error')} ">
	<label for="renderType">
		<g:message code="inputField.renderType.label" default="Render Type" />
		
	</label>
	<g:textField name="renderType" value="${inputFieldInstance?.renderType}"/>
</div>

