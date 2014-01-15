
/*--------------------------------------------------------
START dataElementConcept  SCRIPTS
---------------------------------------------------------*/
function selectConceptStuff(selectedDataElements, subConcepts){
	// create a deferred object
	  var r = $.Deferred();
	
	//change options to selected for DataElements, subElements and external Reference
	if(selectedDataElements.length==1){
		$('select[name="dataElements"]').find('option[value="'+selectedDataElements+'"]').attr("selected",true);
	}else if(selectedDataElements.length>1){
		$.each(selectedDataElements, function(index, item) {
			$('select[name="dataElements"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
	
	//change options to selected for DataElements, subElements and external Reference
	if(subConcepts.length==1){
		$('select[name="subConcepts"]').find('option[value="'+subConcepts+'"]').attr("selected",true);
	}else if(subConcepts.length>1){
		$.each(subConcepts, function(index, item) {
			$('select[name="subConcepts"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
	
	r.resolve();
	return r
	
}

 function conceptDualListBox(){
	 
	//set up dual list box for DataElements, subElements and external Reference
		
		$('#dataElements').bootstrapDualListbox({
		    nonselectedlistlabel: 'Available Data Elements',
		    selectedlistlabel: 'Associated Data Elements',
		    preserveselectiononmove: 'moved',
		    moveonselect: false
		});
		
	//set up dual list box for DataElements, subElements and external Reference
		
		$('#subConcepts').bootstrapDualListbox({
		    nonselectedlistlabel: 'Available Sub Concepts',
		    selectedlistlabel: 'Associated Sub Concepts',
		    preserveselectiononmove: 'moved',
		    moveonselect: false
		});
		
 }

function dataElementConceptForm(selectedDataElements, subConcepts){
	
	//set up form selecting the data elements that have been included in the collection 
	//and when this is done set up the dual list boxes (otherwise it may miss some)
	selectConceptStuff(selectedDataElements, subConcepts).done(conceptDualListBox())

}

/*--------------------------------------------------------
END EDIT dataElementConcept  SCRIPTS
---------------------------------------------------------*/


/*--------------------------------------------------------
START DATAELEMENTCONCEPT LIST  SCRIPTS
---------------------------------------------------------*/

function dataElementConceptList(){
	
	
	$('#dataElementConceptList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="dataElementConceptTable"></table>' );
	oTable = $('#dataElementConceptTable').dataTable( {
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
					return '<a id="'+ row.id + '" href="' + root +'/dataElementConcept/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"50%",
			    "sTitle":"name"
			},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {	
					if(data!=null){
						return '<a href="' + root +'/dataElementConcept/show/' + row.parent_id + '">' + data + '</a> <img class="floatright" src="../images/details_open.png" />'
					}else{
						return ''
					}
				
				},
		    	"mDataProp": "parent_name", 
		    	"sWidth":"40%",
		    	"sTitle":"parent"
			}
		],
		"fnDrawCallback": function () {

			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition

			$('#dataElementConceptTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatConceptDetails(nTr), 'details' );
				}
			} );
		}
	} );	

	oTable.fnSetFilteringDelay(1000);

}


function formatConceptDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	var subConcepts = getObjectTable(aData.subConcepts, "dataElementConcept");
	var dataElements = getObjectTable(aData.dataElements, "dataElement");
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td class="labelCol">Description: </td><td>' + description + '</td></tr>'}
	if(subConcepts!=null){sOut += '<tr><td class="labelCol">Subconcepts: </td><td>' + subConcepts + '</td></tr>'}
	if(dataElements!=null){sOut += '<tr><td class="labelCol">Data Elements: </td><td>' + dataElements + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}


/*--------------------------------------------------------
END DATAELEMENTCONCEPT LIST  SCRIPTS
---------------------------------------------------------*/

