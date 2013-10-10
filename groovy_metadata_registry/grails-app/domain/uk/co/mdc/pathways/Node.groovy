package uk.co.mdc.pathways

class Node extends PathwayElement{
	
	PathwaysModel pathwaysModel
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwaysModel nullable:true
    }
}
