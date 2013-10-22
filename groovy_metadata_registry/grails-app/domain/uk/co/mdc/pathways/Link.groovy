package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;

class Link extends PathwayElement{
	
	Node source
	Node target
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwayElement nullable:true
    }
}
