package uk.co.mdc.pathways

class Node extends PathwayElement{
	
	PathwaysModel pathwaysModel
	String x
	String y
	
	
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwaysModel nullable:true
		x nullable:true
		y nullable:true
    }
}
