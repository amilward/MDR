<%@ page import="uk.co.mdc.pathways.PathwaysModel"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main_no-sidebar">
<g:set var="entityName"
	value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
<title>Show Pathway</title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'pathway.css')}" type="text/css">
<parameter name="name" value="Show Pathways" />

<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css/pathways', file: 'style.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css/pathways', file: 'treeView.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css">

</head>
<body>
	<div class="box">
		<div id="container">
		
		<div id="header-panel" class="panel-heading ui-layout-north large-rounded">
            <div class="panel panel-default" >
          	  <div class="panel-body">
                <button type="button" class="btn btn-link btn-xs pull-right" id="goToParent" data-bind="if: isSubPathway(), click: goToParent()">
                    <i class="fa fa-arrow-up"></i> Parent
                </button>
           	 	<button type="button" class="btn btn-link btn-xs pull-right" id="addNode" data-bind="click: createNode">
                    <i class="fa fa-plus"></i> Add Node
                </button>
                <button type="button" class="btn btn-link btn-xs pull-right" id="updatePathway" data-bind="click: updatePathway">
                    <i class="fa fa-save"></i> Save
                </button>
                <button type="button" class="btn btn-link btn-xs pull-right" id="editPathwayInfo" data-bind="click: editPathway">
                    <i class="fa fa-edit"></i> Edit Info
                </button>
               
            	
                 <div class="form-group">
                      <h1 id="pathwayName" data-bind="text: pathwayModel ? pathwayModel.name : ''"></h1>
                </div>
                
                </div>
 				</div>
            </div>
		
		
			<div class="ui-layout-west large-rounded" id="tree-panel">
				<div class="panel panel-default" data-bind="with: topLevelPathway">
					<div class="panel-heading" data-bind="attr:{title: name}, text: name">Tree View</div>
					<div class="panel-body">
                                            <!--
						<div class="pathway-title" data-bind="attr:{title: name}, text: name"></div>
						-->
						<div id="jsTreeView" class="treeview">
                                                    <ul class="level1" data-bind="foreach: nodes">
                                                      <li>
                                                          <!-- ko if: subPathwayId != null -->
                                                          <input type="checkbox" checked data-bind="click: function(){ getSubNodes(); return !self.checked;}, attr:{id: 'cb' + id}">
                                                          <!-- /ko -->
                                                          <label data-bind="attr:{for: 'cb' + id}">
                                                              <span data-bind="attr:{title: description}, click: $root.selectNode, css: {selectedItem: $root.itemEqualsToSelected($data)}">{{name}}</span>
                                                          </label>
                                                          <!-- ko if: subNodes != null -->
                                                          <ul class="level2" data-bind="foreach: subNodes">
                                                              <li>
                                                                  <!-- ko if: subPathwayId != null -->
                                                                  <input type="checkbox" checked data-bind="click: function(){ getSubNodes(); return !self.checked;}, attr:{id: 'cb' + id}">
                                                                  <!-- /ko -->
                                                                  <label data-bind="attr:{for: 'cb' + id}">
                                                                      <span data-bind="click: $root.selectNode, css: {selectedItem: $root.itemEqualsToSelected($data)}">{{name}}</span>
                                                                  </label>
                                                                  <!-- ko if: subNodes != null -->
                                                                  <ul class="level3" data-bind="foreach: subNodes">
                                                                      <li>
                                                                          <label data-bind="attr:{for: 'cb' + id}">
                                                                              <span data-bind="click: $root.selectNode, css: {selectedItem: $root.itemEqualsToSelected($data)}">{{name}}</span>
                                                                          </label>
                                                                      </li> 
                                                                  </ul>
                                                                  <!-- /ko -->
                                                              </li> 
                                                          </ul>
                                                          <!-- /ko -->

                                                      </li>
                                                  </ul>
						</div>
					</div>
				</div>
			</div>



			<div class="ui-layout-center" id="center-panel">
			<div id="model-panel" class="ui-layout-center  large-rounded">

			<div id="canvas-panel" class="panel panel-primary">
            <div class="panel-body" data-bind="with: pathwayModel">
                <div class="jsplumb-container" data-bind="foreach: nodes ">
                    <div class="node" data-bind="makeNode: $data, click: $root.selectNode, style: {top:y, left:x}, attr: { 'id': 'node' + id}, css: {selectedItem: $root.itemEqualsToSelected($data)}">
                        <div data-bind="attr:{title: description}">{{name}}</div>
                        <div class="fa fa-chevron-right ep right"></div>
			<div class="fa fa-chevron-left ep left"></div>
			<div class="fa fa-chevron-up ep up"></div>
			<div class="fa fa-chevron-down ep down"></div>
                    </div>
                </div>
            </div>
        </div>

				</div>
			</div>


