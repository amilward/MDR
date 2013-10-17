// This is a simple *viewmodel* - JavaScript that defines the data and behavior of your UI

var dataTypeTemplates = [
	 {
		 name: "numeric",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["text"],
		 allowMultiple: true,
		 simpleRestrictions: ["Integer", "PositiveInteger","NegativeInteger"],
		 useOptions: false,
		 previewRender: function(){
			 return "<span><input type=\"text\"/></span>";
		 }
	 },
	 {
		 name: "string",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["text", "textarea"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 var self = this;
			 if(self.renderingOption() == "text")
				return "<span><input type=\"text\"/></span>";
			 else if(self.renderingOption() == "textarea")
				return "<span><textarea rows=\"2\" cols=\"300\" style=\"width: 40em;\"/></span>";
			 else return "";
		 }
	 },
	 {
		 name: "boolean",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["checkbox", "radio"],
		 allowMultiple: false,
		 useOptions: false,
		 previewRender: function(){
			 var self = this;
			 if(self.renderingOption() == "checkbox")
				return "<span><input type=\"checkbox\"/></span>";
				//return "<span><input type=\"text\"/></span>";
			 else if(self.renderingOption() == "radio")
				return "<span><label><input type=\"radio\"/>Yes</label><br/><label><input type=\"radio\"/>No</label></span>";
			 else return "";

		 }
	 },
	 {
		 name: "date",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["datepicker"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<i class=\"icon-large pull-left icon-calendar\"></i>"
		 }
	 },
	 {
		 name: "time",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["time"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<h4>My form control here.</h4>";
		 }
	 },
	 {
		 name: "datetime",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["datetimepicker"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<h4>My form control here.</h4>";
		 }
	 },
	 {
		 name: "duration",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["duration"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<h4>My form control here.</h4>";
		 }
	 },
	 {
		 name: "enumeration",
		 isPrimitive: true,
		 isAbstract: true,
		 prefRenderingOptions: ["radio","select", "checkbox","combobox"],
		 allowMultiple: true,
		 useOptions: true,
		 previewRender: function(){
			 var self = this;
			 var enumerations = "";
			 if(self.renderingOption() == "checkbox"){
					return "<span><input type=\"checkbox\"/></span>";
					//return "<span><input type=\"text\"/></span>";
			 }else if(self.renderingOption() == "radio"){
					enumerations = " <ul id=\"ui-accordion-accordion-header-1\" class=\"unstyled accordion collapse in\">";
					
					enumerations += "<li class=\"accordion-group \">" ;
					enumerations += "<a data-parent=\"#menu\" data-toggle=\"collapse\" class=\"accordion-toggle\" data-target=\"#enumerations-nav-"+self.iid() +"\">";
					enumerations += "<i class=\"icon-list-ol icon-large\"></i> Enumerations  [Radio] ";
					enumerations += "</a>";
					enumerations += "<ul class=\"collapse\" id=\"enumerations-nav-"+self.iid() +"\">";
					$.each(self.options(),function(index, value){
						enumerations += "<input class=\"pull-left\" type=\"radio\"/><li>"+ value +" ["+index+"]</li>";

					});
					enumerations += "</ul>";
					enumerations += "</li>";
					enumerations += "</ul>";
						 
					return enumerations;
					
			 }else {
				 return enumerations;
			 }
		}
	 } 
 ];

var paletteItems = [
                    {
                 	   name: "Simple Questions",
                 	   elements: [
                 	              {
                 	            	  id: 4,
                 	            	  name: "Numeric Input",
                 	            	  icon: "icon-pencil",
                 	            	  type: "question",
                 	            	  datatype: "numeric",
                 	            	  properties: []
                 	              },
                 	              {
                 	            	  id: 5,
                 	            	  name: "String Input",
                 	            	  icon: "icon-pencil",
                 	            	  type: "question",
                 	            	  datatype: "string",
                 	            	  properties: []
                 	              },
                 	              {
                 	            	  id: 6,
                 	            	  name: "Boolean Input",
                 	            	  icon: "icon-check",
                 	            	  type: "question",
                 	            	  datatype: "boolean",
                 	            	  properties: []
                 	              },
                 	              {
                 	            	  id: 7,
                 	            	  name: "Date Input",
                 	            	  icon: "icon-calendar",
                 	            	  type: "question",
                 	            	  datatype: "date",
                 	            	  properties: []
                 	              },
                 	              {
                 	            	  id: 8,
                 	            	  name: "Date / Time Input",
                 	            	  icon: "icon-calendar",
                 	            	  type: "question",
                 	            	  datatype: "datetime",
                 	            	  properties: []
                 	              },
                 	              {
                 	            	  id: 9,
                 	            	  name: "Time Input",
                 	            	  icon: "icon-time",
                 	            	  type: "question",
                 	            	  datatype: "time",
                 	            	  properties: []
                 	              }]
                    },
                    {
                 	   name: "Complex Questions",
                 	   elements: [{
                 	            	  id: 10,
                 	            	  name: "Radio Select",
                 	            	  icon: "icon-list-ul",
                 	            	  type: "question",
                 	            	  datatype: "enumeration",
                 	            	  properties: []
                 	              },{
                 	            	  id: 11,
                 	            	  name: "Dropdown Select",
                 	            	  icon: "icon-reorder",
                 	            	  type: "question",
                 	            	  datatype: "enumeration",
                 	            	  properties: []
                 	              }]
                    }];


