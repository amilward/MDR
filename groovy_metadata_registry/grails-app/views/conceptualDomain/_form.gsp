<%@ page import="uk.co.mdc.model.ConceptualDomain" %>


<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: conceptualDomainInstance, field: 'refId', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="conceptualDomain.refId.label" default="Reference ID" /></span></td>
						<td class="right_col_show"><g:textField title="${g.message(code:'tooltip.conceptualDomain.refId')}" class="input-large input-block-level" name="refId" value="${conceptualDomainInstance.refId}" required=""/></td>
					</tr>
					<tr class="${hasErrors(bean: conceptualDomainInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="conceptualDomain.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.conceptualDomain.name" class="input-large input-block-level"  name="name" value="${conceptualDomainInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: conceptualDomainInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="conceptualDomain.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textArea title="tooltip.conceptualDomain.description" class="input-block-level" name="description" value="${conceptualDomainInstance?.description}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label">Value Domains</span></td>
						<td class="right_col_show">
							<g:select
								title="tooltip.conceptualDomain.ValueDomains"
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
				</tbody>
			</table>
<g:javascript library="dualListBox"/>	
<r:script disposition="defer">

	<g:if test="${!selectedValueDomains*.id}">
		selectedValueDomains = ' '
	</g:if>
	<g:else>
		selectedValueDomains = ${selectedValueDomains*.id}
	</g:else>

	$(document).ready(function() {
		conceptualDomainForm(selectedValueDomains);
    });
				
</r:script>