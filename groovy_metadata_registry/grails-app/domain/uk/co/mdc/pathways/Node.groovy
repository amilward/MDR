package uk.co.mdc.pathways

import uk.co.mdc.model.Collection;

class Node extends PathwayElement{
	
	PathwaysModel subModel
	String x
	String y

	static belongsTo = [pathwaysModel: PathwaysModel]
	
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
