
    var loadPathway = function (id) {
        //Load a pathway model from server
    };

    var savePathway = function (model) {

        //Turn model into pure JSON string
        var jsonModel = ko.toJSON(model); 
        
        console.log(jsonModel)

     // FIXME Save pathway model to server
    	//TODO remove console log
    	console.log("posting to server" + jsonModel)
    	
    	$.ajax({
    		type : "POST",
    		// FIXME remove static app name
    		url : "/groovy_metadata_registry/pathwaysModel/saveREST",
    		data : jsonModel,
    		contentType: "application/json; charset=utf-8",
    		success : function(data){
    			console.log(data)
    			vm.updatePathwayFromServer(data.id)
    		},
    		error : function(xhr, ajaxOptions, thrownError) {
    			console.log("Creation of pathway failed: " + thrownError);
    			failure();
    		}
    	});
        
        
        //Save pathway model to server
    };
    
    var createNode = function(node, pathwayId){
    	
    	console.log('test')
    	//Turn node into pure JSON String
    	var jsonNode = ko.toJSON(node); 
    	console.log(jsonNode)
    	
    	var jsonNodeToServer = {}
    	var nodeInstance = {}
    	nodeInstance.refId = node.name
    	nodeInstance.name = node.name
    	nodeInstance.description = node.description
    	nodeInstance.x = node.x
    	nodeInstance.y = node.y
    	nodeInstance.pathwaysModelId = pathwayId
    	
    	jsonNodeToServer.nodeInstance = nodeInstance
    	
    	console.log(JSON.stringify(jsonNodeToServer))
    	
    	//{"id":"node-1384963617899","name":"node1384963617901","type":"node","inputs":[],"outputs":[]}
    	//{'nodeInstance':{'refId': 'TMN123','pathwaysModelId': 1, 'name':'transfer to O.R. TEST CREATE','description':'test ccng Room','x':'15','y':'10','mandatoryInputs':[],'mandatoryOutputs':[],'optionalInputs':[],'optionalOutputs':[]}})
    	
    	$.ajax({
    		type: "POST",
    		url: "/groovy_metadata_registry/Node/createNodeFromJSON",
    		data: JSON.stringify(jsonNodeToServer),
    		success: function(data){
    			console.log(data);
    			vm.updateNodeFromServer(data.nodeId)
    		},
    		error: function (xhr, ajaxOptions, thrownError) {
    	        console.log(xhr.status);
    	        alert(thrownError);
    	      },
    		contentType: 'application/json',
    		dataType: 'json'
    		});
    	
    	
    };
    
    var createLink = function(link, pathwayId){
    	
    	//{'linkInstance':{'source':'node2','pathwaysModelId':1, 'target':'node3','refId':'testRef', 'name':'Test create link'}}
    
    	console.log(link)

    	var jsonLinkToServer = {}
    	var linkInstance = {}
    	linkInstance.source = link.source
    	linkInstance.target = link.target
    	linkInstance.refId = link.refId
    	linkInstance.name = link.name
    	linkInstance.pathwaysModelId = pathwayId
    	
    	jsonLinkToServer.linkInstance = linkInstance;
    	
    	console.log(jsonLinkToServer)
    		
    		$.ajax({
    			type: "POST",
    			url: '/groovy_metadata_registry/Link/createLinkFromJSON',
    			data: JSON.stringify(jsonLinkToServer),
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
