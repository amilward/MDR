<!-- Modal to create a new pathway
  Author: Adam Milward (adam.milward@outlook.com)
-->
	<!-- Create Form Modal -->
    <div id="createFormModal" class="modal fade" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content" data-bind="">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Create Form</h4>
			</div>
			<div class="modal-body">
			
				<form class="form" role="form" id="createFormForm" action="${request.contextPath}/formDesign/show" method="post">
					<input type="hidden" name="createForm" value="createForm">
					<div class="form-group">
						<label for="txt-name" class="control-label">Name: </label> 
						<input name="name"
							id="txt-name" type="text" class="form-control" 
							 />
					</div>
					<div class="form-group">
						<label for="txt-desc" class="control-label">Description: </label>
						<textarea name="description" id="txt-desc" rows="3" class="form-control"
							></textarea>
					</div>
					
					<div class="form-group">
			            <label for="txt-version" class="control-label">Version: </label> <input
			              id="txt-version" type="text" name="versionNo" class="form-control"
			              />
			         </div>
			          <div class="form-group"> 
			            <label for="bool-isDraft" name="isDraft" class="control-label">Draft: </label> 
			            <select>
								<option value="true">true</option>
   								<option value="false">false</option>
						</select>
			          </div>
				</form>
			</div>
			<div class="modal-footer">
          <button id="createFormSubmit" type="submit" class="btn btn-primary"
          >Create</button>
        <button class="closeModalLink" type="button" class="btn" data-dismiss="modal">Close</button>
      </div>
      
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->