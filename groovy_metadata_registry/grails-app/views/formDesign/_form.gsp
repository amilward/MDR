<%@ page import="uk.co.mdc.forms.FormDesign" %>


<table class="table table-hovered">
				<tbody>
				<g:if test="${collectionId}">
				<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label">Originating Collection</span></td>
						<td class="right_col_show"><g:field type="text" readonly="readonly" id="collection" name="collection.id" value="${collectionId}"/></td>
					</tr>
				</g:if>
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'redId', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.refId.label" default="Ref Id" /></span></td>
						<td class="right_col_show">
						<g:textField  title="tooltip.dataType.name" name="refId" value="${formDesignInstance?.refId}"/>
						</td>
					</tr>
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.name.label" default="Name" /></span></td>
						<td class="right_col_show"><g:textField name="name" title="tooltip.dataType.name" value="${formDesignInstance?.name}"/></td>
					</tr>										
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="enumerated-label" class="label"><g:message code="formDesign.description.label" default="Description" /></span></td>
						<td class="right_col_show"><g:textField name="description" value="${formDesignInstance?.description}"/></td>
					</tr>
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'version', 'error')} ">
						<td class="left_col_show"><span id="version-label" class="label"><g:message code="formDesign.version.label" default="Version" /></span></td>
						<td class="right_col_show"><g:textField name="version" value="${formDesignInstance?.version}"/></td>
					</tr>
					<tr id="header">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.header.label" default="Header" /></span></td>
						<td class="right_col_show">
						<table id="headerTable">
								<tbody>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.header.label.label" default="Label" /></span></td>
										<td class="right_col_show">
										<g:textField name="header.label" value="${formDesignInstance?.header?.label}"/>
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.style.label" default="Style" /></span></td>
										<td class="right_col_show">
										<g:textField name="header.style" value="${formDesignInstance?.header?.style}"/>
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.title.label" default="Title" /></span></td>
										<td class="right_col_show">
										<g:textField name="header.title" value="${formDesignInstance?.header?.title}"/>
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.preText.label" default="Pre Text" /></span></td>
										<td class="right_col_show">
										<g:textField name="header.preText" value="${formDesignInstance?.header?.preText}"/>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr id="questions">
						<td class="left_col_show"><span id="name-label" class="label">Questions</span></td>
						<td class="right_col_show">
						<table id="questionsTable">
							<tr>
									<th>Label</th>
									<th>Unit Of Measure</th>
									<th>Data Type</th>
									<th>Format</th>
									<th>RenderType</th>
									<th>Options</th>
								</tr>
							<tbody>
							<g:each var="question" in="${questions}">
								<tr>
									<td>
									<g:hiddenField name="questionDataElementIds" value="${question?.dataElementId}" />
									<g:hiddenField name="questionValueDomainIds" value="${question?.valueDomainId}" />
									<g:field type="text" readonly="readonly" name="questionLabels" value="${question?.label}"/></td>
									<td><g:field type="text" readonly="readonly" name="questionUnitOfMeasures" value="${question?.unitOfMeasure}"/></td>
									<td><g:field type="text" readonly="readonly" name="questionDataTypes" value="${question?.dataType?.id}"/></td>
									<td><g:field type="text" readonly="readonly" name="questionFormats" value="${question?.format}"/></td>
									<td><g:field type="text" readonly="readonly" name="questionRenderTypes" value="${question?.renderType}"/></td>
									<td><g:field type="text" readonly="readonly" name="questionOptions" value="${question?.options}"/></td>
								</tr>
							</g:each>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
	




