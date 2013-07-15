<%@ page import="uk.co.mdc.model.ConceptualDomain" %>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="conceptualDomain.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${conceptualDomainInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="conceptualDomain.description.label" default="Description" />
		
	</label>
	
	<g:textArea name="description" value="${conceptualDomainInstance?.description}" rows="5" cols="40"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="conceptualDomain.refId.label" default="Ref Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refId" type="number" value="${conceptualDomainInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'valueDomains', 'error')} ">
	<label for="valueDomains">
		<g:message code="conceptualDomain.valueDomains.label" default="Value Domains" />
		
	</label>
	
	<g:select
		name="valueDomains"
		noSelection="['': 'select one...']"
		from="${valueDomains}"
		value="${params.list('valueDomains')}"
		optionKey="id"
		optionValue="name"
		multiple="true"
		size="6"
	/>

</div>

