define(['jquery', 'knockout', 'model/PathwayModel', 'service/PathwayService', 'service/FormService'], function ($, ko, PathwayModel, PathwayService, FormService) {

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

        //View related functions/logic
        self.createPathway = function () {
            //Create the pathway (on server?)
            PathwayService.savePathway(self.pathwayModel);

            //Content for Properties panel
            self.selectedNode = self.pathwayModel;

            //Hide the create pathway modal
            $('#CreatePathwayModal').modal('hide');

        };

        //Initialize form list using FormService
        $.when(FormService.loadFormList()).done(function (data) {
            //"data" is assumed to be pure JSON array containing items with no observable applied.
            self.availableForms = data;
        });

    };

    //Return constructor function
    return AppViewModel;
});