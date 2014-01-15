<%@ page import="uk.co.mdc.model.DataElement" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: dataElementInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" ><g:message code="dataElement.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.dataElement.name" class="input-large input-block-level"  name="name" value="${dataElementInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: dataElementInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" ><g:message code="dataElement.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.dataElement.description" class="input-block-level" name="description" value="${dataElementInstance?.description}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" ><g:message code="dataElement.definition.label" default="Definition" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.dataElement.definition" class="input-block-level" name="definition" value="${dataElementInstance?.definition}"/></td>
					</tr>
					
					<tr>
						<td class="left_col_show"><span id="name-label" ><g:message code="dataElement.parent.label" default="Parent" /></span></td>
						<td>
						<g:select 
						title="tooltip.dataElement.parent"
						id="parent" 
						name="parent.id" 
						from="${uk.co.mdc.model.DataElement.list().minus(dataElementInstance)}" 
						optionKey="id" 
						optionValue="name"
						value="${dataElementInstance?.parent?.id}" 
						class="many-to-one" 
						noSelection="${[null:'Select One...']}"
						/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" >Associated Value Domains</span></td>
						<td class="right_col_show">
							<g:select
								title="tooltip.dataElement.ValueDomains"
								name="valueDomains"
								id="valueDomains"
								noSelection="${['':'Select One...']}"
								from="${valueDomains}"
								value="${params.list('valueDomains')}"
								optionKey="id"
								optionValue="name"
								multiple="true"
								size="6"
							/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" >Synonyms</span></td>
						<td class="right_col_show">
							<g:select
									title="tooltip.dataElement.synonyms"
						            name="synonyms"
						            id="synonyms"
						            noSelection="${['':'Select One...']}"
						            from="${dataElements.minus(dataElementInstance)}"
						            value="${params.list('dataElements')}"
						            optionKey="id"
						            optionValue="name"
						            multiple="true"
						            size="6"
						    />
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" >Children</span></td>
						<td class="right_col_show">
							<g:select
									title="tooltip.dataElement.subElements"
						            name="subElements"
						            id="subElements"
						            noSelection="${['':'Select One...']}"
						            from="${dataElements.minus(dataElementInstance)}"
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
<g:javascript library="dataElement"/>
<g:javascript library="dualListBox"/>	
<r:script disposition="defer">

	<g:if test="${!selectedValueDomains*.id}">
		selectedValueDomains = ' '
	</g:if>
	<g:else>
		selectedValueDomains = ${selectedValueDomains*.id}
	</g:else>
	
	<g:if test="${!selectedSynonyms*.id}">
		selectedSynonyms = ' '
	</g:if>
	<g:else>
		selectedSynonyms =${selectedSynonyms*.id}
	</g:else>
	
	<g:if test="${!subElements*.id}">
		subElements = ' '
	</g:if>
	<g:else>
		subElements =${subElements*.id}
	</g:else>


	$(document).ready(function() {
		dataElementForm(selectedValueDomains, selectedSynonyms, subElements);
    });
				
</r:script>

