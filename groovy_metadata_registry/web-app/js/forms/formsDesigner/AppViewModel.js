function AppViewModel() {
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
    
    self.createQuestion = function (element){
    	
    	var dt = $.grep(dataTypeTemplates, function(n,i){ return (n.name == element.datatype); })[0];
    	//return c;
    	var renderingOption = dt.prefRenderingOptions[0];
    	var name = dt.name;
    	var restriction = "";
    	var selectMultiple = false;
    	var options = element.enumerations;
    	//var options = ['test','asddfsaafds'];
    	var previewRender = dt.previewRender;
    	//this uses a random is to identify the data type
    	//it checks that there isn't another datatype with teh same random id first
    	var newRandomId = getUniqueId(1,1000)
    	
    	var dti = new DataTypeInstance(newRandomId, '', name, dt, renderingOption, restriction, selectMultiple, options, previewRender);
    	

    	if(element.prompt==null){
    		
    		var question = new Question("no question prompt", "no additionalInstructions","", "no label", "", dti, "no defaultValue", "no placeholder", "no unitOfMeasure test", "", "no format", "false", "no enumerations", "");
    		
    		
    	}else{
    		

    		var question = new Question(element.prompt, element.additionalInstructions, element.questionNumber, element.label, element.style, 
    				dti, element.defaultValue, element.placeholder, 
    				element.unitOfMeasure,  element.maxCharacters, 
    				element.format, element.isEnumerated, element.enumerations, 
    				element.questionId, element.inputId, element.dataElementId, element.valueDomainId);
    	}
    	

    	//console.log(ko.toJSON(question, null, 2));
    	
    	return question;
    	
    }
    
    
    self.createComponent = function (element)
    {

    	
    	//console.log(ko.toJSON(element))

    	lastComponentID++;
    	var iid = element.id + '-' + lastComponentID;
    	var eid = iid;
    	var properties = element.properties;
    	var icon = element.icon;
    	
    	//var html = element.defaultView;
    	var c = new Component(iid, eid, properties, icon);
    	
    	if(element.type == "question")
    	{
    		
    		question = self.createQuestion(element);

    		c.setQuestion(question);
    		//console.log(ko.toJSON(question, null, 2));
    	}
    	
    	
    	if(element.type == "section")
    	{
    		//console.log(element.questions)
    		var questions = []
    		if(element.questions){
    			questions = element.questions
    		}
    		section = new Section(element.name, element.id, questions);
    		c.setSection(section);

    	}
    	
    	
    	//console.log(c.question());

    	return c; 
    	
    }
      
}