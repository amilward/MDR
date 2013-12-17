//Pathway model
    var CollectionModel = function () {
        var self = this;
        self.id = undefined;
        self.name = undefined;
        self.dataElements = []
        ko.track(self);
        
        
        self.previewCollections = function(){
        	
        	console.log('need to add Collection preview here')
        	
        	window.location = '../../collection/show/' + self.id
        }
    }