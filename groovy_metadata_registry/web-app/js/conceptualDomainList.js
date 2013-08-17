var oTable;

$(document).ready(function() {

	//initialise datatable with custom hide/show columns
	//and server side processing
	//and draggable columns - so that the data elements within the table can be dragged onto the collection cart
	
	$('#conceptualDomainList').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="conceptualDomainTable"></table>' );
	oTable = $('#conceptualDomainTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 1, "asc" ]],
		"aoColumns": [
		    {
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
			"mRender": function ( data, type, row ) {		
				return data + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "refId", 
		   "sWidth":"10%",
		   "sTitle":"Ref ID"
			},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="/groovy_metadata_registry/conceptualDomain/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"30%",
			    "sTitle":"name"
			},
			{ "mDataProp": "description", "sTitle":"Description", "sWidth":"60%"}
		]
	} );	
	

	oTable.fnSetFilteringDelay(1000);
	
	//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
	
	
	$('#conceptualDomainTable tbody td img').live( 'click', function () {
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


} );


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function fnFormatDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var valueDomains = getObjectTable(aData.valueDomains, "valueDomain");

	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(valueDomains!=null){sOut += '<tr><td class="labelCol">Value Domains: </td><td>' + valueDomains + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}

