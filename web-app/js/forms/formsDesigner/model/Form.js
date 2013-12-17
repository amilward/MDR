function Form(id, fullName, description, versionNo, isDraft, collectionId, formVersionNo, components) {
	
	var self = this;
	self.formID = ko.observable(id);
	self.formDesignName = ko.observable(fullName);
	self.formCollectionId = ko.observable(collectionId);
	self.formVersionNo = formVersionNo;
	self.formDescription = ko.observable(description);
	self.versionNo = ko.observable(versionNo);
	self.isDraft = ko.observable(isDraft);
	//console.log(ko.toJSON(components))
	self.components = ko.observableArray(components);
	
	
    self.editForm = function(){
    	
    	$('#EditFormModal').modal({ show: true, keyboard: false, backdrop: 'static' });
    }
	
	self.copyComponent = function(data, idx){
		//console.log(data);
		//console.log(idx);
		//var idx = self.components.indexOf(iid);
		var newItem = data.clone();
		self.components.splice(idx+1, 0, newItem);
		viewModel.setCurrentlySelectedFormSectionIdx(idx+1);
		
		refreshFormPanelViews();
	};
	
	self.deleteComponent = function(comp) {

	    var heading = 'Confirm Form Component Delete';
	    var question = 'Please confirm that you wish to delete this form component: ' + comp.externalIdentifier() + '.';
	    var cancelButtonTxt = 'Cancel';
	    var okButtonTxt = 'Confirm';

	    var callback = function() {
	    	self.components.remove(comp);
	    	refreshFormPanelViews();
	    };

	    confirm(heading, question, cancelButtonTxt, okButtonTxt, callback);
	};
	
	self.editComponent = function(comp) {
		$('#EditSectionModal').modal({ show: true, keyboard: false, backdrop: 'static' });
	};
	
	
}