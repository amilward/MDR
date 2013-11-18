
loadPathway = function(id) {
	// Load a pathway model from server
};

savePathway = function(model, success, failure) {

	var jsonModel = ko.toJSON(model);

	// FIXME Save pathway model to server
	//TODO remove console log
	console.log("posting to server" + jsonModel)
	$.ajax({
		type : "POST",
		// FIXME remove static app name
		url : "/groovy_metadata_registry/pathwaysModel/saveREST",
		data : jsonModel,
		contentType: "application/json; charset=utf-8",
		success : success,
		error : function(xhr, ajaxOptions, thrownError) {
			console.log("Creation of pathway failed: " + thrownError);
			failure();
		}
	});

	// FIXME redirect to show page if we're not in "edit mode.

};
