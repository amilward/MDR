package uk.co.mdc.pathways

import uk.co.mdc.forms.FormDesign
import uk.co.mdc.model.Collection
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementCollection
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ExtensibleObject;

abstract class PathwayElement extends ExtensibleObject{
	
	String refId
	String name
	String description

	static belongsTo = [pathwaysModel: PathwaysModel]
	
    static constraints = {
		refId unique:true, nullable:true
		description nullable:true
		pathwaysModel nullable:true
		extension nullable: true
		peCollection nullable:true
    }
	
	Collection peCollection
		
	static hasMany = [mandatoryInputs: Collection,
					  mandatoryOutputs: Collection,
					  optionalInputs: Collection,
					  optionalOutputs: Collection]

}
