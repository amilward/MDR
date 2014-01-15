
//Pathway model
    var FormModel = function () {
        var self = this;
        self.id = undefined;
        self.name = undefined;
        self.dataElements = []
        ko.track(self);
        
        
        self.previewForm = function(){
        	
        	console.log('need to add form preview here')
        	
        	window.location = '../../formDesign/show/' + self.id
        }
        
      
    }