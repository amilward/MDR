package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;

class Link extends PathwayElement{
	
	Node source
	Node target
	
	public Link(String ref, String nm,String x, String y, String desc, Collection peCollection){
		super( ref, nm, desc, peCollection)
		this.x = x
		this.y = y
	}
	
	static belongsTo = [pathwayElement : PathwayElement]

    static constraints = {
		pathwayElement nullable:true
    }
}
