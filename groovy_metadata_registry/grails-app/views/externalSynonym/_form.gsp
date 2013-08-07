<%@ page import="uk.co.mdc.model.ExternalSynonym" %>



<div class="fieldcontain ${hasErrors(bean: externalSynonymInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="externalSynonym.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${externalSynonymInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: externalSynonymInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="externalSynonym.url.label" default="Url" />
		
	</label>
	<g:textField name="url" value="${externalSynonymInstance?.url}"/>
</div>

<div id="attributes" class="fieldcontain ${hasErrors(bean: dataTypeInstance, field: 'attributes', 'error')} map">	
	<h1><g:message code="externalSynonym.attributes.label" default="Add Attributes" /></h1>
	<table id="attributesTable" class="mapTable">
	<thead>
	<tr>
		<th>Attribute</th>
		<th>Value</th>
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

<g:javascript src="form_map.js" />