<%@ page import="uk.co.mdc.model.Document" %>

<table class="table table-hovered">
				<tbody>
					<tr class="${hasErrors(bean: documentInstance, field: 'refId', 'error')} required">
						<td class="left_col_show"><span id="refId-label" class="label"><g:message code="document.refId.label" default="Reference Id" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.document.refId" class="input-large input-block-level"  name="refId" value="${documentInstance?.refId}"/></td>
					</tr>
					<tr class="${hasErrors(bean: documentInstance, field: 'name', 'error')} required">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="document.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.document.name" class="input-large input-block-level"  name="name" value="${documentInstance?.name}"/></td>
					</tr>
					<tr class="${hasErrors(bean: documentInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="description-label" class="label"><g:message code="document.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.document.url" class="input-block-level" name="description" value="${documentInstance?.description}"/></td>
					</tr>
					<tr class="${hasErrors(bean: documentInstance, field: 'content', 'error')} ">
						<td class="left_col_show"><span id="content-label" class="label"><g:message code="document.content.label" default="Content" /></span></td>
						<td class="right_col_show"><input type="file" id="content" name="content" /></td>
					</tr>
					<tr class="${hasErrors(bean: documentInstance, field: 'contentType', 'error')} ">
						<td class="left_col_show"><span id="contentType-label" class="label"><g:message code="document.contentType.label" default="Content Type" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.document.contentType" class="input-block-level" name="contentType" value="${documentInstance?.contentType}"/></td>
					</tr>
					<tr class="${hasErrors(bean: documentInstance, field: 'fileName', 'error')} ">
						<td class="left_col_show"><span id="fileName-label" class="label"><g:message code="document.fileName.label" default="File Name" /></span></td>
						<td class="right_col_show"><g:textField title="tooltip.document.fileName" class="input-block-level" name="fileName" value="${documentInstance?.fileName}"/></td>
					</tr>				
				</tbody>
			</table>

