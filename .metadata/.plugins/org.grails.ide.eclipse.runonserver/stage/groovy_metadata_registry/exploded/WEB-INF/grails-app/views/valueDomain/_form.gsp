<%@ page import="uk.co.mdc.model.ValueDomain" %>



<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="valueDomain.dataElements.label" default="Data Elements" />
		
	</label>
	<g:select name="dataElements" from="${uk.co.mdc.model.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${valueDomainInstance?.dataElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'datatype', 'error')} ">
	<label for="datatype">
		<g:message code="valueDomain.datatype.label" default="Datatype" />
		
	</label>
	<g:textField name="datatype" value="${valueDomainInstance?.datatype}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="valueDomain.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${valueDomainInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'refid', 'error')} required">
	<label for="refid">
		<g:message code="valueDomain.refid.label" default="Refid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="refid" type="number" value="${valueDomainInstance.refid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'regexDef', 'error')} ">
	<label for="regexDef">
		<g:message code="valueDomain.regexDef.label" default="Regex Def" />
		
	</label>
	<g:textField name="regexDef" value="${valueDomainInstance?.regexDef}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: valueDomainInstance, field: 'unitOfMeasure', 'error')} ">
	<label for="unitOfMeasure">
		<g:message code="valueDomain.unitOfMeasure.label" default="Unit Of Measure" />
		
	</label>
	<g:textField name="unitOfMeasure" value="${valueDomainInstance?.unitOfMeasure}"/>
</div>

