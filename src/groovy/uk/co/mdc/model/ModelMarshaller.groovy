package uk.co.mdc.model
import grails.converters.JSON
import uk.co.mdc.forms.FormDesign;

class ModelMarshaller extends CustomMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Model) { Model model ->
				
			return [
			'id' : model.id,
			'name' : model.name,
			'description' : model.description,
			'relationships': limitRender(model.relations()), 
			'modelType': getModelType(model)
			]
		}
	}
	
	def getModelType(Model model){
		if(model.instanceOf(FormDesign)){
			return 'form';
		}else{
			return 'model';
		}
		
	}

}


