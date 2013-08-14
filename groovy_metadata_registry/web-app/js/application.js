var c = {};


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
	
	/*$('.help a').click(function(){
		alert('test');
		$('.help p').toggle('slow', function() {});
	});*/
	
});

$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "/groovy_metadata_registry/collectionBasket/dataElementsAsJSON",
		success: function(result){
			if(result!=null){
				$.each(result.dataElements, function(){
					$( "<li></li>" ).html(this.refId + ' - <a id="' + this.id + '" href="/groovy_metadata_registry/dataElement/show/' + this.id + '" >' + this.name + '</a>').appendTo($( ".cart ol" )).draggable({
				        helper: "clone",
				        start: function(event, ui) {
				            c.li = this;
				            c.id = $(this).find("a").attr("id");
				            c.helper = ui.helper;
				        }
					});
				});
			}
		},
		dataType: "json"
	});
	
})

$("#maincontainer").droppable({
        drop: function(event, ui) {
            $(c.li).remove();
            $(c.helper).remove();
            removeFromCollectionBasket(c.id)
        }
 });


function removeFromCollectionBasket(dataElementId){
	
	if($( ".cart ol li" ).size()>0){
		
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
}

