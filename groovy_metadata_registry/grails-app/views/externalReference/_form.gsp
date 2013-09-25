<%@ page import="uk.co.mdc.model.ExternalReference" %>



<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: externalReferenceInstance, field: 'externalIdentifier', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.externalIdentifier.label" default="externalIdentifier" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalReference.externalIdentifier" class="input-large input-block-level"  name="externalIdentifier" value="${externalReferenceInstance?.externalIdentifier}"/></td>
					</tr>
					<tr class="${hasErrors(bean: externalReferenceInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalReference.name" class="input-large input-block-level"  name="name" value="${externalReferenceInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: externalReferenceInstance, field: 'url', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.url.label" default="Url" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalReference.url" class="input-block-level" name="url" value="${externalReferenceInstance?.url}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalReference.attributes.label" default="Add Attributes" /></span></td>
						<td class="right_col_show">
						<table id="attributesTable" class="mapTable">
							<thead>
							<tr>
								<th>Attribute</th>
								<th>Value</th>
							</tr>
							<tbody>
							<g:if test="${externalReferenceInstance?.attributes}">
							<g:each var="attribute" in="${externalReferenceInstance.attributes}">
								<tr>
									<td><g:textField name="map_key" value="${attribute?.key}"/></td>
									<td><g:textField name="map_value" value="${attribute?.value}"/></td>
									<td><g:link params="[attribute: "${attribute?.key}",externalReferenceId: "${externalReferenceInstance?.id}"]" action="removeAttribute" controller="ExternalReference">Remove</g:link></td>		
								</tr>
							</g:each>
							</g:if>
							<tr>
								<td><g:textField name="map_key" /></td>
								<td><g:textField name="map_value" /> </td>
							</tr>
							</tbody>
							</table>
							<button id="add">+</button><button id="remove">-</button>
						</td>
					</tr>
				</tbody>
			</table>
			
		<r:script disposition="defer">
		$(document).ready(function() {
		
			formMap();
		
		});
		</r:script>
