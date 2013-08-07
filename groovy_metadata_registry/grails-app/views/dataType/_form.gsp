<%@ page import="uk.co.mdc.model.DataType" %>


<div class="fieldcontain ${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataType.name.label" default="Data Type" />
		
	</label>
	<g:textField name="name" value="${dataTypeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataTypeInstance, field: 'enumerated', 'error')} ">
	<label for="enumerated">
		<g:message code="dataType.enumerated.label" default="Enumerated" />
		
	</label>
	<g:checkBox name="enumerated" id="isEnumerated" value="${dataTypeInstance?.enumerated}" />
</div>

<div id="enumerations" class="fieldcontain ${hasErrors(bean: dataTypeInstance, field: 'enumerations', 'error')} map">	
	<h1><g:message code="dataType.enumerations.label" default="Add Enumerations" /></h1>
	<table id="enumerationsTable" class="mapTable">
	<thead>
	<tr>
		<th>Value</th>
		<th>Description</th>
	</tr>
	<tbody>
	<tr>
		<td><g:textField name="map_key" /></td>
		<td><g:textField name="map_value" /> </td>
	</tr>
	</tbody>
	</table>
	<button id="add">+</button><button id="remove">-</button>
</div>

<g:javascript src="create_dataType.js" />
<g:javascript src="form_map.js" />



