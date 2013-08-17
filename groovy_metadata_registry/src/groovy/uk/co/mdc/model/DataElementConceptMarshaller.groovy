package uk.co.mdc.model
import grails.converters.JSON

class DataElementConceptMarshaller {

	void register() {
		JSON.registerObjectMarshaller(DataElementConcept) { DataElementConcept dataElementConcept ->
				
			return [
			'id' : dataElementConcept.id,
			'refId' : dataElementConcept.refId,
			'name' : dataElementConcept.name,
			'description' : dataElementConcept.description,
			'parent_id' : dataElementConcept?.parent?.id,
			'parent_name' : dataElementConcept?.parent?.name,
			'subConcepts' : dataElementConcept?.subConcepts,
			'dataElements' : dataElementConcept?.dataElements
			]
		}
	}
	
}
