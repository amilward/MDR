
    //The main view model
    var AppViewModel = function () {
    	
        var self = this;

        //Pathway Model
        self.pathwayModel = undefined;

        //View related properties
        self.selectedNode = undefined;

        self.availableForms = [];

        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

        //#region View related functions/logic

        self.createPathway = function (pathway) {
        	//FIXME this isn't used yet...presumable this will be for the sub pathways
        	console.log('creating a new pathway')
        	console.log(pathway)
            var pm = new PathwayModel(pathway);
            return pm;
        };

        self.savePathwayToServer = function(model) {

            //Create the pathway (on server)
        	
        	$.when(pathwayService.savePathway(model)).done(function (data) {
        		
        		//create pathwayModal
        		self.pathwayModel = model;
        		 //Set the new pathway model id given the id created on the server
                self.pathwayModel.id = data.id;
                console.log(self.pathwayModel.id)
                //Add a default node
                self.createNode();

                //Hide the create pathway modal
                $('#CreatePathwayModal').modal('hide');
        	});
        	
        };
        

        self.selectNode = function (n) {
            //Set current seletect node to bind to properties panel
            self.selectedNode = n;
        };

        self.createNode = function () {
        	//create the node in the model
        	var node = new NodeModel();
            node.name = 'node' + (new Date().getTime());
            node.x = 0;
            node.y = 0;
            //create a json representation of the node to send to the server using the pathways service
            var jsonNodeToServer = pathwayService.createJsonNode(node, self.pathwayModel.id)
            console.log(jsonNodeToServer)
            //after the node has been created on the server using the pathways service methods
            //pass the version number and the id from the server to the node and 
            //add it to the pathways model

            $.when(pathwayService.createNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
	                node.id = data.nodeId
	                node.version = data.nodeVersion
	                self.pathwayModel.nodes.push(node);
	                //console.log(ko.toJSON(self.pathwayModel.nodes));
            	}else{
            		alert('node creation failed')
            	}
            });
        };
        
        self.deleteNode = function(nodeId){
        	console.log('deleting node');
        	
        	//get ko node
        	var nodeToDelete;
		    ko.utils.arrayForEach(self.pathwayModel.nodes, function(node) {
		      if(node.id == nodeId){
		    	  nodeToDelete = node;
		      }
		    });
		    
		    //remove all links associated with ko node
		    
		    var linksToDelete = []
		    
		    ko.utils.arrayForEach(self.pathwayModel.links, function(link) {
			      if(link.target == nodeToDelete){
			    	  linksToDelete.push(link)
			      }
			});
		    
		    ko.utils.arrayForEach(self.pathwayModel.links, function(link) {
			      if(link.source == nodeToDelete){
			    	  linksToDelete.push(link)
			      }
			});
		    
		    $.each(linksToDelete, function( index, value ) {
		    	
		    	console.log('deleting link ' + value.connectionId);
		    	self.deleteLink(value.connectionId);
		    	
		    	});
		    
        	
		    
		    //remove the ko node from pathway model
        	$.when(pathwayService.deleteNode(nodeId)).done(function (data) {
		    	console.log(self.pathwayModel.nodes);
			    ko.utils.arrayRemoveItem(self.pathwayModel.nodes, nodeToDelete);
			    console.log(self.pathwayModel.nodes);
        	});
        }
       
        
        self.createLink = function(sourceId, targetId, connectionId){
        	console.log('creating link')
        	if(targetId!=null && sourceId!=null){
	        	var link = new LinkModel();
	        	link.name = 'link_' + sourceId + '_' + targetId;
	        	link.source = 'node' + sourceId;
	        	link.target = 'node' + targetId;
	        	link.connectionId = connectionId;
	        	console.log(ko.toJSON(link))
	        	var jsonLink = pathwayService.createJsonLink(link, self.pathwayModel.id);
	        	$.when(pathwayService.createLink(jsonLink)).done(function (data) {
	        		console.log(data);
	        		link.id = data.linkId;
	        		link.version = data.linkVersion;
	        		self.pathwayModel.links.push(link);
	        	});
	        		
	        }
        	
        }
        
        self.deleteLink = function(connectionId){
        	
        	console.log(connectionId)
        	
        	var link;
		    ko.utils.arrayForEach(self.pathwayModel.links, function(connection) {
		      if(connection.connectionId == connectionId){
		    	 link = connection;
		      }
		    });
		
		    
		    $.when(pathwayService.deleteLink(link.id)).done(function (data) {
		    	console.log(self.pathwayModel.links);
			    ko.utils.arrayRemoveItem(self.pathwayModel.links, link);
			    console.log(self.pathwayModel.links);
        	});

        }

        //#endregion

        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
