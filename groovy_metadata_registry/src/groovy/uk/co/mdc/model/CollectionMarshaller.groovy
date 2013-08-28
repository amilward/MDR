package uk.co.mdc.model
import grails.converters.JSON

class CollectionMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Collection) { Collection collection ->
				
			return [
			'id' : collection.id,
			'refId' : collection.refId,
			'name' : collection.name,
			'description' : collection.description,
			'dataElements': collection.dataElementCollections(),
			'formSpecifications': collection?.formSpecifications
			]
		}
	}

}


