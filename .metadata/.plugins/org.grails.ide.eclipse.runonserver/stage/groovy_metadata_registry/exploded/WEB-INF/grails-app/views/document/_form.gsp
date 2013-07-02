<%@ page import="uk.co.mdc.model.Document" %>



<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="document.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="20" required="" value="${documentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="document.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="500" required="" value="${documentInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'content', 'error')} required">
	<label for="content">
		<g:message code="document.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="content" name="content" />
</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'contentType', 'error')} ">
	<label for="contentType">
		<g:message code="document.contentType.label" default="Content Type" />
		
	</label>
	<g:textField name="contentType" value="${documentInstance?.contentType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'fileName', 'error')} ">
	<label for="fileName">
		<g:message code="document.fileName.label" default="File Name" />
		
	</label>
	<g:textField name="fileName" value="${documentInstance?.fileName}"/>
</div>

