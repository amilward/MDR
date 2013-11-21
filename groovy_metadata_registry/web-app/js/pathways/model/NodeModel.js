
    var NodeModel = function () {
        var self = this;

        //TODO: better id generation
        self.id = undefined
        self.name = undefined
        self.description = undefined;
        self.type = 'node' //'node' | 'pathway'
        self.x = undefined
        self.y = undefined
        self.inputs = [];
        self.outputs = [];

        ko.track(self);
    };
