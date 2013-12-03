package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;

class Node extends PathwayElement{
	
	PathwaysModel subModel
	String x //ISSUE It seems strange to me that x and y are not integers. (@charlescrichton)
	String y

	static belongsTo = [pathwaysModel: PathwaysModel]
	
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
		pathwaysModel nullable: true
		subModel nullable: true
		x nullable:true
		y nullable:true
    }
	
	

}
