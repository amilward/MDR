/*--------------------------------------------------------
START VALUEDOMAIN LIST  SCRIPTS
---------------------------------------------------------*/

function valueDomainList(){
	
	$('#valueDomainList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="documentTable"></table>' );
	oTable = $('#documentTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 4, "asc" ]],
		"aoColumns": [
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/valueDomain/show/' + row.id + '">' + data + '</a>'
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
						return '<a href="' + root +'/dataType/show/' + row.dataType_id + '">' + data + '</a>'
					}else{
						return ''
					}
				
				},
		    	"mDataProp": "dataType_name", 
		    	"sWidth":"25%",
		    	"sTitle":"dataType"
			},
			{ "mDataProp": "format", "sTitle":"Format", "sWidth":"10%"},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
			"mRender": function ( data, type, row ) {		
				return '<a href="' + root +'/conceptualDomain/show/' + row.conceptualDomain_id + '">' + data + '</a>' + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "conceptualDomain_name", 
		   "sWidth":"25%",
		   "sTitle":"Conceptual Domain"
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
				oTable.fnOpen( nTr, formatValueDomainDetails(nTr), 'details' );
				}
			} );
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);

}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatValueDomainDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	var unitOfMeasure = aData.unitOfMeasure;
	var regexDef = aData.regexDef;
	var dataElements = getObjectTable(aData.dataElements, "dataElement");
	var externalReferences = getObjectTable(aData.externalReferences, "externalReference");
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td class="labelCol">Description: </td><td>' + description + '</td></tr>'}
	if(unitOfMeasure!=null){sOut += '<tr><td class="labelCol">Unit Of Measure: </td><td>' + unitOfMeasure + '</td></tr>'}
	if(regexDef!=null){sOut += '<tr><td class="labelCol">Regex: </td><td>' + subElements + '</td></tr>'}
	if(dataElements!=null){sOut += '<tr><td class="labelCol">Data Elements: </td><td>' + dataElements + '</td></tr>'}
	if(externalReferences!=null){sOut += '<tr><td class="labelCol">External Reference: </td><td>' + externalReferences + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}



/*--------------------------------------------------------
END VALUEDOMAIN LIST  SCRIPTS
---------------------------------------------------------*/






/*--------------------------------------------------------
START EDIT VALUE DOMAIN SCRIPTS
---------------------------------------------------------*/

function selectValueDomainStuff(selectedDataElements, externalReferences){
	// create a deferred object
	  var r = $.Deferred();


		//change options to selected for valueDomains, subElements and external Reference
		if(selectedDataElements.length==1){
			$('select[name="dataElements"]').find('option[value="'+selectedDataElements+'"]').attr("selected",true);
		}else if(selectedDataElements.length>1){
			$.each(selectedDataElements, function(index, item) {
				$('select[name="dataElements"]').find('option[value="'+item+'"]').attr("selected",true);
			});
		}
		
		if(externalReferences.length==1){
			$('select[name="externalReferences"]').find('option[value="'+externalReferences+'"]').attr("selected",true);
		}else if(externalReferences.length>1){
			$.each(externalReferences, function(index, item) {
				$('select[name="externalReferences"]').find('option[value="'+item+'"]').attr("selected",true);
			});
		}	  
	  
		r.resolve();
		return r

	}

function valueDomainDualListBox(){
	//set up dual list box for valueDomains, subElements and external Reference
	
	$('#dataElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Data Elements',
	    selectedlistlabel: 'Associated Data Elements',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	
	$('#externalReferences').bootstrapDualListbox({
	    nonselectedlistlabel: 'Choose External Reference',
	    selectedlistlabel: 'External Reference',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
}
	


function valueDomainForm(selectedDataElements, externalReferences){
	
	//set up form selecting the data elements that have been included
	//and when this is done set up the dual list boxes (otherwise it may miss some)
	selectValueDomainStuff(selectedDataElements, externalReferences).done(valueDomainDualListBox())
}

/*--------------------------------------------------------
END EDIT VALUE DOMAIN SCRIPTS
---------------------------------------------------------*/