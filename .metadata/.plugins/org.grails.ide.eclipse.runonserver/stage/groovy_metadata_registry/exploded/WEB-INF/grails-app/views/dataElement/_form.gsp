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
	<g:textField name="refId" value="${dataElementInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataElement.description.label" default="Description" />
		
	</label>
	<g:textArea rows="5" cols="40" name="description" value="${dataElementInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'definition', 'error')} ">
	<label for="definition">
		<g:message code="dataElement.definition.label" default="Definition" />
		
	</label>
	<g:textArea rows="5" cols="40" name="definition" value="${dataElementInstance?.definition}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'dataElementConcept', 'error')} ">
	<label for="dataElementConcept">
		<g:message code="dataElement.dataElementConcept.label" default="Data Element Concept" />
		
	</label>
	<g:select 
			id="dataElementConcept" 
			name="dataElementConcept.id" 
			from="${uk.co.mdc.model.DataElementConcept.list()}" 
			optionKey="id" 
			optionValue="name"
			value="${dataElementInstance?.dataElementConcept?.id}" 
			class="many-to-one" 
			noSelection="${[null:'Select One...']}"
			/>
</div>


<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="dataElement.parent.label" default="Parent" />
		
	</label>
	<g:select 
			id="parent" 
			name="parent.id" 
			from="${uk.co.mdc.model.DataElement.list().minus(dataElementInstance)}" 
			optionKey="id" 
			optionValue="name"
			value="${dataElementInstance?.parent?.id}" 
			class="many-to-one" 
			noSelection="${[null:'Select One...']}"
			/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'dataElementValueDomains', 'error')} ">
	<label for="dataElementValueDomains">
		<g:message code="dataElement.dataElementValueDomains.label" default="Add Associated Value Domain" />
		
	</label>
	
	<g:select
			name="valueDomains"
			noSelection="${['':'Select One...']}"
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
	
   <g:select
            name="subElements"
            noSelection="${['':'Select One...']}"
            from="${dataElements.minus(dataElementInstance)}"
            value="${params.list('dataElements')}"
            optionKey="id"
            optionValue="name"
            multiple="true"
            size="6"
    />

</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'externalSynonyms', 'error')} ">
	<label for="subElements">
		<g:message code="dataElement.externalSynonyms.label" default="External Synonyms" />
		
	</label>
	
   <g:select
            name="externalSynonyms"
            noSelection="${['':'Select One...']}"
            from="${externalSynonyms}"
            value="${params.list('externalSynonyms')}"
            optionKey="id"
            optionValue="name"
            multiple="true"
            size="6"
    />

</div>




