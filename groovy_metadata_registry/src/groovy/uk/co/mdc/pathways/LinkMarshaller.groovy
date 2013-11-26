package uk.co.mdc.pathways

import grails.converters.JSON

class LinkMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Link) { Link link ->
				
			return [
			'source': 'node' + link?.source?.id,
			'target': 'node' + link?.target?.id,
			'label': link?.name
			]
		}
	}
	
}
