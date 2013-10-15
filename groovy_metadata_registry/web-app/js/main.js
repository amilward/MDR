$(function() {

    $('a[rel=tooltip]').tooltip();
    $().tooltip();

    // make code pretty
    window.prettyPrint && prettyPrint();

    $('.minimize-box').on('click', function(e){
        e.preventDefault();
        var $icon = $(this).children('i');
        if($icon.hasClass('icon-chevron-down')) {
            $icon.removeClass('icon-chevron-down').addClass('icon-chevron-up');
        } else if($icon.hasClass('icon-chevron-up')) {
            $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');
        }
    });
    $('.minimize-box').on('click', function(e){
        e.preventDefault();
        var $icon = $(this).children('i');
        if($icon.hasClass('icon-minus')) {
            $icon.removeClass('icon-minus').addClass('icon-plus');
        } else if($icon.hasClass('icon-plus')) {
            $icon.removeClass('icon-plus').addClass('icon-minus');
        }
    });

    $('.close-box').click(function() {
        $(this).closest('.box').hide('slow');
    });

    $('#changeSidebarPos').on('click', function(e) {
        $('body').toggleClass('hide-sidebar');
    });
});



$(function() {
	if (typeof jQuery !== 'undefined') {
		(function($) {
			$('#spinner').ajaxStart(function() {
				$(this).fadeIn();
			}).ajaxStop(function() {
				$(this).fadeOut();
			});
		})(jQuery);
	}
});



/*--------------------------------------------------------
SHARED FUNCTIONS
---------------------------------------------------------*/

var c = {};


//global func tion that gets used by all the data table lists to render data table stuff

function getObjectTable(jsonArray, classname){
	
	var table
	
	if(jsonArray.length>0){
		table = '<table>';
		
		$.each(jsonArray, function(key, value) {
			table = table + '<tr><td><a href="' + root +'/' + classname +  '/show/' + value.id +'">'+ value.name +'</a></td></tr>';
		});
		
		table = table + '</table>' 
		
		
	}else{
		
		table = null
		
	}
	
	return table;

}

function deleteItem(item){
	 $( "#dialog-confirm" ).text('Delete ' + item + '?');
	 $( "#dialog-confirm" ).dialog({
		 resizable: false,
		 height:140,
		 modal: true,
		 title: 'delete',
		 buttons: {
		 "Delete Item": function() {
			$( this ).dialog( "close" );
			$('#deleteForm').submit();
		 },
		 Cancel: function() {
			 $( this ).dialog( "close" );
		 }
		 }
	 });
}

function updateForm(){
	 
	$('#updateForm').submit();

}

function saveCreate(){
	$('#createForm').submit();
}

function formMap(){
		$('#add').button().click(function(event){
				event.preventDefault();
				$('.mapTable tbody').append('<tr><td><input type="text" name="map_key" /></td><td><input type="text" name="map_value" /></td></tr>');
			});
			
			$('#remove').button().click(function(event){
				event.preventDefault();
				if( $('.mapTable tr').size()<=2) {
				    alert('cannot delete last row');
				}
				else {
				    $('.mapTable tr:last').remove();
				}
			});
}


/*--------------------------------------------------------
END SHARED FUNCTIONS
---------------------------------------------------------*/


/*--------------------------------------------------------
 BEGIN DASHBOARD SCRIPTS
 ---------------------------------------------------------*/
function dashboard() {

	startCollectionBasket();
	
	
}
/*--------------------------------------------------------
 END DASHBOARD SCRIPTS
 ---------------------------------------------------------*/

/*--------------------------------------------------------
BEGIN COLLECTION BASKET SCRIPTS
---------------------------------------------------------*/

/* The collection basket  is like a traditional shopping cart. When a user is created a corresponding collection cart is created which belongs to that user. 
 * The collection basket is persisted across sessions and allows users to add data elements via drag and drop on the dashboard using javascript and ajax
 * (please see the collectionBasket model and controller
 *  Once they have added all the data elements needed they can view their data elements and then create a collection from them.
 * */


/* this function starts the collection basket on the dashboard
 * it makes an ajax request to the controller to get the data elements in the collections cart for the current user
 * and displays them */

