<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- Built using James Welch's Forms Builder ------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->
<!-- --------------------------------------------------------------- -->

<%@ page import="uk.co.mdc.forms.FormDesign" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'formDesign.label', default: 'FormDesign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="name" value=" FORM Builder - ${formDesignInstance?.name}" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
	</head>
	<body>
		<header>
			<g:form id="deleteForm" url="[action:'delete',controller:'formDesign']">
				<g:hiddenField name="id" value="${collectionInstance?.id}" />
			    	<div class="navbar">
					    <div class="navbar-inner">
						    <ul class="nav">
						   		<li class="active"><a class="brand" href="#"><i class="icon-edit"></i> FormsBuilder</a></li>
						  		<li><g:link action="show" id="${formDesignInstance?.id}"><g:message code="default.button.show.label" default="Show" /></g:link></li>
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
		<div id="container">
		<div class="ui-layout-center" id="center-panel">
			<div id="model-panel" class="ui-layout-center large-rounded">
			    <!-- ko if: activeForm() == null && forms().length == 0 -->
        			<div class="alert alert-info">
        				<p><strong>Empty model: </strong> You don't currently have any forms in your model.  Use the button on the left-hand panel to create a new form.</p>
        			</div>
    			<!-- /ko -->
			    <!-- ko if: activeForm() == null && forms().length > 0 -->
        			<div class="alert alert-info">
        				<p><strong>No form selected: </strong> Choose a form from the left-hand panel.</p>
        			</div>
    			<!-- /ko -->
    			<!-- ko if: activeForm() != null -->
				<!-- ko with: activeForm() -->
				<div class="panel-title">
					<span data-bind="text: formFullName()"></span>
				</div>
				<div class="large-rounded form-item form">
					<!-- ko if: components().length == 0 -->
					<div class="alert alert-info">
        				<p><strong>Empty form: </strong> Your form is currently blank.  Drag a component from the palette on the right-hand side to start creating your form.</p>
        			</div>
        			<ul class="sortable" data-bind="sortable: {data: components, afterMove: function(something) { $root.setCurrentlySelectedFormComponentIdx(something.targetIndex); refreshFormPanelViews(); }}" >
        			</ul>
        			<!-- /ko -->
        			<!-- ko if: components().length > 0 -->
					<ul class="sortable" data-bind="sortable: {data: components, afterMove: function(something) { $root.setCurrentlySelectedFormComponentIdx(something.targetIndex); refreshFormPanelViews(); }}" >
						<li data-bind="click: function(data, event) { if(!$(event.target).is('.menu-content li')){ $root.setCurrentlySelectedFormComponentIdx($index());}}">
							<div class="large-rounded form-item" data-bind="css: {selected: $root.currentlySelectedFormComponentIdx() == $index()}">
								<table class="small-rounded">
									<tr>
										<!-- <td class="content" data-bind="html: computedHtml()">
										</td> -->
										<td class="content">
											<h4 data-bind="text: question().prompt() == 'no prompt set'? 'Question ' + getQuestionNumber($index(), $parent.components()) : question().computedPrompt()"></h4>
											<p data-bind="text: question().additionalInstructions()"></p>
											<p data-bind="html: question().dataTypeInstance().previewRender()">
												<!-- <span data-bind="text: question().label()"></span> -->
												
											</p>
											<!-- <p data-bind="text: question().style()"></p>
											<p data-bind="text: question().defaultValue()"></p>
											<p data-bind="text: question().placeholder()"></p>
											<p data-bind="text: question().unitOfMeasure()"></p>
											<p data-bind="text: question().maxCharacters()"></p>
											<p data-bind="text: question().format()"></p>
											<p data-bind="text: question().isEnumerated()"></p>
											<p data-bind="text: question().enumerations()"></p> -->
										
										</td>
										<td class="menu">
											<div class="menu-content">
												<ul>
													<li>Quick edit</li>
													<li>Properties</li>
													<li data-bind="click: function(){ $parent.copyComponent($data, $index()); }">Copy</li>
													<li data-bind="click: $parent.deleteComponent">Delete</li>
												</ul>
											</div>
										</td>
									</tr>
		
								</table>
							</div>
						</li>
					</ul>
					<!-- /ko -->
				</div>
				<!-- /ko -->
    			<!-- /ko -->
				
			</div>
			<div id="properties-panel" class="ui-layout-south large-rounded">
				<div class="panel-title">
					<span>Properties</span>
				</div>
				
				<!-- ko if: currentlySelectedFormComponent() == null && forms().length > 0 -->
        			<div class="alert alert-info">
        				<p><strong>No component selected: </strong> Select a component from your form.</p>
        			</div>
    			<!-- /ko -->
    			<!-- ko if: currentlySelectedFormComponent() != null -->
    			<table id="properties-table" data-bind="with: currentlySelectedFormComponent()">
					<thead>
						<tr>
							<th style="width: 30%;">Name</th>
							<th style="width: 1em;"></th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Internal Identifier</td>
							<td></td>
							<td data-bind="text: internalIdentifier"></td>
						</tr>
						<tr>
							<td>Date of creation</td>
							<td></td>
							<td data-bind="text: dateCreated"></td>
						</tr>
						<tr>
							<td>External Identifier</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: externalIdentifier, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Prompt</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().computedPrompt, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Additional Instructions</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().additionalInstructions, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Style</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().style, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Label</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().label, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Rendering Option</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-type="select" data-bind="editable: question().dataTypeInstance().renderingOption, 
								editableOptions: {value: question().dataTypeInstance().renderingOption, source: question().dataTypeInstance().renderingOptions(), mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Default Value</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().defaultValue, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Placeholder</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().placeholder, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Unit Of Measure</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().unitOfMeasure, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Max Characters</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().maxCharacters, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Format</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().format, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Enumerated</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().isEnumerated, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
												<tr>
							<td>Enumerations</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: question().enumerations, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- /ko -->
			</div>
			<div id="palette-panel" class="ui-layout-east">
				<h4>Palette</h4>
				<div id="accordion">
					<!-- ko foreach: palette -->
					<h5><span data-bind="text: name"></span></h5>
					<ul data-bind="foreach: {data: elements, as: 'element'}">
						<li class="palette-option" 
							data-bind="draggable: { data: newComponent(element), options: { 
										appendTo: '#center-panel',
										helper: 'clone',
										connectToSortable: '.sortable',
										revert: 'invalid',
										distance: 20,
										zIndex: 100,
										containment: '#container', 
										scroll: false}}">
							&nbsp;<i data-bind="attr: {class: element.icon + ' icon-large'}"></i>
							&nbsp;<span data-bind="text: element.name"></span>
						</li>
					</ul>
					<!-- /ko -->
				</div>
			</div>
		</div>		
		</div>
	</div>
			<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<g:javascript disposition="defer" library="formsBuilder"/>	
	<r:script disposition="defer">
		var formInstanceId
		
		<g:if test="${formDesignInstance?.id}">
			formInstanceId = ${formDesignInstance.id}
		</g:if>
		<g:else>
			formInstanceId = ''
		</g:else>
		
		openForms(formInstanceId);
	</r:script>
		
	</body>
</html>
