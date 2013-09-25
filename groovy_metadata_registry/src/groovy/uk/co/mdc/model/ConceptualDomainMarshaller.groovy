package uk.co.mdc.model
import grails.converters.JSON

class ConceptualDomainMarshaller extends CustomMarshaller{
	
	void register() {
		JSON.registerObjectMarshaller(ConceptualDomain) { ConceptualDomain conceptualDomain ->
				
			return [
			'id' : conceptualDomain.id,
			'refId' : conceptualDomain.refId,
			'name' : conceptualDomain.name,
			'description' : conceptualDomain.description,
			'valueDomains' : limitRender(conceptualDomain.valueDomains)
			]
		}
	}
	
}
