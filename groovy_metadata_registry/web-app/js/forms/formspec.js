$(document).ready(function() {
      // Load the form object from path/to/form.json
		$.getJSON('../jsonFormSpec/1', function(data) {
	        $("#previewForm").dform(data);
			});
});


