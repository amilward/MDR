<%@ page import="uk.co.mdc.pathways.PathwaysModel"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main_no-sidebar">
<g:set var="entityName"
	value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'pathway.css')}" type="text/css">
<parameter name="name" value="Create Pathways" />

<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-editable.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">

<link rel="stylesheet" href="${resource(dir: 'css/pathways', file: 'style.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css">

</head>
<body>
	<div class="box">
		<div id="container">

			<div class="ui-layout-west large-rounded" id="tree-panel">
				<div class="panel panel-default">
					<div class="panel-heading">Tree View</div>
					<div class="panel-body" data-bind="with: pathwayModel">
						<div class="pathway-title" data-bind="attr:{title: description}">{{name}}</div>
						<ul data-bind="foreach: nodes">
							<li><a
								data-bind="attr:{title: description}, click: $root.selectNode">{{name}}</a>
							</li>
						</ul>
					</div>
				</div>
			</div>



			<div class="ui-layout-center" id="center-panel">
				<div id="model-panel" class="ui-layout-center large-rounded">

					<div id="canvas-panel" class="panel panel-primary">
            <div class="panel-heading">
                <span>Pathway: {{pathwayModel ? pathwayModel.name : ''}}</span>
                <button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: createNode">
                    <i class="fa fa-plus"></i> Add Node
                </button>
                <button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: updatePathway">
                    <i class="fa fa-save"></i> Save Pathway
                </button>
            </div>
            <div class="panel-body" data-bind="with: pathwayModel">
                <div class="jsplumb-container" data-bind="foreach: nodes">
                    <div class="node" data-bind="makeNode: $data, click: $root.selectNode,">
                        <div data-bind="attr:{title: description}">{{name}}</div>
                        <div class="anchor"></div>
                    </div>
                </div>
            </div>
        </div>

				</div>
			</div>


<!-- If selectedNode is undefined, the right panel will be empty -->
    <div id="properties-panel" class="ui-layout-east large-rounded" data-bind="with: selectedNode">
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
                <div class="panel panel-primary">
                    <div class="panel-heading">Inputs</div>
                    <div class="panel-body inputs">
                        <ul class="list-group" data-bind="foreach: inputs">
                            <li class="list-group-item">{{name}}</li>
                        </ul>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">Outputs</div>
                    <div class="panel-body outputs">
                        <ul class="list-group" data-bind="foreach: outputs">
                            <li class="list-group-item">{{name}}</li>
                        </ul>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">Forms</div>
                    <div class="panel-body forms">
                        <ul class="list-group" data-bind="foreach: forms">
                            <li class="list-group-item"><a href="#" data-bind="click: previewForm">{{name}}</a></li>
                        </ul>
                    </div>
                </div>
                <button type="button" class="btn btn-link btn-xs pull-right" data-bind="click: addFormDialog">
                    <i class="fa fa-plus"></i> Add Form
                </button>
            </div>
        </div>
    </div>
		</div>
	</div>


	<!-- Add Pathway Modal -->
    <div class="modal fade" id="CreatePathwayModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" data-bind="with: $root.createPathway">
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    <h4 class="modal-title" id="myModalLabel">Create Pathway</h4>
                </div>
                <div class="modal-body">
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
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bind="click: $root.savePathway">Create</button>
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
                	<div id="formDesignCart"><i class="fa fa-plus"></i> Add to Node</div>
                   <div id="formDesignList" ></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bind="click: $root.addFormFinish">Finish</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    

	<g:javascript disposition="defer" library="pathways" />
	<r:script disposition="defer">
	
		<g:if test="${pathwaysModelInstance}">
			pathwaysModel = ${pathwaysModelInstance}
		</g:if>
		<g:else>
			pathwaysModel = ''
		</g:else>
	
		console.log(pathwaysModel)
	
		initPathways(pathwaysModel);
	</r:script>

</body>
</html>


