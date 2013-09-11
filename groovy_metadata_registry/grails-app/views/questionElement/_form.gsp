<%@ page import="uk.co.mdc.forms.QuestionElement" %>



<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'label', 'error')} ">
	<label for="label">
		<g:message code="questionElement.label.label" default="Label" />
		
	</label>
	<g:textField name="label" value="${questionElementInstance?.label}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'style', 'error')} ">
	<label for="style">
		<g:message code="questionElement.style.label" default="Style" />
		
	</label>
	<g:textField name="style" value="${questionElementInstance?.style}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'designOrder', 'error')} ">
	<label for="designOrder">
		<g:message code="questionElement.designOrder.label" default="Design Order" />
		
	</label>
	<g:field name="designOrder" type="number" value="${questionElementInstance.designOrder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="questionElement.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${questionElementInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'dataElement', 'error')} ">
	<label for="dataElement">
		<g:message code="questionElement.dataElement.label" default="Data Element" />
		
	</label>
	<g:select id="dataElement" name="dataElement.id" from="${uk.co.mdc.model.DataElement.list()}" optionKey="id" value="${questionElementInstance?.dataElement?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'valueDomain', 'error')} ">
	<label for="valueDomain">
		<g:message code="questionElement.valueDomain.label" default="Value Domain" />
		
	</label>
	<g:select id="valueDomain" name="valueDomain.id" from="${uk.co.mdc.model.ValueDomain.list()}" optionKey="id" value="${questionElementInstance?.valueDomain?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'inputField', 'error')} required">
	<label for="inputField">
		<g:message code="questionElement.inputField.label" default="Input Field" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="inputField" name="inputField.id" from="${uk.co.mdc.forms.InputField.list()}" optionKey="id" required="" value="${questionElementInstance?.inputField?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'formDesign', 'error')} required">
	<label for="formDesign">
		<g:message code="questionElement.formDesign.label" default="Form Design" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="formDesign" name="formDesign.id" from="${uk.co.mdc.forms.FormDesign.list()}" optionKey="id" required="" value="${questionElementInstance?.formDesign?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionElementInstance, field: 'preText', 'error')} ">
	<label for="preText">
		<g:message code="questionElement.preText.label" default="Pre Text" />
		
	</label>
	<g:textField name="preText" value="${questionElementInstance?.preText}"/>
</div>

