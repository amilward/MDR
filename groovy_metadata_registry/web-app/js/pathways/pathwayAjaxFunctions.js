function createNode(){
	
	
}

function getNode(nodeId){
	console.log('get the node')
	
	$.getJSON('../../Node/getNodeJSON/' + nodeId, function(data) {
		console.log(JSON.stringify(data))
		return data
	})
	.fail(function(jqXHR, textStatus, errorThrown) { console.log('getJSON request failed! ' + textStatus); })
	
	
}

function updateNode(updatedNodeJSON){
	console.log(JSON.stringify(updatedNodeJSON))
	
	$.ajax({
		type: "POST",
		url: '../../Node/updateNodeFromJSON',
		data: JSON.stringify(updatedNodeJSON),
		success: function(data){
			console.log(data.message);
		},
		error: function (xhr, ajaxOptions, thrownError) {
	        console.log(xhr.status);
	        alert(thrownError);
	      },
		contentType: 'application/json',
		dataType: 'json'
		});

}

function deleteNode(id){
	
}

function getLink(id){
	
}

function updateLink(id, link){
	
}

function deleteLink(id){
	
	   	var heading = 'Confirm Delete Link';
	    var question = 'Please confirm that you wish to delete this link: ' + comp.prompt() + '.';
	    var cancelButtonTxt = 'Cancel';
	    var okButtonTxt = 'Confirm';

	    var callback = function() {
	    	self.questions.remove(comp);
	    	refreshFormPanelViews();
	    };

	    confirm(heading, question, cancelButtonTxt, okButtonTxt, callback);
	
}


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
