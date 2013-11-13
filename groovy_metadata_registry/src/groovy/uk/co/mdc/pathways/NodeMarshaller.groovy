package uk.co.mdc.pathways

//'dataElements' : '[{"id" : "Data_Element_10", "description" : "How loud can you shout?"}]'      + node?.GetElementsJSON() + 
//node.peCollection.dataElementsCollections()
//

import grails.converters.JSON

//{"id" : "' + node.peCollection.id + '", "description" : "' + node.peCollection.description + '"}

class NodeMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Node) { Node node ->
				
			return [
			'id': 'node' + node?.id,
			'name' : node?.name,
			'description': node?.description,
			'x' : node.x,
			'y' : node.y,
			'mandatoryInputs': node?.mandatoryInputs,
			'mandatoryOutputs': node?.mandatoryOutputs,
			'optionalInputs': node?.optionalInputs,
			'optionalOutputs': node?.optionalOutputs
			]
		}
	}
}
