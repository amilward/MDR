var PathwayService = function () {
	
	var self = this;
	
	//FIXME at the moment the gsp passes in the json....we should probably change this
	
    self.loadPathway = function (id) {
    	
    	 //Load a pathway model from server
    	
    	return $.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "../../pathwaysModel/jsonPathways/" + id,
    		contentType: "application/json; charset=utf-8",
    	});
       
    };
    
    //delete a pathway
    self.deletePathway = function(pathwaysModelId){
	    return $.ajax({
			type: "POST",
			url: '../../pathwaysModel/delete/' + pathwaysModelId,
			contentType: 'application/json',
			});
    }
    //update an existing pathway

    self.updatePathway = function(pathwayModel){
    	return $.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "../../pathwaysModel/updatePathwayJSON",
    		data : ko.toJSON(pathwayModel),
    		contentType: "application/json; charset=utf-8"
    		
    	});

    }
    
    //create a pathway and save it
    
    self.savePathway = function (model) {

    	return $.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "../../pathwaysModel/createPathwayFromJSON",
    		data : ko.toJSON(model),
    		contentType: "application/json; charset=utf-8"
    	});

    };
    
    self.createNode = function(jsonNodeToServer){

    	return $.ajax({
    		type: "POST",
    		url: "../../Node/createNodeFromJSON",
    		data: self.stringify(jsonNodeToServer),
    		/*success: function(data){
    			console.log(data);
    			vm.updateNodeFromServer(data.nodeId)
    		},
    		error: function (xhr, ajaxOptions, thrownError) {
    	        console.log(xhr.status);
    	        alert(thrownError);
    	      },*/
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    	
    	
    };
    
    self.getPathwayNodes = function(pathwayId){
    	return $.ajax({
    		type: "POST",
    		url: "../../PathwaysModel/getNodes/" + pathwayId,
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    }
    
    self.updateNode = function(jsonNodeToServer){
    	return $.ajax({
    		type: "POST",
    		url: "../../Node/updateNodeFromJSON",
    		data: self.stringify(jsonNodeToServer),
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    }
    
    
    self.createJsonNode = function(node, pathwayId){
    	var jsonNodeToServer = {}
    	var nodeInstance = {}
    	nodeInstance.id = node.id
    	nodeInstance.versionOnServer = node.version
    	nodeInstance.name = node.name
    	nodeInstance.description = node.description
    	nodeInstance.x = node.x
    	nodeInstance.y = node.y
    	nodeInstance.forms = node.forms
    	nodeInstance.collections = node.collections
    	nodeInstance.pathwaysModelId = pathwayId
    	jsonNodeToServer.nodeInstance = nodeInstance
    	//console.log(jsonNodeToServer)
    	return jsonNodeToServer
    }
    
    self.createLink = function(jsonLinkToServer){
    		
    		return $.ajax({
    			type: "POST",
    			url: '../../Link/createLinkFromJSON',
    			data: self.stringify(jsonLinkToServer),
    			/*success: function(data){
    				console.log(data.message);
    				
    			},
    			error: function (xhr, ajaxOptions, thrownError) {
    		        console.log(xhr.status);
    		        alert(thrownError);
    		      },*/
    			contentType: 'application/json',
    			dataType: 'json'
    			});

    
    }
    
    self.deleteNode = function(nodeId){
    	return $.ajax({
    		type: "POST",
    		url: '../../Node/deleteNode/' + nodeId,
    		/*success: function(data){
    			console.log(data.message);
    		},
    		error: function (xhr, ajaxOptions, thrownError) {
    	        console.log(xhr.status);
    	        alert(thrownError);
    	      },*/
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    }
    
    //FIXME need to implement this but ok at the moment to use the updateNodeFunction
    self.addFormToNode = function(){
    	
    }
    
    //FIXME need to implement this but ok at the moment to use the updateNodeFunction
    self.removeFormFromNode = function(){	
    	
    }
    
    //FIXME need to implement this but ok at the moment to use the updateNodeFunction
    self.addCollectionToNode = function(){	
    }
    
  //FIXME need to implement this but ok at the moment to use the updateNodeFunction
    self.removeCollectionFromNode = function(){	
    }
    
    //this method gets around problems with references to other nodes
    //i.e. fixes the TypeError: cyclic object value
    self.stringify = function(jsonObject){
    	var seen = [];
    	var jso = JSON.stringify(jsonObject, function(key, val) {
    		   if (typeof val == "object") {
    		        if (seen.indexOf(val) >= 0)
    		            return
    		        seen.push(val);
    		    }
    		    return val;
    		})
    	return jso;
    }
    
    
    self.createJsonLink = function(link, pathwayId){
    	var jsonLinkToServer = {}
    	var linkInstance = {}
    	linkInstance.source = link.source.id
    	linkInstance.target = link.target.id
    	linkInstance.name = link.name
    	linkInstance.pathwaysModelId = pathwayId
    	linkInstance.versionOnServer = link.version
    	
    	jsonLinkToServer.linkInstance = linkInstance;
    	return jsonLinkToServer
    }
    
    self.deleteLink = function(linkId){
    	return $.ajax({
    		type: "POST",
    		url: '../../Link/deleteLink/' + linkId,
    		/*success: function(data){
    			console.log(data.message);
    		},
    		error: function (xhr, ajaxOptions, thrownError) {
    	        console.log(xhr.status);
    	        alert(thrownError);
    	      },*/
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    }

    self.jsonWrap = function(result, collection){
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

    self.createCollection = function(collection){

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
}