<%@ page import="uk.co.mdc.model.Collection" %>


<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="collection.refId.label" default="Reference ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="refId" value="${collectionInstance?.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="collection.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${collectionInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="collection.description.label" default="Description" />
		
	</label>
	<g:textArea rows="5" cols="40" name="description" value="${collectionInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'dataElementCollections', 'error')} ">
        <label for="dataElementCollections">
                <g:message code="collection.dataElementCollections.label" default="Add Mandatory Data Elements" />
                
        </label>
        
        <g:select
                        name="mandatoryDataElements"
                        noSelection="${['':'Select One...']}"
                        from="${dataElements}"
                        value="${params.list('dataElements')}"
                        optionKey="id"
                        optionValue="name"
                        multiple="true"
                        size="6"
                />

</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'dataElementCollections', 'error')} ">
        <label for="dataElementCollections">
                <g:message code="collection.dataElementCollections.label" default="Add Required Data Elements" />
                
        </label>
        
        <g:select
                        name="requiredDataElements"
                        noSelection="${['':'Select One...']}"
                        from="${dataElements}"
                        value="${params.list('dataElements')}"
                        optionKey="id"
                        optionValue="name"
                        multiple="true"
                        size="6"
                />

</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'dataElementCollections', 'error')} ">
        <label for="dataElementCollections">
                <g:message code="collection.dataElementCollections.label" default="Add Optional Data Elements" />
                
        </label>
        
        <g:select
                        name="optionalDataElements"
                        noSelection="${['':'Select One...']}"
                        from="${dataElements}"
                        value="${params.list('dataElements')}"
                        optionKey="id"
                        optionValue="name"
                        multiple="true"
                        size="6"
                />

</div>

<div class="fieldcontain ${hasErrors(bean: collectionInstance, field: 'dataElementCollections', 'error')} ">
        <label for="dataElementCollections">
                <g:message code="collection.dataElementCollections.label" default="Add Reference (X) Data Elements" />
                
        </label>
        
        <g:select
                        name="referenceDataElements"
                        noSelection="${['':'Select One...']}"
                        from="${dataElements}"
                        value="${params.list('dataElements')}"
                        optionKey="id"
                        optionValue="name"
                        multiple="true"
                        size="6"
                />

</div>


