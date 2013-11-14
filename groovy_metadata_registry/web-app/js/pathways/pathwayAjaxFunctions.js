function getPathwaysModel(pathwayId){
	$.getJSON('../jsonPathways/' + pathwayId, function(data) {
		return data
	})
	.fail(function(jqXHR, textStatus, errorThrown) { console.log('getJSON request failed! ' + textStatus); })
}

function updatePathwaysModel(pathwaysModel){

	$.ajax({
		type: "POST",
		url: '../update',
		data: ko.toJSON(pathwaysModel),
		success: function(data){
			if(data.formVersion!=null){
				form.formVersionNo = data.formVersion
			}
			alert(data.message);
		},
		error: function (xhr, ajaxOptions, thrownError) {
	        console.log(xhr.status);
	        alert(thrownError);
	      },
		contentType: 'application/json',
		dataType: 'json'
		});
	
	console.log(pathwaysModel)
}

function deletePathwaysModel(id){
	
}

function getNode(id){
	
}

function updateNode(id, node){
	
}

function deleteNode(id){
	
}

function getLink(id){
	
}

function updateLink(id, link){
	
}

function deleteLink(id){
	
}