function startCollectionBasket() {

	$.ajax({
		type: "GET",
		url: root + "/collectionBasket/dataElementsAsJSON",
		success: function(result){
			if(result!=null){
				$.each(result.dataElements, function(){
					$( "<li></li>" ).html('<a id="' + this.id + '" href="' + root +'/dataElement/show/' + this.id + '" >' + this.refId + ' - ' + this.name + '</a>').prependTo($( ".cart ul" )).draggable({
				        helper: "clone",
				        start: function(event, ui) {
				            c.li = this;
				            c.id = $(this).find("a").attr("id");
				            c.helper = ui.helper;
				        }
					});
				});
				updateCartCount();
			}
			
		},
		dataType: "json"
	});
	
	
	
	/* bind the droppable behaviour for the data elements in the collection basket
	* This allows you to drag data elements out of the collection basket. This in bound
	* to the whole page so that the user can drag a data element out of the collections cart 
	* anywhere on the page to remove them
	*/
	
	$("#wrap").droppable({
        drop: function(event, ui) {
        	if(c.id){
	            $(c.li).remove();
	            $(c.helper).remove();
	            removeFromCollectionBasket(c.id)
        	}
        }
	});	
	
}


/* This function is called when the user is looking at the data elements list page 
 * This binds droppable functionality to the cart (i.e. when you are on the data elements list page
 * you can drag the data elements that you want and DROP them onto the collections basket to add them to your basket)
 * */

function dataElementDragStart(){
	
	//bind the click handler to the collections cart to enable the user to drag data elements onto the cart

	$( ".cart" ).droppable({
		activeClass: "ui-state-default",
		hoverClass: "ui-state-hover",
		accept: ":not(.ui-sortable-helper)",
		greedy: true,
		drop: function( event, ui ) {
		
		// change the data element link text to include the reference id...this will be useful when we are trying to create a collection from the cart
		var link = $(c.name);
		link.text(c.refId + ' - ' + $(c.name).text());

		$( "<li></li>" ).html(link).prependTo($( ".cart ul" )).draggable({
	        helper: "clone",
	        start: function(event, ui) {
	            c.li = this;
	            c.id = $(this).find("a").attr("id");
	            c.helper = ui.helper;
	        }
		});
		addToCollectionBasket(c.id);
		}
		})
}


/* This function is called when the user drops a data element onto the cart
 * This is an ajax request to collection basket controller to add the dataElement to the basket
 * is passes the information via json and should receive a json success message
 */

function addToCollectionBasket(dataElementId){
	
	var data = {id: dataElementId};
	
	$.ajax({
		type: "POST",
		url: root + "/collectionBasket/addElement",
		data: data,
		success: function(e){
		},
		dataType: "json"
	});
	
	updateCartCount();
	
}

/* This function is called when the user drags and drops a data element out of the collection basket that they no longer want
 * This is an ajax request to collection basket controller to remove the dataElement from the basket
 * is passes the information via json and should receive a json success message
 */

function removeFromCollectionBasket(dataElementId){
	
	if($( ".cart ul li" ).size()>0){
		
		var data = {id: dataElementId};
		
		$.ajax({
			type: "POST",
			url: root + "/collectionBasket/removeElement",
			data: data,
			success: function(e){
			},
			dataType: "json"
		});
	}
	
	updateCartCount();
}

//function to display the number of data elements in the cart on the dashboard

function updateCartCount(){
	
	$("#cart a span").text($(".cart ul li" ).size()-1);
	
}


/*--------------------------------------------------------
END COLLECTION CART SCRIPTS
---------------------------------------------------------*/



/*--------------------------------------------------------
 BEGIN PROGRESS.HTML SCRIPTS
 ---------------------------------------------------------*/

function progRess() {
    /* required bootstrap-progressbar.min.js*/
    
        $('.progress .bar.text-no').progressbar();
        $('.progress .bar.text-filled').progressbar({
            display_text: 1
        });
        $('.progress .bar.text-centered').progressbar({
            display_text: 2
        });
}
/*--------------------------------------------------------
 END PROGRESS.HTML SCRIPTS
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
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
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
					c.refId = $(this).find("td").eq(0).html();  
					c.name = $(this).find("td").eq(1).html();
					c.id = $(this).find("td").eq(1).find("a").attr("id");
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
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"20%"},
			{
				    // `data` refers to the data for the cell (defined by `mData`, which
				    // defaults to the column being worked with, in this case is the first
				    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {		
					return '<a id="'+ row.id + '" href="' + root +'/dataElementConcept/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"40%",
			    "sTitle":"name"
			},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
				"mRender": function ( data, type, row ) {	
					if(data!=null){
						return '<a href="' + root +'/dataType/show/' + row.parent_id + '">' + data + '</a> <img class="floatright" src="../images/details_open.png" />'
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
        "aaSorting": [[ 1, "asc" ]],
		"aoColumns": [
			{ "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
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


/*--------------------------------------------------------
START DATATYPE EDIT/CREATE SCRIPTS
---------------------------------------------------------*/



