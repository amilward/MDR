<!-- Modal to create a new node
-->

<!--  TODO move to HEAD -->
<!--  FIXME errors in require.js -->
<!--  FIXME add padding so scroll of modal works -->

<!-- TODO include JS library (knockout + view model) -->
<div id="createNodeModal" class="modal fade hide" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Create Node</h4>
			</div>
			<div class="modal-body">
			
				<form class="form" role="form" data-bind="with: nodeModel" action="/groovy_metadata_registry/nodesModel/save" method="post">
					<div class="form-group">
						<label for="txt-name" class="control-label">Name: </label> <input
							id="txt-name" type="text" class="form-control"
							data-bind="value: name, valueUpdate: 'input'" />
					</div>
					<div class="form-group">
						<label for="txt-desc" class="control-label">Description: </label>
						<textarea id="txt-desc" rows="3" class="form-control"
							data-bind="value: description, valueUpdate: 'input'"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer">
        <button type="button" class="btn btn-primary"
          data-bind="click: createNode">Create</button>
        <button type="button" class="btn"
          data-bind="click: cancel" data-dismiss="modal">Cancel</button>
      </div>
      
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->