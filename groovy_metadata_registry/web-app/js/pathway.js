		mainDiv = $('#main');
		$.each(pathway_model.nodes, function(index, node) {

			$newNode = $('<div/>', {
			    id: node.id,
			    class: 'node',
			    rel: 'popover',
			    'data-trigger' : 'click',
			    'data-content' : node.shortDescription,
			    'data-original-title' : node.name
			});
			/*$newNode.append("<p>" + node.name + "</p>") */
			$newNode.text(node.name);
			$newNode.append("<div class=\"ep icon-chevron-sign-right right\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-left left\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-up up\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-down down\"></div>");
			mainDiv.append($newNode)
			$newNode.animate({
				left : node.x + "em",
				top : node.y + "em"
			}, 1000);
			
			//bindNode($newNode);
			$newNode.hover(function(){
				$(this).find('.ep').show();
			}, function(){
				$(this).find('.ep').hide();
			});
			
			//$newNode.popover();
			$newNode.click(function(){
				$sidebar = $('#sidebar');
				$sidebar.html('');
				$sidebar.append($('<h3>' + node.name + '</h3>'));
				$sidebar.append($('<p>' + node.shortDescription + '</p>'));
				$sidebar.append($('<h4>Data Elements</h4>'));
				$sidebar.append($('<p>Below is a list of data elements collected at this stage in the pathway.</p>'));
				$table = $('<table/>', {
					class: 'table table-striped table-bordered'
				});
				$.each(node.dataElements, function(index, de){
					$tr = $('<tr/>');
					$tr.append("<td>" + de.id + "</td>");
					$tr.append("<td>" + de.description + "</td>");
					$table.append($tr);
				});
				$sidebar.append($table);
			});	
			
			$newNode.contextmenu(function(e){
				e.preventDefault();
				alert("Something!");
			});

		});

		jsPlumb.ready(function() {

			// don't refresh each time we add an element
			jsPlumb.setSuspendDrawing(true);
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
				} ],/* [ "Label", {
					label : "FOO",
					id : "label",
					cssClass : "aLabel"
				} ] */]
			});

			var nodes = $(".node");

			// initialise draggable elements.  
			jsPlumb.draggable(nodes);

			// bind a click listener to each connection; the connection is deleted. you could of course
			// just do this: jsPlumb.bind("click", jsPlumb.detach), but I wanted to make it clear what was
			// happening.
			/*jsPlumb.bind("click", function(c) {
				jsPlumb.detach(c);
			}); */

			// make each ".ep" div a source and give it some parameters to work with.  here we tell it
			// to use a Continuous anchor and the StateMachine connectors, and also we give it the
			// connector's paint style.  note that in this demo the strokeStyle is dynamically generated,
			// which prevents us from just setting a jsPlumb.Defaults.PaintStyle.  but that is what i
			// would recommend you do. Note also here that we use the 'filter' option to tell jsPlumb
			// which parts of the element should actually respond to a drag start.
			jsPlumb.makeSource(nodes, {
				filter : ".ep", // only supported by jquery
				anchor : "Continuous",
				connector : [ "StateMachine", {
					curviness : 20
				} ],
				connectorStyle : {
					strokeStyle : "#5c96bc",
					lineWidth : 2,
					outlineColor : "transparent",
					outlineWidth : 4
				},
				maxConnections : 5,
				onMaxConnections : function(info, e) {
					alert("Maximum connections (" + info.maxConnections
							+ ") reached");
				}
			});

			// bind a connection listener. note that the parameter passed to this function contains more than
			// just the new connection - see the documentation for a full list of what is included in 'info'.
			// this listener sets the connection's internal
			// id as the label overlay's text.
			/*jsPlumb.bind("connection", function(info) {
				info.connection.getOverlay("label")
						.setLabel(info.connection.id);
			});*/

			// initialise all '.w' elements as connection targets.
			jsPlumb.makeTarget(nodes, {
				dropOptions : {
					hoverClass : "dragHover"
				},
				anchor : "Continuous"
			});

			// and finally, make a couple of connections

			$("div").promise().done(function() {
				$.each(pathway_model.links, function(index, link) {
					jsPlumb.connect({
						source : link.source,
						target : link.target,
						overlays : [ [ "Label", {
							label : link.label,
							id : "label",
							cssClass : "aLabel"
						} ] ],
					});
					
					
				});

				// allow refresh again
				jsPlumb.setSuspendDrawing(false, true);
				
				// Now we add the default label again
				jsPlumb.importDefaults({
					ConnectionOverlays : [ [ "Arrow", {
						location : 1,
						id : "arrow",
						length : 14,
						foldback : 0.8
					} ], [ "Label", {
						label : "New connection",
						id : "label",
						cssClass : "aLabel"
					} ]  ]
				});
				$.fn.editable.defaults.mode = 'inline';
				jsPlumb.bind("connection", function(info) {
					   var label = $.grep(info.connection.getOverlays(), function(overlay){
						   return overlay.type == "Label";
					   })[0].getElement();
					   $(label).html("<span>New Connection</span>");
					   console.log($(label).find("span"));
					   
					   $(label).find("span").editable({
						   	type: 'text',
							showbuttons : false
					   }).editable('toggle'); 
					   
					});
			});
		});
		
function bindNode(n){


}		
		
