package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;
 

class Node extends PathwayElement{
	
	PathwaysModel pathwaysModel
	String x
	String y
	
	public Node(String refId, String name,String x, String y, String desc, Collection peCollection){
		super( refId, name, desc, peCollection)
		this.x = x
		this.y = y
	}
	
	static hasMany = [
		mandatoryInputs: Collection,
		mandatoryOutputs: Collection,
		optionalInputs: Collection,
		optionalOutputs: Collection]

    static constraints = {
		pathwaysModel nullable:true
		x nullable:true
		y nullable:true
    }
}
