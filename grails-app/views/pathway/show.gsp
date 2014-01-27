<%@ page import="uk.co.mdc.pathways.Pathway"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main_fullwidth">
<g:set var="entityName"
	value="${message(code: 'pathway.label', default: 'Pathway')}" />
<title>Pathway Editor</title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'pathway.css')}" type="text/css">
<parameter name="name" value="Pathway Editor" />

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css/pathways', file: 'treeView.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css">

    <asset:stylesheet href="jquery.layout/dist/jquery.layout-latest.css"/>

</head>
<body >
<g:set var="grailsParams" value="${params.collect{ it.key + '=\'' + it.value + '\''}.join('; ')}" />
<div ng-app="pathway-editor" ng-init="${grailsParams}" class="pathwayEditor">
<div ng-controller="PathwayEditorCtrl">

    <div class="row">
        <div class="col-xs-12">
            <div class="pull-right">
                <button type="button" class="btn btn-link btn-xs" id="addNode" data-toggle="modal" data-target="#CreateNode">
                    <i class="fa fa-plus"></i> Add Node
                </button>
                <small editable-textarea="pathway.description" e-form="pathwayDescriptionForm">
                    {{pathway.description || 'This pathway needs a description'}}
                    <small><i class="fa fa-edit" ng-click="pathwayDescriptionForm.$show()" ng-hide="pathwayDescriptionForm.$visible"></i></small>
                </small>
            </div>
            <div>
                <h3>
                    <span  id="pathwayName" editable-text="pathway.name"        e-style="width: 25em">{{ pathway.name || 'Please set the pathway name' }}</span>
                    <small id="userVersion" editable-text="pathway.userVersion" e-style="width: 5em">{{pathway.userVersion}}</small>
                </h3>
            </div>
        </div>
    </div>


    <div id="container" class="row" >

         <div class="ui-layout-west" id="tree-panel" >
             <ul>
                 <li ng-repeat="node in pathway.nodes" ng-include="'templates/pathway/pathwayTreeView.html'"></li>
             </ul>
         </div>


        <div class="ui-layout-center">
            <div mc-graph-container class="jsplumb-container canvas" ng-controller="GraphCanvasCtrl">
                <div mc-graph-node graph-node="node" select-node="selectNode(node)" is-selected="isSelected(node)" ng-repeat="node in pathway.nodes"></div>
                <div mc-graph-link graph-link="link" ng-repeat="link in pathway.links"></div>
            </div>
        </div>


        <!-- If selectedItem is undefined, the right panel will be empty -->
        <div class="ui-layout-east large-rounded panel panel-primary" id="properties-panel" ng-controller="NodePropertiesCtrl">
            <div class="panel-heading">
                Properties
            </div>
            <div class="panel-body" ng-hide="selectedNode">
                <p>Select a node to view it's properties</p>
            </div>
            <div class="panel-body" ng-show="selectedNode">
                <h4><a href="#" editable-text="selectedNode.name">{{ selectedNode.name || "empty" }}</a></h4>
                <p><a href="#" editable-text="selectedNode.description">{{ selectedNode.description || "empty" }}</a></p>

                <button type="button"class="btn btn-default btn-xs" ng-show="selectedNode.nodes" ng-click="switchToSubPathway()">
                    <i class="fa fa-sitemap"></i> Go to sub-pathway
                </button>

                <button type="button" class="btn btn-danger btn-xs" ng-click="delete()">
                    <i class="fa fa-trash-o"></i> Delete
                </button>

                <h5>Forms <i class="fa fa-plus-square-o" ng-click="addForm()"></i></h5>
                <ul>
                    <li ng-repeat="form in selectedNode.forms">
                        {{form.name}}
                         <i class="fa fa-minus-square-o" ng-click="removeForm(form)"></i>
                    </li>
                </ul>

                <h5>Data elements <i class="fa fa-plus-square-o" ng-click="addDataElement()"></i></h5>
                <ul>
                    <li ng-repeat="element in selectedNode.dataElements">
                        {{element.name}}
                        <!-- FIXME: Add data element collections to list (possibly a little folder icon? -->
                        <i class="fa fa-minus-square-o" ng-click="removeDataElement(element)"></i>
                    </li>
                </ul>
            </div>
        </div>
    </div>

<!-- FIXME refactor into a separate file -->
<script type="text/ng-template" id="templates/pathway/pathwayTreeView.html">
    <span ng-click="selectNode(node)"  ng-class="{selectedItem: isSelected(node)}">{{node.name}}</span>
    <ul>
        <li ng-repeat="node in node.nodes" ng-include="'templates/pathway/pathwayTreeView.html'" pathway="node"></li>
    </ul>
</script>

<script type="text/ng-template" id="templates/pathway/jsPlumbNode.html">
<div class="node" id="node{{node.id}}" ng-click="selectNode(node)"  ng-class="{selectedItem: isSelected(node)}" style="left: {{node.x}}px; top: {{node.y}}px">
    <div>{{node.name}}</div>
    <div class="fa fa-arrow-circle-o-right ep right"></div>
    <div class="fa fa-arrow-circle-o-left ep left"></div>
    <div class="fa fa-arrow-circle-o-up ep up"></div>
    <div class="fa fa-arrow-circle-o-down ep down"></div>
</div>
</script>
<!-- Refactored down to this point -->
<!-- Modals and other bits of hidden content -->

    <!-- Add Node Modal -->
    <div class="modal fade" id="CreateNode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    <h4 class="modal-title" id="createNodeModalLabel">Create Node</h4>
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
                    <button id="createNodeButton" type="button" class="btn btn-primary" data-bind="click: $root.saveNode">Create</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->


    <!-- Add Form Modal -->
    <div class="modal fade" id="AddFormModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Finish</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- Add CollectionModal -->
    <div class="modal fade" id="AddCollectionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
    <div class="modal fade" id="AddNewDECollectionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
    <div id="updatePathwayModal" class="modal fade" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
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
                <button class="closeModalLink" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <div class="pull-left">
                    <div id="deletePathway-initial">
                        <button type="button" id="deletePathway" class="btn btn-danger" data-bind="click: deletePathway">
                            <span class="glyphicon glyphicon-remove"></span> Delete Pathway
                        </button>
                    </div>
                    <div id="deletePathway-confirmation" class="pull-right" aria-hidden="true" style="display: none;">
                        <button type="button" id="cancelDeletePathway" class="btn btn-default">Abort deletion</button>
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

    <!-- FIXME remove this and use asset pipeline only. The only reference left is for the layout... -->
	<g:javascript disposition="defer" library="pathways" />

</body>
</html>


