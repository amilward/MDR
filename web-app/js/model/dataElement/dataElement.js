
/*--------------------------------------------------------
START EDIT DATAELEMENT  SCRIPTS
---------------------------------------------------------*/

function selectDataElementStuff(selectedValueDomains, synonyms, subElements, externalReferences){
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
		
		if(synonyms.length==1){
			$('select[name="synonyms"]').find('option[value="'+synonyms+'"]').attr("selected",true);
		}else if(synonyms.length>1){
			$.each(synonyms, function(index, item) {
				$('select[name="synonyms"]').find('option[value="'+item+'"]').attr("selected",true);
			});
		}
		
		if(subElements.length==1){
			$('select[name="subElements"]').find('option[value="'+subElements+'"]').attr("selected",true);
		}else if(subElements.length>1){
			$.each(subElements, function(index, item) {
				$('select[name="subElements"]').find('option[value="'+item+'"]').attr("selected",true);
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

function dataElementDualListBox(){
	
	//set up dual list box for valueDomains, synonyms, subElements and external Reference
	
	$('#valueDomains').bootstrapDualListbox({
	    nonselectedlistlabel: 'Available Value Domains',
	    selectedlistlabel: 'Associated Value Domains',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	$('#synonyms').bootstrapDualListbox({
	    nonselectedlistlabel: 'Choose synonyms',
	    selectedlistlabel: 'synonyms',
	    preserveselectiononmove: 'moved',
	    moveonselect: false
	});
	
	$('#subElements').bootstrapDualListbox({
	    nonselectedlistlabel: 'Choose SubElements',
	    selectedlistlabel: 'SubElements',
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


function dataElementForm(selectedValueDomains, synonyms, subElements, externalReferences){
	
	//set up form selecting the data elements that have been included
	//and when this is done set up the dual list boxes (otherwise it may miss some)
	selectDataElementStuff(selectedValueDomains, synonyms, subElements, externalReferences).done(dataElementDualListBox())
	
}

/*--------------------------------------------------------
END EDIT DATAELEMENT  SCRIPTS
---------------------------------------------------------*/



/*--------------------------------------------------------
BEGIN DATAELEMENT LIST SCRIPTS
---------------------------------------------------------*/

function dataElementList(){

	//initialise datatable with custom hide/show columns
	//and server side processing
	//and draggable columns - so that the data elements within the table can be dragged onto the collection cart
	
	$('#dataElementList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="dataElementTable"></table>' );
	oTable = $('#dataElementTable').dataTable( {
	    "bProcessing": true,
	    "bServerSide": true,
	    "sAjaxSource": "dataTables",
	    "sEmptyTable": "Loading data from server",
	    "bAutoWidth": false,
	    "aaSorting": [[ 3, "asc" ]],
		"aoColumns": [
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/dataElement/show/' + row.id + '">' + data + '</a>'
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
						return '<a href="' + root +'/dataElement/show/' + row.parent_id + '">' + data + '</a>'
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
				return '<a href="' + root +'/dataElementConcept/show/' + data + '">' + row.dataElementConcept_name + '</a>' + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "dataElementConcept_id", 
		   "sWidth":"30%",
		   "sTitle":"dataElementConcept"
			},
			{ "mDataProp": "id", "bVisible":    false }
		],
		"fnDrawCallback": function () {
			
			$('#dataElementTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatDataElementDetails(nTr), 'details' );
				}
			} );

			$("#dataElementTable tr").draggable({
		        helper: "clone",
		        start: function(event, ui) {
		            c.tr = this;
					c.name = $(this).find("td").eq(0).html();
					c.id = $(this).find("td").eq(0).find("a").attr("id");
		            c.helper = ui.helper;
		        }
			});	
	     }
	} );	
	
	
	oTable.fnSetFilteringDelay(1000);
	
	//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition
	
	
	

}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatDataElementDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	var definition = aData.definition;
	var subElements = getObjectTable(aData.subElements, 'dataElement');
	var valueDomains = getObjectTable(aData.valueDomains, 'valueDomain');
	var collections = getObjectTable(aData.collections, 'collection');
	var externalReferences = getObjectTable(aData.externalReferences, 'externalReference');
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td class="labelCol">Description: </td><td>' + description + '</td></tr>'}
	if(definition!=null){sOut += '<tr><td class="labelCol">Definition: </td><td>' + definition + '</td></tr>'}
	if(subElements!=null){sOut += '<tr><td class="labelCol">Subelements: </td><td>' + subElements + '</td></tr>'}
	if(valueDomains!=null){sOut += '<tr><td class="labelCol">Value Domains: </td><td>' + valueDomains + '</td></tr>'}
	if(collections!=null){sOut += '<tr><td class="labelCol">Collections: </td><td>' + collections + '</td></tr>'}
	if(externalReferences!=null){sOut += '<tr><td class="labelCol">External Reference: </td><td>' + externalReferences + '</td></tr>'}
	
	sOut += '</table>';
	 
	return sOut;
}



/*--------------------------------------------------------
END DATAELEMENT LIST  SCRIPTS
---------------------------------------------------------*/
