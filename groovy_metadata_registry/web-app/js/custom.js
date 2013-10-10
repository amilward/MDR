var mainLayout, modelLayout;

/*var formElementPattern = 
	'<div class="large-rounded form-item"><table class="small-rounded"><tr><td class="content"></td><td class="menu"><div class="menu-content"><ul><li>Edit</li><li>Properties</li><li>Copy</li><li>Delete</li></ul></div></td></tr></table></div>';
*/



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
	,	east: {
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
		$('#container').height($('html').height() - 105);
	}



});

function initializePalette(){
	$( "#accordion" ).accordion({
		collapsible: true,
		autoHeight: true,
		beforeActivate: function( event, ui ) {
			ui.oldHeader.blur();
			ui.newHeader.blur();
		}, 
		
	});
}


	function refreshFormPanelViews(){
		
		$( ".sortable" ).disableSelection();

		
		$('.sortable .menu-content').hide();

		$('.sortable input').prop('disabled', true);

		$('.form .form-item').mouseover(function(){
			$(this).addClass('hover');
			$(this).find('.menu-content').show();
		});

		$('.form .form-item').mouseout(function(){
			$(this).removeClass('hover');
			$(this).find('.menu-content').hide();
		});	


		$('.menu-content li').mouseover(function(){
			$(this).addClass('linkhover');
		});

		$('.menu-content li').mouseout(function(){
			$(this).removeClass('linkhover');
		});	
	}
	refreshFormPanelViews();





/*
function newForm()
{
	var formID =  $('#newFormDialogFormID').val();
	var formFullName = $('#newFormDialogFormFullName').val();
	var formShortName = $('#newFormDialogFormShortName').val();
	$('#newFormDialog').modal('hide');
	viewModel.addForm(formID,formFullName,formShortName,[]);
}
*/

// ---------------------------------------------------------- Generic Confirm  

function confirm(heading, question, cancelButtonTxt, okButtonTxt, callback) {

  var confirmModal = 
    $('<div class="modal hide fade">' +    
        '<div class="modal-header">' +
          '<a class="close" data-dismiss="modal" >&times;</a>' +
          '<h3>' + heading +'</h3>' +
        '</div>' +

        '<div class="modal-body">' +
          '<p>' + question + '</p>' +
        '</div>' +

        '<div class="modal-footer">' +
          '<a href="#" class="btn" data-dismiss="modal">' + 
            cancelButtonTxt + 
          '</a>' +
          '<a href="#" id="okButton" class="btn btn-primary">' + 
            okButtonTxt + 
          '</a>' +
        '</div>' +
      '</div>');

  confirmModal.find('#okButton').click(function(event) {
    callback();
    confirmModal.modal('hide');
  });

  confirmModal.modal('show');     
};

function getQuestionNumber(idx, data){
	var num = 1; 
	for(var i=0;i<idx && i < data.length; i++)
	{
		if(data[i].question() != null && data[i].question() != null)
		{
			num++;
		}
	}
	return num;
		
}