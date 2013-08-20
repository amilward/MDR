$(function() {

    $('a[rel=tooltip]').tooltip();

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


//global function that gets used by all the data table lists to render data table stuff

function getObjectTable(jsonArray, classname){
	
	var table
	
	if(jsonArray.length>0){
		table = '<table>';
		
		$.each(jsonArray, function(key, value) {
			table = table + '<tr><td><a href="/groovy_metadata_registry/' + classname +  '/show/' + value.id +'">'+ value.name +'</a></td></tr>';
		});
		
		table = table + '</table>' 
		
		
	}else{
		
		table = null
		
	}
	
	return table;

}

/*--------------------------------------------------------
END SHARED FUNCTIONS
---------------------------------------------------------*/


/*--------------------------------------------------------
 BEGIN DASHBOARD SCRIPTS
 ---------------------------------------------------------*/
function dashboard() {

	startCollectionCart();
	
	
}
/*--------------------------------------------------------
 END DASHBOARD SCRIPTS
 ---------------------------------------------------------*/

/*--------------------------------------------------------
BEGIN COLLECTION CART SCRIPTS
---------------------------------------------------------*/
function startCollectionCart() {

//ajax request to get the items in the collections cart and display them
	
	$.ajax({
		type: "GET",
		url: "/groovy_metadata_registry/collectionBasket/dataElementsAsJSON",
		success: function(result){
			if(result!=null){
				$.each(result.dataElements, function(){
					$( "<li></li>" ).html('<a id="' + this.id + '" href="/groovy_metadata_registry/dataElement/show/' + this.id + '" >' + this.refId + ' - ' + this.name + '</a>').appendTo($( ".cart ul" )).draggable({
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
	
	
	
	//bind the droppable behaviour to the whole page so that the user can drag a dataelement out of the collections cart anywhere on the page to remove it from the basket
	
	
	$("#wrap").droppable({
        drop: function(event, ui) {
            $(c.li).remove();
            $(c.helper).remove();
            removeFromCollectionBasket(c.id)
        }
	});	
	
}


//initialise dataElement Drag and drop cart behaviour

function dataElementDragStart(){
	
	//bind the click handler to the collections cart to enable the user to drag data elements onto the cart

	$( ".cart" ).droppable({
		activeClass: "ui-state-default",
		hoverClass: "ui-state-hover",
		accept: ":not(.ui-sortable-helper)",
		greedy: true,
		drop: function( event, ui ) {
		
		// change the data element link text to include the reference id
		var link = $(c.name);
		link.text(c.refId + ' - ' + $(c.name).text());

		$( "<li></li>" ).html(link).appendTo($( ".cart ul" )).draggable({
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


/* ajax request to collection basket controller to add the dataElement to the basket......this is 
 *  */

function addToCollectionBasket(dataElementId){
	
	var data = {id: dataElementId};
	
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/groovy_metadata_registry/collectionBasket/addElement",
		data: data,
		success: function(e){
		},
		dataType: "json"
	});
	
	updateCartCount();
	
}


//ajax request to the collection basket controller to remove dataelement from basket  

function removeFromCollectionBasket(dataElementId){
	
	if($( ".cart ul li" ).size()>0){
		
		var data = {id: dataElementId};
		
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/groovy_metadata_registry/collectionBasket/removeElement",
			data: data,
			success: function(e){
			},
			dataType: "json"
		});
	}
	
	updateCartCount();
}

//function to display the number of data elements in the cart

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
	
	$('#dataElementList').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataElementTable"></table>' );
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
					return '<a id="'+ row.id + '" href="/groovy_metadata_registry/dataElement/show/' + row.id + '">' + data + '</a>'
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
						return '<a href="/groovy_metadata_registry/dataElement/show/' + row.parent_id + '">' + data + '</a>'
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
				return '<a href="/groovy_metadata_registry/dataElementConcept/show/' + data + '">' + row.dataElementConcept_name + '</a>' + '<img class="floatright" src="../images/details_open.png" />'
		    },
		   "mDataProp": "dataElementConcept_id", 
		   "sWidth":"30%",
		   "sTitle":"dataElementConcept"
			},
			{ "mDataProp": "id", "bVisible":    false }
		],
		"fnDrawCallback": function () {
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
	
	
	$('#dataElementTable tbody td img').live( 'click', function () {
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

}


/* Formating function for row details - this is for the description and definition columns.....
 * potentially need to add more info from the data elements class
 *  */
function fnFormatDetails ( nTr )
{
	var aData = oTable.fnGetData( nTr );
	var description = aData.description;
	var definition = aData.definition;
	var subElements = getObjectTable(aData.subElements, 'dataElement');
	var valueDomains = getObjectTable(aData.valueDomains, 'valueDomain');
	var collections = getObjectTable(aData.collections, 'collection');
	var externalSynonyms = getObjectTable(aData.externalSynonyms, 'externalSynonym');
	
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	
	if(description!=null){sOut += '<tr><td class="labelCol">Description: </td><td>' + description + '</td></tr>'}
	if(definition!=null){sOut += '<tr><td class="labelCol">Definition: </td><td>' + definition + '</td></tr>'}
	if(subElements!=null){sOut += '<tr><td class="labelCol">Subelements: </td><td>' + subElements + '</td></tr>'}
	if(valueDomains!=null){sOut += '<tr><td class="labelCol">Value Domains: </td><td>' + valueDomains + '</td></tr>'}
	if(collections!=null){sOut += '<tr><td class="labelCol">Collections: </td><td>' + collections + '</td></tr>'}
	if(externalSynonyms!=null){sOut += '<tr><td class="labelCol">External Synonyms: </td><td>' + externalSynonyms + '</td></tr>'}
	
	sOut += '</table>';
	 
	return sOut;
}



/*--------------------------------------------------------
END DATAELEMENT LIST  SCRIPTS
---------------------------------------------------------*/




