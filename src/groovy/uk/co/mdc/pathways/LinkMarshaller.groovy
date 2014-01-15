package uk.co.mdc.pathways

import grails.converters.JSON

class LinkMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Link) { Link link ->
				
			return [
			'id' : link?.id,
			'source': link?.source?.id,
			'target': link?.target?.id,
			'name': link?.name,
			'pathwaysModelVersion': link?.pathwaysModel.version,
			'version' : link?.version,
            'description': link?.description
			]
		}
	}
	
}
