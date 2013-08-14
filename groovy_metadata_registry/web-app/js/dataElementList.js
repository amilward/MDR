var oTable;

/* Formating function for row details */
function fnFormatDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	var definition = aData.definition;
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td>Description: </td><td>' + description + '</td></tr>'}
	if(definition!=null){sOut += '<tr><td>Definition: </td><td>' + definition + '</td></tr>'}
	
	sOut += '</table>';
	 
	return sOut;
}

$(document).ready(function() {
	$('#dataElementList').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataElementTable"></table>' );
	oTable = $('#dataElementTable').dataTable( {
        "bProcessing": true,
        "sAjaxSource": "listJSON",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 3, "asc" ]],
		"aoColumns": [
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="/groovy_metadata_registry/dataElement/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"30%",
			    "sTitle":"name"
			},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {	
					if(data!=null){
						return '<a href="/groovy_metadata_registry/dataElement/show/' + row.parent_id + '">' + data + '</a>'
					}else{
						return ''
					}
				
		    },
		    	"mDataProp": "parent_name", 
		    	"sWidth":"30%",
		    	"sTitle":"parent"
			},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
			"mRender": function ( data, type, row ) {		
				return '<a href="/groovy_metadata_registry/dataElementConcept/show/' + data + '">' + row.dataElementConcept_name + '</a>' + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "dataElementConcept_id", 
		   "sWidth":"30%",
		   "sTitle":"dataElementConcept"
			},
			{ "mDataProp": "id", "bVisible":    false }
		],
		"fnDrawCallback": function () {
			$("#dataElementTable tr").draggable({
		        helper: "clone",
		        start: function(event, ui) {
		            c.tr = this;
					c.refId = $(this).find("td").eq(0).html();  
					c.name = $(this).find("td").eq(1).html();
					c.id = $(this).find("td").eq(1).find("a").attr("id");
		            c.helper = ui.helper;
		        }
			});	
	     }
	} );	
	
	 $( ".cart" ).droppable({
		activeClass: "ui-state-default",
		hoverClass: "ui-state-hover",
		accept: ":not(.ui-sortable-helper)",
		greedy: true,
		drop: function( event, ui ) {
		$( "<li></li>" ).html( c.refId + ' - ' + c.name).appendTo($( ".cart ol" )).draggable({
	        helper: "clone",
	        start: function(event, ui) {
	            c.li = this;
	            c.id = $(this).find("a").attr("id");
	            c.helper = ui.helper;
	        }
		});
		addToCollectionBasket(c.id);
		}
		}).sortable({
		sort: function() {
		// gets added unintentionally by droppable interacting with sortable
		// using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
		$( this ).removeClass( "ui-state-default" );
		}
		});

} );


$('#dataElementTable tbody td img').live( 'click', function () {
	var nTr = $(this).parents('tr')[0];
	if ( oTable.fnIsOpen(nTr) )
	{
	/* This row is already open - close it */
	this.src = "../images/details_open.png";
	oTable.fnClose( nTr );
	}
	else
	{
	/* Open this row */
	this.src = "../images/details_close.png";
	oTable.fnOpen( nTr, fnFormatDetails(nTr), 'details' );
	}
} );


function addToCollectionBasket(dataElementId){
	
	var data = {id: dataElementId};
	
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/groovy_metadata_registry/collectionBasket/addElement",
		data: data,
		success: function(e){
		},
		dataType: "json"
	});
	
}