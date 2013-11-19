// Usage: <div class="node" data-bind="makeNode: $data">....</div>
ko.bindingHandlers.makeNode = {
    init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
        var value = valueAccessor();

        //Turn binded element into jsPlumb source node
        jsPlumb.makeSource($('.anchor', element), {
            parent: $(element),
            connector: 'StateMachine',
            connectorStyle: { strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4 },
            connectorOverlays: [
                  ["Arrow", {
                      location: 1,
                      id: "arrow",
                      length: 14,
                      foldback: 0.8
                  }]
            ],
            endpoint: ["Dot", { radius: 1 }]
        });

        //Turn binded element into jsPlumb target node
        jsPlumb.makeTarget($(element), {
            anchor: 'Continuous',
            endpoint: ["Dot", { radius: 2 }]
        });

        //Enable dragging of nodes
        jsPlumb.draggable($(element), {
            containment: "parent"
        });

        //Listening for connection event
        jsPlumb.bind("connection", function (info) {
            var source = ko.dataFor(info.source); //Get the source node model instance
            var target = ko.dataFor(info.target); //Get the target node model instance
            
            //If source is current node, and target node is not already in the outputs array, add it to outputs
            if (value === source && !ko.utils.arrayFirst(value.outputs, function (item) { return item === target })) {
                value.outputs.push(target);
            }

            //If target is current node, and source node is not already in the inputs array, add it to inputs
            if (value === target && !ko.utils.arrayFirst(value.inputs, function (item) { return item === source })) {
                value.inputs.push(source);
            }
        });
    },
    update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
        
    }
};