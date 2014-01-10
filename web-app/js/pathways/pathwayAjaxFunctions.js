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


function jsonWrap(result, collection){
     var ids = [];
    var de = result.dataElements;
    for (j in de){
        var rid = result.dataElements[j].id;
        ids.push(rid);
    }
    var jsonObject ={saveBasketCollection:"Save Collection",
        collection_basket_id:result.id ,
        name:collection.name ,
        description:collection.description,
        dataElementIds:ids
    };
    var jsonStuff = JSON.stringify(jsonObject);
    return jsonStuff;
}

function createCollection(collection){

    console.log("startCollectionBasket" + collection.name);
    var ncollection = "";
    //I need to get the current collection basket reference if (thread safe? I suspect not)
    $.ajax({
        type: "GET",
        url: root + "/collectionBasket/collectionAsJSON",
        success: function(result){
            if(result!=null){
                ncollection = jsonWrap(result,collection);
            }
        },
        dataType: "json"
    });
    //FIXME : not a very elegant way to ensure method is called with data available
    setTimeout(function(){
        var data =  ncollection;
        $.ajax({
            type: "POST",
            url: root + "/collection/saveDEBasketCollection",
            data: {jsonobject: data},
            success: function(e){
            },
            dataType: "json"
        });
    },300);
}
function getLink(linkId){

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
