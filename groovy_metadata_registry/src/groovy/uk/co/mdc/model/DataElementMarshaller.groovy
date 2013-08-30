package uk.co.mdc.model
import grails.converters.JSON

class DataElementMarshaller extends CustomMarshaller{
	
	
	void register() {
		JSON.registerObjectMarshaller(DataElement) { DataElement dataElement ->
				
			return [
			'id' : dataElement.id,
			'refId' : dataElement.refId,
			'name' : dataElement.name,
			'description' : dataElement.description,
			'definition' : dataElement.definition,
			'parent_id' : dataElement?.parent?.id,
			'parent_name' : dataElement?.parent?.name,
			'dataElementConcept_id' : dataElement?.dataElementConcept?.id,
			'dataElementConcept_name' : dataElement?.dataElementConcept?.name,
			'subElements': limitRender(dataElement?.subElements),
			'valueDomains': limitRender(dataElement.dataElementValueDomains()),
			'collections': limitRender(dataElement.dataElementCollections()),
			'externalSynonyms': dataElement?.externalSynonyms?.name,
			]
		}
	}
}




