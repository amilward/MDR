package uk.co.mdc.pathways

import uk.co.mdc.model.Collection
import uk.co.mdc.model.DataElement
import uk.co.mdc.model.DataElementCollection
import uk.co.mdc.model.DataElementConcept

abstract class PathwayElement {
	
	String refId
	String name
	String description

	Collection peCollection
	
	String GetElementsJSON(){
		def result = []
		def de = new StringBuffer()
		def isFirst = new Boolean('True')

		Iterator i = peCollection.dataElementCollections.iterator();		
		while (i.hasNext()){
			DataElementCollection it = i.next()
			if(isFirst){

				de.append('{"id" : "' + it.dataElement.refId + '", "description" : "' + it.dataElement.description + '"}')
			}else{
				de.append(',')
				de.append('{"id" : "' + it.dataElement.refId + '", "description" : "' + it.dataElement.description + '"}')
			}
			isFirst = new Boolean('False')
		}
		
		result = de.toString()
	}
	

	
	static hasMany = [mandatoryInputs: Collection,
					  mandatoryOutputs: Collection,
					  optionalInputs: Collection,
					  optionalOutputs: Collection]

    static constraints = {
		mandatoryInputs nullable:true
		mandatoryOutputs nullable:true
		optionalInputs nullable:true
		optionalOutputs nullable:true
		refId unique:true
		description nullable:true
		peCollection nullable:true
    }
}
