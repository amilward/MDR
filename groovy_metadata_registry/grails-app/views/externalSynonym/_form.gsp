<%@ page import="uk.co.mdc.model.ExternalSynonym" %>

<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: externalSynonymInstance, field: 'externalIdentifier', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.externalIdentifier.label" default="externalIdentifier" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalSynonym.externalIdentifier" class="input-large input-block-level"  name="externalIdentifier" value="${externalSynonymInstance?.externalIdentifier}"/></td>
					</tr>
					<tr class="${hasErrors(bean: externalSynonymInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalSynonym.name" class="input-large input-block-level"  name="name" value="${externalSynonymInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: externalSynonymInstance, field: 'url', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.url.label" default="Url" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.externalSynonym.url" class="input-block-level" name="url" value="${externalSynonymInstance?.url}"/></td>
					</tr>
					<tr>
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="externalSynonym.attributes.label" default="Add Attributes" /></span></td>
						<td class="right_col_show">
						<table id="attributesTable" class="mapTable">
							<thead>
							<tr>
								<th>Attribute</th>
								<th>Value</th>
							</tr>
							<tbody>
							<g:if test="${externalSynonymInstance?.attributes}">
							<g:each var="attribute" in="${externalSynonymInstance.attributes}">
								<tr>
									<td><g:textField name="map_key" value="${attribute?.key}"/></td>
									<td><g:textField name="map_value" value="${attribute?.value}"/></td>
									<td><g:link params="[attribute: "${attribute?.key}",externalSynonymId: "${externalSynonymInstance?.id}"]" action="removeAttribute" controller="ExternalSynonym">Remove</g:link></td>		
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