/*--------------------------------------------------------
START DATATYPE FORM SCRIPTS
---------------------------------------------------------*/

function dataTypeForm(){
	
	  if($('#isEnumerated').is(':checked')){
		  $('#enumerations').toggle('slow', function() {
			    // Animation complete.
			  });  
	  }


		$('#isEnumerated').click(function() {
			  $('#enumerations').toggle('slow', function() {
			    // Animation complete.
			  });
			});
	
}

/*--------------------------------------------------------
END  DATATYPE FORM  SCRIPTS
---------------------------------------------------------*/




function dataTypeList(){
	
	$('#dataTypeList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="dataTypeTable"></table>' );
	oTable = $('#dataTypeTable').dataTable( {
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
				    		
				    		var link = '<a href="' + root +'/dataType/show/' + row.id + '">' + data + '</a>'
				    		
							if(row.enumerated){
								return link + '<img class="floatright" src="../images/details_open.png" />'
							}else{
								return link
							}

				    },
				    "mDataProp": "name", 
				    "sWidth":"100%",
				    "sTitle":"Data Type"
				}
		],
		"fnDrawCallback": function () {


			$('#dataTypeTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatDataTypeDetails(nTr), 'details' );
				}
				} );
			
		}
	} );	
	
	oTable.fnSetFilteringDelay(1000);
	
}

function formatDataTypeDetails ( nTr )
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


/*--------------------------------------------------------
END DATATYPE LIST  SCRIPTS
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
        "aaSorting": [[ 1, "asc" ]],
		"aoColumns": [
		    { "mDataProp": "refId", "sTitle":"Ref ID", "sWidth":"10%"},
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

/*--------------------------------------------------------
START DOCUMENT LIST  SCRIPTS
---------------------------------------------------------*/

function documentList(){
	
	$('#documentList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="documentTable"></table>' );
	oTable = $('#documentTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
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
					return '<a id="'+ row.id + '" href="' + root +'/document/show/' + row.id + '">' + data + '</a>'
			    },
			    "mDataProp": "name",
			    "sWidth":"15%",
			    "sTitle":"name"
			},
			{ "mDataProp": "description", "sTitle":"Description", "sWidth":"50%"},
			{
			    // `data` refers to the data for the cell (defined by `mData`, which
			    // defaults to the column being worked with, in this case is the first
			    // Using `row[0]` is equivalent.
			"mRender": function ( data, type, row ) {		
				return '<a id="'+ row.id + '" href="' + root +'/document/download/' + row.id + '">download ' + data + '</a>'
			    },
			    "mDataProp": "fileName",
			    "sWidth":"15%",
			    "sTitle":"content"
			},
			{ "mDataProp": "contentType", "sTitle":"File Type", "sWidth":"10%"},
		]
	} );	
	

	oTable.fnSetFilteringDelay(1000);

}


/*--------------------------------------------------------
END DOCUMENT LIST  SCRIPTS
---------------------------------------------------------*/

/*--------------------------------------------------------
START EXTERNAL SYNONYM LIST  SCRIPTS
---------------------------------------------------------*/

function externalReferenceList(){
	
	$('#externalReferenceList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="externalReferenceTable"></table>' );
	oTable = $('#externalReferenceTable').dataTable( {
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
					return '<a id="'+ row.id + '" href="' + root +'/externalReference/show/' + row.id + '">' + data + '</a>'
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
			    	
							return data + '<img class="floatright" src="../images/details_open.png" />'

			    },
			    "mDataProp": "url", 
			    "sWidth":"50%",
			    "sTitle":"Url"
			}
		],
		"fnDrawCallback": function () {
			
			//bind the click handler to the + image within the datatable to show information that is too long for data columns i.e. description/definition


			$('#externalReferenceTable tbody td img').on( 'click', function () {
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
				oTable.fnOpen( nTr, formatExtSynonymDetails(nTr), 'details' );
				}
			} );
		}
	} );	
	

	oTable.fnSetFilteringDelay(1000);



}

