package uk.co.mdc.pathways;

import grails.converters.JSON


public class PathwaysModelMarshaller {

	void register() {
		JSON.registerObjectMarshaller(PathwaysModel) { PathwaysModel pathwayModel ->
				
			return [
			'nodes' : pathwayModel.getNodes(),
			'links' : pathwayModel.getLinks()
			]
		}
	}

	}


