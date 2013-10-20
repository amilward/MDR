package uk.co.mdc.pathways

import uk.co.mdc.model.Collection

abstract class PathwayElement {
	
	String refId
	String name
	String description
	Collection peCollection
	
	String GetElementsJSON(){
		def result = []
		def de = '{}'
		
		peCollection.dataElementCollections.each{
			de =+ ',{"id" : "' + it.refId + '", "description" : "' + it.description + '"}'
		}
		result
	}
	
	
	static hasMany = [nodes: Node, links: Link, 
					  mandatoryInputs: Collection,
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
