package uk.co.mdc.pathways

import uk.co.mdc.model.Model
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.ModelElement;

abstract class PathwayElement extends ModelElement{
	
	/**
	 * This is a transient value which could well be removed in the future.
	 */
	//protected transientId
	
	String name
	String description

	static belongsTo = [pathwaysModel: PathwaysModel]
	
    static constraints = {
		//transientId nullable:true
		description nullable:true
		pathwaysModel nullable:true
		extension nullable: true
		peCollection nullable:true
    }
	

	/*static transients =  {
		transientId
	}*/
	
	static mapping = {
		 
	}
	

	Model peCollection
		
	static hasMany = [mandatoryInputs: Model,
					  mandatoryOutputs: Model,
					  optionalInputs: Model,
					  optionalOutputs: Model]

}
