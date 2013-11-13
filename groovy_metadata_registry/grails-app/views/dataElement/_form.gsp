<%@ page import="uk.co.mdc.model.DataElement" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: dataElementInstance, field: 'refId', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.refId.label" default="Reference ID" /></span></td>
						<td class="right_col_show"><g:textField title="${g.message(code:'tooltip.dataElement.refId')}" class="input-large input-block-level" name="refId" value="${dataElementInstance.refId}" required=""/></td>
					</tr>
					<tr class="${hasErrors(bean: dataElementInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.dataElement.name" class="input-large input-block-level"  name="name" value="${dataElementInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: dataElementInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.dataElement.description" class="input-block-level" name="description" value="${dataElementInstance?.description}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.definition.label" default="Definition" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.dataElement.definition" class="input-block-level" name="definition" value="${dataElementInstance?.definition}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.dataElementConcept.label" default="Data Element Concept" /></span></td>
						<td class="right_col_show">
						<g:select 
							title="tooltip.dataElement.dataElementConcept"
							id="dataElementConcept" 
							name="dataElementConcept.id" 
							from="${uk.co.mdc.model.DataElementConcept.list()}" 
							optionKey="id" 
							optionValue="name"
							value="${dataElementInstance?.dataElementConcept?.id}" 
							class="many-to-one" 
							noSelection="${[null:'Select One...']}"
							/>
						</td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataElement.parent.label" default="Parent" /></span></td>
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
						<td class="left_col_show"><span id="name-label" class="label">Associated Value Domains</span></td>
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
						<td class="left_col_show"><span id="name-label" class="label">Synonyms</span></td>
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
						<td class="left_col_show"><span id="name-label" class="label">SubElements</span></td>
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
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">External References</span></td>
						<td class="right_col_show">
						<g:select
								title="tooltip.dataElement.externalReferences"
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
<g:javascript library="dataElement"/>
<g:javascript library="dualListBox"/>	
<r:script disposition="defer">

	<g:if test="${!selectedValueDomains*.id}">
		selectedValueDomains = ' '
	</g:if>
	<g:else>
		selectedValueDomains = ${selectedValueDomains*.id}
	</g:else>
	
	<g:if test="${!dataElementInstance?.synonyms()*.id}">
		synonyms = ' '
	</g:if>
	<g:else>
		synonyms =${dataElementInstance?.synonyms()*.id}
	</g:else>
	
	<g:if test="${!dataElementInstance?.subElements*.id}">
		subElements = ' '
	</g:if>
	<g:else>
		subElements =${dataElementInstance?.subElements*.id}
	</g:else>

	<g:if test="${!dataElementInstance?.externalReferences*.id}">
		externalReferences = ' '
	</g:if>
	<g:else>
		externalReferences = ${dataElementInstance?.externalReferences*.id}
	</g:else>

	$(document).ready(function() {
		dataElementForm(selectedValueDomains, synonyms, subElements, externalReferences);
    });
				
</r:script>

