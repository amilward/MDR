function Section(title, sectionId, questions){
	
	var self = this;
	
	self.title = ko.observable(title);
	
	self.sectionId = ko.observable(sectionId);
	
	self.questions = ko.observableArray(questions);
	
	self.addQuestion = function(q){
		
		self.questions.push(q)
	}
	
	self.clone = function() {
		
		//console.log('cloning section test')
		
		var s = new Section(
				self.title(),
				self.sectionId(),
				self.questions()
				);
		
		
		//console.log(ko.toJSON(s))
		
		return s
		
	}
	
	self.deleteQuestion = function(comp) {

	    var heading = 'Confirm Form Component Delete';
	    var question = 'Please confirm that you wish to delete this question: ' + comp.prompt() + '.';
	    var cancelButtonTxt = 'Cancel';
	    var okButtonTxt = 'Confirm';

	    var callback = function() {
	    	self.questions.remove(comp);
	    	refreshFormPanelViews();
	    };

	    confirm(heading, question, cancelButtonTxt, okButtonTxt, callback);
	};
	
	
	
}
