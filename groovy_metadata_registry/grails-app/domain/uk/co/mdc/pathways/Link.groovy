package uk.co.mdc.pathways

class Link extends PathwayElement{
	
	Node source
	Node target
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwayElement nullable:true
    }
}