<!-- If selectedItem is undefined, the right panel will be empty -->
    <div id="properties-panel" class="ui-layout-east large-rounded" data-bind="with: selectedItem">
        <div class="panel panel-primary">
            <div class="panel-heading">Properties: {{id}}</div>
            <div class="panel-body">
                <form class="form" role="form">
                    <div class="form-group">
                        <label for="txt-name" class="control-label">Name: </label>
                        <input id="txt-name" type="text" class="form-control" data-bind="value: name, valueUpdate: 'input'" />
                    </div>
                    <div class="form-group">
                        <label for="txt-desc" class="control-label">Description: </label>
                        <textarea id="txt-desc" rows="3" class="form-control" data-bind="value: description, valueUpdate: 'input'"></textarea>
                    </div>
                    
                </form>
                <!-- ko if: !($data instanceof LinkModel) -->
                <div class="panel panel-primary">
                    <div class="panel-heading">Pathways Model</div>
                    <div class="panel-body">
                        <div>
                            <button id="viewSubPathway" type="button" class="btn btn-link btn-xs pull-right" data-bind="click: viewSubPathway">
                                <i class="fa fa-plus"></i> View
                            </button>
                        </div>
                        <!--
                        <div data-bind="if: subPathwayId === null || subPathwayId === undefined">
                        	<button id="addSubPathway" type="button" class="btn btn-link btn-xs pull-right" data-bind="click: createSubPathway">
			                    <i class="fa fa-plus"></i> Add 
			                </button>
                        </div>
                        -->
                    </div>
                </div>
                    
                <div class="panel panel-primary">
                    <div class="panel-heading">Inputs</div>
                    <div class="panel-body inputs">
                        <ul class="list-group" data-bind="foreach: inputs">
                            <li class="list-group-item">{{name}}</li>
                        </ul>
                    </div>
                </div>
                <!-- ko if: outputs!=[]-->
                <div class="panel panel-primary">
                    <div class="panel-heading">Outputs</div>
                    <div class="panel-body outputs">
                        <ul class="list-group" data-bind="foreach: outputs">
                            <li class="list-group-item">{{name}}</li>
                        </ul>
                    </div>
                </div>
                <!-- /ko -->
                <div class="panel panel-primary">
                <!-- ko if: forms!=[]-->
                    <div class="panel-heading">Forms</div>
                    <div class="panel-body forms">
                        <ul class="list-group" data-bind="foreach: forms">
                            <li class="list-group-item"><a href="#" data-bind="click: previewForm">{{name}}</a>
                            <i class="fa icon-remove" data-bind="click: function(){$parent.removeForm(id);}"></i></li>
                        </ul>
                    </div>
                <!-- /ko -->
                    <button type="button" id="addFormToNode" class="btn btn-link btn-xs pull-right" data-bind="click: addFormDialog">
                  	  <i class="fa fa-plus"></i> Add Form
                	</button>
                </div>

                <div class="panel panel-primary">
                    <!-- ko if: collections!=[] -->
                    <div class="panel-heading">Data Elements</div>
                    <div class="panel-body forms">
                        <ul class="list-group" data-bind="foreach: collections">
                            <li class="list-group-item"><a href="#" data-bind="click: previewCollections">{{name}}</a>
                                <i class="fa icon-remove" data-bind="click: function(){$parent.removeCollection(id);}"></i></li>
                        </ul>
                    </div>
                    <!-- /ko -->
                    <button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: addCollectionDialog">
                        <i class="fa fa-plus"></i> Add Data Elements
                    </button>
                    <!--button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: addCollectionDialog">
                        <i class="fa fa-plus"></i> Add Collections
                    </button-->
                    <!--button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: AddNewDECollectionModal">
                        <i class="fa fa-plus"></i> Add Collections
                    </button-->
                </div>
                <!-- /ko -->
            </div>
        </div>
    </div>
		</div>
	</div>


	<!-- Add Pathway Modal -->
    <div class="modal fade hide" id="CreatePathwayModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" >
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    <h4 class="modal-title" id="myModalLabel">Create Pathway</h4>
                </div>
                <div class="modal-body">
                    <form class="form" role="form">
                        <div class="form-group">
                            <label for="txt-name" class="control-label">Name: </label>
                            <input id="txt-name" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="txt-desc" class="control-label">Description: </label>
                            <textarea id="txt-desc" rows="3" class="form-control"></textarea>
                        </div>
			        </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bind="click: $root.savePathway">Create</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- Add Node Modal -->
    <div class="modal fade hide" id="CreateNode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    <h4 class="modal-title" id="myModalLabel">Create Node</h4>
                </div>
                <div class="modal-body">
                    <form class="form" role="form">
                        <div class="form-group">
                            <label for="txt-name" class="control-label">Name: </label>
                            <input id="createNodeName" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="txt-name" class="control-label">Description: </label>
                            <input id="createNodeDescription" type="text" class="form-control"/>
                        </div>
			        </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bind="click: $root.saveNode">Create</button>
                    <button class="closeModalLink" type="button" class="btn"
          >Cancel</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    <!-- Add Form Modal -->
    <div class="modal fade hide" id="AddFormModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    <h4 class="modal-title" id="myModalLabel">Add Form</h4>
                </div>
                <div class="modal-body">
                	<div id="formDesignCart">Drag Form Here To Add <i style="display:block" class="fa fa-plus"></i>
                	<ul class="pull-left" id="formCartList">
                	</ul>
                	</div>
                   <div id="formDesignList" ></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bind="click: $root.addFormFinish">Finish</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

