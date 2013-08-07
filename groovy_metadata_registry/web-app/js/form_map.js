$(function() {
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
});