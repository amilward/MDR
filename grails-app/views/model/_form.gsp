<%@ page import="uk.co.mdc.model.Model" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: modelInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.model.name" class="input-large input-block-level"  name="name" value="${modelInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: modelInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.model.description" class="input-block-level" name="description" value="${modelInstance?.description}"/></td>
					</tr>

					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.modelConcept.label" default="Mandatory Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.model.mandatoryElements"
                        name="mandatoryDataElements"
                        id="mandatoryDataElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.modelConcept.label" default="Required Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.model.requiredElements"
                        name="requiredDataElements"
                        id="requiredDataElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.modelConcept.label" default="Optional Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.model.optionalElements"
                        name="optionalDataElements"
                        id="optionalDataElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="model.modelConcept.label" default="Reference Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.model.referenceElements"
                        name="referenceDataElements"
                        id="referenceDataElements"
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
					
				</tbody>
			</table>
<g:javascript library="dualListBox"/>
<g:javascript library="model"/>
	
<r:script disposition="defer">

	<g:if test="${!mandatoryDataElements*.id}">
		mandatoryDataElements = ' '
	</g:if>
	<g:else>
		mandatoryDataElements = ${mandatoryDataElements*.id}
	</g:else>
	
	<g:if test="${!requiredDataElements*.id}">
		requiredDataElements = ' '
	</g:if>
	<g:else>
		requiredDataElements = ${requiredDataElements*.id}
	</g:else>
	
	<g:if test="${!optionalDataElements*.id}">
		optionalDataElements = ' '
	</g:if>
	<g:else>
		optionalDataElements = ${optionalDataElements*.id}
	</g:else>
	
	<g:if test="${!referenceDataElements*.id}">
		referenceDataElements = ' '
	</g:if>
	<g:else>
		referenceDataElements = ${referenceDataElements*.id}
	</g:else>
	
	$(document).ready(function() {
		modelForm(mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements);
    });
				
</r:script>