<!-- Add CollectionModal -->
<div class="modal fade hide" id="AddCollectionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                <h4 class="modal-title" id="myModalLabel">Add Data Element Collection</h4>
            </div>
            <div class="modal-body">
                <div id="collectionCart">Drag Collection Here To Add <i style="display:block" class="fa fa-plus"></i>
                    <ul class="pull-left" id="collectionCartList">
                    </ul>
                </div>
                <div id="collectionList" ></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bind="click: $root.refreshCollections">Refresh</button>
                <button type="button" class="btn btn-primary" data-bind="click: $root.addNewDECollection">Add New DE Collection</button>
                <button type="button" class="btn btn-primary" data-bind="click: $root.addCollectionFinish">Finish</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Add NewDECollectionModal -->
<div class="modal fade hide" id="AddNewDECollectionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                <h4 class="modal-title" id="myModalLabel">Add Data Element </h4>
            </div>
            <div class="modal-body">
                <div id="deCollectionCart">Drag Data Element Here To Add <i style="display:block" class="fa fa-plus"></i>
                    <ul class="pull-left" id="deCollectionCartList">
                    </ul>
                </div>
                <div id="deCollectionList" ></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bind="click: $root.addNewDECollectionFinish">Finish</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- Update Pathway Info -->
    <div id="updatePathwayModal" class="modal fade hide" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Update Pathway</h4>
			</div>
			<div class="modal-body">
			
				<form class="form" role="form" data-bind="with: pathwayModel" id="createPathwayForm" action="#" method="post">
					<div class="form-group">
						<label for="txt-name" class="control-label">Name: </label> 
						<input name="name"
							id="txt-nameUpdate" type="text" class="form-control" data-bind="value: name, valueUpdate: 'input'" 
							 />
					</div>
					<div class="form-group">
						<label for="txt-desc" class="control-label">Description: </label>
						<textarea name="description" id="txt-descUpdate" rows="3" class="form-control" data-bind="value: description, valueUpdate: 'input'" 
							></textarea>
					</div>
					<div class="form-group">
            <label for="txt-version" class="control-label">Version: </label> <input
              id="txt-versionNoUpdate" type="text" name="version" class="form-control" data-bind="value: versionNo, valueUpdate: 'input'" 
              />
          </div>
          <div class="form-group"> 
			            <label for="bool-isDraft" class="control-label">Draft: </label> 
			            <select id="select-isDraftUpdate" name="isDraft" data-bind="optionsText: 'isDraft', value: isDraft, optionsCaption: 'Choose...'">
									<option value="true">true</option>
   									 <option value="false">false</option>
								</select>
			          </div>
				</form>
			</div>
			<div class="modal-footer">
                <button class="closeModalLink" type="button" class="btn">Close</button>
                <div class="pull-left">
                    <div id="deletePathway-initial">
                        <button type="button" class="btn btn-danger btn-xs pull-right" id="deletePathway" data-bind="click: deletePathway">
                            <span class="glyphicon glyphicon-remove"></span> Delete Pathway
                        </button>
                    </div>
                    <div id="deletePathway-confirmation" class="pull-right" aria-hidden="true" style="display: none;">
                        <button type="button" id="cancelDeletePathway" class="btn">Abort deletion</button>
                        <button type="button" id="confirmDeletePathway" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>Confirm (WARNING: this cannot be undone)</button>
                    </div>
                </div>
            </div>
      
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
    
	<g:javascript disposition="defer" library="pathways" />
	<r:script disposition="defer">
	
		<g:if test="${id}">
			pathwaysModelId = ${id}
		</g:if>
		<g:else>
			pathwaysModelId = ''
		</g:else>

		initPathways(pathwaysModelId);
	</r:script>

</body>
</html>


