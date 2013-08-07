$(function() {

	$(document).ready(function() {
		  if($('#isEnumerated').is(':checked')){
			  $('#enumerations').toggle('slow', function() {
				    // Animation complete.
				  });  
		  }
		});
	
	$('#isEnumerated').click(function() {
		  $('#enumerations').toggle('slow', function() {
		    // Animation complete.
		  });
		});
	
});