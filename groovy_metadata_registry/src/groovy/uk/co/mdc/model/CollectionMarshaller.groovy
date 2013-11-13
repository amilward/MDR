package uk.co.mdc.model
import grails.converters.JSON

class CollectionMarshaller extends CustomMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Collection) { Collection collection ->
				
			return [
			'id' : collection.id,
			'refId' : collection.refId,
			'name' : collection.name,
			'description' : collection.description,
			'dataElements': limitRender(collection.dataElementCollections())
			]
		}
	}

}


