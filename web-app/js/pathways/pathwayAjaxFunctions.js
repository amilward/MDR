function createNode(createNodeJSON){
	
console.log(JSON.stringify(createNodeJSON))
	
	$.ajax({
		type: "POST",
		url: '../../Node/createNodeFromJSON',
		data: JSON.stringify(createNodeJSON),
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

function getNode(nodeId){
	console.log('get the node')
	
	$.getJSON('../../Node/getNodeJSON/' + nodeId, function(data) {
		console.log(JSON.stringify(data))
		return data
	})
	.fail(function(jqXHR, textStatus, errorThrown) { console.log('getJSON request failed! ' + textStatus); })
	
	
}

function updateNode(updatedNodeJSON){
	console.log("updatedNodeJSON:" + JSON.stringify(updatedNodeJSON))
	
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
	$.ajax({
		type: "POST",
		url: '../../Node/deleteNode/' + id,
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

function jsonBasket(result){
    var jsonString = "[ saveBasketCollection:Save Collection, collection_basket_id:" + result.id + ",";
    console.log(result);
    console.log(result.class);
    console.log(result.id);
        var de = result.dataElements;
        var decol = "dataElementIds:[";
        for (j in de)
        {
            var rid = result.dataElements[j].id;
            console.log("de id = " + rid);
            jsonString = jsonString + "dataElement_" + rid + ":mandatory,";
            if(j == 0){
                decol = decol + rid ;
            }else{
                decol = decol + "," + rid ;
            }

        }
    jsonString = jsonString + decol + "],";
    jsonString = jsonString + " action:saveBasketCollection,controller:collection]";

   return jsonString;
}

function createCollection(collection){

    console.log("startCollectionBasket" + collection.name);
    //startCollectionBasket();
    var ncollection = "";

    $.ajax({
        type: "GET",
        url: root + "/collectionBasket/collectionAsJSON",
        success: function(result){
            if(result!=null){
                console.log(result.id);
                ncollection = jsonBasket(result);
                console.log("ncollection=" +  ncollection);
               // ncollection = result;
                console.log("ncollection=" +  ncollection);
            }
        },
        dataType: "json"
    });

    console.log("new collection=" + ncollection);
    $.ajax({
        type: "POST",
        url: '../../collection/saveBasketCollection',
        data: ncollection,
        success: function(data){
            console.log(data.message);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr.status);
            alert(thrownError);
        },
        contentType: 'application/json'
    });



}
function getLink(linkId){
	
console.log('get the link')
	
	$.getJSON('../../Link/getLinkJSON/' + linkId, function(data) {
		console.log(JSON.stringify(data))
		return data
	})
	.fail(function(jqXHR, textStatus, errorThrown) { console.log('getJSON request failed! ' + textStatus); })
	
	
}

function updateLink(updatedLinkJSON){
	
console.log(JSON.stringify(updatedLinkJSON))
	
	$.ajax({
		type: "POST",
		url: '../../Link/updateLinkFromJSON',
		data: JSON.stringify(updatedLinkJSON),
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

function createLink(createLinkJSON){
	
	console.log(JSON.stringify(createLinkJSON))
		
		$.ajax({
			type: "POST",
			url: '../../Link/createLinkFromJSON',
			data: JSON.stringify(createLinkJSON),
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

function deleteLink(id){
	
	$.ajax({
		type: "POST",
		url: '../../Link/deleteLink/' + id,
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
