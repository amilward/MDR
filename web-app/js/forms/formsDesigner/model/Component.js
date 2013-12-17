function Component(iid, eid, properties, icon) {
	var self = this;
	self.dateCreated = new Date();
	self.internalIdentifier = ko.observable(iid);
	self.externalIdentifier = ko.observable(eid);
	self.question = ko.observable();
	self.section = ko.observable();
	self.icon = ko.observable(icon);
	self.setQuestion = function(q) {
		self.question(q);
	};
	
	self.setSection = function(s) {
		self.section(s);
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
	
	self.clone = function(sectionNo){
		
		//
		
		if(self.question()){
			
			//console.log('creating a question')
			
			//console.log(sectionNo)

			viewModel.setCurrentlySelectedFormSectionIdx(sectionNo)
			
			c = viewModel.currentlySelectedFormSection()
			
			var s = c.section()
			
			var q = self.question().clone();
			
			s.addQuestion(q);
			
			c.setSection(s);

			//console.log(ko.toJSON(c))
			
		}else if(self.section()){
			
			c = new Component(lastComponentID++, self.externalIdentifier(), self.properties(), self.icon());

			//console.log('cloning component section')
			
			var s = new Section("new section", 0, [])
			
			c.setSection(s);

		}
		
		
		//var s = self.question().clone();
		//c.setSection(s);
		
		//console.log("cloning");
		
		//console.log(ko.toJSON(c, null, 2));
		
		return c;
		
	};
	

	
}