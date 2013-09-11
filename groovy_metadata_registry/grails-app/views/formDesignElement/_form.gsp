<%@ page import="uk.co.mdc.forms.FormDesignElement" %>



<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'label', 'error')} ">
	<label for="label">
		<g:message code="formDesignElement.label.label" default="Label" />
		
	</label>
	<g:textField name="label" value="${formDesignElementInstance?.label}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'style', 'error')} ">
	<label for="style">
		<g:message code="formDesignElement.style.label" default="Style" />
		
	</label>
	<g:textField name="style" value="${formDesignElementInstance?.style}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'designOrder', 'error')} ">
	<label for="designOrder">
		<g:message code="formDesignElement.designOrder.label" default="Design Order" />
		
	</label>
	<g:field name="designOrder" type="number" value="${formDesignElementInstance.designOrder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="formDesignElement.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${formDesignElementInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'formDesign', 'error')} required">
	<label for="formDesign">
		<g:message code="formDesignElement.formDesign.label" default="Form Design" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="formDesign" name="formDesign.id" from="${uk.co.mdc.forms.FormDesign.list()}" optionKey="id" required="" value="${formDesignElementInstance?.formDesign?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: formDesignElementInstance, field: 'preText', 'error')} ">
	<label for="preText">
		<g:message code="formDesignElement.preText.label" default="Pre Text" />
		
	</label>
	<g:textField name="preText" value="${formDesignElementInstance?.preText}"/>
</div>

