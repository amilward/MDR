/**
 * ViewModel for the pathway creation modal
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 */
$( document ).ready(function() {
	
	//Create the main view model
    var vm = new AppViewModel();

    //Bind the view model to <body> and its descendants
    ko.applyBindings(vm, document.getElementById('modal-body'));

    //Initial action on page load
    $('#createPathwayModal').modal({ show: false, keyboard: false, backdrop: 'static' });
});