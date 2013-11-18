
	
	loadPathway = function(id) {
		// Load a pathway model from server
	};
	
	savePathway = function(model) {
	
		var jsonModel = ko.toJSON(model);
	
		// FIXME Save pathway model to server
		alert("Saving to server: "+ jsonModel);
		
		// FIXME redirect to show page if we're not in "edit mode.
	};
