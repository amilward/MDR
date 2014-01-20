/*--------------------------------------------------------
START COLLECTION LIST  SCRIPTS
---------------------------------------------------------*/

function relationshipTypeList(){
	
	$('#relationshipTypeList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="relationshipTypeTable"></table>' );
	oTable = $('#relationshipTypeTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
		"aoColumns": [
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {	
					if(row.relationshipTypeType=='relationshipType'){
						return '<a id="'+ row.id + '" href="' + root +'/relationshipType/show/' + row.id + '">' + data + '</a>'
					}else{
						return '<a id="'+ row.id + '" href="' + root +'/relationshipType/show/' + row.id + '">' + data + '</a>'
					}
			    },
			    "mDataProp": "name",
			    "sWidth":"20%",
			    "sTitle":"name"
			}
		],
		"fnDrawCallback": function () {
			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
			
			
			$('#relationshipTypeTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatModelDetails(nTr), 'details' );
				}
			} );
			
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);
	
}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatModelDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var dataElements = getObjectTable(aData.dataElements, "dataElement");
	var formSpecifications = getObjectTable(aData.formSpecifications, "formSpecifications");
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(dataElements!=null){sOut += '<tr><td class="labelCol">Data Elements: </td><td>' + dataElements + '</td></tr>'}
	if(formSpecifications!=null){sOut += '<tr><td class="labelCol">External Reference: </td><td>' + formSpecifications + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}




