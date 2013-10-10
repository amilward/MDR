
<%@ page import="uk.co.mdc.pathways.PathwaysModel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pathwaysModel.label', default: 'PathwaysModel')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'pathway.css')}" type="text/css">
		
	</head>
	<body>
		<div class="navbar navbar-fixed-top navbar-inverse" id="header">
			<div style="width: auto;" class="container">
				<a href="." class="navbar-brand">NHIC Model Catalogue</a>
	
				<p class="navbar-text pull-right">Not logged in</p>
			</div>
		</div>

	<h4>Pathway Viewer</h4>

	<div id="main" class="graph-paper">
		<div id="render"></div>
		<!-- 			<div class="node" id="opened">BEGIN<div class="ep"></div></div>
			<div class="node" id="phone1">PHONE INTERVIEW 1<div class="ep"></div></div>
			<div class="node" id="phone2">PHONE INTERVIEW 2<div class="ep"></div></div>
			<div class="node" id="inperson">IN PERSON<div class="ep"></div></div>
			<div class="node" id="rejected">REJECTED<div class="ep"></div></div>
			 -->
	</div>
	<div id="sidebar">
		<div id="explanation">
			<h4>STATE MACHINE</h4>
			<p>Nodes are connected with the StateMachine connector.</p>
			<p>Endpoints are located with 'Continuous' anchors, which are
				anchors whose location is calculated based on the location of all
				other connected elements, and which guarantee a unique endpoint per
				connection.</p>
			<p>Click and drag new Connections from the orange div in each
				element; the main elements in the UI are configured to be Connection
				targets. You can drag from one of these divs onto its parent element
				to create a 'loopback' connection. Each element supports up to 5
				Connections.</p>
			<p>Click on a Connection to delete it.</p>

			<p>This demonstration uses jsPlumb 1.5.2, jQuery 1.9.0 and jQuery
				UI 1.9.2. For touch support, jQuery Touch Punch is used.</p>
		</div>
	</div>
	
	<g:javascript disposition="defer" library="pathways"/>	
	
	</body>
</html>
