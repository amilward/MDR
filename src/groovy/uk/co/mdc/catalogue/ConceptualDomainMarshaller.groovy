package uk.co.mdc.catalogue
import grails.converters.JSON

class ConceptualDomainMarshaller extends CustomMarshaller{
	
	void register() {
		JSON.registerObjectMarshaller(ConceptualDomain) { ConceptualDomain conceptualDomain ->
				
			return [
			'id' : conceptualDomain.id,
			'name' : conceptualDomain.name,
			'description' : conceptualDomain.description,
			'valueDomains' : limitRender(conceptualDomain.valueDomains)
			]
		}
	}
	
}
