
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
        	
        	$.when(savePathway(model)).done(function (data) {
        		//create pathwayModal
        		self.pathwayModel = model;
        		 //Set the new pathway model id given the id created on the server
                self.pathwayModel.id = data.pathwayId;

                //Add a default node
                self.saveNodeToServer();

                //Hide the create pathway modal
                $('#CreatePathwayModal').modal('hide');
        	});
        	
        };
        

        self.selectNode = function (n) {
            //Set current seletect node to bind to properties panel
            self.selectedNode = n;
        };

        self.saveNodeToServer = function () {
        	//create the node in the model
        	var node = new NodeModel();
            node.name = 'node' + (new Date().getTime());
            node.x = 0
            node.y = 0
            //create a json representation of the node to send to the server using the pathways service
            var jsonNodeToServer = createJsonNode(node, self.pathwayModel.id)
            //after the node has been created on the server using the pathways service methods
            //pass the version number and the id from the server to the node and 
            //add it to the pathways model
            $.when(createNode(jsonNodeToServer)).done(function (data) {
            	if(data.success===true){
	                node.id = data.nodeId
	                node.version = data.nodeVersion
	                self.pathwayModel.nodes.push(node);
	                console.log(ko.toJSON(self.pathwayModel.nodes));
            	}else{
            		alert('node creation failed')
            	}
            });
        };
        
       
        
        self.createLink = function(sourceId, targetId){
        	//FIXME need to amalgamate both methods into a jquery wait function
        	if(targetId!=null && sourceId!=null){
	        	var link = new LinkModel();
	        	link.name = 'link' + (new Date().getTime());
	        	link.source = 'node' + sourceId
	        	link.target = 'node' + targetId
	        	var jsonLink = createJsonLink(link, self.pathwayModel.id)
	        	$.when(createLink(jsonLink)).done(function (data) {
	        		console.log(data)
	        		link.id = data.linkId
	        		link.version = data.linkVersion
	        		self.pathwayModel.links.push(link);
	        		
	        		console.log(ko.toJSON(self.pathwayModel.links))
	        	});
	        	
	        	
	        }
        	
        }

        //#endregion

        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
