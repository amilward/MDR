
    //Pathway model
    var PathwayModel = function () {
        var self = this;
        self.id = undefined;
        self.name = undefined;
    	self.description = undefined;
    	self.version = undefined;
    	self.isDraft = true;
    	self.parentPathwayId = undefined;
    	self.parentNodeId = undefined;
    	self.nodes = [];
    	self.links = [];
    	
    	
        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

    };

    //json marshaller(so we don't get cyclical problems)
    
    PathwayModel.prototype.toJSON = function() {
        var copy = ko.toJS(this); //easy way to get a clean copy
        if(copy.parentPathwayId===null){delete copy.parentPathwayId}
        return copy; //return the copy to be serialized
    };