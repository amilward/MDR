
//Enable embedded text bindings in html (http://mbest.github.io/knockout.punches/)
var vm   
var pathwayService
var nextSave

function initPathways(pathwayId){
	
	ko.punches.interpolationMarkup.enable();

	//create new pathwayService
	pathwayService = new PathwayService()
    //Create the main view catalogue
    vm = new AppViewModel();


    //console.log('applying bindings')

    //Bind the view catalogue to <body> and its descendants
    ko.applyBindings(vm, document.getElementById('content'));
    

    //Initial action on page load
    if(pathwayId){
    	
    	$.when(pathwayService.loadPathway(pathwayId)).done(function (pathwayJSON) {
			//console.log('test')
			//console.log(pathwayJSON.pathwaysModelInstance)
			vm.loadPathway(pathwayJSON.pathwaysModelInstance);
		});
    	
    }else{
    	$('#CreatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });
    	
    }
    
    //save every 60 seconds
    
    setInterval(function(){vm.updatePathway(); console.log('saving')},100000);
    
    
}