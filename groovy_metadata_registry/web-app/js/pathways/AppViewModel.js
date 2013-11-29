
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
        
        self.updatePathway = function() {
        	
			$.when(pathwayService.updatePathway(self.pathwayModel)).done(function (data) {
			        		//console.log(data)
			        		//console.log('pathway saved')
			        	});

        };
        
        self.editPathway = function() {
        	
        	$('#updatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });

        };
        
        self.loadPathway = function(pathwayJSON){
        	
        	 var pm = new PathwayModel();
        	 pm.name = pathwayJSON.name;
        	 pm.description = pathwayJSON.description;
        	 pm.version = pathwayJSON.version;
        	 pm.id = pathwayJSON.id;
        	 self.pathwayModel = pm;
        	 var nodes = pathwayJSON.nodes;
        	 
        	 
				        	 $.each(nodes, function( index, node ) {
				        		self.loadNode(node);        	 
				        	 });
								     
				   
        	 
        	 //console.log('finished creating nodes')
        	 
        	 setTimeout( function()
							      {
							       var links = pathwayJSON.links;
							       $.each(links, function( index, link ) {
							            		 //console.log('load link')
							            		self.loadLink(link);        	 
							         	 });
							      }, 100);
        	 
        	 
        	 

        }
        
        self.createPathway = function (pathway) {
        	//FIXME this isn't used yet...presumable this will be for the sub pathways
        	////console.log('creating a new pathway')
        	////console.log(pathway)
            var pm = new PathwayModel(pathway);
            return pm;
        };
        

        self.savePathway = function() {

        	
        	var pathway = self.pathwayModel
        	
            //Create the pathway (on server)
        	
        	//console.log(ko.toJSON(pathway))
        	
        	$.when(pathwayService.savePathway(pathway)).done(function (data) {
        		
        		//create pathwayModal
        		self.pathwayModel = pathway;
        		 //Set the new pathway model id given the id created on the server
                self.pathwayModel.id = data.pathwayId;
                self.pathwayModel.version = data.pathwayVersion
               // //console.log(self.pathwayModel.id)
                //Add a default node
                self.saveNode();

                //Hide the create pathway modal
                $('#CreatePathwayModal').modal('hide');
        	});
        	
        };
        

        self.selectNode = function (n) {
            //Set current seletect node to bind to properties panel
            self.selectedNode = n;
        };
        
        self.loadNode = function(JSONNode) {
        	//create the node in the model
        	//console.log('test')
        	var node = new NodeModel();
            node.name = JSONNode.name;
            node.x = JSONNode.x ;
            node.y = JSONNode.y ;
            node.id = JSONNode.id
	        node.version = JSONNode.nodeVersion
	        node.setForms(JSONNode.optionalOutputs)
	         self.pathwayModel.version = JSONNode.pathwaysModelVersion
	         self.pathwayModel.nodes.push(node);
	         //console.log("loadNodecomplete");

        };
        
        
        
        self.createNode = function(){
            	$('#CreateNode').modal({ show: true, keyboard: false, backdrop: 'static' });
        };
        
        self.saveNode = function (createFrom) {
        	
        	var name = 'node' + (new Date().getTime());	 
        	var description = ''
        	
        	if($('#createNodeName').val()!=''){
        		name = $('#createNodeName').val();
        		description = $('#createNodeDescription').val();
        	}
        	
        	//create the node in the model
        	var node = new NodeModel();
            node.name = name;
            node.description = description;
            node.x = '150px';
            node.y = '150px';
            var jsonNodeToServer = pathwayService.createJsonNode(node, self.pathwayModel.id)
           // //console.log(jsonNodeToServer)
            //after the node has been created on the server using the pathways service methods
            //pass the version number and the id from the server to the node and 
            //add it to the pathways model

            $.when(pathwayService.createNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
	                node.id = data.nodeId
	                node.version = data.nodeVersion
	                self.pathwayModel.version = data.pathwaysModelVersion
	                self.pathwayModel.nodes.push(node);
	                ////console.log(ko.toJSON(self.pathwayModel.nodes));
            	}else{
            		alert('node creation failed')
            	}
            });
            $('#createNodeName').val('');
    		$('#createNodeDescription').val('');
            $('#CreateNode').modal('hide');
        };
        
        self.deleteNode = function(nodeId){
        	////console.log('deleting node');
        	
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
		    	
		    	////console.log('deleting link ' + value.connectionId);
		    	self.deleteLink(value.connectionId);
		    	
		    	});
		    
        	
		    
		    //remove the ko node from pathway model
        	$.when(pathwayService.deleteNode(nodeId)).done(function (data) {
		    	////console.log(self.pathwayModel.nodes);
			    ko.utils.arrayRemoveItem(self.pathwayModel.nodes, nodeToDelete);
			    ////console.log(self.pathwayModel.nodes);
        	});
        }
        
        
        self.createLink = function(source, target, connectionId){
        	////console.log('creating link')
        	if(target!=null && source!=null){
	        	var link = new LinkModel();
	        	link.name = 'link_' + source.id + '_' + target.id;
	        	link.source = source;
	        	link.target = target;
	        	link.connectionId = connectionId;
	        	
	        	//If source is current node, and target node is not already in the outputs array, add it to outputs
	            if (!ko.utils.arrayFirst(source.outputs, function (item) { return item === target })) {
	                source.outputs.push(target);     
	                
	            }

	            //If target is current node, and source node is not already in the inputs array, add it to inputs
	            if (!ko.utils.arrayFirst(target.inputs, function (item) { return item === source })) {
	                target.inputs.push(source);
	            }

	        	var jsonLink = pathwayService.createJsonLink(link, self.pathwayModel.id);
	        	$.when(pathwayService.createLink(jsonLink)).done(function (data) {
	        		////console.log(data);
	        		link.id = data.linkId;
	        		link.version = data.linkVersion;
	        		self.pathwayModel.version = data.pathwaysModelVersion
	        		self.pathwayModel.links.push(link);
	        	});
	        		
	        }
        	
        };
        
        self.loadLink = function(JSONLink){
        	//console.log('creating link')
        	var targetid = JSONLink.target;
        	var sourceid = JSONLink.source;
        	
        	//console.log(targetid)
        	//console.log(sourceid)

        	if(targetid!=null && sourceid!=null){
	        	var link = new LinkModel();
	        	var source = null
	        	var target = null
	        	//console.log(ko.toJSON(self.pathwayModel.nodes))
	        	
	        	ko.utils.arrayForEach(self.pathwayModel.nodes, function(node) {
				      if(node.id == targetid){
				    	  target = node;
				    	  //console.log(target)
				    	  //console.log(node)
				      }
				});
	        	
	        	
	        	ko.utils.arrayForEach(self.pathwayModel.nodes, function(node) {
				      if(node.id == sourceid){
				    	  source = node;
				    	  //console.log(source)
				    	  //console.log(node)
				      }
				});
	        	
	        	if(source!=null && target!=null){
	        	  	        
	        		link.id = JSONLink.id
	        		
		        	link.name = 'link_' + source.id + '_' + target.id;
		        	link.source = source;
		        	link.target = target;
		        	link.connectionId = 'connection_' + (new Date().getTime());	        	
		        	//If source is current node, and target node is not already in the outputs array, add it to outputs
		            if (!ko.utils.arrayFirst(source.outputs, function (item) { return item === target })) {
		                source.outputs.push(target);        
		            }
		            //If target is current node, and source node is not already in the inputs array, add it to inputs
		            if (!ko.utils.arrayFirst(target.inputs, function (item) { return item === source })) {
		                target.inputs.push(source);
		            }
		            
		        	link.version = JSONLink.linkVersion;	
		        	self.pathwayModel.links.push(link);	 
		        	
		        	var sourceDiv = "node" + source.id
		        	var targetDiv = "node" + target.id
		        	
		        	//console.log('divy stuff')
		        	//console.log(sourceDiv)
		        	
		        	//console.log($("#" + sourceDiv).attr('id'))
		        	
		        	jsPlumb.connect({
		        			source: sourceDiv,
		       				target: targetDiv,
		       				parameters: {
		       					"connectionId" : link.connectionId
		       				},
		       				anchor : 'Continuous',
		       				paintStyle:{ strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4  }
		        	});
		        		
		        		
	        	}
	        }
        	
        };
        
        self.deleteLink = function(connectionId){
        	
        	////console.log(connectionId)
        	
        	var link;
		    ko.utils.arrayForEach(self.pathwayModel.links, function(connection) {
		      if(connection.connectionId == connectionId){
		    	 link = connection;
		      }
		    });
		    
		    
		    //remove inputs/outputs
		    
		    var source = link.source; //Get the source node model instance            
   		    var target = link.target; //Get the target node model instance
   		    
   		    ////console.log(source.outputs)
   			////console.log(target.inputs)
   			
   			//If source is current node, and target node is not already in the outputs array, add it to outputs
		    if (ko.utils.arrayFirst(source.outputs, function (item) { return item === target })) {
		    	ko.utils.arrayRemoveItem(source.outputs, target);   
		        
		    }
		
		    //If target is current node, and source node is not already in the inputs array, add it to inputs
		    if (ko.utils.arrayFirst(target.inputs, function (item) { return item === source })) {
		        ko.utils.arrayRemoveItem(target.inputs, source);
		    }
		    
		    
		    //remove the link itself
		    
		    $.when(pathwayService.deleteLink(link.id)).done(function (data) {
		    	////console.log(self.pathwayModel.links);
			    ko.utils.arrayRemoveItem(self.pathwayModel.links, link);
			   // //console.log(self.pathwayModel.links);
        	});

        }

        //#endregion

        
        //FIXME  need to pu this into either the node model method and find a better way to call it or take all
        // he methods out of the node model and put them here 
		self.addFormFinish = function(){
		        	  $('#AddFormModal').modal('hide');
		        }
        
        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
    
    
    
    
    
    
    
