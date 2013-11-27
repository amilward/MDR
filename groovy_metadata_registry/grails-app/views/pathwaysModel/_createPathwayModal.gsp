<!-- Modal to create a new pathway
  Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
-->

<!--  TODO move to HEAD -->
<!--  FIXME errors in require.js -->
<!--  FIXME add padding so scroll of modal works -->
<!--  <g :javascript library="pathwa yCreationModal"/> -->

<!-- TODO include JS library (knockout + view model) -->
<div id="createPathwayModal" class="modal fade hide" tabindex="-1"	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop = "true" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
				<h4 class="modal-title" id="myModalLabel">Create Pathway</h4>
			</div>
			<div class="modal-body">
			
				<form class="form" role="form" action="/groovy_metadata_registry/pathwaysModel/save" method="post">
					<div class="form-group">
						<label for="txt-name" class="control-label">Name: </label> <input
							id="txt-name" type="text" class="form-control"
							 />
					</div>
					<div class="form-group">
						<label for="txt-desc" class="control-label">Description: </label>
						<textarea id="txt-desc" rows="3" class="form-control"
							></textarea>
					</div>
					<div class="form-group">
            <label for="txt-version" class="control-label">Version: </label> <input
              id="txt-version" type="text" class="form-control"
              />
          </div>
          <div class="form-group">
            <label for="bool-isDraft" class="control-label">Draft: </label> <input
              id="bool-isDraft" type="checkbox" class="form-control"
              />
          </div>
				</form>
			</div>
			<div class="modal-footer">
        <button type="submit" class="btn btn-primary"
          >Create</button>
        <button type="button" class="btn"
          >Cancel</button>
      </div>
      
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->