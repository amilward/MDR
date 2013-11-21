package uk.co.mdc.pathways

import uk.co.mdc.forms.FormDesign
import uk.co.mdc.model.Collection
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementCollection
import uk.co.mdc.model.DataElementConcept
import uk.co.mdc.model.ExtensibleObject;

abstract class PathwayElement extends ExtensibleObject  {
	
	String refId
	String name
	String description

	Collection peCollection
		
	static hasMany = [mandatoryInputs: Collection,
					  mandatoryOutputs: Collection,
					  optionalInputs: Collection,
					  optionalOutputs: Collection]

    static constraints = {
		refId unique:true
		description nullable:true
		peCollection nullable:true
    }
}
