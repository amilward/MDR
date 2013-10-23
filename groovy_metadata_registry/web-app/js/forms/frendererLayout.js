var mainLayout, modelLayout;

$(document).ready(function () {

	resizeWindows();


	mainLayout = $('#container').layout({
		closable:					true	// pane can open & close
		,	resizable:					true	// when open, pane can be resized 
		,	slidable:					true	// when closed, pane can 'slide' open over other panes - closes on mouse-out
		,	livePaneResizing:			true
		,	showDebugMessages:			true    // log and/or display messages from debugging & testing code
		,	west: {
			size: '20%',
		}
	});

	modelLayout = $('#center-panel').layout({
		closable:					true	// pane can open & close
		,	resizable:					true	// when open, pane can be resized 
		,	slidable:					true	// when closed, pane can 'slide' open over other panes - closes on mouse-out
		,	livePaneResizing:			true
		,	showDebugMessages:			true    // log and/or display messages from debugging & testing code
		,	south: {
			size: '20%',
		}
	,	west: {
		size: '20%',
	}
	});


	
	$('#changeSidebarPos').on('click', function(e) {		
		setTimeout(function(){
			mainLayout.resizeAll()
		}, 500);
		
    });

	$(window).resize(function(){
		resizeWindows();
	});

	function resizeWindows(){
		$('#container').height($('html').height() - 200);
	}



});