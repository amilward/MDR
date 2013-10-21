function getPathway(pathwayId){
	$.getJSON('../jsonPathways/' + pathwayId, function(data) {
		openPathways(data);
	})
	
	
	
}
