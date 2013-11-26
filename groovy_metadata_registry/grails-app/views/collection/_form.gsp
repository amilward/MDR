<%@ page import="uk.co.mdc.model.Collection" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: collectionInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.collection.name" class="input-large input-block-level"  name="name" value="${collectionInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: collectionInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.collection.description" class="input-block-level" name="description" value="${collectionInstance?.description}"/></td>
					</tr>

					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.collectionConcept.label" default="Mandatory Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.collection.mandatoryElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.collectionConcept.label" default="Required Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.collection.requiredElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.collectionConcept.label" default="Optional Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.collection.optionalElements"
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
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="collection.collectionConcept.label" default="Reference Data Elements" /></span></td>
						<td class="right_col_show">
						<g:select
						title="tooltip.collection.referenceElements"
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
		collectionForm(mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements);
    });
				
</r:script>


