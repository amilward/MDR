/*
function getFormDesignJSON(){
	// Load the form object from path/to/form.json
	$.getJSON('../jsonFormDesign/' + $("#formDesignId").val(), function(data) {
        $("#previewForm").dform(data);
		});
}*/

/*--------------------------------------------------------
START FORM LIST  SCRIPTS
---------------------------------------------------------*/

function formDesignList(){
	
	$('#formDesignList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="formDesignTable"></table>' );
	oTable = $('#formDesignTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "/groovy_metadata_registry/formDesign/dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 1, "asc" ]],
		"aoColumns": [
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/formDesign/show/' + row.id + '">' + data + '</a>'
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
			
			
			$('#formDesignTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatFormDesignDetails(nTr), 'details' );
				}
			} );
			
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);
	
}

function formDesignListDraggable(){
	
	$('#formDesignList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="formDesignTable"></table>' );
	oTable = $('#formDesignTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "/groovy_metadata_registry/formDesign/dataTables",
        "sEmptyTable": "Loading data from server",
        "bAutoWidth": false,
        "aaSorting": [[ 1, "asc" ]],
		"aoColumns": [
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/formDesign/show/' + row.id + '">' + data + '</a>'
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
			
			
			$('#formDesignTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatFormDesignDetails(nTr), 'details' );
				}
			} );
			
			$("#formDesignTable tr").draggable({
		        helper: "clone",
		        start: function(event, ui) {
		            c.tr = this;
					c.refId = $(this).find("td").eq(0).html();  
					c.name = $(this).find("td").eq(1).find("a").text();
					c.id = $(this).find("td").eq(1).find("a").attr("id");
		            c.helper = ui.helper;
		        }
			});	
			
		}
	} );	
	
	

	oTable.fnSetFilteringDelay(1000);
	
}



/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function formatFormDesignDetails ( nTr )
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
END FORM LIST  SCRIPTS
---------------------------------------------------------*/