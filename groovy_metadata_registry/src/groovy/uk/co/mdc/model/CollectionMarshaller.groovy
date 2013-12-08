package uk.co.mdc.model
import grails.converters.JSON
import uk.co.mdc.forms.FormDesign;

class CollectionMarshaller extends CustomMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Collection) { Collection collection ->
				
			return [
			'id' : collection.id,
			'name' : collection.name,
			'description' : collection.description,
			'dataElements': limitRender(collection.dataElementCollections()), 
			'collectionType': getCollectionType(collection)
			]
		}
	}
	
	def getCollectionType(Collection collection){
		if(collection.instanceOf(FormDesign)){
			return 'form';
		}else{
			return 'collection';
		}
		
	}

}


