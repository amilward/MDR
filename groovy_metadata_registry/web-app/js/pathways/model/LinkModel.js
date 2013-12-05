//Link model
    var LinkModel = function () {
        var self = this;
        self.id = undefined;
        self.version = undefined;
        self.name = undefined;
        self.description = undefined;
    	self.source = undefined;
    	self.target = undefined;
    	self.connectionId = undefined;
        self.collections = [];
        
        //Turn all self.XXX properties above this statement to observable{Array}
        ko.track(self);

        //We can now use the observable without ()

    };
    
  //json marshaller(so we don't get cyclical problems)
    
    
    LinkModel.prototype.toJSON = function() {
        var copy = ko.toJS(this); //easy way to get a clean copy
        delete copy.connectionId; //remove an extra property
        copy.source = this.source.id
        copy.target = this.target.id
        return copy; //return the copy to be serialized
    };
