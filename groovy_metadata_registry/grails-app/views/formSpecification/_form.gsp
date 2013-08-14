<%@ page import="uk.co.mdc.forms.FormSpecification" %>



<div class="fieldcontain ${hasErrors(bean: formSpecificationInstance, field: 'collection', 'error')} required">
	<label for="collection">
		<g:message code="formSpecification.collection.label" default="Collection" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="collection.id" id="collection" value="${collectionId}" />
	<g:textField name="collection_name" value="${collectionName}" disabled="true" />
	
</div>

<div class="fieldcontain ${hasErrors(bean: formSpecificationInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="formSpecification.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${formSpecificationInstance?.name}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: formSpecificationInstance, field: 'fields', 'error')} ">
	<label for="fields">
		<g:message code="formSpecification.fields.label" default="Fields" />
		
	</label>
	<g:if test="${fields}">
					<h1>Data Elements</h1>
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Field Type</th>
									<th>Caption</th>
									<th>Options</th>
								</tr>
							</thead>
							<g:each var="field" in="${fields}">
								<tr>
									<td><g:field type="text" readonly="readonly" name="fieldnames" value="${field?.name}"/></td>
									<td><g:field type="text" readonly="readonly" name="fieldtypes" value="${field?.type}"/></td>
									<td><g:field type="text" readonly="readonly" name="fieldcaptions" value="${field?.caption}"/></td>
									<td><g:field type="text" readonly="readonly" name="fieldoptions" value="${field?.options}"/></td>
								</tr>
							</g:each>
					</table>
	</g:if>

</div>