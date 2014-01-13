package uk.co.mdc.pathways

import uk.co.mdc.model.Model;

class Link extends PathwayElement{
	
	Node source
	Node target

	static belongsTo = [pathwaysModel: PathwaysModel]
	
	static constraints = {
		pathwaysModel nullable: true
	}
	
}
