/**
 * ViewModel for the pathway creation modal
 * Author: Ryan Brooks (ryan.brooks@ndm.ox.ac.uk)
 */
$( document ).ready(function() {
	
	//Create the main view model
    var vm = new CreationModalAVM();

    //Bind the view model to <createPathwayModal> and its descendants
    ko.applyBindings(vm, document.getElementById('createPathwayModal'));
});