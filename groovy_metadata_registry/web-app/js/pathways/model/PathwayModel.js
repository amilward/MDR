define(['jquery', 'knockout'], function ($, ko) {

    //Pathway model
    var PathwayModel = function () {
        var self = this;

        self.name = undefined;
        self.description = undefined;
        self.nodes = [];
        self.links = [];

        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

    };

    //Return constructor function
    return PathwayModel;
});