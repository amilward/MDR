package uk.co.mdc.pathways

import uk.co.mdc.model.Model;

class Node extends PathwayElement{
	
	String x
	String y
	PathwaysModel subModel
	PathwaysModel pathwaysModel

	static belongsTo = [pathwaysModel: PathwaysModel]
	
	static hasOne = [subModel: PathwaysModel]
	
	public Node(String refId, String name,String x, String y, String desc, Model peCollection){
		super( refId, name, desc, peCollection)
		this.x = x
		this.y = y
	}
	
	static hasMany = [
		mandatoryInputs: Model,
		mandatoryOutputs: Model,
		optionalInputs: Model,
		optionalOutputs: Model]

    static constraints = {
		pathwaysModel nullable: true
		subModel nullable: true
		x nullable:true
		y nullable:true
    }
	
	static mapping = {
		sort "name"
	}

}
