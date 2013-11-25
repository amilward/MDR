
    //Enable embedded text bindings in html (http://mbest.github.io/knockout.punches/)
var vm   
var pathwayService

function initPathways(){
	
	ko.punches.interpolationMarkup.enable();

	//create new pathwayService
	pathwayService = new PathwayService()
    //Create the main view model
    vm = new AppViewModel();

    console.log('applying bindings')
    
    //Bind the view model to <body> and its descendants
    ko.applyBindings(vm, document.getElementById('content'));

    //Initial action on page load
    $('#CreatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });
    
}