var viewModel;


function Property(iname, ename, value){
	var self = this;

	self.iname = ko.observable(iname);
	self.ename = ko.observable(ename);
	self.value = ko.observable(value);
}


function Component(iid, eid, properties, icon) {
	var self = this;
	self.dateCreated = new Date();
	self.internalIdentifier = ko.observable(iid);
	self.externalIdentifier = ko.observable(eid);
	self.question = ko.observable();
	self.icon = ko.observable(icon);
	self.setQuestion = function(q) {
		self.question(q);
	};
	
	
	self.structuralFeature = ko.observable();
	
	var mappedProperties = $.map(properties, function(item)
			{ 	if(typeof(item.iname) == 'function')
			  	{
					return new Property(item.iname(), item.ename(), item.value());
				}else{
					return new Property(item.iname, item.ename, item.value);
				}
			});
	
	
	self.properties = ko.observableArray(mappedProperties);

		
	self.clone = function(){
		var c = new Component(lastComponentID++, self.externalIdentifier(), self.properties(), self.icon());
		
		var q = self.question().clone();
		c.setQuestion(q);
		//console.log("cloning");
		//console.log(ko.toJSON(self, null, 2));
		return c;
		
	};
	
}

function Form(id, fullName, refId, description, versionNo, isDraft, collectionId, formVersionNo, components) {
	var self = this;
	self.formID = ko.observable(id);
	self.formDesignName = ko.observable(fullName);
	self.formRefId = ko.observable(refId);
	self.formCollectionId = ko.observable(collectionId);
	self.formVersionNo = formVersionNo;
	self.formDescription = ko.observable(description);
	self.versionNo = ko.observable(versionNo);
	self.isDraft = ko.observable(isDraft);
	self.components = ko.observableArray(components);
	
	
	self.copyComponent = function(data, idx){
		//console.log(data);
		//console.log(idx);
		//var idx = self.components.indexOf(iid);
		var newItem = data.clone();
		self.components.splice(idx+1, 0, newItem);
		viewModel.setCurrentlySelectedFormComponentIdx(idx+1);
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
}



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
    
    self.currentlySelectedFormComponentIdx = ko.observable(null);

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
    

    self.setActiveFormId = function(id){
    	self.activeFormId(id); 
    	refreshFormPanelViews();
    };

    self.setCurrentlySelectedFormComponentIdx = function(idx){
    	//console.log("index: " + idx);
    	self.currentlySelectedFormComponentIdx(idx); 
    	//refreshFormPanelViews();
    };


    
    self.addForm = function(id, fullName, refId, description, versionNo, isDraft, collectionId, formVersionNo, components){
    	//self.palette = paletteItems;
    	self.forms.push(new Form(id, fullName, refId, description, versionNo, isDraft, collectionId, formVersionNo, components));
    	self.setActiveFormId(id);
    	//self.palette = paletteItems;
    	self.palette = paletteItems;
    	
    };
    
    
    
}

var lastComponentID = 0;

function newComponent(element)
{

	lastComponentID++;
	var iid = element.id + '-' + lastComponentID;
	var eid = iid;
	var properties = element.properties;
	var icon = element.icon;
	
	//var html = element.defaultView;
	var c = new Component(iid, eid, properties, icon);
	
	
	if(element.type == "question")
	{
		//console.log(dataTypeTemplates);
		//console.log("something");
		//console.log($.grep(dataTypeTemplates, function(n,i){ return n.name==element.datatype; })[0]);
		var dt = $.grep(dataTypeTemplates, function(n,i){ return (n.name == element.datatype); })[0];
		//return c;
		var renderingOption = dt.prefRenderingOptions[0];
		var name = dt.name;
		var restriction = "";
		var selectMultiple = false;
		var options = element.enumerations;
		//var options = ['test','asddfsaafds'];
		var previewRender = dt.previewRender;
		var dti = new DataTypeInstance(iid, eid, name, dt, renderingOption, restriction, selectMultiple, options, previewRender);
		
		if(element.prompt==null){
			
			var question = new Question("no question prompt", "no additionalInstructions", "no label", "", dti, "no defaultValue", "no placeholder", "no unitOfMeasure test", "", "no format", "false", "no enumerations", "");
			
			
		}else{
			
			var question = new Question(element.prompt, element.additionalInstructions, element.label, element.style, 
					dti, element.defaultValue, element.placeholder, 
					element.unitOfMeasure,  element.maxCharacters, 
					element.format, element.isEnumerated, element.enumerations, 
					element.questionId, element.inputId, element.dataElementId, element.valueDomainId);
		}
		
		
		c.setQuestion(question);
		//console.log(ko.toJSON(question, null, 2));
	}
	//console.log(c.question());

	return c; 
	
}

