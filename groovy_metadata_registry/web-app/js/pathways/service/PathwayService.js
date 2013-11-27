﻿var PathwayService = function () {
	
	var self = this;
	
    self.loadPathway = function (id) {
        //Load a pathway model from server
    };

    self.updatePathway = function(pathwayModel){
    	
    	return $.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "/groovy_metadata_registry/pathwaysModel/updatePathwayJSON",
    		data : ko.toJSON(pathwayModel),
    		contentType: "application/json; charset=utf-8",
    		/*success : function(data){
    			console.log(data)
    			vm.updatePathwayFromServer(data.id)
    		},
    		error : function(xhr, ajaxOptions, thrownError) {
    			console.log("Creation of pathway failed: " + thrownError);
    			failure();
    		}*/
    	});

    }
    
    self.savePathway = function (model) {

    	
    	return $.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "/groovy_metadata_registry/pathwaysModel/createPathwayFromJSON",
    		data : ko.toJSON(model),
    		contentType: "application/json; charset=utf-8",
    		/*success : function(data){
    			console.log(data)
    			vm.updatePathwayFromServer(data.id)
    		},
    		error : function(xhr, ajaxOptions, thrownError) {
    			console.log("Creation of pathway failed: " + thrownError);
    			failure();
    		}*/
    	});

    };
    
    self.createNode = function(jsonNodeToServer){

    	return $.ajax({
    		type: "POST",
    		url: "/groovy_metadata_registry/Node/createNodeFromJSON",
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
    
    self.createJsonNode = function(node, pathwayId){
    	var jsonNodeToServer = {}
    	var nodeInstance = {}
    	nodeInstance.refId = node.name
    	nodeInstance.name = node.name
    	nodeInstance.description = node.description
    	nodeInstance.x = node.x
    	nodeInstance.y = node.y
    	nodeInstance.pathwaysModelId = pathwayId
    	jsonNodeToServer.nodeInstance = nodeInstance
    	//console.log(jsonNodeToServer)
    	return jsonNodeToServer
    }
    
    self.createLink = function(jsonLinkToServer){
    		
    		return $.ajax({
    			type: "POST",
    			url: '/groovy_metadata_registry/Link/createLinkFromJSON',
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
    		url: '/groovy_metadata_registry/Node/deleteNode/' + nodeId,
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
    	linkInstance.refId = link.refId
    	linkInstance.name = link.name
    	linkInstance.pathwaysModelId = pathwayId
    	
    	jsonLinkToServer.linkInstance = linkInstance;
    	return jsonLinkToServer
    }
    
    self.deleteLink = function(linkId){
    	return $.ajax({
    		type: "POST",
    		url: '/groovy_metadata_registry/Link/deleteLink/' + linkId,
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
}