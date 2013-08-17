package uk.co.mdc.model
import grails.converters.JSON

class ConceptualDomainMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(ConceptualDomain) { ConceptualDomain conceptualDomain ->
				
			return [
			'id' : conceptualDomain.id,
			'refId' : conceptualDomain.refId,
			'name' : conceptualDomain.name,
			'description' : conceptualDomain.description,
			'valueDomains' : conceptualDomain.valueDomains
			]
		}
	}
	
}