function Question(prompt, additionalInstructions, label, style, dataTypeInstance, defaultValue, placeholder, unitOfMeasure, maxCharacters, format, isEnumerated, enumerations, questionId, inputId, dataElementId, valueDomainId)
{
	var self = this;
	self.prompt = ko.observable(prompt);
	
	self.computedPrompt = ko.computed({
    	read: function() {
	    	if(self.prompt() == "no prompt set")
	    	{
	    		return "Question n.";
	    	}
	    	else return self.prompt();
	    },
	    write: function(value) {
	    	self.prompt(value);
	    },
	    owner: self
    });
	
	self.questionId = ko.observable(questionId);
	self.inputId = ko.observable(inputId);
	self.dataElementId = ko.observable(dataElementId);
	self.valueDomainId = ko.observable(valueDomainId);
	self.additionalInstructions = ko.observable(additionalInstructions);
	self.label = ko.observable(label);
	self.style = ko.observable(style);
	self.dataTypeInstance = ko.observable(dataTypeInstance);
	self.defaultValue = ko.observable(defaultValue);
	self.placeholder = ko.observable(placeholder);
	self.unitOfMeasure = ko.observable(unitOfMeasure);
	self.maxCharacters = ko.observable(maxCharacters);
	self.format = ko.observable(format);
	self.isEnumerated = ko.observable(isEnumerated);
	self.enumerations = ko.observable(JSON.stringify(enumerations));
	self.icon = ko.observable()
	
	self.setDataTypeInstance = function(dti) {
		self.dataTypeInstance(dti);
	};
	
	
	self.clone = function() {
		var dti = null;
		if(self.dataTypeInstance())
		{
			dti = self.dataTypeInstance().clone();
		}
		var q = new Question(self.prompt(), 
				self.additionalInstructions(), 
				self.label(), self.style(), 
				dti, self.defaultValue(), 
				self.placeholder(), 
				self.unitOfMeasure(), 
				self.maxCharacters(), 
				self.format(), 
				self.isEnumerated(), 
				self.enumerations(), 
				self.questionId(), 
				self.inputId(), 
				self.dataElementId(),
				self.valueDomainId());
		//console.log(ko.toJSON(q, null, 2));
		return q;
	};
}

function DataTypeInstance(iid, eid, name, instanceOf, renderingOption, restriction, selectMultiple, options, previewRender)
{
	var self = this;
	self.iid = ko.observable(iid);
	self.eid = ko.observable(eid);
	self.name = ko.observable(name);
	self.instanceOf = ko.observable(instanceOf);
	self.renderingOption = ko.observable(renderingOption);
	self.restriction = ko.observable(restriction);
	self.selectMultiple = ko.observable(selectMultiple);
	self.options = ko.observable(options);
	self.previewRender = previewRender;
	self.clone = function() {
		return new DataTypeInstance( self.iid(), self.eid(), self.name(), 
				self.instanceOf(), self.renderingOption(), self.restriction(),
				self.selectMultiple(), self.options(), self.previewRender);
	};
	
	self.renderingOptions = function() {
		var array = [];
		$.each(self.instanceOf().prefRenderingOptions, function(i, n)
		{
			array.push({'value': n, 'text': n});
		});
		return array;
	};
	
}

function SelectOption()
{
	var self = this;
	self.text = ko.observable();
	self.value = ko.observable();
}

function getDataType(dataType, isEnumerated){
	
	if(!isEnumerated){
		dataType = dataType.toLowerCase();
	}else{
		dataType = 'enumeration'
	}
	
	//console.log(dataType)

	return dataType;
	
}

function getIcon(dataType, isEnumerated){
	
	var icon
	
	if(!isEnumerated){
		
		switch(dataType)
		{
		case 'Date':
			icon = 'icon-calendar'
		  break;
		case 'DateTime':
			icon = 'icon-calendar'
		  break;
		case 'Time':
			icon = 'icon-time'
			  break;
		case 'Boolean':
			icon = 'icon-check'
			  break;
		default:
			icon = 'icon-pencil'
		}
		
	}else{
		icon = 'icon-check'
	}
	
	//console.log(dataType)

	return icon;
	
}

