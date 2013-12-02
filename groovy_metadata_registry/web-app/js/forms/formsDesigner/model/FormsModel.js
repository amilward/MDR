function FormsModel() {
    var self = this;
    self.forms = ko.observableArray([]);
    self.activeFormId = ko.observable(null);

    self.activeForm = ko.computed(function() {
    	if(self.activeFormId != null && self.forms().length > 0)
    	{
    		return $.grep(self.forms(), function (n,i) {  return n.formID() == self.activeFormId(); })[0];
    	} else {
    		return null;
    	}
    }, self);
    
    
    self.currentlySelectedFormComponentIdx = ko.observable(null)
    
    self.currentlySelectedFormSectionIdx = ko.observable(null);

    self.currentlySelectedQuestionIdx = ko.observable(null);
    
    
    self.currentlySelectedFormComponent = ko.computed({
    	read: function() {
    		
	    	if(self.currentlySelectedFormComponentIdx() != null && self.activeForm() != null && self.activeForm().components().length > 0)
	    	{
	    		return self.activeForm().components()[self.currentlySelectedFormComponentIdx()];
	    	} else {
	    		return null;
	    	}
	    },
	    write: function(value) {
	    	console.log(value);
	    },
	    owner: self
    });
    
    
    self.currentlySelectedFormSection = ko.computed({
    	read: function() {
    		
	    	if(self.currentlySelectedFormSectionIdx() != null && self.activeForm() != null && self.activeForm().components().length > 0)
	    	{
	    		return self.activeForm().components()[self.currentlySelectedFormSectionIdx()];
	    	} else {
	    		return null;
	    	}
	    	
	    	console.log(value)
	    },
	    write: function(value) {
	    	console.log(value);
	    },
	    owner: self
    });
    
    //note to self nned to check th
    
    self.currentlySelectedQuestion = ko.computed({
    	read: function() {
    		//console.log('selecting question')
	    	if(self.currentlySelectedQuestionIdx() != null && self.activeForm() != null && self.activeForm().components().length > 0)
	    	{
	    		if(self.currentlySelectedFormSection().section().questions()!=null){
	    		//console.log(self.currentlySelectedFormSection().section().questions()[self.currentlySelectedQuestionIdx()])
	    		
	    			return self.currentlySelectedFormSection().section().questions()[self.currentlySelectedQuestionIdx()]
	    		
	    		}else{
	    			return null
	    		}
	    	} else {
	    		return null;
	    	}
	    },
	    write: function(value) {
	    	console.log(value);
	    },
	    owner: self
    });
    
    

    self.setActiveFormId = function(id){
    	self.activeFormId(id); 
    	refreshFormPanelViews();
    };

    self.setCurrentlySelectedFormSectionIdx = function(idx){
    	//console.log("index: " + idx);
    	self.currentlySelectedFormSectionIdx(idx); 
    	//refreshFormPanelViews();
    };
    
    self.setCurrentlySelectedQuestionIdx = function(idx){
    	
    	self.currentlySelectedQuestionIdx(idx); 
    	//refreshFormPanelViews();
    };
    
    


    
    self.addForm = function(id, fullName, description, versionNo, isDraft, collectionId, formVersionNo, components){
    	//self.palette = questionPallette;
    	
    	self.forms.push(new Form(id, fullName, description, versionNo, isDraft, collectionId, formVersionNo, components));
    	self.setActiveFormId(id);
    	//self.palette = questionPallette;
    	self.palette = questionPallette;
    	self.sectionPalette = sectionPallette;
    	self.sections = sectionItems;
    	
    	
    	
    };
      
}