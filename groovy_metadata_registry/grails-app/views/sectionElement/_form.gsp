<%@ page import="uk.co.mdc.forms.SectionElement" %>



<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'label', 'error')} ">
	<label for="label">
		<g:message code="sectionElement.label.label" default="Label" />
		
	</label>
	<g:textField name="label" value="${sectionElementInstance?.label}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'style', 'error')} ">
	<label for="style">
		<g:message code="sectionElement.style.label" default="Style" />
		
	</label>
	<g:textField name="style" value="${sectionElementInstance?.style}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'designOrder', 'error')} ">
	<label for="designOrder">
		<g:message code="sectionElement.designOrder.label" default="Design Order" />
		
	</label>
	<g:field name="designOrder" type="number" value="${sectionElementInstance.designOrder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="sectionElement.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${sectionElementInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'preText', 'error')} ">
	<label for="preText">
		<g:message code="sectionElement.preText.label" default="Pre Text" />
		
	</label>
	<g:textField name="preText" value="${sectionElementInstance?.preText}"/>
</div> 




<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'dataElementConcept', 'error')} ">
	<label for="dataElementConcept">
		<g:message code="sectionElement.dataElementConcept.label" default="Data Element Concept" />
		
	</label>
	<g:select id="dataElementConcept" name="dataElementConcept.id" from="${uk.co.mdc.model.DataElementConcept.list()}" optionKey="id" value="${sectionElementInstance?.dataElementConcept?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'formDesign', 'error')} required">
	<label for="formDesign">
		<g:message code="sectionElement.formDesign.label" default="Form Design" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="formDesign" name="formDesign.id" from="${uk.co.mdc.forms.FormDesign.list()}" optionKey="id" required="" value="${sectionElementInstance?.formDesign?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'formDesignElement', 'error')} required">
	<label for="formDesignElement">
		<g:message code="sectionElement.formDesignElement.label" default="Form Design Element" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="formDesignElement" name="formDesignElement.id" from="${uk.co.mdc.forms.FormDesignElement.list()}" optionKey="id" required="" value="${sectionElementInstance?.formDesignElement?.id}" class="many-to-one"/>
</div>



<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'presentationElements', 'error')} ">
	<label for="presentationElements">
		<g:message code="sectionElement.presentationElements.label" default="Presentation Elements" />
		
	</label>
	<g:select name="presentationElements" from="${uk.co.mdc.forms.PresentationElement.list()}" multiple="multiple" optionKey="id" size="5" value="${sectionElementInstance?.presentationElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sectionElementInstance, field: 'questionElements', 'error')} ">
	<label for="questionElements">
		<g:message code="sectionElement.questionElements.label" default="Question Elements" />
		
	</label>
	<g:select name="questionElements" from="${uk.co.mdc.forms.QuestionElement.list()}" multiple="multiple" optionKey="id" size="5" value="${sectionElementInstance?.questionElements*.id}" class="many-to-many"/>
</div>

