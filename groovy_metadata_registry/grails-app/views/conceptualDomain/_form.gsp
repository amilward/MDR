<%@ page import="uk.co.mdc.model.ConceptualDomain" %>



<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="conceptualDomain.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${conceptualDomainInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'refid', 'error')} required">
	<label for="refid">
		<g:message code="conceptualDomain.refid.label" default="Refid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refid" type="number" value="${conceptualDomainInstance.refid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptualDomainInstance, field: 'valueDomains', 'error')} ">
	<label for="valueDomains">
		<g:message code="conceptualDomain.valueDomains.label" default="Value Domains" />
		
	</label>
	<g:select name="valueDomains" from="${uk.co.mdc.model.ValueDomain.list()}" multiple="multiple" optionKey="id" size="5" value="${conceptualDomainInstance?.valueDomains*.id}" class="many-to-many"/>
</div>