/* Formating function for row details - this is for the description and definition columns.....
* potentially need to add more info from the data elements class
*  */
function formatExtSynonymDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var attributes = aData.attributes;
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	$.each(attributes, function( key, value ) {
	sOut += '<tr><td>' + key + '</td><td>' + value + '</td></tr>';
	});
	
	sOut += '</table>';
	 
	return sOut;
}


/*--------------------------------------------------------
END EXTERNAL SYNONYM LIST  SCRIPTS
---------------------------------------------------------*/

/*--------------------------------------------------------
START EXTERNAL REFERENCE LIST  SCRIPTS
---------------------------------------------------------*/

function externalReferenceList(){
	
	$('#externalReferenceList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="externalReferenceTable"></table>' );
	oTable = $('#externalReferenceTable').dataTable( {
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
					return '<a id="'+ row.id + '" href="' + root +'/externalReference/show/' + row.id + '">' + data + '</a>'
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
			    	
							return data + '<img class="floatright" src="../images/details_open.png" />'

			    },
			    "mDataProp": "url", 
			    "sWidth":"50%",
			    "sTitle":"Url"
			}
		], 
		"fnInitComplete": function() {
		      $('#externalReferenceTable tbody td img').on('click', function () {
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
		  		oTable.fnOpen( nTr, formatExtSynonymDetails(nTr), 'details' );
		  		}
		  	} );
		}
	} );	
	
	oTable.fnSetFilteringDelay(1000);
	
 }

 
	/* Formating function for row details - this is for the description and definition columns.....
	* potentially need to add more info from the data elements class
	*  */
	function formatExtSynonymDetails ( nTr )
	{
		var aData = oTable.fnGetData( nTr );
		var attributes = aData.attributes;
		
		var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		
		$.each(attributes, function( key, value ) {
		sOut += '<tr><td>' + key + '</td><td>' + value + '</td></tr>';
		});
		
		sOut += '</table>';
		 
		return sOut;
	}

/*--------------------------------------------------------
END EXTERNAL REFERENCE LIST  SCRIPTS
---------------------------------------------------------*/

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
START collection  SCRIPTS
---------------------------------------------------------*/


 function selectCollectionDataElements(mandatoryDataElements, requiredDataElements, optionalDataElements, referenceDataElements){
	
	// create a deferred object
	  var r = $.Deferred();
	
	//change options to selected for DataElements, subElements and external Reference
	if(mandatoryDataElements.length==1){
		$('select[name="mandatoryDataElements"]').find('option[value="'+mandatoryDataElements+'"]').attr("selected",true);
	}else if(mandatoryDataElements.length>1){
		$.each(mandatoryDataElements, function(index, item) {
			$('select[name="mandatoryDataElements"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
	
	//change options to selected for DataElements, subElements and external Reference
	if(requiredDataElements.length==1){
		$('select[name="requiredDataElements"]').find('option[value="'+requiredDataElements+'"]').attr("selected",true);
	}else if(requiredDataElements.length>1){
		$.each(requiredDataElements, function(index, item) {
			$('select[name="requiredDataElements"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
	
	//change options to selected for DataElements, subElements and external Reference
	if(optionalDataElements.length==1){
		$('select[name="optionalDataElements"]').find('option[value="'+optionalDataElements+'"]').attr("selected",true);
	}else if(optionalDataElements.length>1){
		$.each(optionalDataElements, function(index, item) {
			$('select[name="optionalDataElements"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}

	
	//change options to selected for DataElements, subElements and external Reference
	if(referenceDataElements.length==1){
		$('select[name="referenceDataElements"]').find('option[value="'+referenceDataElements+'"]').attr("selected",true);
	}else if(referenceDataElements.length>1){
		$.each(referenceDataElements, function(index, item) {
			$('select[name="referenceDataElements"]').find('option[value="'+item+'"]').attr("selected",true);
		});
	}
	
	r.resolve();
	return r
	
}

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
START FORM LIST  SCRIPTS
---------------------------------------------------------*/

function formDesignList(){
	
	$('#formDesignList').html( '<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-condensed table-hover table-striped" id="formDesignTable"></table>' );
	oTable = $('#formDesignTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "dataTables",
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
