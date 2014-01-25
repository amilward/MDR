package uk.co.mdc.pathways;

import grails.converters.JSON


public class PathwayMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Pathway) { Pathway pathway ->
				
			return [
			'id' : pathway.id,
			'name': pathway?.name,
			'userVersion': pathway?.userVersion,
			'isDraft': pathway?.isDraft,
			'description'	: pathway?.description,
			'nodes' : pathway.getNodes(),
			'links' : pathway.getLinks(),
			'version' : pathway.version,
			]
		}
	}
}


