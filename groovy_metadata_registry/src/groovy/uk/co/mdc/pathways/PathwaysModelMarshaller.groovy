package uk.co.mdc.pathways;

import grails.converters.JSON


public class PathwaysModelMarshaller {

	void register() {
		JSON.registerObjectMarshaller(PathwaysModel) { PathwaysModel pathwayModel ->
				
			return [
			'id' : pathwayModel.id,
			'refId': pathwayModel?.refId,
			'name': pathwayModel?.name,
			'versionNo': pathwayModel?.versionNo,
			'isDraft': pathwayModel?.isDraft,
			'description': pathwayModel?.description,
			'nodes' : pathwayModel.getNodes(),
			'links' : pathwayModel.getLinks()
			]
		}
	}

	}


