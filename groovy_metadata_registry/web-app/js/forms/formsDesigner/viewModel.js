// This is a simple *viewmodel* - JavaScript that defines the data and behavior of your UI

var sectionItems = []

var viewModel

var lastComponentID = 0;

var randomIds = []


function getDataType(dataType, isEnumerated){
	
	if(!isEnumerated){
		dataType = dataType.toLowerCase();
	}else{
		dataType = 'enumeration'
	}

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
	
	viewModel.addForm('','','', '', true,'','', components);
	setTimeout(function(){
		initializePalette();
	}, 100);

	ko.applyBindings(viewModel);
	
}


function createFormFromCollection(collectionId, jsonQuestions){

	//set up defer object
	var jsonDeferred = $.Deferred();

	// Activates knockout.js	
	
	viewModel = new FormsModel();
	
	var components = []
	
	var questions = []
	
	jsonQuestions = JSON.parse(jsonQuestions);
	
	$.each(jsonQuestions, function(index, value){
		
		var question = 	{
		      	  id: '',
		    	  name: '',
		    	  icon: getIcon(value.dataType.name, value.isEnumerated),
		    	  type: "question",	    	  
		    	  datatype: getDataType(value.dataType.name, value.isEnumerated),
		    	  properties: [{ename: value.name, iname: value.name, value: value.name}],
		    	  prompt: escapeChar(value.label),
		    	  style: '', 
		    	  defaultValue: '', 
		    	  placeholder: '',
		    	  unitOfMeasure: value.unitOfMeasure, 
		    	  maxCharacters: value.maxCharacters, 
		    	  format: value.format,
		    	  isEnumerated: value.isEnumerated, 
		    	  enumerations: value.enumerations,
		    	  additionalInstructions: escapeChar(value.additionalInstructions), 
		    	  questionNumber: escapeChar(value.questionNumber), 
		    	  label: escapeChar(value.label), 
		    	  questionId: '',
		    	  inputId: '', 
		    	  dataElementId: value.dataElementId,
		    	  valueDomainId: value.valueDomainId
		      };	

			questions.push(viewModel.createQuestion(question));
			
	});
	
	
	
	
		var section = {
				name: 'new section',
				icon: 'icon-pencil',
				type: 'section', 
				properties: [],
				id: 1, 
				questions: questions
		};
		
		
		console.log(ko.toJSON(section.questions))
		
		components.push((viewModel.createComponent(section)))
	
		jsonDeferred.resolve();

	
	
	
	jsonDeferred.done(function(){
		
		
		
		viewModel.addForm('', '','', '', true,collectionId,'', components);
		setTimeout(function(){
			initializePalette();
		}, 500);

		ko.applyBindings(viewModel);
	});
	
}

function openForms(formDesignId, formDesignName, formDesignDescription, formVersionNo, formIsDraft, formCollectionId, formVersionNo){

	//set up defer object
	var jsonDeferred = $.Deferred();

	// Activates knockout.js
	
	viewModel = new FormsModel();
		
	var components = []
		
	//ajax call to formDesign controller to get form design JSON	
	$.getJSON('../jsonFormsBuilder/' + formDesignId, function(data) {

		//console.log(data)
		//console.log(data.formDesign)
		
		//console.log(data.formDesign.containedElements)
		
		var elements = data.formDesign.containedElements
		
		$.each(elements, function( index, value ){
			
			sectionItems.push({name: value.label});

			var questions = [];
			
			//console.log(section)
			
			$.each(value.containedElements, function( index, value ){
			
				
				var question = 	{
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
			    	  questionNumber: escapeChar(value.questionNumber), 
			    	  label: escapeChar(value.label), 
			    	  questionId: value.id,
			    	  inputId: value.inputId,
			    	  dataElementId: value.dataElementId,
			    	  valueDomainId: value.valueDomainId
			      };	

				
				questions.push(viewModel.createQuestion(question));
			
			});
			
			
			
			
			var section = {
					name: value.label,
					icon: 'icon-pencil',
					type: 'section', 
					properties: [],
					id: value.id, 
					questions: questions
			};
			
			//console.log(section.questions)
			
			components.push((viewModel.createComponent(section)))
			
		});
		
		jsonDeferred.resolve()
			
	});

	jsonDeferred.done(function(){
		viewModel.addForm(formDesignId, formDesignName,formDesignDescription, formVersionNo, formIsDraft, formCollectionId, formVersionNo, components);
		setTimeout(function(){
			initializePalette();
		}, 500);

		ko.applyBindings(viewModel);
	});

}

function saveForm(){
	
	var form = viewModel.activeForm()
	
	//console.log(ko.toJSON(form))
	
	$.ajax({
		type: "POST",
		url: '../save',
		data: ko.toJSON(form),
		success: function(data){
			alert('saved')
			window.location.href = '../show/' + data.formDesignId
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
	if(text!=null && text!=''){
		text =  text.toString().replace("'", "/'");
	}
	
	return text
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}


/**
 * Returns a random integer between min and max
 * Using Math.round() will give you a non-uniform distribution!
 * checks that the id is unique i.e. not in the randomIds array
 */
function getUniqueId (min, max) {
    
	var isValid = false;
	var newRandomId;
	
	while(!isValid){
		
		newRandomId = Math.floor(Math.random() * (max - min + 1)) + min;
		
		if(jQuery.inArray(newRandomId, randomIds)==-1){
			
			isValid = true;
			
		}
	}
	
	randomIds.push(newRandomId)
	
	return newRandomId;
    
}
