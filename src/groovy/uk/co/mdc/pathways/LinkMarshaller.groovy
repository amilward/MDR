package uk.co.mdc.pathways

import grails.converters.JSON

class LinkMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Link) { Link link ->
				
			return [
			'id' : link?.id,
			'source': link?.source?.id,
			'target': link?.target?.id,
			'version' : link?.version,
			]
		}
	}
	
}
