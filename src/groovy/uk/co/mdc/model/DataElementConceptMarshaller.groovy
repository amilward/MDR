package uk.co.mdc.model
import grails.converters.JSON

class DataElementConceptMarshaller extends CustomMarshaller {

	void register() {
		JSON.registerObjectMarshaller(DataElementConcept) { DataElementConcept dataElementConcept ->
				
			return [
			'id' : dataElementConcept.id,
			'name' : dataElementConcept.name,
			'description' : dataElementConcept.description,
			'parent_id' : dataElementConcept?.parent?.id,
			'parent_name' : dataElementConcept?.parent?.name,
			'subConcepts' : limitRender(dataElementConcept?.subConcepts),
			'dataElements' : limitRender(dataElementConcept?.dataElements)
			]
		}
	}
	
}
