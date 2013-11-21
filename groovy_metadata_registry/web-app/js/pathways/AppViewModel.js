
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
        	console.log('creating a new pathway')
        	console.log(pathway)
            var pm = new PathwayModel(pathway);
            return pm;
        };

        self.savePathwayToServer = function(model) {
        	console.log('saving a pathway')
        	console.log(model)
        	console.log(ko.toJSON(model))
            //Create the pathway (on server?)
            savePathway(model);
        	self.pathwayModel = model;
        	
        };
        
        self.updatePathwayFromServer = function(pathwayId){
        	//console.log(pathway)

            //Set the new pathway model as the current model
            self.pathwayModel.id = pathwayId;
            
            //console.log(ko.toJSON(self.pathwayModel))

            //Add a default node
            self.saveNodeToServer();

            //Hide the create pathway modal
            $('#CreatePathwayModal').modal('hide');

        }

        self.selectNode = function (n) {
            //Set current seletect node to bind to properties panel
            self.selectedNode = n;
        };

        self.saveNodeToServer = function () {
        	var node = new NodeModel();
            node.name = 'node' + (new Date().getTime());
            node.x = 0
            node.y = 0
            createNode(node, self.pathwayModel.id)
        };

        self.updateNodeFromServer = function (nodeId) {
        	console.log(nodeId)
            var node = new NodeModel();
            node.name = 'node' + (new Date().getTime());
            node.id = nodeId
            self.pathwayModel.nodes.push(node);
            console.log(ko.toJSON(self.pathwayModel.nodes));
        };

        //#endregion

        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
