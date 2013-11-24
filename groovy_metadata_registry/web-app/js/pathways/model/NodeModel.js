
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
        self.inputs = [];
        self.outputs = [];

        ko.track(self);
    };
    
  //json marshaller(so we don't get cyclical problems)
    
    NodeModel.prototype.toJSON = function() {
        var copy = ko.toJS(this); //easy way to get a clean copy
        delete copy.inputs; //remove an extra property
        delete copy.outputs; //remove an extra property
        return copy; //return the copy to be serialized
    };
