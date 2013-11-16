
function getPathway(pathwayId){
	/*$.getJSON('../jsonPathways/' + pathwayId, function(data) {
		alert("Call openPathways");
		 console.log(data) 
		openPathways(data);
		 alert("openPathways completed");
	})*/
	
	var pathway_model = {
		
		nodes: [{
			id: "pwNode1",
			name : 'Assessed for Eligibility',
			shortDescription: 'At this stage, the patient is assessed for their eligibility for the trial.  This description might include how the assessment takes place, perhaps including links to the eligibility criteria.',
			x : 5,
			y : 0,
			dataElements : [{
				id : 'Data_Element_1',
				description : 'Name in Klingon'
			},{
				id : 'Data_Element_7',
				description : 'Age of best friend'
			}]
		},
		{
			id: "pwNode2",
			name : 'Randomized',
			shortDescription: 'Here the participant is randomized.  The randomization protocol could be accessed via a link here.',
			x : 15,
			y : 10,
			dataElements : [{
				id : 'Data_Element_10',
				description : 'How loud can you shout?'
			},{
				id : 'Data_Element_11',
				description : 'Peak tear flow'
			},{
				id : 'Data_Element_19',
				description : 'Can you stand on your head?'
			}]
		},
		{
			id: "pwNode3",
			name : 'Allocated to Intervention 1',
			shortDescription: 'At this point in the trial, the patient is allocated to a particular intervention.  Description of the first intervention goes here.',
			x : 25,
			y : 20,
			dataElements : [{
				id : 'Data_Element_4',
				description : 'Date of Birth'
			},{
				id : 'Data_Element_9',
				description : 'Date of first Admission'
			},{
				id : 'Data_Element_23',
				description : 'Referring Clinician'
			},{
				id : 'Data_Element_23',
				description : 'No. of visits'
			}]
		},
		{
			id: "pwNode4",
			name : 'Allocated to Intervention 2',
			shortDescription: 'At this point in the trial, the patient is allocated to a particular intervention.  Description of the second intervention goes here.',
			x : 43,
			y : 20,
			dataElements : [{
				id : 'Data_Element_15',
				description : 'Size of left foot'
			},{
				id : 'Data_Element_19',
				description : 'How high can you jump?'
			},{
				id : 'Data_Element_25',
				description : 'Largest Ear'
			}]
			
		}],
		
		links: [{
			source:{"class":"PathwaysNode","id":1},
			target:{"class":"PathwaysNode","id":2},
			label: "Randomization"
		},
		{
			source:{"class":"PathwaysNode","id":2},
			target:{"class":"PathwaysNode","id":3},
			label: "Allocated to Phase A"
		},
		{
			source:{"class":"PathwaysNode","id":2},
			target:{"class":"PathwaysNode","id":4},
			label: "Allocated to Phase B"
		}]	

	};
	
	openPathways(pathway_model);
	
}

function initPathways(){
	pwnodes = []; 
	descisionpoints = [];
	mainDiv = $('#main');
}

