
    //The main view model
    var AppViewModel = function () {
        var self = this;

        //Pathway Model
        self.pathwayModel = new PathwayModel();

        //View related properties
        self.selectedNode = undefined;

        self.availableForms = [];

        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

        //#region View related functions/logic

        self.createPathway = function () {
            var pm = new PathwayModel();
            return pm;
        };

        self.savePathway = function(model) {
            //Create the pathway (on server?)
            PathwayService.savePathway(model);

            //Set the new pathway model as the current model
            self.pathwayModel = model;

            //Add a defulat node
            self.addNode();

            //Hide the create pathway modal
            $('#CreatePathwayModal').modal('hide');
        };

        self.selectNode = function (n) {
            //Set current seletect node to bind to properties panel
            self.selectedNode = n;
        };

        self.createNode = function () {
            var node = new NodeModel();
            node.name = 'node' + (new Date().getTime());
            return node;
        };

        self.addNode = function () {
            self.pathwayModel.nodes.push(self.createNode());
        };

        //#endregion

        //Initialize form list using FormService
        $.when(loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };
