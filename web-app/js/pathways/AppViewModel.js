﻿
    //The main view model
    var AppViewModel = function () {
    	
        var self = this;
        
        self.topLevelPathway = undefined;

        //Pathway Model
        self.pathwayModel = undefined;
 
        //FIX ME not using this at the moment - might be worth using it it cache 
        self.containerPathway = undefined;

        //View related properties
        self.selectedItem = undefined;

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
						alert("you do not have permissions to modify this pathway please contact your system administrator")
					}else if(error == "Unauthorized"){
						location.reload()
					}
					
			});
			
        };
        
        //delete the current pathway
        
        self.deletePathway = function(){
        	$( "#dialog-confirm .modal-header h4" ).text('Delete PathwaysModel?');
        	$( "#dialog-confirm" ).modal({ show: true, keyboard: false, backdrop: 'static' });
        	$( "#deleteModalButton" ).bind('click', function(){
    	   			
	        		$.when(pathwayService.deletePathway(self.pathwayModel.id)).done(function (data) {
		        		$( "#deleteModalButton" ).unbind();
	    	   			$('.modal').modal('hide');
	    	   			if(data.success){
		        			window.location = '../list';
		        		}
		        	});
        		
    	   			
        	})
        	
        }
      
        
        //displays a modal that is bound to the pathway and allows you to edit the pathway info
        //i.e. name, description etc
        
        self.editPathway = function() {
        	$('#updatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });

        };
        
        //this is used to display a pathway when given pathway JSON
        
        self.loadPathway = function(pathwayJSON){
        	
        	 //self.selectedItem = undefined;
        	 var pm = new PathwayModel();
        	 pm.name = pathwayJSON.name;
        	 pm.description = pathwayJSON.description;
        	 pm.versionOnServer = pathwayJSON.version;
        	 pm.versionNo = pathwayJSON.versionNo;
        	 pm.id = pathwayJSON.id;
        	 pm.isDraft = pathwayJSON.isDraft.toString();
        	 pm.parentPathwayId = pathwayJSON.parentPathwayId;
        	 pm.parentNodeId = pathwayJSON.parentNodeId;
        	 self.pathwayModel = pm;
        	 var nodes = pathwayJSON.nodes;
        	    $.each(nodes, function( index, node ) {
				        self.loadNode(node);        	 
				 });
        	 				   
        	 //console.log('finished creating nodes')
//				        	 setTimeout( function()
//				 {
							var links = pathwayJSON.links;
							 $.each(links, function( index, link ) {
							      self.loadLink(link);        	 
							 });
//							      }, 200);
                if (!self.topLevelPathway || self.topLevelPathway.id === self.pathwayModel.id) {
                    self.topLevelPathway = self.pathwayModel;
                }
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
                if (bindingContext.$parent && bindingContext.$parent instanceof NodeModel) {
                    //Switch to subpathway
                    $.when(pathwayService.loadPathway(bindingContext.$parent.subPathwayId)).done(function (pathwayJSON) {
                        self.containerPathway = self.pathwayModel;
                        self.loadPathway(pathwayJSON.pathwaysModelInstance);
                        
                        self.selectedItem = ko.utils.arrayFirst(self.pathwayModel.nodes, function (node) { return node.id === n.id });
                        $('#properties-panel .form-group input').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
                        $('#properties-panel .form-group textarea').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
                    });
                } else if (bindingContext.$parent && bindingContext.$parent === self.topLevelPathway && self.containerPathway) {
                    self.goToParent();
                    self.selectedItem = ko.utils.arrayFirst(self.pathwayModel.nodes, function (node) { return node.id === n.id });
                }
            }
            self.selectedItem = n;
            
            $('#properties-panel .form-group input').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
            $('#properties-panel .form-group textarea').css({'max-width': $('#properties-panel').width() - 15, 'min-width': $('#properties-panel').width() - 15});
            
            
        };
        
        self.getNodeName = function (n) {
            //Set current seletect node to bind to properties panel
        	//console.log(ko.toJSON(n))
        	return ko.toJSON(n);
           // self.selectedItem = n;
        };
        
        self.loadNode = function(JSONNode) {
        	//create the node in the model
        	//create the node in the model
        	//console.log(JSONNode.pathwaysModelVersion)
        	var node = new NodeModel();
            node.name = JSONNode.name;
            node.description = JSONNode.description;
            node.subPathwayName = JSONNode.subModelName;
            node.subPathwayId = JSONNode.subModelId;
            node.name = JSONNode.name;
            node.x = JSONNode.x ;
            node.y = JSONNode.y ;
            node.id = JSONNode.id
	        node.versionOnServer = JSONNode.nodeVersion
            node.setCollections(JSONNode.optionalOutputs);
	        self.pathwayModel.versionOnServer = JSONNode.pathwaysModelVersion
	        self.pathwayModel.nodes.push(node);
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
	                node.versionOnServer = data.nodeVersion
	                self.pathwayModel.versionOnServer = data.pathwaysModelVersion
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
        		//console.log('test')
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
		       				paintStyle:{ strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4  },
                                                /*
                                                overlays:[ 
                                                    [ "Label", { label: link.name, location:0.25, id:link.connectionId } ]
                                                ],*/
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
        
        self.selectLink = function(connectionId) {
            var l = ko.utils.arrayFirst(self.pathwayModel.links, function (link) { return link.connectionId === connectionId });
            self.selectedItem = l;
        };
        
        //#endregion
        //FIXME  need to pu this into either the node model method and find a better way to call it or take all
        // he methods out of the node model and put them here 
        self.addFormFinish = function(){
      	    $('#AddFormModal').modal('hide');
        }
	
        self.addCollectionFinish = function(){
        	$('#AddCollectionModal').modal('hide');
        }
	
		
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
					//console.log('test')
					//console.log(pathwayJSON.pathwaysModelInstance)
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
    
    
    
    
    
    
    