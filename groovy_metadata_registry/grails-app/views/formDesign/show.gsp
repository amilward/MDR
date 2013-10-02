
<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" FORM DESIGN - ${formDesignInstance?.name}" />
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						  		<li class="active"><g:link action="show" id="${formDesignInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
							    <li><g:link action="edit" id="${formDesignInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></li>
							    <li><g:link action="create" id="${formDesignInstance?.id}"><g:message code="default.button.create.label" default="Create" /></g:link></li>
							    <li><a href="#" onclick="deleteItem('${formDesignInstance?.name}')">Delete</a></li>
							    <li><g:link action="preview" id="${formDesignInstance?.id}"><g:message code="default.button.preview.label" default="Preview" /></g:link></li>
							    
							</ul>
					    </div>
			    	</div>
			   </g:form>
		</header>
		<div class="box">
		<div id="show-formDesign" class="content scaffold-show" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			
			
			<table class="table table-hovered">
				<tbody>
				<g:if test="${formDesignInstance?.collection}">
				<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label">Originating Collection</span></td>
						<td class="right_col_show">
						<g:link controller="collection" action="show" id="${formDesignInstance?.collection?.id}">${formDesignInstance?.collection?.name?.encodeAsHTML()}</g:link>
						</td>
					</tr>
				</g:if>
				<g:if test="${formDesignInstance?.refId}">
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.refId.label" default="Ref Id" /></span></td>
						<td class="right_col_show">
						<g:fieldValue bean="${formDesignInstance}" field="refId"/></td>
					</tr>
				</g:if>
				<g:if test="${formDesignInstance?.name}">
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'name', 'error')} ">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.name.label" default="Name" /></span></td>
						<td class="right_col_show">${formDesignInstance?.name}</td>
					</tr>
				</g:if>
				<g:if test="${formDesignInstance?.description}">										
					<tr class="${hasErrors(bean: dataTypeInstance, field: 'description', 'error')} ">
						<td class="left_col_show"><span id="description-label" class="label"><g:message code="formDesign.description.label" default="Description" /></span></td>
						<td class="right_col_show">${formDesignInstance?.description}</td>
					</tr>
				</g:if>
				<g:if test="${formDesignInstance?.header}">
					<tr id="header">
						<td class="left_col_show"><span id="name-label" class="label"><g:message code="formDesign.header.label" default="Header" /></span></td>
						<td class="right_col_show">
						<table id="headerTable">
								<tbody>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.header.label.label" default="Label" /></span></td>
										<td class="right_col_show">
										${formDesignInstance?.header?.label}
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.style.label" default="Style" /></span></td>
										<td class="right_col_show">
										${formDesignInstance?.header?.style}
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.designOrder.label" default="Design Order" /></span>
										</td>
										<td class="right_col_show">
										${formDesignInstance?.header?.designOrder}
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.title.label" default="Title" /></span></td>
										<td class="right_col_show">
										${formDesignInstance?.header?.title}
										</td>
									</tr>
									<tr>
										<td class="left_col_show"><span id="name-label" class="label">
										<g:message code="formDesign.preText.label" default="Pre Text" /></span></td>
										<td class="right_col_show">
										${formDesignInstance?.header?.preText}
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					</g:if>
					<g:if test="${formDesignInstance.formDesignElements}">
					<tr id="questions">
						<td class="left_col_show"><span id="name-label" class="label">Questions</span></td>
						<td class="right_col_show">
						<table id="headerTable">
							<tr>
									<th>Label</th>
									<th>Unit Of Measure</th>
									<th>Data Type</th>
									<th>Format</th>
									<th>Options</th>
								</tr>
							<tbody>
							<g:each var="question" in="${formDesignInstance.formDesignElements}">
								<g:if test="${question instanceof uk.co.mdc.forms.QuestionElement}">
										<tr>
											<td>${question?.dataElement}</td>
											<td>${question?.valueDomain}</td>
											<td>${question?.label}</td>
											<td>${question?.inputField?.unitOfMeasure}</td>
											<td>${question?.inputField?.dataType}</td>
											<td>${question?.inputField?.format}</td>
											<td>${question?.inputField?.dataType?.enumerations}</td>
										</tr>
								</g:if>
							</g:each>
							</tbody>
						</table>
						</td>
					</tr>
					</g:if>
				</tbody>
			</table>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${formDesignInstance?.id}" />
					<g:link class="edit" action="edit" id="${formDesignInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
		</div>
	</body>
</html>
