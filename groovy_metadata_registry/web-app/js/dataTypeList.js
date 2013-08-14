var oTable;

/* Formating function for row details */
function fnFormatDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var enumerations = aData.enumerations;
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	$.each(enumerations, function( key, value ) {
	sOut += '<tr><td>' + key + '</td><td>' + value + '</td></tr>';
	});
	
	sOut += '</table>';
	 
	return sOut;
}

$(document).ready(function() {
	$('#dataTypeList').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataTypeTable"></table>' );
	oTable = $('#dataTypeTable').dataTable( {
        "bProcessing": true,
        "sAjaxSource": "listJSON",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
		"aoColumns": [
			
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				    "mRender": function ( data, type, row ) {
				    		
				    		var link = '<a href="/groovy_metadata_registry/dataType/show/' + row.id + '">' + data + '</a>'
				    		
							if(row.enumerated){
								return link + '<img class="floatright" src="../images/details_open.png" />'
							}else{
								return link
							}

				    },
				    "mDataProp": "name", 
				    "sWidth":"100%",
				    "sTitle":"name"
				}
		]
	} );	

} );


$('#dataTypeTable tbody td img').live( 'click', function () {
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