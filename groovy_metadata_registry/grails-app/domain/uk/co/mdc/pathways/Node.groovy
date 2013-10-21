package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;
 

class Node extends PathwayElement{
	
	PathwaysModel pathwaysModel
	String x
	String y

	public Node(String ref, String nm,String x, String y, String desc, Collection peCollection){
		super( ref, nm, desc, peCollection)
		this.x = x
		this.y = y
	}
	
	
	
	
	
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwaysModel nullable:true
		x nullable:true
		y nullable:true
    }
}
