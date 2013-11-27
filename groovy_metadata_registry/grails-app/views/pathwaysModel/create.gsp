	<%@ page import="uk.co.mdc.pathways.PathwaysModel" %>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main_no-sidebar">
<g:set var="entityName"
	value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'pathway.css')}" type="text/css">
<parameter name="name" value=" Pathways" />
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'bootstrap-editable.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'layout.css')}" type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
	
</head>
<body>
	<header>
		<g:form id="deleteForm"
			url="[action:'delete',controller:'formDesign']">
			<g:hiddenField name="id" value="" />
			<div class="navbar">
				<div class="navbar-inner">
					<ul class="nav">
						<li class="active"><a class="brand" href="#"><i
								class="icon-edit"></i> Pathways</a></li>
						<li><a href="#" onclick="saveForm('${pathwaysInstance?.id}')">Save</a></li>
						<li><a href="#" onclick="addNode()">Add Node</a></li>


					</ul>
				</div>
			</div>
		</g:form>
	</header>
	<div class="box">
		<div id="container">

			<div class="ui-layout-west large-rounded" id="tree-panel">
				<div class="panel panel-default">
	                <div class="panel-heading">Tree View</div>
	                <div class="panel-body">
	                    {Tree View}
	                </div>
	            </div>
			</div>
			

			<div class="ui-layout-center" id="center-panel">
				<div id="model-panel" class="ui-layout-center large-rounded">

					<div id="main" class="graph-paper">
						<div id="render"></div>
					</div>

				</div>
			</div>
			<div id="properties-panel" class="ui-layout-east large-rounded"
				data-bind="with: selectedNode">
				<div class="panel panel-default">
					<div class="panel-heading">Properties</div>
					<div class="panel-body">
						<form class="form" role="form">
							<div class="form-group">
								<label for="txt-name" class="control-label">Name: </label> <input
									id="txt-name" type="text" class="form-control"
									data-bind="value: name, valueUpdate: 'input'" />
							</div>
							<div class="form-group">
								<label for="txt-desc" class="control-label">Description:
								</label>
								<textarea id="txt-desc" rows="3" class="form-control"
									data-bind="value: description, valueUpdate: 'input'"></textarea>
							</div>
						</form>
					</div>
				</div>
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
		initPathways();
	</r:script>

</body>
</html>
