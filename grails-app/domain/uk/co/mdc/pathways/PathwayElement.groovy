package uk.co.mdc.pathways

import uk.co.mdc.catalogue.Model
import uk.co.mdc.catalogue.DataElement
import uk.co.mdc.catalogue.CatalogueElement;

abstract class PathwayElement extends CatalogueElement{
	
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
