//Link model
    var LinkModel = function () {
        var self = this;
        self.id = undefined;
        self.version = undefined
        self.name = undefined;
    	self.source = undefined;
    	self.target = undefined;
    	
        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

    };