function openPathways(pathway_model){	
		mainDiv = $('#main');
		pwnodes = []; 
		descisionpoints = [];
		$.each(pathway_model.nodes, function(index, node) {
			  
			$newNode = $('<div/>', {
			    id: node.id,
			    class: 'pwNode',
			    rel: 'popover',
			    'data-trigger' : 'click',
			    'data-content' : node.description,
			    'data-original-title' : node.name
			});
			$newNode.text(node.name);
			$newNode.append("<div class=\"ep icon-chevron-sign-right right\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-left left\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-up up\"></div>");
			$newNode.append("<div class=\"ep icon-chevron-sign-down down\"></div>");
			mainDiv.append($newNode);

			$newNode.animate({
				left : node.x + "em",
				top : node.y + "em"
			}, 1000);
			$newNode.hover(function(){
				$(this).find('.ep').show();
			}, function(){
				$(this).find('.ep').hide();
			});

			$newNode.click(function(){
				$sidebar = $('#right-panel');
				$sidebar.html('');
				$sidebar.append($('<h3>' + node.name + '</h3>'));
				$sidebar.append($('<p>' + node.description + '</p>'));
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
			});
			pwnodes.push($newNode);
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
				} ],]
			});

			var nodes = $(".pwNode");
			// initialise draggable elements.  
			jsPlumb.draggable(nodes);

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

			jsPlumb.makeTarget(nodes, {
				dropOptions : {
					hoverClass : "dragHover"
				},
				anchor : "Continuous"
			});

			$("div").promise().done(function() {
				 $.each(pathway_model.links, function(index, link) {
				 	
				 	var src = "pwNode" + link.source.id.toString();
				 	var tgt = "pwNode" + link.target.id.toString();
 
				 	
					 jsPlumb.connect({
						source : src,
						target : tgt,
						overlays : [ [ "Label", {
							label : link.label,
							id : "label",
							cssClass : "aLabel"
						} ] ]
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
	}
		
function bindNode(n){

}	

function addProcessPoint(){

   var nname = prompt("Enter a name", "Start Process X");

   var ndescription = prompt("Please give a short description of the process", " ... process");
   var did = descisionpoints.length + 1;
   var id = "processpoint" + did.toString();

   var d  = $('<div/>', {
			    id:  id,
			    class: 'processpoint',
			    rel: 'popover',
			    'data-trigger' : 'click',
			    'data-content' : ndescription,
			    'data-original-title' : nname
			});

	d.text(nname);
	d.append("<div class=\"ep icon-chevron-sign-right right\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-left left\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-up up\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-down down\"></div>");
	mainDiv.append(d);	
	var w = screen.width - 162, h = screen.height - 162;
	var x = (0.2 * w) + Math.floor(Math.random()*(0.5 * w));
	var y = (0.2 * h) + Math.floor(Math.random()*(0.6 * h));

	 initHover(id);
	 pwnodes.push(d);
	 jsPlumb.draggable(d);
	jsPlumb.makeSource(d, {
		filter : ".ep", // only supported by jquery
		anchor : "Continuous",
		connector : [ "StateMachine", {curviness : 20} ],
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
				
	 jsPlumb.makeTarget(d, {
				dropOptions : {
					hoverClass : "dragHover"
				},
				anchor : "Continuous"
			}); 

}	

function addDecisionPoint(){
	
   var nname = prompt("Enter a name", "Start Chemo");
   var ndescription = prompt("Please give a short description of the decision", "Decide if Chemo should start - if so start the Chemo process");
   var did = descisionpoints.length + 1;
   var id = "decisionpoint" + did.toString();
   var d  = $('<div/>', {
			    id:  id,
			    class: 'decisionpoint',
			    rel: 'popover',
			    'data-trigger' : 'click',
			    'data-content' : ndescription,
			    'data-original-title' : nname
			});

	d.text(nname);
	d.append("<div class=\"ep icon-chevron-sign-right right\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-left left\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-up up\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-down down\"></div>");
	mainDiv.append(d);	
	var w = screen.width - 162, h = screen.height - 162;
	var x = (0.2 * w) + Math.floor(Math.random()*(0.5 * w));
	var y = (0.2 * h) + Math.floor(Math.random()*(0.6 * h));

	 initHover(id);
	 pwnodes.push(d);

	 jsPlumb.draggable(d);
	 jsPlumb.makeSource(d, {
		filter : ".ep", // only supported by jquery
		anchor : "Continuous",
		connector : [ "StateMachine", {curviness : 20} ],
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
	
 
	
    //alert("Alert 5"   );				
	 jsPlumb.makeTarget(d, {
				dropOptions : {
					hoverClass : "dragHover"
				},
				anchor : "Continuous"
			}); 
			
	
}	

function addNode(){

   var nname = prompt("Enter a name", "Surgery");
   var ndescription = prompt("Please give a short description of the process", "Take an implement and start cutting");
   var nid = pwnodes.length + 1;
   var id = "pwNode" + nid.toString();
   var d  = $('<div/>', {
			    id:  id,
			    class: 'pwNode',
			    rel: 'popover',
			    'data-trigger' : 'click',
			    'data-content' : ndescription,
			    'data-original-title' : nname
			});
			
	d.text(nname);
	d.append("<div class=\"ep icon-chevron-sign-right right\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-left left\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-up up\"></div>");
	d.append("<div class=\"ep icon-chevron-sign-down down\"></div>");
	mainDiv.append(d);		
	//var id = 
	//alert ("Add Node 2 #" + id);
	var w = screen.width - 162, h = screen.height - 162;
	var x = (0.2 * w) + Math.floor(Math.random()*(0.5 * w));
	var y = (0.2 * h) + Math.floor(Math.random()*(0.6 * h));

	//Prepare
	 initHover(id);
	 pwnodes.push(d);

	 jsPlumb.draggable(d);
	jsPlumb.makeSource(d, {
		filter : ".ep", // only supported by jquery
		anchor : "Continuous",
		connector : [ "StateMachine", {curviness : 20} ],
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
	
			
	 jsPlumb.makeTarget(d, {
				dropOptions : {
					hoverClass : "dragHover"
				},
				anchor : "Continuous"
			}); 
			
	d.click(function(){
		
		      // alert("ChangeSidebar1");
				$sidebar = $('#rhsidebar');
				$sidebar.html('');
				$sidebar.append($('<h3>new node</h3>'));
				$sidebar.append($('<p>new node description not available</p>'));
				$sidebar.append($('<h4>Data Elements</h4>'));
				$sidebar.append($('<p>Below is a list of data elements collected at this stage in the pathway.</p>'));
				$table = $('<table/>', {
					class: 'table table-striped table-bordered'
				});				 
				$sidebar.append($table);
			});	

}


 
function initHover(elId){
 
	 $("#" + elId).hover(function(){
				$(this).find('.ep').show();
			}, function(){
				$(this).find('.ep').hide();
			});
}
	
function initAnimation(elId) {
	$("#" + elId).bind('click', function(e, ui) {
				if ($(this).hasClass("jsPlumb_dragged")) {
					$(this).removeClass("jsPlumb_dragged");
					return;
				}
				var o = $(this).offset(),
				w = $(this).outerWidth(),
				h = $(this).outerHeight(),
				c = [o.left + (w/2) - e.pageX, o.top + (h/2) - e.pageY],
				oo = [c[0] / w, c[1] / h],
				l = oo[0] < 0 ? '+=' : '-=', t = oo[1] < 0 ? "+=" : '-=',
				DIST = 450,
				l = l + Math.abs(oo[0] * DIST);
	
				t = t + Math.abs(oo[1] * DIST);
				// notice the easing here.  you can pass any args into this animate call; they
				// are passed through to jquery as-is by jsPlumb.
				var id = $(this).attr("id");
				jsPlumb.animate(id, {left:l, top:t}, { duration:1400, easing:'easeOutBack' });
	});
}
