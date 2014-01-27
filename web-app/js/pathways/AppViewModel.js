
    //The main view model
    var AppViewModel = function () {
    	
        var self = this;
        
        self.topLevelPathway = undefined;

        //Pathway Model
        self.pathwayModel = undefined;
 
        //Container pathway i.e. the parent of the current pathway
        self.containerPathway = undefined;

        //View related properties
        self.selectedItem = undefined;

        //is the current view read only (defaults to false)
        self.readOnly = false;
        
        self.availableForms = [];
        
        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);
        
        //We can now use the observable without ()

        //#region View related functions/logic
        
        self.updatePathway = function() {
        	
        	//console.log(ko.toJSON(self.pathwayModel.versionOnServer))
			$.when(pathwayService.updatePathway(self.pathwayModel))
			.done(function(data){
				if(data.success){
					self.pathwayModel.versionOnServer = data.versionOnServer;
				}else if(data.errors){
					alert(data.details)
				}
			})
			.fail(function(request, status, error){
					if(error == "Internal Server Error"){
						if(!self.readOnly){
							alert("You do not have permissions to modify this pathway and none of your changes will be saved. Please contact your system administrator for permissions")
							self.readOnly = true
						}
					}else if(error == "Unauthorized"){
						location.reload()
					}
					
			});
			
        };

        //delete the current pathway
        self.deletePathway = function(){
            // Reset the confirmation divs on modal close
            $('#updatePathwayModal').on('hidden.bs.modal', function (e) {
                $( "#deletePathway-confirmation" ).hide();
                $( "#deletePathway-initial" ).show();
            })

        	$( "#deletePathway-confirmation" ).show();
            $( "#deletePathway-initial").hide();

        	$( "#confirmDeletePathway" ).bind('click', function(){
	        	$.when(pathwayService.deletePathway(self.pathwayModel.id)).done(function (data) {
	    	   		// FIXME What about failure?
                    if(data.success){
		        		window.location = '../list';
		        	}
		        });
        	});
            $( "#cancelDeletePathway" ).bind('click', function(){
                $( "#deletePathway-confirmation" ).hide();
                $( "#deletePathway-initial" ).show();
            });
        	
        }
      

        
        //this is used to display a pathway when given pathway JSON
        
        self.loadPathway = function(pathwayJSON){
        	
        	 //self.selectedItem = undefined;
        	 var pm = self.createPathway(pathwayJSON)
        	 self.pathwayModel = pm;
        	    
        	 //console.log('finished creating nodes')
//				        	 setTimeout( function()
//				 {
							var links = pathwayJSON.links;
							 $.each(links, function( index, link ) {
							      self.loadLink(link);        	 
							 });

//							      }, 200);
                if (!self.topLevelPathway) {
                	if(pm.topLevelPathwayId){
                		$.when(pathwayService.loadPathway(pm.topLevelPathwayId)).done(function (data) {
                			var tlpm = self.createPathway(data.pathwaysModelInstance);
                   		 	self.topLevelPathway = tlpm;
                   		 	//console.log(self.topLevelPathway);
                		});
                		 
                	}else{
                		self.topLevelPathway = self.pathwayModel;
                	}
                    
                }
                
             self.selectedItem = undefined;
        }
        
        self.logoutFromApp = function(){
        	
        	var jqxhr = $.ajax({
        		type: "POST",
        		url: "../../logout/index",
        		contentType: 'application/json',
        		dataType: 'json'
        		});
        	alert("logoutFromApp::DONE : ../../logout/index");        	
        }
        
        self.createPathway = function (pathwayJSON) {
        	
        	var pm = new PathwayModel();
	       	 pm.name = pathwayJSON.name;
	       	 pm.description = pathwayJSON.description;
	       	 pm.versionOnServer = pathwayJSON.version;
	       	 pm.versionNo = pathwayJSON.versionNo;
	       	 pm.id = pathwayJSON.id;
	       	 pm.isDraft = pathwayJSON.isDraft.toString();
	       	 pm.parentPathwayId = pathwayJSON.parentPathwayId;
	       	 pm.parentNodeId = pathwayJSON.parentNodeId;
	       	 pm.topLevelPathwayId = pathwayJSON.topLevelPathwayId
	       	 
	       	var nodes = pathwayJSON.nodes;
     	    $.each(nodes, function( index, node ) {
				        self.loadNode(node, pm);        	 
				 });
	       	 
            return pm;
        };
        

        self.savePathway = function() {

        	
        	var pathway = self.pathwayModel
        	
            //Create the pathway (on server)
        	
        	$.when(pathwayService.savePathway(pathway)).done(function (data) {
        		
        		//create pathwayModal
        		self.pathwayModel = pathway;
        		 //Set the new pathway model id given the id created on the server
                self.pathwayModel.id = data.pathwayId;
                self.pathwayModel.versionOnServer = data.versionOnServer
               // //console.log(self.pathwayModel.id)
                //Add a default node
                self.saveNode();

                //Hide the create pathway modal
                $('#CreatePathwayModal').modal('hide');
        	});
        	
        };

        self.selectNode = function (n, e) {
            if (e) {
                //Check whether selected item has a parent node (i.e. whether in subpathway)
                var bindingContext = ko.contextFor(e.target);
                if (bindingContext.$parent instanceof NodeModel) {
                    //Switch to subpathway
                    $.when(pathwayService.loadPathway(bindingContext.$parent.subPathwayId)).done(function (pathwayJSON) {
                        
                    	self.containerPathway = self.pathwayModel;
                        self.loadPathway(pathwayJSON.pathwaysModelInstance);
                        
                        self.selectedItem = ko.utils.arrayFirst(self.pathwayModel.nodes, function (node) { return node.id === n.id });
                        $('#properties-panel .form-group input').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
                        $('#properties-panel .form-group textarea').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
                    });
                } else if (bindingContext.$parent && bindingContext.$parent === self.topLevelPathway && self.containerPathway) {
                	$.when(pathwayService.loadPathway(n.pathwayId)).done(function (pathwayJSON) {
						self.loadPathway(pathwayJSON.pathwaysModelInstance);
						self.selectedItem = ko.utils.arrayFirst(self.pathwayModel.nodes, function (node) { return node.id === n.id });
			              
					});
                }
            }
            self.selectedItem = n;
            console.log("SELECTED:" + n.name);
            console.log("SELECTED:" + self.selectedItem.name);
            
            $('#properties-panel .form-group input').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
            $('#properties-panel .form-group textarea').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
            
            
        };
        
        self.getNodeName = function (n) {
            //Set current seletect node to bind to properties panel
        	//console.log(ko.toJSON(n))
        	return ko.toJSON(n);
           // self.selectedItem = n;
        };
        
        self.loadNode = function(JSONNode, pm) {
        	//create the node in the model
        	//create the node in the model
        	//console.log(JSONNode.pathwaysModelVersion)
        	var node = new NodeModel();
            var subNodes;
            node.name = JSONNode.name;
            node.description = JSONNode.description;
            node.pathwayId = pm.id;
            node.subPathwayName = JSONNode.subModelName;
            node.subPathwayId = JSONNode.subModelId;
            node.name = JSONNode.name;
            node.x = JSONNode.x ;
            node.y = JSONNode.y ;
            node.id = JSONNode.id;
	        node.versionOnServer = JSONNode.nodeVersion;
            // FIXME the following does lots of AJAX calls, we should probably grab the tree in one JSON hit.
            subNodes = node.getSubNodes();

            node.setCollections(JSONNode.optionalOutputs);
            pm.versionOnServer = JSONNode.pathwaysModelVersion;
            pm.nodes.push(node);
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
	                node.versionOnServer = data.nodeVersion
	                self.pathwayModel.versionOnServer = data.pathwaysModelVersion
	                self.pathwayModel.nodes.push(node);
	                
	                //refresh the top level pathway if there have been updates
	                
	                if(self.pathwayModel.id == self.topLevelPathway.id){
	                	self.topLevelPathway = self.pathwayModel
	                }else if(node.pathwayId == self.topLevelPathway.id){
	                	$.when(pathwayService.loadPathway(self.topLevelPathway.id)).done(function (data) {
                			var tlpm = self.createPathway(data.pathwaysModelInstance);
                   		 	self.topLevelPathway = tlpm;
                		});
	                }
	                
            	}else{
            		alert('node creation failed')
            	}
            });
            
            
            
            $('#createNodeName').val('');
    		$('#createNodeDescription').val('');
            $('#CreateNode').modal('hide');
        };
        
        self.deleteNode = function(nodeId){

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
		    		self.deleteLink(value.connectionId);
		    	});
		    
        	
		    
		    //remove the ko node from pathway model
        	$.when(pathwayService.deleteNode(nodeId)).done(function (data) {
			    ko.utils.arrayRemoveItem(self.pathwayModel.nodes, nodeToDelete);
			    ////console.log(self.pathwayModel.nodes);
        	});
                
                //Only modify the right panel if the currently displayed properties belong to the deleted node
                if (self.selectedItem === nodeToDelete) {
                    self.selectedItem = undefined;
                }
                
                //Notify treeview that the content have changed
                ko.getObservable(self, 'topLevelPathway').valueHasMutated();
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
	        		link.versionOnServer = data.linkVersion;
	        		self.pathwayModel.versionOnServer = data.pathwaysModelVersion
	        		self.pathwayModel.links.push(link);
	        	});
	        		
	        }
        	
        };
        
        self.loadLink = function(JSONLink){
        	var targetid = JSONLink.target;
        	var sourceid = JSONLink.source;

        	if(targetid!=null && sourceid!=null){
	        	var link = new LinkModel();
	        	var source = null
	        	var target = null
	        	
	        	ko.utils.arrayForEach(self.pathwayModel.nodes, function(node) {
				      if(node.id == targetid){
				    	  target = node;
				      }
				});
	        	
	        	
	        	ko.utils.arrayForEach(self.pathwayModel.nodes, function(node) {
				      if(node.id == sourceid){
				    	  source = node;

				      }
				});
	        	
	        	if(source!=null && target!=null){
	        	  	        
	        		link.id = JSONLink.id;
	        		
		        	link.name = JSONLink.name;
		        	link.source = source;
		        	link.target = target;
                                link.description = JSONLink.description;
		        	link.connectionId = 'connection_' + source.id + '_' + target.id;        	
		        	//If source is current node, and target node is not already in the outputs array, add it to outputs
		            if (!ko.utils.arrayFirst(source.outputs, function (item) { return item === target })) {
		                source.outputs.push(target);        
		            }
		            //If target is current node, and source node is not already in the inputs array, add it to inputs
		            if (!ko.utils.arrayFirst(target.inputs, function (item) { return item === source })) {
		                target.inputs.push(source);
		            }
		            
		        	link.versionOnServer = JSONLink.linkVersion;	
		        	self.pathwayModel.links.push(link);	 
		        	
		        	var sourceDiv = "node" + source.id
		        	var targetDiv = "node" + target.id

		        	
		        	jsPlumb.connect({
		        			source: sourceDiv,
		       				target: targetDiv,
		       				parameters: {
		       					"connectionId" : link.connectionId
		       				},
		       				anchor : 'Continuous',
		       				paintStyle:{ strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4  }
                                                /*
                                                overlays:[ 
                                                    [ "Label", { label: link.name, location:0.25, id:link.connectionId } ]
                                                ],*/
		        	});
		        		
		        		
	        	}
	        }
        	
        };
        
        self.deleteLink = function(connectionId){

        	var link;
		    ko.utils.arrayForEach(self.pathwayModel.links, function(connection) {
		      if(connection.connectionId == connectionId){
		    	 link = connection;
		      }
		    });
		    //remove inputs/outputs
		    var source = link.source; //Get the source node model instance            
   		    var target = link.target; //Get the target node model instance
   			
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
			    ko.utils.arrayRemoveItem(self.pathwayModel.links, link);
        	});

            //Only modify the right panel if the currently displayed properties belong to the deleted node
            if (self.selectedItem === link) {
                self.selectedItem = undefined;
            }

            ko.utils.arrayForEach( jsPlumb.getConnections(), function(connection) {
                if(connection.getParameter("connectionId") == link.connectionId){
                    jsPlumb.detach(connection);
                }
            });
        }
        
        self.selectLink = function(connectionId) {
            var l = ko.utils.arrayFirst(self.pathwayModel.links, function (link) { return link.connectionId === connectionId });
            self.selectedItem = l;
        };
        
        //#endregion
        self.addFormFinish = function(){
      	    $('#AddFormModal').modal('hide');
        }

        self.addNodeCancel = function(){

            $('#createNodeName').val('');
            $('#createNodeDescription').val('');
            $('#CreateNode').modal('hide');
        }
        self.refreshCollections = function(){
            self.selectedItem.refreshCollections();
        }
        self.addCollectionFinish = function(){
            self.selectedItem.hideCollectionDialog();
        }

        self.addNewDECollection = function(){

            //console.log("addNewDECollection" + self.selectedItem.name );

            self.selectedItem.hideCollectionDialog();
            self.selectedItem.showNewDECollectionDialog();
        }

        self.addNewDECollectionFinish = function(){
            self.selectedItem.hideNewDECollectionDialog();
            self.selectedItem.showCollectionDialog();
        }
        
        self.deleteSelectedElement = function(){
            if(self.selectedItem instanceof LinkModel){
                self.deleteLink(self.selectedItem.connectionId);
            }else{
                //assume it's a node instead
                self.deleteNode(self.selectedItem.id);
            }
        };
        
	
		
		self.isSubPathway = function(){
        
			if(self.pathwayModel){
				if(self.pathwayModel.parentPathwayId!=undefined && self.pathwayModel.parentPathwayId!=null){
					return true
				}else{
					return false
				}
			}else{
				false
			}
		}
		
		self.goToParent = function(){
			if(self.pathwayModel){
				
				$.when(pathwayService.loadPathway(self.pathwayModel.parentPathwayId)).done(function (pathwayJSON) {
					self.loadPathway(pathwayJSON.pathwaysModelInstance);
				});

			}
		}
                        
        self.gotoContainerPathway = function() {
            self.pathwayModel = self.containerPathway;
            self.containerPathway = undefined;
        };
        
        self.itemEqualsToSelected = function(item) {
            return (item && self.selectedItem && item.id === self.selectedItem.id)
        };
        
        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
    
    
    
    
    
    
    
