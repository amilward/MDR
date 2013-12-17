function Question(prompt, additionalInstructions, questionNumber, label, style, dataTypeInstance, defaultValue, placeholder, unitOfMeasure, maxCharacters, format, isEnumerated, enumerations, questionId, inputId, dataElementId, valueDomainId)
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
	self.questionNumber = ko.observable(questionNumber);
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
		//console.log('test question clone')
		//console.log(ko.toJSON(self))
		var dti = null;
		if(self.dataTypeInstance())
		{
			dti = self.dataTypeInstance().clone();
		}
		
		var q = new Question(self.prompt(), 
				self.additionalInstructions(), self.questionNumber(),
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
		

	//	console.log(ko.toJSON(q, null, 2));
		
		return q;
	};
}