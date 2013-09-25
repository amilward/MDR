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
			 return "<h4>My form control here.</h4>";
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
			 return "<h4>My form control here.</h4>";
		 }
	 }
	 
	 
 ];



function Property(iname, ename, value){
	var self = this;

	self.iname = ko.observable(iname);
	self.ename = ko.observable(ename);
	self.value = ko.observable(value);
}


function Component(iid, eid, properties) {
	var self = this;
	self.dateCreated = new Date();
	self.internalIdentifier = ko.observable(iid);
	self.externalIdentifier = ko.observable(eid);
	
	self.question = ko.observable();
	
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
		var c = new Component(lastComponentID++, self.externalIdentifier(), self.properties());
		var q = self.question().clone();
		c.setQuestion(q);
		//console.log("cloning");
		//console.log(ko.toJSON(self, null, 2));
		return c;
		
	};
	
}

function Form(id, fullName, shortName, components) {
	var self = this;
	self.formID = ko.observable(id);
	self.formFullName = ko.observable(fullName);
	self.formShortName = ko.observable(shortName);
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


    
    self.addForm = function(id, fullName, shortName, components){
    	self.forms.push(new Form(id, fullName, shortName, components));
    	self.setActiveFormId(id);
    };
    
    self.palette = [
                   {
                	   name: "Structural Components",
                	   elements: [
                	              {
                	            	  id: 1,
                	            	  name: "Header Text",
                	            	  icon: "icon-font",
                	            	  type: "structural",
                	            	  defaultView: "<h3>headerTitle</h3>",
                	            	  properties: [{ename: "Heading title", iname: "headerTitle", value: "Heading"}]
                	              },{
                	            	  id: 2,
                	            	  name: "Explanatory Text",
                	            	  icon: "icon-align-justify",
                	            	  type: "structural",
                	            	  defaultView: "<p>text</p>",
                	            	  properties: [{ename: "Text", iname: "text", value: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque quis ipsum et metus consectetur malesuada vel eget diam. Vestibulum mi tortor, vulputate sed convallis non, elementum ut felis. Suspendisse suscipit libero eleifend velit consequat dictum. Morbi laoreet volutpat tortor, hendrerit posuere ante dapibus non. Pellentesque non fringilla dolor. Nulla ac sem ac dui semper commodo. Morbi at metus eu augue gravida ornare. Mauris porttitor sollicitudin nisl, gravida luctus nibh interdum in. "}]
                	              },{
                	            	  id: 3,
                	            	  name: "Section",
                	            	  icon: "icon-sitemap",
                	            	  type: "structural",
                	            	  defaultView: "<h4>Section</h4><p>This is a new form section</p>",
                	            	  properties: []
                	                  
                	              }]
                   },
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

    
}

var lastComponentID = 0;

function newComponent(paletteElement)
{
	lastComponentID++;
	var iid = paletteElement.id + '-' + lastComponentID;
	var eid = iid;
	var properties = paletteElement.properties;
	
	//var html = paletteElement.defaultView;
	var c = new Component(iid, eid, properties);
	
	
	if(paletteElement.type == "question")
	{
		//console.log(dataTypeTemplates);
		//console.log("something");
		//console.log($.grep(dataTypeTemplates, function(n,i){ return n.name==paletteElement.datatype; })[0]);
		var dt = $.grep(dataTypeTemplates, function(n,i){ return (n.name == paletteElement.datatype); })[0];
		//return c;
		var renderingOption = dt.prefRenderingOptions[0];
		var restriction = "";
		var selectMultiple = false;
		var options = [];
		var previewRender = dt.previewRender;
		var dti = new DataTypeInstance(iid, eid, name, dt, renderingOption, restriction, selectMultiple, options, previewRender);
		var question = new Question("no title set", "preText", "label", "", dti);
		
		c.setQuestion(question);
		//console.log(ko.toJSON(question, null, 2));
	}
	//console.log(c.question());
	return c; 
	
}

function openForms(formDesignId){


	
// Activates knockout.js	
	
var viewModel = new FormsModel();
viewModel.addForm('refId','formName','',[]);

ko.applyBindings(viewModel);

//ajax call to formDesign controller to get form design JSON	
$.getJSON('../jsonFormsBuilder/' + formDesignId, function(data) {
	$.each(data.questions, function( index, value ){
		
	var element = 	{
      	  id: 5,
    	  name: "String Input",
    	  icon: "icon-pencil",
    	  type: "question",
    	  datatype: "string",
    	  properties: [{ename: value.name, iname: value.name, value: value.name}]
      }	
		
	//alert()
	newComponent(element)
	});
});


}


function Question(title, preText, label, style, dataTypeInstance)
{
	var self = this;
	self.title = ko.observable(title);
	
	self.computedTitle = ko.computed({
    	read: function() {
	    	if(self.title() == "no title set")
	    	{
	    		return "Question n.";
	    	}
	    	else return self.title();
	    },
	    write: function(value) {
	    	self.title(value);
	    },
	    owner: self
    });
	self.preText = ko.observable(preText);
	self.label = ko.observable(label);
	self.style = ko.observable(style);
	self.dataTypeInstance = ko.observable(dataTypeInstance);
	
	self.setDataTypeInstance = function(dti) {
		self.dataTypeInstance(dti);
	};
	
	
	self.clone = function() {
		var dti = null;
		if(self.dataTypeInstance())
		{
			dti = self.dataTypeInstance().clone();
		}
		var q = new Question(self.title(), self.preText(), self.label(), self.style(), dti);
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
	self.options = ko.observableArray(options);
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


