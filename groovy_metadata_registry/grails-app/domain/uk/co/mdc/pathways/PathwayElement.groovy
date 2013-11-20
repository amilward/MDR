package uk.co.mdc.pathways

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
		refId unique:true
		description nullable:true
		pathwaysModel nullable:true
		extension nullable: true
    }
	

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
	
}
