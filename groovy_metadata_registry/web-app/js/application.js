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
	
	$('.help a').click(function(){
		alert('test');
		$('.help p').toggle('slow', function() {});
	});
	
});



