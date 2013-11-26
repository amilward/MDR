
//Pathway model
    var FormModel = function () {
        var self = this;
        self.id = undefined;
        self.name = undefined;
        self.dataElemets = []
        ko.track(self);
        
        
        self.previewForm = function(){
        	
        	console.log('need to add form preview here')
        	
        	window.location = 'http://localhost/groovy_metadata_registry/formDesign/show/' + self.id
        }
    }