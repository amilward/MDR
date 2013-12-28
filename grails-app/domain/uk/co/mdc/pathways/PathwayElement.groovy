package uk.co.mdc.pathways

import uk.co.mdc.model.Collection
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementCollection
import uk.co.mdc.model.DataElementConcept
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
		relations nullable: true
		peCollection nullable:true
    }
	

	/*static transients =  {
		transientId
	}*/
	
	static mapping = {
		 
	}
	

	Collection peCollection
		
	static hasMany = [mandatoryInputs: Collection,
					  mandatoryOutputs: Collection,
					  optionalInputs: Collection,
					  optionalOutputs: Collection]

}
