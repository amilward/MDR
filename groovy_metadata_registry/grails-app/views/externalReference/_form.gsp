<%@ page import="uk.co.mdc.model.ExternalReference" %>



<div class="fieldcontain ${hasErrors(bean: externalReferenceInstance, field: 'attributes', 'error')} ">
	<label for="attributes">
		<g:message code="externalReference.attributes.label" default="Attributes" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: externalReferenceInstance, field: 'externalIdentifier', 'error')} ">
	<label for="externalIdentifier">
		<g:message code="externalReference.externalIdentifier.label" default="External Identifier" />
		
	</label>
	<g:textField name="externalIdentifier" value="${externalReferenceInstance?.externalIdentifier}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: externalReferenceInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="externalReference.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${externalReferenceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: externalReferenceInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="externalReference.url.label" default="Url" />
		
	</label>
	<g:textField name="url" value="${externalReferenceInstance?.url}"/>
</div>

