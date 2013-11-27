package uk.co.mdc.pathways

import grails.converters.JSON

class LinkMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Link) { Link link ->
				
			return [
			'source': link?.source?.id,
			'target': link?.target?.id,
			'label': link?.name,
			'version' : link?.version
			]
		}
	}
	
}
