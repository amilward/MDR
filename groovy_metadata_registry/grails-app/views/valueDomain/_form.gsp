<%@ page import="uk.co.mdc.model.ValueDomain" %>

<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: valueDomainInstance, field: 'refId', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.refId.label" default="Reference ID" /></span></td>
						<td class="right_col_show"><g:textField title="${g.message(code:'tooltip.valueDomain.refId')}" class="input-large input-block-level" name="refId" value="${valueDomainInstance.refId}" required=""/></td>
					</tr>
					<tr class="${hasErrors(bean: valueDomainInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.valueDomain.name" class="input-large input-block-level"  name="name" value="${valueDomainInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: valueDomainInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.valueDomain.description" class="input-block-level" name="description" value="${valueDomainInstance?.description}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.conceptualDomain.label" default="Conceptual Domain" /></span></td>
						<td class="right_col_show">
						<g:select id="conceptualDomain" 
							name="conceptualDomain.id" 
							from="${uk.co.mdc.model.ConceptualDomain.list()}" 
							noSelection="['': 'select one...']"
							optionKey="id" 
							optionValue="name" 
							value="${valueDomainInstance?.conceptualDomain?.id}" 
							class="many-to-one"/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.dataType.label" default="Data Type" /></span></td>
						<td>
						<g:select
                        name="dataType"
                        from="${dataTypes}"
                        noSelection="['': 'select one...']"
                        value="${valueDomainInstance?.dataType?.id}"
                        optionKey="id"
                        optionValue="name"
      					/>
						</td>
					</tr>
					<tr class="${hasErrors(bean: valueDomainInstance, field: 'unitOfMeasure', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.unitOfMeasure.label" default="Unit Of Measure" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.valueDomain.unitOfMeasure" class="input-block-level" name="unitOfMeasure" value="${valueDomainInstance?.unitOfMeasure}"/></td>
					</tr>
					<tr class="${hasErrors(bean: valueDomainInstance, field: 'regexDef', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="valueDomain.regexDef.label" default="regexDef" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.valueDomain.regexDef" class="input-block-level" name="regexDef" value="${valueDomainInstance?.regexDef}"/></td>
					</tr>
					
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">Associated Data Elements</span></td>
						<td class="right_col_show">
							<g:select
								title="tooltip.valueDomain.dataElements"
								name="dataElements"
								id="dataElements"
								noSelection="${['':'Select One...']}"
								from="${dataElements}"
								value="${params.list('dataElements')}"
								optionKey="id"
								optionValue="name"
								multiple="true"
								size="6"
							/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">External References</span></td>
						<td class="right_col_show">
						<g:select
								title="tooltip.valueDomain.externalReferences"
					            name="externalReferences"
					            id="externalReferences"
					            noSelection="${['':'Select One...']}"
					            from="${externalReferences}"
					            value="${params.list('externalReferences')}"
					            optionKey="id"
					            optionValue="name"
					            multiple="true"
					            size="6"
					    />
						</td>
					</tr>
				</tbody>
			</table>
<g:javascript library="dualListBox"/>	
<r:script disposition="defer">

	<g:if test="${!selectedDataElements}">
		selectedDataElements = ' '
	</g:if>
	<g:else>
		selectedDataElements = ${selectedDataElements*.id}
	</g:else>

	<g:if test="${!valueDomainInstance?.externalReferences}">
		externalReferences = ' '
	</g:if>
	<g:else>
		externalReferences = ${valueDomainInstance?.externalReferences*.id}
	</g:else>

	$(document).ready(function() {
		valueDomainForm(selectedDataElements, externalReferences);
    });
				
</r:script>




