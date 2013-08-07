<%@ page import="uk.co.mdc.model.DataElementConcept" %>



<div class="fieldcontain ${hasErrors(bean: dataElementConceptInstance, field: 'parent', 'error')} ">
	<label for="parent">
		<g:message code="dataElementConcept.parent.label" default="Parent" />
		
	</label>
	<g:select 
			id="parent"
			name="parent.id" 
            noSelection="${['':'Select One...']}"
            from="${dataElementConcepts.minus(dataElementConceptInstance)}"
            value="${dataElementConceptInstance?.parent?.id}"
            optionKey="id"
            optionValue="name" 
			class="many-to-one"
	/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementConceptInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataElementConcept.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dataElementConceptInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'refId', 'error')} ">
	<label for="refId">
		<g:message code="dataElementConcept.refId.label" default="Reference ID" />
	</label>
	<g:textField name="refId" value="${dataElementConceptInstance?.refId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementConceptInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="dataElementConcept.description.label" default="Description" />
		
	</label>
	<g:textArea rows="5" cols="40" name="description" value="${dataElementConceptInstance?.description}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: dataElementConceptInstance, field: 'subConcepts', 'error')} ">
	<label for="subConcepts">
		<g:message code="dataElementConcept.subConcepts.label" default="Sub Concepts" />
		
	</label>
	
   <g:select
            name="subConcepts"
            noSelection="${['':'Select One...']}"
            from="${dataElementConcepts.minus(dataElementConceptInstance)}"
            value="${params.list('dataElementConcepts')}"
            optionKey="id"
            optionValue="name"
            multiple="true"
            size="6"
    />
    
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementConceptInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="dataElementConcept.dataElements.label" default="Data Elements" />
		
	</label>
	
   <g:select
            name="dataElements"
            noSelection="${['':'Select One...']}"
            from="${dataElements}"
            value="${params.list('dataElements')}"
            optionKey="id"
            optionValue="name"
            multiple="true"
            size="6"
    />
    
</div>



