<%@ page import="uk.co.mdc.model.DataElementConcept" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: dataElementConceptInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElementConcept.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.dataElementConcept.name" class="input-large input-block-level"  name="name" value="${dataElementConceptInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: dataElementConceptInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElementConcept.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.dataElementConcept.description" class="input-block-level" name="description" value="${dataElementConceptInstance?.description}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="parent-label" class="label"><g:message code="dataElementConcept.parent.label" default="Parent" /></span></td>
						<td>
						<g:select
						title="tooltip.dataElementConcept.parent"
                        id="parent"
						name="parent.id" 
			            noSelection="${['':'Select One...']}"
			            from="${dataElementConcepts.minus(dataElementConceptInstance)}"
			            value="${dataElementConceptInstance?.parent?.id}"
			            optionKey="id"
			            optionValue="name" 
						class="many-to-one"
      					/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="subConcepts-label" class="label">Sub Concepts</span></td>
						<td class="right_col_show">
							<g:select
								title="tooltip.dataElementConcept.subConcepts"
							 	name="subConcepts"
							 	id="subConcepts"
					            noSelection="${['':'Select One...']}"
					            from="${dataElementConcepts.minus(dataElementConceptInstance)}"
					            value="${params.list('dataElementConcepts')}"
					            optionKey="id"
					            optionValue="name"
					            multiple="true"
					            size="6"
							/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="dataElement-label" class="label">Data Elements</span></td>
						<td class="right_col_show">
						<g:select
								title="tooltip.dataElementConcept.dataElements"
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
				</tbody>
			</table>
<g:javascript library="dataElementConcept"/>	
<g:javascript library="dualListBox"/>	
<r:script disposition="defer">

	<g:if test="${!dataElementConceptInstance?.dataElements}">
		selectedDataElements = ' '
	</g:if>
	<g:else>
		selectedDataElements = ${dataElementConceptInstance?.dataElements*.id}
	</g:else>

	<g:if test="${!dataElementConceptInstance?.subConcepts}">
		subConcepts = ' '
	</g:if>
	<g:else>
		subConcepts = ${dataElementConceptInstance?.subConcepts*.id}
	</g:else>

	$(document).ready(function() {
		dataElementConceptForm(selectedDataElements, subConcepts);
    });
				
</r:script>



