
    var NodeModel = function () {
        var self = this;

        //TODO: better id generation
        self.id = undefined
        self.name = undefined
        self.version = undefined
        self.description = undefined;
        self.type = 'node' //'node' | 'pathway'
        self.x = undefined
        self.y = undefined
        self.inputs = [];
        self.outputs = [];
        self.forms = [];
        self.collections = [];

        ko.track(self);

        
        self.setForms = function(JSONforms){
        	
        	console.log(JSONforms)
        	$.each(JSONforms, function(index, formJSON){
        			
	        	var form = new FormModel()
	        	form.id = formJSON.id
	        	form.name = formJSON.name
	        	
	        	self.forms.push(form)
        	});
        	
        }
        
        self.addForm = function(form){
        	
        	self.forms.push(form)
        	
        }
        
        self.addFormDialog = function(){
        	console.log('addingForm');
        	//Initial action on page load
            $('#AddFormModal').modal({ show: true, keyboard: false, backdrop: 'static' });
            formDesignListDraggable();
            /* bind the droppable behaviour for the data elements in the collection basket
        	* This allows you to drag data elements out of the collection basket. This in bound
        	* to the whole page so that the user can drag a data element out of the collections cart 
        	* anywhere on the page to remove them
        	*/
        	
        	$("#formDesignCart").droppable({
                drop: function(event, ui) {
                	if(c.id){
                		console.log('test')
                		var form = new FormModel();
                		form.id = c.id
                		form.name = c.name
                		
        	            $(c.li).remove();
        	            $(c.helper).remove();
        	            //removeFromCollectionBasket(c.id)
        	            self.addForm(form)
                	}
                }
        	});	
        	
        	//on close delete binding
        }
        
    };
    
    
    
    
  //json marshaller(so we don't get cyclical problems)
    
    NodeModel.prototype.toJSON = function() {
        var copy = ko.toJS(this); //easy way to get a clean copy
        delete copy.inputs; //remove an extra property
        delete copy.outputs; //remove an extra property
        return copy; //return the copy to be serialized
    };
