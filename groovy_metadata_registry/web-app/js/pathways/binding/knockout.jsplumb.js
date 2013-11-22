// setup some defaults for jsPlumb.	
			jsPlumb.importDefaults({
				Endpoint : [ "Dot", {
					radius : 2
				} ],
				HoverPaintStyle : {
					strokeStyle : "#1e8151",
					lineWidth : 2
				},
				ConnectionOverlays : [ [ "Arrow", {
					location : 1,
					id : "arrow",
					length : 14,
					foldback : 0.8
				} ],]
			});

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

        
    },
    update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
        
    }
};



//Listening for connection event
jsPlumb.bind("connection", function (info) {
	console.log('makeConnectionBinding')
	
    var source = ko.dataFor(info.source); //Get the source node model instance            
    var target = ko.dataFor(info.target); //Get the target node model instance

    var linkId = vm.createLink(source.id, target.id);
    info.connection.setParameter("linkId", linkId)

    //If source is current node, and target node is not already in the outputs array, add it to outputs
    if (!ko.utils.arrayFirst(source.outputs, function (item) { return item === target })) {
        source.outputs.push(target);     
        
    }

    //If target is current node, and source node is not already in the inputs array, add it to inputs
    if (!ko.utils.arrayFirst(target.inputs, function (item) { return item === source })) {
        target.inputs.push(source);
    }
    
    //binding for connection double click
    info.connection.bind("dblclick", function(conn) {
    	$( "#dialog-confirm" ).text('Delete connection?');
	   	$( "#dialog-confirm" ).dialog({
	   		 resizable: false,
	   		 height:140,
	   		 modal: true,
	   		 title: 'delete connection',
	   		 buttons: {
	   		 "Delete Connection": function() {
	   			$( this ).dialog( "close" );
	   			var params = info.connection.getParameters()
	   			jsPlumb.detach(info.connection);
	   			vm.deleteLink(params.linkId);
	   		 },
	   		 Cancel: function() {
	   			 $( this ).dialog( "close" );
	   		 }
	   		 }
	   	 });
    	
    });
    
    
});
