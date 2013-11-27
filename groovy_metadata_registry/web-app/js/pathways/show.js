
//Enable embedded text bindings in html (http://mbest.github.io/knockout.punches/)
var vm   
var pathwayService
var nextSave

function initPathways(param){
	
	ko.punches.interpolationMarkup.enable();

	//create new pathwayService
	pathwayService = new PathwayService()
    //Create the main view model
    vm = new AppViewModel();


    //console.log('applying bindings')
    
    //Bind the view model to <body> and its descendants
    ko.applyBindings(vm, document.getElementById('content'));

    //Initial action on page load
    if(param==='create'){
    	$('#CreatePathwayModal').modal({ show: true, keyboard: false, backdrop: 'static' });
    }else{
    	
    	//console.log(param)
    	vm.loadPathway(param);
    }
    
    //save every 60 seconds
    
    setInterval(function(){vm.updatePathway(); console.log('saving')},100000);
    
    
}