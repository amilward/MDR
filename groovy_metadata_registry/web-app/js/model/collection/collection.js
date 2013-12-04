
 function collectionDualListBox(){
	//set up dual list box for DataElements, subElements and external Reference
	
	
	$('#mandatoryDataElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Data Elements',
	    selectedlistlabel: 'Mandatory Data Elements',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	//set up dual list box for DataElements, subElements and external Reference
	
	$('#requiredDataElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Data Elements',
	    selectedlistlabel: 'Required Data Elements',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	//set up dual list box for DataElements, subElements and external Reference
	
	$('#optionalDataElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Data Elements',
	    selectedlistlabel: 'Optional Data Elements',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	//set up dual list box for DataElements, subElements and external Reference
	
	$('#referenceDataElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Data Elements',
	    selectedlistlabel: 'Reference Data Elements',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
}


function collectionForm(mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements){
	
	//set up form selecting the data elements that have been included in the collection 
	//and when this is done set up the dual list boxes (otherwise it may miss some)
	selectCollectionDataElements(mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements).done(collectionDualListBox())

}

/*--------------------------------------------------------
END EDIT collection  SCRIPTS
---------------------------------------------------------*/


/*--------------------------------------------------------
START COLLECTION LIST  SCRIPTS
---------------------------------------------------------*/

function collectionList(){
	
	$('#collectionList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="collectionTable"></table>' );
	oTable = $('#collectionTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 0, "asc" ]],
		"aoColumns": [
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/collection/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"20%",
			    "sTitle":"name"
			},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
			    "mRender": function ( data, type, row ) {
			    	
							return data + '<img class="floatright" src="../images/details_open.png" />'

			    },
			    "mDataProp": "description", 
			    "sWidth":"70%",
			    "sTitle":"Description"
			}
		],
		"fnDrawCallback": function () {
			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
			
			
			$('#collectionTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatCollectionDetails(nTr), 'details' );
				}
			} );
			
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);
	
}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatCollectionDetails ( nTr )
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



/*--------------------------------------------------------
END COLLECTION LIST  SCRIPTS
---------------------------------------------------------*/

