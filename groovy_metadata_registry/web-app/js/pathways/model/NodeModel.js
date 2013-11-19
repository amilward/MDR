
    var NodeModel = function () {
        var self = this;

        //TODO: better id generation
        self.id = 'node-' + (new Date().getTime());
        self.name = self.id;
        self.description = undefined;
        self.type = 'node' //'node' | 'pathway'
        self.inputs = [];
        self.outputs = [];

        ko.track(self);
    };
