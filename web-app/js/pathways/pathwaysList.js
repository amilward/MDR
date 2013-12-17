/*--------------------------------------------------------
START PATHWAY LIST  SCRIPTS
---------------------------------------------------------*/

function pathwaysList(){
	
	$('#pathwaysList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="documentTable"></table>' );
	oTable = $('#documentTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 0, "asc" ]],
		"aoColumns": [
			{ "mDataProp": "versionNo", "sTitle":"versionNo", "sWidth":"10%"},
			{ "mDataProp": "isDraft", "sTitle":"isDraft", "sWidth":"10%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/pathwaysModel/show/' + row.id + '">' + data + '</a>' + '<img class="floatright" src="../images/details_open.png" />'
			    },
			    "mDataProp": "name",
			    "sWidth":"30%",
			    "sTitle":"name"
			}
		],
		"fnDrawCallback": function () {
			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
			
			
			$('#documentTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatPathwaysDetails(nTr), 'details' );
				}
			} );
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);

}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatPathwaysDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td class="labelCol">Description: </td><td>' + description + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}



/*--------------------------------------------------------
END VALUEDOMAIN LIST  SCRIPTS
---------------------------------------------------------*/