function createEmptyForm(){
	
	viewModel = new FormsModel();
	var components = [];
	
	viewModel = new FormsModel();
	
	viewModel.addForm('', '','','', '', true,'','', components);
	setTimeout(function(){
		initializePalette();
	}, 100);

	ko.applyBindings(viewModel);
	
}


function createFormFromCollection(collectionId, questions){

	//set up defer object
	var jsonDeferred = $.Deferred();

	// Activates knockout.js	
	
	viewModel = new FormsModel();
	
	var components = []
	
	questions = JSON.parse(questions);
	
	$.each(questions, function(index, question){
		var element = 	{
	      	  id: '',
	    	  name: '',
	    	  icon: getIcon(question.dataType.name, question.dataType.enumerated),
	    	  type: 'question',
	    	  datatype: getDataType(question.dataType.name, question.dataType.enumerated),
	    	  properties: [{ename: question.name, iname: question.name, value: question.name}],
	    	  prompt: escapeChar(question.label),
	    	  style: '', 
	    	  defaultValue: '', 
	    	  placeholder: '',
	    	  unitOfMeasure: question.unitOfMeasure, 
	    	  maxCharacters: question.maxCharacters, 
	    	  format: question.format,
	    	  isEnumerated: question.isEnumerated, 
	    	  enumerations: question.options,
	    	  additionalInstructions: escapeChar(question.additionalInstructions), 
	    	  label: escapeChar(question.label), 
	    	  questionId: '',
	    	  inputId: '', 
	    	  dataElementId: question.dataElementId,
	    	  valueDomainId: question.valueDomainId
	      }	

		components.push(newComponent(element));
		jsonDeferred.resolve();
	});
	
	
	jsonDeferred.done(function(){
		viewModel.addForm('', '','','', '', true,collectionId,'', components);
		setTimeout(function(){
			initializePalette();
		}, 500);

		ko.applyBindings(viewModel);
	});
	
}

function openForms(formDesignId, formDesignRefId, formDesignName, formDesignDescription, formVersionNo, formIsDraft, formCollectionId, formVersionNo){

	//set up defer object
	var jsonDeferred = $.Deferred();

	// Activates knockout.js	
	
	viewModel = new FormsModel();
		
	var components = []
		
	//ajax call to formDesign controller to get form design JSON	
	$.getJSON('../jsonFormsBuilder/' + formDesignId, function(data) {
		$.each(data.questions, function( index, value ){
		var element = 	{
	      	  id: '',
	    	  name: '',
	    	  icon: getIcon(value.dataType, value.isEnumerated),
	    	  type: "question",
	    	  datatype: getDataType(value.dataType, value.isEnumerated),
	    	  properties: [{ename: value.name, iname: value.name, value: value.name}],
	    	  prompt: escapeChar(value.prompt),
	    	  style: value.style, 
	    	  defaultValue: escapeChar(value.defaultValue), 
	    	  placeholder: escapeChar(value.placeholder),
	    	  unitOfMeasure: value.unitOfMeasure, 
	    	  maxCharacters: value.maxCharacters, 
	    	  format: value.format,
	    	  isEnumerated: value.isEnumerated, 
	    	  enumerations: value.enumerations,
	    	  additionalInstructions: escapeChar(value.additionalInstructions), 
	    	  label: escapeChar(value.label), 
	    	  questionId: value.id,
	    	  inputId: value.inputId,
	    	  dataElementId: value.dataElementId,
	    	  valueDomainId: value.valueDomainId
	      }	

		components.push((newComponent(element)))
		
		});
		
		jsonDeferred.resolve()
			
	});

	jsonDeferred.done(function(){
		viewModel.addForm(formDesignId, formDesignName,formDesignRefId,formDesignDescription, formVersionNo, formIsDraft, formCollectionId, formVersionNo, components);
		setTimeout(function(){
			initializePalette();
		}, 500);

		ko.applyBindings(viewModel);
	});

}

function saveForm(){
	
	var form = viewModel.activeForm()
	
	$.ajax({
		type: "POST",
		url: 'save',
		data: ko.toJSON(form),
		success: function(data){
			alert('saved')
			window.location.href = 'show/' + data.formDesignId
		},
		contentType: 'application/json',
		dataType: 'json'
		});
	
}

function updateForm(formDesignId){
	
	var form = viewModel.activeForm()
	
	form.formDesignId = formDesignId

	$.ajax({
		type: "POST",
		url: '../update',
		data: ko.toJSON(form),
		success: function(data){
			if(data.formVersion!=null){
				form.formVersionNo = data.formVersion
			}
			alert(data.message);
		},
		contentType: 'application/json',
		dataType: 'json'
		});
	
}


function escapeChar(text){
	if(text!=null){
		text =  text.replace("'", "/'");
	}
	
	return text
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}



