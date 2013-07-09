<%@ page import="uk.co.mdc.model.ValueDomain" %>



<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="valueDomain.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${valueDomainInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'refId', 'error')} required">
	<label for="refId">
		<g:message code="valueDomain.refId.label" default="Ref Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refId" type="number" value="${valueDomainInstance.refId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="valueDomain.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${valueDomainInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'dataType', 'error')} ">
	<label for="dataType">
		<g:message code="valueDomain.dataType.label" default="Data Type" />
		
	</label>
	<g:textField name="dataType" value="${valueDomainInstance?.dataType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'unitOfMeasure', 'error')} ">
	<label for="unitOfMeasure">
		<g:message code="valueDomain.unitOfMeasure.label" default="Unit Of Measure" />
		
	</label>
	<g:textField name="unitOfMeasure" value="${valueDomainInstance?.unitOfMeasure}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'regexDef', 'error')} ">
	<label for="regexDef">
		<g:message code="valueDomain.regexDef.label" default="Regex Def" />
		
	</label>
	<g:textField name="regexDef" value="${valueDomainInstance?.regexDef}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'dataElementValueDomains', 'error')} ">
	<label for="dataElementValueDomains">
		<g:message code="dataElement.dataElementValueDomains.label" default="Add Associated Value Domain" />
		
	</label>
	
	<g:select
			name="dataElements"
			from="${dataElements}"
			value="${params.list('dataElements')}"
			optionKey="id"
			optionValue="name"
			multiple="true"
			size="6"
		/>

</div>



