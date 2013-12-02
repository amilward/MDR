<%@ page import="uk.co.mdc.forms.FormDesign" %>

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
				<!--  <div class="panel-title">
					<span data-bind="text: formDesignName()"></span>
				</div>-->
				<div class="large-rounded form-item form">

									
					<div class="form-info">
					
					<p class="lead"><span data-bind="text: formDesignName"></span><button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: editForm">
                   	 <i class="fa fa-edit"></i> Edit Form Info
                	</button></p>
					
					
					
					</div>
					
					<div class="spacer30"></div>
					
					<!-- ko if: components().length == 0 -->
					<div class="alert alert-info">
        				<p><strong>Empty form: </strong> Your form is currently blank.  Drag a component from the palette on the right-hand side to start creating your form.</p>
        			</div>
        			<ul class="sortable section" data-bind="sortable: {data: components, afterMove: function(something) { $root.setCurrentlySelectedFormSectionIdx(something.targetIndex); refreshFormPanelViews(); }}" >
        			</ul>
        			<!-- /ko -->
        			<!-- ko if: components().length > 0 -->
					<ul class="sortable" data-bind="sortable: {data: components, 
																connectClass: 'section', 
																afterMove: function(something) { $root.setCurrentlySelectedFormSectionIdx(something.targetIndex);refreshFormPanelViews(); }
																}" >
						<li data-bind="click: function(data, event) { if(!$(event.target).is('.menu-content li')){ $root.setCurrentlySelectedFormSectionIdx($index());}}">
							<!-- ko if: section()!= null -->
							<div class="section" data-bind="event: { mouseover: function(){$root.setCurrentlySelectedFormSectionIdx($index());}}">
									
											
										<p><span class="lead" data-bind="text: section().title()"></span>
										 <span data-bind="click: $parents[0].deleteComponent"> delete </span></p>
										
										
							
							<!-- ko if: section().questions()==null-->
							
							<ul class="sortable form-item" data-bind="sortable: {data: section().questions, afterMove: function(something) { $root.setCurrentlySelectedQuestionIdx(something.targetIndex); refreshFormPanelViews(); }}" >
        					</ul>
		
								
							<!-- /ko -->
										
									
							<!-- ko if: section().questions()!=null-->
							<!-- ko with: section() -->
								<ul class="sortableQuestions" data-bind="sortable: {data: questions, connectClass: 'form-item', 
																					afterMove: function(something) { $root.setCurrentlySelectedQuestionIdx(something.targetIndex); refreshFormPanelViews(); }}" 
																					>
								<li data-bind="click: function(data, event) { if(!$(event.target).is('.menu-content li')){ $root.setCurrentlySelectedQuestionIdx($index());}}">
									<div class="large-rounded question form-item" data-bind="css: {selected: $root.currentlySelectedQuestionIdx() == $index() && $root.currentlySelectedFormSectionIdx() == $parentContext.$index()}">
										<table class="small-rounded">
											<tr>
												<!-- <td class="content" data-bind="html: computedHtml()">
												</td> -->
												
												<td class="content">
													<p>
													<i class="icon-large pull-left" data-bind="attr: {class: icon() + ' icon-large'}"></i>
													<span class="pull-left" data-bind="text: prompt()"></span>
													
													<!--  <span class="lead" data-bind="text: prompt() == 'no prompt set'? 'Question ' + getQuestionNumber($index(), $parent.components()) : computedPrompt()">
													</span>-->
													(<span data-bind="text: dataTypeInstance().name()"></span>)
													</p>
													<!--  <p data-bind="text: additionalInstructions()"></p>-->
													<p><span class="pull-left" data-bind="text: label()"></span>
													<span class="pull-left"> &nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</span> 
													<span class="pull-left" data-bind="html: dataTypeInstance().previewRender()"></span>
													<span class="pull-right" data-bind="text: maxCharacters()"></span>
													</p>
													
													
													
													<!-- 
													<p data-bind="text: dataElementId()"></p>
													<p data-bind="text: valueDomainId()"></p>
													<p data-bind="text: style()"></p>
													<p data-bind="text: defaultValue()"></p>
													<p data-bind="text: placeholder()"></p>
													
													 <p data-bind="text: isEnumerated()"></p>
													<p data-bind="text: enumerations()"></p> -->
												
												</td>
												<td class="menu">
													<div class="menu-content">
														<ul>
															<li>Quick edit</li>
															<li>Properties</li>
															<li data-bind="click: function(){ $parent.copyComponent($data, $index()); }">Copy</li>
															<li data-bind="click: $parent.deleteQuestion">Delete</li>
														</ul>
													</div>
												</td>
											</tr>
				
										</table>
									</div>
									</li>
									</ul>
									<!-- /ko -->
									<!-- /ko -->
									</div>
									<div class="spacer30"></div>
									<!-- /ko -->
							</li>
						</ul>
					<!-- /ko -->
				</div>
				<!-- /ko -->
    			<!-- /ko -->
				
			</div>
			<div id="properties-panel" class="ui-layout-west large-rounded">
				<div class="panel-title">
					<span>Properties</span>
				</div>
				
				<!-- 
				<!- - ko if: currentlySelectedQuestion() == null && forms().length > 0 - ->
        			<div class="alert alert-info">
        				<p><strong>No component selected: </strong> Select a component from your form.</p>
        			</div>
    			<!- - /ko - ->
    			 -->
    			 <!-- ko if: currentlySelectedFormSection() != null -->
    			 <table id="properties-table" data-bind="with: currentlySelectedFormSection()">
					<thead>
						<tr>
							<th style="width: 30%;">Name</th>
							<th style="width: 1em;"></th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
					
					<tr>
							<td>Section Title</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: section().title, editableOptions: { mode: 'inline' }"></a>
							</td>
					</tr>
					
					 <!-- ko if: section().rules != null -->
					<tr>
							<td>Rule Predicate</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td></td>
					</tr>

					<!-- /ko -->
					
					</tbody>
					</table>
					
					
				<div class="spacer30"></div> 	
    			<!-- /ko -->
    			 
    			<!-- ko if: currentlySelectedQuestion() != null -->
    			<table id="properties-table" data-bind="with: currentlySelectedQuestion()">
					<thead>
						<tr>
							<th style="width: 30%;">Name</th>
							<th style="width: 1em;"></th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
					<!--
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
						
						-->
						<tr>
							<td>Question Id</td>
							<td></td>
							<td data-bind="text: questionId"></td>
						</tr>
						<tr>
							<td>Data Element Id</td>
							<td></td>
							<td data-bind="text: dataElementId"></td>
						</tr>
						<tr>
							<td>Value Domain Id</td>
							<td></td>
							<td data-bind="text: valueDomainId"></td>
						</tr>
						<tr>
							<td>Input Id</td>
							<td></td>
							<td data-bind="text: inputId"></td>
						</tr>
						
						<tr>
							<td>Prompt</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: computedPrompt, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Additional Instructions</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: additionalInstructions, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Style</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: style, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Label</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: label, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Rendering Option</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-type="select" data-bind="editable: dataTypeInstance().renderingOption, 
								editableOptions: {value: dataTypeInstance().renderingOption, source: dataTypeInstance().renderingOptions(), mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Default Value</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: defaultValue, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Placeholder</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: placeholder, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Unit Of Measure</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: unitOfMeasure, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Max Characters</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: maxCharacters, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Format</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: format, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
						<tr>
							<td>Enumerated</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: isEnumerated, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
												<tr>
							<td>Enumerations</td>
							<td><span id="pencil"><i class="icon-pencil"></i></span></td>
							<td>
								<a data-bind="editable: enumerations, editableOptions: { mode: 'inline' }"></a>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- /ko -->
			</div>
			<div id="palette-panel" class="ui-layout-east">
			
				<!--  <ul class="unstyled accordion collapse in">
					<li class="accordion-group ">
					<h4>
                        <a data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#form-section-nav">
                            Sections 
                        </a>
                    </h4>
                        <ul class="collapse" id="form-section-nav">
                        
	                        < !-- ko foreach: sections -- >
							<li><span data-bind="text: name"></span></li>
							< !-- /ko -- >

                        </ul>
                    </li>
                 </ul> -->
                 
                 
                <h4>Sections</h4>
				<div id="sectionAccordion">
					<!-- ko foreach: sectionPalette -->
					<h5><span data-bind="text: name"></span></h5>
					<ul data-bind="foreach: {data: elements, as: 'element'}">
						<li class="palette-option" 
							data-bind="draggable: { data: $root.createComponent(element),
							connectClass: 'section',
							options: { 
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

				<h4>Questions</h4>
				<div id="questionAccordion">
					<!-- ko foreach: palette -->
					<h5><span data-bind="text: name"></span></h5>
					<ul data-bind="foreach: {data: elements, as: 'element'}">
						<li class="palette-option" 
							data-bind="draggable: { data: $root.createComponent(element),
							connectClass: 'form-item',
							options: { 
										appendTo: '#center-panel',
										helper: 'clone',
										connectToSortable: '.sortableQuestions',
										revert: 'invalid',
										distance: 20,
										zIndex: 100,
										containment: '#container', 
										toleranceElement: 'div',
										scroll: false}}">
							&nbsp;<i data-bind="attr: {class: element.icon + ' icon-large'}"></i>
							&nbsp;<span data-bind="text: element.name"></span>
						</li>
					</ul>
					<!-- /ko -->
				</div>
			</div>
		</div>		

