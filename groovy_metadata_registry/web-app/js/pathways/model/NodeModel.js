
    var NodeModel = function () {
        var self = this;

        //TODO: better id generation
        self.id = undefined
        self.name = undefined
        self.version = undefined
        self.description = undefined;
        self.type = 'node' //'node' | 'pathway'
        self.x = undefined
        self.y = undefined
        self.subPathway = undefined;
        self.subPathwayId = undefined;
        self.subPathwayName = undefined;
        self.subNodes = [];
        self.inputs = [];
        self.outputs = [];
        self.forms = [];
        self.collections = [];
    	

        ko.track(self);

        
        self.setCollections = function(JSONCollections){

        	$.each(JSONCollections, function(index, JSONCollection){    
        		if(JSONCollection.collectionType==='form'){
        			var form = new FormModel()
    	        	form.id = JSONCollection.id
    	        	form.name = JSONCollection.name	        	
    	        	self.forms.push(form);
		        	
        		}else{
        			var collection = new CollectionModel()
		        	collection.id = JSONCollection.id
		        	collection.name = JSONCollection.name        	
		        	self.collections.push(collection);
        			
        		}
        	});
        	
        }
        
        self.getSubNodes = function(){
        	if(self.subPathwayId){
        		
        	$.when(pathwayService.getPathwayNodes(self.subPathwayId)).done(function (data) {
            	if(data.success===true){
            		console.log(data.nodes)
            		//reset subNodes
            		self.subNodes = [];
            		$.each(data.nodes, function(index, value){
            			var node = new NodeModel()
                		node.id = value.id
                		node.name = value.name
                		if(value.subModelId){
                			node.subPathwayId = value.subModelId;
                		}else{
                			node.subPathwayId = null;
                		}
                		self.subNodes.push(node);
            		})
            		console.log(self.subNodes)
            		}
            	});
        	}
        }
        
        self.addForm = function(form){
        	
	        self.forms.push(form)
	        //FIXME this is a crude way to update the node
	        	var jsonNodeToServer = pathwayService.createJsonNode(self)
	        	//console.log(jsonNodeToServer)
	        	$.when(pathwayService.updateNode(jsonNodeToServer)).done(function (data) {
	            	if(data.success===true){
	            		console.log('form added on server')
	            		}
				});

        }
        
        self.removeForm = function(formId){
        	
        	ko.utils.arrayForEach(self.forms, function(form) {
  		      if(form.id == formId){
  		    	  formToRemove = form;
  		      }
  		    });
        	//FIXME this is a crude way to update the node
        	ko.utils.arrayRemoveItem(self.forms, formToRemove);
        	var jsonNodeToServer = pathwayService.createJsonNode(self)
        	//console.log(jsonNodeToServer)
        	$.when(pathwayService.updateNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
            		console.log('form removed from server')
            		}
			});
        	
        }
        
       
        
        self.addCollection = function(collection){        	
        	self.collections.push(collection);
        	//FIXME this is a crude way to update the node
        	var jsonNodeToServer = pathwayService.createJsonNode(self)
        	console.log(jsonNodeToServer)
        	$.when(pathwayService.updateNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
            		console.log('collection added to server')
            		}
			});
        }
        
        self.removeCollection = function(collectionId){
        	
        	ko.utils.arrayForEach(self.collections, function(collection) {
  		      if(collection.id == collectionId){
  		    	collectionToRemove = collection;
  		      }
  		    });
        	//FIXME this is a crude way to update the node
        	ko.utils.arrayRemoveItem(self.collections, collectionToRemove);
        	var jsonNodeToServer = pathwayService.createJsonNode(self)
        	//console.log(jsonNodeToServer)
        	$.when(pathwayService.updateNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
            		console.log('collection removed from server')
            		}
			});
        	
        }
        

        self.addCollectionDialog = function(){
        	 $('#AddCollectionModal').modal({ show: true, keyboard: false, backdrop: 'static' });
        	 collectionListDraggable();
        	 $("#collectionCart").droppable({
                 drop: function(event, ui) {
                 	if(c.id){
                 		if(c.type.indexOf("collection") !== -1){ 
                 			$('#collectionCartList').append('<li>' + c.name + '</li>')
                 			var collection = new CollectionModel();
                     		collection.id = c.id;
                     		collection.name = c.name;
                 			self.addCollection(collection);
                 		}else{
                 			//$('#collectionCartList').append('<li>' + c.name + '</li>')
                 			var form = new FormModel();
                    		form.id = c.id
                    		form.name = c.name  
                 			self.addForm(form);
                 		}  
                 		$(c.li).remove();
                 		$(c.helper).remove();
                 	}
                 }
         	});	
        	 
        }
        
        self.addFormDialog = function(){
        	//console.log('addingForm');
        	//Initial action on page load
            $('#AddFormModal').modal({ show: true, keyboard: false, backdrop: 'static' });
            formDesignListDraggable();//this is in js/forms/formDesign.js
            
            /* bind the droppable behaviour for the data elements in the collection basket
        	* This allows you to drag data elements out of the collection basket. This in bound
        	* to the whole page so that the user can drag a data element out of the collections cart 
        	* anywhere on the page to remove them
        	*/
        	
        	$("#formDesignCart").droppable({
                drop: function(event, ui) {
                	if(c.id){
                		$('#formCartList').append('<li>' + c.name + '</li>')
                		var form = new FormModel();
                		form.id = c.id
                		form.name = c.name               		
        	            $(c.li).remove();
        	            $(c.helper).remove();
        	            self.addForm(form)
                	}
                }
        	});	        	
        	//on close delete binding
        };
        
        
        //create a subpathway
        //called from show.gsp
        //creates a subPathway in the Node
        //and adds pathway on the server
        
        self.createSubPathway = function(data, e) {
        	var bindingContext = ko.contextFor(e.target);
            var subPathway = new PathwayModel();
            subPathway.name = self.name;
            subPathway.parentNodeId = self.id
            subPathway.isDraft = true
            
            $.when(pathwayService.savePathway(subPathway)).done(function (data) {
            	if(data.success===true){
            		
            		self.subPathwayId = data.pathwayId;
            		
            		}
            	});
            
            //root.pathwayModel = self.subPathway;
            bindingContext.$root.updatePathway();
            
        };
        
        self.viewSubPathway = function(data, e) {
            var bindingContext = ko.contextFor(e.target);
            
             $.when(pathwayService.loadPathway(self.subPathwayId)).done(function (pathwayJSON) {
            		
            		var containerPathway = bindingContext.$root.pathwayModel;
                    //containerPathway.subPathwayId = self.subPathwayId;
                    //self.subPathway.parentPathwayId = containerPathway.id;
                    
            		//console.log(pathwayJSON)
                    bindingContext.$root.containerPathway = containerPathway;
                    bindingContext.$root.loadPathway(pathwayJSON.pathwaysModelInstance);
                    
            	});
            
            
            
            
        
        };
    };
  
  //json marshaller(so we don't get cyclical problems)
    
    NodeModel.prototype.toJSON = function() {
        var copy = ko.toJS(this); //easy way to get a clean copy
        delete copy.inputs; //remove an extra property
        delete copy.outputs; //remove an extra property
        return copy; //return the copy to be serialized
    };
