

/*--------------------------------------------------------
START CONCEPTUALDOMAIN  SCRIPTS
---------------------------------------------------------*/


function selectConceptualDomainStuff(selectedValueDomains){
	// create a deferred object
	  var r = $.Deferred();

	//change options to selected for valueDomains, subElements and external Reference
	if(selectedValueDomains.length==1){
			$('select[name="valueDomains"]').find('option[value="'+selectedValueDomains+'"]').attr("selected",true);
	}else if(selectedValueDomains.length>1){
		$.each(selectedValueDomains, function(index, item) {
				$('select[name="valueDomains"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
		
	r.resolve();
	return r

}

function conceptualDomainDualListBox(){
	
	//set up dual list box for valueDomains, subElements and external Reference
	
	$('#valueDomains').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Value Domains',
	    selectedlistlabel: 'Associated Value Domains',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
}

function conceptualDomainForm(selectedValueDomains){
	
	//set up form selecting the data elements that have been included 
	//and when this is done set up the dual list boxes (otherwise it may miss some)
	selectConceptualDomainStuff(selectedValueDomains).done(conceptualDomainDualListBox())

}

/*--------------------------------------------------------
END EDIT CONCEPTUALDOMAIN  SCRIPTS
---------------------------------------------------------*/
/*--------------------------------------------------------
START CONCEPTUALDOMAIN LIST  SCRIPTS
---------------------------------------------------------*/

function conceptualDomainList(){
	
	//initialise datatable with custom hide/show columns
	//and server side processing
	//and draggable columns - so that the data elements within the table can be dragged onto the collection cart
	
	$('#conceptualDomainList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="conceptualDomainTable"></table>' );
	oTable = $('#conceptualDomainTable').dataTable( {
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
					return '<a id="'+ row.id + '" href="' + root +'/conceptualDomain/show/' + row.id + '">' + data + '</a>'
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
				return data + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "description", 
		   "sWidth":"60%",
		   "sTitle":"Description"
			}
		],
		"fnDrawCallback": function () {
			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
			
			
			$('#conceptualDomainTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatConceptualDomainDetails(nTr), 'details' );
				}
			} );
			
		}
	} );	
	
	oTable.fnSetFilteringDelay(1000);
	
}

function formatConceptualDomainDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var valueDomains = getObjectTable(aData.valueDomains, "valueDomain");

	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(valueDomains!=null){sOut += '<tr><td class="labelCol">Value Domains: </td><td>' + valueDomains + '</td></tr>'}

	sOut += '</table>';
	 
	return sOut;
}


/*--------------------------------------------------------
END CONCEPTUALDOMAIN LIST  SCRIPTS
---------------------------------------------------------*/