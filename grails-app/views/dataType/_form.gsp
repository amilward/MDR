<%@ page import="uk.co.mdc.catalogue.DataType" %>

<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataType.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.dataType.name" class="input-large input-block-level"  name="name" value="${dataTypeInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'enumerated', 'error')} ">
						<td class="left_col_show"><span id="enumerated-label" class="label"><g:message code="dataType.enumerated.label" default="Enumerated" /></span></td>
						<td class="right_col_show"><g:checkBox name="enumerated" id="isEnumerated" value="${dataTypeInstance?.enumerated}" /></td>
					</tr>
					<tr id="enumerations">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="dataType.enumerations.label" default="Add Enumerations" /></span></td>
						<td class="right_col_show">
						<table id="enumerationsTable" class="mapTable">
							<thead>
							<tr>
								<th>Value</th>
								<th>Description</th>
							</tr>
							<tbody>
							<g:if test="${dataTypeInstance?.enumerations}">
							<g:each var="enumeration" in="${dataTypeInstance.enumerations}">
								<tr>
									<td><g:textField name="map_key" value="${enumeration?.key}"/></td>
									<td><g:textField name="map_value" value="${enumeration?.value}"/></td>
									<td><g:link params="[enumeratedValue: "${enumeration?.key}",dataTypeId: "${dataTypeInstance?.id}"]" action="removeEnumeratedValue" controller="DataType">Remove</g:link></td>	
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
		<g:javascript library="dataType"/>	
		<r:script disposition="defer">
		$(document).ready(function() {
			dataTypeForm();
			formMap();
		
		});
		</r:script>


